package fr.gabidut76.westerlife.client.gui.acs.atm;

import fr.aym.acsguis.component.layout.GuiScaler;
import fr.aym.acsguis.component.panel.GuiFrame;
import fr.aym.acsguis.component.panel.GuiPanel;
import fr.aym.acsguis.component.textarea.GuiLabel;
import fr.gabidut76.westerlife.westercore.Main;
import fr.gabidut76.westerlife.common.network.PacketATMInteraction;
import fr.gabidut76.westerlife.common.objects.StyleToLoad;
import fr.gabidut76.westerlife.common.objects.character.Character;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import java.util.Arrays;
import java.util.List;

@StyleToLoad
public class CSSGuiATMExchange extends GuiFrame {
    private String bankAccountIDTrying;
    private String password_used;
    private Character character;


    public CSSGuiATMExchange() { // For @StyleToLoad
        super(new GuiScaler.Identity());
        this.bankAccountIDTrying = "null";
    }

    public CSSGuiATMExchange(String bankAccountIDTrying, String password_used, Character character, String exchangeType) {
        super(new GuiScaler.Identity());

        System.out.println(character);

        this.bankAccountIDTrying = bankAccountIDTrying;
        this.password_used = password_used;
        this.character = character;

        GuiPanel background = new GuiPanel();
        background.setCssClass("background");

        if (exchangeType.equals("remove")) {
            GuiLabel remove_text = new GuiLabel("Retrait en espèce.\nPour combien voulez-vous retirer ?");
            remove_text.setCssClass("title_text");
            background.add(remove_text);
        } else {
            GuiLabel add_text = new GuiLabel("Dépôt en espèce.\nPour combien voulez-vous déposer ?");
            add_text.setCssClass("title_text");
            background.add(add_text);
        }

        System.out.println("ok2 ?");
        GuiPanel button5 = new GuiPanel();
        button5.setCssClass("button5");
        GuiLabel button5_text = new GuiLabel("5");
        button5_text.setCssClass("button_text");
        button5.addClickListener((a,b,c) -> {
            Main.network.sendToServer(new PacketATMInteraction("exchange", Arrays.asList(bankAccountIDTrying, password_used, "5", exchangeType)));
        });
        button5.add(button5_text);


        background.add(button5);


        GuiPanel button10 = new GuiPanel();
        button10.setCssClass("button10");
        button10.addClickListener((mouseX, mouseY, mouseButton) -> Main.network.sendToServer(new PacketATMInteraction("exchange", Arrays.asList(bankAccountIDTrying, password_used, "10", exchangeType))));
        GuiLabel button10_text = new GuiLabel("10");
        button10_text.setCssClass("button_text");
        button10.add(button10_text);

        background.add(button10);

        GuiPanel button20 = new GuiPanel();
        button20.setCssClass("button20");
        button20.addClickListener((mouseX, mouseY, mouseButton) -> Main.network.sendToServer(new PacketATMInteraction("exchange", Arrays.asList(bankAccountIDTrying, password_used, "20", exchangeType))));
        GuiLabel button20_text = new GuiLabel("20");
        button20_text.setCssClass("button_text");
        button20.add(button20_text);

        background.add(button20);

        GuiPanel button50 = new GuiPanel();
        button50.setCssClass("button50");
        button50.addClickListener((mouseX, mouseY, mouseButton) -> Main.network.sendToServer(new PacketATMInteraction("exchange", Arrays.asList(bankAccountIDTrying, password_used, "50", exchangeType))));
        GuiLabel button50_text = new GuiLabel("50");
        button50_text.setCssClass("button_text");
        button50.add(button50_text);

        background.add(button50);

        GuiPanel button100 = new GuiPanel();
        button100.setCssClass("button100");
        button100.addClickListener((mouseX, mouseY, mouseButton) -> Main.network.sendToServer(new PacketATMInteraction("exchange", Arrays.asList(bankAccountIDTrying, password_used, "100", exchangeType))));
        GuiLabel button100_text = new GuiLabel("100");
        button100_text.setCssClass("button_text");
        button100.add(button100_text);

        background.add(button100);

        GuiPanel button200 = new GuiPanel();
        button200.setCssClass("button200");
        button200.addClickListener((mouseX, mouseY, mouseButton) -> Main.network.sendToServer(new PacketATMInteraction("exchange", Arrays.asList(bankAccountIDTrying, password_used, "200", exchangeType))));
        GuiLabel button200_text = new GuiLabel("200");
        button200_text.setCssClass("button_text");
        button200.add(button200_text);

        background.add(button200);

        GuiPanel button500 = new GuiPanel();
        button500.setCssClass("button500");
        button500.addClickListener((mouseX, mouseY, mouseButton) -> Main.network.sendToServer(new PacketATMInteraction("exchange", Arrays.asList(bankAccountIDTrying, password_used, "500", exchangeType))));
        GuiLabel button500_text = new GuiLabel("500");
        button500_text.setCssClass("button_text");
        button500.add(button500_text);

        background.add(button500);

        GuiPanel amount = new GuiPanel();
        amount.setCssClass("amount");
        background.add(amount);

        GuiPanel cancel = new GuiPanel();
        cancel.setCssClass("cancel");
        cancel.addClickListener((mouseX, mouseY, mouseButton) -> Minecraft.getMinecraft().displayGuiScreen(new CSSGuiATMHome(bankAccountIDTrying, password_used, character).getGuiScreen()));
        background.add(cancel);

        add(background);

    }

    public List<ResourceLocation> getCssStyles() {
        return Arrays.asList(new ResourceLocation(Main.MODID, "acsgui/atm/main.css"), new ResourceLocation(Main.MODID, "acsgui/atm/exchange.css"));
    }
}
