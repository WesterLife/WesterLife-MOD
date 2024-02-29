package fr.gabidut76.westerlife.common.capabilities.playerstat;

import fr.gabidut76.westerlife.common.capabilities.packets.PacketSyncPlayerStats;
import fr.gabidut76.westerlife.common.objects.character.Character;
import fr.gabidut76.westerlife.common.utils.Animation;
import fr.gabidut76.westerlife.westerapi.api.NemesisLink;
import fr.gabidut76.westerlife.westercore.Main;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.io.IOException;
import java.util.Objects;
import java.util.logging.Logger;

@Mod.EventBusSubscriber(modid = Main.MODID)
public class PlayerStatHandler {
    public static Logger AUTH_LOGGER = Logger.getLogger("WesterAuth");

    Thread providerThread;


    @SubscribeEvent
    public void onPlayerJoin(final PlayerEvent.PlayerLoggedInEvent e) {
        if(FMLCommonHandler.instance().getSide().isClient()) return;
        if (!(e.player instanceof EntityPlayer)) {
            return;
        }
        EntityPlayer entityPlayer = (EntityPlayer) e.player;
        providerThread = new Thread(() -> {


            AUTH_LOGGER.info("Starting AUTH for player: " + e.player.getName() + " in thread " + Thread.currentThread().getName() + " / " + providerThread.getName());

            final EntityPlayer target = (EntityPlayer) e.player;

            Character c;
            try {
                c = NemesisLink.NEMESIS_API.getCharacterByUserUUID(target.getUniqueID());
            } catch (IOException ex) {
                AUTH_LOGGER.severe("Error while getting character for player: " + target);
                EntityPlayerMP player = (EntityPlayerMP) target;
                player.connection.disconnect(new TextComponentString("Please wait..."));
                providerThread.interrupt();
                throw new RuntimeException(ex);

            }

            if (c.getNationality().equals("null")) {
                AUTH_LOGGER.info("Kicking player (2): " + target);
                System.out.println("Kicking player: " + target);
                EntityPlayerMP player = (EntityPlayerMP) target;
                player.connection.disconnect(new TextComponentString("Please wait..."));
                providerThread.interrupt();
            }

            if (target.hasCapability(PlayerStatData.PlayerStatProvider.CAPABILITY, null)) {
                IPlayerStat stat = Objects.requireNonNull(target.getCapability(PlayerStatData.PlayerStatProvider.CAPABILITY, null));
                stat.setCharacter(c);
                System.out.println("set char");
            } else {
                System.out.println("Player don't have char");
            }
            if(Objects.requireNonNull(e.player.getCapability(PlayerStatData.PlayerStatProvider.CAPABILITY, null)).getAnimation() == null) {
                System.out.println("Setting animation to NONE");
                Objects.requireNonNull(e.player.getCapability(PlayerStatData.PlayerStatProvider.CAPABILITY, null)).setAnimation(Animation.NONE);
            }

            System.out.println(c.getUuid());
            System.out.println(c.getNationality());
            System.out.println(c.getLastName());
            System.out.println(c.getFirstNames());
            System.out.println(c.getRelatedBankAccount());
            System.out.println(c.getGender());
            System.out.println(c.getBirthDate());
            System.out.println(c.getBirthPlace());

            System.out.println(e.player.getCapability(PlayerStatData.PlayerStatProvider.CAPABILITY, null).getCharacter());

            sync(e.player);
            AUTH_LOGGER.info("Player " + target.getName() + " has been authenticated");

        });

        Thread.UncaughtExceptionHandler h = (th, ex) -> {
            final EntityPlayer target = (EntityPlayer) e.player;
            AUTH_LOGGER.severe("Error while getting character for player: " + target);
            ex.printStackTrace();
            EntityPlayerMP player = (EntityPlayerMP) target;
            player.connection.disconnect(new TextComponentString("Please wait..."));
            providerThread.interrupt();
            throw new RuntimeException(ex);
        };

        providerThread.setUncaughtExceptionHandler(h);



        if (!FMLCommonHandler.instance().getSide().isClient()) providerThread.start();
        AUTH_LOGGER.info("Thread started");
    }

    @SubscribeEvent
    public void onPlayerRespawn(final PlayerEvent.PlayerRespawnEvent e) {
        sync(e.player);
    }

    @SubscribeEvent
    public void timer(TickEvent.PlayerTickEvent e) {

        if (e.phase == TickEvent.Phase.END && e.player.ticksExisted % 20 == 0 && e.side.isServer()) {
            try {
                sync(e.player);
            } catch (Exception ex) {
                AUTH_LOGGER.severe("Error while syncing player: " + e.player);
                throw new RuntimeException(ex);
            }
        }
    }

    public static void sync(EntityPlayer entity) {

        Main.network.sendTo(new PacketSyncPlayerStats(entity.getEntityId(), Objects.requireNonNull(entity.getCapability(PlayerStatData.PlayerStatProvider.CAPABILITY, null)).getAnimation(), Objects.requireNonNull(entity.getCapability(PlayerStatData.PlayerStatProvider.CAPABILITY, null)).getCharacter()), (EntityPlayerMP) entity);
        Main.network.sendToAllTracking(new PacketSyncPlayerStats(entity.getEntityId(), Objects.requireNonNull(entity.getCapability(PlayerStatData.PlayerStatProvider.CAPABILITY, null)).getAnimation(), Objects.requireNonNull(entity.getCapability(PlayerStatData.PlayerStatProvider.CAPABILITY, null)).getCharacter()), entity);
    }
}
