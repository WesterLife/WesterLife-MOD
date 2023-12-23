package fr.yan36.westerlife.common.blocks.tileentity.render;

import fr.dynamx.client.renders.TESRDynamXBlock;
import fr.dynamx.common.DynamXContext;
import fr.yan36.westerlife.common.Util;
import fr.yan36.westerlife.common.blocks.tileentity.TileFeuRouge;
import fr.yan36.westerlife.common.blocks.tileentity.TilePark;
import fr.yan36.westerlife.common.init.DynamXInit;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.opengl.GL11;

public class RenderPark extends TESRDynamXBlock<TilePark> {


    @Override
    public void render(TilePark te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {

        GL11.glPushMatrix();
        GL11.glTranslatef((float) (x + 0.5D + (te.getPackInfo().getTranslation()).x), (float) (y + 1.3D + (te.getPackInfo().getTranslation()).y), (float) (z + 0.5D + (te.getPackInfo().getTranslation()).z));
        GL11.glScalef((te.getPackInfo().getScaleModifier()).x, (te.getPackInfo().getScaleModifier()).y, (te.getPackInfo().getScaleModifier()).z);
        GL11.glRotatef(te.getRotation() * 22.5F, 0.0F, -1.0F, 0.0F);

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glColor4f(0, 1f, 0, (float) (Math.cos(Minecraft.getMinecraft().world.getWorldTime() / 5.0) / 2.0) + 0.7f);
        DynamXContext.getDxModelRegistry().getModel(te.getPackInfo().getModel()).renderGroups("park", (byte) te.getBlockMetadata(), false);
        GL11.glColor4f(1f, 1f, 1f, 1f);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();


        GL11.glPushMatrix();

        GlStateManager.translate(x + 0.5D + (te.getPackInfo().getTranslation()).x, y + 1.3D + (te.getPackInfo().getTranslation()).y, z + 0.5D + (te.getPackInfo().getTranslation()).z);
        GL11.glLineWidth(20);
        GlStateManager.glBegin(GL11.GL_LINES);
        GL11.glVertex3d(0, 0, 0);

        if (te.getLinkedTo().equals(new BlockPos(-1, -1, -1)) || te.getLinkedTo().equals(new BlockPos(0, 0, 0))) {
            if (Minecraft.getMinecraft().player.getHeldItem(EnumHand.MAIN_HAND) != null) {

                if (Minecraft.getMinecraft().player.getHeldItem(EnumHand.MAIN_HAND).getItem() == DynamXInit.magicWand) {
                    ItemStack stack = Minecraft.getMinecraft().player.getHeldItem(EnumHand.MAIN_HAND);
                    if (stack.hasTagCompound()) {
                        if (!stack.getTagCompound().getString("parkingLink").isEmpty()) {
                            BlockPos pos2 = Minecraft.getMinecraft().player.getPosition();
                            GL11.glVertex3f((float) pos2.getX() - te.getPos().getX(), (float)pos2.getY() - te.getPos().getY(), (float)pos2.getZ() - te.getPos().getZ());
                        }
                    }
                }
            }
        } else {
            if(Minecraft.getMinecraft().player.getHeldItem(EnumHand.MAIN_HAND).getItem() == DynamXInit.magicWand) {
                GL11.glVertex3f(te.getLinkedTo().getX() - te.getPos().getX(), te.getLinkedTo().getY() - te.getPos().getY(), te.getLinkedTo().getZ() - te.getPos().getZ());
            }
        }

        GlStateManager.glEnd();

        GL11.glPopMatrix();
    }
}