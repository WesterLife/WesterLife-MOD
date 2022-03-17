package fr.yan36.westerlife.client;

import fr.aym.acsguis.api.ACsGuiApi;
import fr.yan36.westerlife.Discord;
import fr.yan36.westerlife.common.CommonProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import org.lwjgl.opengl.Display;
import java.io.File;
import java.nio.ByteBuffer;

public class ClientProxy extends CommonProxy {

    private static ByteBuffer[] icons;

    @Override
    public void preInit(File configFile) {
        super.preInit(configFile);


        Display.setTitle("WesterLife - " + Minecraft.getMinecraft().getSession().getUsername());

        MinecraftForge.EVENT_BUS.register(new Client());
        ACsGuiApi.registerStyleSheetToPreload(new ResourceLocation("dynamxmod", "css/mainmenu.css"));
        //ACsGuiApi.registerStyleSheetToPreload(new ResourceLocation("dynamxmod", "css/ingamemenu.css"));
        ACsGuiApi.registerStyleSheetToPreload(new ResourceLocation("dynamxmod", "css/createprofil.css"));
    }

    @Override
    public void init() {
        super.init();
    }

}
