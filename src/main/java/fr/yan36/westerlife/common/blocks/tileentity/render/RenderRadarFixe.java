package fr.yan36.westerlife.common.blocks.tileentity.render;

import com.jme3.math.Quaternion;
import fr.dynamx.api.contentpack.object.part.IShapeInfo;
import fr.dynamx.client.handlers.ClientDebugSystem;
import fr.dynamx.client.renders.TESRDynamXBlock;
import fr.dynamx.common.DynamXContext;
import fr.dynamx.common.blocks.TEDynamXBlock;
import fr.dynamx.utils.debug.DynamXDebugOptions;
import fr.dynamx.utils.debug.TerrainDebugData;
import fr.dynamx.utils.debug.renderer.VehicleDebugRenderer;
import fr.dynamx.utils.optimization.GlQuaternionPool;
import fr.dynamx.utils.optimization.MutableBoundingBox;
import fr.dynamx.utils.optimization.QuaternionPool;
import fr.dynamx.utils.optimization.Vector3fPool;
import fr.yan36.westerlife.Main;
import fr.yan36.westerlife.common.blocks.tileentity.TileMovingGate;
import fr.yan36.westerlife.common.blocks.tileentity.TileRadarFixe;
import jdk.nashorn.internal.ir.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.opengl.GL11;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

import static net.minecraft.client.renderer.GlStateManager.*;

public class RenderRadarFixe extends TESRDynamXBlock<TileRadarFixe> {
    private static int ro = 0;

    @Override
    public void render(TileRadarFixe te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        super.render(te, x, y, z, partialTicks, destroyStage, alpha);
    }

    @Override
    public boolean shouldRenderDebug() {
        return ClientDebugSystem.enableDebugDrawing && (Main.radar.isActive());
    }

    @Override
    public void renderDebug(TEDynamXBlock te, double x, double y, double z) {
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





        super.renderDebug(te, x, y, z);
    }
}
