package fr.yan36.westerlife.common.utils;

import com.jme3.bullet.collision.shapes.CompoundCollisionShape;
import com.jme3.math.Vector3f;
import fr.dynamx.api.contentpack.ContentPackType;
import fr.dynamx.api.contentpack.object.IPhysicsPackInfo;
import fr.dynamx.api.contentpack.object.part.IDrawablePart;
import fr.dynamx.api.contentpack.object.part.IShapeInfo;
import fr.dynamx.api.contentpack.object.part.InteractivePart;
import fr.dynamx.common.contentpack.PackInfo;
import fr.dynamx.common.contentpack.type.ObjectCollisionsHelper;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class WesterBuiltinPack implements IPhysicsPackInfo {

    public WesterBuiltinPack() {
    }

    @Override
    public Vector3f getCenterOfMass() {
        return new Vector3f(0, 0, 0);
    }


    @Override
    public <T extends InteractivePart<?, ?>> List<T> getInteractiveParts() {
        return IPhysicsPackInfo.super.getInteractiveParts();
    }



    @Override
    public List<IDrawablePart<?>> getDrawableParts() {
        return new ArrayList<>();
    }

    @Override
    public ItemStack getPickedResult(int i) {
        return new ItemStack(Blocks.AIR);
    }

    @Override
    public float getAngularDamping() {
        return 0;
    }

    @Override
    public float getLinearDamping() {
        return 0;
    }

    @Override
    public String getName() {
        return "westerlife:builtin";
    }

    @Override
    public String getPackName() {
        return "westerlife";
    }

    @Override
    public String getFullName() {
        return "westerlife:builtin";
    }

    @Override
    public Vector3f getScaleModifier() {
        return null;
    }

    @Override
    public ObjectCollisionsHelper getCollisionsHelper() {
        return null;
    }


    public static class WesterPackInfo extends PackInfo {

        public WesterPackInfo() {
            super("westerlife_builtin", ContentPackType.BUILTIN);
        }
    }
}
