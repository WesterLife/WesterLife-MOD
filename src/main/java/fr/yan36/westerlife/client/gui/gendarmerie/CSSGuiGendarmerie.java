package fr.yan36.westerlife.client.gui.gendarmerie;

import fr.aym.acsguis.component.button.GuiButton;
import fr.aym.acsguis.component.layout.GridLayout;
import fr.aym.acsguis.component.layout.GuiScaler;
import fr.aym.acsguis.component.panel.GuiFrame;
import fr.aym.acsguis.component.panel.GuiPanel;
import fr.aym.acsguis.component.panel.GuiScrollPane;
import fr.aym.acsguis.component.panel.GuiTabbedPane;
import fr.aym.acsguis.component.textarea.GuiLabel;
import fr.aym.acsguis.component.textarea.GuiSearchField;
import fr.aym.acsguis.component.textarea.GuiTextArea;
import fr.aym.acsguis.component.textarea.GuiTextField;
import fr.yan36.westerlife.Main;
import fr.yan36.westerlife.common.network.PacketCreatePlainte;
import fr.yan36.westerlife.utils.list.*;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SideOnly(Side.CLIENT)
public class CSSGuiGendarmerie extends GuiFrame {
    public CSSGuiGendarmerie() {
        super(new GuiScaler.Identity());

        List<Plainte> plaintes = new ArrayList<>();
        plaintes.add(new Plainte(1, "VOTARD", "DUTEMPS", "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum."));
        plaintes.add(new Plainte(2, "PIERROT", "MACRON", "ike readable English. Many desktop publishing packages and web page editors now use Lorem Ipsum as their default model text, and a search fo"));
        plaintes.add(new Plainte(3, "VOTARD", "DUTEMPS", "or randomised words which don't look even slightly believable. If you are going to use a passage of Lorem Ipsum, you need to be sure there isn't anything embarrassing hidden in the middle of text. All the Lorem Ipsum generators on the Internet tend to repeat predefined chunks as necessary, making this the first true generator on the Internet. It uses a "));
        plaintes.add(new Plainte(4, "PIERROT", "MACRON", "chunk of Lorem Ipsum used since the 1500s is reproduced below for thos"));
        plaintes.add(new Plainte(5, "VOTARD", "DUTEMPS", "The standard chunk of Lorem Ipsum used since the 1500s is reproduced below for those interested. Sections 1.10.32 and 1.10.33 from "));
        plaintes.add(new Plainte(6, "PIERROT", "MACRON", "The standard chunk of Lorem Ipsum used since the 1500s is reproduced below for those interested. Sections 1.10.32 and 1.10.33 from "));
        plaintes.add(new Plainte(7, "VOTARD", "DUTEMPS", "The standard chunk of Lorem Ipsum used since the 1500s is reproduced below for those interested. Sections 1.10.32 and 1.10.33 from "));
        plaintes.add(new Plainte(8, "PIERROT", "MACRON", "The standard chunk of Lorem Ipsum used since the 1500s is reproduced below for those interested. Sections 1.10.32 and 1.10.33 from "));
        plaintes.add(new Plainte(9, "VOTARD", "DUTEMPS", "The standard chunk of Lorem Ipsum used since the 1500s is reproduced below for those interested. Sections 1.10.32 and 1.10.33 from "));
        plaintes.add(new Plainte(10, "PIERROT", "MACRON", "The standard chunk of Lorem Ipsum used since the 1500s is reproduced below for those interested. Sections 1.10.32 and 1.10.33 from "));

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


        // Plaintes

        GuiPanel guiScreenPlaintes = new GuiPanel();
        guiScreenPlaintes.setCssId("guiscreenplainte");
        guiScreenPlaintes.setCssClass("guiscreen");

        //View plaintes

        GuiPanel viewPlaintes = new GuiPanel();
        viewPlaintes.setCssId("viewplainte");
        viewPlaintes.setCssClass("leftpanel");

        GuiScrollPane scrollPlaintes = new GuiScrollPane();
        scrollPlaintes.setCssId("leftpanel");

        scrollPlaintes.getySlider().setCssId("slider");

        scrollPlaintes.setLayout(new GridLayout(-1, 300, 1, GridLayout.GridDirection.HORIZONTAL, 1));

        for (Plainte plainte : plaintes) {
            GuiPanel plainteIntro = new GuiPanel().add(new GuiLabel("Plainte n°" + plainte.getId() + " - Déposée par " + plainte.getPlaigant() + " - Mis(e) en cause : " + plainte.getContre()).setCssId("intro").setCssCode("height: 100%; width: 100%; padding-left: 10px; padding-top: 10px;"));

            GuiLabel deposition = new GuiLabel("");
            deposition.setMaxTextLength(48000);
            deposition.setCssId("deposition");
            deposition.setCssCode("color: #FFFFFF; height: 70%; width: 100%; padding-left: 10px; padding-top: 10px; top: 20%;");
            deposition.setText("Déposition : " + plainte.getDeposition());
            System.out.println(plainte.getDeposition());
            System.out.println(deposition.getText());
            deposition.allowLineBreak();
            GuiPanel plainteViewPanel = new GuiPanel().add(deposition);
            GuiPanel buttonPlainteDelete = (GuiPanel) new GuiPanel().add(new GuiLabel("Supprimer").setCssId("supprplainte").setCssCode("text-align: center; width: 100%; height: 100%;")).setCssId("deletebutton").setCssCode("top: 91%; padding-left: 4px; height: 8%; width: 40%; left: 55%;");
            plainteIntro.setCssClass("plainteintro");

            plainteViewPanel.setCssClass("plainte");
            plainteViewPanel.setCssId("plainte");
            plainteViewPanel.add(plainteIntro);
            plainteViewPanel.add(buttonPlainteDelete);
            scrollPlaintes.add(plainteViewPanel);
        }

        viewPlaintes.add(scrollPlaintes);

        // Create Plainte

        GuiPanel createPlainte = new GuiPanel();
        createPlainte.setCssId("createplainte");
        createPlainte.setCssClass("rightpanel");

        createPlainte.add(new GuiLabel("Créer une plainte").setCssId("createplainte-title").setCssCode("width: 100%; height: 6%; top: 1%;"));

        GuiTextArea plaignant = (GuiTextArea) new GuiTextArea().setHintText("Plaignant").setMaxTextLength(50);
        plaignant.setCssClass("plaignantplainte");
        plaignant.setEnabled(true);

        GuiTextArea miseEnCause = (GuiTextArea) new GuiTextArea().setHintText("Mis(e) en cause").setMaxTextLength(50);
        miseEnCause.setCssClass("miseencauseplainte");

        GuiTextArea description = (GuiTextArea) new GuiTextArea().setHintText("Déposition").setMaxTextLength(1024);
        description.setCssClass("descriptionplainte");

        GuiPanel createPlainteButton = (GuiPanel) new GuiPanel().add(new GuiLabel(0, 0, 0, 0, "Créer").setCssId("createplaintebutton"));
        createPlainteButton.setCssClass("createplaintebutton");

        GuiPanel titlePlainte = new GuiPanel();
        GuiLabel titlePlainteLabel;
        titlePlainte.setCssClass("title");
        titlePlainte.add(titlePlainteLabel = (GuiLabel) new GuiLabel(0, 0, -1, 20, "Plaintes :").setCssId("titleplainte"));

        GuiLabel error = (GuiLabel) new GuiLabel("").setCssId("error").setCssCode("color: red; bottom: 0%; height: 7%; width: 100%;");
        error.setVisible(false);
        error.allowLineBreak();


        createPlainte.add(error);
        createPlainteButton.addClickListener((x, y, bu) -> {
            if(!plaignant.getText().isEmpty() && !miseEnCause.getText().isEmpty() && !description.getText().isEmpty()) {
                Main.network.sendToServer(new PacketCreatePlainte(plaignant.getText(), miseEnCause.getText(), description.getText()));
                Minecraft.getMinecraft().player.sendMessage(new net.minecraft.util.text.TextComponentString("§6[§bGendarmerie§6] §aLa plainte de " + plaignant.getText() + " a bien été créée."));
                error.setVisible(false);
                plaignant.setText("");
                miseEnCause.setText("");
                description.setText("");
            } else {
                error.setVisible(true);
                error.setText("Remplissez tous les champs.");
            }
        });


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

        // Button profil

        GuiPanel buttonProfil = new GuiPanel();
        buttonProfil.setCssClass("profilbutton");

        buttonProfil.addClickListener((x, y, z) -> {

        });

        GuiPanel logo = new GuiPanel();
        logo.setCssClass("logo");

        leftbar.add(logo);

        guiScreenPlaintes.add(viewPlaintes);
        guiScreenPlaintes.add(createPlainte);
        guiScreenPlaintes.add(titlePlainte);

        createPlainte.add(plaignant);
        createPlainte.add(miseEnCause);
        createPlainte.add(description);
        createPlainte.add(createPlainteButton);


        menu.setLayout(new GridLayout(0, 50, 1, GridLayout.GridDirection.HORIZONTAL, 1));

        menu.addTab("Plaintes", guiScreenPlaintes);
        menu.addTab("TAJ", guiScreenTaj);
        menu.addTab("Amendes", guiScreenAmendes);
        menu.addTab("Avis de Recherches", guiScreenAvisDeRecherches);
        menu.addTab("Gestion", guiScreenGestion);
        menu.getTabButton(4).setCssId("buttonGestion").setCssCode("color: #F43B3B;");

        background.add(menu);
        //background.add(leftbar);
        add(background);
        background.add(buttonProfil);
    }

    public List<ResourceLocation> getCssStyles() {
        return Collections.singletonList(new ResourceLocation("dynamxmod:css/gendarmerie.css"));
    }
//text-align-vertical: bottom;

}
