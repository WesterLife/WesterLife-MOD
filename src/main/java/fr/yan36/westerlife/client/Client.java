package fr.yan36.westerlife.client;

import com.mrcrayfish.obfuscate.client.event.ModelPlayerEvent;
import fr.yan36.westerlife.Main;
import fr.yan36.westerlife.client.gui.mcef.BrowserScreen;
import fr.yan36.westerlife.common.utils.Animation;
import fr.yan36.westerlife.common.utils.list.Warp;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.util.ResourceLoader;
import scala.Int;

import java.awt.*;
import java.io.InputStream;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Client {

    // TODO: Apprendre à développer à _INeox.

    public static int needToCreateCharacter = 0;

    public static HashMap<Integer, Animation> animationState = new HashMap<>();
    public static List<Warp> warplist = new ArrayList<>();

    public static String openScreenMcef = "none";

    public static void setScreenMcef(String screenName) {
        openScreenMcef = screenName;
    }

    @SubscribeEvent
    public void setupPlayerRotations(ModelPlayerEvent.SetupAngles event) {
        animationState.forEach((id, animation) -> {
            if (event.getEntityPlayer().getEntityId() == id) {
                animatePlayer(event.getEntityPlayer(), event.getModelPlayer());
            }
        });

    }

    private void animatePlayer(EntityPlayer ep, ModelBiped modelBiped) {

        animationState.putIfAbsent(ep.getEntityId(), Animation.NONE);
        if(animationState.equals(Animation.HANDS_UP)) {
            modelBiped.bipedRightArm.rotateAngleX = (float) Math.toRadians(-180);
            modelBiped.bipedLeftArm.rotateAngleX = (float) Math.toRadians(-180);
        }
    }

    @SubscribeEvent
    public void GuieventHandler(GuiOpenEvent e) {

        if (e.getGui() instanceof GuiMainMenu && !Main.isEnvDev) {
            // prout c'est chiant pour dev donc
            e.setCanceled(true);
            Main.browserScreen = new BrowserScreen("mod://westerlife/main_menu/main.html");
            Minecraft.getMinecraft().displayGuiScreen(Main.browserScreen);
            Main.browserScreen.openMenu();
        }

        if (e.getGui() instanceof GuiIngameMenu) {
            e.setCanceled(true);
            Main.browserScreen = new BrowserScreen("mod://westerlife/menu_echap/echap.html");
            Main.browserScreen.openMenu();
            setScreenMcef("ingamemenu");
        }

        if (e.getGui() == null) {
        }

    }

    @SubscribeEvent
    public void InteractWithEntity(FMLNetworkEvent.ClientConnectedToServerEvent e) {
    }

    @SubscribeEvent
    public void onTickEvent(TickEvent.ClientTickEvent event) {
            if (needToCreateCharacter == 1) {
                Main.browserScreen = new BrowserScreen("mod://westerlife/create_perso/perso1.html");
                Main.browserScreen.openMenu();
                needToCreateCharacter = 2;
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
                    Main.browserScreen = new BrowserScreen("mod://westerlife/animations/index.html");
                    Main.browserScreen.openMenu();
                    openScreenMcef = "none";
                    break;
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

            DecimalFormat df = new DecimalFormat("#.##");
            df.setRoundingMode(RoundingMode.HALF_UP);
//            this.drawString(Minecraft.getMinecraft().fontRenderer, "WesterLife - Menu de Débug", 5, 10, 0xFF5C5C);
//            this.drawString(Minecraft.getMinecraft().fontRenderer, mc.debug.split(",", 2)[0].substring(0, 6), 5, 20, 0xFF5C5C);
//            this.drawString(Minecraft.getMinecraft().fontRenderer, "Direction : " + dire, 5, 30, 0xFF5C5C);
//            this.drawString(Minecraft.getMinecraft().fontRenderer, "GPS :", 5, 40, 0xFF5C5C);
//            this.drawString(Minecraft.getMinecraft().fontRenderer, "X: " + df.format(Minecraft.getMinecraft().player.posX) + " Y: " + df.format(Minecraft.getMinecraft().player.posY) + " Z: " + df.format(Minecraft.getMinecraft().player.posZ), 5, 50, 0xFF5C5C);
            GlStateManager.pushMatrix();
            getFont().drawString(5, 10, "WesterLife - Menu de Débug", org.newdawn.slick.Color.white);
            getFont().drawString(5, 20, mc.debug.split(",", 2)[0].substring(0, 6), org.newdawn.slick.Color.white);
            getFont().drawString(5, 30, "Direction : " + dire, org.newdawn.slick.Color.white);
            getFont().drawString(5, 40, "GPS :", org.newdawn.slick.Color.white);
            getFont().drawString(5, 50, "X: " + df.format(Minecraft.getMinecraft().player.posX) + " Y: " + df.format(Minecraft.getMinecraft().player.posY) + " Z: " + df.format(Minecraft.getMinecraft().player.posZ), org.newdawn.slick.Color.white);
            GlStateManager.popMatrix();
        }

        if (event.getType() == RenderGameOverlayEvent.ElementType.EXPERIENCE || event.getType() == RenderGameOverlayEvent.ElementType.FOOD || event.getType() == RenderGameOverlayEvent.ElementType.HEALTH || event.getType() == RenderGameOverlayEvent.ElementType.HEALTH) {
            event.setCanceled(true);

        }
    }

    public void drawString(FontRenderer fontRenderer, String str, int x, int y, int color) {
        fontRenderer.drawStringWithShadow(str, x, y, color);
    }

    @SubscribeEvent
    public void renderPseudo(RenderLivingEvent.Specials.Pre e) {

        if (!(Minecraft.getMinecraft().player.isCreative())) {
            e.setCanceled(true);
        }

    }

    @SubscribeEvent
    public void onClickItem(PlayerInteractEvent.RightClickItem e) {
    }

    public static KeyBinding keyBindTest;
    public static KeyBinding keyBindAnimation;

    public Client() {
        FMLCommonHandler.instance().bus().register(this);
        MinecraftForge.EVENT_BUS.register(this);
        keyBindTest = new KeyBinding("westerlife.admin", Keyboard.KEY_F9, "westerlife.category");
        keyBindAnimation = new KeyBinding("westerlife.animation", Keyboard.KEY_F4, "westerlife.keybind");
        ClientRegistry.registerKeyBinding(keyBindTest);
        ClientRegistry.registerKeyBinding(keyBindAnimation);
    }

    @SubscribeEvent
    public void onEvent(InputEvent.KeyInputEvent event) {
        if (keyBindTest.isPressed()) {
            keyTestTyped();
        }

        if (keyBindAnimation.isPressed()) {
            keyAnimationTyped();
        }
    }

    @SubscribeEvent
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

    private void keyTestTyped() {
        //ACsGuiApi.asyncLoadThenShowGui("gendarmerie", CSSGuiGendarmerieLogin::new);
        Main.browserScreen = new BrowserScreen();
        Main.browserScreen.openMenu();
        //Main.browserScreen.executeJS("window.vue.setWindowF4('test', 'test');");
        System.out.println("Ouverture du menu");

    }

    private static void keyAnimationTyped() {
        openScreenMcef = "animations";
    }

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
