package fr.yan36.westerlife.common;

import net.minecraft.util.math.BlockPos;

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
}
