package fr.gabidut76.westerlife.client.phone.apps;

import fr.gabidut76.westerlife.client.phone.App;
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
