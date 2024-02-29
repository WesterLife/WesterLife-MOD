package fr.gabidut76.westerlife.client.phone.apps;

import fr.gabidut76.westerlife.client.phone.App;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;

public class AppSMS extends App {
    public AppSMS() {
        super(new ResourceLocation("dynamxmod:textures/phone/settings.png"), "SMS", "1.2");
    }


    @Override
    public void onClick(EntityPlayer executor) {
        executor.sendMessage(new TextComponentString("Hello !"));
    }
}
