package fr.yan36.westerlife.common;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
}
