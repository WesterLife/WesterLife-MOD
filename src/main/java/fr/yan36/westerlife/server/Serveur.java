package fr.yan36.westerlife.server;

import fr.dynamx.api.entities.VehicleEntityProperties;
import fr.dynamx.api.events.PhysicsEvent;
import fr.dynamx.api.events.VehicleEntityEvent;
import fr.dynamx.api.physics.EnumBulletShapeType;
import fr.dynamx.common.entities.BaseVehicleEntity;
import fr.dynamx.common.entities.modules.BasicEngineModule;
import fr.dynamx.common.physics.entities.BaseVehiclePhysicsHandler;
import fr.nathanael2611.simpledatabasemanager.core.Databases;
import fr.yan36.westerlife.Main;
import fr.yan36.westerlife.common.blocks.dynamx.BlockComputer;
import fr.yan36.westerlife.common.blocks.dynamx.BlockDistributeur;
import fr.yan36.westerlife.common.init.DynamXInit;
import fr.yan36.westerlife.common.init.ItemInit;
import fr.yan36.westerlife.common.items.ItemCard;
import fr.yan36.westerlife.common.network.PacketAnimationToAll;
import fr.yan36.westerlife.common.network.PacketAskToCreateCharacter;
import fr.yan36.westerlife.common.network.PacketOpenMcefGui;
import fr.yan36.westerlife.common.network.old.PacketOpenGUIAtm;
import fr.yan36.westerlife.common.network.old.PacketSyncPlayer;
import fr.yan36.westerlife.common.objects.PlayerHealth;
import fr.yan36.westerlife.common.utils.Animation;
import fr.yan36.westerlife.server.bdd.DBUtils;
import fr.yan36.westerlife.server.bdd.MethodesBDD;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.management.PlayerInteractionManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.entity.item.ItemEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.*;


public class Serveur {

    public static HashMap<EntityPlayerMP, Animation> animations = new HashMap<>();

    public static HashMap<UUID, List<String>> superpositionServer = new HashMap<>();
    public static HashMap<EntityPlayer, Boolean> menottes = new HashMap<>();
    @SubscribeEvent
    public void onConnectToServer(PlayerEvent.PlayerLoggedInEvent e) {
        boolean devmod = false;
        if(!devmod) {
            if(!DBUtils.getCharacterExists(e.player)){
                Main.network.sendTo(new PacketAskToCreateCharacter(), (EntityPlayerMP) e.player);
                e.player.sendMessage(new TextComponentString("§cVous n'avez pas de personnage, veuillez en créer un."));
                Databases.getPlayerData(e.player).setFloat("watervalue", 100f);
            }
        }

        if(!Databases.getPlayerData(e.player).contains("watervalue")) {
            Databases.getPlayerData(e.player).setFloat("watervalue", 100f);
        }

        if(!e.player.getEntityData().hasKey("health")) {
            e.player.getEntityData().setString("health", new PlayerHealth(Collections.emptyList(), Collections.emptyList()).toString());
        }

    }


    @SubscribeEvent
    public void onDynxCollide(PhysicsEvent.PhysicsCollision e) {
        if(e.getObject1().getType().equals(EnumBulletShapeType.VEHICLE) && e.getObject2().getType().equals(EnumBulletShapeType.VEHICLE)) {
            BaseVehicleEntity<?> vehicle1 = (BaseVehicleEntity<?>) e.getCollisionInfo().getEntityA().getObjectIn();
            BaseVehicleEntity<?> vehicle2 = (BaseVehicleEntity<?>) e.getCollisionInfo().getEntityB().getObjectIn();
            System.out.println(vehicle1.getPhysicsHandler().getSpeed(BaseVehiclePhysicsHandler.SpeedUnit.KMH));
            System.out.println(getSpeed(vehicle1));


            System.out.println(vehicle1.getName() + " à percuté " + vehicle2.getName() + " à " + getSpeed(vehicle1) +" km/h");

        }
    }

    @SubscribeEvent
    public void onDynxCollide(VehicleEntityEvent.ControllerUpdate e) {
        System.out.println("c");
    }
    @SubscribeEvent
    public void onPlayerFall(LivingFallEvent e) {
        if(e.getEntityLiving() instanceof EntityPlayer) {
            PlayerHealth health = PlayerHealth.getFromString(e.getEntityLiving().getEntityData().getString("health"));
            if(e.getDistance() > 5) {
                health.addBrokenPart(PlayerHealth.PlayerParts.JAMBE_DROITE);
                health.addBrokenPart(PlayerHealth.PlayerParts.JAMBE_GAUCHE);
            }
            e.getEntityLiving().getEntityData().setString("health", health.toString());
        }
    }

    @SubscribeEvent
    public void on(TickEvent.PlayerTickEvent e) {
        Databases.getPlayerData(e.player).setFloat("watervalue", Databases.getPlayerData(e.player).getFloat("watervalue") - 0.00005f);
        if(e.player.isSprinting()) {
            Databases.getPlayerData(e.player).setFloat("watervalue", Databases.getPlayerData(e.player).getFloat("watervalue") - 0.005f);
        }
        if(e.player.isBurning()) {
            Databases.getPlayerData(e.player).setFloat("watervalue", Databases.getPlayerData(e.player).getFloat("watervalue") - 0.005f);
        }
    }

    @SubscribeEvent
    public void cardProtection(PlayerContainerEvent e) {
        if(!(e.getContainer() instanceof ContainerPlayer)) {
            for (int i = 0; i < e.getContainer().getInventory().size(); i++) {
                ItemStack stack = e.getContainer().getInventory().get(i);
                if(stack.getItem() instanceof ItemCard) {
                    e.getContainer().getInventory().set(i, ItemStack.EMPTY);
                }
            }
        }
    }

//    @SubscribeEvent
//    public void radarHandler(PhysicsEvent.StepSimulation e) {
//        if(e.getPhysicsWorld().getTerrainManager().getWorld().getWorldTime() % 20 == 0) {
//
//        }
//    }

    //TODO: Make staff unmenottable
    //TODO: disable interaction with other blocks & find a way to disable jump better than jump boost
    @SubscribeEvent
    public void onRightClickPlayer(PlayerInteractEvent.EntityInteract e) {
        if(e.getEntityPlayer().getHeldItemMainhand().getItem() == DynamXInit.Menottes.getItem()) {
            Entity et = e.getTarget();
            EntityPlayer target = et instanceof EntityPlayer ? (EntityPlayer) et : null;
            //TODO: Set range to 2 blocks
            assert target != null;
            if(Serveur.menottes.containsKey(target)) {
                target.clearActivePotions();
                Main.network.sendToAll(new PacketAnimationToAll(Animation.NONE.getId(), target.getEntityId()));
                target.sendMessage(new TextComponentString("Vous avez été démenotté."));
                Serveur.menottes.remove(target);
            } else {
                Main.network.sendToAll(new PacketAnimationToAll(Animation.MENOTTE.getId(), target.getEntityId()));
                target.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 1000000, 100));
                // disable jump
                target.addPotionEffect(new PotionEffect(MobEffects.JUMP_BOOST, 1000000, -100));
                target.sendMessage(new TextComponentString("Vous avez été menotté."));
                Serveur.menottes.put(target, true);
            }
        }
    }

    @SubscribeEvent
    public void onRightClick(PlayerInteractEvent.RightClickBlock e){
        BlockPos blockPos = e.getPos();
        Block block = e.getWorld().getBlockState(blockPos).getBlock();
        if(block instanceof BlockDistributeur) {
            if(e.getEntityPlayer().getHeldItemMainhand().isItemEqual(new ItemStack(ItemInit.CARTE_BANCAIRE))) {
                Main.network.sendTo(new PacketSyncPlayer(MethodesBDD.getNom(e.getEntityPlayer()), MethodesBDD.getPrenom(e.getEntityPlayer()), MethodesBDD.getSex(e.getEntityPlayer()), MethodesBDD.getDate(e.getEntityPlayer()), MethodesBDD.getArgent(e.getEntityPlayer()), MethodesBDD.getRIB(e.getEntityPlayer())), (EntityPlayerMP) e.getEntityPlayer());
                Main.network.sendTo(new PacketOpenGUIAtm(MethodesBDD.getCodeCB(e.getEntityPlayer())), (EntityPlayerMP) e.getEntityPlayer());
            } else {
                e.getEntityPlayer().sendMessage(new TextComponentString("§cVeuillez insérer votre carte bancaire."));
            }
        }

        if (block instanceof BlockComputer) {
            Main.network.sendTo(new PacketOpenMcefGui("computer"), (EntityPlayerMP) e.getEntityPlayer());
        }
    }

    public static int getSpeed(BaseVehicleEntity<?> entity) {
        if (entity == null) {
            return -1;
        }
        BasicEngineModule engine = entity.getModuleByType(BasicEngineModule.class);
        if (engine != null) {
            float[] ab = engine.getEngineProperties();
            if (ab == null) return 0;
            return (int) Math.abs(ab[VehicleEntityProperties.EnumEngineProperties.SPEED.ordinal()]);
        }
        return -1;
    }


}
