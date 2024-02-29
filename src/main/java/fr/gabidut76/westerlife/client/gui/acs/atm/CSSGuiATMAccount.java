package fr.gabidut76.westerlife.client.gui.acs.atm;

import fr.aym.acsguis.component.layout.GuiScaler;
import fr.aym.acsguis.component.panel.GuiFrame;
import fr.aym.acsguis.component.panel.GuiPanel;
import fr.aym.acsguis.component.textarea.GuiLabel;
import fr.gabidut76.westerlife.westercore.Main;
import fr.gabidut76.westerlife.common.objects.StyleToLoad;
import fr.gabidut76.westerlife.common.objects.character.Character;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import java.util.Arrays;
import java.util.List;

@StyleToLoad
public class CSSGuiATMAccount extends GuiFrame {
    private String bankAccountIDTrying;
    private String password_used;
    private Character character;


    public CSSGuiATMAccount() { // For @StyleToLoad
        super(new GuiScaler.Identity());
        this.bankAccountIDTrying = "null";
    }

    public CSSGuiATMAccount(String bankAccountIDTrying, String password_used, Character character) {
        super(new GuiScaler.Identity());

        System.out.println(character);

        this.bankAccountIDTrying = bankAccountIDTrying;
        this.password_used = password_used;
        this.character = character;

        GuiPanel background = new GuiPanel();
        background.setCssClass("background");

        GuiPanel balance = new GuiPanel();
        balance.setCssClass("balance");
        background.add(balance);

        GuiLabel balance_text = new GuiLabel("Solde : " + character.getRelatedBankAccount().getMoney() + "e");
        balance_text.setCssClass("balance_text");
        balance.add(balance_text);

        GuiPanel accountinfo = new GuiPanel();
        accountinfo.setCssClass("accountinfo");
        background.add(accountinfo);

        GuiLabel accountinfo_text = new GuiLabel("Compte n° " + bankAccountIDTrying);
        accountinfo_text.setCssClass("accountinfo_text");
        accountinfo.add(accountinfo_text);

        GuiPanel identity = new GuiPanel();
        identity.setCssClass("identity");
        background.add(identity);

        GuiLabel identity_text = new GuiLabel((character.getGender().equals(Character.Gender.MALE) ? "Monsieur " : "Madame ") + character.getFirstNames() + " " + character.getLastName());
        identity_text.setCssClass("identity_text");
        identity.add(identity_text);

        GuiPanel changepassword = new GuiPanel();
        changepassword.setCssClass("changepassword");
        changepassword.addClickListener((mouseX, mouseY, mouseButton) -> {
            Minecraft.getMinecraft().displayGuiScreen(new CSSGuiATMChangePassword(bankAccountIDTrying, password_used, character).getGuiScreen());
        });
        background.add(changepassword);

        GuiPanel close = new GuiPanel();
        close.setCssClass("close");
        close.addClickListener((mouseX, mouseY, mouseButton) -> {
            Minecraft.getMinecraft().displayGuiScreen(new CSSGuiATMHome(bankAccountIDTrying, password_used, character).getGuiScreen());
        });
        background.add(close);



        add(background);

    }

    public List<ResourceLocation> getCssStyles() {
        return Arrays.asList(new ResourceLocation(Main.MODID, "acsgui/atm/main.css"), new ResourceLocation(Main.MODID, "acsgui/atm/account.css"));
    }
}
