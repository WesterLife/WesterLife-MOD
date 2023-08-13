package fr.yan36.westerlife.common.capabilities.playerstat;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class PlayerStatProvider implements ICapabilityProvider, INBTSerializable<NBTTagCompound> {

    private final PlayerStat storage;

    public PlayerStatProvider(final PlayerStat storage) {
        this.storage = storage;
    }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == PlayerStatCapability.CAPABILITY;
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == PlayerStatCapability.CAPABILITY) {
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
