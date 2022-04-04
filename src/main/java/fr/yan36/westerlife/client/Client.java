package fr.yan36.westerlife.client;


import fr.dynamx.api.audio.IDynamXSound;
import fr.dynamx.api.contentpack.DynamXAddon;
import fr.dynamx.api.contentpack.object.subinfo.SubInfoTypeOwner;
import fr.dynamx.api.events.VehicleEntityEvent;
import fr.dynamx.client.sound.DynamXSoundHandler;
import fr.dynamx.common.DynamXMain;
import fr.dynamx.common.entities.BaseVehicleEntity;
import fr.dynamx.common.physics.entities.BaseVehiclePhysicsHandler;
import fr.dynamx.common.physics.entities.EntityPhysicsHandler;
import fr.dynamx.server.network.DynamXServerNetworkSystem;
import fr.dynamx.utils.DynamXUtils;
import fr.dynamx.utils.debug.DynamXDebugOptions;
import fr.yan36.westerlife.Discord;
import fr.yan36.westerlife.Main;
import fr.yan36.westerlife.client.gui.*;
import fr.yan36.westerlife.common.blocks.BlockATM;
import fr.yan36.westerlife.common.blocks.WesterBlocks;
import fr.yan36.westerlife.common.items.WesterItem;
import fr.yan36.westerlife.common.registry.SoundsHandler;
import fr.yan36.westerlife.serveur.bdd.MethodesBDD;
import ibxm.Player;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBanner;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.IInteractionObject;
import net.minecraftforge.client.GuiIngameForge;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.util.Iterator;
import java.util.List;

public class Client {
    public static int create = 0;
    @SubscribeEvent
    public void GuieventHandler(GuiOpenEvent e) {

        if(e.getGui() instanceof GuiMainMenu){
            if(Minecraft.getMinecraft().getSession().getUsername().equals("gabidut76") || Minecraft.getMinecraft().getSession().getUsername().equals("_INeox")) {
                System.out.println("Salut pas BG");
            } else if(Minecraft.getMinecraft().getSession().getUsername().equals("yan36")) {
                //e.setGui(new CSSGuiMainMenu().getGuiScreen());
                System.out.println("T'es beau !");
            } else {
                //e.setGui(new CSSGuiMainMenu().getGuiScreen());
            }
        }
        if (e.getGui() instanceof GuiIngameMenu) {
            e.setGui(new CSSGuiIngameMenu().getGuiScreen());
        }
        
        if (e.getGui() == null) {
            if(create == 1){
                e.setGui(new CSSGuiCreateProfil().getGuiScreen());
            }
        }

    }

    @SubscribeEvent
    public void InteractWithEntity(PlayerInteractEvent.EntityInteractSpecific e) {
        //e.getEntityPlayer().getHeldItemMainhand().isItemEqual(new ItemStack(WesterItem.CNI))
    }

    @SubscribeEvent
    public void renderPseudo(RenderLivingEvent.Specials.Pre e){

        if(!(Minecraft.getMinecraft().player.isCreative())){
            e.setCanceled(true);
        }

    }

    @SubscribeEvent
    public void onClickItem(PlayerInteractEvent.RightClickItem e){
        if(e.getItemStack().isItemEqual(new ItemStack(WesterItem.DISC_MARSEILLAISE))){
            e.getEntityPlayer().playSound(SoundsHandler.MARSEILLAISE, 0.8f,1f);

        }
    }

    @SubscribeEvent
    public void onRightClickBlock(PlayerInteractEvent.RightClickBlock e){
        if(e.getEntityPlayer().getHeldItemMainhand().isItemEqual(new ItemStack(WesterItem.CB))){

            Minecraft.getMinecraft().displayGuiScreen(new CSSGuiAtm().getGuiScreen());
        }
    }

    private static KeyBinding keyBindTest;

    public Client()
    {
        FMLCommonHandler.instance().bus().register(this);
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

    private void keyTestTyped() {
            //if (Minecraft.getMinecraft().getSession().getUsername().equals("yan36")) {
                Minecraft.getMinecraft().displayGuiScreen(new CSSGuiGendarmerie().getGuiScreen());
        //}
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
