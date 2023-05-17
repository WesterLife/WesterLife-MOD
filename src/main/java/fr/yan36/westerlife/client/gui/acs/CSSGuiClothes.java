package fr.yan36.westerlife.client.gui.acs;

import fr.aym.acsguis.component.layout.GuiScaler;
import fr.aym.acsguis.component.panel.GuiFrame;
import fr.aym.acsguis.component.panel.GuiPanel;
import fr.aym.acsguis.component.textarea.GuiLabel;
import fr.dynamx.common.items.DynamXItemArmor;
import fr.yan36.westerlife.Main;
import fr.yan36.westerlife.client.Client;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import scala.Int;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

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
                    label.allowLineBreak();
                    label.getStyle().setOffsetY(l * 20);
                    label.addClickListener((mouseX, mouseY, mouseButton) -> {
                        HashMap<Integer, List<Item>> list = Client.superpositionState;
                        Integer k1 = mc.player.getEntityId();
                        list.put(k1, Collections.singletonList(item));
                    });
                    background.add(label);
                    l++;
                }
            }
        }

        Item item = Items.AIR;
        GuiLabel label = new GuiLabel(item.getItemStackDisplayName(new net.minecraft.item.ItemStack(item)));
        label.setCssClass("item");
        label.allowLineBreak();
        label.getStyle().setOffsetY(l * 20);
        label.addClickListener((mouseX, mouseY, mouseButton) -> {
            HashMap<Integer, List<Item>> list = Client.superpositionState;
            Integer k1 = mc.player.getEntityId();
            list.put(k1, Collections.singletonList(item));
        });
        background.add(label);

        add(background);

    }

    @Override
    public List<ResourceLocation> getCssStyles() {
        return Collections.singletonList(new ResourceLocation(Main.MODID, "acsgui/clothes.css"));
    }
}
