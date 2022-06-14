package fr.yan36.westerlife.client.gui.gendarmerie;

import fr.aym.acsguis.component.layout.GuiScaler;
import fr.aym.acsguis.component.panel.GuiFrame;
import fr.aym.acsguis.component.panel.GuiPanel;
import fr.aym.acsguis.component.textarea.GuiTextArea;
import fr.aym.acsguis.component.textarea.GuiTextField;
import fr.yan36.westerlife.Main;
import fr.yan36.westerlife.common.network.PacketCreatePlainte;
import fr.yan36.westerlife.server.Plainte;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SideOnly(Side.CLIENT)
public class CSSGuiGendarmerie extends GuiFrame {
    public CSSGuiGendarmerie(List<String> plainteArrayList, List<String> gendarmeUser, List<String> userAccount) {
        super(new GuiScaler.Identity());

        // Get plainte ICI !

        for (String s : plainteArrayList) {
            Plainte plainte = Plainte.fromString(s.replaceAll(" ", ""));
        }

        System.out.println(plainteArrayList);

        GuiPanel home = new GuiPanel();
        home.setCssClass("home");
        home.setCssId("home");
        GuiPanel plainte = new GuiPanel();
        GuiPanel plaintePanel = new GuiPanel();
        plainte.setCssClass("plainte");
        plainte.setCssId("plainte");
        GuiPanel amende = new GuiPanel();
        amende.setCssClass("amende");
        amende.setCssId("amende");
        GuiPanel taj = new GuiPanel();
        taj.setCssClass("taj");
        taj.setCssId("taj");
        GuiPanel createPlainte = new GuiPanel();
        createPlainte.setCssClass("createPlainte");
        createPlainte.setCssId("createPlainte");
        GuiPanel createPlainteGui = new GuiPanel();
        createPlainteGui.setCssClass("createPlainteGUI");
        createPlainteGui.setCssId("createPlainteGUI");

        GuiPanel createPlainteGuiimg = new GuiPanel();
        createPlainteGuiimg.setCssClass("createPlainteGUIimg");
        createPlainteGuiimg.setCssId("createPlainteGUIimg");

        GuiPanel plaintes = new GuiPanel();

        GuiTextArea Plaignant,Contre,Date, Deposition;
        Plaignant = (GuiTextArea) new GuiTextArea(0, 0, 0, 0).setMaxTextLength(30).setHintText("Plaignant").setCssId("Plaignant");
        Contre = (GuiTextArea) new GuiTextArea(0, 0, 0, 0).setMaxTextLength(30).setHintText("A l'encontre de").setCssId("Contre");
        Deposition = (GuiTextArea) new GuiTextField().setMaxTextLength(1024).setHintText("Déposition").setCssId("Deposition");

        plainte.addClickListener((x, y, bu) ->{

            createPlainte.setVisible(true);


        });

        createPlainte.addClickListener((x, y, bu) ->{
            if(createPlainte.isVisible()){
                createPlainteGui.setVisible(true);
                home.add(Plaignant);
                home.add(Contre);
                home.add(Deposition);
                home.add(createPlainteGuiimg);
                plainte.setVisible(false);
                amende.setVisible(false);
                taj.setVisible(false);
            }
        });

        createPlainteGuiimg.addClickListener((x, y, bu) ->{
           if(!Plaignant.getText().isEmpty()&&!Deposition.getText().isEmpty()&&!Contre.getText().isEmpty()){
               Minecraft.getMinecraft().displayGuiScreen(null);

               Main.network.sendToServer(new PacketCreatePlainte(Plaignant.getText().replaceAll("'", "''"), Contre.getText().replaceAll("'", "''"), Deposition.getText().replaceAll("'", "''")));
           } else {

           }
        });



        createPlainteGui.setVisible(false);
        createPlainte.setVisible(false);
        home.add(createPlainteGui);
        home.add(createPlainte);
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
