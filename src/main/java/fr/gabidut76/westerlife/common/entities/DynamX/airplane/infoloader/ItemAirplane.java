package fr.gabidut76.westerlife.common.entities.DynamX.airplane.infoloader;

import com.jme3.math.Vector3f;
import fr.dynamx.common.contentpack.type.vehicle.ModularVehicleInfo;
import fr.dynamx.common.entities.BaseVehicleEntity;
import fr.dynamx.common.entities.vehicles.HelicopterEntity;
import fr.dynamx.common.items.ItemModularEntity;
import fr.gabidut76.westerlife.common.entities.DynamX.airplane.AirplaneEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class ItemAirplane extends ItemModularEntity {
    public ItemAirplane(ModularVehicleInfo info) {
        super(info);
    }

    public BaseVehicleEntity<?> getSpawnEntity(World worldIn, EntityPlayer playerIn, Vector3f pos, float spawnRotation, int metadata) {
        return new AirplaneEntity<>(((ModularVehicleInfo)this.getInfo()).getFullName(), worldIn, pos, spawnRotation, metadata);
    }
}
