package fr.gabidut76.westerlife.common.blocks.tileentity;

import com.jme3.math.Vector3f;
import fr.dynamx.common.contentpack.type.objects.BlockObject;
import fr.dynamx.common.entities.vehicles.CarEntity;
import fr.dynamx.utils.DynamXUtils;
import fr.gabidut76.westerlife.common.Util;
import fr.gabidut76.westerlife.common.utils.AABB;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;


public class TileHerse extends TileEntitySyncClient implements ITickable {

    public TileHerse(BlockObject blockObjectInfo) {
        super(blockObjectInfo);
    }

    public TileHerse() {
        super(null);
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);
        return tagCompound;
    }

    @Override
    public void update() { // NE FONCTIONNE PAS EN SOLO, UTILISER LE SERVEUR.
        AABB aabb = new AABB(this.pos).expand(2,3,2);
        List<EntityPlayer> players = Util.getEntitiesWithinAABB(world, EntityPlayer.class, aabb);

        for(EntityPlayer p : players) {
            if(p.isRiding() && p.getLowestRidingEntity() instanceof CarEntity<?>) {
                CarEntity<?> v = (CarEntity<?>) p.getLowestRidingEntity();
                if(DynamXUtils.getSpeed(v) > 5 ) {
                    System.out.println("Car is moving");
                    new Thread(() -> {
                        try {
                            world.setBlockState(pos, Blocks.AIR.getDefaultState());
                            v.getPhysicsHandler().setLinearVelocity(new Vector3f(
                                    v.getPhysicsHandler().getLinearVelocity().x / 2,
                                    v.getPhysicsHandler().getLinearVelocity().y / 2,
                                    v.getPhysicsHandler().getLinearVelocity().z / 2
                            ));
                            Thread.sleep(2000);
                            v.getPhysicsHandler().setLinearVelocity(new Vector3f(
                                    v.getPhysicsHandler().getLinearVelocity().x / 4,
                                    v.getPhysicsHandler().getLinearVelocity().y / 4,
                                    v.getPhysicsHandler().getLinearVelocity().z / 4
                            ));

                            Thread.sleep(2000);
                            v.getPhysicsHandler().setFreezePhysics(true);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println("Car is stopped");

                    }).start();

                }
            }
        }


    }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate) {
        return oldState.getBlock() != newSate.getBlock();
    }
}
