package fr.gabidut76.westerlife.client.gui.acs;

import fr.aym.acsguis.component.button.GuiButton;
import fr.aym.acsguis.component.layout.GridLayout;
import fr.aym.acsguis.component.layout.GuiScaler;
import fr.aym.acsguis.component.panel.GuiFrame;
import fr.aym.acsguis.component.panel.GuiPanel;
import fr.aym.acsguis.component.panel.GuiScrollPane;
import fr.aym.acsguis.component.textarea.GuiLabel;
import fr.gabidut76.westerlife.westercore.Main;
import fr.gabidut76.westerlife.common.Util;
import fr.gabidut76.westerlife.common.blocks.tileentity.TileMacdo;
import fr.gabidut76.westerlife.common.init.ItemInit;
import fr.gabidut76.westerlife.common.network.PacketUpdateMacdo;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;

import java.util.Collections;
import java.util.List;

public class CSSGuiMacdo extends GuiFrame {
    public CSSGuiMacdo() {
        super(new GuiScaler.Identity());
    }

    public CSSGuiMacdo(String arg, String pos) {
        super(new GuiScaler.Identity());


        BlockPos blockPos = Util.parseBlockPosFromString(pos);
        GuiPanel background = new GuiPanel();
        background.setCssClass("background");

        GuiLabel title = new GuiLabel("Macdo");
        title.setCssClass("title_2");
        background.add(title);

        GuiScrollPane scrollPane = new GuiScrollPane();
        scrollPane.setCssClass("scrollpane");
        scrollPane.setLayout(new GridLayout(-1, 30, 1, GridLayout.GridDirection.HORIZONTAL, 1));

        if (arg.equals("base")) {
            GuiButton section = new GuiButton("Pain de base");
            section.setCssClass("element");
            scrollPane.add(section);
            if (Minecraft.getMinecraft().player.inventory.hasItemStack(new ItemStack(ItemInit.burger_bread))) {
                section.getStyle().setFontColor(TextFormatting.GREEN);
                section.addClickListener((mouseX, mouseY, mouseButton) -> {
                    Main.network.sendToServer(new PacketUpdateMacdo("BREAD", Util.blockPosToString(blockPos)));
                    Minecraft.getMinecraft().displayGuiScreen(null);
                });
            } else {
                section.getStyle().setFontColor(TextFormatting.RED);
            }

            GuiButton section2 = new GuiButton("Baguette");
            section2.setCssClass("element");
            scrollPane.add(section2);
            if (Minecraft.getMinecraft().player.inventory.hasItemStack(new ItemStack(ItemInit.baguette))) {
                section2.getStyle().setFontColor(TextFormatting.GREEN);
                section2.addClickListener((mouseX, mouseY, mouseButton) -> {
                    Main.network.sendToServer(new PacketUpdateMacdo("BAGUETTE", Util.blockPosToString(blockPos)));
                    Minecraft.getMinecraft().displayGuiScreen(null);
                });
            } else {
                section2.getStyle().setFontColor(TextFormatting.RED);
            }
        }

        if (arg.equals("baguette")) {
            TileMacdo tile = (TileMacdo) Minecraft.getMinecraft().world.getTileEntity(blockPos);
            if (tile.getBurgeringredients().size() <= 4) {
                GuiButton section = new GuiButton("Fromage");
                section.setCssClass("element");
                scrollPane.add(section);
                if (Minecraft.getMinecraft().player.inventory.hasItemStack(new ItemStack(ItemInit.cheese))) {
                    section.getStyle().setFontColor(TextFormatting.GREEN);
                    section.addClickListener((mouseX, mouseY, mouseButton) -> {
                        Main.network.sendToServer(new PacketUpdateMacdo("CHEESE", Util.blockPosToString(blockPos)));
                        Minecraft.getMinecraft().displayGuiScreen(null);
                    });
                } else {
                    section.getStyle().setFontColor(TextFormatting.RED);
                }
                GuiButton section2 = new GuiButton("Tomates");
                section2.setCssClass("element");
                scrollPane.add(section2);
                if (Minecraft.getMinecraft().player.inventory.hasItemStack(new ItemStack(ItemInit.tomatos))) {
                    section2.getStyle().setFontColor(TextFormatting.GREEN);
                    section2.addClickListener((mouseX, mouseY, mouseButton) -> {
                        Main.network.sendToServer(new PacketUpdateMacdo("TOMATO", Util.blockPosToString(blockPos)));
                        Minecraft.getMinecraft().displayGuiScreen(null);
                    });
                } else {
                    section2.getStyle().setFontColor(TextFormatting.RED);
                }
                GuiButton section3 = new GuiButton("Salade");
                section3.setCssClass("element");
                scrollPane.add(section3);
                if (Minecraft.getMinecraft().player.inventory.hasItemStack(new ItemStack(ItemInit.salad))) {
                    section3.getStyle().setFontColor(TextFormatting.GREEN);
                    section3.addClickListener((mouseX, mouseY, mouseButton) -> {
                        Main.network.sendToServer(new PacketUpdateMacdo("SALAD", Util.blockPosToString(blockPos)));
                        Minecraft.getMinecraft().displayGuiScreen(null);
                    });
                } else {
                    section3.getStyle().setFontColor(TextFormatting.RED);
                }
                GuiButton section4 = new GuiButton("Poulet");
                section4.setCssClass("element");
                scrollPane.add(section4);
                if (Minecraft.getMinecraft().player.inventory.hasItemStack(new ItemStack(ItemInit.chicken))) {
                    section4.getStyle().setFontColor(TextFormatting.GREEN);
                    section4.addClickListener((mouseX, mouseY, mouseButton) -> {
                        Main.network.sendToServer(new PacketUpdateMacdo("CHICKEN", Util.blockPosToString(blockPos)));
                        Minecraft.getMinecraft().displayGuiScreen(null);
                    });
                } else {
                    section4.getStyle().setFontColor(TextFormatting.RED);
                }
            }
        }

        if (arg.equals("burger")) {
            GuiButton section2 = new GuiButton("Terminer");
            section2.setCssClass("element");

            section2.getStyle().setFontColor(TextFormatting.GREEN);
            section2.addClickListener((mouseX, mouseY, mouseButton) -> {
                Main.network.sendToServer(new PacketUpdateMacdo("END", Util.blockPosToString(blockPos)));
                Minecraft.getMinecraft().displayGuiScreen(null);
            });
            scrollPane.add(section2);
            for (TileMacdo.burger el : TileMacdo.burger.values()) {
                if (el == TileMacdo.burger.BAGUETTE) continue;
                GuiButton section = new GuiButton(el.getDisplayString());
                section.setCssClass("element");

                if (Minecraft.getMinecraft().player.inventory.hasItemStack(new ItemStack(el.getAssociated_item()))) {
                    section.getStyle().setFontColor(TextFormatting.GREEN);
                    section.addClickListener((mouseX, mouseY, mouseButton) -> {
                        Main.network.sendToServer(new PacketUpdateMacdo(el.name(), Util.blockPosToString(blockPos)));
                        Minecraft.getMinecraft().displayGuiScreen(null);
                    });
                } else {
                    section.getStyle().setFontColor(TextFormatting.RED);
                }
                scrollPane.add(section);
            }
        }


        background.add(scrollPane);

        add(background);

    }

    @Override
    public List<ResourceLocation> getCssStyles() {
        return Collections.singletonList(new ResourceLocation(Main.MODID, "acsgui/macdo.css"));
    }
}
