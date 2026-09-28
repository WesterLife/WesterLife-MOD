package fr.yan36.westerlife.client.gui.acs;

import fr.aym.acsguis.component.button.GuiButton;
import fr.aym.acsguis.component.layout.GuiScaler;
import fr.aym.acsguis.component.panel.GuiFrame;
import fr.aym.acsguis.component.panel.GuiPanel;
import fr.aym.acsguis.component.textarea.GuiLabel;
import fr.aym.acsguis.component.textarea.GuiTextField;
import fr.yan36.westerlife.Main;
import fr.yan36.westerlife.client.Client;
import fr.yan36.westerlife.common.network.PacketCreateCharacter;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

public class CSSCreateCharacter extends GuiFrame {
    public CSSCreateCharacter() {
        super(new GuiScaler.Identity());

        GuiPanel background = new GuiPanel();
        background.setCssClass("background");

        GuiTextField prenoms = new GuiTextField();
        prenoms.setHintText("Prénom(s)");
        prenoms.setCssClass("prenoms");
        background.add(prenoms);

        GuiTextField nom = new GuiTextField();
        nom.setHintText("Nom");
        nom.setCssClass("nom");
        background.add(nom);

        GuiTextField birthdate = new GuiTextField();
        birthdate.setHintText("Date de naissance (JJ/MM/AAAA)");
        birthdate.setCssClass("birthdate");
        background.add(birthdate);

        GuiTextField birthplace = new GuiTextField();
        birthplace.setHintText("Lieu de naissance");
        birthplace.setCssClass("birthplace");
        background.add(birthplace);
        // inline list

        GuiTextField nationalite = new GuiTextField();
        nationalite.setCssClass("nationalite");
        nationalite.setHintText("[F/A] (Français/Autre)");
        nationalite.setRegexPattern(Pattern.compile("^(?:F|A)$"));
        background.add(nationalite);

        GuiTextField sexe = new GuiTextField();
        sexe.setCssClass("sexe");
        sexe.setRegexPattern(Pattern.compile("^[MF]$"));
        sexe.setHintText("[M/F] (Homme / Femme)");
        background.add(sexe);

        GuiButton create = new GuiButton("Créer");
        create.setText("Créer");
        create.setCssClass("create");
        create.addClickListener((mouseX, mouseY, mouseButton) -> {
            if(nom.getText().isEmpty() || prenoms.getText().isEmpty() || birthdate.getText().isEmpty() || birthplace.getText().isEmpty() || nationalite.getText().isEmpty() || sexe.getText().isEmpty()) {
                Minecraft.getMinecraft().player.sendMessage(new TextComponentString(TextFormatting.RED + "Veuillez remplir tous les champs !"));
                return;
            }

            if(birthdate.getText().split("/").length != 3) {
                Minecraft.getMinecraft().player.sendMessage(new TextComponentString(TextFormatting.RED + "Veuillez entrer une date de naissance valide !"));
                return;
            }
            Main.network.sendToServer(new PacketCreateCharacter(Minecraft.getMinecraft().player,
                    nom.getText(),
                    prenoms.getText(),
                    birthdate.getText().replaceAll("/", "-"), // mdr
                    birthplace.getText(),
                    Objects.equals(nationalite.getText(), "F") ? "Française" : "Étrangère",
                    Objects.equals(sexe.getText(), "M") ? "HOMME" : "FEMME"
            ));
            Client.needToCreateCharacter = 0;
            Minecraft.getMinecraft().displayGuiScreen(null);
        });

        background.add(create);


        GuiLabel perf = new GuiLabel("Mode performance");
        perf.setCssClass("perf");
        add(perf);

        add(background);

    }

    public List<ResourceLocation> getCssStyles() {
        return Collections.singletonList(new ResourceLocation(Main.MODID, "acsgui/createchar.css"));
    }

    @Override
    public void guiClose() {
        if (Client.needToCreateCharacter == 2) {
            Client.needToCreateCharacter = 1;
        }
        super.guiClose();
    }
}
