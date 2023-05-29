package fr.yan36.westerlife.client.renderer;

import fr.nathanael2611.simpledatabasemanager.client.ClientDatabases;
import fr.nathanael2611.simpledatabasemanager.core.Databases;
import fr.yan36.westerlife.Main;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

public class ClientHUD {

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void healthRender(RenderGameOverlayEvent.Post event) {
        if (event.getType().equals(RenderGameOverlayEvent.ElementType.ALL)) {
            GL11.glColor4f(1, 1, 1, 1);
            int width = event.getResolution().getScaledWidth();
            int x = width - 100;
            ScaledResolution scaledresolution = event.getResolution();
            if (!Minecraft.getMinecraft().player.capabilities.disableDamage) {
                Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation(Main.MODID, "textures/hud/health_empty.png"));
                Gui.drawScaledCustomSizeModalRect(40, scaledresolution.getScaledHeight() - 76, 0, 0, 15, 70, 13, 70, 15, 70);
                int percent = (int) (Minecraft.getMinecraft().player.getHealth() * 70 / Minecraft.getMinecraft().player.getMaxHealth());
                if (percent > 0) {
                    Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation(Main.MODID, "textures/hud/health_full.png"));
                    Gui.drawScaledCustomSizeModalRect( 40, scaledresolution.getScaledHeight() - 76 + (70 - percent), 0, 70 - percent, 15, percent, 13, percent, 15, 70);
                }
                Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation(Main.MODID, "textures/hud/food_empty.png"));
                Gui.drawScaledCustomSizeModalRect(10, scaledresolution.getScaledHeight() - 76, 0, 0, 15, 70, 13, 70, 15, 70);
                int percentFood = (int) (Minecraft.getMinecraft().player.getFoodStats().getFoodLevel() * 70 / 20);
                if (percentFood > 0) {
                    Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation(Main.MODID, "textures/hud/food_full.png"));
                    Gui.drawScaledCustomSizeModalRect( 10, scaledresolution.getScaledHeight() - 76 + (70 - percentFood), 0, 70 - percentFood, 15, percentFood, 13, percentFood, 15, 70);
                }
                Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation(Main.MODID, "textures/hud/water_empty.png"));
                Gui.drawScaledCustomSizeModalRect(25, scaledresolution.getScaledHeight() - 76, 0, 0, 15, 70, 13, 70, 15, 70);
                int percentArmor = Math.round(ClientDatabases.getPersonalPlayerData().getFloat("watervalue") * 70 / 100);
                System.out.println(ClientDatabases.getPersonalPlayerData().getFloat("watervalue"));
                if (percentArmor > 0) {
                    Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation(Main.MODID, "textures/hud/water_full.png"));
                    Gui.drawScaledCustomSizeModalRect(25, scaledresolution.getScaledHeight() - 76 + (70 - percentArmor), 0, 70 - percentArmor, 15, percentArmor, 13, percentArmor, 15, 70);
                }
            }
        }
    }
}
