package fr.yan36.westerlife.client.gui.gendarmerie;

import fr.aym.acsguis.component.layout.GuiScaler;
import fr.aym.acsguis.component.panel.GuiFrame;
import fr.aym.acsguis.component.panel.GuiPanel;
import fr.yan36.westerlife.server.Plainte;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Collections;
import java.util.List;

@SideOnly(Side.CLIENT)
public class CSSGuiGendarmerie extends GuiFrame {
    public CSSGuiGendarmerie(List<String> plainteArrayList, List<String> userAccount, List<String> amendesArrayList, List<String> tajArrayList, List<String> avisDeRecherchesArrayList) {
        super(new GuiScaler.Identity());

        // Get plainte ICI !

        for (String s : plainteArrayList) {
            Plainte plainte = Plainte.fromString(s.replaceAll(" ", ""));
        }

        System.out.println(plainteArrayList);

        GuiPanel background = new GuiPanel();
        background.setCssClass("background");
        background.setCssId("background");

        GuiPanel leftbar = new GuiPanel();
        leftbar.setCssClass("leftbar");

        GuiPanel logo = new GuiPanel();
        logo.setCssClass("logo");

        leftbar.add(logo);

        background.add(leftbar);
        add(background);
    }

    public List<ResourceLocation> getCssStyles() {
        return Collections.singletonList(new ResourceLocation("dynamxmod:css/gendarmerie.css"));
    }
//text-align-vertical: bottom;

}
