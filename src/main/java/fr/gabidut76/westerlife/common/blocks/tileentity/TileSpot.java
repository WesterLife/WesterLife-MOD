package fr.gabidut76.westerlife.common.blocks.tileentity;

import com.jme3.math.Vector3f;
import fr.betterlights.BetterLightsMod;
import fr.betterlights.lighting.lightcasters.BlockLightCaster;
import fr.betterlights.lighting.lightcasters.LightCaster;
import fr.betterlights.lighting.lightcasters.StaticLightCaster;
import fr.dynamx.common.contentpack.type.objects.BlockObject;
import fr.dynamx.utils.DynamXUtils;
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

import java.awt.*;

public class TileSpot extends TileEntitySyncClient implements ITickable {

    private BlockObject b;

    private int angle;
    @Getter
    @Setter
    private int color = 0xFFFFFF;
    @SideOnly(Side.CLIENT)
    private LightCaster lightCaster;

    public TileSpot() {
        super(null);
    }

    public TileSpot(BlockObject<?> blockObjectInfo) {
        super(blockObjectInfo);
        this.b = blockObjectInfo;
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);
        this.angle = tagCompound.getInteger("angle");
        this.color = tagCompound.getInteger("color");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);
        tagCompound.setInteger("angle", this.angle);
        tagCompound.setInteger("color", this.color);
        return tagCompound;
    }


    public void setAngle(int angle) {
        this.angle = angle;

        sync();
        markDirty();
    }

    public int getAngle() {
        return angle;
    }

    protected final Vec3d getVectorForRotation(float pitch, float yaw) {
        float f = MathHelper.cos(-yaw * 0.017453292F - (float) Math.PI);
        float f1 = MathHelper.sin(-yaw * 0.017453292F - (float) Math.PI);
        float f2 = -MathHelper.cos(-pitch * 0.017453292F);
        float f3 = MathHelper.sin(-pitch * 0.017453292F);
        return new Vec3d((double) (f1 * f2), (double) f3, (double) (f * f2));
    }

    @Override
    public void update() {
        if (FMLCommonHandler.instance().getSide().isClient()) {
            if (this.lightCaster == null) {
                this.lightCaster = new BlockLightCaster(this);
                System.out.println("Creating light caster");

                Vector3f toMove = new Vector3f(.5f, 0, .5f);
                setColor(0xFFFFFF);
                int color = getColor();


                this.lightCaster
                        .pos(DynamXUtils.toVector3f(getPos()).add(0, 1.5f + (getAngle() > 30 ? 0 : -1f), 0).addLocal(toMove))
                        .direction(getVectorForRotation(Minecraft.getMinecraft().world.getWorldTime() * 22.5f % 360, getAngle()))
                        .color(new Vector3f(new Vector3f((color >> 16) & 0xFF, (color >> 8) & 0xFF, color & 0xFF)).divide(new Vector3f(255f, 255f, 255f)))
                        .setEnabled(true)
                        .angle(15f, 50f)
                        .intensity(100f);

                BetterLightsMod.getLightManager().addLightCaster(this.lightCaster, true);

                markDirty();
                update();
            }


            float timeScale = 0.05f;
//            int color = new Color(Color.HSBtoRGB((float) Math.cos(world.getWorldTime() * timeScale % 255), 1, 1)).getRGB();

            int color = getColor();
            this.lightCaster
                    .direction(getVectorForRotation(getAngle(), getRotation() * 22.5f % 360))
                    .color(new Vector3f(new Vector3f((color >> 16) & 0xFF, (color >> 8) & 0xFF, color & 0xFF)).divide(new Vector3f(255f, 255f, 255f)));
        }

    }

//    @Override
//    public void update() {
//        if(this.lightCaster == null) {
//            this.lightCaster = new BlockLightCaster(this);
//            this.lightCaster
//                    .pos(DynamXUtils.toVector3f(getPos()))
//                    .direction(new Vec3d(0, 0, 1));
//        }
////        this.lightCaster.getVolumetricConfig()
//    }
}
