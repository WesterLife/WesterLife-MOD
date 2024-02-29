package fr.gabidut76.westerlife.common.capabilities.playergarage;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class PlayerGarageProvider implements ICapabilityProvider, INBTSerializable<NBTTagCompound> {

    private final PlayerGarage storage;

    public PlayerGarageProvider(final PlayerGarage storage) {
        this.storage = storage;
    }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == PlayerGarageCapability.CAPABILITY;
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == PlayerGarageCapability.CAPABILITY) {
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
