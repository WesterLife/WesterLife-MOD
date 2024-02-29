package fr.gabidut76.westerlife.common.capabilities.playergarage;

import fr.gabidut76.westerlife.westercore.Main;
import fr.gabidut76.westerlife.common.capabilities.packets.PacketSyncGarage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.Objects;

@Mod.EventBusSubscriber
public class PlayerGarageCapability {
    @CapabilityInject(IPlayerGarage.class)
    public static final Capability<IPlayerGarage> CAPABILITY;

    static {
        CAPABILITY = null;
    }


    @SubscribeEvent
    public void onPlayerJoin(final EntityJoinWorldEvent e) {
        if (!(e.getWorld().isRemote)) {
            if (!(e.getEntity() instanceof EntityPlayer)) {
                return;
            }
            final EntityPlayer target = (EntityPlayer) e.getEntity();

            System.out.println("Synced garage");
            sync(target, Collections.singletonList(target));
        }
    }

    public static void sync(final EntityPlayer entity, final Iterable<EntityPlayer> receivers) {
        if (entity.hasCapability(CAPABILITY, null)) {

            final PacketSyncGarage packet = new PacketSyncGarage(entity, Objects.requireNonNull(entity.getCapability(CAPABILITY, null)).getCars());
            receivers.forEach(player -> {
                System.out.println("Synced garage to : " + player.getName());
                Main.network.sendTo(packet, (EntityPlayerMP) player);
            });
        }
    }

    public static class Storage implements Capability.IStorage<IPlayerGarage> {

        @Nullable
        @Override
        public NBTBase writeNBT(Capability<IPlayerGarage> capability, IPlayerGarage instance, EnumFacing side) {
            return null;
        }

        @Override
        public void readNBT(Capability<IPlayerGarage> capability, IPlayerGarage instance, EnumFacing side, NBTBase nbt) {

        }
    }

}
