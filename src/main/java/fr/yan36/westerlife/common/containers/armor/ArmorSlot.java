package fr.yan36.westerlife.common.containers.armor.slots;

import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public class ArmorSlot  extends SlotItemHandler {
    public ArmorSlot(final IItemHandler inv, final int index, final int xPosition, final int yPosition, final EntityEquipmentSlot slot) {
        super(inv, index, xPosition, yPosition);
    }

    @Override
    public int getSlotStackLimit() {
        return 1;
    }

    @Override
    public boolean isItemValid(@Nonnull final ItemStack stack) {
        return stack.getItem().isValidArmor(stack, EntityEquipmentSlot.CHEST, null);
    }
}