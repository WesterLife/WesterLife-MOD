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
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

@TileToRegister(location = "westerlife:busstop")
public class TileBusStop extends TileEntitySyncClient implements ITickable {
    @Getter
    @Setter
    private String stopname = "Arrêt provisiore";

    public List<LightCaster> lightCasters = new ArrayList<>();

    public TileBusStop(BlockObject blockObjectInfo) {
        super(blockObjectInfo);
    }

    public TileBusStop() {
        super(null);
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        this.stopname = tagCompound.getString("stopname");
        super.readFromNBT(tagCompound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);
        tagCompound.setString("stopname", this.stopname);
        return tagCompound;
    }

    @Override
    public void update() {

        if (lightCasters.isEmpty()) {
            System.out.println("Adding light casters");
            System.out.println(getRotation() * 22.5f);
            for (int i = 0; i < 4; i++) {
                LightCaster lightCaster = new StaticLightCaster().color(1.0f, 1.0f, 1.0f).intensity(2.5f)
                        .pos(
                                pos.getX() + 0.5f + ((getRotation() * 22.5f == 90.0) ? i : 0) + ((getRotation() * 22.5f == 270.0) ? i : 0) + ((getRotation() * 22.5f == 180.0) ? 1.25f : 0) + ((getRotation() * 22.5f == 0.0) ? 0 : -1.5f ),
                                pos.getY() + 2f,
                                pos.getZ() + 0.5f + ((getRotation() * 22.5f == 0.0) ? i : 0) + ((getRotation() * 22.5f == 180.0) ? i : 0) + ((getRotation() * 22.5f == 90.0) ? -1.5f : -1.25f) +((getRotation() * 22.5f == 270.0) ? 1.5f : 0))
                        .angle(0, 35).direction(Util.getVectorForRotation(90, 0));
                lightCaster.getShadowConfiguration().setEnabled(false);
                lightCasters.add(lightCaster);
            }
            for (LightCaster lightCaster : lightCasters) {
                BetterLightsMod.getLightManager().addLightCaster(lightCaster, true);
            }
        }
    }

}
