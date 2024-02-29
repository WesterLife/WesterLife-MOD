package fr.gabidut76.westerlife.common.capabilities.playerinventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.items.IItemHandlerModifiable;

public interface IExtraItemHandler extends IItemHandlerModifiable {
    void setPlayer(EntityPlayer player);
}