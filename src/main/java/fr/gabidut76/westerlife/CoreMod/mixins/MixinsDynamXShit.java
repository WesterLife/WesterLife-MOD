package fr.gabidut76.westerlife.CoreMod.mixins;

import fr.aym.acslib.api.services.error.ErrorLevel;
import fr.dynamx.client.renders.model.renderer.ObjModelRenderer;
import fr.dynamx.client.renders.model.renderer.ObjObjectRenderer;
import fr.dynamx.client.renders.model.texture.MaterialTexture;
import fr.dynamx.common.DynamXMain;
import fr.dynamx.common.objloader.data.Material;
import fr.dynamx.common.objloader.data.ObjObjectData;
import fr.dynamx.utils.errors.DynamXErrorManager;
import net.minecraft.client.renderer.GlStateManager;
import org.spongepowered.asm.mixin.*;

import javax.annotation.Nullable;
import javax.vecmath.Vector4f;

@Mixin(value = {ObjObjectRenderer.class}, priority = 999, remap = false)
public abstract class MixinsDynamXShit {

    @Mutable
    @Final
    @Shadow
    private final ObjObjectData objObjectData;
    @Shadow
    private Vector4f objectColor = new Vector4f(1.0F, 1.0F, 1.0F, 1.0F);

    @Shadow public abstract ObjObjectData getObjObjectData();

    public MixinsDynamXShit(ObjObjectData objObjectData) {
        this.objObjectData = objObjectData;
    }

    /**
     * @author ?
     * @reason caca
     */
    @Overwrite
    private Material bindMaterial(ObjModelRenderer model, String materialName, @Nullable String baseVariantName, @Nullable String variantName) {
        if (variantName != null && materialName.equals(baseVariantName)) {
            materialName = variantName;
        }



        Material material = (Material)model.getMaterials().get(materialName);
        if (material == null && baseVariantName != null) {
            material = (Material)model.getMaterials().get(baseVariantName);
        }

        if (!this.isMaterialValid(model, material)) {
            return null;
        } else {
            MaterialTexture materialMultipleTextures = material.diffuseTexture.containsKey(variantName) ? (MaterialTexture)material.diffuseTexture.get(variantName) : (MaterialTexture)material.diffuseTexture.get("default");
            if (materialMultipleTextures != null) {
                this.bindTexture(materialMultipleTextures.getGlTextureId());
            } else {
                DynamXMain.log.error("Failed to load Default texture of " + this.objObjectData.getName() + " in " + model.getLocation() + " in material " + material.getName());
            }

            return material;
        }
    }


    private void bindTexture(int id) {
        GlStateManager.bindTexture(id);
    }

    private boolean isMaterialValid(ObjModelRenderer model, Material material) {
        if (material == null) {
            return false;
        } else if (material.getName().equals("none")) {
            if (!model.hasNoneMaterials) {
                DynamXErrorManager.addError(model.getTextureVariants() != null ? model.getTextureVariants().getPackName() : "Non-pack model", DynamXErrorManager.MODEL_ERRORS, "obj_none_material", ErrorLevel.LOW, model.getLocation().getModelPath().toString(), this.objObjectData.getName());
            }

            model.hasNoneMaterials = true;
            return false;
        } else {
            return true;
        }
    }
}
