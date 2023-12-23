package fr.yan36.westerlife.common.capabilities.playerchunckrel;

import fr.yan36.westerlife.Main;
import fr.yan36.westerlife.common.blocks.BlockPlayerSensor;
import fr.yan36.westerlife.common.capabilities.packets.PacketSyncChunk;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockFire;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.Objects;

@Mod.EventBusSubscriber
public class PlayerChunkRelCapability {
    @CapabilityInject(IPlayerChunk.class)
    public static final Capability<IPlayerChunk> CAPABILITY;

    static {
        CAPABILITY = null;
    }


    @SubscribeEvent
    public void onPlayerJoin(final EntityJoinWorldEvent e) {
        if (!(e.getWorld().isRemote)) {
            if (!(e.getEntity() instanceof EntityPlayer)) {
                return;
            }
            PlayerChunkRelCapability.sync(e.getWorld().getChunk(e.getEntity().getPosition()), Collections.singletonList((EntityPlayer) e.getEntity()));
        }
    }

    @SubscribeEvent
    public void onPlayerTrack(PlayerEvent.StartTracking e) {
        if (e.getTarget() != null && e.getTarget() != null && e.getTarget() instanceof EntityPlayer) {
            PlayerChunkRelCapability.sync(e.getEntity().world.getChunk(e.getTarget().getPosition()), Collections.singletonList((EntityPlayer) e.getEntity()));
        }
    }

    public static void sync(final Chunk c, final Iterable<EntityPlayer> receivers) {
        if (c.hasCapability(CAPABILITY, null)) {
            final PacketSyncChunk packet = new PacketSyncChunk(c.x, c.z, Objects.requireNonNull(c.getCapability(CAPABILITY, null)).getCO2(), Objects.requireNonNull(c.getCapability(CAPABILITY, null)).getPollen());
            receivers.forEach(player -> {
                Main.network.sendTo(packet, (EntityPlayerMP) player);
            });
        }
    }



    @SubscribeEvent
    public static void updateBlock(BlockEvent.NeighborNotifyEvent event) {
        if (!event.getWorld().isRemote) {
            if(event.getState().getBlock() instanceof BlockPlayerSensor) return;
            if (event.getState().getBlock() instanceof BlockFire || event.getState().getBlock() instanceof BlockAir) {
                Chunk chunk = event.getWorld().getChunk(event.getPos());
                if (chunk.getCapability(PlayerChunkRelCapability.CAPABILITY, null).getCO2() < 100 && chunk.getCapability(PlayerChunkRelCapability.CAPABILITY, null).getPollen() > 0) {
                    System.out.println(event.getState().getBlock());
                    if(event.getState().getBlock() instanceof BlockFire){
                        chunk.getCapability(PlayerChunkRelCapability.CAPABILITY, null).setCO2(chunk.getCapability(PlayerChunkRelCapability.CAPABILITY, null).getCO2() + 10);
                        Main.co2ManagementThread.addFireToReplace(System.currentTimeMillis() + 10000, event.getPos());
                    }

                }
                chunk.markDirty();
                sync(chunk, event.getWorld().playerEntities);
            }

        }
    }


    public static class Storage implements Capability.IStorage<IPlayerChunk> {

        @Nullable
        @Override
        public NBTBase writeNBT(Capability<IPlayerChunk> capability, IPlayerChunk instance, EnumFacing side) {
            NBTTagCompound nbt = new NBTTagCompound();
            nbt.setInteger("pollen", instance.getPollen());
            nbt.setInteger("co2", instance.getCO2());

            return nbt;
        }

        @Override
        public void readNBT(Capability<IPlayerChunk> capability, IPlayerChunk instance, EnumFacing side, NBTBase nbt) {
            if (!(nbt instanceof NBTTagCompound)) {
                return;
            }
            final NBTTagCompound compound = (NBTTagCompound) nbt;
            instance.setPollen(compound.getInteger("pollen"));
            instance.setCO2(compound.getInteger("co2"));
        }
    }

}
