package fr.gabidut76.westerlife.common.blocks.tileentity;

import com.jme3.math.Vector3f;
import fr.betterlights.BetterLightsMod;
import fr.betterlights.lighting.lightcasters.BlockLightCaster;
import fr.betterlights.lighting.lightcasters.LightCaster;
import fr.dynamx.common.contentpack.type.objects.BlockObject;
import fr.dynamx.utils.DynamXUtils;
import fr.gabidut76.westerlife.common.objects.TileToRegister;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@TileToRegister(location = "westerlife:light_controller")
public class TileLightController extends TileEntitySyncClient implements ITickable {

    private BlockObject b;


    public TileLightController() {
        super(null);
    }

    public TileLightController(BlockObject<?> blockObjectInfo) {
        super(blockObjectInfo);
        this.b = blockObjectInfo;
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
    public void update() {

    }
}
