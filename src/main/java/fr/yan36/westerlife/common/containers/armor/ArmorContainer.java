package fr.yan36.westerlife.common.containers.armor;

import fr.yan36.westerlife.common.capabilities.playerinventory.ExtraItemCapability;
import fr.yan36.westerlife.common.capabilities.playerinventory.IExtraItemHandler;
import fr.yan36.westerlife.common.containers.armor.slots.ArmorSlot;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/***
 * Modified copy of Vanilla's Player inventory
 */
public class ArmorContainer extends Container {

    private static final EntityEquipmentSlot[] EQUIPMENT_SLOTS = new EntityEquipmentSlot[]{EntityEquipmentSlot.HEAD, EntityEquipmentSlot.CHEST,
            EntityEquipmentSlot.LEGS, EntityEquipmentSlot.FEET};
    public final InventoryCrafting craftMatrix = new InventoryCrafting(this, 2, 2);
    public final InventoryCraftResult craftResult = new InventoryCraftResult();
    private final EntityPlayer thePlayer;
    public IExtraItemHandler extra;
    public boolean isLocalWorld;

    public ArmorContainer(final InventoryPlayer playerInv, final boolean isLocalWorld, final EntityPlayer player) {
        this.isLocalWorld = isLocalWorld;
        this.thePlayer = player;
        this.onCraftMatrixChanged(this.craftMatrix);

        this.addSlots(playerInv, player);
    }

    public void addSlots(final InventoryPlayer playerInv, final EntityPlayer player) {
        this.inventorySlots.clear();
        this.inventoryItemStacks.clear();

        this.extra = player.getCapability(ExtraItemCapability.CAPABILITY, null);

        this.addSlotToContainer(new SlotCrafting(playerInv.player, this.craftMatrix, this.craftResult, 0, 154, 28));

        for (int i = 0; i < 2; ++i) {
            for (int j = 0; j < 2; ++j) {
                this.addSlotToContainer(new Slot(this.craftMatrix, j + (i * 2), 116 + (j * 18), 18 + (i * 18)));
            }
        }

        for (int k = 0; k < 4; k++) {
            final EntityEquipmentSlot slot = EQUIPMENT_SLOTS[k];
            this.addSlotToContainer(new Slot(playerInv, 36 + (3 - k), 8, 8 + (k * 18)) {
                @Override
                public int getSlotStackLimit() {
                    return 1;
                }

                @Override
                public boolean isItemValid(final ItemStack stack) {
                    return stack.getItem().isValidArmor(stack, slot, player);
                }

                @Override
                public boolean canTakeStack(final EntityPlayer playerIn) {
                    final ItemStack itemstack = this.getStack();
                    return (itemstack.isEmpty() || playerIn.isCreative() || !EnchantmentHelper.hasBindingCurse(itemstack)) && super.canTakeStack(playerIn);
                }

                @Override
                public String getSlotTexture() {
                    return ItemArmor.EMPTY_SLOT_NAMES[slot.getIndex()];
                }
            });
        }

        // Second light gray slots bar
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlotToContainer(new Slot(playerInv, j + ((i + 1) * 9), 8 + (j * 18), 102 - 12 + (i * 18)));
            }
        }

        // First light gray slots bar
        for (int i = 0; i < 9; ++i) {
            this.addSlotToContainer(new Slot(playerInv, i, 8 + (i * 18), 166 - 12));
        }

        // This is for the OFFHAND MouseHover
        this.addSlotToContainer(new Slot(playerInv, 40, 76, 62) {
            @Override
            @Nullable
            @SideOnly(Side.CLIENT)
            public String getSlotTexture() {
                return "minecraft:items/empty_armor_slot_shield";
            }
        });

//        this.addSlotToContainer(new SlotBackpack(this.extra, 0, ModUtil.BACKPACK_SLOT_OFFSET_X, ModUtil.BACKPACK_SLOT_OFFSET_Y + 1) {
//            @Override
//            public void onSlotChanged() {
//                ContainerInventoryModified.this.updateBackpack();
//                ContainerInventoryModified.this.addSlots(playerInv, player);
//                super.onSlotChanged();
//            }
//        });


        for (int i = 0; i < 2; i++) {
            this.addSlotToContainer(new ArmorSlot(this.extra, 1, 0, 0, EntityEquipmentSlot.HEAD) {
                @Override
                public void onSlotChanged() {
                    ArmorContainer.this.addSlots(playerInv, player);
                    super.onSlotChanged();
                }

                @Override
                @Nullable
                @SideOnly(Side.CLIENT)
                public String getSlotTexture() {
                    return "minecraft:items/empty_armor_slot_shield";
                }
            });
        }


    }




    @Override
    public void onCraftMatrixChanged(final IInventory par1IInventory) {
        this.slotChangedCraftingGrid(this.thePlayer.getEntityWorld(), this.thePlayer, this.craftMatrix, this.craftResult);
    }

    @Override
    public void onContainerClosed(final EntityPlayer player) {
        super.onContainerClosed(player);
        this.craftResult.clear();

        if (!player.world.isRemote) {
            this.clearContainer(player, player.world, this.craftMatrix);
        }
    }

    @Override
    public boolean canInteractWith(final EntityPlayer par1EntityPlayer) {
        return true;
    }

    @Override
    public ItemStack transferStackInSlot(final EntityPlayer playerIn, final int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        final Slot slot = this.inventorySlots.get(index);

        if ((slot != null) && slot.getHasStack()) {
            final ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            final EntityEquipmentSlot entityequipmentslot = EntityLiving.getSlotForItemStack(itemstack);


            if (itemstack1.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            final ItemStack itemstack2 = slot.onTake(playerIn, itemstack1);

            if (index == 0) {
                playerIn.dropItem(itemstack2, false);
            }
        }

        return itemstack;
    }

    @Override
    public boolean canMergeSlot(final ItemStack stack, final Slot slot) {
        return (slot.inventory != this.craftResult) && super.canMergeSlot(stack, slot);
    }

    @Override
    public NonNullList<ItemStack> getInventory() {
        return super.getInventory();
    }
}