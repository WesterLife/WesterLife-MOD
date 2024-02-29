package fr.gabidut76.westerlife.common.blocks.tileentity;

import fr.dynamx.common.contentpack.type.objects.BlockObject;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;
import net.minecraftforge.fml.common.Optional;

import javax.vecmath.Vector2f;

@Optional.Interface(iface="com.elytradev.mirage.lighting.ILightEventConsumer", modid="mirage")
public class TileLyre extends TileEntitySyncClient implements ITickable {

    private BlockObject b;

    private Vector2f rotationfrom = new Vector2f(0, 0);
    private Vector2f rotationto = new Vector2f(0, 0);
    private Vector2f actualrotation = new Vector2f(0, 0);

    private int time = 0;
    private int timeMax = 0;
    private boolean flip = false;
    private boolean blink = false;

    public TileLyre(){
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
            this.actualrotation.x = (float) (this.rotationfrom.x + (this.rotationto.x - this.rotationfrom.x) * (1 - Math.pow(1 - (float) this.time / this.timeMax, 2)));
            this.actualrotation.y = (float) (this.rotationfrom.y + (this.rotationto.y - this.rotationfrom.y) * (1 - Math.pow(1 - (float) this.time / this.timeMax, 2)));

            sync();
            markDirty();
            if(this.time >= this.timeMax){
                this.time = 0;
                this.timeMax = 0;
            }
        }
    }
}
