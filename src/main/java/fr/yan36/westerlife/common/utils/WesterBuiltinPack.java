package fr.yan36.westerlife.common.utils;

import com.jme3.bullet.collision.shapes.CompoundCollisionShape;
import com.jme3.math.Vector3f;
import fr.dynamx.api.contentpack.ContentPackType;
import fr.dynamx.api.contentpack.object.IPhysicsPackInfo;
import fr.dynamx.api.contentpack.object.part.IShapeInfo;
import fr.dynamx.common.contentpack.PackInfo;
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
    public Collection<? extends IShapeInfo> getShapes() {
        Collection<IShapeInfo> a = new ArrayList<>();
        a.add(new IShapeInfo() {
            @Override
            public Vector3f getPosition() {
                return new Vector3f(0, 0, 0);
            }

            @Override
            public Vector3f getSize() {
                return new Vector3f(1,1,1);
            }
        });
        return a;
    }

    @Override
    public List<Vector3f> getCollisionShapeDebugBuffer() {
        return Collections.emptyList();
    }

    @Override
    public CompoundCollisionShape getPhysicsCollisionShape() {
        CompoundCollisionShape shape = new CompoundCollisionShape();
        shape.setScale(new Vector3f(1,1,1));
        return shape;
    }

    @Override
    public ItemStack getPickedResult(int i) {
        return new ItemStack(Blocks.AIR);
    }

    @Override
    public String getName() {
        return "westerlife:builtin";
    }

    public static class WesterPackInfo extends PackInfo {

        public WesterPackInfo() {
            super("westerlife_builtin", ContentPackType.BUILTIN);
        }
    }
}
