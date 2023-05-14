package fr.yan36.westerlife.client.gui.acs;

import fr.aym.acsguis.component.layout.GridLayout;
import fr.aym.acsguis.component.layout.GuiScaler;
import fr.aym.acsguis.component.panel.GuiFrame;
import fr.aym.acsguis.component.panel.GuiPanel;
import fr.aym.acsguis.component.textarea.GuiLabel;
import fr.aym.acsguis.component.textarea.GuiTextArea;
import fr.dynamx.common.DynamXContext;
import fr.dynamx.common.contentpack.DynamXObjectLoaders;
import fr.dynamx.common.items.DynamXItemArmor;
import fr.dynamx.common.items.DynamXItemRegistry;
import fr.yan36.westerlife.Main;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class CSSGuiClothes extends GuiFrame {
    public CSSGuiClothes() {
        super(new GuiScaler.Identity());

        GuiPanel background = new GuiPanel();
        background.setCssClass("background");



        InventoryPlayer i = Minecraft.getMinecraft().player.inventory;
        int l = 0;
        for (int k = 0; k < i.getSizeInventory(); k++) {
            Item item = i.getStackInSlot(k).getItem();
            if (!item.equals(Items.AIR)) {
                System.out.println(item);
                if(item instanceof DynamXItemArmor<?>) {
                    GuiLabel label = new GuiLabel(item.getItemStackDisplayName(i.getStackInSlot(k)));
                    label.setCssClass("item");
                    System.out.println("ARMOR ! " + item.getItemStackDisplayName(i.getStackInSlot(k))) ;
                    label.allowLineBreak();
                    label.getStyle().setOffsetY(l * 20);
                    background.add(label);
                    l++;
                }
            } else {
                System.out.println("air at slot " + k);
            }
        }

        add(background);

    }

    @Override
    public List<ResourceLocation> getCssStyles() {
        return Collections.singletonList(new ResourceLocation(Main.MODID, "acsgui/clothes.css"));
    }
}
