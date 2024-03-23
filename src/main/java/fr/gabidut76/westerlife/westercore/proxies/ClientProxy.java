package fr.gabidut76.westerlife.westercore.proxies;

import com.labymedia.ultralight.UltralightRenderer;
import com.mojang.authlib.GameProfile;
import fr.aym.acsguis.api.ACsGuiApi;
import fr.gabidut76.westerlife.client.Client;
import fr.gabidut76.westerlife.client.renderer.AnimationRenderer;
import fr.gabidut76.westerlife.client.renderer.ClientNotifications;
import fr.gabidut76.westerlife.common.entities.npcbank.NPCBank;
import fr.gabidut76.westerlife.common.entities.npcbank.NPCBankRenderer;
import fr.gabidut76.westerlife.common.objects.RenderTileBinded;
import fr.gabidut76.westerlife.westercore.Main;
import fr.gabidut76.westerlife.client.gui.ultralight.UltraLight;

import fr.gabidut76.westerlife.client.renderer.ClientHUD;
import fr.gabidut76.westerlife.client.renderer.LayerArmorSuperposition;
import fr.gabidut76.westerlife.client.utils.TestEntity2Renderer;
import fr.gabidut76.westerlife.client.utils.WarningSignRenderer;
import fr.gabidut76.westerlife.westercore.proxies.CommonProxy;
import fr.gabidut76.westerlife.common.blocks.tileentity.*;
import fr.gabidut76.westerlife.common.blocks.tileentity.render.*;
import fr.gabidut76.westerlife.common.entities.DynamX.clotheentity.ClothEntity;
import fr.gabidut76.westerlife.common.entities.DynamX.clotheentity.renderer.ClothEntityRenderer;
import fr.gabidut76.westerlife.common.entities.DynamX.punchingball.TestEntity2;
import fr.gabidut76.westerlife.common.entities.DynamX.warningsign.WarningSignEntity;
import fr.gabidut76.westerlife.common.entities.ModelNPC;
import fr.gabidut76.westerlife.common.entities.NPCTestEntity;
import fr.gabidut76.westerlife.common.entities.NpcRenderer;
import fr.gabidut76.westerlife.common.entities.npc.NPCConcessEntity;
import fr.gabidut76.westerlife.common.entities.npc.NPCConcessEntityRenderer;
import fr.gabidut76.westerlife.common.entities.npcdomac.NPCDomacEntity;
import fr.gabidut76.westerlife.common.entities.npcdomac.NPCDomacEntityRenderer;
import fr.gabidut76.westerlife.common.objects.StyleToLoad;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.discovery.ASMDataTable;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import org.apache.commons.io.IOUtils;
import org.lwjgl.opengl.Display;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.util.*;


public class ClientProxy extends CommonProxy {

//    public static UltralightRenderer renderer;

    @Override
    public void registerItemRenderer(Item item, int meta) {
        super.registerItemRenderer(item, meta);
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), "inventory"));
    }

    @Override
    public void registerVariantRenderer(Item item, int meta, String filename, String id) {
        super.registerVariantRenderer(item, meta, filename, id);
    }

    @Override
    public void registerEntityRenderers() {
        super.registerEntityRenderers();
    }

    @Override
    public EntityPlayer loadPlayer(NBTTagCompound playerNBT, World world) {
        EntityPlayer member;
        if (world.isRemote) {
            GameProfile profile = new GameProfile(UUID.fromString(playerNBT.getString("uuid")), playerNBT.getString("name"));
            TileEntitySkull.updateGameProfile(profile);
            member = new EntityOtherPlayerMP(world, profile);
            member.deserializeNBT(playerNBT.getCompoundTag("data"));
            member.setEntityId(playerNBT.getInteger("id"));
        } else {
            member = super.loadPlayer(playerNBT, world);
        }

        return member;
    }

    @Override
    public void preInit() throws IOException {
        super.preInit();
        System.out.println("ClientProxy preInit");

//        UltraLight.init();
//        renderer = UltraLight.getRenderer();

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
        ClientRegistry.bindTileEntitySpecialRenderer(TileCarPresentation.class, new RenderTileCarPresentation());
        ClientRegistry.bindTileEntitySpecialRenderer(TileTestSphere.class, new RenderTestSphere());
        ClientRegistry.bindTileEntitySpecialRenderer(TileCoke.class, new RenderCoke());

        RenderingRegistry.registerEntityRenderingHandler(TestEntity2.class, TestEntity2Renderer::new);
        RenderingRegistry.registerEntityRenderingHandler(WarningSignEntity.class, WarningSignRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ClothEntity.class, ClothEntityRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(NPCBank.class, NPCBankRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(NPCTestEntity.class, new NpcRenderer(new ModelNPC(), 0.5F));



        MinecraftForge.EVENT_BUS.register(new Client());
        MinecraftForge.EVENT_BUS.register(new ClientHUD());
        MinecraftForge.EVENT_BUS.register(new AnimationRenderer());
        MinecraftForge.EVENT_BUS.register(new ClientNotifications());
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
        ACsGuiApi.registerStyleSheetToPreload(new ResourceLocation(Main.MODID, "acsgui/notregistred.css"));
        ACsGuiApi.registerStyleSheetToPreload(new ResourceLocation(Main.MODID, "acsgui/debug.css"));
        ACsGuiApi.registerStyleSheetToPreload(new ResourceLocation(Main.MODID, "acsgui/staff.css"));

        ACsGuiApi.registerStyleSheetToPreload(new ResourceLocation(Main.MODID, "acsgui/carhud.css"));





    }

    public static List<ResourceLocation> LOADED_STYLESHEETS = new ArrayList<>();

    public static void discoverGuis(FMLConstructionEvent event) {
        Set<ASMDataTable.ASMData> modData = event.getASMHarvestedData().getAll(StyleToLoad.class.getName());
        Iterator<ASMDataTable.ASMData> var2 = modData.iterator();

        while (true) {
            ASMDataTable.ASMData data;
            if (!var2.hasNext()) {
                return;
            }

            data = var2.next();
            String name = data.getClassName();

            try {
                Class<?> styleGui = Class.forName(data.getClassName());

                if(styleGui.getSuperclass().equals(fr.aym.acsguis.component.panel.GuiFrame.class)) {
                    Method m = styleGui.getMethod("getCssStyles");


                    List<ResourceLocation> css = (List<ResourceLocation>) m.invoke(styleGui.newInstance());

                    System.out.println("Found css styles for gui " + name + " : " + css);
                    for (ResourceLocation resourceLocation : css) {
                        if(!LOADED_STYLESHEETS.contains(resourceLocation)) {
                            ACsGuiApi.registerStyleSheetToPreload(resourceLocation);
                        }
                        LOADED_STYLESHEETS.add(resourceLocation);
                    }
                }



            } catch (Exception var12) {
                throw new RuntimeException("Failed to load style gui class " + name, var12);
            }
        }
    }

    public static void discoverBindedTiles(FMLConstructionEvent event) {
        Set<ASMDataTable.ASMData> modData = event.getASMHarvestedData().getAll(RenderTileBinded.class.getName());
        Iterator<ASMDataTable.ASMData> var2 = modData.iterator();

        while (true) {
            ASMDataTable.ASMData data;
            if (!var2.hasNext()) {
                return;
            }

            data = var2.next();
            String name = data.getClassName();

            try {
                Class<?> aClass = Class.forName(data.getClassName());
                RenderTileBinded annotation = aClass.getAnnotation(RenderTileBinded.class);
                if(annotation != null) {
                    System.out.println("Found tile to bind for " + name + " : " + annotation.tileEntityBinded());
                    ClientRegistry.bindTileEntitySpecialRenderer(annotation.tileEntityBinded(), (net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer) aClass.newInstance());
                }



            } catch (Exception var12) {
                throw new RuntimeException("Failed to load style gui class " + name, var12);
            }
        }
    }


    private void setWindowIcon() {
        Util.EnumOS util$enumos = Util.getOSType();

        if (util$enumos != Util.EnumOS.OSX) {
            InputStream inputstream = null;
            InputStream inputstream1 = null;

            try {

                inputstream = Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation(Main.MODID, "icons/logo.png")).getInputStream();
                inputstream1 = Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation(Main.MODID, "icons/logodark.png")).getInputStream();

                if (inputstream != null && inputstream1 != null) {
                    Display.setIcon(new ByteBuffer[]{
                            this.readImageToBuffer(inputstream), this.readImageToBuffer(inputstream1)
                    });
                }
            } catch (IOException ioexception) {
                ioexception.printStackTrace();
                System.out.println("Erreur, impossible de charger l'icone.");
            } finally {
                IOUtils.closeQuietly(inputstream);
                IOUtils.closeQuietly(inputstream1);
            }
        }
    }

    private ByteBuffer readImageToBuffer(InputStream imageStream) throws IOException {
        BufferedImage bufferedimage = ImageIO.read(imageStream);
        int[] aint = bufferedimage.getRGB(0, 0, bufferedimage.getWidth(), bufferedimage.getHeight(), (int[]) null, 0, bufferedimage.getWidth());
        ByteBuffer bytebuffer = ByteBuffer.allocate(4 * aint.length);

        for (int i : aint) {
            bytebuffer.putInt(i << 8 | i >> 24 & 255);
        }

        bytebuffer.flip();
        return bytebuffer;
    }

    @Override
    public void init() {
        final Map<String, RenderPlayer> skinMap = Minecraft.getMinecraft().getRenderManager().getSkinMap();
        skinMap.forEach((key, value) -> value.addLayer(new LayerArmorSuperposition(value)));
        RenderingRegistry.registerEntityRenderingHandler(NPCConcessEntity.class, new NPCConcessEntityRenderer(Minecraft.getMinecraft().getRenderManager()));
        RenderingRegistry.registerEntityRenderingHandler(NPCDomacEntity.class, new NPCDomacEntityRenderer(Minecraft.getMinecraft().getRenderManager()));
        super.init();
    }

}
