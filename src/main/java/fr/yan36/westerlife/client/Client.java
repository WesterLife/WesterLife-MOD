package fr.yan36.westerlife.client;

import com.jme3.math.Vector3f;
import fr.aym.acsguis.api.ACsGuiApi;
import fr.dynamx.client.DynamXModelRegistry;
import fr.dynamx.client.renders.model.ArmorRenderer;
import fr.dynamx.client.renders.model.ModelObjArmor;
import fr.dynamx.common.contentpack.DynamXObjectLoaders;
import fr.dynamx.common.contentpack.type.objects.ArmorObject;
import fr.dynamx.common.entities.BaseVehicleEntity;
import fr.dynamx.common.items.DynamXItemArmor;
import fr.dynamx.common.physics.entities.BaseVehiclePhysicsHandler;
import fr.dynamx.utils.client.DynamXRenderUtils;
import fr.yan36.westerlife.client.gui.*;
import fr.yan36.westerlife.client.gui.phone.CSSGuiPhone;
import fr.yan36.westerlife.common.items.WesterItem;
import fr.yan36.westerlife.common.registry.SoundsHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
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
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Objects;

public class Client {

    public static int create = 0;
    @SubscribeEvent
    public void GuieventHandler(GuiOpenEvent e) {

        if(e.getGui() instanceof GuiMainMenu){
            if(Minecraft.getMinecraft().getSession().getUsername().equals("gabidut76") || Minecraft.getMinecraft().getSession().getUsername().equals("_INeox")) {
                System.out.println("Salut pas BG");
            } else if(Minecraft.getMinecraft().getSession().getUsername().equals("yan36")) {
                ACsGuiApi.asyncLoadThenShowGui("mainmenu", CSSGuiMainMenu::new);
                System.out.println("T'es beau !");
            } else {
                //ACsGuiApi.asyncLoadThenShowGui("mainmenu", CSSGuiMainMenu::new);
            }
        }
        if (e.getGui() instanceof GuiIngameMenu) {
            ACsGuiApi.asyncLoadThenShowGui("ingamemenu", CSSGuiIngameMenu::new);
        }

        if (e.getGui() == null) {
            if(create == 1){
                ACsGuiApi.asyncLoadThenShowGui("createprofil", CSSGuiCreateProfil::new);
            }
        }

    }

    @SubscribeEvent
    public void InteractWithEntity(PlayerInteractEvent.EntityInteractSpecific e) {
        //e.getEntityPlayer().getHeldItemMainhand().isItemEqual(new ItemStack(WesterItem.CNI))
    }

    @SubscribeEvent
    public void InteractWithEntity(FMLNetworkEvent.ClientConnectedToServerEvent e) {
        System.out.println(e.getConnectionType());
    }


    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onRenderPre(RenderGameOverlayEvent.Pre event)
    {
        if(event.getType() == RenderGameOverlayEvent.ElementType.DEBUG)
        {


            Minecraft mc = Minecraft.getMinecraft();
            event.setCanceled(true);

            EnumFacing orientation = mc.player.getHorizontalFacing();
            int dir = Math.round(orientation.getHorizontalAngle());
            String dire;
            switch (dir){
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

        if(event.getType() == RenderGameOverlayEvent.ElementType.EXPERIENCE){
            event.setCanceled(true);
        }
    }

    public void drawString(FontRenderer fontRenderer, String str, int x, int y, int color)
    {
        fontRenderer.drawStringWithShadow(str, x, y, color);
    }

    @SubscribeEvent
    public void renderPseudo(RenderLivingEvent.Specials.Pre e) {

        if (!(Minecraft.getMinecraft().player.isCreative())) {
            e.setCanceled(true);
        }
        System.out.println(Minecraft.getMinecraft().gameSettings.thirdPersonView);
        if(Minecraft.getMinecraft().gameSettings.thirdPersonView != 2) {
            ModelObjArmor a = Objects.requireNonNull(DynamXObjectLoaders.ARMORS.findInfo("westerpack.armor_pompierinterventionbleue")).getObjArmor();
            GlStateManager.pushMatrix();
            GL11.glTranslatef(0.0F, 2.0F, 0.0F);
            GL11.glRotatef(180.0F, 0.0F, 1.0F, 1.0F);
            GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
            a.render(e.getEntity(),a.swingProgress, 1.0F, 1.0F, e.getEntity().rotationYaw, e.getEntity().rotationPitch, 1.0F);
            GlStateManager.popMatrix();
        }
    }

    @SubscribeEvent
    public void onClickItem(PlayerInteractEvent.RightClickItem e){
        if(e.getItemStack().isItemEqual(new ItemStack(WesterItem.DISC_MARSEILLAISE))){
            e.getEntityPlayer().playSound(SoundsHandler.MARSEILLAISE, 0.8f,1f);
        }
    }

    private static KeyBinding keyBindTest;

    public Client()
    {
        System.out.println("WesterClient");
        FMLCommonHandler.instance().bus().register(this);
        MinecraftForge.EVENT_BUS.register(this);
        keyBindTest = new KeyBinding("westerlife.admin", Keyboard.KEY_F9, "");
        ClientRegistry.registerKeyBinding(keyBindTest);
    }

    @SubscribeEvent
    public void onEvent(InputEvent.KeyInputEvent event)
    {
        if(keyBindTest.isPressed())
        {
            keyTestTyped();
        }
    }

    @SubscribeEvent
    public void onInteractEvent(PlayerInteractEvent.EntityInteract event)
    {
        System.out.println("target");
        Entity Target = event.getTarget();
        System.out.println(Target);
        if (Target instanceof BaseVehicleEntity) {
            BaseVehiclePhysicsHandler<?> physicsHandler = ((BaseVehicleEntity<?>) Target).physicsHandler;
            float speed = physicsHandler.getSpeed(BaseVehiclePhysicsHandler.SpeedUnit.KMH);
            System.out.println(speed);
            Minecraft.getMinecraft().ingameGUI.setOverlayMessage("§cVitesse du Véhicule : " + speed, true);
        }
    }

    private void keyTestTyped() {
            ACsGuiApi.asyncLoadThenShowGui("phone", CSSGuiPhone::new);
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
