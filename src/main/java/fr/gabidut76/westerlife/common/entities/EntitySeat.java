package fr.gabidut76.westerlife.common.entities;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntitySeat extends Entity {
    public EntitySeat(World world)
    {
        super(world);
        noClip = true;
        height = 0.0001F;
        width = 0.0001F;
    }

    public EntitySeat(World world, BlockPos pos)
    {
        super(world);
        setPosition(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D);
        noClip = true;
        height = 0.0001F;
        width = 0.0001F;
    }

    @Override
    protected void entityInit() {}

    @Override
    protected void readEntityFromNBT(NBTTagCompound tag) {}

    @Override
    protected void writeEntityToNBT(NBTTagCompound tag) {}
}
