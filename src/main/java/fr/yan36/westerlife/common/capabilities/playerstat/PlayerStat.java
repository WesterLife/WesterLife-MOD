package fr.yan36.westerlife.common.capabilities.playerstat;

import fr.yan36.westerlife.common.objects.character.Character;
import fr.yan36.westerlife.common.utils.Animation;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;

public class PlayerStat implements IPlayerStat, INBTSerializable<NBTTagCompound> {

    private Animation animation;
    private Character character;

    public PlayerStat(Animation animation, Character character) {
        this.animation = animation;
        this.character = character;
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
        this.animation = Animation.getAnimationById(nbt.getInteger("animation"));
    }

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
}
