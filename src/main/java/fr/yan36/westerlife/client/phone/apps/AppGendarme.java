package fr.yan36.westerlife.client.phone.apps;

import fr.aym.acsguis.api.ACsGuiApi;
import fr.yan36.westerlife.client.phone.App;
import fr.yan36.westerlife.client.phone.apps.gui.GuiAppManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class AppGendarme extends App {
    public AppGendarme() {
        super(new ResourceLocation("dynamxmod:textures/phone/settings.png"), "AppGendarme", "0.1");
    }

    @Override
    public void onClick(EntityPlayer executor) {
        ACsGuiApi.closeHudGui();
        //ACsGuiApi.asyncLoadThenShowGui("phone", GuiAppGendarme::new);
    }
}
