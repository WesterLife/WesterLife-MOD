package fr.yan36.westerlife.client;

import com.mrcrayfish.obfuscate.client.event.ModelPlayerEvent;
import fr.aym.acsguis.api.ACsGuiApi;
import fr.dynamx.api.events.ArmorEvent;
import fr.dynamx.api.events.VehicleEntityEvent;
import fr.dynamx.client.handlers.hud.CarController;
import fr.dynamx.common.items.DynamXItemArmor;
import fr.yan36.westerlife.Main;
import fr.yan36.westerlife.client.gui.acs.*;
import fr.yan36.westerlife.client.gui.mcef.AtmScreen;
import fr.yan36.westerlife.client.gui.mcef.BrowserScreen;
import fr.yan36.westerlife.client.gui.other.EngineFailureIcon;
import fr.yan36.westerlife.common.init.DynamXInit;
import fr.yan36.westerlife.common.objects.character.Character;
import fr.yan36.westerlife.common.objects.character.Permis;
import fr.yan36.westerlife.common.utils.Animation;
import fr.yan36.westerlife.common.utils.list.Warp;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreenServerList;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.GuiOpenEvent;
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
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.util.ResourceLoader;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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

    public static void setScreenMcef(String screenName) {
        openScreenMcef = screenName;
    }

    public static void setScreenMcef(String screenName, BlockPos pos) {
        openScreenMcef = screenName;
        openScreenMcefPos = pos;
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void setupPlayerRotations(ModelPlayerEvent.SetupAngles.Pre event) {
        event.getModelPlayer().bipedRightArm.rotateAngleZ = 0;
        animationState.forEach((id, animation) -> {

            if (event.getEntityPlayer().getEntityId() == id) {
                animatePlayer(event.getEntityPlayer(), event.getModelPlayer());

            }
        });

    }

    @SideOnly(Side.CLIENT)

    private void animatePlayer(EntityPlayer ep, ModelBiped modelBiped) {

        Client.animationState.putIfAbsent(ep.getEntityId(), Animation.NONE);
        if (!animationState.get(ep.getEntityId()).equals(Animation.SITTED)) ep.eyeHeight = 1.5f;
        if (animationState.get(ep.getEntityId()).equals(Animation.HANDS_UP)) {
            modelBiped.bipedRightArm.rotateAngleX = (float) Math.toRadians(-180);
            modelBiped.bipedLeftArm.rotateAngleX = (float) Math.toRadians(-180);

        }
        if (animationState.get(ep.getEntityId()).equals(Animation.POINTING_FINGER)) {
            modelBiped.bipedRightArm.rotateAngleX = (float) Math.toRadians(-90);
        }
        if (animationState.get(ep.getEntityId()).equals(Animation.HANDS_BEHIND) || animationState.get(ep.getEntityId()).equals(Animation.MENOTTE)) {
            modelBiped.bipedRightArm.rotateAngleX = (float) Math.toRadians(25);
            modelBiped.bipedLeftArm.rotateAngleX = (float) Math.toRadians(25);
            modelBiped.bipedRightArm.rotateAngleZ = (float) Math.toRadians(-25);
            modelBiped.bipedLeftArm.rotateAngleZ = (float) Math.toRadians(25);
        }
        if (animationState.get(ep.getEntityId()).equals(Animation.RIGHT_ARM_UP)) {
            modelBiped.bipedRightArm.rotateAngleX = (float) Math.toRadians(-180);
        }
        if (animationState.get(ep.getEntityId()).equals(Animation.STAND_AT)) {
            modelBiped.bipedRightArm.rotateAngleX = (float) Math.toRadians(-180);
            modelBiped.bipedRightArm.rotateAngleZ = (float) Math.toRadians(20);
        }
        if (animationState.get(ep.getEntityId()).equals(Animation.SITTED)) {
            modelBiped.bipedLeftLeg.rotateAngleX = (float) Math.toRadians(-90);
            modelBiped.bipedRightLeg.rotateAngleX = (float) Math.toRadians(-90);
            modelBiped.bipedRightLeg.offsetY = 0.58f;
            modelBiped.bipedLeftLeg.offsetY = 0.58f;
            modelBiped.bipedRightLeg.offsetZ = 0.04f;
            modelBiped.bipedLeftLeg.offsetZ = 0.04f;
            ep.eyeHeight = 1f;
            modelBiped.bipedHead.offsetY = 0.5f;
            modelBiped.bipedHeadwear.offsetY = 0.5f;
            modelBiped.bipedBody.offsetY = 0.5f;
            modelBiped.bipedRightArm.offsetY = 0.5f;
            modelBiped.bipedLeftArm.offsetY = 0.5f;
        }
        if (animationState.get(ep.getEntityId()).equals(Animation.HELLO)) {
            // make animation from -80 to -110 with ep.world.getTotalWorldTime()
            int val = (int) ep.world.getTotalWorldTime() % 20;
            if (val < 10) {
                modelBiped.bipedRightArm.rotateAngleZ = (float) Math.toRadians(160 - (val * 1.5));
            } else {
                modelBiped.bipedRightArm.rotateAngleZ = (float) Math.toRadians(160 + (val * 1.5) - 30);
            }
        }

        if (animationState.get(ep.getEntityId()).equals(Animation.SLEEP)) {
            modelBiped.bipedLeftLeg.rotateAngleX = (float) Math.toRadians(-90);
            modelBiped.bipedRightLeg.rotateAngleX = (float) Math.toRadians(-90);
            modelBiped.bipedRightLeg.offsetY = 0.58f;
            modelBiped.bipedLeftLeg.offsetY = 0.58f;
            modelBiped.bipedRightLeg.offsetZ = 0.04f;
            modelBiped.bipedLeftLeg.offsetZ = 0.04f;
            ep.eyeHeight = 0.5f;
            modelBiped.bipedHead.offsetY = 0.5f;
            modelBiped.bipedHeadwear.offsetY = 0.5f;
            modelBiped.bipedBody.offsetY = 0.5f;
            modelBiped.bipedRightArm.offsetY = 0.5f;
            modelBiped.bipedLeftArm.offsetY = 0.5f;
        }
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void GuieventHandler(GuiOpenEvent e) throws IOException, InterruptedException {

        if (e.getGui() instanceof GuiMainMenu) {
//             prout c'est chiant pour dev donc
            e.setCanceled(true);
            if(Main.isOpti) {
                Minecraft.getMinecraft().displayGuiScreen(new CSSGuiMainMenu().getGuiScreen());
            } else {
                Main.browserScreen = new BrowserScreen("mod://westerlife/main_menu/main.html");
                Minecraft.getMinecraft().displayGuiScreen(Main.browserScreen);
//                Main.browserScreen.openMenu();
            }
        }


        if (e.getGui() instanceof GuiScreenServerList) {

        }
        if (e.getGui() instanceof GuiIngameMenu) {

            if (Main.isOpti) {
                e.setGui(new CSSGuiPauseMenu().getGuiScreen());
            } else {
                e.setCanceled(true);
                Thread.sleep(100);
                Main.browserScreen = new BrowserScreen("mod://westerlife/menu_echap/echap.html");
                Main.browserScreen.openMenu();

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
        if (needToCreateCharacter == 1 && Minecraft.getMinecraft().world != null) {
            if (Main.isOpti) {
                Minecraft.getMinecraft().displayGuiScreen(new CSSCreateCharacter().getGuiScreen());
                needToCreateCharacter = 2;
            } else {
                Main.browserScreen = new BrowserScreen("mod://westerlife/create_perso/perso1.html");
                Minecraft.getMinecraft().displayGuiScreen(Main.browserScreen);
                Main.browserScreen.openMenu();
                needToCreateCharacter = 2;
            }
        }

        switch (openScreenMcef) {
            case "computer":
                Main.browserScreen = new BrowserScreen("mod://westerlife/computer/main.html");
                Main.browserScreen.openMenu();
                openScreenMcef = "none";
                break;
            case "ingamemenu":
                Main.browserScreen = new BrowserScreen("mod://westerlife/menu_echap/echap.html");
                Main.browserScreen.openMenu();
                openScreenMcef = "none";
                break;
            case "animations":
                if (!Main.isOpti) {
                    Main.browserScreen = new BrowserScreen("mod://westerlife/animations/index.html");
                    Main.browserScreen.openMenu();
                } else {
                    Minecraft.getMinecraft().displayGuiScreen(new CSSGuiAnimations().getGuiScreen());
                }
                openScreenMcef = "none";
                break;
            case "digicode":
                Main.browserScreen = new BrowserScreen("mod://westerlife/digicode/index.html");
                Main.browserScreen.openMenu();
                openScreenMcef = "none";
                break;
            case "none":
                break;
        }
    }

    //    @SideOnly(Side.CLIENT)
//    @SubscribeEvent
//    public void onRenderPre(RenderGameOverlayEvent.Pre event) {
//        if (event.getType() == RenderGameOverlayEvent.ElementType.DEBUG) {
//            Minecraft mc = Minecraft.getMinecraft();
//            event.setCanceled(true);
//            EnumFacing orientation = mc.player.getHorizontalFacing();
//            int dir = Math.round(orientation.getHorizontalAngle());
//            String dire;
//            switch (dir) {
//                case 0:
//                    dire = "North";
//                    break;
//                case 90:
//                    dire = "East";
//                    break;
//                case 180:
//                    dire = "South";
//                    break;
//                case 270:
//                    dire = "West";
//                    break;
//                default:
//                    dire = "undifined";
//                    break;
//            }
//
//            DecimalFormat df = new DecimalFormat("#.##");
//            df.setRoundingMode(RoundingMode.HALF_UP);
////            this.drawString(Minecraft.getMinecraft().fontRenderer, "WesterLife - Menu de Débug", 5, 10, 0xFF5C5C);
////            this.drawString(Minecraft.getMinecraft().fontRenderer, mc.debug.split(",", 2)[0].substring(0, 6), 5, 20, 0xFF5C5C);
////            this.drawString(Minecraft.getMinecraft().fontRenderer, "Direction : " + dire, 5, 30, 0xFF5C5C);
////            this.drawString(Minecraft.getMinecraft().fontRenderer, "GPS :", 5, 40, 0xFF5C5C);
////            this.drawString(Minecraft.getMinecraft().fontRenderer, "X: " + df.format(Minecraft.getMinecraft().player.posX) + " Y: " + df.format(Minecraft.getMinecraft().player.posY) + " Z: " + df.format(Minecraft.getMinecraft().player.posZ), 5, 50, 0xFF5C5C);
//            GlStateManager.pushMatrix();
//            getFont().drawString(5, 10, "WesterLife - Menu de Débug", org.newdawn.slick.Color.white);
//            getFont().drawString(5, 20, mc.debug.split(",", 2)[0].substring(0, 6), org.newdawn.slick.Color.white);
//            getFont().drawString(5, 30, "Direction : " + dire, org.newdawn.slick.Color.white);
//            getFont().drawString(5, 40, "GPS :", org.newdawn.slick.Color.white);
//            getFont().drawString(5, 50, "X: " + df.format(Minecraft.getMinecraft().player.posX) + " Y: " + df.format(Minecraft.getMinecraft().player.posY) + " Z: " + df.format(Minecraft.getMinecraft().player.posZ), org.newdawn.slick.Color.white);
//            GlStateManager.popMatrix();
//        }
//
//        if (event.getType() == RenderGameOverlayEvent.ElementType.EXPERIENCE || event.getType() == RenderGameOverlayEvent.ElementType.FOOD || event.getType() == RenderGameOverlayEvent.ElementType.HEALTH || event.getType() == RenderGameOverlayEvent.ElementType.HEALTH) {
//            event.setCanceled(true);
//
//        }
//    }
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

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void armorSuperpositor(ArmorEvent.Render event) {

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
    public void armorSuperpositor(TickEvent.RenderTickEvent event) {
        // get all players arround the player
        // check if player is ingame
        boolean isPlayerInGame = Minecraft.getMinecraft().player != null && Minecraft.getMinecraft().world != null;
        if (!isPlayerInGame) return;
        List<EntityPlayer> players = Minecraft.getMinecraft().world.playerEntities;
        players.forEach((entityPlayer -> {

        }));


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
        if (Keyboard.isKeyDown(Keyboard.KEY_F3)) {
            List<DynamXItemArmor<?>> globalitems = ForgeRegistries.ITEMS.getEntries().stream().filter(e -> e.getValue() instanceof DynamXItemArmor<?>).collect(Collectors.toCollection(ArrayList::new)).stream().map(e -> (DynamXItemArmor<?>) e.getValue()).collect(Collectors.toList());
            for (DynamXItemArmor<?> item : globalitems) {
                DynamXInit.fastRegistryAccess.put(item.getInfo().getFullName(), item);
                System.out.println("added " + item.getInfo().getFullName() + " to fast registry access");
            }
            Client.knowCharacters.clear();
            Client.knowPermis.clear();
            Client.waitForSomething.clear();
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_F7)) {
            Main.isOpti = !Main.isOpti;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_F12)) {
//            Main.getPlayerManager().loadTrack("http://193.38.250.14:8000/mix.m3u");
//            Main.getPlayerManager().getAudioPlayer().setVolume(100);
//            System.out.println(Main.getPlayerManager().getAudioPlayer().getPlayingTrack() + " / " + Main.getPlayerManager().getAudioPlayer().getVolume());
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
        Main.atmScreen = new AtmScreen();
        Main.atmScreen.openMenu();
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
            e.getEntityPlayer().playSound(SoundsHandler.BIP, 0.5f, 1f);


            Minecraft.getMinecraft().ingameGUI.setOverlayMessage("§cVitesse du Véhicule : ", true);

        */
