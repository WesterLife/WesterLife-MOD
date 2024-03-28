package fr.gabidut76.westerlife.common.blocks.tileentity;

import com.jme3.math.Vector3f;
import fr.betterlights.BetterLightsMod;
import fr.betterlights.lighting.lightcasters.BlockLightCaster;
import fr.betterlights.lighting.lightcasters.LightCaster;
import fr.betterlights.lighting.lightcasters.StaticLightCaster;
import fr.dynamx.common.contentpack.type.objects.BlockObject;
import fr.gabidut76.westerlife.common.Util;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;
import net.minecraftforge.fml.common.Optional;

import javax.vecmath.Vector2f;

@Optional.Interface(iface = "com.elytradev.mirage.lighting.ILightEventConsumer", modid = "mirage")
public class TileLyre extends TileEntitySyncClient implements ITickable {

    private BlockObject b;

    private Vector2f rotationfrom = new Vector2f(0, 0);
    private Vector2f rotationto = new Vector2f(0, 0);
    private Vector2f actualrotation = new Vector2f(0, 0);

    @Getter
    @Setter

    private Vector3f color = new Vector3f(1, 1, 1);
    @Getter
    @Setter
    private int intensity = 10;
    @Getter
    @Setter
    private int zoom = 20;
    private int time = 0;
    private int timeMax = 0;
    private boolean flip = false;
    private boolean blink = false;

    public LightCaster lightCaster;

    public TileLyre() {
        super(null);
    }

    public TileLyre(BlockObject<?> blockObjectInfo) {
        super(blockObjectInfo);
        this.b = blockObjectInfo;

    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);
        this.rotationfrom = new Vector2f(tagCompound.getInteger("yawf"), tagCompound.getInteger("pitchf"));
        this.rotationto = new Vector2f(tagCompound.getInteger("yawt"), tagCompound.getInteger("pitcht"));
        this.actualrotation = new Vector2f(tagCompound.getInteger("yaw"), tagCompound.getInteger("pitch"));
        this.time = tagCompound.getInteger("time");
        this.timeMax = tagCompound.getInteger("timemax");
        this.flip = tagCompound.getBoolean("flip");
        this.blink = tagCompound.getBoolean("blink");
        this.intensity = tagCompound.getInteger("intensity");
        this.color = new Vector3f(tagCompound.getFloat("colorr"), tagCompound.getFloat("colorg"), tagCompound.getFloat("colorb"));
        this.zoom = tagCompound.getInteger("zoom");

    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);
        tagCompound.setInteger("yawf", (int) this.rotationfrom.x);
        tagCompound.setInteger("pitchf", (int) this.rotationfrom.y);
        tagCompound.setInteger("yawt", (int) this.rotationto.x);
        tagCompound.setInteger("pitcht", (int) this.rotationto.y);
        tagCompound.setInteger("yaw", (int) this.actualrotation.x);
        tagCompound.setInteger("pitch", (int) this.actualrotation.y);
        tagCompound.setInteger("time", this.time);
        tagCompound.setInteger("timemax", this.timeMax);
        tagCompound.setBoolean("flip", this.flip);
        tagCompound.setBoolean("blink", this.blink);
        tagCompound.setInteger("intensity", this.intensity);
        tagCompound.setFloat("colorr", this.color.x);
        tagCompound.setFloat("colorg", this.color.y);
        tagCompound.setFloat("colorb", this.color.z);
        tagCompound.setInteger("zoom", this.zoom);

        return tagCompound;
    }


    public void setRotationfrom(Vector2f rotationfrom) {
        this.rotationfrom = rotationfrom;
        sync();
        markDirty();
    }

    public Vector2f getRotationfrom() {
        return rotationfrom;
    }

    public void setRotationto(Vector2f rotationto) {
        this.rotationto = rotationto;
        sync();
        markDirty();
    }

    public void setActualrotation(Vector2f actualrotation) {
        this.actualrotation = actualrotation;
        sync();
        markDirty();

    }

    public Vector2f getRotationto() {
        return rotationto;
    }


    public int getTime() {
        return time;
    }

    public Vector2f getActualrotation() {
        return actualrotation;
    }

    public void setTimeMax(int timeMax) {
        this.timeMax = timeMax;
        sync();
        markDirty();
    }

    public int getTimeMax() {
        return timeMax;
    }

    public void setFlip(boolean flip) {
        this.flip = flip;
        sync();
        markDirty();
    }

    public boolean isFlip() {
        return flip;
    }

    public void setBlink(boolean blink) {
        this.blink = blink;
        sync();
        markDirty();
    }

    public boolean isBlink() {
        return blink;
    }

    @Override
    public void update() {

        if (this.timeMax > 0) {
            this.time++;

            // ease out animation
            this.actualrotation.x = (float) ((this.rotationfrom.x + (this.rotationto.x - this.rotationfrom.x) * (1 - Math.pow(1 - (float) this.time / this.timeMax, 2))));
            this.actualrotation.y = (float) (this.rotationfrom.y + (this.rotationto.y - this.rotationfrom.y) * (1 - Math.pow(1 - (float) this.time / this.timeMax, 2)));

            sync();
            markDirty();
            if (this.time >= this.timeMax) {
                this.time = 0;
                this.timeMax = 0;
            }
        }

        if (world.isRemote) {
            if (lightCaster == null) {
                System.out.println(actualrotation);
                lightCaster = new BlockLightCaster(this)
                        .intensity(10f)
                        .color(1f, 1f, 1f)
                        .angle(getZoom(), getZoom() + 5)
                        .pos(this.getPos().getX() + 0.5f, this.getPos().getY() + 1.5f, this.getPos().getZ() + 0.5f)
                        .direction(Util.getVectorForRotation(-this.actualrotation.y, -this.actualrotation.x));
                ;


                lightCaster.direction(Util.getVectorForRotation(-this.actualrotation.y, -this.actualrotation.x));
                System.out.println("LightCaster created");
                BetterLightsMod.getLightManager().addLightCaster(lightCaster, true);
            } else {
                lightCaster.direction(Util.getVectorForRotation(-this.actualrotation.y,(getRotation() * 22.5f - 90f) -this.actualrotation.x));
                lightCaster.color(this.color.x, this.color.y, this.color.z);
                lightCaster.intensity(this.intensity);
                lightCaster.angle(getZoom(), getZoom() + 5);
            }
        }


    }
}
