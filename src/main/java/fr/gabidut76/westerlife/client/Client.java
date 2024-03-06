package fr.gabidut76.westerlife.client;

import com.google.common.collect.Lists;
import fr.aym.acsguis.api.ACsGuiApi;
import fr.dynamx.api.events.VehicleEntityEvent;
import fr.dynamx.api.events.client.DynamXRenderItemEvent;
import fr.dynamx.client.handlers.hud.CarController;
import fr.dynamx.client.renders.model.renderer.DxModelRenderer;
import fr.dynamx.common.DynamXContext;
import fr.dynamx.common.items.DynamXItemArmor;
import fr.gabidut76.westerlife.CoreMod.WesterLifeSecurityManager;
import fr.gabidut76.westerlife.client.gui.other.*;
import fr.gabidut76.westerlife.common.capabilities.playerstat.PlayerStatData;
import fr.gabidut76.westerlife.westercore.Main;
import fr.gabidut76.westerlife.client.gui.acs.*;
import fr.gabidut76.westerlife.client.renderer.ClientNotifications;
import fr.gabidut76.westerlife.common.blocks.tileentity.TileMacdo;
import fr.gabidut76.westerlife.common.init.DynamXInit;
import fr.gabidut76.westerlife.common.network.PacketReqOpenInv;
import fr.gabidut76.westerlife.common.objects.Notification;
import fr.gabidut76.westerlife.common.objects.character.Character;
import fr.gabidut76.westerlife.common.objects.character.Permis;
import fr.gabidut76.westerlife.common.utils.Animation;
import fr.gabidut76.westerlife.common.utils.list.Warp;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.gui.toasts.SystemToast;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.client.GuiIngameForge;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.util.ResourceLoader;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

import static net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType.CHAT;

@Mod.EventBusSubscriber
public class Client {

    // TODO: Apprendre à développer à _INeox.

    public static int needToCreateCharacter = 0;

    public static HashMap<Integer, Animation> animationState = new HashMap<>();
    public static HashMap<UUID, Character> knowCharacters = new HashMap<>();
    public static HashMap<UUID, Permis> knowPermis = new HashMap<>();
    public static HashMap<String, Boolean> waitForSomething = new HashMap<>();

    @SideOnly(Side.CLIENT)
    public static HashMap<UUID, List<DynamXItemArmor<?>>> superpositionState = new HashMap<>();
    public static List<Warp> warplist = new ArrayList<>();

    public static String openScreenMcef = "none";
    public static BlockPos openScreenMcefPos = null;

    public static boolean isLogginIn = false;




    public static void setScreenMcef(String screenName) {
        openScreenMcef = screenName;
    }

    public static void setScreenMcef(String screenName, BlockPos pos) {
        openScreenMcef = screenName;
        openScreenMcefPos = pos;
    }


    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void GuieventHandler(GuiOpenEvent e) throws IOException, InterruptedException, NoSuchFieldException, IllegalAccessException {

        System.out.println("GUI: " + e.getGui());



//        if(isLogginIn) {
//            e.setGui(new fr.yan36.westerlife.client.gui.other.GuiConnecting(Minecraft.getMinecraft()));
//            return;
//        }

        if (e.getGui() instanceof GuiMainMenu) {
            e.setCanceled(true);
            if(WesterLifeSecurityManager.SHOULD_MANUAL_LOGIN) {
                Minecraft.getMinecraft().displayGuiScreen(new CSSGuiLogin().getGuiScreen());
            } else {
                Minecraft.getMinecraft().displayGuiScreen(new CSSGuiMainMenu().getGuiScreen());
            }


        }



        if (e.getGui() == null && Minecraft.getMinecraft().player == null) {
            System.out.println("CASE OK");
        }
        if (e.getGui() instanceof GuiDownloadTerrain || e.getGui() instanceof GuiScreenWorking || e.getGui() instanceof GuiConnecting) {

            e.setGui(new GuiLoadingTerrain(false).getGuiScreen());
        }


        if (e.getGui() instanceof GuiWorldSelection) {

            e.setGui(new GuiWorldSelectPatcher(Minecraft.getMinecraft().currentScreen));

        }

        if(e.getGui() instanceof GuiInventory) {
            e.setCanceled(true);
            if(Minecraft.getMinecraft().player.isCreative() && !(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))) {
                Minecraft.getMinecraft().displayGuiScreen(new GuiContainerCreative(Minecraft.getMinecraft().player));
            } else {
                Main.network.sendToServer(new PacketReqOpenInv());
            }
        }

        if(e.getGui() instanceof GuiDisconnected) {

            try {
                Field f = e.getGui().getClass().getDeclaredField("message");

                f.setAccessible(true);
                Object o = f.get(e.getGui());
                ITextComponent reason = (ITextComponent) o;


                e.setGui(new GuiServerError(Minecraft.getMinecraft().currentScreen, reason).getGuiScreen());
            } catch (NoSuchFieldException e1) {
                Main.logger.warn("Error while trying to get the message field of GuiDisconnected");
                e1.printStackTrace();
            }


        }


        if (e.getGui() instanceof GuiIngameMenu) {

            if (Main.isOpti) {
                e.setGui(new CSSGuiPauseMenu().getGuiScreen());
            } else {
                e.setCanceled(true);
                Thread.sleep(100);
//                Main.browserScreen = new BrowserScreen("mod://westerlife/menu_echap/echap.html");
//                Main.browserScreen.openMenu();

            }

        }

        if (e.getGui() == null) {
        }

    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void InteractWithEntity(GuiOpenEvent e) {
    }


    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onTickEvent(TickEvent.ClientTickEvent event) {
        if (needToCreateCharacter == 1 && Minecraft.getMinecraft().world != null && Minecraft.getMinecraft().isGamePaused()) {
            if (Main.isOpti) {
                Minecraft.getMinecraft().displayGuiScreen(new CSSCreateCharacter().getGuiScreen());
                needToCreateCharacter = 2;
            } else {
//                Main.browserScreen = new BrowserScreen("mod://westerlife/create_perso/perso1.html");
//                Minecraft.getMinecraft().displayGuiScreen(Main.browserScreen);
//                Main.browserScreen.openMenu();
//                needToCreateCharacter = 2;
            }
        }

        switch (openScreenMcef) {
            case "computer":
//                Main.browserScreen = new BrowserScreen("mod://westerlife/computer/main.html");
//                Main.browserScreen.openMenu();
//                openScreenMcef = "none";
                break;
            case "ingamemenu":
//                Main.browserScreen = new BrowserScreen("mod://westerlife/menu_echap/echap.html");
//                Main.browserScreen.openMenu();
//                openScreenMcef = "none";
                break;
            case "animations":
                if (!Main.isOpti) {
//                    Main.browserScreen = new BrowserScreen("mod://westerlife/animations/index.html");
//                    Main.browserScreen.openMenu();
                } else {
                    Minecraft.getMinecraft().displayGuiScreen(new CSSGuiAnimations().getGuiScreen());
                }
                openScreenMcef = "none";
                break;
            case "digicode":
//                Main.browserScreen = new BrowserScreen("mod://westerlife/digicode/index.html");
//                Main.browserScreen.openMenu();
                openScreenMcef = "none";
                break;
            case "none":
                break;
        }
    }

    private boolean drawHardwareProps = false;


    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void itemRenderer(DynamXRenderItemEvent e) throws Exception {
        if (e.getStage().equals(DynamXRenderItemEvent.EventStage.PRE)) {
            if (e.getContext().getStack().getItem().equals(DynamXInit.burger)) {
                e.setCanceled(true);
                GlStateManager.pushMatrix();
                GlStateManager.rotate(90, 1, 0, 0);
                GlStateManager.translate(0.5f, 0.5f, -0.6f);
                DxModelRenderer objModelRenderer = DynamXContext.getDxModelRegistry().getModel(new ResourceLocation("westerlife", "models/dynamx/blocks/macdo/macdo.obj"));


                if (e.getContext().getStack().getTagCompound() == null) {



                } else {
                    List<TileMacdo.burger> ingredients = new ArrayList<>();
                    String s = e.getContext().getStack().getTagCompound().getString("burger");
                    for (String s1 : s.split(", ")) {
                        ingredients.add(TileMacdo.burger.valueOf(s1));
                    }
                    if (e.getContext().getRenderType().equals(ItemCameraTransforms.TransformType.GUI)) {
                        GlStateManager.rotate(90, 1, 0, 0);
                        GlStateManager.scale(2.2, 2.2, 2.2);
                        DynamXContext.getDxModelRegistry().getModel(new ResourceLocation("westerlife", "models/dynamx/blocks/macdo/macdo.obj")).renderGroup("steak", (byte) 0, false);

                    } else if (e.getContext().getRenderType().equals(ItemCameraTransforms.TransformType.FIRST_PERSON_LEFT_HAND) || e.getContext().getRenderType().equals(ItemCameraTransforms.TransformType.FIRST_PERSON_RIGHT_HAND)) {
                        GlStateManager.rotate(90, 1, 0, 0);
                        GlStateManager.translate(0, -0.1f, 0);
                    } else {
                        GlStateManager.rotate(180, 0, 0, 1);
                    }

                    for (TileMacdo.burger compo : ingredients) {
                        objModelRenderer.renderGroup(compo.getRendervalue(), (byte) 0, false);
                        GlStateManager.translate(0, 0.02f,0);
                    }
                }

                GlStateManager.popMatrix();
            }
        }
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onRenderPre(RenderGameOverlayEvent.Pre event) {
        if (event.getType() == RenderGameOverlayEvent.ElementType.DEBUG) {
            Minecraft mc = Minecraft.getMinecraft();
            event.setCanceled(true);
            EnumFacing orientation = mc.player.getHorizontalFacing();
            int dir = Math.round(orientation.getHorizontalAngle());
            String dire;
            switch (dir) {
                case 0:
                    dire = "North";
                    break;
                case 90:
                    dire = "East";
                    break;
                case 180:
                    dire = "South";
                    break;
                case 270:
                    dire = "West";
                    break;
                default:
                    dire = "undifined";
                    break;
            }
            Chunk chunk = mc.world.getChunk(mc.player.getPosition());
            DecimalFormat df = new DecimalFormat("#.##");
            df.setRoundingMode(RoundingMode.HALF_UP);
            this.drawString(Minecraft.getMinecraft().fontRenderer, "WesterLife - Menu de Debug", 5, 10, 0xFF5C5C);
//            this.drawString(Minecraft.getMinecraft().fontRenderer, mc.debug.split(",", 2)[0].substring(0, 6), 5, 20, 0xFF5C5C);
            this.drawString(Minecraft.getMinecraft().fontRenderer, "Direction : " + dire, 5, 30, 0xFF5C5C);
            if (mc.player.isCreative())
                this.drawString(Minecraft.getMinecraft().fontRenderer, "GPS :", 5, 40, 0xFF5C5C);
            if (mc.player.isCreative())
                this.drawString(Minecraft.getMinecraft().fontRenderer, "X: " + df.format(Minecraft.getMinecraft().player.posX) + " Y: " + df.format(Minecraft.getMinecraft().player.posY) + " Z: " + df.format(Minecraft.getMinecraft().player.posZ), 5, 50, 0xFF5C5C);
            this.drawString(Minecraft.getMinecraft().fontRenderer, "FPS : " + Minecraft.getDebugFPS(), 5, 60, 0xFF5C5C);
            this.drawString(Minecraft.getMinecraft().fontRenderer, "Private data : " + Minecraft.getMinecraft().player.chunkCoordX + " " + Minecraft.getMinecraft().player.chunkCoordZ + " " + Main.isOpti, 5, 70, 0xFF5C5C);
            this.drawString(Minecraft.getMinecraft().fontRenderer, "    SPV : " + DynamXInit.fastRegistryAccess.keySet().size() + " " + Client.knowCharacters.keySet().size() + " " + Client.knowPermis.size(), 5, 80, 0xFF5C5C);
            this.drawString(Minecraft.getMinecraft().fontRenderer, "    OPT : " + Main.isOpti + " " + Client.waitForSomething.keySet().size() + " " + Client.superpositionState.keySet().size(), 5, 90, 0xFF5C5C);
            this.drawString(Minecraft.getMinecraft().fontRenderer, "    LGH : " + chunk.getLightSubtracted(mc.player.getPosition(), 0), 5, 100, 0xFF5C5C);

            if (Minecraft.getMinecraft().player.hasCapability(PlayerStatData.PlayerStatProvider.CAPABILITY, null) && event.getType().equals(RenderGameOverlayEvent.ElementType.DEBUG) && Keyboard.isKeyDown(Keyboard.KEY_APOSTROPHE)) {
                GlStateManager.pushMatrix();
                EntityPlayer player = Minecraft.getMinecraft().player;
                GlStateManager.color(1, 1, 0, 1);
                Minecraft.getMinecraft().fontRenderer.drawString(Objects.requireNonNull(player.getCapability(PlayerStatData.PlayerStatProvider.CAPABILITY, null)).getAnimation() + "", 0, 110, 0xFFFFFF);
                Minecraft.getMinecraft().fontRenderer.drawString(Objects.requireNonNull(player.getCapability(PlayerStatData.PlayerStatProvider.CAPABILITY, null)).getCharacter() + "", 0, 120, 0xFFFFFF);
                GlStateManager.color(1, 1, 1, 1);
                GlStateManager.popMatrix();
            }

            long i = Runtime.getRuntime().maxMemory();
            long j = Runtime.getRuntime().totalMemory();
            long k = Runtime.getRuntime().freeMemory();
            long l = j - k;
            List<String> list = Lists.newArrayList(String.format("Java: %s %dbit", System.getProperty("java.version"), mc.isJava64bit() ? 64 : 32), String.format("Mem: % 2d%% %03d/%03dMB", l * 100L / i, bytesToMb(l), bytesToMb(i)), String.format("Allocated: % 2d%% %03dMB", j * 100L / i, bytesToMb(j)), "", String.format("CPU: %s", OpenGlHelper.getCpu()), "", String.format("Display: %dx%d (%s)", Display.getWidth(), Display.getHeight(), GlStateManager.glGetString(7936)), GlStateManager.glGetString(7937), GlStateManager.glGetString(7938));

            if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) || drawHardwareProps) {
                for (int i1 = 0; i1 < list.size(); ++i1) {
                    String s = list.get(i1);
                    this.drawString(Minecraft.getMinecraft().fontRenderer, s, 5, 120 + i1 * 10, 0xFF5C5C);
                }
                drawHardwareProps = true;
            }

//            GlStateManager.pushMatrix();
//            Gui.drawString(5, 10, "WesterLife - Menu de Débug", org.newdawn.slick.Color.white);
//            getFont().drawString(5, 20, mc.debug.split(",", 2)[0].substring(0, 6), org.newdawn.slick.Color.white);
//            getFont().drawString(5, 30, "Direction : " + dire, org.newdawn.slick.Color.white);
//            getFont().drawString(5, 40, "GPS :", org.newdawn.slick.Color.white);
//            getFont().drawString(5, 50, "X: " + df.format(Minecraft.getMinecraft().player.posX) + " Y: " + df.format(Minecraft.getMinecraft().player.posY) + " Z: " + df.format(Minecraft.getMinecraft().player.posZ), org.newdawn.slick.Color.white);
//            GlStateManager.popMatrix();
        }

        if (event.getType() == RenderGameOverlayEvent.ElementType.EXPERIENCE || event.getType() == RenderGameOverlayEvent.ElementType.FOOD || event.getType() == RenderGameOverlayEvent.ElementType.HEALTH || event.getType() == RenderGameOverlayEvent.ElementType.HEALTH) {
            event.setCanceled(true);

        }
    }

    private static long bytesToMb(long bytes) {
        return bytes / 1024L / 1024L;
    }

    @SideOnly(Side.CLIENT)

    public void drawString(FontRenderer fontRenderer, String str, int x, int y, int color) {
        fontRenderer.drawStringWithShadow(str, x, y, color);
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void renderPseudo(RenderLivingEvent.Specials.Pre e) {

        if (!(Minecraft.getMinecraft().player.isCreative())) {
            e.setCanceled(true);
        }

    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onClickItem(PlayerInteractEvent.RightClickItem e) {
    }

    @SideOnly(Side.CLIENT)
    public static KeyBinding keyBindTest;
    @SideOnly(Side.CLIENT)
    public static KeyBinding keyBindAnimation;
    @SideOnly(Side.CLIENT)
    public static KeyBinding keyOpenClothes;

    @SideOnly(Side.CLIENT)
    public Client() {
        FMLCommonHandler.instance().bus().register(this);
        MinecraftForge.EVENT_BUS.register(this);
        keyBindTest = new KeyBinding("westerlife.admin", Keyboard.KEY_F9, "westerlife.category");
        keyBindAnimation = new KeyBinding("westerlife.animation", Keyboard.KEY_F4, "westerlife.keybind");
        keyOpenClothes = new KeyBinding("westerlife.clohtes", Keyboard.KEY_F10, "westerlife.keybind");
        ClientRegistry.registerKeyBinding(keyBindTest);
        ClientRegistry.registerKeyBinding(keyBindAnimation);
        ClientRegistry.registerKeyBinding(keyOpenClothes);
        System.out.println("WesterLife - Client");
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void createHud(VehicleEntityEvent.CreateHud event) {
        System.out.println("CreateHud");
        CarController.setHudIcons(new EngineFailureIcon());
    }


//    @SubscribeEvent
//    @SideOnly(Side.CLIENT)
//    public void addLayer(Set event) {
//
//    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void armorSuperpositor(RenderPlayerEvent.Post event) {
//        LayerRenderer<EntityPlayer> renderer = new LayerArmorSuperposition(event.getRenderer());
//        event.getRenderer().addLayer(renderer);

    }


    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onEvent(InputEvent.KeyInputEvent event) {
        if (keyBindTest.isPressed()) {
            keyTestTyped();
        }

        if (keyBindAnimation.isPressed()) {
            keyAnimationTyped();
        }

        if (keyOpenClothes.isPressed()) {
            ACsGuiApi.asyncLoadThenShowGui("clothes", CSSGuiClothes::new);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_F3) && Keyboard.isKeyDown(Keyboard.KEY_V)) {
            List<DynamXItemArmor<?>> globalitems = ForgeRegistries.ITEMS.getEntries().stream().filter(e -> e.getValue() instanceof DynamXItemArmor<?>).collect(Collectors.toCollection(ArrayList::new)).stream().map(e -> (DynamXItemArmor<?>) e.getValue()).collect(Collectors.toList());
            for (DynamXItemArmor<?> item : globalitems) {
                DynamXInit.fastRegistryAccess.put(item.getInfo().getFullName(), item);
                System.out.println("added " + item.getInfo().getFullName() + " to fast registry access");
            }
            Client.knowCharacters.clear();
            Client.knowPermis.clear();
            Client.waitForSomething.clear();
            ClientNotifications.notifications.clear();
        }

        if(Keyboard.isKeyDown(Keyboard.KEY_F3) && Keyboard.isKeyDown(Keyboard.KEY_D)) {
            Minecraft.getMinecraft().displayGuiScreen(new GuiCharNotRegistred().getGuiScreen());
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_F3) && Keyboard.isKeyDown(Keyboard.KEY_K)) {
            Main.isOpti = !Main.isOpti;
            // show toast message
            if (Main.isOpti) {
//                Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(new TextComponentString("§l§cMode optimisation des activé"));
                ClientNotifications.notifications.add(new Notification("Optimisation", "Vous avez activé le mode optimisation.", 0x00FF00, System.currentTimeMillis()));

            } else {
//                Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(new TextComponentString("§l§cMode optimisation des désactivé"));
//                Minecraft.getMinecraft().getToastGui().add(new SystemToast(SystemToast.Type.NARRATOR_TOGGLE, new TextComponentString("§l§cMode optimisation des désactivé"), new TextComponentString("")));
                ClientNotifications.notifications.add(new Notification("Optimisation", "Vous avez désactivé le mode optimisation.", 0xFF0000, System.currentTimeMillis()));
            }
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_F12)) {
//            ClientNotifications.notifications.add(new Notification("AVERTISSEMENT", "Vous avez avez été warn pour la raison suivante : HRP", 0xFF0000, System.currentTimeMillis()));
//            Minecraft.getMinecraft().player.sendMessage(new TextComponentString("§c> Added new notification."));

            final int[] color = {0};
            Minecraft.getMinecraft().displayGuiScreen(new GuiColorPicker(null, color1 -> System.out.println("Color picked: " + color1)));
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_F3) && Keyboard.isKeyDown(Keyboard.KEY_L)) {
            Minecraft.getMinecraft().ingameGUI = new GuiWesterIngame(Minecraft.getMinecraft());
        }
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onInteractEvent(PlayerInteractEvent.EntityInteract event) {
//        System.out.println("target");
//        Entity Target = event.getTarget();
//
//        System.out.println(Target);
//        if (Target instanceof BaseVehicleEntity) {
//            BaseVehiclePhysicsHandler<?> physicsHandler = ((BaseVehicleEntity<?>) Target).physicsHandler;
//            float speed = physicsHandler.getSpeed(BaseVehiclePhysicsHandler.SpeedUnit.KMH);
//            System.out.println(speed);
//            Minecraft.getMinecraft().ingameGUI.setOverlayMessage("§cVitesse du Véhicule : " + speed, true);
//        }
    }

    @SideOnly(Side.CLIENT)
    private void keyTestTyped() {
        //ACsGuiApi.asyncLoadThenShowGui("gendarmerie", CSSGuiGendarmerieLogin::new);
//        Main.browserScreen = new BrowserScreen();
//        Main.browserScreen.openMenu();
        //Main.browserScreen.executeJS("window.vue.setWindowF4('test', 'test');");
        System.out.println("Ouverture du menu");

    }

    @SideOnly(Side.CLIENT)
    private static void keyAnimationTyped() {
        openScreenMcef = "animations";
    }

    @SideOnly(Side.CLIENT)
    public static TrueTypeFont getFont() {
        TrueTypeFont font = null;

        try {
            InputStream inputStream = ResourceLoader.getResourceAsStream("font.ttf");

            Font awtFont = Font.createFont(Font.TRUETYPE_FONT, inputStream);
            awtFont = awtFont.deriveFont(5f);
            font = new TrueTypeFont(awtFont, false);

        } catch (Exception e) {
            e.printStackTrace();
        }
        assert font != null;

        return font;
    }
}

    /*@SubscribeEvent
    public void livingUpdateEvent(LivingEvent.LivingUpdateEvent e) {


        if (Minecraft.getMinecraft().player.getHeldItemMainhand().isItemEqual(new ItemStack(Main.PistoletRadar))) {

            System.out.println("e");

        }
    }*/
        /*if(e.getItemStack().isItemEqualIgnoreDurability(pistolerradar)){

            e.getEntityPlayer().getCooldownTracker().setCooldown(e.getItemStack().getItem(), 30);
            e.getEntityPlayer().playSound(SoundsInit.BIP, 0.5f, 1f);


            Minecraft.getMinecraft().ingameGUI.setOverlayMessage("§cVitesse du Véhicule : ", true);

        */
