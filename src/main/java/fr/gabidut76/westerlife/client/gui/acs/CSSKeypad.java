package fr.gabidut76.westerlife.client.gui.acs;

import fr.aym.acsguis.component.layout.GuiScaler;
import fr.aym.acsguis.component.panel.GuiFrame;
import fr.aym.acsguis.component.panel.GuiPanel;
import fr.aym.acsguis.component.textarea.GuiTextArea;
import net.minecraft.util.ResourceLocation;

import java.util.Collections;
import java.util.List;

public class CSSKeypad extends GuiFrame {
    public CSSKeypad(String code) {
        super(new GuiScaler.Identity());

        GuiPanel background = new GuiPanel();
        background.setCssClass("background");

        GuiTextArea saisieText;
        saisieText = (GuiTextArea) new GuiTextArea(0, 0, 0, 0).setCssClass("saisie");
        saisieText.setMaxTextLength(4);

        GuiPanel one = new GuiPanel();
        one.setCssClass("one");

        GuiPanel two = new GuiPanel();
        two.setCssClass("two");

        GuiPanel three = new GuiPanel();
        three.setCssClass("three");

        GuiPanel four = new GuiPanel();
        four.setCssClass("four");

        GuiPanel five = new GuiPanel();
        five.setCssClass("five");

        GuiPanel six = new GuiPanel();
        six.setCssClass("six");

        GuiPanel seven = new GuiPanel();
        seven.setCssClass("seven");

        GuiPanel eight = new GuiPanel();
        eight.setCssClass("eight");

        GuiPanel nine = new GuiPanel();
        nine.setCssClass("nine");

        GuiPanel zero = new GuiPanel();
        zero.setCssClass("zero");

        GuiPanel clear = new GuiPanel();
        clear.setCssClass("clear");

        GuiPanel enter = new GuiPanel();
        enter.setCssClass("enter");

        background.add(saisieText);
        background.add(one);
        background.add(two);
        background.add(three);
        background.add(four);
        background.add(five);
        background.add(six);
        background.add(seven);
        background.add(eight);
        background.add(nine);
        background.add(zero);
        background.add(clear);
        background.add(enter);

        one.addClickListener((x, y, bu) -> {
            saisieText.setText(saisieText.getText() + "1");
        });

        two.addClickListener((x, y, bu) -> {
            saisieText.setText(saisieText.getText() + "2");
        });

        three.addClickListener((x, y, bu) -> {
            saisieText.setText(saisieText.getText() + "3");
        });

        four.addClickListener((x, y, bu) -> {
            saisieText.setText(saisieText.getText() + "4");
        });

        five.addClickListener((x, y, bu) -> {
            saisieText.setText(saisieText.getText() + "5");
        });

        six.addClickListener((x, y, bu) -> {
            saisieText.setText(saisieText.getText() + "6");
        });

        seven.addClickListener((x, y, bu) -> {
            saisieText.setText(saisieText.getText() + "7");
        });

        eight.addClickListener((x, y, bu) -> {
            saisieText.setText(saisieText.getText() + "8");
        });

        nine.addClickListener((x, y, bu) -> {
            saisieText.setText(saisieText.getText() + "9");
        });

        zero.addClickListener((x, y, bu) -> {
            saisieText.setText(saisieText.getText() + "0");
        });

        clear.addClickListener((x, y, bu) -> {
            saisieText.setText("");
        });

        enter.addClickListener((x, y, bu) -> {

            if(saisieText.getText().equals(code)) {
                System.out.println("Code correct");
            } else {
                System.out.println("Code incorrect");
            }
        });

        add(background);

    }

    @Override
    public List<ResourceLocation> getCssStyles() {
        return Collections.singletonList(new ResourceLocation("dynamxmod:css/keypad.css"));
    }
}
