package fr.yan36.westerlife.client.gui.acs.macdo;

import fr.aym.acsguis.component.layout.GuiScaler;
import fr.aym.acsguis.component.panel.GuiFrame;
import fr.aym.acsguis.component.panel.GuiPanel;
import fr.aym.acsguis.component.textarea.GuiLabel;
import fr.yan36.westerlife.Main;
import fr.yan36.westerlife.common.network.PacketTakeMacdoCommand;
import fr.yan36.westerlife.common.objects.CarDealer;
import fr.yan36.westerlife.common.objects.StyleToLoad;
import fr.yan36.westerlife.common.objects.gameplay.MacdoCommand;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@StyleToLoad
public class CSSGuiCommand extends GuiFrame {
    public CSSGuiCommand() {
        super(null);
    }

    public CSSGuiCommand(MacdoCommand.Command command, String status) {
        super(new GuiScaler.Identity());


        System.out.println(command.drinks);
        for (MacdoCommand.Recipe recipe : command.recipes) {
            System.out.println(recipe.name);
        }

        GuiPanel background = new GuiPanel();
        background.setCssClass("background");

        GuiLabel title = new GuiLabel("MucDonald - Commande");
        title.setCssClass("title");
        background.add(title);

        GuiLabel commandLabel = new GuiLabel("Commande : ");
        commandLabel.setCssClass("commandLabel");
        background.add(commandLabel);

        int i = 0;

        for (MacdoCommand.Recipe recipe : command.recipes) {
            GuiLabel recipeLabel = new GuiLabel("- " + recipe.name);
            recipeLabel.setCssClass("recipeLabel");
            recipeLabel.getStyle().setOffsetY(20 * i);
            background.add(recipeLabel);
            i++;
        }


        for (String drink : command.drinks) {
            GuiLabel drinkLabel = new GuiLabel("- " + I18n.format(Objects.requireNonNull(Item.getByNameOrId(drink)).getTranslationKey()));
            drinkLabel.setCssClass("drinkLabel");
            drinkLabel.getStyle().setOffsetY(20 * i);
            background.add(drinkLabel);
            i++;
        }

        GuiPanel takecommand = new GuiPanel();
        takecommand.setCssClass("takecommand");

        GuiLabel takecommandText =  new GuiLabel("Prendre la commande");
        takecommandText.setCssClass("takecommandText");
        takecommand.add(takecommandText);



        System.out.println(status);

        if(!status.equals("waiting")) {
            takecommandText.setText("Commande prise");
            takecommand.setEnabled(false);
        }
        background.add(takecommand);
        takecommand.addClickListener((mouseX, mouseY, mouseButton) -> {
            Main.network.sendToServer(new PacketTakeMacdoCommand());
        });
        add(background);

    }

    @Override
    public List<ResourceLocation> getCssStyles() {
        return Collections.singletonList(new ResourceLocation(Main.MODID, "acsgui/macdo.css"));
    }
}
