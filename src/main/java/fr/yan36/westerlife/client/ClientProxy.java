package fr.yan36.westerlife.client;

import fr.aym.acsguis.api.ACsGuiApi;
import fr.yan36.westerlife.Main;
import fr.yan36.westerlife.client.phone.Apps;
import fr.yan36.westerlife.common.CommonProxy;
import fr.yan36.westerlife.common.blocks.tileentity.TESign;
import fr.yan36.westerlife.common.blocks.tileentity.TESignRender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.apache.commons.io.IOUtils;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.imageout.ImageIOWriter;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.openal.SoundStore;
import org.newdawn.slick.opengl.ImageIOImageData;
import org.newdawn.slick.util.ResourceLoader;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Objects;


public class ClientProxy extends CommonProxy {

    @Override
    public void preInit() throws IOException {
        super.preInit();

        ClientRegistry.bindTileEntitySpecialRenderer(TESign.class, new TESignRender());

        /**if(Minecraft.getMinecraft().getSession().getUsername().equals("yan36")){
        Audio oggEffect = AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream("assets/sounds/load.ogg"));
        oggEffect.playAsSoundEffect(1.0f, 1.0f, false);

        SoundStore.get().poll(0);}**/

        Display.setTitle("WesterLife - " + Minecraft.getMinecraft().getSession().getUsername());

        setWindowIcon();

        MinecraftForge.EVENT_BUS.register(new Client());
        ACsGuiApi.registerStyleSheetToPreload(new ResourceLocation("dynamxmod", "css/mainmenu.css"));
        ACsGuiApi.registerStyleSheetToPreload(new ResourceLocation("dynamxmod", "css/ingame.css"));
        ACsGuiApi.registerStyleSheetToPreload(new ResourceLocation("dynamxmod", "css/createprofil.css"));
        ACsGuiApi.registerStyleSheetToPreload(new ResourceLocation("dynamxmod", "css/atm.css"));
        ACsGuiApi.registerStyleSheetToPreload(new ResourceLocation("dynamxmod", "css/gendarmerie.css"));
        ACsGuiApi.registerStyleSheetToPreload(new ResourceLocation("dynamxmod", "css/phone.css"));
        ACsGuiApi.registerStyleSheetToPreload(new ResourceLocation("dynamxmod", "css/gendarmerie_login.css"));
        ACsGuiApi.registerStyleSheetToPreload(new ResourceLocation("dynamxmod", "css/keypad.css"));
        ACsGuiApi.registerStyleSheetToPreload(new ResourceLocation("dynamxmod", "css/pompier_login.css"));
        Apps.Init();

    }

    private void setWindowIcon()
    {
        Util.EnumOS util$enumos = Util.getOSType();

        if (util$enumos != Util.EnumOS.OSX)
        {
            InputStream inputstream = null;
            InputStream inputstream1 = null;

            try
            {

                inputstream = Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation(Main.MODID, "icons/icon16.png")).getInputStream();
                inputstream1 = Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation(Main.MODID,"icons/icon32.png")).getInputStream();

                if (inputstream != null && inputstream1 != null)
                {
                    Display.setIcon(new ByteBuffer[] {
                            this.readImageToBuffer(inputstream), this.readImageToBuffer(inputstream1)
                    });
                }
            }
            catch (IOException ioexception)
            {
                ioexception.printStackTrace();
                System.out.println("Erreur, impossible de charger l'icone.");
            }
            finally
            {
                IOUtils.closeQuietly(inputstream);
                IOUtils.closeQuietly(inputstream1);
            }
        }
    }

    private ByteBuffer readImageToBuffer(InputStream imageStream) throws IOException
    {
        BufferedImage bufferedimage = ImageIO.read(imageStream);
        int[] aint = bufferedimage.getRGB(0, 0, bufferedimage.getWidth(), bufferedimage.getHeight(), (int[])null, 0, bufferedimage.getWidth());
        ByteBuffer bytebuffer = ByteBuffer.allocate(4 * aint.length);

        for (int i : aint)
        {
            bytebuffer.putInt(i << 8 | i >> 24 & 255);
        }

        bytebuffer.flip();
        return bytebuffer;
    }

    @Override
    public void init() {
        super.init();
    }

}
