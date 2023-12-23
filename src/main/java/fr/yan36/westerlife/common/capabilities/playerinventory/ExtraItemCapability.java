package fr.yan36.westerlife.common.capabilities.playerinventory;

import fr.yan36.westerlife.Main;
import fr.yan36.westerlife.common.capabilities.packets.PacketSyncExtraItem;
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

@Mod.EventBusSubscriber
public class ExtraItemCapability {
    @CapabilityInject(IExtraItemHandler.class)
    public static final Capability<IExtraItemHandler> CAPABILITY;

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

            System.out.println("Player joined world");
            sync(target, Collections.singletonList(target));
        }
    }

    public static void sync(final EntityPlayer entity, final Iterable<EntityPlayer> receivers) {
        if(entity.hasCapability(CAPABILITY, null)) {
            for (int i = 0; i < ((IExtraItemHandler) entity.getCapability((Capability) ExtraItemCapability.CAPABILITY, (EnumFacing) null)).getSlots(); i++) {
                final PacketSyncExtraItem msg = new PacketSyncExtraItem(entity, i, ((IExtraItemHandler) entity.getCapability((Capability) ExtraItemCapability.CAPABILITY, (EnumFacing) null)).getStackInSlot(i));
                receivers.forEach(p -> {
                    Main.network.sendTo(msg, (EntityPlayerMP) p);
                });
            }
        }
    }

    public static class Storage implements Capability.IStorage<IExtraItemHandler> { // suce ma bite <3

        @Nullable
        @Override
        public NBTBase writeNBT(Capability<IExtraItemHandler> capability, IExtraItemHandler instance, EnumFacing side) {
            return null;
        }

        @Override
        public void readNBT(Capability<IExtraItemHandler> capability, IExtraItemHandler instance, EnumFacing side, NBTBase nbt) {
        }
    }

}
