package fr.gabidut76.westerlife.client.gui.acs;

import fr.aym.acsguis.component.layout.GuiScaler;
import fr.aym.acsguis.component.panel.GuiPanel;
import fr.aym.acsguis.component.panel.container.GuiContainer;
import fr.aym.acsguis.component.panel.container.GuiSlot;
import fr.gabidut76.westerlife.common.containers.armor.ArmorContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;

import java.util.Collections;
import java.util.List;

public class GuiCustomInventory extends GuiContainer {


    public GuiCustomInventory(EntityPlayer player) {
        super(0, 0, 0, 0, new GuiScaler.Identity());

        ArmorContainer container = new ArmorContainer(player.inventory, !player.world.isRemote, player);

        GuiPanel background = new GuiPanel();
        background.setCssClass("background");

        GuiSlot slot = new GuiSlot(new Slot(player.inventory, 0, 0, 0));
        slot.setCssClass("slot");

        background.add(slot);


        add(background);


    }


    @Override
    public List<ResourceLocation> getCssStyles() {
        return Collections.singletonList(new ResourceLocation("westerlife:acsgui/inventory.css"));
    }

    @Override
    public boolean isEnableDebugPanel() {
        return true;
    }
}
