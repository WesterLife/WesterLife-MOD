package fr.gabidut76.westerlife.client.gui;

import fr.gabidut76.westerlife.client.gui.other.GuiCustomInventory;
import fr.gabidut76.westerlife.client.gui.other.GuiInventory;
import fr.gabidut76.westerlife.common.containers.inventory.ContainerInventory;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
public class GuiHandler implements IGuiHandler {
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == 7) {
            return new ContainerInventory(player);
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == 7) {
            return new GuiInventory(new ContainerInventory(player));
        }
        return null;
    }
}
