package fr.yan36.westerlife.client.gui.acs;

import fr.aym.acsguis.api.ACsGuiApi;
import fr.aym.acsguis.component.button.GuiButton;
import fr.aym.acsguis.component.layout.GuiScaler;
import fr.aym.acsguis.component.panel.GuiFrame;
import fr.aym.acsguis.component.panel.GuiPanel;
import fr.aym.acsguis.component.panel.GuiScrollPane;
import fr.aym.acsguis.component.textarea.GuiLabel;
import fr.aym.acsguis.component.textarea.GuiTextField;
import fr.yan36.westerlife.Main;
import fr.yan36.westerlife.common.network.PacketSyncClothes;
import fr.yan36.westerlife.common.objects.LightSequence;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentBase;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.event.ClickEvent;

import java.util.Collections;
import java.util.List;

public class CSSGuiLights extends GuiFrame {
    public CSSGuiLights() {
        super(new GuiScaler.Identity());
    }
    public CSSGuiLights(LightSequence sequence, String name) {
        super(new GuiScaler.Identity());

        GuiPanel background = new GuiPanel();
        background.setCssClass("background");

        GuiPanel lyresLinked = new GuiPanel();
        lyresLinked.setCssClass("lyresLinked");

        sequence.getLyres().forEach((lyre) -> {
            GuiPanel lyrePanel = new GuiPanel();
            lyrePanel.setCssClass("lyre");

            GuiLabel lyreName = new GuiLabel(lyre.getX() + " " + lyre.getY() + " " + lyre.getZ());
            lyreName.setCssClass("lyrePos");
            lyrePanel.add(lyreName);

            GuiButton lyreButton = new GuiButton("Faire clignoter");
            lyreButton.setCssClass("lyreButton");
            lyreButton.addClickListener((a,b,c) -> {
                TextComponentString text = new TextComponentString("Envoi de la séquence de clignotement à la lyre " + lyre.getX() + " " + lyre.getY() + " " + lyre.getZ());
                text.getStyle().setBold(true);
                TextComponentBase[] textComponents = new TextComponentBase[1];
                textComponents[0] = text;
                // add command suggestion on click
                text.getStyle().setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/wlmod world sequence bl " + lyre.getX() + " " + lyre.getY() + " " + lyre.getZ()));
                Minecraft.getMinecraft().player.sendMessage(text);
            });
            lyrePanel.add(lyreButton);

            GuiButton lyreButton2 = new GuiButton("retirer");
            lyreButton2.setCssClass("lyreButtonremove");
            lyreButton2.addClickListener((a,b,c) -> {
                TextComponentString text = new TextComponentString("Cliquez sur le message pour confirmer la suppression de la lyre " + lyre.getX() + " " + lyre.getY() + " " + lyre.getZ());
                text.getStyle().setBold(true);
                TextComponentBase[] textComponents = new TextComponentBase[1];
                textComponents[0] = text;
                // add command suggestion on click
                sequence.getLyres().remove(sequence.getLyres().indexOf(lyre));
                text.getStyle().setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/wlmod world sequence manuset " + name + " " + sequence.toString()));
                Minecraft.getMinecraft().player.sendMessage(text);
                ACsGuiApi.closeHudGui();
                Minecraft.getMinecraft().player.closeScreen();
            });
            lyrePanel.add(lyreButton2);

            lyrePanel.getStyle().setOffsetY(lyrePanel.getStyle().getOffsetY() + 20 * sequence.getLyres().indexOf(lyre));

            lyresLinked.add(lyrePanel);
        });

        GuiButton switchtoseq = new GuiButton("Switcher vers les séquences");
        switchtoseq.setCssClass("switchtoseq");
        switchtoseq.addClickListener((a,b,c) -> {
            background.remove(lyresLinked);
            sequence.getSequence().forEach((doubleVector -> {
                GuiScrollPane scrollPane = new GuiScrollPane();
                scrollPane.setCssClass("scrollPane");

                GuiLabel from = new GuiLabel(doubleVector.to.z + " ticks");
                from.setCssClass("from");
                scrollPane.add(from);

                GuiTextField fromX = new GuiTextField();
                fromX.setCssClass("fromX");
                fromX.setText(String.valueOf(doubleVector.from.x));
                scrollPane.add(fromX);

                GuiTextField fromY = new GuiTextField();
                fromY.setCssClass("fromY");
                fromY.setText(String.valueOf(doubleVector.from.y));
                scrollPane.add(fromY);

                GuiTextField toX = new GuiTextField();
                toX.setCssClass("toX");
                toX.setText(String.valueOf(doubleVector.to.x));
                scrollPane.add(toX);

                GuiTextField toY = new GuiTextField();
                toY.setCssClass("toY");
                toY.setText(String.valueOf(doubleVector.to.y));
                scrollPane.add(toY);

                GuiTextField toZ = new GuiTextField();
                toZ.setCssClass("toZ");
                toZ.setText(String.valueOf(doubleVector.to.z));
                scrollPane.add(toZ);

                GuiButton remove = new GuiButton("remove");
                remove.setCssClass("remove");
                remove.addClickListener((d,e,f) -> {
                    TextComponentString text = new TextComponentString("Cliquez sur le message pour confirmer la suppression de la séquence " + doubleVector.from.x + " " + doubleVector.from.y + " " + doubleVector.from.z + " " + doubleVector.to.x + " " + doubleVector.to.y + " " + doubleVector.to.z);
                    text.getStyle().setBold(true);
                    TextComponentBase[] textComponents = new TextComponentBase[1];
                    textComponents[0] = text;
                    // add command suggestion on click
                    sequence.getSequence().remove(sequence.getSequence().indexOf(doubleVector));
                    text.getStyle().setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/wlmod world sequence manuset " + name + " " + sequence.toString()));
                    Minecraft.getMinecraft().player.sendMessage(text);
                    ACsGuiApi.closeHudGui();
                    Minecraft.getMinecraft().player.closeScreen();
                });

                scrollPane.add(remove);

                GuiButton add = new GuiButton("update");

                add.setCssClass("update");
                add.addClickListener((d,e,f) -> {
                    TextComponentString text = new TextComponentString("Cliquez sur le message pour confirmer la modification de la séquence " + doubleVector.from.x + " " + doubleVector.from.y + " " + doubleVector.from.z + " " + doubleVector.to.x + " " + doubleVector.to.y + " " + doubleVector.to.z);
                    text.getStyle().setBold(true);
                    TextComponentBase[] textComponents = new TextComponentBase[1];
                    textComponents[0] = text;
                    // add command suggestion on click
                    sequence.getSequence().remove(sequence.getSequence().indexOf(doubleVector));
                    doubleVector.from.x = Float.parseFloat(fromX.getText());
                    doubleVector.from.y = Float.parseFloat(fromY.getText());
                    doubleVector.to.x = Float.parseFloat(toX.getText());
                    doubleVector.to.y = Float.parseFloat(toY.getText());
                    doubleVector.to.z = Float.parseFloat(toZ.getText());
                    sequence.getSequence().add(doubleVector);
                    text.getStyle().setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/wlmod world sequence manuset " + name + " " + sequence.toString()));
                    Minecraft.getMinecraft().player.sendMessage(text);
                    ACsGuiApi.closeHudGui();
                    Minecraft.getMinecraft().player.closeScreen();
                });


                scrollPane.add(add);

                // no toZ because it's always 0

                scrollPane.getStyle().setOffsetY(scrollPane.getStyle().getOffsetY() + 20 * sequence.getSequence().indexOf(doubleVector));
                background.add(scrollPane);


            }));

            GuiButton play = new GuiButton("play");
            play.setCssClass("play");
            play.addClickListener((d,e,f) -> {
                TextComponentString text = new TextComponentString("Cliquez sur le message pour confirmer la lecture de la séquence");
                text.getStyle().setBold(true);
                TextComponentBase[] textComponents = new TextComponentBase[1];
                textComponents[0] = text;
                // add command suggestion on click
                text.getStyle().setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/wlmod world sequence play " + name));
                Minecraft.getMinecraft().player.sendMessage(text);
                ACsGuiApi.closeHudGui();
                Minecraft.getMinecraft().player.closeScreen();
            });

            GuiButton resetall = new GuiButton("reset");
            resetall.setCssClass("reset");
            resetall.addClickListener((d,e,f) -> {
                TextComponentString text = new TextComponentString("Cliquez sur le message pour confirmer la réinitialisation de la séquence");
                text.getStyle().setBold(true);
                TextComponentBase[] textComponents = new TextComponentBase[1];
                textComponents[0] = text;
                // add command suggestion on click
                text.getStyle().setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/wlmod world sequence reset " + name));
                Minecraft.getMinecraft().player.sendMessage(text);
                ACsGuiApi.closeHudGui();
                Minecraft.getMinecraft().player.closeScreen();
            });

            background.add(resetall);
            background.add(play);
        });

        lyresLinked.add(switchtoseq);

        background.add(lyresLinked);


        add(background);

    }

    @Override
    public List<ResourceLocation> getCssStyles() {
        return Collections.singletonList(new ResourceLocation(Main.MODID, "acsgui/lights.css"));
    }
}
