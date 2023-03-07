package fr.yan36.westerlife.client.gui;

import fr.aym.acsguis.component.layout.GuiScaler;
import fr.aym.acsguis.component.panel.GuiFrame;
import fr.aym.acsguis.component.panel.GuiPanel;
import fr.aym.acsguis.component.textarea.GuiLabel;
import fr.aym.acsguis.component.textarea.GuiPasswordField;
import fr.aym.acsguis.component.textarea.GuiTextArea;
import fr.yan36.westerlife.Main;
import fr.yan36.westerlife.client.Profil;
import fr.yan36.westerlife.common.init.ItemInit;
import fr.yan36.westerlife.common.network.PacketATMTransaction;
import fr.yan36.westerlife.common.network.PacketChangerCodeServer;
import fr.yan36.westerlife.common.network.PacketDepoArgentServer;
import fr.yan36.westerlife.common.network.PacketRetirerArgentServer;
import fr.yan36.westerlife.common.registry.SoundsHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

@SideOnly(Side.CLIENT)
public class CSSGuiAtm extends GuiFrame {
    String status = "code";

    public CSSGuiAtm(String code) {

        super(new GuiScaler.Identity());

        GuiPanel back = new GuiPanel();
        back.setCssClass("back");

        add(back);

        /** Clavier */

        GuiPanel oneButton = new GuiPanel();
        oneButton.setCssClass("one-button");

        GuiPanel twoButton = new GuiPanel();
        twoButton.setCssClass("two-button");

        GuiPanel threeButton = new GuiPanel();
        threeButton.setCssClass("three-button");

        GuiPanel fourButton = new GuiPanel();
        fourButton.setCssClass("four-button");

        GuiPanel fiveButton = new GuiPanel();
        fiveButton.setCssClass("five-button");

        GuiPanel sixButton = new GuiPanel();
        sixButton.setCssClass("six-button");

        GuiPanel sevenButton = new GuiPanel();
        sevenButton.setCssClass("seven-button");

        GuiPanel eightButton = new GuiPanel();
        eightButton.setCssClass("eight-button");

        GuiPanel nineButton = new GuiPanel();
        nineButton.setCssClass("nine-button");

        GuiPanel zeroButton = new GuiPanel();
        zeroButton.setCssClass("zero-button");

        GuiPanel enterButton = new GuiPanel();
        enterButton.setCssClass("enter-button");

        GuiPanel clearButton = new GuiPanel();
        clearButton.setCssClass("clear-button");

        GuiPanel cancelButton = new GuiPanel();
        cancelButton.setCssClass("cancel-button");

        back.add(oneButton);
        back.add(twoButton);
        back.add(threeButton);
        back.add(fourButton);
        back.add(fiveButton);
        back.add(sixButton);
        back.add(sevenButton);
        back.add(eightButton);
        back.add(nineButton);
        back.add(zeroButton);
        back.add(enterButton);
        back.add(clearButton);
        back.add(cancelButton);

        /** 8 buttons */

        GuiPanel button1right = new GuiPanel();
        GuiPanel button2right = new GuiPanel();
        GuiPanel button3right = new GuiPanel();
        GuiPanel button4right = new GuiPanel();

        GuiPanel button1left = new GuiPanel();
        GuiPanel button2left = new GuiPanel();
        GuiPanel button3left = new GuiPanel();
        GuiPanel button4left = new GuiPanel();

        button1right.setCssId("button1right");
        button2right.setCssId("button2right");
        button3right.setCssId("button3right");
        button4right.setCssId("button4right");

        button1left.setCssId("button1left");
        button2left.setCssId("button2left");
        button3left.setCssId("button3left");
        button4left.setCssId("button4left");

        back.add(button1right);
        back.add(button2right);
        back.add(button3right);
        back.add(button4right);

        back.add(button1left);
        back.add(button2left);
        back.add(button3left);
        back.add(button4left);

        button1left.setVisible(true);
        button2left.setVisible(true);
        button3left.setVisible(true);
        button4left.setVisible(true);
        button1right.setVisible(true);
        button2right.setVisible(true);
        button3right.setVisible(true);
        button4right.setVisible(true);

        /**
         * Home Page
         */

        GuiPanel screenHome = new GuiPanel();
        screenHome.setCssClass("screen");
        screenHome.setCssId("screen-home");

        back.add(screenHome);
        screenHome.setVisible(false);

        GuiPanel screenHomeTitle = new GuiPanel();
        GuiLabel screenHomeTitleLabel;
        screenHomeTitle.add(screenHomeTitleLabel = new GuiLabel(0, 0, 0, 80,"Bonjour " + Profil.getPrenom() + ", bienvenue sur votre compte. Votre solde est de " + Profil.getBank() + "€.")).setCssId("home-title").setCssClass("text-home");
        screenHomeTitle.setCssClass("text-home");
        screenHome.add(screenHomeTitle);

        /**
         * Code Login Page
         */

        GuiPanel screenCodeLogin = new GuiPanel();
        screenCodeLogin.setCssClass("screen");
        screenCodeLogin.setCssId("screen-login");

        back.add(screenCodeLogin);
        screenCodeLogin.setVisible(true);

        GuiPasswordField codeLogin;
        codeLogin = (GuiPasswordField) new GuiPasswordField().setHintText("****").setMaxTextLength(4).setCssClass("code-login");
        codeLogin.setCssClass("code-login");

        screenCodeLogin.add(codeLogin);
        codeLogin.setVisible(true);

        /**
         * Dépôt
         */

        GuiPanel screenDepot = new GuiPanel();
        screenDepot.setCssClass("screen");
        screenDepot.setCssId("screen-depot");

        back.add(screenDepot);
        screenDepot.setVisible(false);

        /**
         * Retrait
         */

        GuiPanel screenRetrait = new GuiPanel();
        screenRetrait.setCssClass("screen");
        screenRetrait.setCssId("screen-retrait");

        back.add(screenRetrait);
        screenRetrait.setVisible(false);

        /**
         * Transaction
         */

        GuiPanel screenTransaction = new GuiPanel();
        screenTransaction.setCssClass("screen");
        screenTransaction.setCssId("screen-transaction");

        back.add(screenTransaction);
        screenTransaction.setVisible(false);

        GuiTextArea rib, montant;
        rib = (GuiTextArea) new GuiTextArea().setHintText("RIB destinataire").setMaxTextLength(10);
        rib.setCssClass("transac-rib");

        screenTransaction.add(rib);
        rib.setVisible(false);

        montant = (GuiTextArea) new GuiTextArea().setHintText("Montant").setMaxTextLength(6).setRegexPattern(Pattern.compile(".*[0-9].*"));
        montant.setCssClass("transac-montant");

        screenTransaction.add(montant);
        montant.setVisible(false);

        GuiPanel screenProfil = new GuiPanel();
        screenProfil.setCssClass("screen");
        screenProfil.setCssId("screen-profil");

        back.add(screenProfil);
        screenProfil.setVisible(false);

        GuiPanel infoProfil = new GuiPanel();
        infoProfil.add(new GuiLabel(0, 10, 0, 0, "Nom : " + Profil.getNom()).setCssId("text-profil"));
        infoProfil.add(new GuiLabel(0, 30, 0, 0, "Prenom : " + Profil.getPrenom()).setCssId("text-profil"));
        infoProfil.add(new GuiLabel(0, 50, 0, 0, "RIB  : " + Profil.getRib()).setCssId("text-profil"));
        infoProfil.add(new GuiLabel(0, 70, 0, 0, "Solde : " + Profil.getBank() + "€").setCssId("text-profil"));
        infoProfil.setCssClass("profil-info");

        screenProfil.add(infoProfil);
        infoProfil.setVisible(false);

        GuiPanel screenProfilcode = new GuiPanel();
        screenProfilcode.setCssClass("screen");
        screenProfilcode.setCssId("screen-profilcode");

        GuiTextArea newCode;
        newCode = (GuiTextArea) new GuiTextArea().setHintText("****").setText(code).setMaxTextLength(4).setRegexPattern(Pattern.compile(".*[0-9].*"));
        newCode.setCssClass("new-code");
        screenProfilcode.add(newCode);
        screenProfilcode.setVisible(false);
        back.add(screenProfilcode);

        oneButton.addClickListener((x, y, bu) -> {
            if(status == "code") {
                codeLogin.setText(codeLogin.getText() + "1");
                Minecraft.getMinecraft().player.playSound(SoundsHandler.ATMSOUNDBIP, 0.5f, 1);
            }
        });

        twoButton.addClickListener((x, y, bu) -> {
            if(status == "code") {
                codeLogin.setText(codeLogin.getText() + "2");
                Minecraft.getMinecraft().player.playSound(SoundsHandler.ATMSOUNDBIP, 0.5f, 1);
            }
        });

        threeButton.addClickListener((x, y, bu) -> {
            if(status == "code") {
                codeLogin.setText(codeLogin.getText() + "3");
                Minecraft.getMinecraft().player.playSound(SoundsHandler.ATMSOUNDBIP, 0.5f, 1);
            }
        });

        fourButton.addClickListener((x, y, bu) -> {
            if(status == "code") {
                codeLogin.setText(codeLogin.getText() + "4");
                Minecraft.getMinecraft().player.playSound(SoundsHandler.ATMSOUNDBIP, 0.5f, 1);
            }
        });

        fiveButton.addClickListener((x, y, bu) -> {
            if(status == "code") {
                codeLogin.setText(codeLogin.getText() + "5");
                Minecraft.getMinecraft().player.playSound(SoundsHandler.ATMSOUNDBIP, 0.5f, 1);
            }
        });

        sixButton.addClickListener((x, y, bu) -> {
            if(status == "code") {
                codeLogin.setText(codeLogin.getText() + "6");
                Minecraft.getMinecraft().player.playSound(SoundsHandler.ATMSOUNDBIP, 0.5f, 1);
            }
        });

        sevenButton.addClickListener((x, y, bu) -> {
            if(status == "code") {
                codeLogin.setText(codeLogin.getText() + "7");
                Minecraft.getMinecraft().player.playSound(SoundsHandler.ATMSOUNDBIP, 0.5f, 1);
            }
        });

        eightButton.addClickListener((x, y, bu) -> {
            if(status == "code") {
                codeLogin.setText(codeLogin.getText() + "8");
                Minecraft.getMinecraft().player.playSound(SoundsHandler.ATMSOUNDBIP, 0.5f, 1);
            }
        });

        nineButton.addClickListener((x, y, bu) -> {
            if(status == "code") {
                codeLogin.setText(codeLogin.getText() + "9");
                Minecraft.getMinecraft().player.playSound(SoundsHandler.ATMSOUNDBIP, 0.5f, 1);
            }
        });

        zeroButton.addClickListener((x, y, bu) -> {
            if(status == "code") {
                codeLogin.setText(codeLogin.getText() + "0");
                Minecraft.getMinecraft().player.playSound(SoundsHandler.ATMSOUNDBIP, 0.5f, 1);
            }
        });

        enterButton.addClickListener((x, y, bu) -> {
            if(status == "code") {
                if (codeLogin.getText().length() == 4) {
                    if (codeLogin.getText().equals(code)) {
                        screenCodeLogin.setVisible(false);
                        screenHome.setVisible(true);
                        status = "home";
                        Minecraft.getMinecraft().player.playSound(SoundsHandler.ATMSOUNDBIP, 0.5f, 1);
                        Minecraft.getMinecraft().player.playSound(SoundsHandler.ATMSOUNDBIP, 0.5f, 1);
                    } else {
                        codeLogin.setText("");
                        Minecraft.getMinecraft().player.sendMessage(new TextComponentString("§cCode incorrect. Le code par défaut est 0000. Si vous l'avez modifié et ne vous souvenez plus de ce dernier contactez votre banque."));
                        Minecraft.getMinecraft().player.playSound(SoundsHandler.BIP, 0.8f, 1);
                    }
                }
            }
        });

        cancelButton.addClickListener((x, y, bu) -> {
            if(status == "code") {

                Minecraft.getMinecraft().player.playSound(SoundsHandler.ATMSOUNDBIP, 0.5f, 1);
                codeLogin.setText("");
            }

        });

        clearButton.addClickListener((x, y, bu) -> {
            Minecraft.getMinecraft().player.playSound(SoundsHandler.ATMSOUNDBIP, 0.5f, 1);
            if(status == "code" && codeLogin.getText().length() > 0) {
                codeLogin.setText(codeLogin.getText().substring(0, codeLogin.getText().length() - 1));
            }
        });

        button1right.addClickListener((x, y, bu) -> {
            Minecraft.getMinecraft().player.playSound(SoundsHandler.ATMSOUNDBIP, 0.5f, 1);
            switch (status){
                case "depot":
                    Main.network.sendToServer(new PacketDepoArgentServer(Minecraft.getMinecraft().player, 10, new ItemStack(ItemInit.DIX_EUROS)));
                    break;
                case "retrait":
                    Main.network.sendToServer(new PacketRetirerArgentServer(Minecraft.getMinecraft().player, 10));
                    break;
                default:
                    break;
            }
        });

        button1left.addClickListener((x, y, bu) -> {
            Minecraft.getMinecraft().player.playSound(SoundsHandler.ATMSOUNDBIP, 0.5f, 1);
            switch (status){
                case "depot":
                    Main.network.sendToServer(new PacketDepoArgentServer(Minecraft.getMinecraft().player, 5, new ItemStack(ItemInit.CINQ_EUROS)));
                    break;
                case "retrait":
                    Main.network.sendToServer(new PacketRetirerArgentServer(Minecraft.getMinecraft().player, 5));
                    break;
                default:
                    break;
            }
        });

        button2right.addClickListener((x, y, bu) -> {
            Minecraft.getMinecraft().player.playSound(SoundsHandler.ATMSOUNDBIP, 0.5f, 1);
            switch (status){
                case "depot":
                    Main.network.sendToServer(new PacketDepoArgentServer(Minecraft.getMinecraft().player, 50, new ItemStack(ItemInit.CINQUANTE_EUROS)));
                    break;
                case "retrait":
                    Main.network.sendToServer(new PacketRetirerArgentServer(Minecraft.getMinecraft().player, 50));
                    break;
                case "home":
                    Minecraft.getMinecraft().displayGuiScreen(null);
                    break;
                default:
                    break;
            }
        });

        button2left.addClickListener((x, y, bu) -> {
            Minecraft.getMinecraft().player.playSound(SoundsHandler.ATMSOUNDBIP, 0.5f, 1);
            switch (status){
                case "depot":
                    Main.network.sendToServer(new PacketDepoArgentServer(Minecraft.getMinecraft().player, 20, new ItemStack(ItemInit.VINGT_EUROS)));
                    break;
                case "retrait":
                    Main.network.sendToServer(new PacketRetirerArgentServer(Minecraft.getMinecraft().player, 20));
                    break;
                default:
                    break;
            }
        });

        button3right.addClickListener((x, y, bu) -> {
            Minecraft.getMinecraft().player.playSound(SoundsHandler.ATMSOUNDBIP, 0.5f, 1);
            switch (status){
                case "home":
                    screenHome.setVisible(false);
                    screenTransaction.setVisible(true);
                    montant.setVisible(true);
                    rib.setVisible(true);
                    status = "transaction";
                    break;
                case "depot":
                    Main.network.sendToServer(new PacketDepoArgentServer(Minecraft.getMinecraft().player, 200, new ItemStack(ItemInit.DEUX_CENTS_EUROS)));
                    break;
                case "retrait":
                    Main.network.sendToServer(new PacketRetirerArgentServer(Minecraft.getMinecraft().player, 200));
                    break;
                default:
                    break;
            }
        });

        button3left.addClickListener((x, y, bu) -> {
            Minecraft.getMinecraft().player.playSound(SoundsHandler.ATMSOUNDBIP, 0.5f, 1);
            switch (status){
                case "home":
                    screenProfil.setVisible(true);
                    screenHome.setVisible(false);
                    infoProfil.setVisible(true);
                    status = "profil";
                    break;
                case "depot":
                    Main.network.sendToServer(new PacketDepoArgentServer(Minecraft.getMinecraft().player, 100, new ItemStack(ItemInit.CENT_EUROS)));
                    break;
                case "retrait":
                    Main.network.sendToServer(new PacketRetirerArgentServer(Minecraft.getMinecraft().player, 100));
                    break;
                default:
                    break;
            }
        });

        button4right.addClickListener((x, y, bu) -> {
            Minecraft.getMinecraft().player.playSound(SoundsHandler.ATMSOUNDBIP, 0.5f, 1);
            switch (status){
                case "home":
                    screenHome.setVisible(false);
                    screenRetrait.setVisible(true);
                    status = "retrait";
                    break;
                case "retrait":
                    Main.network.sendToServer(new PacketRetirerArgentServer(Minecraft.getMinecraft().player, 500));
                    break;
                case "depot":
                    Main.network.sendToServer(new PacketDepoArgentServer(Minecraft.getMinecraft().player, 500, new ItemStack(ItemInit.CINQ_CENTS_EUROS)));
                    break;
                case "transaction":

                    /**
                     * Fonctionnement RIB :
                     * Type de RIB :
                     * Commencant par :
                     *   - 01 : Compte Joueur
                     *   - 02 : Compte Entreprise
                     */

                    if(!montant.getText().isEmpty() && !rib.getText().isEmpty()){
                        Main.network.sendToServer(new PacketATMTransaction(rib.getText(), montant.getText()));
                        System.out.println("Transaction envoyée");
                    }
                    break;
                case "profil":
                    screenProfil.setVisible(false);
                    screenProfilcode.setVisible(true);
                    status = "profil-code";
                    break;
                case "profil-code":
                    if(newCode.getText().length() == 4) {
                        Main.network.sendToServer(new PacketChangerCodeServer(Minecraft.getMinecraft().player, newCode.getText()));
                        screenProfil.setVisible(true);
                        screenProfilcode.setVisible(false);
                        infoProfil.setVisible(true);
                        status = "profil";
                    }
                        break;
                default:
                    break;
            }
        });

        button4left.addClickListener((x, y, bu) -> {
            Minecraft.getMinecraft().player.playSound(SoundsHandler.ATMSOUNDBIP, 0.5f, 1);
            switch (status){
                case "home":
                    screenHome.setVisible(false);
                    screenDepot.setVisible(true);
                    status = "depot";
                    break;
                case "depot":
                    screenDepot.setVisible(false);
                    screenHome.setVisible(true);
                    screenHomeTitleLabel.setText("Bonjour " + Profil.getPrenom() + ", bienvenue sur votre compte. Votre solde est de " + Profil.getBank() + "€.");
                    status = "home";
                    break;
                case "retrait":
                    screenRetrait.setVisible(false);
                    screenHome.setVisible(true);
                    screenHomeTitleLabel.setText("Bonjour " + Profil.getPrenom() + ", bienvenue sur votre compte. Votre solde est de " + Profil.getBank() + "€.");
                    status = "home";
                    break;
                case "transaction":
                    screenTransaction.setVisible(false);
                    screenHome.setVisible(true);
                    screenHomeTitleLabel.setText("Bonjour " + Profil.getPrenom() + ", bienvenue sur votre compte. Votre solde est de " + Profil.getBank() + "€.");
                    status = "home";
                    break;
                case "profil":
                    screenProfil.setVisible(false);
                    screenHome.setVisible(true);
                    screenHomeTitleLabel.setText("Bonjour " + Profil.getPrenom() + ", bienvenue sur votre compte. Votre solde est de " + Profil.getBank() + "€.");
                    status = "home";
                    break;
                case "profil-code":
                    screenProfilcode.setVisible(false);
                    screenProfil.setVisible(true);
                    status = "profil";
                    break;
                case "code":
                    Minecraft.getMinecraft().displayGuiScreen(null);
                    break;
                default:
                    break;
            }
        });
    }
    public List<ResourceLocation> getCssStyles() {
        return Collections.singletonList(new ResourceLocation("dynamxmod:css/atm.css"));
    }
}