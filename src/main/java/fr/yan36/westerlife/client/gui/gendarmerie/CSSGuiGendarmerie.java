package fr.yan36.westerlife.client.gui.gendarmerie;

import fr.aym.acsguis.component.button.GuiButton;
import fr.aym.acsguis.component.layout.GridLayout;
import fr.aym.acsguis.component.layout.GuiScaler;
import fr.aym.acsguis.component.panel.GuiFrame;
import fr.aym.acsguis.component.panel.GuiPanel;
import fr.aym.acsguis.component.panel.GuiScrollPane;
import fr.aym.acsguis.component.panel.GuiTabbedPane;
import fr.aym.acsguis.component.textarea.GuiLabel;
import fr.aym.acsguis.component.textarea.GuiTextArea;
import fr.aym.acsguis.component.textarea.GuiTextField;
import fr.yan36.westerlife.utils.list.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Collections;
import java.util.List;

@SideOnly(Side.CLIENT)
public class CSSGuiGendarmerie extends GuiFrame {
    public CSSGuiGendarmerie() {
        super(new GuiScaler.Identity());

        //List<String> plainteArrayList, List<String> userAccount, List<String> amendesArrayList, List<String> tajArrayList, List<String> avisDeRecherchesArrayList
//
//        for (String s : plainteArrayList) {
//            Plainte plainte = Plainte.fromString(s.replaceAll(" ", ""));
//
//        }
//
//
//        for (String s : userAccount) {
//            Gendarme accounts = Gendarme.fromString(s.replaceAll(" ", ""));
//
//        }
//
//
//        for (String s : amendesArrayList) {
//            Amendes amendes = Amendes.fromString(s.replaceAll(" ", ""));
//
//        }
//
//        for (String s : tajArrayList) {
//            TAJ taj = TAJ.fromString(s.replaceAll(" ", ""));
//
//        }
//
//        for (String s : avisDeRecherchesArrayList) {
//            AvisDeRecherche avisDeRecherche = AvisDeRecherche.fromString(s.replaceAll(" ", ""));
//
//        }
//
//
//        System.out.println(plainteArrayList);

        GuiPanel background = new GuiPanel();
        background.setCssClass("background");
        background.setCssId("background");

        GuiPanel leftbar = new GuiPanel();
        leftbar.setCssClass("leftbar");

        GuiTabbedPane menu = new GuiTabbedPane();
        menu.setCssClass("menu");

        leftbar.add(menu);

        // Plaintes

        GuiPanel guiScreenPlaintes = new GuiPanel();
        guiScreenPlaintes.setCssId("guiscreenplainte");
        guiScreenPlaintes.setCssClass("guiscreen");

        GuiPanel viewPlaintes = new GuiPanel();
        viewPlaintes.setCssId("viewplainte");
        viewPlaintes.setCssClass("leftpanel");

        // Create Plainte

        GuiPanel createPlainte = new GuiPanel();
        createPlainte.setCssId("createplainte");
        createPlainte.setCssClass("rightpanel");

        createPlainte.add(new GuiLabel("Créer une plainte").setCssId("createplainte-title").setCssCode("width: 100%; height: 5%;"));

        GuiTextArea plaignant = (GuiTextArea) new GuiTextArea().setHintText("Plaignant").setMaxTextLength(50);
        plaignant.setCssClass("plaignantplainte");
        plaignant.setEnabled(true);

        GuiTextArea miseEnCause = (GuiTextArea) new GuiTextArea().setHintText("Mis(e) en cause").setMaxTextLength(50);
        miseEnCause.setCssClass("miseencauseplainte");

        GuiTextArea description = (GuiTextArea) new GuiTextArea().setHintText("Déposition").setMaxTextLength(1024);
        description.setCssClass("descriptionplainte");

        GuiPanel createPlainteButton = new GuiPanel().add(new GuiLabel(0, 0, 0, 0,"Créer").setCssId("createplaintebutton").setCssCode("top: 50%; left: 50%; right: 50%; bottom: 50%;"));
        createPlainteButton.setCssClass("createplaintebutton");

        GuiPanel titlePlainte = new GuiPanel();
        GuiLabel titlePlainteLabel;
        titlePlainte.setCssClass("title");
        titlePlainte.add(titlePlainteLabel = (GuiLabel) new GuiLabel(0, 0, -1, 20, "Plaintes :").setCssId("titleplainte"));


        // Amendes

        GuiPanel guiScreenAmendes = new GuiPanel();
        guiScreenAmendes.setCssId("guiscreenamendes");
        guiScreenAmendes.setCssClass("guiscreen");

        GuiPanel viewAmendes = new GuiPanel();
        viewAmendes.setCssId("viewamendes");
        viewAmendes.setCssClass("leftpanel");

        GuiPanel createAmendes = new GuiPanel();
        createAmendes.setCssId("createamendes");
        createAmendes.setCssClass("rightpanel");

        GuiPanel titleAmendes = new GuiPanel();
        GuiLabel titleAmendesLabel;
        titleAmendes.setCssClass("title");

        titleAmendes.add(titleAmendesLabel = (GuiLabel) new GuiLabel(0, 0, 0, 0, "Amendes :").setCssId("titleamendes"));
        guiScreenAmendes.add(viewAmendes);
        guiScreenAmendes.add(createAmendes);
        guiScreenAmendes.add(titleAmendes);

        // TAJ equal Traitement des Antécédants Judiciaires

        GuiPanel guiScreenTaj = new GuiPanel();
        guiScreenTaj.setCssId("guiscreentaj");
        guiScreenTaj.setCssClass("guiscreen");

        GuiPanel viewTaj = new GuiPanel();
        viewTaj.setCssId("viewtaj");
        viewTaj.setCssClass("leftpanel");

        GuiPanel createTaj = new GuiPanel();
        createTaj.setCssId("createtaj");
        createTaj.setCssClass("rightpanel");

        GuiPanel titleTaj = new GuiPanel();
        GuiLabel titleTajLabel;
        titleTaj.setCssClass("title");
        titleTaj.add(titleTajLabel = (GuiLabel) new GuiLabel(0, 0, 0, 0, "Traitements des Antécédents Judiciaires :").setCssId("taj"));

        guiScreenTaj.add(viewTaj);
        guiScreenTaj.add(createTaj);
        guiScreenTaj.add(titleTaj);

        background.add(guiScreenTaj);

        // Avis de Recherches

        GuiPanel guiScreenAvisDeRecherches = new GuiPanel();
        guiScreenAvisDeRecherches.setCssId("guiscreenavisderecherches");
        guiScreenAvisDeRecherches.setCssClass("guiscreen");

        GuiPanel viewAvisDeRecherches = new GuiPanel();
        viewAvisDeRecherches.setCssId("viewavisderecherches");
        viewAvisDeRecherches.setCssClass("leftpanel");

        GuiPanel createAvisDeRecherches = new GuiPanel();
        createAvisDeRecherches.setCssId("createavisderecherches");
        createAvisDeRecherches.setCssClass("rightpanel");

        GuiPanel titleAvisDeRecherches = new GuiPanel();
        GuiLabel titleAvisDeRecherchesLabel;
        titleAvisDeRecherches.setCssClass("title");
        titleAvisDeRecherches.add(titleAvisDeRecherchesLabel = (GuiLabel) new GuiLabel(0, 0, 0, 0, "Avis de Recherches :").setCssId("titleavisderecherches"));

        guiScreenAvisDeRecherches.add(viewAvisDeRecherches);
        guiScreenAvisDeRecherches.add(createAvisDeRecherches);
        guiScreenAvisDeRecherches.add(titleAvisDeRecherches);

        // (A) Gestion

        GuiPanel guiScreenGestion = new GuiPanel();
        guiScreenGestion.setCssId("guiscreengestion");
        guiScreenGestion.setCssClass("guiscreen");

        GuiPanel viewGestion = new GuiPanel();
        viewGestion.setCssId("viewgestion");
        viewGestion.setCssClass("leftpanel");

        GuiPanel createGestion = new GuiPanel();
        createGestion.setCssId("creategestion");
        createGestion.setCssClass("rightpanel");

        GuiPanel titleGestion = new GuiPanel();
        GuiLabel titleGestionLabel;
        titleGestion.setCssClass("title");
        titleGestion.add(titleGestionLabel = (GuiLabel) new GuiLabel(0, 0, 0, 0, "Gestion :").setCssId("titlegestion"));

        guiScreenGestion.add(viewGestion);
        guiScreenGestion.add(createGestion);
        guiScreenGestion.add(titleGestion);

        // profil

        GuiPanel guiScreenProfil = new GuiPanel();
        guiScreenProfil.setCssId("guiscreenprofil");
        guiScreenProfil.setCssClass("guiscreen");

        menu.setLayout(new GridLayout(-1, 50, 1, GridLayout.GridDirection.HORIZONTAL, 1));

        menu.addTab("Plaintes", guiScreenPlaintes);
        menu.addTab("TAJ", guiScreenTaj);
        menu.addTab("Amendes", guiScreenAmendes);
        menu.addTab("Avis de Recherches", guiScreenAvisDeRecherches);
        menu.addTab("Gestion", guiScreenGestion);
        menu.getTabButton(4).setCssId("buttonGestion").setCssCode("color: #F43B3B;");

//        menu.getTabButton(0).setCssClass("button");
//        menu.getTabButton(1).setCssClass("button");
//        menu.getTabButton(2).setCssClass("button");
//        menu.getTabButton(3).setCssClass("button");
//        menu.getTabButton(4).setCssClass("button");

        // Button profil

        GuiPanel buttonProfil = new GuiPanel();
        buttonProfil.setCssClass("profilbutton");

        buttonProfil.addClickListener((x, y, z) -> {
            menu.getTabButton(0).setEnabled(false);
            menu.getTabButton(1).setEnabled(false);
            menu.getTabButton(2).setEnabled(false);
            menu.getTabButton(3).setEnabled(false);
            menu.getTabButton(4).setEnabled(false);
        });

        GuiPanel logo = new GuiPanel();
        logo.setCssClass("logo");

        leftbar.add(logo);

        background.add(leftbar);
        background.add(guiScreenPlaintes);
        background.add(guiScreenTaj);
        background.add(guiScreenAmendes);
        background.add(guiScreenAvisDeRecherches);
        background.add(guiScreenGestion);
        add(background);

        guiScreenPlaintes.add(viewPlaintes);
        guiScreenPlaintes.add(createPlainte);
        guiScreenPlaintes.add(titlePlainte);

        createPlainte.add(plaignant);
        createPlainte.add(miseEnCause);
        createPlainte.add(description);
        createPlainte.add(createPlainteButton);

        background.add(buttonProfil);
    }

    public List<ResourceLocation> getCssStyles() {
        return Collections.singletonList(new ResourceLocation("dynamxmod:css/gendarmerie.css"));
    }
//text-align-vertical: bottom;

}
