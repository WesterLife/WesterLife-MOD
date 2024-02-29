package fr.gabidut76.westerlife.client.gui.acs;

import fr.aym.acsguis.component.button.GuiButton;
import fr.aym.acsguis.component.button.GuiCheckBox;
import fr.aym.acsguis.component.layout.GuiScaler;
import fr.aym.acsguis.component.panel.GuiFrame;
import fr.aym.acsguis.component.panel.GuiPanel;
import fr.aym.acsguis.component.panel.GuiScrollPane;
import fr.aym.acsguis.component.textarea.GuiTextArea;
import fr.aym.acsguis.component.textarea.GuiTextField;
import fr.gabidut76.westerlife.client.gui.other.GuiColorPicker;
import fr.gabidut76.westerlife.common.network.PacketATMInteraction;
import fr.gabidut76.westerlife.common.network.PacketNotif;
import fr.gabidut76.westerlife.common.objects.Notification;
import fr.gabidut76.westerlife.common.objects.character.Character;
import fr.gabidut76.westerlife.common.objects.economy.BankAccount;
import fr.gabidut76.westerlife.westercore.Main;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class CSSGuiEco extends GuiFrame {
    public CSSGuiEco() {
        super(null);
    }

    public CSSGuiEco(List<BankAccount> accounts, List<Character> chars) {
        super(new GuiScaler.Identity());

        System.out.println(accounts);

        GuiPanel background = new GuiPanel();
        background.setCssClass("background_2");

        GuiScrollPane scrollPane = new GuiScrollPane(0, 0, -1, 500);
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

            if(chars.size() == 1) checkBox.setChecked(true);

            scrollPane.add(panel);
            o++;
        }

        if(chars.size() > 1) background.add(scrollPane);

        GuiScrollPane scrollPane2 = new GuiScrollPane(0, 0, -1, 500);
        scrollPane2.setCssClass("scrollpane_2");
        scrollPane2.getStyle().setOffsetX(100);
        List<GuiCheckBox> checkBoxes2 = new java.util.ArrayList<>(Collections.emptyList());
        int o2 = 0;
        for(BankAccount account : accounts) {
            GuiPanel panel = new GuiPanel();
            GuiCheckBox checkBox = new GuiCheckBox();
            checkBox.getCheckLabel().setCssClass("checkbox_label");
            Character ch = chars.stream().filter(c -> {
                System.out.println(c.getUuid() + " - " + account.getOwner());

                return Objects.equals(c.getUuid().toString(), account.getOwner());
            }).findFirst().orElse(null);
            System.out.println(ch);
            if(ch == null) {
                checkBox.setText(account.getRib() + " - " + account.getOwner());
            } else {
                checkBox.setText(account.getRib() + " - " + ch.getLastName() + " " + ch.getFirstNames());
            }

            checkBox.setCssClass("checkbox");

            checkBoxes2.add(checkBox);
            panel.add(checkBox);

            panel.getStyle().setOffsetY(12 * o2);

            scrollPane2.add(panel);
            o2++;
        }

        background.add(scrollPane2);

        GuiButton button = new GuiButton("Valider");
        button.setCssClass("button");
        button.addClickListener((mouseX, mouseY, mouseButton) -> {
            for (GuiCheckBox checkBox : checkBoxes) {
                if(checkBox.isChecked()) {
                    EntityPlayer player = Minecraft.getMinecraft().world.getPlayerEntityByName(checkBox.getText());

                    assert player != null;
                    Main.network.sendToServer(new PacketNotif(player, new Notification("Carte bancaire", "Vous avez reçu votre carte bancaire.", 0x00FF00, System.currentTimeMillis())));

                    for (GuiCheckBox checkBox2 : checkBoxes2) {
                        if(checkBox2.isChecked()) {
                            BankAccount account = accounts.get(checkBoxes2.indexOf(checkBox2));
                            Main.network.sendToServer(new PacketATMInteraction("givecard", Arrays.asList(account.getRib(), player.getUniqueID().toString())));
                        }
                    }

                }
            }
        });



        background.add(button);

        add(background);
    }

    @Override
    public List<ResourceLocation> getCssStyles() {
        return Collections.singletonList(new ResourceLocation(Main.MODID, "acsgui/notif.css"));
    }
}
