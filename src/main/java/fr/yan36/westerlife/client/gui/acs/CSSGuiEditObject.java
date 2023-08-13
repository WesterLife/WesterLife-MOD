package fr.yan36.westerlife.client.gui.acs;

import fr.aym.acsguis.component.button.GuiButton;
import fr.aym.acsguis.component.button.GuiCheckBox;
import fr.aym.acsguis.component.layout.GuiScaler;
import fr.aym.acsguis.component.list.GuiList;
import fr.aym.acsguis.component.list.GuiSlotList;
import fr.aym.acsguis.component.panel.GuiFrame;
import fr.aym.acsguis.component.panel.GuiPanel;
import fr.aym.acsguis.component.textarea.GuiTextArea;
import fr.aym.acsguis.component.textarea.GuiTextField;
import fr.nathanael2611.modularvoicechat.client.gui.GuiDropDownMenu;
import fr.yan36.westerlife.Main;
import fr.yan36.westerlife.common.Util;
import fr.yan36.westerlife.common.blocks.dynamx.BlockPanneauRue;
import fr.yan36.westerlife.common.blocks.tileentity.*;
import fr.yan36.westerlife.common.network.PacketUpdateTileEntity;
import net.minecraft.util.ResourceLocation;

import java.util.*;

public class CSSGuiEditObject extends GuiFrame {
    public CSSGuiEditObject(String pos, String isFor) {
        super(new GuiScaler.Identity());

        GuiPanel background = new GuiPanel();
        background.setCssClass("background");
        System.out.println("pos = " + pos);
        System.out.println("isFor = " + isFor);
        if (Objects.equals(isFor, "prue")) {
            TilePanneauRue tile = (TilePanneauRue) Objects.requireNonNull(mc.world).getTileEntity(Objects.requireNonNull(Util.parseBlockPosFromString(pos)));
            GuiTextField text = new GuiTextField();
            text.setCssClass("text");
            assert tile != null;
            text.setText(tile.getName());

            GuiCheckBox isWall = new GuiCheckBox();
            isWall.setCssClass("isWall");
            isWall.setText("Sur un mur ?");
            isWall.setChecked(tile.getType().equals(BlockPanneauRue.Type.WALL));

            GuiButton save = new GuiButton();
            save.setCssClass("save");
            save.setText("Sauvegarder");
            save.addClickListener((a, b, c) -> {
                Main.network.sendToServer(new PacketUpdateTileEntity(tile.getPos(), "prue", new String[]{text.getText(), isWall.isChecked() ? "WALL" : "PILLAR"}));
            });


            background.add(text);
            background.add(isWall);
            background.add(save);
        }
        if (Objects.equals(isFor, "tombe")) {
            TileTombe tile = (TileTombe) Objects.requireNonNull(mc.world).getTileEntity(Objects.requireNonNull(Util.parseBlockPosFromString(pos)));
            GuiTextField text = new GuiTextField();
            text.setCssClass("text");
            assert tile != null;
            text.setText(tile.getText());

            GuiButton save = new GuiButton();
            save.setCssClass("save");
            save.setText("Sauvegarder");
            save.addClickListener((a, b, c) -> {
                Main.network.sendToServer(new PacketUpdateTileEntity(tile.getPos(), "tombe", new String[]{text.getText()}));
            });


            background.add(text);
            background.add(save);
        }

        if (Objects.equals(isFor, "radar")) {
            TileRadarFixe tile = (TileRadarFixe) Objects.requireNonNull(mc.world).getTileEntity(Objects.requireNonNull(Util.parseBlockPosFromString(pos)));
            GuiTextField text = new GuiTextField();
            text.setCssClass("text");
            assert tile != null;
            text.setText(String.valueOf(tile.getSpeed()));

            GuiButton save = new GuiButton();
            save.setCssClass("save");
            save.setText("Sauvegarder");
            save.addClickListener((a, b, c) -> {
                Main.network.sendToServer(new PacketUpdateTileEntity(tile.getPos(), "radar", new String[]{text.getText()}));
            });


            background.add(text);
            background.add(save);
        }
        if (Objects.equals(isFor, "movinggate")) {
            TileMovingGate tile = (TileMovingGate) Objects.requireNonNull(mc.world).getTileEntity(Objects.requireNonNull(Util.parseBlockPosFromString(pos)));
            GuiTextField text = new GuiTextField();
            text.setCssClass("text");
            assert tile != null;
            // array getPlayer() splitted by ;
            text.setText(tile.getPlayer().stream().map(Object::toString).reduce((a, b) -> a + ";" + b).orElse("error"));


            GuiCheckBox isOpenForever = new GuiCheckBox();
            isOpenForever.setCssClass("isWall");
            isOpenForever.setText("Ouvert en permanence ?");
            isOpenForever.setChecked(tile.isOpenForever());


            GuiButton save = new GuiButton();
            save.setCssClass("save");
            save.setText("Sauvegarder");
            save.addClickListener((a, b, c) -> {
                Main.network.sendToServer(new PacketUpdateTileEntity(tile.getPos(), "movinggate", new String[]{text.getText(), String.valueOf(isOpenForever.isChecked())}));
            });


            background.add(text);
            background.add(isOpenForever);
            background.add(save);
        }

        if (Objects.equals(isFor, "feurouge")) {
            TileFeuRouge tile = (TileFeuRouge) Objects.requireNonNull(mc.world).getTileEntity(Objects.requireNonNull(Util.parseBlockPosFromString(pos)));

            // inline arraylist


            GuiTextField posisition = new GuiTextField();
            posisition.setCssClass("text2");
            assert tile != null;
            posisition.setText(String.valueOf(tile.getPosition()));

            GuiTextField text = new GuiTextField();
            text.setCssClass("text");
            assert tile != null;
            text.setText(String.valueOf(tile.getSyncvalue()));




            GuiButton save = new GuiButton();

            save.setCssClass("save");
            save.setText("Sauvegarder");
            save.addClickListener((a, b, c) -> {
                Main.network.sendToServer(new PacketUpdateTileEntity(tile.getPos(), "feurouge", new String[]{
                        posisition.getText(),
                        text.getText()
                }));
            });

            background.add(text);
            background.add(save);
            background.add(posisition);


        }
        if (Objects.equals(isFor, "pagglo")) {
            TilePanneauAgglomeration tile = (TilePanneauAgglomeration) Objects.requireNonNull(mc.world).getTileEntity(Objects.requireNonNull(Util.parseBlockPosFromString(pos)));

            // inline arraylist


            GuiTextField text = new GuiTextField();
            text.setCssClass("text");
            assert tile != null;
            text.setText(String.valueOf(tile.getName()));

            GuiButton save = new GuiButton();

            save.setCssClass("save");
            save.setText("Sauvegarder");
            save.addClickListener((a, b, c) -> {
                Main.network.sendToServer(new PacketUpdateTileEntity(tile.getPos(), "pagglo", new String[]{
                        text.getText()
                }));
            });

            background.add(text);
            background.add(save);
        }
        if (Objects.equals(isFor, "portenom")) {
            TilePorteNom tile = (TilePorteNom) Objects.requireNonNull(mc.world).getTileEntity(Objects.requireNonNull(Util.parseBlockPosFromString(pos)));

            // inline arraylist


            GuiTextField text = new GuiTextField();
            text.setCssClass("text");
            assert tile != null;
            text.setText(String.valueOf(tile.getName()));

            GuiTextField name = new GuiTextField();
            name.setCssClass("text2");
            assert tile != null;
            name.setText(String.valueOf(tile.getFunction()));

            GuiButton save = new GuiButton();

            save.setCssClass("save");
            save.setText("Sauvegarder");
            save.addClickListener((a, b, c) -> {
                Main.network.sendToServer(new PacketUpdateTileEntity(tile.getPos(), "portenom", new String[]{
                        text.getText(),
                        name.getText()
                }));
            });

            background.add(text);
            background.add(name);
            background.add(save);
        }





        add(background);

    }

    @Override
    public List<ResourceLocation> getCssStyles() {
        return Collections.singletonList(new ResourceLocation("westerlife:acsgui/editobj.css"));
    }
}
