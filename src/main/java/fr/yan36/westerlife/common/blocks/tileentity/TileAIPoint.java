package fr.yan36.westerlife.common.blocks.tileentity;

import com.jme3.bullet.collision.shapes.CompoundCollisionShape;
import fr.dynamx.api.contentpack.object.part.IShapeInfo;
import fr.dynamx.common.contentpack.type.objects.BlockObject;
import fr.yan36.westerlife.common.Util;
import fr.yan36.westerlife.common.blocks.dynamx.BlockAIPoint;
import fr.yan36.westerlife.common.entities.npcdomac.NPCDomacEntity;
import fr.yan36.westerlife.common.objects.gameplay.MacdoCommand;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class TileAIPoint extends TileEntitySyncClient implements ITickable {

    private BlockObject b;

    private BlockAIPoint.Type type;
    private String target;
    private NPCDomacEntity relatedEntity;

    public TileAIPoint() {
        super(null);
    }

    public TileAIPoint(BlockObject<?> blockObjectInfo, BlockAIPoint.Type type) {
        super(blockObjectInfo);
        this.b = blockObjectInfo;
        this.type = type;
        this.target = "";
        this.relatedEntity = null;
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);
        System.out.println("qsdqsdqsd");
        System.out.println(tagCompound.hasKey("relatedEntity"));
        this.type = BlockAIPoint.Type.values()[tagCompound.getInteger("type")];
        this.target = tagCompound.getString("target");
        if(tagCompound.hasKey("relatedEntity")) {
            try {
                NPCDomacEntity npcConcessEntity = new NPCDomacEntity(world, Util.parseBlockPosFromString(this.getTarget()));

                if(tagCompound.hasKey("relatedEntity")) npcConcessEntity.deserializeNBT(tagCompound.getCompoundTag("relatedEntity"));
                this.relatedEntity = npcConcessEntity;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            this.relatedEntity = null;
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);
        tagCompound.setInteger("type", this.type.ordinal());
        tagCompound.setString("target", this.target);
        System.out.println(this.relatedEntity);
        if(this.relatedEntity != null) {
            if(this.relatedEntity.serializeNBT() != null) {
                tagCompound.setTag("relatedEntity", this.relatedEntity.serializeNBT());
            }
        }

        return tagCompound;
    }

    public BlockAIPoint.Type getType() {
        return type;
    }

    public void setTarget(String target) {
        this.target = target;
        sync();
        syncToClient();
    }

    public String getTarget() {
        return target;
    }

    public void setType(BlockAIPoint.Type type) {
        this.type = type;
        sync();
        syncToClient();
    }

    public NPCDomacEntity getRelatedEntity() {
        return this.relatedEntity;
    }

    public void setRelatedEntity(NPCDomacEntity relatedEntity) {
        this.relatedEntity = relatedEntity;
        sync();
        syncToClient();
    }

    @Override
    public void update() {
        if(!world.isRemote) {
            if (this.relatedEntity == null && this.type == BlockAIPoint.Type.DOMAC_SPAWN) {

                this.relatedEntity = new NPCDomacEntity(world, MacdoCommand.generateCommand(),  new ResourceLocation("westerlife:textures/entities/default.png"), Util.parseBlockPosFromString(this.target));
                this.relatedEntity.setPosition(this.pos.getX(), this.pos.getY(), this.pos.getZ());
                this.relatedEntity.setTargetPos(Util.parseBlockPosFromString(this.target));
                this.relatedEntity.setStatusData("waiting");

                world.spawnEntity(this.relatedEntity);
            }
        }


    }


    @Override
    public List<IShapeInfo> getUnrotatedCollisionBoxes() {
        return new ArrayList<>();
    }

    @Override
    public CompoundCollisionShape getPhysicsCollision() {
        return new CompoundCollisionShape();
    }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate) {
        return oldState.getBlock() != newSate.getBlock();
    }
}
