package fr.yan36.westerlife.client.gui;

import fr.aym.acsguis.component.layout.GuiScaler;
import fr.aym.acsguis.component.panel.GuiFrame;
import fr.aym.acsguis.component.panel.GuiPanel;
import fr.aym.acsguis.component.style.ComponentStyleManager;
import fr.aym.acsguis.component.textarea.GuiLabel;
import fr.aym.acsguis.component.textarea.GuiTextArea;
import fr.aym.acsguis.event.ComponentMouseEvent;
import fr.aym.acsguis.event.listeners.IFocusListener;
import fr.yan36.westerlife.client.Profil;
import ibxm.Player;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

@SideOnly(Side.CLIENT)
public class CSSGuiIngameMenu extends GuiFrame {
    public CSSGuiIngameMenu() {
        super(new GuiScaler.Identity());
        GuiPanel home = new GuiPanel();
        home.setCssClass("home");
        home.setCssId("home");
        GuiPanel returntogame = new GuiPanel();
        returntogame.setCssClass("rtg");
        returntogame.setCssId("rtg");
        returntogame.add(new GuiLabel(0, 0, 100, 20, "Retour au jeu"));
        GuiPanel parameter = new GuiPanel();
        parameter.setCssClass("parameter");
        parameter.setCssId("parameter");
        parameter.add(new GuiLabel(0,0,100,20, "Paramètres"));
        GuiPanel disconnect = new GuiPanel();
        disconnect.setCssClass("disconnect");
        disconnect.setCssId("disconnect");
        disconnect.add(new GuiLabel(0,0,100,20, "Se Déconnecter"));

        returntogame.addClickListener((x, y, bu) -> {

            Minecraft.getMinecraft().displayGuiScreen(null);

        });
        disconnect.addClickListener((x, y, bu) -> {

            Minecraft.stopIntegratedServer();

        });
        parameter.addClickListener((x, y, bu) -> {

            mc.gameSettings.saveOptions();
            mc.displayGuiScreen(new GuiOptions(this.getGuiScreen(), mc.gameSettings));

        });


        home.add(returntogame);
        home.add(parameter);
        home.add(disconnect);
        add(home);
    }

    public List<ResourceLocation> getCssStyles() {
        return Collections.singletonList(new ResourceLocation("dynamxmod:css/ingame.css"));
    }
//text-align-vertical: bottom;

}
