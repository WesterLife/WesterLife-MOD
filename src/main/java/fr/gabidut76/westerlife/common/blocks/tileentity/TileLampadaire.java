package fr.gabidut76.westerlife.common.blocks.tileentity;

import fr.betterlights.BetterLightsMod;
import fr.betterlights.lighting.lightcasters.LightCaster;
import fr.betterlights.lighting.lightcasters.StaticLightCaster;
import fr.dynamx.common.contentpack.type.objects.BlockObject;
import fr.gabidut76.westerlife.common.Util;
import fr.gabidut76.westerlife.common.objects.TileToRegister;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;

import java.util.ArrayList;
import java.util.List;

@TileToRegister(location = "westerlife:lampadaire")
public class TileLampadaire extends TileEntitySyncClient implements ITickable {
    @Getter
    @Setter
    private float intensity = 2.5f;

    public LightCaster lightCaster;

    public TileLampadaire(BlockObject blockObjectInfo) {
        super(blockObjectInfo);
    }

    public TileLampadaire() {
        super(null);
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        this.intensity = tagCompound.getFloat("intensity");
        super.readFromNBT(tagCompound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);
        tagCompound.setFloat("intensity", intensity);
        return tagCompound;
    }

    @Override
    public void update() {

        if (lightCaster == null) {
            lightCaster = new StaticLightCaster()
                    .pos(this.getPos().getX() + .5f, this.getPos().getY() + 3f, this.getPos().getZ() + 0.5f)
                    .direction(Util.getVectorForRotation(90,0))
                    .color(1, 1, 1)
                    .angle(90,90)
                    .intensity(intensity)
            ;
            lightCaster.getShadowConfiguration().setEnabled(false);
            BetterLightsMod.getLightManager().addLightCaster(lightCaster, true);
        }

        if(world.getWorldTime() % 20 == 0) {
            lightCaster.intensity(intensity);
            // if night
            if(world.getWorldTime() > 13000 && world.getWorldTime() < 23000) {
                lightCaster.intensity(intensity);
            } else {
                lightCaster.intensity(0);
            }
        }

    }

}
