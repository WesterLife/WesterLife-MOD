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
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiControls;
import net.minecraft.client.gui.GuiKeyBindingList;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.SPacketDisconnect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
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

        GuiPanel returnToGame = new GuiPanel();
        GuiPanel parameter = new GuiPanel();
        GuiPanel keyboard = new GuiPanel();

        GuiPanel discord = new GuiPanel();
        GuiPanel disconnect = new GuiPanel();
        GuiPanel site = new GuiPanel();

        returnToGame.setCssClass("returnToGame");
        parameter.setCssClass("parameter");
        keyboard.setCssClass("keyboard");
        discord.setCssClass("discord");
        disconnect.setCssClass("disconnect");
        site.setCssClass("site");

        home.add(returnToGame);
        home.add(parameter);
        home.add(keyboard);
        home.add(discord);
        home.add(disconnect);
        home.add(site);

        parameter.addClickListener((x, y, bu) -> {
            mc.gameSettings.saveOptions();
            mc.displayGuiScreen(new GuiOptions(this.getGuiScreen(), mc.gameSettings));

        });

        keyboard.addClickListener((x, y, bu) -> {
            //TODO: Keyboard GUI
            mc.displayGuiScreen(new GuiOptions(this.getGuiScreen(), mc.gameSettings));
        });

        site.addClickListener((x, y, bu) -> {
            try {
                Desktop.getDesktop().browse(new URI("https://westerlife.fr/"));
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
            System.out.println("https://westerlife.fr/");
        });

        discord.addClickListener((x,y,bu) -> {
            try {
                Desktop.getDesktop().browse(new URI("https://discord.gg/ZUsYjXE3"));
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
            System.out.println("https://discord.gg/ZUsYjXE3");
        });

        disconnect.addClickListener((x,y,bu) -> {
            //TODO: Disconnect

            System.out.println("Reste sur WesterLife, tu vas t'amuser !");
            mc.shutdown();

            //Euh en attendant, tu restes ou tu pars.
        });

        returnToGame.addClickListener((x, y, bu) -> {
                    mc.displayGuiScreen(null);
                });

        add(home);
    }

    public List<ResourceLocation> getCssStyles() {
        return Collections.singletonList(new ResourceLocation("dynamxmod:css/ingame.css"));
    }

}
