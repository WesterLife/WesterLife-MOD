package fr.gabidut76.westerlife.common.capabilities.playerchunckrel;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class PlayerChunkRelProvider implements ICapabilityProvider, INBTSerializable<NBTTagCompound> {

    private final PlayerChunkRel storage;

    public PlayerChunkRelProvider(final PlayerChunkRel storage) {
        this.storage = storage;
    }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == PlayerChunkRelCapability.CAPABILITY;
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == PlayerChunkRelCapability.CAPABILITY) {
            return (T) this.storage;
        }
        return null;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        return this.storage.serializeNBT();
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        this.storage.deserializeNBT(nbt);
    }
}
