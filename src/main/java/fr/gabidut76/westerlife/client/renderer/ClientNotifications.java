package fr.gabidut76.westerlife.client.renderer;

import fr.aym.acsguis.api.GuiAPIClientHelper;
import fr.aym.acsguis.component.style.TextComponentStyleManager;
import fr.aym.acsguis.cssengine.font.CssFontHelper;
import fr.gabidut76.westerlife.common.objects.Notification;
import fr.gabidut76.westerlife.westercore.Main;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Mod.EventBusSubscriber(value = Side.CLIENT, modid = Main.MODID)
public class ClientNotifications {

    public static List<Notification> notifications = new ArrayList<>();


    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void healthRender(RenderGameOverlayEvent.Post event) {
        if (!notifications.isEmpty()) {
            int i = 0;
            for (Notification notification : notifications) {
                GlStateManager.pushMatrix();
                GuiAPIClientHelper.glScissor(0, 0, Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);

                if(System.currentTimeMillis() - notification.getTime() < 500f) {
                    GlStateManager.translate(-100 + (float)(System.currentTimeMillis() - notification.getTime()) / 5f,0,0);
                } else {
                    GlStateManager.translate(10,0,0);
                }

                if(System.currentTimeMillis() - notification.getTime() > 10000f) {
                    if(System.currentTimeMillis() - notification.getTime() > 10500f) notification.setVisible(false);
                    GlStateManager.translate(-(float)(System.currentTimeMillis() - notification.getTime()) / 5f,0,0);
                }

                GlStateManager.translate(0, 5 + i * 10, 0);
//                System.out.println((float)(System.currentTimeMillis() - notification.getTime()) / 100f);

                GlStateManager.enableAlpha();
                Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation(Main.MODID, "textures/hud/notif/background.png"));
                Gui.drawModalRectWithCustomSizedTexture(0, 0, 0, 0, 100, 25, 100, 25);

                GlStateManager.pushMatrix();
                GlStateManager.color((float) (notification.popupColor >> 16 & 255) / 255.0F, (float) (notification.popupColor >> 8 & 255) / 255.0F, (float) (notification.popupColor & 255) / 255.0F, 1.0F);
                Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation(Main.MODID, "textures/hud/notif/color.png"));
                Gui.drawModalRectWithCustomSizedTexture(0, 0, 0, 0, 100, 25, 100, 25);
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                GlStateManager.popMatrix();


                GlStateManager.disableTexture2D();

                GlStateManager.pushMatrix();
                GlStateManager.translate(7f, 1f, 0);
                GlStateManager.scale(0.15, 0.15, 0.15);

                CssFontHelper.pushDrawing(new ResourceLocation("westerlife:montserrat_small"), Collections.emptyList());
                CssFontHelper.draw(0,0,  notification.title, Color.WHITE.getRGB());
                CssFontHelper.popDrawing();


                GlStateManager.popMatrix();

                GlStateManager.pushMatrix();

                GlStateManager.translate(7f, 10f, 0);
                GlStateManager.scale(0.1, 0.1, 0.1);

                CssFontHelper.pushDrawing(new ResourceLocation("westerlife:montserrat_small"), Collections.emptyList());
                if(notification.message.length() > 60) {
                    notification.message = notification.message.substring(0, 60) + "...";

                    String[] split = notification.message.split("(?<=\\G.{32})");
                    for (int j = 0; j < split.length; j++) {
                        CssFontHelper.draw(0, j * 50, split[j], Color.WHITE.getRGB());
                    }


                } else if (notification.message.length() > 32) {
                    String[] split = notification.message.split("(?<=\\G.{32})");
                    for (int j = 0; j < split.length; j++) {
                        CssFontHelper.draw(0, j * 50, split[j], Color.WHITE.getRGB());
                    }
                } else
                    CssFontHelper.draw(0,0,  notification.message, Color.WHITE.getRGB());
//                CssFontHelper.draw(0,0,  notification.message, Color.WHITE.getRGB());
                CssFontHelper.popDrawing();


                GlStateManager.popMatrix();

                GlStateManager.disableAlpha();
                GlStateManager.enableTexture2D();
                GlStateManager.popMatrix();
//                Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(notification.getMessage(), 5, 15 + i * 10, notification.getPopupColor());
                if(notification.isVisible()) i+=3;
            }
        }
    }

}
