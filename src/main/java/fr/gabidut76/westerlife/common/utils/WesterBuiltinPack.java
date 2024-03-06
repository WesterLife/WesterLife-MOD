package fr.gabidut76.westerlife.common.utils;

import com.jme3.math.Vector3f;
import fr.dynamx.api.contentpack.ContentPackType;
import fr.dynamx.api.contentpack.object.IPhysicsPackInfo;
import fr.dynamx.api.entities.modules.ModuleListBuilder;
import fr.dynamx.client.renders.model.renderer.ObjObjectRenderer;
import fr.dynamx.client.renders.scene.node.SceneNode;
import fr.dynamx.common.contentpack.PackInfo;
import fr.dynamx.common.contentpack.type.ObjectCollisionsHelper;
import fr.dynamx.common.entities.PackPhysicsEntity;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public class WesterBuiltinPack implements IPhysicsPackInfo {

    public WesterBuiltinPack() {
    }

    @Override
    public Vector3f getCenterOfMass() {
        return new Vector3f(0, 0, 0);
    }

    @Override
    public void addModules(PackPhysicsEntity<?, ?> entity, ModuleListBuilder modules) {

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
    public float getRenderDistance() {
        return 0;
    }

    @Override
    public ResourceLocation getModel() {
        return null;
    }

    @Override
    public SceneNode<?, ?> getSceneGraph() {
        return null;
    }

    @Override
    public float getLinearDamping() {
        return 0;
    }

    @Override
    public String getName() {
        return "westerlife:builtin";
    }

    @Nullable
    @Override
    public IModelTextureVariants getTextureVariantsFor(ObjObjectRenderer objObjectRenderer) {
        return null;
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
            super("westerlife_builtin", "", ContentPackType.BUILTIN);
        }
    }
}
