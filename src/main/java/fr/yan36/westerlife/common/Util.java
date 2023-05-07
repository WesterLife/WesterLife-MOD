package fr.yan36.westerlife.common;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import fr.yan36.westerlife.common.utils.AABB;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.math.*;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import static net.minecraft.world.World.MAX_ENTITY_RADIUS;

public class Util {
    public static BlockPos parseBlockPosFromString(String stringPos)
    {
        String[] xyz = stringPos.split(",");
        if (xyz.length == 3)
        {
            int x = Integer.parseInt(xyz[0]);
            int y = Integer.parseInt(xyz[1]);
            int z = Integer.parseInt(xyz[2]);
            return new BlockPos(x, y, z);
        } else
        {
            System.err.println("The parsed BlockPos value has to be like that: \"x, y, z\"");
        }
        return BlockPos.ORIGIN;
    }

    public static String blockPosToString(BlockPos pos)
    {
        return pos.getX() + "," + pos.getY() + "," + pos.getZ();
    }

    public static List<Entity> getEntitiesNearEntity(EntityLivingBase player, int range) {
        return getEntitiesNearPoint(player.posX, player.posY, player.posZ, range, player.world);
    }

    public static List<Entity> getEntitiesNearPoint(double x, double y, double z, int range, World world) {
        List l = world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(x - range, y - range, z - range, x + range, y + range, z + range));
        List<Entity> result = new ArrayList<Entity>();
        for (int i = 0; i < l.size(); ++i) {
            Entity entity = (Entity) l.get(i);
            if (entity != null) {
                if (entity.getDistance(x, y, z) <= range) {
                    result.add(entity);
                }
            }
        }
        return result;
    }

    public static Entity getEntityLookAt(EntityPlayer player, int max_dis) {
        List list = getEntitiesNearEntity(player, max_dis);
        for (Iterator iterator = list.iterator(); iterator.hasNext(); ) {
            Entity obj = (Entity) iterator.next();

            Vec3d vec3 = player.getLook(1.0F).normalize();
            Vec3d vec31 = new Vec3d(obj.posX - player.posX,
                    obj.getEntityBoundingBox().minY + (double) (obj.height / 2.0F) - (player.posY + (double) player.getEyeHeight()),
                    obj.posZ - player.posZ);
            double d0 = vec31.length();
            vec31 = vec31.normalize();
            double d1 = vec3.dotProduct(vec31);
            if (d1 > 1.0D - 0.025D / d0) {
                return obj;
            }
        }
        return null;
    }

    public static RayTraceResult rayTracePlayer(EntityPlayer player, int max_dis) {
        Vec3d vec3 = player.getPositionEyes(1.0F);
        Vec3d vec31 = player.getLook(1.0F).scale(max_dis);
        Vec3d vec32 = vec3.add(vec31);
        return player.world.rayTraceBlocks(vec3, vec32, false, false, true);
    }

    public static HashMap<BlockPos, Block> getBlocksAround(BlockPos pos, World world, int i) {
        HashMap<BlockPos, Block> blocks = new HashMap<>();
        for (int x = pos.getX() - i; x <= pos.getX() + i; x++) {
            for (int y = pos.getY() - i; y <= pos.getY() + i; y++) {
                for (int z = pos.getZ() - i; z <= pos.getZ() + i; z++) {
                    blocks.put(new BlockPos(x, y, z), world.getBlockState(new BlockPos(x, y, z)).getBlock());
                }
            }
        }
        return blocks;
    }

    public static <T extends Entity> List<T> getEntitiesWithinAABB(World w, Class<? extends T> clazz, AABB aabb)
    {
        int j2 = MathHelper.floor((aabb.minX - MAX_ENTITY_RADIUS) / 16.0D);
        int k2 = MathHelper.ceil((aabb.maxX + MAX_ENTITY_RADIUS) / 16.0D);
        int l2 = MathHelper.floor((aabb.minZ - MAX_ENTITY_RADIUS) / 16.0D);
        int i3 = MathHelper.ceil((aabb.maxZ + MAX_ENTITY_RADIUS) / 16.0D);
        List<T> list = Lists.newArrayList();

        for (int j3 = j2; j3 < k2; ++j3)
        {
            for (int k3 = l2; k3 < i3; ++k3)
            {
                if (w.isChunkGeneratedAt(j3, k3))
                {
                    getEntitiesOfTypeWithinAABB(w.getChunk(j3, k3), clazz, aabb, list,  EntitySelectors.NOT_SPECTATING);
                }
            }
        }

        return list;
    }


    public static  <T extends Entity> void getEntitiesOfTypeWithinAABB(Chunk w, Class <? extends T > entityClass, AABB aabb, List<T> listToFill, Predicate <? super T > filter)
    {
        int i = MathHelper.floor((aabb.minY - World.MAX_ENTITY_RADIUS) / 16.0D);
        int j = MathHelper.floor((aabb.maxY + World.MAX_ENTITY_RADIUS) / 16.0D);
        i = MathHelper.clamp(i, 0, w.getEntityLists().length - 1);
        j = MathHelper.clamp(j, 0, w.getEntityLists().length - 1);

        for (int k = i; k <= j; ++k)
        {
            for (T t : w.getEntityLists()[k].getByClass(entityClass))
            {
                if (t.getEntityBoundingBox().intersects(aabb.minX, aabb.minY, aabb.minZ, aabb.maxX, aabb.maxY, aabb.maxZ) && (filter == null || filter.apply(t)))
                {
                    listToFill.add(t);
                }
            }
        }
    }
}
