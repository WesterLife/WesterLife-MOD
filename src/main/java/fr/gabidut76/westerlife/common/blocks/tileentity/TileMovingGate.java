package fr.gabidut76.westerlife.common.blocks.tileentity;

import com.jme3.bullet.collision.shapes.CompoundCollisionShape;
import fr.dynamx.api.contentpack.object.part.IShapeInfo;
import fr.dynamx.common.DynamXContext;
import fr.dynamx.common.contentpack.type.objects.BlockObject;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class TileMovingGate extends TileEntitySyncClient implements ITickable {
    public String state = "closed";
    private int a = 0;
    private String player = "yan36;gabidut76;_INeox";
    private int timeleft = 0;
    private boolean isOpenForever = false;
    public TileMovingGate(BlockObject blockObjectInfo) {
        super(blockObjectInfo);
    }

    public TileMovingGate() {
        super(null);
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        this.state = tagCompound.getString("state");
        this.a = tagCompound.getInteger("a");
        this.player = tagCompound.getString("player");
        this.timeleft = tagCompound.getInteger("timeleft");
        this.isOpenForever = tagCompound.getBoolean("isOpenForever");
        super.readFromNBT(tagCompound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);
        tagCompound.setString("state", this.state);
        tagCompound.setInteger("a", this.a);
        tagCompound.setString("player", this.player);
        tagCompound.setInteger("timeleft", this.timeleft);
        tagCompound.setBoolean("isOpenForever", this.isOpenForever);
        return tagCompound;
    }

    @Override
    public void update() {
        if(!this.world.isRemote) {
            AxisAlignedBB aabb = new AxisAlignedBB(this.pos).grow(5);
            List<EntityPlayer> players = this.world.getEntitiesWithinAABB(EntityPlayer.class, aabb);

            if(this.isOpenForever) {
                this.setState("open");
            }

            for(EntityPlayer p : players) {
                if(this.getState().equals("closed")) {
                    if(this.player.contains(p.getName())) {
                        this.setState("open");
                    }
                }
            }

            if(this.getState().equals("open")) {
                if(this.a > -90) {
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
                if(isOpenForever) this.setState("open");

                this.timeleft = 0;
                if(this.a < 0) {
                    this.a += 5;
                }
            }
            sync();
        }
//        this.computeBoundingBox();
//        DynamXContext.getPhysicsWorld(world).schedule(this::markCollisionsDirty);
//        this.world.markBlockRangeForRenderUpdate(pos, pos);

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
    public List<String> getPlayer() {
        return Arrays.asList(this.player.split(";"));
    }
    public void addPlayer(String s) {
        this.player += ";" + s;
    }
    public void removePlayer(String s) {
        this.player = this.player.replace(";" + s, "");
    }


    @Override
    public List<IShapeInfo> getUnrotatedCollisionBoxes() {
        if(this.getState().equals("open")) {
            return new ArrayList<>();
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

    public boolean isOpenForever() {
        return isOpenForever;
    }

    public void setOpenForever(boolean openForever) {
        isOpenForever = openForever;
        sync();
        syncToClient();
    }

    public void setPlayer(String player) {
        this.player = player;
        sync();
        syncToClient();
    }
}
