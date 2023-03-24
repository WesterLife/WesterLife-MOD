package fr.yan36.westerlife.client.gui.acs.pompier;

import fr.aym.acsguis.component.layout.GuiScaler;
import fr.aym.acsguis.component.panel.GuiFrame;
import fr.aym.acsguis.component.panel.GuiPanel;
import fr.aym.acsguis.component.textarea.GuiLabel;
import fr.aym.acsguis.component.textarea.GuiPasswordField;
import fr.aym.acsguis.component.textarea.GuiTextArea;
import fr.yan36.westerlife.Main;
import fr.yan36.westerlife.common.network.old.PacketLoginPompierServer;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import java.util.Collections;
import java.util.List;

public class CSSGuiPompierLogin extends GuiFrame {

    public static String errorText = "";

    public CSSGuiPompierLogin() {
        super(new GuiScaler.Identity());


        GuiPanel background = new GuiPanel();
        background.setCssClass("background");
        background.setCssId("background");

        GuiPanel leftbar = new GuiPanel();
        leftbar.setCssClass("leftbar");

        GuiPanel logo = new GuiPanel();
        logo.setCssClass("logo");

        GuiPanel title = new GuiPanel();
        title.setCssClass("title");

        GuiPanel loginButton = new GuiPanel();
        loginButton.setCssClass("loginbutton");

        GuiLabel error = (GuiLabel) new GuiLabel(0, 0, 0, 0, "").setCssClass("error");

        GuiTextArea login;
        GuiTextArea password;

        GuiLabel legend = (GuiLabel) new GuiLabel(0, 0, 600, 1080, "Bienvenue sur le portail intranet du Service Départemental d’Incendie et de Secours 977. Afin d’accéder à vos espaces veuillez saisir vos informations de connexions. Ce portail est réservé aux sapeurs pompiers. En cas de problème avec cette connexion contacter votre hiérarchie.").setCssClass("legend");

        login = (GuiTextArea) new GuiTextArea(0, 0, 0, 0).setMaxTextLength(30).setHintText("Identifiant").setCssClass("login");
        password = (GuiTextArea) new GuiPasswordField().setMaxTextLength(30).setHintText("Mot de passe").setCssClass("password");

        background.add(login);
        background.add(password);
        background.add(loginButton);
        background.add(title);

        leftbar.add(legend);

        leftbar.add(logo);

        background.add(leftbar);
        add(background);
        background.add(error);

        loginButton.addClickListener((x, y, bu) -> {
           if(!login.getText().isEmpty()&&!password.getText().isEmpty()){
               Main.network.sendToServer(new PacketLoginPompierServer(login.getText(), password.getText(), Minecraft.getMinecraft().player));
               error.setText(errorText);
           } else {
               error.setVisible(true);
               error.setText("Erreur, veuillez remplir tous les champs.");
           }
        });
    }

    @Override
    public List<ResourceLocation> getCssStyles() {
        return Collections.singletonList(new ResourceLocation("dynamxmod:css/pompier_login.css"));
    }
}
