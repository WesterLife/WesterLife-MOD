package fr.yan36.westerlife.client.gui;

import fr.aym.acsguis.component.layout.GuiScaler;
import fr.aym.acsguis.component.panel.GuiFrame;
import fr.aym.acsguis.component.panel.GuiPanel;
import fr.aym.acsguis.component.textarea.GuiLabel;
import fr.aym.acsguis.component.textarea.GuiTextArea;
import fr.yan36.westerlife.Main;
import fr.yan36.westerlife.client.Client;
import fr.yan36.westerlife.common.items.WesterItem;
import fr.yan36.westerlife.common.network.PacketCreateIdentityServer;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.Collections;
import java.util.List;

public class CSSGuiCreateProfil extends GuiFrame {
    public CSSGuiCreateProfil( ) {
        super(new GuiScaler.AdjustToScreenSize(true,1,1));
        GuiPanel screen = new GuiPanel();
        screen.setCssClass("screen");
        screen.setCssId("screen");
        GuiTextArea Prenom,Nom,date,Sex;
        Nom = (GuiTextArea) new GuiTextArea(0, 0, 0, 0).setMaxTextLength(10).setHintText("Nom").setCssId("Nom");
        Prenom = (GuiTextArea) new GuiTextArea(0, 0, 0, 0).setMaxTextLength(10).setHintText("Prenom").setCssId("Prenom");
        date = (GuiTextArea) new GuiTextArea(0, 0, 0, 0).setMaxTextLength(10).setHintText("12/03/2003").setCssId("Date");
        Sex = (GuiTextArea) new GuiTextArea(0, 0, 0, 0).setMaxTextLength(5).setHintText("h/f").setCssId("Sex");
        screen.add(Nom);
        screen.add(Prenom);
        screen.add(date);
        screen.add(Sex);

        GuiPanel confirm = new GuiPanel();
        confirm.setCssId("confirm").addClickListener((x, y, bu) -> {
            if(!Nom.getText().isEmpty()&&!Prenom.getText().isEmpty()&&!date.getText().isEmpty()&&!Sex.getText().isEmpty()){
                Client.create=0;
                Minecraft.getMinecraft().displayGuiScreen(null);
                Main.network.sendToServer(new PacketCreateIdentityServer(Minecraft.getMinecraft().player, Nom.getText(),Prenom.getText(),Sex.getText(),date.getText()));
                Minecraft.getMinecraft().player.inventory.addItemStackToInventory(new ItemStack(WesterItem.CNI));
            }else {
                GuiLabel error = (GuiLabel) new GuiLabel(0,0,0,0,"Case vide !").setCssId("error");
                screen.add(error);
            }
        });
        screen.add(confirm);
        add(screen);
    }

    public List<ResourceLocation> getCssStyles() {
        return Collections.singletonList(new ResourceLocation("dynamxmod:css/createprofil.css"));
    }
}
