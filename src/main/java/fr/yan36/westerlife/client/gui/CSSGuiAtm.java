package fr.yan36.westerlife.client.gui;

import fr.aym.acsguis.component.layout.GuiScaler;
import fr.aym.acsguis.component.panel.GuiFrame;
import fr.aym.acsguis.component.panel.GuiPanel;
import fr.aym.acsguis.component.textarea.GuiLabel;
import fr.aym.acsguis.component.textarea.GuiTextArea;
import fr.yan36.westerlife.Main;
import fr.yan36.westerlife.client.Profil;
import fr.yan36.westerlife.common.items.WesterItem;
import fr.yan36.westerlife.common.network.PacketDepoArgentServer;
import fr.yan36.westerlife.common.network.PacketRetirerArgentServer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Collections;
import java.util.List;

@SideOnly(Side.CLIENT)
public class CSSGuiAtm extends GuiFrame {
    String status = "home";

    public void clavier(int number) {
        //TO DO
    }

    public CSSGuiAtm() {

        super(new GuiScaler.Identity());

        GuiPanel back = new GuiPanel();
        back.setCssClass("back");

        GuiPanel clavierAera = new GuiPanel();
        clavierAera.setCssClass("clavier-area");

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

        /** 8 buttons */

        GuiPanel button1right = new GuiPanel();
        GuiPanel button2right = new GuiPanel();
        GuiPanel button3right = new GuiPanel();
        GuiPanel button4right = new GuiPanel();

        GuiPanel button1left = new GuiPanel();
        GuiPanel button2left = new GuiPanel();
        GuiPanel button3left = new GuiPanel();
        GuiPanel button4left = new GuiPanel();

        button1right.setCssClass("button1right");
        button2right.setCssClass("button2right");
        button3right.setCssClass("button3right");
        button4right.setCssClass("button4right");

        button1left.setCssClass("button1left");
        button2left.setCssClass("button2left");
        button3left.setCssClass("button3left");
        button4left.setCssClass("button4left");

        /**
         * Home Page
         */

        GuiPanel screenHome = new GuiPanel();
        screenHome.setCssClass("screen");
        screenHome.setCssId("screen-home");

        GuiPanel screenHomeTitle = new GuiPanel();
        screenHomeTitle.setCssClass("screen-home-title");

        /**
         * Code Login Page
         */

        GuiPanel screenCodeLogin = new GuiPanel();
        screenCodeLogin.setCssClass("screen");
        screenCodeLogin.setCssId("screen-login");

        GuiPanel screenCodeLoginTextAera = new GuiPanel();
        screenCodeLoginTextAera.setCssClass("screen-login-text-area");

        /**
         * Dépôt
         */

        GuiPanel screenDepot = new GuiPanel();
        screenDepot.setCssClass("screen");
        screenDepot.setCssId("screen-depot");

        /**
         * Retrait
         */

        GuiPanel screenRetrait = new GuiPanel();
        screenRetrait.setCssClass("screen");
        screenRetrait.setCssId("screen-retrait");

        /**
         * Transaction
         */

        GuiPanel screenTransaction = new GuiPanel();
        screenTransaction.setCssClass("screen");
        screenTransaction.setCssId("screen-transaction");

        GuiPanel RIBaera = new GuiPanel();
        RIBaera.setCssClass("rib-area");

        GuiPanel Soldeaera = new GuiPanel();
        Soldeaera.setCssClass("solde-area");

        /**
         * Mon profil
         */

        GuiPanel screenProfil = new GuiPanel();
        screenProfil.setCssClass("screen");
        screenProfil.setCssId("screen-profil");

        GuiPanel infoProfil = new GuiPanel();
        infoProfil.setCssClass("info-profil");

        /**
         * Modif code
         */

        GuiPanel screenProfilcode = new GuiPanel();
        screenProfilcode.setCssClass("screen");
        screenProfilcode.setCssId("screen-profil-code");

        GuiPanel newCode = new GuiPanel();
        newCode.setCssClass("new-code");

    }

    public List<ResourceLocation> getCssStyles() {
        return Collections.singletonList(new ResourceLocation("dynamxmod:css/atm.css"));
    }


}
