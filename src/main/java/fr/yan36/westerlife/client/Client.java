package fr.yan36.westerlife.client;

import fr.yan36.westerlife.Main;
import fr.yan36.westerlife.client.gui.mcef.BrowserScreen;
import fr.yan36.westerlife.common.utils.list.Warp;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.client.settings.KeyBinding;
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

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Client {

    // TODO: Apprendre à développer à _INeox.

    public static int needToCreateCharacter = 0;
    public static String animationState = "default";
    public static List<Warp> warplist = new ArrayList<>();

    @SubscribeEvent
    public void GuieventHandler(GuiOpenEvent e) {

        if (e.getGui() instanceof GuiMainMenu) {
            e.setCanceled(true);
            Main.browserScreen = new BrowserScreen("mod://westerlife/main_menu/main.html");
            Minecraft.getMinecraft().displayGuiScreen(Main.browserScreen);
            Main.browserScreen.openMenu();
        }

        if (e.getGui() instanceof GuiIngameMenu) {
            e.setCanceled(true);
            Main.browserScreen = new BrowserScreen("mod://westerlife/menu_echap/echap.html");
            Minecraft.getMinecraft().displayGuiScreen(Main.browserScreen);
            Main.browserScreen.openMenu();
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
            this.drawString(Minecraft.getMinecraft().fontRenderer, "WesterLife - Menu de Débug", 5, 10, 0xFF5C5C);
            this.drawString(Minecraft.getMinecraft().fontRenderer, mc.debug.split(",", 2)[0].substring(0, 6), 5, 20, 0xFF5C5C);
            this.drawString(Minecraft.getMinecraft().fontRenderer, "Direction : " + dire, 5, 30, 0xFF5C5C);
            this.drawString(Minecraft.getMinecraft().fontRenderer, "GPS :", 5, 40, 0xFF5C5C);
            this.drawString(Minecraft.getMinecraft().fontRenderer, "X: " + df.format(Minecraft.getMinecraft().player.posX) + " Y: " + df.format(Minecraft.getMinecraft().player.posY) + " Z: " + df.format(Minecraft.getMinecraft().player.posZ), 5, 50, 0xFF5C5C);
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

    private void keyAnimationTyped() {
        System.out.println("Ouverture du menu 24");
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
