package fr.yan36.westerlife.common.capabilities.playerstat;

import fr.yan36.westerlife.Main;
import fr.yan36.westerlife.common.capabilities.packets.PacketSyncAnimation;
import fr.yan36.westerlife.common.utils.Animation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
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
public class PlayerStatCapability {
    @CapabilityInject(IPlayerStat.class)
    public static final Capability<IPlayerStat> CAPABILITY;

    static {
        CAPABILITY = null;
    }


    @SubscribeEvent
    public void onPlayerJoin(final EntityJoinWorldEvent e) {
        if (!(e.getEntity() instanceof EntityPlayer)) {
            System.out.println("Non-player entity joined world");
            return;
        }
        final EntityPlayer target = (EntityPlayer) e.getEntity();

        System.out.println("Player joined world");
        sync(target, Collections.singletonList(target)); // set player animation

    }

    public static void sync(final EntityPlayer entity, final Iterable<EntityPlayer> receivers) {
        if(entity.hasCapability(CAPABILITY, null)) {
            final PacketSyncAnimation packet = new PacketSyncAnimation(entity.getEntityId(), Objects.requireNonNull(entity.getCapability(CAPABILITY, null)).getAnimation());
            receivers.forEach(player -> {
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
