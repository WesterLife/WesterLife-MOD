package fr.yan36.westerlife.client.phone.apps;

import fr.aym.acsguis.api.ACsGuiApi;
import fr.yan36.westerlife.client.phone.App;
import fr.yan36.westerlife.client.phone.apps.gui.GuiSettings;
import fr.yan36.westerlife.client.phone.util.PhoneFrame;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class AppSettings extends App {
    public AppSettings() {
        super(new ResourceLocation("dynamxmod:textures/phone/settings.png"), "Paramètres", "1.0");
    }


    @Override
    public void onClick(EntityPlayer executor) {
<<<<<<< Updated upstream
=======
        PhoneFrame.openGui(executor);
>>>>>>> Stashed changes
    }
}
