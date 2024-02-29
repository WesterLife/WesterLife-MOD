package fr.gabidut76.westerlife.server;

import com.jme3.math.Vector3f;
import fr.dynamx.addons.basics.common.modules.LicensePlateModule;
import fr.dynamx.api.entities.VehicleEntityProperties;
import fr.dynamx.api.events.PhysicsEvent;
import fr.dynamx.api.events.VehicleEntityEvent;
import fr.dynamx.api.physics.BulletShapeType;
import fr.dynamx.api.physics.EnumBulletShapeType;
import fr.dynamx.common.entities.BaseVehicleEntity;
import fr.dynamx.common.entities.modules.SeatsModule;
import fr.dynamx.common.entities.modules.engines.BasicEngineModule;
import fr.dynamx.common.entities.vehicles.CarEntity;
import fr.gabidut76.westerlife.common.capabilities.playerstat.PlayerStatData;
import fr.gabidut76.westerlife.common.capabilities.playerstat.PlayerStatHandler;
import fr.nathanael2611.simpledatabasemanager.core.Databases;
import fr.gabidut76.westerlife.westercore.Main;
import fr.gabidut76.westerlife.common.blocks.dynamx.BlockComputer;
import fr.gabidut76.westerlife.common.init.DynamXInit;
import fr.gabidut76.westerlife.common.init.ItemInit;
import fr.gabidut76.westerlife.common.items.ItemCard;
import fr.gabidut76.westerlife.common.network.PacketAskToCreateCharacter;
import fr.gabidut76.westerlife.common.network.PacketOpenMcefGui;
import fr.gabidut76.westerlife.common.network.sync.PacketAnimationToAll;
import fr.gabidut76.westerlife.common.objects.PlayerHealth;
import fr.gabidut76.westerlife.common.utils.Animation;
import fr.gabidut76.westerlife.common.utils.carmodule.DamageCarModule;
import fr.gabidut76.westerlife.common.utils.carmodule.GarageModule;
import fr.gabidut76.westerlife.westerapi.api.NemesisLink;
import fr.gabidut76.westerlife.westerapi.bdd.DBUtils;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.io.IOException;
import java.util.*;


public class Serveur {

    public static HashMap<EntityPlayerMP, Animation> animations = new HashMap<>();

    public static HashMap<UUID, List<String>> superpositionServer = new HashMap<>();
    public static HashMap<EntityPlayer, Boolean> menottes = new HashMap<>();
    public static HashMap<Long, Entity> entityKillMap = new HashMap<>();
    @SubscribeEvent
    public void onConnectToServer(PlayerEvent.PlayerLoggedInEvent e) {
        System.out.println(e.player.getUniqueID());
    }


    @SubscribeEvent
    public void onDynxCollide(PhysicsEvent.PhysicsCollision e) {
//        if(e.getObject1().getType().equals(EnumBulletShapeType.VEHICLE) && e.getObject2().getType().equals(EnumBulletShapeType.VEHICLE)) {
//            BaseVehicleEntity<?> vehicle1 = (BaseVehicleEntity<?>) e.getCollisionInfo().getEntityA().getObjectIn();
//            BaseVehicleEntity<?> vehicle2 = (BaseVehicleEntity<?>) e.getCollisionInfo().getEntityB().getObjectIn();
//            System.out.println(vehicle1.getPhysicsHandler().getSpeed(BaseVehiclePhysicsHandler.SpeedUnit.KMH));
//            System.out.println(getSpeed(vehicle1));
//
//
//            System.out.println(vehicle1.getName() + " à percuté " + vehicle2.getName() + " à " + getSpeed(vehicle1) +" km/h");
//
//        }

//        System.out.println("Collision : " + e.getCollisionInfo().getCollisionEvent() + " " + e.getCollisionInfo().getEntityA() + " " + e.getCzaollisionInfo().getEntityB());
        if(e.getCollisionInfo().getEntityB().getType().equals(EnumBulletShapeType.TERRAIN)) {
            BulletShapeType<?> b = e.getCollisionInfo().getEntityB();

            if(e.getCollisionInfo().getEntityA().getType().equals(EnumBulletShapeType.VEHICLE)) {
                BaseVehicleEntity<?> vehicle = (BaseVehicleEntity<?>) e.getCollisionInfo().getEntityA().getObjectIn();

                Vector3f vel = vehicle.getPhysicsHandler().getLinearVelocity();

                if(!(vel.y < 0 && vel.y > -1)) {
                    if((vel.z > 5 || vel.z < -5) || (vel.x > 5 || vel.x < -5)) {
                        Entity player = vehicle.getModuleByType(SeatsModule.class).getControllingPassenger();


                        if(player == null) return;
                        player.sendMessage(new TextComponentString("§cVous avez percuté un obstacle à " + getSpeed(vehicle) + " km/h."));
                        player.sendMessage(new TextComponentString("Damage : " + (vel.z + vel.x) / 2));

                        if(vehicle.hasModuleOfType(DamageCarModule.class)) {
                            DamageCarModule damage = vehicle.getModuleByType(DamageCarModule.class);
                            damage.addPercentage(Math.abs((int) ((vel.z + vel.x) / 2)));
                            player.sendMessage(new TextComponentString("Total Damage : " + damage.getPercentage()));
                        } else {
                            player.sendMessage(new TextComponentString("§4Errored car."));
                        }
                    }
                }


            }
        }
    }

    @SubscribeEvent
    public void onDynxCollide(VehicleEntityEvent.ControllerUpdate<? extends fr.dynamx.api.entities.modules.IVehicleController> e) {
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

    @SubscribeEvent
    public void onCarDestroyed(LivingDeathEvent e) {
        if(e.getEntity() instanceof CarEntity) {
            CarEntity<?> car = (CarEntity<?>) e.getEntity();

            if(car.hasModuleOfType(LicensePlateModule.class)) {
                GarageModule module = car.getModuleByType(GarageModule.class);
                if(!Objects.equals(module.getOwner(), "0")) {
                    EntityPlayer player = e.getEntity().world.getPlayerEntityByUUID(UUID.fromString(module.getOwner()));
                    if(player != null) {
                        player.sendMessage(new TextComponentString("§cVotre voiture a été détruite."));
                    }
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
//        if(block instanceof BlockDistributeur) {
//            if(e.getEntityPlayer().getHeldItemMainhand().isItemEqual(new ItemStack(ItemInit.CARTE_BANCAIRE))) {
//                Main.network.sendTo(new PacketSyncPlayer(MethodesBDD.getNom(e.getEntityPlayer()), MethodesBDD.getPrenom(e.getEntityPlayer()), MethodesBDD.getSex(e.getEntityPlayer()), MethodesBDD.getDate(e.getEntityPlayer()), MethodesBDD.getArgent(e.getEntityPlayer()), MethodesBDD.getRIB(e.getEntityPlayer())), (EntityPlayerMP) e.getEntityPlayer());
//                Main.network.sendTo(new PacketOpenGUIAtm(MethodesBDD.getCodeCB(e.getEntityPlayer())), (EntityPlayerMP) e.getEntityPlayer());
//            } else {
//                e.getEntityPlayer().sendMessage(new TextComponentString("§cVeuillez insérer votre carte bancaire."));
//            }
//        }

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

    // on player take item from ground
    @SubscribeEvent
    public void onItemPickup(EntityItemPickupEvent e) {
        if(e.getEntity() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) e.getEntity();
            if(e.getItem().getItem().getItem().equals(ItemInit.CNI)) {
                MinecraftServer server = player.getServer();

                if(server == null) {
                    System.out.println("Server is null");
                    player.sendMessage(new TextComponentString("§cTf server is null ?"));

                    return;
                }

                assert server != null;
                if(server.getPlayerList().getPlayerByUUID(UUID.fromString(player.getHeldItem(EnumHand.MAIN_HAND).getTagCompound().getString("link"))).hasCapability(PlayerStatData.PlayerStatProvider.CAPABILITY, null)) {
                    PlayerStatHandler.sync(server.getPlayerList().getPlayerByUUID(UUID.fromString(player.getHeldItem(EnumHand.MAIN_HAND).getTagCompound().getString("link"))));
                } else {
                    System.out.println("No capability found for " + server.getPlayerList().getPlayerByUUID(UUID.fromString(player.getHeldItem(EnumHand.MAIN_HAND).getTagCompound().getString("link"))));
                    player.sendMessage(new TextComponentString("§cErreur: Contacter le staff (" + player.getHeldItem(EnumHand.MAIN_HAND).getTagCompound().getString("link") + " doesn't exists)."));
                }
            }
        }
    }

    @SubscribeEvent
    public void onTick(TickEvent.ServerTickEvent e) throws IOException {
        Long time = System.currentTimeMillis();

        for (Map.Entry<Long, Entity> entry : entityKillMap.entrySet()) {
            if(time - entry.getKey() > 1000) {
                if(entry.getValue() instanceof CarEntity<?>) {
                    String uid = NemesisLink.NEMESIS_API.getUserIdFromUUID(String.valueOf(((CarEntity<?>) entry.getValue()).getModuleByType(GarageModule.class).getOwner()));
                    NemesisLink.NEMESIS_API.setCarState(uid, entry.getValue().getUniqueID().toString(), "1");
                }
                System.out.println("ok3");
                entry.getValue().setDead();
                entityKillMap.remove(entry.getKey());
            }
        }
    }

    @SubscribeEvent
    public void onEntityKilledEvent(PhysicsEvent.PhysicsEntityRemoved e) throws IOException {
        Thread thread = new Thread(() -> {
            if(e.getPhysicsEntity() instanceof CarEntity<?>) {
                String uid = null;
                try {
                    uid = NemesisLink.NEMESIS_API.getUserIdFromUUID(String.valueOf(((CarEntity<?>) e.getPhysicsEntity()).getModuleByType(GarageModule.class).getOwner()));
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                try {
                    NemesisLink.NEMESIS_API.setCarState(uid, e.getPhysicsEntity().getUniqueID().toString(), "1");
                    EntityPlayer owner = e.getPhysicsEntity().world.getPlayerEntityByUUID(UUID.fromString(((CarEntity<?>) e.getPhysicsEntity()).getModuleByType(GarageModule.class).getOwner()));
                    assert owner != null;
                    String immaDetails = ((CarEntity<?>) e.getPhysicsEntity()).getModuleByType(LicensePlateModule.class).getPlate();
                    owner.sendMessage(new TextComponentString(TextFormatting.DARK_BLUE + "[INFO] " + TextFormatting.BLUE + " Votre voiture est de retour au garage. (" + immaDetails + ")"));
                    NemesisLink.NEMESIS_API.logDiscordData("1179441286365847612", "-> Voiture " + e.getPhysicsEntity().getName() + " de " + owner.getName() + "  detruite par un admin.");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        thread.start();
    }

    @SubscribeEvent
    public void onPlayerDisconnect(PlayerEvent.PlayerLoggedOutEvent e) {
        for (CarEntity<?> entity : e.player.getEntityWorld().getEntities(CarEntity.class, input -> input.hasModuleOfType(GarageModule.class))) {
            GarageModule module = entity.getModuleByType(GarageModule.class);
            if(module.getOwner().equals("0")) {
                return;
            }
            if(module.getOwner().equals(e.player.getUniqueID().toString())) {
                Long time = System.currentTimeMillis() + 300000;
                entityKillMap.put(time, entity);
            }
        }
    }

    @SubscribeEvent
    public void onControllerUpdate(VehicleEntityEvent.PlayerInteract e) {
        if(e.getEntity().hasModuleOfType(DamageCarModule.class)) {
            DamageCarModule damage = e.getEntity().getModuleByType(DamageCarModule.class);
        }
    }



}
