package fr.gabidut76.westerlife.client.gui.acs.atm;

import fr.aym.acsguis.component.layout.GuiScaler;
import fr.aym.acsguis.component.panel.GuiFrame;
import fr.aym.acsguis.component.panel.GuiPanel;
import fr.aym.acsguis.component.textarea.GuiLabel;
import fr.aym.acsguis.component.textarea.GuiTextField;
import fr.gabidut76.westerlife.westercore.Main;
import fr.gabidut76.westerlife.common.network.PacketATMInteraction;
import fr.gabidut76.westerlife.common.objects.StyleToLoad;
import fr.gabidut76.westerlife.common.objects.character.Character;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import java.util.Arrays;
import java.util.List;

@StyleToLoad
public class CSSGuiATMTransfer extends GuiFrame {
    private String bankAccountIDTrying;
    private String password_used;
    private Character character;


    public CSSGuiATMTransfer() { // For @StyleToLoad
        super(new GuiScaler.Identity());
        this.bankAccountIDTrying = "null";
    }

    public CSSGuiATMTransfer(String bankAccountIDTrying, String password_used, Character character) {
        super(new GuiScaler.Identity());

        System.out.println(character);

        this.bankAccountIDTrying = bankAccountIDTrying;
        this.password_used = password_used;
        this.character = character;

        GuiPanel background = new GuiPanel();
        background.setCssClass("background");

        GuiPanel target = new GuiPanel();
        target.setCssClass("target");
        background.add(target);

        GuiTextField target_input = new GuiTextField();
        target_input.setCssClass("target_input");
        target.add(target_input);

        GuiPanel amount = new GuiPanel();
        amount.setCssClass("amount");
        background.add(amount);

        GuiTextField amount_input = new GuiTextField();
        amount_input.setCssClass("amount_input");
        amount.add(amount_input);

        GuiPanel ok = new GuiPanel();
        ok.setCssClass("ok");
        ok.addClickListener((a,b,c) -> {
            Main.network.sendToServer(new PacketATMInteraction("transfer", Arrays.asList(bankAccountIDTrying, password_used, target_input.getText(), amount_input.getText())));
        });
        background.add(ok);

        GuiPanel cancel = new GuiPanel();
        cancel.setCssClass("cancel");
        cancel.addClickListener((a,b,c) -> {
            Minecraft.getMinecraft().displayGuiScreen(new CSSGuiATMHome(bankAccountIDTrying, password_used, character).getGuiScreen());
        });
        background.add(cancel);

        add(background);

    }

    public CSSGuiATMTransfer(String bankAccountIDTrying, String password_used, Character character, boolean confirm) {
        super(new GuiScaler.Identity());

        System.out.println(character);

        this.bankAccountIDTrying = bankAccountIDTrying;
        this.password_used = password_used;
        this.character = character;

        GuiPanel background = new GuiPanel();
        background.setCssClass("background");

        GuiLabel confirm_text = new GuiLabel("Transfert d'argent.\nRéalisé !");
        confirm_text.setCssClass("confirm_text");
        background.add(confirm_text);

        GuiPanel close = new GuiPanel();
        close.setCssClass("close");
        close.addClickListener((a,b,c) -> {
            System.out.println(character);
            try {
                Minecraft.getMinecraft().displayGuiScreen(new CSSGuiATMHome(bankAccountIDTrying, password_used, character).getGuiScreen());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        background.add(close);


        add(background);

    }



    public List<ResourceLocation> getCssStyles() {
        return Arrays.asList(new ResourceLocation(Main.MODID, "acsgui/atm/main.css"), new ResourceLocation(Main.MODID, "acsgui/atm/transfer.css"));
    }
}
