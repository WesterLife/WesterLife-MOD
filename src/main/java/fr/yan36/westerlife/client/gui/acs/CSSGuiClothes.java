package fr.yan36.westerlife.client.gui.acs;

import fr.aym.acsguis.api.ACsGuiApi;
import fr.aym.acsguis.component.layout.GuiScaler;
import fr.aym.acsguis.component.panel.GuiFrame;
import fr.aym.acsguis.component.panel.GuiPanel;
import fr.aym.acsguis.component.textarea.GuiLabel;
import fr.dynamx.common.items.DynamXItemArmor;
import fr.yan36.westerlife.Main;
import fr.yan36.westerlife.common.network.PacketSyncClothes;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
                if(item instanceof DynamXItemArmor<?>) {
                    GuiLabel label = new GuiLabel(item.getItemStackDisplayName(i.getStackInSlot(k)));
                    label.setCssClass("item");
                    label.allowLineBreak();

                    if(Main.wl_db.getString(Minecraft.getMinecraft().player.getUniqueID().toString()) == null) {
                        label.getStyle().setFontColor(TextFormatting.RED);
                    } else {
                        ArrayList<String> itemsReadOnly = new ArrayList<>(Arrays.asList(Main.wl_db.getString(Minecraft.getMinecraft().player.getUniqueID().toString()).split(",")));
                        if(itemsReadOnly.contains(((DynamXItemArmor<?>) item).getInfo().getFullName())) {
                            label.getStyle().setFontColor(TextFormatting.GREEN);
                        } else {
                            label.getStyle().setFontColor(TextFormatting.RED);
                        }
                    }

                    label.getStyle().setOffsetY(l * 20);

                    label.addClickListener((mouseX, mouseY, mouseButton) -> {

                        if(Main.wl_db.getString(Minecraft.getMinecraft().player.getUniqueID().toString()) == null) {
                            Main.network.sendToServer(new PacketSyncClothes(Minecraft.getMinecraft().player.getUniqueID().toString(), ((DynamXItemArmor<?>) item).getInfo().getFullName(), "add", ((DynamXItemArmor<?>) item).getInfo().getObjArmor().getActiveTextureId(), ((DynamXItemArmor<?>) item).getInfo().getObjArmor().getActivePart().getSlotIndex()));

                            label.getStyle().setFontColor(TextFormatting.GREEN);
                        } else {
                            ArrayList<String> itemsReadOnly = new ArrayList<>(Arrays.asList(Main.wl_db.getString(Minecraft.getMinecraft().player.getUniqueID().toString()).split(",")));
                            if(itemsReadOnly.contains(((DynamXItemArmor<?>) item).getInfo().getFullName())) {
                                label.getStyle().setFontColor(TextFormatting.RED);
                                Main.network.sendToServer(new PacketSyncClothes(Minecraft.getMinecraft().player.getUniqueID().toString(), ((DynamXItemArmor<?>) item).getInfo().getFullName(), "remove", ((DynamXItemArmor<?>) item).getInfo().getObjArmor().getActiveTextureId(), ((DynamXItemArmor<?>) item).getInfo().getObjArmor().getActivePart().getSlotIndex()));

                            } else {
                                label.getStyle().setFontColor(TextFormatting.GREEN);
                                Main.network.sendToServer(new PacketSyncClothes(Minecraft.getMinecraft().player.getUniqueID().toString(), ((DynamXItemArmor<?>) item).getInfo().getFullName(), "add", ((DynamXItemArmor<?>) item).getInfo().getObjArmor().getActiveTextureId(), ((DynamXItemArmor<?>) item).getInfo().getObjArmor().getActivePart().getSlotIndex()));                            }
                        }
                    });
                    background.add(label);
                    l++;
                }
            }
        }

        Item item = Items.AIR;
        GuiLabel label = new GuiLabel("Tout déséquiper");
        label.setCssClass("item");
        label.allowLineBreak();
        label.getStyle().setOffsetY(l * 20);
        label.addClickListener((mouseX, mouseY, mouseButton) -> {
            Main.network.sendToServer(new PacketSyncClothes(Minecraft.getMinecraft().player.getUniqueID().toString(), "removeall", "removeall", (byte) 0, 0));
            ACsGuiApi.closeHudGui();
        });
        background.add(label);
        add(background);

    }

    @Override
    public List<ResourceLocation> getCssStyles() {
        return Collections.singletonList(new ResourceLocation(Main.MODID, "acsgui/clothes.css"));
    }
}
