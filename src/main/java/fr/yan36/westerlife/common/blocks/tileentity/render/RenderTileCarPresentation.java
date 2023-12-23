package fr.yan36.westerlife.common.blocks.tileentity.render;

import fr.dynamx.client.renders.TESRDynamXBlock;
import fr.dynamx.client.renders.model.renderer.ObjModelRenderer;
import fr.dynamx.client.renders.model.renderer.ObjObjectRenderer;
import fr.dynamx.common.DynamXContext;
import fr.dynamx.common.contentpack.DynamXObjectLoaders;
import fr.dynamx.common.contentpack.parts.PartWheel;
import fr.dynamx.common.contentpack.type.vehicle.SteeringWheelInfo;
import fr.dynamx.utils.client.DynamXRenderUtils;
import fr.yan36.westerlife.common.blocks.tileentity.TileCarPresentation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class RenderTileCarPresentation extends TESRDynamXBlock<TileCarPresentation> {


    @Override
    public void render(TileCarPresentation te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(x + 0.5D + (te.getPackInfo().getTranslation()).x, y + 0.2d + (te.getPackInfo().getTranslation()).y, z + 0.5D + (te.getPackInfo().getTranslation()).z);
        GlStateManager.scale((te.getPackInfo().getScaleModifier()).x, (te.getPackInfo().getScaleModifier()).y, (te.getPackInfo().getScaleModifier()).z);
        GlStateManager.rotate(te.getRotation() * 22.5F, 0.0F, -1.0F, 0.0F);
        DynamXContext.getDxModelRegistry().getModel(te.getPackInfo().getModel()).renderGroups("base", (byte) te.getBlockMetadata(), false);
        if(!te.getCar().isEmpty()) {
            GlStateManager.translate(0, 0.45f, 0);
            GlStateManager.scale(0.8f, 0.8f, 0.8f);
            GlStateManager.rotate(Minecraft.getMinecraft().world.getWorldTime() % (360*2), 0, 1, 0);
            if(DynamXObjectLoaders.WHEELED_VEHICLES.findInfo(te.getCar()) != null) {
                DynamXRenderUtils.renderCar(DynamXObjectLoaders.WHEELED_VEHICLES.findInfo(te.getCar()), (byte) 0);
            } else {
                Minecraft.getMinecraft().fontRenderer.drawString("NULL", 0, 0, 0xFFFFFF);
            }

        }
        GlStateManager.popMatrix();
    }


}
