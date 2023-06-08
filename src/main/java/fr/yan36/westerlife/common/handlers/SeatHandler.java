package fr.yan36.westerlife.common.handlers;


import fr.yan36.westerlife.Main;
import fr.yan36.westerlife.common.blocks.dynamx.BlockChair;
import fr.yan36.westerlife.common.entities.EntitySeat;
import fr.yan36.westerlife.common.utils.Seatutils;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityMountEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid= Main.MODID)
public class SeatHandler {
    @SubscribeEvent
    public static void onRightClickBlock(RightClickBlock event)
    {
        if(!event.getWorld().isRemote && !Seatutils.isPlayerSitting(event.getEntityPlayer()))
        {
            World world = event.getWorld();
            BlockPos pos = event.getPos();
            IBlockState state = world.getBlockState(pos);
            Block block = world.getBlockState(pos).getBlock();
            EntityPlayer player = event.getEntityPlayer();
            if(isValidBlock(world, pos, state, block) && isPlayerInRange(player, pos) && player.getHeldItemMainhand().isEmpty())
            {



                EntitySeat sit = new EntitySeat(world, pos);

                if(Seatutils.addSitEntity(world, pos, sit))
                {
                    world.spawnEntity(sit);
                    player.startRiding(sit);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onBreak(BreakEvent event)
    {
        if(!event.getWorld().isRemote)
        {
            EntitySeat entity = Seatutils.getSitEntity(event.getWorld(), event.getPos());

            if(entity != null && Seatutils.removeSitEntity(event.getWorld(), event.getPos()))
                entity.setDead();
        }
    }

    @SubscribeEvent
    public static void onEntityMount(EntityMountEvent event)
    {
        if(!event.getWorldObj().isRemote && event.isDismounting())
        {
            Entity entity = event.getEntityBeingMounted();

            if(entity instanceof EntitySeat && Seatutils.removeSitEntity(event.getWorldObj(), entity.getPosition()))
                entity.setDead();
        }

    }

    /**
     * Returns whether or not the given block can be sat on
     * @param world The world to check in
     * @param pos The position to check at
     * @param state The block state at the given position in the given world
     * @param block The block to check
     * @return true if the given block can be sat one, false otherwhise
     */
    private static boolean isValidBlock(World world, BlockPos pos, IBlockState state, Block block)
    {

        return block instanceof BlockChair;
    }

    /**
     * Checks whether the given block is a specific block from a mod. Used to support stairs/slabs from other mods that don't work with Sit by default.
     * @param world The world to check in
     * @param pos The position to check at
     * @param block The block to check
     * @return true if the block is a block to additionally support, false otherwise
     */
    private static boolean isModBlock(World world, BlockPos pos, Block block)
    {
        return false;
    }

    /**
     * Returns whether or not the player is close enough to the block to be able to sit on it
     * @param player The player
     * @param pos The position of the block to sit on
     * @return true if the player is close enough, false otherwhise
     */
    private static boolean isPlayerInRange(EntityPlayer player, BlockPos pos)
    {
        BlockPos playerPos = player.getPosition();

//        if(Configuration.blockReachDistance == 0) //player has to stand on top of the block
//            return playerPos.getY() - pos.getY() <= 1 && playerPos.getX() - pos.getX() == 0 && playerPos.getZ() - pos.getZ() == 0;

        pos = pos.add(0.5D, 0.5D, 0.5D);

        AxisAlignedBB range = new AxisAlignedBB(pos.getX() + 3, pos.getY() + 3, pos.getZ() + 3, pos.getX() - 3, pos.getY() - 3, pos.getZ() - 3);

        playerPos = playerPos.add(0.5D, 0.5D, 0.5D);
        return range.minX <= playerPos.getX() && range.minY <= playerPos.getY() && range.minZ <= playerPos.getZ() && range.maxX >= playerPos.getX() && range.maxY >= playerPos.getY() && range.maxZ >= playerPos.getZ();
    }
}
