package fr.yan36.westerlife.client;

import com.mrcrayfish.obfuscate.client.event.ModelPlayerEvent;
import fr.aym.acsguis.api.ACsGuiApi;
import fr.dynamx.api.obj.ObjModelPath;
import fr.dynamx.common.DynamXContext;
import fr.yan36.westerlife.Main;
import fr.yan36.westerlife.client.phone.Apps;
import fr.yan36.westerlife.client.utils.TestEntity2Renderer;
import fr.yan36.westerlife.common.CommonProxy;
import fr.yan36.westerlife.common.blocks.tileentity.*;
import fr.yan36.westerlife.common.blocks.tileentity.render.RenderRadarFixe;
import fr.yan36.westerlife.common.blocks.tileentity.render.RenderTileMovingGate;
import fr.yan36.westerlife.common.entities.TestEntity2;
import fr.yan36.westerlife.common.registry.RegistryHandler;
import fr.yan36.westerlife.common.utils.WesterBuiltinPack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.entity.RenderEnderCrystal;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.commons.io.IOUtils;
import org.lwjgl.opengl.Display;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;


public class ClientProxy extends CommonProxy {

    @Override
    public void registerItemRenderer(Item item, int meta)
    {
        super.registerItemRenderer(item, meta);
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), "inventory"));
    }

    @Override
    public void registerVariantRenderer(Item item, int meta, String filename, String id) {
        super.registerVariantRenderer(item, meta, filename, id);
    }

    @Override
    public void registerEntityRenderers()
    {
        super.registerEntityRenderers();
    }



    @Override
    public void preInit() throws IOException {
        super.preInit();
        System.out.println("ClientProxy preInit");
        ClientRegistry.bindTileEntitySpecialRenderer(TESign.class, new TESignRender());
        ClientRegistry.bindTileEntitySpecialRenderer(TileMovingGate.class, new RenderTileMovingGate());
        ClientRegistry.bindTileEntitySpecialRenderer(TileRadarFixe.class, new RenderRadarFixe());
        ClientRegistry.bindTileEntitySpecialRenderer(TEBisign.class, new TEBisignRender());

        RenderingRegistry.registerEntityRenderingHandler(TestEntity2.class, TestEntity2Renderer::new);
        DynamXContext.getObjModelRegistry().registerModel(new ObjModelPath(new WesterBuiltinPack.WesterPackInfo(), new ResourceLocation(Main.MODID, "test.obj")));

        MinecraftForge.EVENT_BUS.register(new Client());
        Display.setTitle("WesterLife - " + Minecraft.getMinecraft().getSession().getUsername());

        setWindowIcon();




//        ACsGuiApi.registerStyleSheetToPreload(new ResourceLocation("dynamxmod", "css/mainmenu.css"));
//        ACsGuiApi.registerStyleSheetToPreload(new ResourceLocation("dynamxmod", "css/ingame.css"));
//        ACsGuiApi.registerStyleSheetToPreload(new ResourceLocation("dynamxmod", "css/createprofil.css"));
//        ACsGuiApi.registerStyleSheetToPreload(new ResourceLocation("dynamxmod", "css/atm.css"));
//        ACsGuiApi.registerStyleSheetToPreload(new ResourceLocation("dynamxmod", "css/gendarmerie.css"));
//        ACsGuiApi.registerStyleSheetToPreload(new ResourceLocation("dynamxmod", "css/phone.css"));
//        ACsGuiApi.registerStyleSheetToPreload(new ResourceLocation("dynamxmod", "css/gendarmerie_login.css"));
//        ACsGuiApi.registerStyleSheetToPreload(new ResourceLocation("dynamxmod", "css/keypad.css"));
//        ACsGuiApi.registerStyleSheetToPreload(new ResourceLocation("dynamxmod", "css/pompier_login.css"));
//        ACsGuiApi.registerStyleSheetToPreload(new ResourceLocation("phoneframe", "css/phoneframe.css"));
//        ACsGuiApi.registerStyleSheetToPreload(new ResourceLocation("dynamxmod", "css/admin.css"));
//        ACsGuiApi.registerStyleSheetToPreload(new ResourceLocation("dynamxmod", "css/hudig.css"));
        ACsGuiApi.registerStyleSheetToPreload(new ResourceLocation(Main.MODID, "acsgui/clothes.css"));
        Apps.Init(); // Gabi <3


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

                inputstream = Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation(Main.MODID, "icons/logo.png")).getInputStream();
                inputstream1 = Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation(Main.MODID,"icons/logodark.png")).getInputStream();

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
