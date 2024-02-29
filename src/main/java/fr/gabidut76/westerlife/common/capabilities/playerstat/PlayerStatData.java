package fr.gabidut76.westerlife.common.capabilities.playerstat;

import fr.gabidut76.westerlife.common.objects.character.Character;
import fr.gabidut76.westerlife.common.utils.Animation;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

import javax.annotation.Nullable;

public class PlayerStatData implements IPlayerStat {
    private Character character;
    private Animation animation;
    private int thirst;

    public static final ResourceLocation LOCATION = new ResourceLocation("westerlife", "playerstat");
    @Override
    public Animation getAnimation() {
        return animation;
    }

    @Override
    public void setAnimation(Animation animation) {
        this.animation = animation;
    }

    @Override
    public Character getCharacter() {
        return character;
    }

    @Override
    public void setCharacter(Character character) {
        this.character = character;
    }

    @Override
    public void readNBT(NBTTagCompound nBTTagCompound) {
        this.animation = Animation.getAnimationById(nBTTagCompound.getInteger("animation"));
        this.character = new Character();
        this.character.deserializeNBT(nBTTagCompound.getCompoundTag("character"));
        this.thirst = nBTTagCompound.getInteger("thirst");
    }

    @Override
    public void writeNBT(NBTTagCompound nBTTagCompound) {
        nBTTagCompound.setInteger("animation", this.animation.getId());
        nBTTagCompound.setTag("character", this.character.serializeNBT());
        nBTTagCompound.setInteger("thirst", this.thirst);
    }

    @Override
    public int getThirst() {
        return thirst;
    }

    @Override
    public void setThirst(int thirst) {
        this.thirst = thirst;
    }

    public static class PlayerStatProvider implements ICapabilitySerializable<NBTBase> {
        @CapabilityInject(IPlayerStat.class)
        public static final Capability<IPlayerStat> CAPABILITY = null;

        private IPlayerStat instance = CAPABILITY.getDefaultInstance();

        @Override
        public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
            return capability == CAPABILITY;
        }

        @Override
        public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
            return capability == CAPABILITY ? CAPABILITY.cast(instance) : null;
        }

        @Override
        public NBTBase serializeNBT() {
            return CAPABILITY.getStorage().writeNBT(CAPABILITY, instance, null);
        }

        @Override
        public void deserializeNBT(NBTBase nbt) {
            CAPABILITY.getStorage().readNBT(CAPABILITY, instance, null, nbt);
        }
    }

    public static class Storage implements Capability.IStorage<IPlayerStat> {
        @Nullable
        @Override
        public NBTBase writeNBT(Capability<IPlayerStat> capability, IPlayerStat instance, EnumFacing side) {
            final NBTTagCompound nbt = new NBTTagCompound();
            instance.writeNBT(nbt);
            return nbt;
        }

        @Override
        public void readNBT(Capability<IPlayerStat> capability, IPlayerStat instance, EnumFacing side, NBTBase nbt) {
            instance.readNBT((NBTTagCompound) nbt);
        }
    }
}
