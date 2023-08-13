package fr.yan36.westerlife.common.blocks.tileentity;

import fr.dynamx.common.contentpack.type.objects.BlockObject;
import fr.dynamx.common.entities.BaseVehicleEntity;
import fr.dynamx.common.entities.vehicles.CarEntity;
import fr.dynamx.common.physics.entities.BaseVehiclePhysicsHandler;
import fr.yan36.westerlife.common.Util;
import fr.yan36.westerlife.common.objects.justice.Conviction;
import fr.yan36.westerlife.common.objects.justice.Punishment;
import fr.yan36.westerlife.common.utils.AABB;
import fr.yan36.westerlife.server.bdd.DBUtils;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

import java.util.Collections;
import java.util.Date;
import java.util.List;


public class TileRadarFixe extends TileEntitySyncClient implements ITickable {
    private int speed = 130;
    private String playercooldown = "";

    public TileRadarFixe(BlockObject blockObjectInfo) {
        super(blockObjectInfo);
    }

    public TileRadarFixe() {
        super(null);
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        this.speed = tagCompound.getInteger("speed");
        super.readFromNBT(tagCompound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);
        tagCompound.setInteger("speed", this.speed);
        return tagCompound;
    }

    @Override
    public void update() {
        AABB aabb = new AABB(this.pos).grow(5);
        // float[] with 9 values: minX, minY, minZ, maxX, maxY, maxZ, centerX, centerY, centerZ
//        float[] aabb2 = new float[] { (float) aabb.minX, (float) aabb.minY, (float) aabb.minZ, (float) aabb.maxX, (float) aabb.maxY, (float) aabb.maxZ, (float) aabb.getCenter().x, (float) aabb.getCenter().y, (float) aabb.getCenter().z };

        // grow in direction of getBlockObjectInfo().getRotation()


        switch ((int) (getRotation() * 22.5F)) {
            case 0:
                aabb = new AABB(this.pos.add(0,0,5)).grow(5);
                break;
            case 90:
                aabb = new AABB(this.pos.add(-5,0,0)).grow(5);
                break;
            case 180:
                aabb = new AABB(this.pos.add(0,0,-5)).grow(5);
                break;
            case 270:
                aabb = new AABB(this.pos.add(5,0,0)).grow(5);
                break;
        }

        if(!this.world.isRemote) {

            assert aabb != null;
            List<EntityPlayer> entityPlayerList = Util.getEntitiesWithinAABB(getWorld(), EntityPlayer.class, aabb);


            for (EntityPlayer ep : entityPlayerList) {
                if(ep.isRiding()) {
                    if(ep.getLowestRidingEntity() instanceof CarEntity<?>) {
                        BaseVehicleEntity<?> v = (BaseVehicleEntity<?>) ep.getLowestRidingEntity();
                        if(v.getPhysicsHandler().getSpeed(BaseVehiclePhysicsHandler.SpeedUnit.KMH) > this.speed) {
                            int depassement = (int) (v.getPhysicsHandler().getSpeed(BaseVehiclePhysicsHandler.SpeedUnit.KMH) - this.speed);
                            List<Punishment> l = new java.util.ArrayList<>(Collections.emptyList());

                            if(depassement >= 1 && depassement <= 5) {
                                l.add(new Punishment(Punishment.PunishmentType.FINE, 68f, false));
                            } else if(depassement >= 6 && depassement <= 29) {
                                l.add(new Punishment(Punishment.PunishmentType.FINE, 68f, false));
                            } else if(depassement >= 30 && depassement <= 39) {
                                l.add(new Punishment(Punishment.PunishmentType.FINE, 135f, false));
                            } else if(depassement >= 40 && depassement <= 49) {
                                l.add(new Punishment(Punishment.PunishmentType.FINE, 135f, false));
                            } else if(depassement >= 50) {
                                l.add(new Punishment(Punishment.PunishmentType.FINE, 1500f, false));
                            }

                            Conviction conviction = new Conviction(ep.getUniqueID().toString(), "Excès de vitesse au coordonnées GPS de : " + getPos().getX() + " " + getPos().getY() + " " + getPos().getZ() + ". Flashé à la vitesse de : " + v.getPhysicsHandler().getSpeed(BaseVehiclePhysicsHandler.SpeedUnit.KMH) + " (+" + depassement + ")", String.valueOf(new Date().getTime()) , l);

                            DBUtils.saveToDB(conviction);
                            float t = 0;
                            for (Punishment value : conviction.getPunishment()) {
                                t+=value.getAmount();
                            }
                            ep.sendMessage(new TextComponentString("Vous avez reçu une amende de " + t + "€ pour un excès de vitesse de : " + depassement + " KM/H."));
                            ep.sendMessage(new TextComponentString("Merci de vous rendre au commissariat le plus proche pour payer votre amende."));
                        }
                    }
                }
            }
        }


    }
    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate) {
        return oldState.getBlock() != newSate.getBlock();
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getSpeed() {
        return speed;
    }


}
