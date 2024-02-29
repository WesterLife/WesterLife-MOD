package fr.gabidut76.westerlife.common.capabilities.playerstat;

import fr.gabidut76.westerlife.common.objects.character.Character;
import fr.gabidut76.westerlife.common.utils.Animation;
import net.minecraft.nbt.NBTTagCompound;

public interface IPlayerStat {
    Animation getAnimation();
    void setAnimation(Animation animation);

    Character getCharacter();

    void setCharacter(Character character);


    void readNBT(NBTTagCompound nBTTagCompound);

    void writeNBT(NBTTagCompound nBTTagCompound);

    int getThirst();
    void setThirst(int thirst);

}
