package fr.yan36.westerlife.client.phone.apps;

import fr.aym.acsguis.api.ACsGuiApi;
import fr.yan36.westerlife.client.phone.App;
import fr.yan36.westerlife.client.phone.apps.gui.GuiAppManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class AppSoutMessage extends App {
    public AppSoutMessage() {
        super(new ResourceLocation("dynamxmod:textures/phone/settings.png"), "AppDebug", "0.1");
    }


    @Override
    public void onClick(EntityPlayer executor) {
        System.out.println("Hello !");
    }
}
