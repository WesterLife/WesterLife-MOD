package fr.yan36.westerlife.common.blocks.tileentity;

import com.jme3.bullet.collision.shapes.CompoundCollisionShape;
import fr.dynamx.common.DynamXContext;
import fr.dynamx.common.contentpack.type.objects.BlockObject;
import fr.dynamx.utils.optimization.MutableBoundingBox;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class TilePoteauLevant extends TileEntitySyncClient implements ITickable {
    public String state = "closed";
    private int a = 0;
    private int timeleft = 0;
    public TilePoteauLevant(BlockObject blockObjectInfo) {
        super(blockObjectInfo);
    }

    public TilePoteauLevant() {
        super(null);
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        this.state = tagCompound.getString("state");
        this.timeleft = tagCompound.getInteger("timeleft");
        super.readFromNBT(tagCompound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);
        tagCompound.setString("state", this.state);
        tagCompound.setInteger("a", this.a);
        tagCompound.setInteger("timeleft", this.timeleft);
        return tagCompound;
    }

    @Override
    public void update() {
        if(this.getState().equals("open")) {
            System.out.println(this.a);
            if(this.a > -100) {
                this.a -= 5;
            }
            this.computeBoundingBox();
            try {
                DynamXContext.getPhysicsWorld(world).schedule(this::markCollisionsDirty);
            } catch (Exception e) {
                System.out.println("Error in TileMovingGate.java: " + e.getMessage() + " " + this.getPos());
            }

            this.timeleft++;
            if(this.timeleft > 300) {
                setState("closed");
                this.timeleft = 0;
            }
        } else {
            this.timeleft = 0;
            if(this.a < 0) {
                this.a += 5;
            }
        }
        sync();
        super.update();
    }

    public void setState(String s) {
        this.state = s;
    }

    public String getState() {
        return this.state;
    }

    public int getA() {
        return this.a;
    }


    @Override
    public List<MutableBoundingBox> getUnrotatedCollisionBoxes() {
        if(this.getState().equals("open")) {
            return Collections.singletonList(new MutableBoundingBox(0, 0, 0, 0, 0, 0));
        }
        return super.getUnrotatedCollisionBoxes();
    }

    @Override
    public CompoundCollisionShape getPhysicsCollision() {
        if(this.getState().equals("open")) {
            return new CompoundCollisionShape();
        }
        return super.getPhysicsCollision();
    }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate) {
        return oldState.getBlock() != newSate.getBlock();
    }
}
