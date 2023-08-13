package fr.yan36.westerlife.common.capabilities.playerstat;

import fr.yan36.westerlife.common.utils.Animation;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;

public class PlayerStat implements IPlayerStat, INBTSerializable<NBTTagCompound> {

    private Animation animation;

    public PlayerStat(Animation animation) {
        this.animation = animation;
    }



    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound nbt = new NBTTagCompound();
        System.out.println("Serializing animation: " + animation.getId());
        nbt.setInteger("animation", animation.getId());
        return nbt;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        this.animation = Animation.getAnimationById(nbt.getInteger("animationa"));
    }

    @Override
    public Animation getAnimation() {
        return animation;
    }

    @Override
    public void setAnimation(Animation animation) {
        this.animation = animation;
    }
}
