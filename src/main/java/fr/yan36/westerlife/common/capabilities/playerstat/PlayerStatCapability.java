package fr.yan36.westerlife.common.capabilities.playerstat;

import fr.yan36.westerlife.Main;
import fr.yan36.westerlife.common.capabilities.packets.PacketSyncPlayerStats;
import fr.yan36.westerlife.common.objects.character.Character;
import fr.yan36.westerlife.common.utils.Animation;
import fr.yan36.westerlife.server.api.NemesisLink;
import net.minecraft.command.CommandServerKick;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.Objects;

@Mod.EventBusSubscriber
public class PlayerStatCapability {
    @CapabilityInject(IPlayerStat.class)
    public static final Capability<IPlayerStat> CAPABILITY;

    static {
        CAPABILITY = null;
    }

    Thread thread;

    @SubscribeEvent
    public void onPlayerJoin(final EntityJoinWorldEvent e) {
        if (!(e.getWorld().isRemote)) {
            if (!(e.getEntity() instanceof EntityPlayer)) {
                return;
            }
            thread = new Thread(() -> {
                final EntityPlayer target = (EntityPlayer) e.getEntity();

                Character c = null;
                try {
                    c = NemesisLink.NEMESIS_API.getCharacterByUserUUID(target.getUniqueID());
                } catch (IOException ex) {
                    System.out.println("Kicking player: " + target);
                    System.out.println(ex);
                    EntityPlayerMP player = (EntityPlayerMP) target;
                    player.connection.disconnect(new TextComponentString("Please wait..."));
                    thread.interrupt();
                    throw new RuntimeException(ex);

                }

                if(c.getNationality().equals("null")) {
                    System.out.println("Kicking player: " + target);
                    EntityPlayerMP player = (EntityPlayerMP) target;
                    player.connection.disconnect(new TextComponentString("Please wait..."));
                    thread.interrupt();
                }

                if(target.hasCapability(CAPABILITY, null)) {
                    IPlayerStat stat = Objects.requireNonNull(target.getCapability(CAPABILITY, null));
                    stat.setCharacter(c);
                }

                sync(target, e.getWorld().playerEntities);
            });

            Thread.UncaughtExceptionHandler h = (th, ex) -> {
                final EntityPlayer target = (EntityPlayer) e.getEntity();
                EntityPlayerMP player = (EntityPlayerMP) target;
                player.connection.disconnect(new TextComponentString("Please wait..."));
                thread.interrupt();
                throw new RuntimeException(ex);
            };

            thread.setUncaughtExceptionHandler(h);



            thread.start();
        }
    }

    public static void sync(final EntityPlayer entity, final Iterable<EntityPlayer> receivers) {
        if (entity.hasCapability(CAPABILITY, null)) {
            IPlayerStat stat = Objects.requireNonNull(entity.getCapability(CAPABILITY, null));
            System.out.println("Syncing char: " + stat.getCharacter());
            System.out.println("Syncing for entity: " + entity);

            final PacketSyncPlayerStats packet = new PacketSyncPlayerStats(entity.getEntityId(), stat.getAnimation(), stat.getCharacter());

            receivers.forEach(player -> {
                System.out.println("Syncing for player: " + player);
                Main.network.sendTo(packet, (EntityPlayerMP) player);
            });
        }
    }

    public static class Storage implements Capability.IStorage<IPlayerStat> {

        @Nullable
        @Override
        public NBTBase writeNBT(Capability<IPlayerStat> capability, IPlayerStat instance, EnumFacing side) {
            NBTTagCompound nbt = new NBTTagCompound();
            nbt.setInteger("animation", instance.getAnimation().getId());
            return nbt;
        }

        @Override
        public void readNBT(Capability<IPlayerStat> capability, IPlayerStat instance, EnumFacing side, NBTBase nbt) {
            instance.setAnimation(Animation.getAnimationById(((NBTTagCompound) nbt).getInteger("animation")));
        }
    }

}
