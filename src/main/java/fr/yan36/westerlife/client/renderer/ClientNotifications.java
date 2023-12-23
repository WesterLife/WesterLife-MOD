package fr.yan36.westerlife.client.renderer;

import fr.yan36.westerlife.common.objects.Notification;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ClientNotifications {

    public static List<Notification> notifications = new ArrayList<>();


    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void healthRender(RenderGameOverlayEvent.Pre event) {
        if (event.getType().equals(RenderGameOverlayEvent.ElementType.HEALTH) || event.getType().equals(RenderGameOverlayEvent.ElementType.FOOD) || event.getType().equals(RenderGameOverlayEvent.ElementType.EXPERIENCE)) {
            if (!notifications.isEmpty()) {
                int i = 0;
                for (Notification notification : notifications) {
                    System.out.println("caca");
                    Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(notification.getTitle(), 5, 5 + i * 10, notification.getPopupColor());
                    Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(notification.getMessage(), 5, 15 + i * 10, notification.getPopupColor());
                    i++;
                }
            }
        }
    }

}
