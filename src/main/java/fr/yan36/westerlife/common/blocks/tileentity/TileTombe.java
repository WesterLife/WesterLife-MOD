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


public class TileTombe extends TileEntitySyncClient implements ITickable {
    private String text = "Quelqu'un.";
    public TileTombe(BlockObject blockObjectInfo) {
        super(blockObjectInfo);
    }

    public TileTombe() {
        super(null);
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        this.text = tagCompound.getString("text");
        super.readFromNBT(tagCompound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);
        tagCompound.setString("text", this.text);
        return tagCompound;
    }

    @Override
    public void update() {

        if(!world.isRemote) {
            if(world.getWorldTime() % 20 == 0) {
                sync();
            }
        }



        super.update();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
