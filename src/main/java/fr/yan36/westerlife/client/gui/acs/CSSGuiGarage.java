package fr.yan36.westerlife.client.gui.acs;

import fr.aym.acsguis.component.button.GuiButton;
import fr.aym.acsguis.component.layout.GridLayout;
import fr.aym.acsguis.component.layout.GuiScaler;
import fr.aym.acsguis.component.panel.GuiFrame;
import fr.aym.acsguis.component.panel.GuiPanel;
import fr.aym.acsguis.component.panel.GuiScrollPane;
import fr.aym.acsguis.component.textarea.GuiLabel;
import fr.aym.acsguis.cssengine.positionning.Size;
import fr.aym.acsguis.utils.GuiConstants;
import fr.dynamx.common.contentpack.DynamXObjectLoaders;
import fr.dynamx.utils.client.DynamXRenderUtils;
import fr.nathanael2611.simpledatabasemanager.client.ClientDatabases;
import fr.yan36.westerlife.Main;
import fr.yan36.westerlife.common.Util;
import fr.yan36.westerlife.common.capabilities.playergarage.PlayerGarage;
import fr.yan36.westerlife.common.capabilities.playergarage.PlayerGarageCapability;
import fr.yan36.westerlife.common.network.garage.PacketExtractFromGarage;
import fr.yan36.westerlife.common.network.garage.PacketPutCarInGarage;
import fr.yan36.westerlife.common.objects.GarageCar;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CSSGuiGarage extends GuiFrame {
    public CSSGuiGarage(String pos, String parkloc) {
        super(new GuiScaler.Identity());

        GuiPanel background = new GuiPanel();
        background.setCssClass("background");

        GuiLabel title = new GuiLabel("Garage : " + Minecraft.getMinecraft().player.getName());
        title.setCssClass("title");
        background.setCssClass("background");

        GuiScrollPane scrollPane = new GuiScrollPane();
        scrollPane.setCssClass("scrollPane");
        scrollPane.setLayout(
                new GridLayout(
                        new Size.SizeValue(-1, GuiConstants.ENUM_SIZE.ABSOLUTE),
                        new Size.SizeValue(50, GuiConstants.ENUM_SIZE.ABSOLUTE),
                        new Size.SizeValue(10, GuiConstants.ENUM_SIZE.ABSOLUTE),
                        GridLayout.GridDirection.HORIZONTAL,
                        1
                )
        );


        List<GarageCar> cars = new ArrayList<>();

        EntityPlayer p = Minecraft.getMinecraft().player;

        if (p.hasCapability(PlayerGarageCapability.CAPABILITY, null)) {
            PlayerGarage cap = (PlayerGarage) p.getCapability(PlayerGarageCapability.CAPABILITY, null);
            cars = cap.getCars();
            System.out.println("capa cars : " + cars);
        }

        enableDebugPanel = true;
        int i1 = 0;

        if(cars.isEmpty()) {
            GuiLabel label = new GuiLabel("Aucun véhicule dans le garage");
            label.setCssClass("nocars");
            scrollPane.add(label);
        }

        for (GarageCar car : cars) {
            System.out.println("car : " + car.getCarName());
            GuiPanel carPanel = new GuiPanel();
            System.out.println("car : " + car.isInGarage());
            if(car.isInGarage()) {
                carPanel.setCssClass("carPanel");
            } else {
                carPanel.setCssClass("carPanel3");
            }


            GuiLabel carName = new GuiLabel(TextFormatting.BOLD + DynamXObjectLoaders.WHEELED_VEHICLES.findInfo(car.getCarName()).getDefaultName());
            carName.setCssClass("carName");
            carPanel.add(carName);

            GuiLabel carPlate = new GuiLabel(car.getCarPlate());
            carPlate.setCssClass("carPlate");
            carPanel.add(carPlate);

            carPanel.add(carName);


            final int[] tick = {0};
            int finalI = i1;
            GuiPanel panel = new GuiPanel() {


                @Override
                public void drawBackground(int mouseX, int mouseY, float partialTicks) {
                    super.drawBackground(mouseX, mouseY, partialTicks);
                    GlStateManager.pushMatrix();
                    GlStateManager.disableCull();
                    GlStateManager.translate((this.getScreenX() - this.getScaledBorderSize()) + 30, (this.getScreenY() - this.getScaledBorderSize()) + 35, 20);
                    GlStateManager.scale(10, 10, 10);
                    GL11.glRotatef(180, 1, 0, 0);
                    float rot = tick[0] % 1440 / 4f;
                    GlStateManager.rotate(rot, 0, 1, 0);
                    DynamXRenderUtils.renderCar(DynamXObjectLoaders.WHEELED_VEHICLES.findInfo(car.getCarName()), (byte) car.getMeta());
                    GlStateManager.enableCull();
                    GlStateManager.popMatrix();
                    tick[0]++;

                }
            };
            panel.setCssClass("carPanel2");

            carPanel.add(panel);

            carPanel.addClickListener((mouseX, mouseY, mouseButton) -> {

                Main.network.sendToServer(new PacketExtractFromGarage(Util.parseBlockPosFromString(parkloc), car));
                Minecraft.getMinecraft().displayGuiScreen(null);
            });


            scrollPane.add(carPanel);
            i1++;
        }


        GuiLabel button = new GuiLabel("§lRentrer un véhicule");
        button.setCssClass("button");


        button.addClickListener((mouseX, mouseY, mouseButton) -> {
            Main.network.sendToServer(new PacketPutCarInGarage(Util.parseBlockPosFromString(parkloc)));
            Minecraft.getMinecraft().displayGuiScreen(null);
        });

        background.add(scrollPane);
        background.add(button);
        add(background);
    }

    @Override
    public List<ResourceLocation> getCssStyles() {
        return Collections.singletonList(new ResourceLocation(Main.MODID, "acsgui/garage.css"));
    }
}
