package fr.yan36.westerlife.client.gui.acs;

import fr.aym.acsguis.component.layout.GridLayout;
import fr.aym.acsguis.component.layout.GuiScaler;
import fr.aym.acsguis.component.panel.GuiFrame;
import fr.aym.acsguis.component.panel.GuiPanel;
import fr.aym.acsguis.component.textarea.GuiLabel;
import fr.yan36.westerlife.Main;
import fr.yan36.westerlife.common.network.PacketChangeBlockColor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

import java.util.Collections;
import java.util.List;

public class CSSGuiColoredBlock extends GuiFrame {
    public CSSGuiColoredBlock() {
        super(new GuiScaler.Identity());
    }
    public CSSGuiColoredBlock(BlockPos pos) {
        super(new GuiScaler.Identity());


        GuiPanel background = new GuiPanel();
        background.setCssClass("bg");

        GuiLabel title = new GuiLabel("Choissisez une couleur");
        title.setCssClass("title");
        background.add(title);


        GuiPanel panel = new GuiPanel();
        panel.setCssClass("coloredblock");
        panel.setLayout(new GridLayout(-1, 500, 20, GridLayout.GridDirection.HORIZONTAL, 1));


        GuiPanel darkgray = new GuiPanel();
        darkgray.setCssClass("darkgray");
        panel.add(darkgray);
        darkgray.addClickListener((mouseX, mouseY, mouseButton) -> {
            Minecraft.getMinecraft().player.closeScreen();
            Main.network.sendToServer(new PacketChangeBlockColor(pos, 0x1c1c1c));
        });
        GuiPanel middlegray = new GuiPanel();
        middlegray.setCssClass("middlegray");
        panel.add(middlegray);
        middlegray.addClickListener((mouseX, mouseY, mouseButton) -> {
            Minecraft.getMinecraft().player.closeScreen();
            Main.network.sendToServer(new PacketChangeBlockColor(pos, 0x1f1f1f));
        });

        GuiPanel gray = new GuiPanel();
        gray.setCssClass("gray");
        panel.add(gray);
        gray.addClickListener((mouseX, mouseY, mouseButton) -> {
            Minecraft.getMinecraft().player.closeScreen();
            Main.network.sendToServer(new PacketChangeBlockColor(pos, 0x474747));
        });

        GuiPanel lightgray = new GuiPanel();
        lightgray.setCssClass("lightgray");
        panel.add(lightgray);
        lightgray.addClickListener((mouseX, mouseY, mouseButton) -> {
            Minecraft.getMinecraft().player.closeScreen();
            Main.network.sendToServer(new PacketChangeBlockColor(pos, 0xECECEC));
        });

        GuiPanel lightblue = new GuiPanel();
        lightblue.setCssClass("lightblue");
        panel.add(lightblue);
        lightblue.addClickListener((mouseX, mouseY, mouseButton) -> {
            Minecraft.getMinecraft().player.closeScreen();
            Main.network.sendToServer(new PacketChangeBlockColor(pos, 0xCBF2FF));
        });

        GuiPanel yellow = new GuiPanel();
        yellow.setCssClass("yellow");
        panel.add(yellow);
        yellow.addClickListener((mouseX, mouseY, mouseButton) -> {
            Minecraft.getMinecraft().player.closeScreen();
            Main.network.sendToServer(new PacketChangeBlockColor(pos, 0xFFD53F));
        });

        GuiPanel salmon = new GuiPanel();
        salmon.setCssClass("salmon");
        panel.add(salmon);
        salmon.addClickListener((mouseX, mouseY, mouseButton) -> {
            Minecraft.getMinecraft().player.closeScreen();
            Main.network.sendToServer(new PacketChangeBlockColor(pos, 0xFFAA85));
        });

        GuiPanel blue = new GuiPanel();
        blue.setCssClass("blue");
        panel.add(blue);
        blue.addClickListener((mouseX, mouseY, mouseButton) -> {
            Minecraft.getMinecraft().player.closeScreen();
            Main.network.sendToServer(new PacketChangeBlockColor(pos, 0x6196D4));
        });

        GuiPanel marine = new GuiPanel();
        marine.setCssClass("marine");
        panel.add(marine);
        marine.addClickListener((mouseX, mouseY, mouseButton) -> {
            Minecraft.getMinecraft().player.closeScreen();
            Main.network.sendToServer(new PacketChangeBlockColor(pos, 0x385867));
        });

        GuiPanel brown = new GuiPanel();
        brown.setCssClass("brown");
        panel.add(brown);
        brown.addClickListener((mouseX, mouseY, mouseButton) -> {
            Minecraft.getMinecraft().player.closeScreen();
            Main.network.sendToServer(new PacketChangeBlockColor(pos, 0x725D2E));
        });

        GuiPanel darkred = new GuiPanel();
        darkred.setCssClass("darkred");
        panel.add(darkred);
        darkred.addClickListener((mouseX, mouseY, mouseButton) -> {
            Minecraft.getMinecraft().player.closeScreen();
            Main.network.sendToServer(new PacketChangeBlockColor(pos, 0xA95A34));
        });

        GuiPanel darkblue = new GuiPanel();
        darkblue.setCssClass("darkblue");
        panel.add(darkblue);
        darkblue.addClickListener((mouseX, mouseY, mouseButton) -> {
            Minecraft.getMinecraft().player.closeScreen();
            Main.network.sendToServer(new PacketChangeBlockColor(pos, 0x25456E));
        });

        GuiPanel green = new GuiPanel();
        green.setCssClass("green");
        panel.add(green);
        green.addClickListener((mouseX, mouseY, mouseButton) -> {
            Minecraft.getMinecraft().player.closeScreen();
            Main.network.sendToServer(new PacketChangeBlockColor(pos, 0x5DA13E));
        });

        GuiPanel darkgreen = new GuiPanel();
        darkgreen.setCssClass("darkgreen");
        panel.add(darkgreen);
        darkgreen.addClickListener((mouseX, mouseY, mouseButton) -> {
            Minecraft.getMinecraft().player.closeScreen();
            Main.network.sendToServer(new PacketChangeBlockColor(pos, 0x2C511A));
        });

        GuiPanel purple = new GuiPanel();
        purple.setCssClass("purple");
        panel.add(purple);
        purple.addClickListener((mouseX, mouseY, mouseButton) -> {
            Minecraft.getMinecraft().player.closeScreen();
            Main.network.sendToServer(new PacketChangeBlockColor(pos, 0x9481E4));
        });

        GuiPanel darkpurple = new GuiPanel();
        darkpurple.setCssClass("darkpurple");
        panel.add(darkpurple);
        darkpurple.addClickListener((mouseX, mouseY, mouseButton) -> {
            Minecraft.getMinecraft().player.closeScreen();
            Main.network.sendToServer(new PacketChangeBlockColor(pos, 0x52438C));
        });



        background.add(panel);
        add(background);

    }

    @Override
    public List<ResourceLocation> getCssStyles() {
        return Collections.singletonList(new ResourceLocation(Main.MODID, "acsgui/coloredblocks.css"));
    }
}
