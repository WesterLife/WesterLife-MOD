package fr.yan36.westerlife.common;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import com.jme3.math.Vector3f;
import fr.aym.acsguis.cssengine.font.CssFontHelper;
import fr.dynamx.client.renders.mesh.VertexBuffer;
import fr.yan36.westerlife.common.blocks.dynamx.BlockAIPoint;
import fr.yan36.westerlife.common.blocks.tileentity.TileEntitySyncClient;
import fr.yan36.westerlife.common.init.DynamXInit;
import fr.yan36.westerlife.common.objects.KitWSD;
import fr.yan36.westerlife.common.utils.AABB;
import jme3utilities.Validate;
import net.minecraft.block.Block;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.*;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.storage.MapStorage;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.*;

import static net.minecraft.world.World.MAX_ENTITY_RADIUS;

public class Util {
    public static KitWSD getKitTest(World world) {
        MapStorage storage = world.getMapStorage();
        assert storage != null;
        KitWSD instance = (KitWSD) storage.getOrLoadData(KitWSD.class, "test");

        if (instance == null) {
            instance = new KitWSD("caca ?");
            storage.setData("test", instance);
        }
        return instance;
    }

    public static int getAngleBetweenTwoPoint(BlockPos p1, BlockPos p2) {
        double angle = Math.toDegrees(Math.atan2(p2.getZ() - p1.getZ(), p2.getX() - p1.getX()));
        if(angle < 0){
            angle += 360;
        }
        return (int) angle;
    }

    public static BlockPos getNearestBlockAIPoint(World w,BlockPos from, int range) {
        // get all blocks in a radius of range
        List<BlockPos> blocks = new ArrayList<BlockPos>();
        for (int x = from.getX() - range; x < from.getX() + range; x++) {
            for (int y = from.getY() - range; y < from.getY() + range; y++) {
                for (int z = from.getZ() - range; z < from.getZ() + range; z++) {
                    BlockPos pos = new BlockPos(x - 1 , y , z - 1);
                    if (w.getBlockState(pos).getBlock() instanceof BlockAIPoint) {
                        blocks.add(pos);
                    }
                }
            }
        }

        // sort by distance
        Collections.sort(blocks, new Comparator<BlockPos>() {
            @Override
            public int compare(BlockPos o1, BlockPos o2) {
                return (int) (o1.distanceSq(from) - o2.distanceSq(from));
            }
        });

        if(blocks.size() == 1 || blocks.size() == 0)
            return new BlockPos(-1,-1,-1);

        if((blocks.get(1) != null)) return blocks.get(1);
        else return new BlockPos(-1,-1,-1);

    }

    public static void setKitTest(World world, String arg) {
        MapStorage storage = world.getMapStorage();
        assert storage != null;
        KitWSD instance = (KitWSD) storage.getOrLoadData(KitWSD.class, "test");

        if (instance == null) {
            instance = new KitWSD(arg);
            storage.setData("test", instance);
        }
        instance.markDirty();

    }

    public static boolean isProximity(EntityPlayer player, BlockPos pos, int range) {
        return player.getDistanceSq(pos) < range * range;
    }


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

    public static void drawSplitString(FontRenderer renderer, String str, int x, int y, int wrapWidth, int textColor) {
        str = trimStringNewline(str);
        renderSplitStringCentered(renderer, str, x, y, wrapWidth, textColor);
    }
    public static void drawSplitString2(FontRenderer renderer, String str, int x, int y, int wrapWidth, int textColor) {
        str = trimStringNewline(str);
        renderSplitStringCentered2(renderer, str, x, y, wrapWidth, textColor);
    }
    private static void renderSplitStringCentered(FontRenderer renderer, String str, int x, int y, int wrapWidth, int textColor) {
        List<String> lines = renderer.listFormattedStringToWidth(str, 55);
        for (int i = 0; i < lines.size() && i < 4; i++) {
            String line = (String)lines.get(i);
            x = (wrapWidth + -renderer.getStringWidth(line)) / 2;
            renderer.drawString(line, x, y, textColor);
            y += renderer.FONT_HEIGHT;
        }
    }

    private static void renderSplitStringCentered2(FontRenderer renderer, String str, int x, int y, int wrapWidth, int textColor) {
        List<String> lines = renderer.listFormattedStringToWidth(str, wrapWidth);
        for (int i = 0; i < lines.size() && i < 4; i++) {
            String line = (String)lines.get(i);
            x = -renderer.getStringWidth(line) / 2;
            renderer.drawString(line, x, y, textColor);
            y += renderer.FONT_HEIGHT;
        }
    }

    private static String trimStringNewline(String text) {
        while (text != null && text.endsWith("\n"))
            text = text.substring(0, text.length() - 1);
        return text;
    }

    public static EntityEquipmentSlot equipementFromSlotID(int slotID)
    {
        for (EntityEquipmentSlot entityequipmentslot : EntityEquipmentSlot.values())
        {
            if (entityequipmentslot.getSlotIndex() == slotID)
            {
                return entityequipmentslot;
            }
        }

        throw new IllegalArgumentException("Invalid slot '" + slotID + "'");
    }


    public static String itemStackListToString(ArrayList<ItemStack> list) {
        StringBuilder str = new StringBuilder();
        for (ItemStack item : list) {
            str.append(item.getItem().getRegistryName().toString()).append("*").append(item.getDisplayName()).append("*").append(item.getItemDamage()).append("*").append(item.getCount()).append(";");

        }
        return str.toString();
    }

    public static ArrayList<ItemStack> stringToItemStackList(String str) {
        ArrayList<ItemStack> list = new ArrayList<>();
        String[] items = str.split(";");
        for (String item : items) {
            String[] itemData = item.split("\\*");
            if (itemData.length == 4) {
                Item i = Item.REGISTRY.getObject(new ResourceLocation(itemData[0]));
                if (i != null) {
                    ItemStack stack = new ItemStack(i, Integer.parseInt(itemData[3]), Integer.parseInt(itemData[2]));
                    stack.setStackDisplayName("§f" + itemData[1]);
                    if(stack.getTagCompound() == null) {
                        stack.setTagCompound(new NBTTagCompound());
                    }
                    stack.getTagCompound().setBoolean("isFromKit", true);
                    list.add(stack);
                }
            }
        }
        return list;
    }
    public static boolean hasPermission(EntityPlayerMP player, String permissionName) {
        return player.canUseCommand(4, permissionName);
    }

    // AcsGui lib draw text

    public static void drawTextWithFont(Vector3f pos, Vector3f scale, Vector3f rotation, String text, int[] color, String font){
        drawTextWithFont(pos, scale, rotation, text, color, font, 0.0F);
    }

    public static void drawTextWithFont(Vector3f pos, Vector3f scale, Vector3f rotation, String text, int[] color, String font, float spacing) {
        if(pos != null) {
            GlStateManager.pushMatrix();
            GlStateManager.color(1, 1, 1, 1);
            GlStateManager.translate(pos.x, pos.y, pos.z);
            GlStateManager.rotate(180, 1, 0, 0);
            GlStateManager.rotate(180, 0, 1, 0);
            float rotate = rotation.x;
            if (rotate != 0)
                GlStateManager.rotate(rotate, 1, 0, 0);
            rotate = rotation.y;
            if (rotate != 0)
                GlStateManager.rotate(rotate, 0, 1, 0);
            rotate = rotation.z;
            if (rotate != 0)
                GlStateManager.rotate(rotate, 0, 0, 1);
            GlStateManager.scale(scale.x / 40, scale.y / 40, scale.z / 40);
            GlStateManager.disableLighting();

            CssFontHelper.pushDrawing(new ResourceLocation(font), Collections.emptyList());
            GlStateManager.scale(0.05, 0.05, 0.05);
            String[] lines = text.split("\\\\n");
            for (String line : lines) {
                String line2 = line.replace("\\n", "");
                CssFontHelper.draw((float) (-CssFontHelper.getBoundFont().getWidth(line2) / 2), 0, line2, (color[0] << 16) | (color[1] << 8) | color[2]);
                GlStateManager.translate(0, (spacing + 1) * 100, 0);
            }
            CssFontHelper.popDrawing();
            GlStateManager.enableLighting();
            GlStateManager.resetColor();
            GlStateManager.popMatrix();
        }
    }

    public static boolean isBlockBurning(World w, Block b, BlockPos p) {

        for (EnumFacing face : EnumFacing.values()) {
            if (b.isFireSource(w, p, face)) {
                return true;
            }
        }
        return false;
    }


}
