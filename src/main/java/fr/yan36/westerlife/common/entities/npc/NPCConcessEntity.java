package fr.yan36.westerlife.common.entities.npc;

import fr.yan36.westerlife.Main;
import fr.yan36.westerlife.common.network.PacketOpenConcessionaire;
import fr.yan36.westerlife.common.objects.CarDealer;
import fr.yan36.westerlife.server.api.NemesisLink;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import java.io.IOException;

public class NPCConcessEntity extends EntityLiving {

    private String concessID;
    private ResourceLocation texture = new ResourceLocation("westerlife:textures/entities/default.png");


    public NPCConcessEntity(World worldIn) {
        super(worldIn);
        setEntityInvulnerable(true);
        setNoGravity(true);
        setSilent(true);
        setNoAI(true);

    }

    public String getConcessID() {
        return concessID;
    }

    public void setConcessID(String concessID) {
        onUpdate();
        this.concessID = concessID;
    }

    public ResourceLocation getTexture() {
        return new ResourceLocation("westerlife:textures/entities/default.png");
    }

    public void setTexture(ResourceLocation texture) {
        onUpdate();
        this.texture = texture;
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        this.concessID = compound.getString("concessID");
        this.texture = new ResourceLocation(compound.getString("texture"));
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        compound.setString("concessID", this.concessID);
        compound.setString("texture", texture.toString());
    }

    @Override
    public EnumPushReaction getPushReaction() {
        return EnumPushReaction.IGNORE;
    }

    @Override
    public float getAIMoveSpeed() {
        return 0f;
    }

    @Override
    public boolean isEntityInvulnerable(DamageSource source) {
        return true;
    }

    @Override
    public boolean getIsInvulnerable() {
        return false;
    }

    @Override
    public boolean processInteract(EntityPlayer player, EnumHand hand) {
        if (!this.world.isRemote) {
            try {
                CarDealer dealer = NemesisLink.NEMESIS_API.getCarDealerByID(this.concessID);
                Main.network.sendTo(new PacketOpenConcessionaire(dealer), (EntityPlayerMP) player);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
        return true;
    }

}
