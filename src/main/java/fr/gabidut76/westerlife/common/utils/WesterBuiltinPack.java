package fr.gabidut76.westerlife.common.utils;

import com.jme3.math.Vector3f;
import fr.dynamx.api.contentpack.ContentPackType;
import fr.dynamx.api.contentpack.object.IPhysicsPackInfo;
import fr.dynamx.api.entities.modules.ModuleListBuilder;
import fr.dynamx.client.renders.model.renderer.ObjObjectRenderer;
import fr.dynamx.client.renders.scene.node.SceneNode;
import fr.dynamx.common.contentpack.PackInfo;
import fr.dynamx.common.contentpack.type.MaterialVariantsInfo;
import fr.dynamx.common.contentpack.type.ObjectCollisionsHelper;
import fr.dynamx.common.entities.PackPhysicsEntity;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public class WesterBuiltinPack {

    public static class WesterPackInfo extends PackInfo {

        public WesterPackInfo() {
            super("westerlife_builtin", "", ContentPackType.BUILTIN);
        }
    }
}
