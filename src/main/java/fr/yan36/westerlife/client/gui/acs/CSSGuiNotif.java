package fr.yan36.westerlife.client.gui.acs;

import fr.aym.acsguis.api.ACsGuiApi;
import fr.aym.acsguis.component.button.GuiButton;
import fr.aym.acsguis.component.button.GuiCheckBox;
import fr.aym.acsguis.component.layout.GuiScaler;
import fr.aym.acsguis.component.panel.GuiFrame;
import fr.aym.acsguis.component.panel.GuiPanel;
import fr.aym.acsguis.component.panel.GuiScrollPane;
import fr.aym.acsguis.component.textarea.GuiLabel;
import fr.aym.acsguis.component.textarea.GuiTextArea;
import fr.aym.acsguis.component.textarea.GuiTextField;
import fr.yan36.westerlife.Main;
import fr.yan36.westerlife.common.network.PacketNotif;
import fr.yan36.westerlife.common.objects.LightSequence;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentBase;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.event.ClickEvent;

import java.util.Collections;
import java.util.List;

public class CSSGuiNotif extends GuiFrame {
    public CSSGuiNotif() {
        super(new GuiScaler.Identity());
        GuiPanel background = new GuiPanel();
        background.setCssClass("background");

        GuiTextArea textField = new GuiTextArea();
        textField.setHintText("§cEntrez votre message ici");
        textField.setCssClass("textaera");
        background.add(textField);

        GuiScrollPane scrollPane = new GuiScrollPane(0,0,-1,500);
        scrollPane.setCssClass("scrollpane");
        List<GuiCheckBox> checkBoxes = new java.util.ArrayList<>(Collections.emptyList());
        int o = 0;
        for (EntityPlayer player : Minecraft.getMinecraft().world.playerEntities) {
            GuiPanel panel = new GuiPanel();
            GuiCheckBox checkBox = new GuiCheckBox();
            checkBox.setText(player.getName());
            checkBox.setCssClass("checkbox");

            checkBoxes.add(checkBox);
            panel.add(checkBox);

            panel.getStyle().setOffsetY(12 * o);

            scrollPane.add(panel);
            o++;
        }

        background.add(scrollPane);

        GuiButton button = new GuiButton("Envoyer");
        button.setCssClass("button");
        button.addClickListener((mouseX, mouseY, mouseButton) -> {
            String message = textField.getText();
            for (GuiCheckBox checkBox : checkBoxes) {
                if (checkBox.isChecked()) {
                    System.out.println("Sending message to " + checkBox.getText());
                    EntityPlayer player = Minecraft.getMinecraft().world.getPlayerEntityByName(checkBox.getText());
                    if (player != null) {
                        System.out.println(player.getName());
                        Main.network.sendToServer(new PacketNotif(player, message));
                    }
                }
            }
        });

        GuiButton button2 = new GuiButton("Sélectionner tout");
        button2.setCssClass("button2");
        button2.addClickListener((mouseX, mouseY, mouseButton) -> {
            for (GuiCheckBox checkBox : checkBoxes) {
                checkBox.setChecked(true);
            }
        });
        background.add(button2);

        background.add(button);

        add(background);
    }

    @Override
    public List<ResourceLocation> getCssStyles() {
        return Collections.singletonList(new ResourceLocation(Main.MODID, "acsgui/notif.css"));
    }
}
