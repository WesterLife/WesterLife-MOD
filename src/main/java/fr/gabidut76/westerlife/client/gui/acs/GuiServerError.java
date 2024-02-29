package fr.gabidut76.westerlife.client.gui.acs;

import fr.aym.acsguis.component.button.GuiButton;
import fr.aym.acsguis.component.layout.GuiScaler;
import fr.aym.acsguis.component.panel.GuiFrame;
import fr.aym.acsguis.component.panel.GuiPanel;
import fr.aym.acsguis.component.textarea.GuiLabel;
import fr.gabidut76.westerlife.westercore.Main;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

import java.util.Collections;
import java.util.List;

public class GuiServerError extends GuiFrame {

    final GuiScreen previousGuiScreen;

    public GuiServerError(GuiScreen previousGuiScreen, ITextComponent reason) {
        super(new GuiScaler.Identity());
        this.previousGuiScreen = previousGuiScreen;

        GuiPanel background = new GuiPanel();
        background.setCssClass("background");

        GuiLabel title = new GuiLabel("WesterLife");
        title.setCssClass("title");

        Thread thread = new Thread(() -> {
            System.out.println(reason.getUnformattedComponentText() + " " + reason.getUnformattedComponentText().equals("e0"));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (reason.getUnformattedComponentText().equals("Please wait...")) {
                System.out.println("Switching to GuiCharNotRegistred");
                Minecraft.getMinecraft().displayGuiScreen(new GuiCharNotRegistred().getGuiScreen());
            }
        });

        thread.start();

        GuiLabel action = new GuiLabel(" " + reason.getFormattedText());
        action.setCssClass("action2");
        action.getStyle().setFontSize(1);


        GuiButton cancel = new GuiButton("Retour");
        cancel.setCssClass("cancel");
        cancel.addClickListener((b, m, c) -> {
            Minecraft.getMinecraft().displayGuiScreen(new GuiMainMenu());
        });

        if (!reason.getUnformattedComponentText().equals("Please wait...")) {
            background.add(cancel);
        }




        GuiLabel mention = new GuiLabel("WesterLife n'est pas affilié à Mojang AB.");
        mention.setCssClass("mention");


        background.add(title);
        background.add(action);
        background.add(mention);

        add(background);

    }

    @Override
    public List<ResourceLocation> getCssStyles() {
        return Collections.singletonList(new ResourceLocation(Main.MODID, "acsgui/loading.css"));
    }
}