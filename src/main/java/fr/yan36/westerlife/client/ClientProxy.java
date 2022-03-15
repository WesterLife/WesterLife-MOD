package fr.yan36.westerlife.client;

import fr.aym.acsguis.api.ACsGuiApi;
import fr.yan36.westerlife.Discord;
import fr.yan36.westerlife.common.CommonProxy;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.relauncher.Side;
import org.lwjgl.opengl.Display;
import java.io.File;
import java.nio.ByteBuffer;

public class ClientProxy extends CommonProxy {

    private static ByteBuffer[] icons;

    @Override
    public void preInit(File configFile) {
        super.preInit(configFile);

        if(Side.CLIENT.isClient()) {
            new Discord().start();
            System.out.println("WesterLife >> Initialisation de RPC");

        }
        Display.setTitle("WesterLife - V1.0");

        MinecraftForge.EVENT_BUS.register(new Client());
        ACsGuiApi.registerStyleSheetToPreload(new ResourceLocation("dynamxmod", "css/mainmenu.css"));
        ACsGuiApi.registerStyleSheetToPreload(new ResourceLocation("dynamxmod", "css/createprofil.css"));

        

    }

    @Override
    public void init() {
        super.init();
    }

}
