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
public class CSSGuiGendarmerie extends GuiFrame {
    public CSSGuiGendarmerie() {
        super(new GuiScaler.Identity());
        GuiPanel home = new GuiPanel();
        home.setCssClass("home");
        home.setCssId("home");
        GuiPanel plainte = new GuiPanel();
        plainte.setCssClass("plainte");
        plainte.setCssId("plainte");
        GuiPanel amende = new GuiPanel();
        amende.setCssClass("amende");
        amende.setCssId("amende");
        GuiPanel taj = new GuiPanel();
        taj.setCssClass("taj");
        taj.setCssId("taj");

        plainte.addClickListener((x, y, bu) ->{




        });

        home.add(taj);
        home.add(amende);
        home.add(plainte);
        add(home);
    }

    public List<ResourceLocation> getCssStyles() {
        return Collections.singletonList(new ResourceLocation("dynamxmod:css/gendarmerie.css"));
    }
//text-align-vertical: bottom;

}
