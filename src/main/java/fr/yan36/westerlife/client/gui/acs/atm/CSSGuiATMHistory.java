package fr.yan36.westerlife.client.gui.acs.atm;

import fr.aym.acsguis.component.layout.GuiScaler;
import fr.aym.acsguis.component.panel.GuiFrame;
import fr.aym.acsguis.component.panel.GuiPanel;
import fr.aym.acsguis.component.textarea.GuiLabel;
import fr.aym.acsguis.component.textarea.GuiTextField;
import fr.yan36.westerlife.Main;
import fr.yan36.westerlife.common.network.PacketATMInteraction;
import fr.yan36.westerlife.common.objects.StyleToLoad;
import fr.yan36.westerlife.common.objects.character.Character;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import java.util.Arrays;
import java.util.List;

@StyleToLoad
public class CSSGuiATMHistory extends GuiFrame {
    private String bankAccountIDTrying;
    private String password_used;
    private Character character;


    public CSSGuiATMHistory() { // For @StyleToLoad
        super(new GuiScaler.Identity());
        this.bankAccountIDTrying = "null";
    }


    public CSSGuiATMHistory(String bankAccountIDTrying, String password_used, Character character) {
        super(new GuiScaler.Identity());

        System.out.println(character);

        this.bankAccountIDTrying = bankAccountIDTrying;
        this.password_used = password_used;
        this.character = character;

        GuiPanel background = new GuiPanel();
        background.setCssClass("background");

        GuiLabel confirm_text = new GuiLabel("Historique.\nEn développement.");
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
