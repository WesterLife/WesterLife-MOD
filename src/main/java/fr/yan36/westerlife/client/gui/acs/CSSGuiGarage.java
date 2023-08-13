package fr.yan36.westerlife.client.gui.acs;

import com.jme3.math.Vector3f;
import fr.aym.acsguis.component.button.GuiButton;
import fr.aym.acsguis.component.entity.GuiEntityRender;
import fr.aym.acsguis.component.layout.GridLayout;
import fr.aym.acsguis.component.layout.GuiScaler;
import fr.aym.acsguis.component.panel.GuiFrame;
import fr.aym.acsguis.component.panel.GuiPanel;
import fr.aym.acsguis.component.panel.GuiScrollPane;
import fr.aym.acsguis.component.panel.GuiTabbedPane;
import fr.aym.acsguis.component.textarea.GuiLabel;
import fr.dynamx.common.contentpack.DynamXObjectLoaders;
import fr.dynamx.common.contentpack.type.vehicle.ModularVehicleInfo;
import fr.dynamx.common.entities.BaseVehicleEntity;
import fr.dynamx.common.entities.vehicles.CarEntity;
import fr.dynamx.common.items.DynamXItemRegistry;
import fr.dynamx.utils.client.DynamXRenderUtils;
import fr.nathanael2611.simpledatabasemanager.client.ClientDatabases;
import fr.yan36.westerlife.Main;
import fr.yan36.westerlife.common.Util;
import fr.yan36.westerlife.common.network.PacketPutCarInGarage;
import fr.yan36.westerlife.common.objects.GarageCar;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;

import java.awt.*;
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

        int garageSize = ClientDatabases.getPersonalPlayerData().getInteger("garageSize");
        List<GarageCar> cars = new ArrayList<>();

        for (int i = 1; i < garageSize + 1; i++) {
            cars.add(GarageCar.fromString(ClientDatabases.getPersonalPlayerData().getString("garage_" + i)));
        }

        for (GarageCar car : cars) {
            GuiPanel carPanel = new GuiPanel();
            carPanel.setCssClass("carPanel");

            GuiLabel carName = new GuiLabel(car.getCarName());
            carName.setCssClass("carName");
            carPanel.add(carName);

            GuiLabel carPlate = new GuiLabel(car.getCarPlate());
            carPlate.setCssClass("carPlate");
            carPanel.add(carPlate);

            carPanel.add(carName);
            scrollPane.add(carPanel);

            scrollPane.add(carPanel);


            GuiPanel panel = new GuiPanel() {
                public void drawBackground(int mouseX, int mouseY, float partialTicks) {
                    super.drawBackground(mouseX, mouseY, partialTicks);
                    RenderHelper.enableStandardItemLighting();
                    DynamXRenderUtils.renderCar(DynamXObjectLoaders.WHEELED_VEHICLES.findInfo(car.getCarName()), (byte) 0);

                    RenderHelper.disableStandardItemLighting();
                }
            };



            scrollPane.add(panel);


        }


        GuiButton button = new GuiButton("Rentrer un véhicule");
        button.setCssClass("button");
        background.add(button);
        button.getStyle().setOffsetY(100);

        button.addClickListener((mouseX, mouseY, mouseButton) -> {
            Main.network.sendToServer(new PacketPutCarInGarage(Util.parseBlockPosFromString(parkloc)));
            Minecraft.getMinecraft().displayGuiScreen(null);
        });

        background.add(scrollPane);
        add(background);
    }

    @Override
    public List<ResourceLocation> getCssStyles() {
        return Collections.singletonList(new ResourceLocation(Main.MODID, "acsgui/garage.css"));
    }
}
