package fr.yan36.westerlife.common.items.renderer;

import fr.dynamx.common.DynamXContext;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class ItemBurgerRenderer extends TileEntityItemStackRenderer {
    public ItemBurgerRenderer() {

    }

    @Override
    public void renderByItem(ItemStack itemStackIn) {
        System.out.println("renderByItem " + itemStackIn);
        GlStateManager.pushMatrix();
        DynamXContext.getDxModelRegistry().getModel(new ResourceLocation("westerlife","models/dynamx/blocks/macdo/macdo.obj")).renderGroups("Steaks", (byte) 0, false);
        GlStateManager.popMatrix();
    }

    @Override
    public void renderByItem(ItemStack itemStackIn, float partialTicks) {
        System.out.println("renderByItem " + itemStackIn);
        GlStateManager.pushMatrix();
        DynamXContext.getDxModelRegistry().getModel(new ResourceLocation("westerlife","models/dynamx/blocks/macdo/macdo.obj")).renderGroups("Steaks", (byte) 0, false);
        GlStateManager.popMatrix();
    }


}
