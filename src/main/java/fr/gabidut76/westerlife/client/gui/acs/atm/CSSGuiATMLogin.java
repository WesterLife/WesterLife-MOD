package fr.gabidut76.westerlife.client.gui.acs.atm;

import fr.aym.acsguis.component.layout.GuiScaler;
import fr.aym.acsguis.component.panel.GuiFrame;
import fr.aym.acsguis.component.panel.GuiPanel;
import fr.aym.acsguis.component.textarea.GuiLabel;
import fr.gabidut76.westerlife.westercore.Main;
import fr.gabidut76.westerlife.common.network.PacketATMInteraction;
import fr.gabidut76.westerlife.common.objects.StyleToLoad;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@StyleToLoad
public class CSSGuiATMLogin extends GuiFrame {
    private GuiLabel password_indication_text;
    private String password_input = "";
    private String bankAccountIDTrying;

    public CSSGuiATMLogin() { // For @StyleToLoad
        super(new GuiScaler.Identity());
        this.bankAccountIDTrying = "null";
    }

    public CSSGuiATMLogin(String bankAccountIDTrying, String errored) {
        super(new GuiScaler.Identity());

        this.bankAccountIDTrying = bankAccountIDTrying;

        GuiPanel background = new GuiPanel();
        background.setCssClass("background");


        if (!Objects.equals(errored, "yes")) {
            GuiLabel password = new GuiLabel("Saississez\nvotre code.");
            password.setCssClass("password");
            background.add(password);

            GuiPanel password_indication = new GuiPanel();
            password_indication.setCssClass("password_indication");
            background.add(password_indication);

            password_indication_text = new GuiLabel("");
            password_indication_text.setCssClass("password_indication_text");
            password_indication.add(password_indication_text);

            GuiPanel button1 = generateButton("1");
            button1.addClickListener((mouseX, mouseY, mouseButton) -> updatePasswordIndicationText("1"));
            background.add(button1);

            GuiPanel button2 = generateButton("2");
            button2.getStyle().setOffsetX(25);
            button2.addClickListener((mouseX, mouseY, mouseButton) -> updatePasswordIndicationText("2"));
            background.add(button2);

            GuiPanel button3 = generateButton("3");
            button3.getStyle().setOffsetX(50);
            button3.addClickListener((mouseX, mouseY, mouseButton) -> updatePasswordIndicationText("3"));
            background.add(button3);

            GuiPanel button4 = generateButton("4");
            button4.getStyle().setOffsetY(25);
            button4.addClickListener((mouseX, mouseY, mouseButton) -> updatePasswordIndicationText("4"));
            background.add(button4);

            GuiPanel button5 = generateButton("5");
            button5.getStyle().setOffsetX(25);
            button5.getStyle().setOffsetY(25);
            button5.addClickListener((mouseX, mouseY, mouseButton) -> updatePasswordIndicationText("5"));
            background.add(button5);

            GuiPanel button6 = generateButton("6");
            button6.getStyle().setOffsetX(50);
            button6.getStyle().setOffsetY(25);
            button6.addClickListener((mouseX, mouseY, mouseButton) -> updatePasswordIndicationText("6"));
            background.add(button6);

            GuiPanel button7 = generateButton("7");
            button7.getStyle().setOffsetY(50);
            button7.addClickListener((mouseX, mouseY, mouseButton) -> updatePasswordIndicationText("7"));
            background.add(button7);

            GuiPanel button8 = generateButton("8");
            button8.getStyle().setOffsetX(25);
            button8.getStyle().setOffsetY(50);
            button8.addClickListener((mouseX, mouseY, mouseButton) -> updatePasswordIndicationText("8"));
            background.add(button8);

            GuiPanel button9 = generateButton("9");
            button9.getStyle().setOffsetX(50);
            button9.getStyle().setOffsetY(50);
            button9.addClickListener((mouseX, mouseY, mouseButton) -> updatePasswordIndicationText("9"));
            background.add(button9);

            GuiPanel button0 = generateButton("0");
            button0.getStyle().setOffsetY(75);
            button0.addClickListener((mouseX, mouseY, mouseButton) -> updatePasswordIndicationText("0"));
            background.add(button0);

            GuiPanel button_return = new GuiPanel();
            button_return.setCssClass("button_return");
            button_return.getStyle().setOffsetY(75);
            button_return.getStyle().setOffsetX(25);
            button_return.addClickListener((mouseX, mouseY, mouseButton) -> updatePasswordIndicationText("-2"));
            background.add(button_return);
        } else {
            GuiLabel password = new GuiLabel("Carte illisible.");
            password.getStyle().setFontColor(TextFormatting.RED);
            password.setCssClass("password");
            background.add(password);
        }

        GuiPanel eject = new GuiPanel(); //TODO: make gradiant button
        eject.setCssClass("eject");
        eject.addClickListener((mouseX, mouseY, mouseButton) -> {
            Minecraft.getMinecraft().displayGuiScreen(null);
        });


        GuiLabel eject_text = new GuiLabel("Ejecter la carte"); // TODO: make sound
        eject_text.setCssClass("eject_text");
        eject.add(eject_text);

        background.add(eject);

        addKeyboardListener((c, i) -> {
            if (Character.isDigit(c)) {
                updatePasswordIndicationText(String.valueOf(c));
            }
        });
        add(background);

    }


    private void updatePasswordIndicationText(String password) {
        if (password.equals("-1")) {
            password_input = "";
            password_indication_text.setText("");
            return;
        }
        if (password.equals("-2")) {
            if (password_input.length() == 0) return;
            password_input = password_input.substring(0, password_input.length() - 1);
            for (int i = 0; i < password_input.length(); i++) {
                password_indication_text.setText(password_input.replaceAll("[0-9]", "* "));
            }
            return;
        }
        password_input += password;
        if (password_input.length() == 4) {
            Main.network.sendToServer(new PacketATMInteraction("login", Arrays.asList(password_input, bankAccountIDTrying)));
            updatePasswordIndicationText("-1");
            return;
        }

        for (int i = 0; i < password_input.length(); i++) {
            password_indication_text.setText(password_input.replaceAll("[0-9]", "* "));
        }
    }


    public static GuiPanel generateButton(String content) {
        GuiPanel button = new GuiPanel();

        button.setCssClass("button");
        GuiLabel button_text = new GuiLabel(content);
        button_text.setCssClass("button_text");

        button.add(button_text);

        return button;
    }


    public List<ResourceLocation> getCssStyles() {
        return Arrays.asList(new ResourceLocation(Main.MODID, "acsgui/atm/login.css"), new ResourceLocation(Main.MODID, "acsgui/atm/main.css"));
    }
}
