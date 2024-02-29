package fr.gabidut76.westerlife.common.blocks.tileentity.render;

import fr.dynamx.client.handlers.ClientDebugSystem;
import fr.dynamx.client.renders.TESRDynamXBlock;
import fr.gabidut76.westerlife.westercore.Main;
import fr.gabidut76.westerlife.common.blocks.tileentity.TileRadarFixe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

import static net.minecraft.client.renderer.GlStateManager.*;

public class RenderRadarFixe extends TESRDynamXBlock<TileRadarFixe> {
    private static int ro = 0;

    @Override
    public void render(TileRadarFixe te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {

        if(shouldRenderDebug()) {
            BlockPos playerpos = Minecraft.getMinecraft().player.getPosition();

            x = te.getPos().getX() - playerpos.getX();
            y = te.getPos().getY() - playerpos.getY();
            z = te.getPos().getZ() - playerpos.getZ();
            switch ((int) (te.getRotation() * 22.5F)) {
                case 0:
                    z += 5;
                    break;
                case 90:
                    x -= 5;
                    break;
                case 180:
                    z -= 5;
                    break;
                case 270:
                    x += 5;
                    break;
            }
            int nx = (int) x;
            int ny = (int) y;
            int nz = (int) z;
            AxisAlignedBB aabb = new AxisAlignedBB(new BlockPos(nx, ny, nz)).grow(5);
            pushMatrix();
            RenderGlobal.drawBoundingBox(aabb.minX, aabb.minY, aabb.minZ, aabb.maxX, aabb.maxY, aabb.maxZ, 1, 0,0.5f, 0.5f);
            popMatrix();
        }

        super.render(te, x, y, z, partialTicks, destroyStage, alpha);
    }

    @Override
    public boolean shouldRenderDebug() {
        return ClientDebugSystem.enableDebugDrawing && (Main.radar.isActive());
    }

}
