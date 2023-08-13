package fr.yan36.westerlife.common.blocks.tileentity;

import fr.dynamx.common.contentpack.type.objects.BlockObject;
import fr.yan36.westerlife.Main;
import fr.yan36.westerlife.common.handlers.SoundsHandler;
import fr.yan36.westerlife.common.network.PacketPlaySound;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.fml.common.network.NetworkRegistry;


public class TileIrm extends TileEntitySyncClient implements ITickable {
    private int step = 0;
    private int step2 = 0;
    private boolean isRunning = false;
    private boolean isRollingBack = false;
    public TileIrm(BlockObject blockObjectInfo) {
        super(blockObjectInfo);
    }

    public TileIrm() {
        super(null);
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        this.step = tagCompound.getInteger("step");
        this.isRunning = tagCompound.getBoolean("isRunning");
        this.step2 = tagCompound.getInteger("step2");
        super.readFromNBT(tagCompound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);
        tagCompound.setInteger("step", step);
        tagCompound.setBoolean("isRunning", isRunning);
        tagCompound.setInteger("step2", step2);
        return tagCompound;
    }

    @Override
    public void update() {

        if(!world.isRemote) {
            if(world.getWorldTime() % 20 == 0) {
                sync();
            }
            if(isRollingBack) {
                if(step <= 0) {
                    step = 0;
                    isRollingBack = false;
                    sync();
                } else {
                    step-=8;
                    if(world.getWorldTime() % 40 == 0) {
                        Main.network.sendToAllAround(new PacketPlaySound(3), new NetworkRegistry.TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 10));
                    }
                }
                sync();
            }
            if(isRunning) {

                if(step >= 2380) {
                    step = 2380;
                    if(step2 == 0) {
                        Main.network.sendToAllAround(new PacketPlaySound(2), new NetworkRegistry.TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 10));
                        step2++;
                        sync();
                        new Thread(() -> {
                            try {
                                Thread.sleep(85000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            isRunning = false;
                            isRollingBack = true;
                            sync();
                        }).start();
                    }


                } else {
                    step+=8;
                    if(world.getWorldTime() % 40 == 0) {
                        Main.network.sendToAllAround(new PacketPlaySound(3), new NetworkRegistry.TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 10));
                    }
                }
                sync();
            } else {
                sync();
            }
        }




    }

    public void setStep(int step) {
        this.step = step;
        sync();
        markDirty();
    }

    public int getStep() {
        return step;
    }

    public void setRunning(boolean running) {
        if(!running) {
            step = 0;
            step2 = 0;
        }
        isRunning = running;
        sync();
        markDirty();
    }

    public boolean isRunning() {
        return isRunning;
    }

    public boolean isRollingBack() {
        return this.isRollingBack;
    }
}
