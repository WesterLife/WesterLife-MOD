package fr.yan36.westerlife.common.containers.inventory;

import fr.dynamx.common.items.DynamXItemArmor;
import fr.yan36.westerlife.common.capabilities.playerinventory.ExtraItemCapability;
import fr.yan36.westerlife.common.capabilities.playerinventory.IExtraItemHandler;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.inventory.Container;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nullable;

public class ContainerInventory extends Container {
    private ItemStackHandler handler;

    public ContainerInventory(EntityPlayer player) {
        super();

        if (player.hasCapability(ExtraItemCapability.CAPABILITY, null)) {
            handler = (ItemStackHandler) player.getCapability(ExtraItemCapability.CAPABILITY, null);
        }
        this.addSlotToContainer(new SlotMoreArmor(handler, 0, 80, 16, EntityEquipmentSlot.HEAD));
        this.addSlotToContainer(new SlotMoreArmor(handler, 1, 80, 40, EntityEquipmentSlot.CHEST));
        this.addSlotToContainer(new SlotMoreArmor(handler, 2, 80, 65, EntityEquipmentSlot.LEGS));
        this.addSlotToContainer(new SlotMoreArmor(handler, 3, 80, 88, EntityEquipmentSlot.FEET));

        this.addSlotToContainer(new SlotMoreArmor(handler, 4, 103, 40, EntityEquipmentSlot.CHEST));
        this.addSlotToContainer(new SlotMoreArmor(handler, 5, 103, 65, EntityEquipmentSlot.LEGS));

        this.addSlotToContainer(new SlotMoreArmor(handler, 6, 125, 40, EntityEquipmentSlot.CHEST));
        this.addSlotToContainer(new SlotMoreArmor(handler, 7, 125, 65, EntityEquipmentSlot.LEGS));

        this.addSlotToContainer(new SlotMoreArmor(handler, 8, 148, 40, EntityEquipmentSlot.CHEST));

        InventoryPlayer playerInv = player.inventory;
        for (int j = 0; j < 6; ++j) {
            this.addSlotToContainer(new Slot(playerInv, 9 + j, 57 + j * 23, 111));
        }

        EntityEquipmentSlot[] VALID_EQUIPMENT_SLOTS = new EntityEquipmentSlot[] {EntityEquipmentSlot.HEAD, EntityEquipmentSlot.CHEST, EntityEquipmentSlot.LEGS, EntityEquipmentSlot.FEET};

        for (int k = 0; k < 4; ++k)
        {
            final EntityEquipmentSlot entityequipmentslot = VALID_EQUIPMENT_SLOTS[k];
            this.addSlotToContainer(new Slot(playerInv, 36 + (3 - k), 57, 16 + k * 24)
            {
                public int getSlotStackLimit()
                {
                    return 1;
                }
                public boolean isItemValid(ItemStack stack)
                {
                    return stack.getItem().isValidArmor(stack, entityequipmentslot, player);
                }
                public boolean canTakeStack(EntityPlayer playerIn)
                {
                    ItemStack itemstack = this.getStack();
                    return !itemstack.isEmpty() && !playerIn.isCreative() && EnchantmentHelper.hasBindingCurse(itemstack) ? false : super.canTakeStack(playerIn);
                }
            });
        }


        for (int k = 0; k < 9; ++k) {
            this.addSlotToContainer(new Slot(playerInv, k, (k * 23) - 12, 134));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return true;
    }

    @Override
    public void onContainerClosed(EntityPlayer player) {
        super.onContainerClosed(player);
        System.out.println("Container closed");
        System.out.println(player.hasCapability(ExtraItemCapability.CAPABILITY, null));
        if (player.hasCapability(ExtraItemCapability.CAPABILITY, null) && !player.world.isRemote) {
            IExtraItemHandler handler = player.getCapability(ExtraItemCapability.CAPABILITY, null);
            assert handler != null;
            handler.setStackInSlot(0, this.handler.getStackInSlot(0));
            handler.setStackInSlot(1, this.handler.getStackInSlot(1));

        }

    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        //si l'item est un ItemSim transferer dans le slot 0
        //sinon transferer dans l'inventaire du joueur
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();

            itemstack = itemstack1.copy();
            if (index == 0) {
                if (!this.mergeItemStack(itemstack1, 1, 37, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.mergeItemStack(itemstack1, 0, 1, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }
        }
        return itemstack;
    }

    public class SlotMoreArmor extends SlotItemHandler {
        final EntityEquipmentSlot armorType;
        public SlotMoreArmor(ItemStackHandler handler, int i, int i1, int i2, EntityEquipmentSlot armorType) {
            super(handler, i, i1, i2);
            this.armorType = armorType;
        }

        @Override
        public boolean isItemValid(ItemStack stack) {
                return stack.getItem() instanceof DynamXItemArmor<?> && ((ItemArmor) stack.getItem()).armorType == this.armorType;
        }
    }
}
