package fr.yan36.westerlife.client.gui.acs.atm;

import fr.aym.acsguis.component.layout.GuiScaler;
import fr.aym.acsguis.component.panel.GuiFrame;
import fr.aym.acsguis.component.panel.GuiPanel;
import fr.aym.acsguis.component.textarea.GuiLabel;
import fr.yan36.westerlife.Main;
import fr.yan36.westerlife.common.network.PacketATMInteraction;
import fr.yan36.westerlife.common.objects.StyleToLoad;
import fr.yan36.westerlife.common.objects.character.Character;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@StyleToLoad
public class CSSGuiATMHome extends GuiFrame {
    private String bankAccountIDTrying;
    private String password_used;
    private Character character;


    public CSSGuiATMHome() { // For @StyleToLoad
        super(new GuiScaler.Identity());
        this.bankAccountIDTrying = "null";
    }

    public CSSGuiATMHome(String bankAccountIDTrying, String password_used, Character character) {
        super(new GuiScaler.Identity());

        System.out.println(character);

        this.bankAccountIDTrying = bankAccountIDTrying;
        this.password_used = password_used;
        this.character = character;

        GuiPanel background = new GuiPanel();
        background.setCssClass("background");

        GuiPanel remove = new GuiPanel();
        remove.setCssClass("remove");
        background.add(remove);

        remove.addClickListener((mouseX, mouseY, mouseButton) -> {
            Minecraft.getMinecraft().displayGuiScreen(new CSSGuiATMExchange(bankAccountIDTrying, password_used, character, "remove").getGuiScreen());
        });

        GuiPanel add = new GuiPanel();
        add.setCssClass("add");
        background.add(add);

        add.addClickListener((mouseX, mouseY, mouseButton) -> {
            Minecraft.getMinecraft().displayGuiScreen(new CSSGuiATMExchange(bankAccountIDTrying, password_used, character, "add").getGuiScreen());
        });

        GuiPanel transfer = new GuiPanel();
        transfer.setCssClass("transfer");
        transfer.addClickListener((mouseX, mouseY, mouseButton) -> {
            Minecraft.getMinecraft().displayGuiScreen(new CSSGuiATMTransfer(bankAccountIDTrying, password_used, character).getGuiScreen());
        });
        background.add(transfer);

        GuiPanel account = new GuiPanel();
        account.setCssClass("account");
        account.addClickListener((mouseX, mouseY, mouseButton) -> {
            Minecraft.getMinecraft().displayGuiScreen(new CSSGuiATMAccount(bankAccountIDTrying, password_used, character).getGuiScreen());
        });
        background.add(account);

        GuiPanel password = new GuiPanel();
        password.setCssClass("password");
        password.addClickListener((mouseX, mouseY, mouseButton) -> Minecraft.getMinecraft().displayGuiScreen(new CSSGuiATMChangePassword(bankAccountIDTrying, password_used, character).getGuiScreen()));
        background.add(password);

        GuiPanel history = new GuiPanel();
        history.setCssClass("history");
        history.addClickListener((mouseX, mouseY, mouseButton) -> Minecraft.getMinecraft().displayGuiScreen(new CSSGuiATMHistory(bankAccountIDTrying, password_used, character).getGuiScreen()));
        background.add(history);

        GuiPanel accountnb = new GuiPanel();
        accountnb.setCssClass("accountnb");
        background.add(accountnb);

        GuiLabel accountnb_text = new GuiLabel("§lCompte N°:");
        accountnb_text.setCssClass("accountnb_text");
        accountnb.add(accountnb_text);

        GuiLabel accountnb_number = new GuiLabel(bankAccountIDTrying);
        accountnb_number.setCssClass("accountnb_number");
        accountnb.add(accountnb_number);

        GuiPanel money = new GuiPanel();
        money.setCssClass("money");
        background.add(money);

        GuiLabel money_text = new GuiLabel("§lSolde:");
        money_text.setCssClass("money_text");
        money.add(money_text);

        GuiLabel money_number = new GuiLabel("§l" + character.getRelatedBankAccount().getMoney() + "§re");
        money_number.setCssClass("money_number");
        money.add(money_number);

        GuiPanel eject = new GuiPanel();
        eject.setCssClass("eject");
        background.add(eject);

        eject.addClickListener((mouseX, mouseY, mouseButton) -> {
            Minecraft.getMinecraft().displayGuiScreen(null);
        });

        add(background);

    }

    public List<ResourceLocation> getCssStyles() {
        return Arrays.asList(new ResourceLocation(Main.MODID, "acsgui/atm/home.css"), new ResourceLocation(Main.MODID, "acsgui/atm/main.css"));
    }
}
