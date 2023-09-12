package fr.yan36.westerlife.client;

import fr.aym.acsguis.api.ACsGuiApi;
import fr.yan36.westerlife.Main;
import fr.yan36.westerlife.client.phone.Apps;
import fr.yan36.westerlife.client.renderer.ClientHUD;
import fr.yan36.westerlife.client.renderer.LayerArmorSuperposition;
import fr.yan36.westerlife.client.utils.TestEntity2Renderer;
import fr.yan36.westerlife.client.utils.WarningSignRenderer;
import fr.yan36.westerlife.common.CommonProxy;
import fr.yan36.westerlife.common.blocks.tileentity.*;
import fr.yan36.westerlife.common.blocks.tileentity.render.*;
import fr.yan36.westerlife.common.entities.DynamX.clotheentity.ClothEntity;
import fr.yan36.westerlife.common.entities.DynamX.clotheentity.renderer.ClothEntityRenderer;
import fr.yan36.westerlife.common.entities.DynamX.punchingball.TestEntity2;
import fr.yan36.westerlife.common.entities.DynamX.warningsign.WarningSignEntity;
import fr.yan36.westerlife.common.entities.ModelNPC;
import fr.yan36.westerlife.common.entities.NPCTestEntity;
import fr.yan36.westerlife.common.entities.NpcRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import org.apache.commons.io.IOUtils;
import org.lwjgl.opengl.Display;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Map;


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

        ClientRegistry.bindTileEntitySpecialRenderer(TileMovingGate.class, new RenderTileMovingGate());
        ClientRegistry.bindTileEntitySpecialRenderer(TileTombe.class, new RenderTombe());
        ClientRegistry.bindTileEntitySpecialRenderer(TileRadarFixe.class, new RenderRadarFixe());
        ClientRegistry.bindTileEntitySpecialRenderer(TEBisign.class, new TEBisignRender());
        ClientRegistry.bindTileEntitySpecialRenderer(TileFeuRouge.class, new RenderFeuRouge());
        ClientRegistry.bindTileEntitySpecialRenderer(TileSpot.class, new RenderSpot());
        ClientRegistry.bindTileEntitySpecialRenderer(TileLyre.class, new RenderLyre());
        ClientRegistry.bindTileEntitySpecialRenderer(TileScreen.class, new RenderScreen());
        ClientRegistry.bindTileEntitySpecialRenderer(TilePoteauLevant.class, new RenderTilePoteuLevant());
        ClientRegistry.bindTileEntitySpecialRenderer(TileChair.class, new RenderChair());
        ClientRegistry.bindTileEntitySpecialRenderer(TileColoredBlock.class, new RenderColoredBlock());
        ClientRegistry.bindTileEntitySpecialRenderer(TileIrm.class, new RenderIRM());
        ClientRegistry.bindTileEntitySpecialRenderer(TilePorteNom.class, new RenderPorteNom());
        ClientRegistry.bindTileEntitySpecialRenderer(TilePanneauAgglomeration.class, new RenderPanneauAgglomeration());
        ClientRegistry.bindTileEntitySpecialRenderer(TilePanneauRue.class, new RenderPanneauRue());
        ClientRegistry.bindTileEntitySpecialRenderer(TilePanneauRueSP.class, new RenderPanneauRueSP());
        ClientRegistry.bindTileEntitySpecialRenderer(TileAIPoint.class, new RenderAIPoint());
        ClientRegistry.bindTileEntitySpecialRenderer(TilePark.class, new RenderPark());
        ClientRegistry.bindTileEntitySpecialRenderer(TileGarage.class, new RenderGarage());
        ClientRegistry.bindTileEntitySpecialRenderer(TileMacdo.class, new RenderMacdo());

        RenderingRegistry.registerEntityRenderingHandler(TestEntity2.class, TestEntity2Renderer::new);
        RenderingRegistry.registerEntityRenderingHandler(WarningSignEntity.class, WarningSignRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ClothEntity.class, ClothEntityRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(NPCTestEntity.class, new NpcRenderer(new ModelNPC(), 0.5F));


        MinecraftForge.EVENT_BUS.register(new Client());
        MinecraftForge.EVENT_BUS.register(new ClientHUD());
        Display.setTitle("WesterLife - " + Minecraft.getMinecraft().getSession().getUsername());

        setWindowIcon();




        ACsGuiApi.registerStyleSheetToPreload(new ResourceLocation(Main.MODID, "acsgui/mainmenu.css"));
        ACsGuiApi.registerStyleSheetToPreload(new ResourceLocation(Main.MODID, "acsgui/pausemenu.css"));
        ACsGuiApi.registerStyleSheetToPreload(new ResourceLocation(Main.MODID, "acsgui/animations.css"));
        ACsGuiApi.registerStyleSheetToPreload(new ResourceLocation(Main.MODID, "acsgui/createchar.css"));
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
        ACsGuiApi.registerStyleSheetToPreload(new ResourceLocation(Main.MODID, "acsgui/lights.css"));
        ACsGuiApi.registerStyleSheetToPreload(new ResourceLocation(Main.MODID, "acsgui/coloredblocks.css"));
        ACsGuiApi.registerStyleSheetToPreload(new ResourceLocation(Main.MODID, "acsgui/gendkit.css"));
        ACsGuiApi.registerStyleSheetToPreload(new ResourceLocation(Main.MODID, "acsgui/notif.css"));
        ACsGuiApi.registerStyleSheetToPreload(new ResourceLocation(Main.MODID, "acsgui/editobj.css"));
        ACsGuiApi.registerStyleSheetToPreload(new ResourceLocation(Main.MODID, "acsgui/garage.css"));
        ACsGuiApi.registerStyleSheetToPreload(new ResourceLocation(Main.MODID, "acsgui/macdo.css"));
        ACsGuiApi.registerStyleSheetToPreload(new ResourceLocation(Main.MODID, "acsgui/loading.css"));
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
        final Map<String, RenderPlayer> skinMap = Minecraft.getMinecraft().getRenderManager().getSkinMap();
        skinMap.forEach((key, value) -> value.addLayer(new LayerArmorSuperposition(value)));
        super.init();
    }

}
