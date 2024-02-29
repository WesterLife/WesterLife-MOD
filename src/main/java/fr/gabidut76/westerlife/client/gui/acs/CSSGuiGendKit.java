package fr.gabidut76.westerlife.client.gui.acs;

import fr.aym.acsguis.component.button.GuiButton;
import fr.aym.acsguis.component.layout.GuiScaler;
import fr.aym.acsguis.component.list.GuiList;
import fr.aym.acsguis.component.panel.GuiFrame;
import fr.aym.acsguis.component.panel.GuiPanel;
import fr.aym.acsguis.component.textarea.GuiLabel;
import fr.gabidut76.westerlife.westercore.Main;
import fr.gabidut76.westerlife.common.Util;
import fr.gabidut76.westerlife.common.network.PacketSetKit;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CSSGuiGendKit extends GuiFrame {
    public CSSGuiGendKit() {
        super(new GuiScaler.Identity());
    }

    public CSSGuiGendKit(String arg) {
        super(new GuiScaler.Identity());


        GuiPanel background = new GuiPanel();
        background.setCssClass("bg");

        GuiLabel title = new GuiLabel("Attention, vous vous apprêtez à changer le kit gendarme : " + arg);
        title.setCssClass("title");
        background.add(title);

        GuiButton confirm = new GuiButton("Confirmer");
        confirm.setCssClass("confirm");
        confirm.addClickListener((mouseX, mouseY, mouseButton) -> {
            // cast nonnulllist to arraylist

            // inline convert nonnulllist to arraylist
            ArrayList<ItemStack> items = Minecraft.getMinecraft().player.inventoryContainer.getInventory().stream().collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
            Main.network.sendToServer(new PacketSetKit(arg, Util.itemStackListToString(items)));
            Minecraft.getMinecraft().displayGuiScreen(null);
        });
        confirm.getStyle().setOffsetY(20);
        background.add(confirm);


        ArrayList<String> elements = new ArrayList<>();


        for (ItemStack item : Minecraft.getMinecraft().player.inventoryContainer.getInventory()) {
            if (item != null) {
                if(item.getDisplayName().contains("Air")) continue;
                elements.add(item.getDisplayName());


            }

        }
        GuiList itemList = new GuiList(elements, 0,50,0,0);
        itemList.setCssClass("itemList");
        background.add(itemList);
        add(background);

    }

    @Override
    public List<ResourceLocation> getCssStyles() {
        return Collections.singletonList(new ResourceLocation(Main.MODID, "acsgui/gendkit.css"));
    }
}
