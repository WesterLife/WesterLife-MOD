package fr.yan36.westerlife.common.entities;

import fr.dynamx.api.contentpack.object.part.IDrawablePart;
import fr.dynamx.client.renders.RenderPhysicsEntity;
import fr.dynamx.common.contentpack.type.vehicle.ModularVehicleInfo;

import javax.annotation.Nullable;

public class PartTestEntity implements IDrawablePart<TestEntity2> {


    @Override
    public void drawParts(@Nullable TestEntity2 testEntity2, RenderPhysicsEntity<?> renderPhysicsEntity, ModularVehicleInfo modularVehicleInfo, byte b, float v) {

    }

    @Override
    public String[] getRenderedParts() {
        return new String[0];
    }

}
