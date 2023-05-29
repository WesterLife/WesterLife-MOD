package fr.yan36.westerlife.common.entities;

import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.entity.EntityLiving;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class NPCTestEntity extends EntityLiving {
    private String name = "test";
    private String associated_car = "none";
    private ResourceLocation texture = new ResourceLocation("westerlife:textures/entities/" + name + ".png");

    public NPCTestEntity(World worldIn) {
        super(worldIn);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        compound.setString("name", name);
        compound.setString("associated_car", associated_car);
        compound.setString("texture", texture.toString());
        super.writeEntityToNBT(compound);

    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        this.name = compound.getString("name");
        this.associated_car = compound.getString("associated_car");
        this.texture = new ResourceLocation(compound.getString("texture"));
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAssociated_car() {
        return associated_car;
    }

    public void setAssociated_car(String associated_car) {
        this.associated_car = associated_car;
    }

    public ResourceLocation getTexture() {
        return texture;
    }

    public void setTexture(ResourceLocation texture) {
        this.texture = texture;
    }

    @Override
    public EnumPushReaction getPushReaction() {
        return super.getPushReaction();
    }

}
