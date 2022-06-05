package fr.yan36.westerlife.client.gui;

import fr.aym.acsguis.component.layout.GuiScaler;
import fr.aym.acsguis.component.panel.GuiFrame;
import fr.aym.acsguis.component.panel.GuiPanel;
import fr.aym.acsguis.component.textarea.GuiLabel;
import fr.aym.acsguis.component.textarea.GuiTextArea;
import fr.yan36.westerlife.Main;
import fr.yan36.westerlife.client.Client;
import fr.yan36.westerlife.client.Profil;
import fr.yan36.westerlife.common.items.WesterItem;
import fr.yan36.westerlife.common.network.PacketCreateIdentityServer;
import fr.yan36.westerlife.common.network.PacketDepoArgentServer;
import fr.yan36.westerlife.common.network.PacketRetirerArgentServer;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Collections;
import java.util.List;

@SideOnly(Side.CLIENT)
public class CSSGuiChangeSign extends GuiFrame {
    String status = "home";

    public CSSGuiChangeSign() {

        super(new GuiScaler.Identity());
        GuiPanel screen = new GuiPanel();
        screen.setCssClass("screen");
        screen.setCssId("screen");
        GuiTextArea Prenom,Nom,date,Sex;
        Nom = (GuiTextArea) new GuiTextArea(0, 0, 0, 0).setMaxTextLength(10).setHintText("Texte").setCssId("Nom");
        screen.add(Nom);

        GuiPanel confirm = new GuiPanel();
        confirm.setCssId("confirm").addClickListener((x, y, bu) -> {
            
        });
        screen.add(confirm);
        add(screen);
    }

    public List<ResourceLocation> getCssStyles() {
        return Collections.singletonList(new ResourceLocation("dynamxmod:css/atm.css"));
    }


}
