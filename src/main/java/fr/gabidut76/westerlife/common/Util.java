package fr.gabidut76.westerlife.common;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import com.jme3.math.Vector3f;
import fr.aym.acsguis.cssengine.font.CssFontHelper;
import fr.gabidut76.westerlife.common.blocks.dynamx.BlockAIPoint;

import fr.gabidut76.westerlife.common.utils.AABB;
import net.minecraft.block.Block;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.*;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.storage.MapStorage;
import org.lwjgl.opengl.GL11;

import java.util.*;

import static net.minecraft.world.World.MAX_ENTITY_RADIUS;

public class Util {

    public static int randBetween(int min, int max) {
        return min + (int) (Math.random() * ((max - min) + 1));
    }

    public static int getAngleBetweenTwoPoint(BlockPos p1, BlockPos p2) {
        double angle = Math.toDegrees(Math.atan2(p2.getZ() - p1.getZ(), p2.getX() - p1.getX()));
        if (angle < 0) {
            angle += 360;
        }
        return (int) angle;
    }

    public static BlockPos getNearestBlockAIPoint(World w, BlockPos from, int range) {
        // get all blocks in a radius of range
        List<BlockPos> blocks = new ArrayList<BlockPos>();
        for (int x = from.getX() - range; x < from.getX() + range; x++) {
            for (int y = from.getY() - range; y < from.getY() + range; y++) {
                for (int z = from.getZ() - range; z < from.getZ() + range; z++) {
                    BlockPos pos = new BlockPos(x - 1, y, z - 1);
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

        if (blocks.size() == 1 || blocks.size() == 0)
            return new BlockPos(-1, -1, -1);

        if ((blocks.get(1) != null)) return blocks.get(1);
        else return new BlockPos(-1, -1, -1);

    }


    public static boolean isProximity(EntityPlayer player, BlockPos pos, int range) {
        return player.getDistanceSq(pos) < range * range;
    }


    public static BlockPos parseBlockPosFromString(String stringPos) {
        String[] xyz = stringPos.split(",");
        if (xyz.length == 3) {
            int x = Integer.parseInt(xyz[0]);
            int y = Integer.parseInt(xyz[1]);
            int z = Integer.parseInt(xyz[2]);
            return new BlockPos(x, y, z);
        } else {
            System.err.println("The parsed BlockPos value has to be like that: \"x, y, z\"");
        }
        return BlockPos.ORIGIN;
    }

    public static String blockPosToString(BlockPos pos) {
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

    public static <T extends Entity> List<T> getEntitiesWithinAABB(World w, Class<? extends T> clazz, AABB aabb) {
        int j2 = MathHelper.floor((aabb.minX - MAX_ENTITY_RADIUS) / 16.0D);
        int k2 = MathHelper.ceil((aabb.maxX + MAX_ENTITY_RADIUS) / 16.0D);
        int l2 = MathHelper.floor((aabb.minZ - MAX_ENTITY_RADIUS) / 16.0D);
        int i3 = MathHelper.ceil((aabb.maxZ + MAX_ENTITY_RADIUS) / 16.0D);
        List<T> list = Lists.newArrayList();

        for (int j3 = j2; j3 < k2; ++j3) {
            for (int k3 = l2; k3 < i3; ++k3) {
                if (w.isChunkGeneratedAt(j3, k3)) {
                    getEntitiesOfTypeWithinAABB(w.getChunk(j3, k3), clazz, aabb, list, EntitySelectors.NOT_SPECTATING);
                }
            }
        }

        return list;
    }


    public static <T extends Entity> void getEntitiesOfTypeWithinAABB(Chunk w, Class<? extends T> entityClass, AABB aabb, List<T> listToFill, Predicate<? super T> filter) {
        int i = MathHelper.floor((aabb.minY - World.MAX_ENTITY_RADIUS) / 16.0D);
        int j = MathHelper.floor((aabb.maxY + World.MAX_ENTITY_RADIUS) / 16.0D);
        i = MathHelper.clamp(i, 0, w.getEntityLists().length - 1);
        j = MathHelper.clamp(j, 0, w.getEntityLists().length - 1);

        for (int k = i; k <= j; ++k) {
            for (T t : w.getEntityLists()[k].getByClass(entityClass)) {
                if (t.getEntityBoundingBox().intersects(aabb.minX, aabb.minY, aabb.minZ, aabb.maxX, aabb.maxY, aabb.maxZ) && (filter == null || filter.apply(t))) {
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
            String line = (String) lines.get(i);
            x = (wrapWidth + -renderer.getStringWidth(line)) / 2;
            renderer.drawString(line, x, y, textColor);
            y += renderer.FONT_HEIGHT;
        }
    }

    private static void renderSplitStringCentered2(FontRenderer renderer, String str, int x, int y, int wrapWidth, int textColor) {
        List<String> lines = renderer.listFormattedStringToWidth(str, wrapWidth);
        for (int i = 0; i < lines.size() && i < 4; i++) {
            String line = (String) lines.get(i);
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

    public static EntityEquipmentSlot equipementFromSlotID(int slotID) {
        for (EntityEquipmentSlot entityequipmentslot : EntityEquipmentSlot.values()) {
            if (entityequipmentslot.getSlotIndex() == slotID) {
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
                    if (stack.getTagCompound() == null) {
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

    public static void drawTextWithFont(Vector3f pos, Vector3f scale, Vector3f rotation, String text, int[] color, String font) {
        drawTextWithFont(pos, scale, rotation, text, color, font, 0.0F);
    }

    public static void drawTextWithFont(Vector3f pos, Vector3f scale, Vector3f rotation, String text, int[] color, String font, float spacing) {
        if (pos != null) {
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


    public NBTTagCompound serializeEntityPlayer(EntityPlayer p) {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setString("name", p.getName());
        nbt.setInteger("id", p.getEntityId());
        nbt.setTag("data", p.serializeNBT());
        nbt.setUniqueId("uuid", p.getUniqueID());
        System.out.println(nbt);
        return nbt;
    }


    public static <T> boolean listEqualsIgnoreOrder(List<T> list1, List<T> list2) {
        return new HashSet<>(list1).equals(new HashSet<>(list2));
    }

    public static void drawGradientRect(int left, int top, int right, int bottom, int coltl, int coltr, int colbl,
                                        int colbr) {
        drawGradientRect(left, top, right, bottom, coltl, coltr, colbl, colbr, 0);
    }

    /**
     * Draws a rectangle with possibly different colors in different corners
     *
     * @param left
     * @param top
     * @param right
     * @param bottom
     * @param coltl  - the color of the top left corner
     * @param coltr  - the color of the top right corner
     * @param colbl  - the color of the bottom left corner
     * @param colbr  - the color of the bottom right corner
     * @param zLevel
     */
    public static void drawGradientRect(int left, int top, int right, int bottom, int coltl, int coltr, int colbl,
                                        int colbr, int zLevel) {
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
        GlStateManager.shadeModel(GL11.GL_SMOOTH);

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
        buffer.pos(right, top, zLevel).color((coltr & 0x00ff0000) >> 16, (coltr & 0x0000ff00) >> 8,
                (coltr & 0x000000ff), (coltr & 0xff000000) >>> 24).endVertex();
        buffer.pos(left, top, zLevel).color((coltl & 0x00ff0000) >> 16, (coltl & 0x0000ff00) >> 8, (coltl & 0x000000ff),
                (coltl & 0xff000000) >>> 24).endVertex();
        buffer.pos(left, bottom, zLevel).color((colbl & 0x00ff0000) >> 16, (colbl & 0x0000ff00) >> 8,
                (colbl & 0x000000ff), (colbl & 0xff000000) >>> 24).endVertex();
        buffer.pos(right, bottom, zLevel).color((colbr & 0x00ff0000) >> 16, (colbr & 0x0000ff00) >> 8,
                (colbr & 0x000000ff), (colbr & 0xff000000) >>> 24).endVertex();
        tessellator.draw();

        GlStateManager.shadeModel(GL11.GL_FLAT);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }

    public static int hsvToRgb(int hue, int saturation, int value) {
        // Source: en.wikipedia.org/wiki/HSL_and_HSV#Converting_to_RGB#From_HSV
        hue %= 360;
        float s = (float) saturation / 100;
        float v = (float) value / 100;
        float c = v * s;
        float h = (float) hue / 60;
        float x = c * (1 - Math.abs(h % 2 - 1));
        float r, g, b;
        switch (hue / 60) {
            case 0:
                r = c;
                g = x;
                b = 0;
                break;
            case 1:
                r = x;
                g = c;
                b = 0;
                break;
            case 2:
                r = 0;
                g = c;
                b = x;
                break;
            case 3:
                r = 0;
                g = x;
                b = c;
                break;
            case 4:
                r = x;
                g = 0;
                b = c;
                break;
            case 5:
                r = c;
                g = 0;
                b = x;
                break;
            default:
                return 0;
        }
        float m = v - c;
        return ((int) ((r + m) * 255) << 16) | ((int) ((g + m) * 255) << 8) | ((int) ((b + m) * 255));
    }

    public static int[] rgbToHsv(int rgb) {
        // Source: en.wikipedia.org/wiki/HSV_and_HSL#Formal_derivation
        float r = (float) ((rgb & 0xff0000) >> 16) / 255;
        float g = (float) ((rgb & 0x00ff00) >> 8) / 255;
        float b = (float) (rgb & 0x0000ff) / 255;
        float M = r > g ? (r > b ? r : b) : (g > b ? g : b);
        float m = r < g ? (r < b ? r : b) : (g < b ? g : b);
        float c = M - m;
        float h;
        if (M == r) {
            h = ((g - b) / c);
            while (h < 0)
                h += 6;
            h %= 6;
        } else if (M == g) {
            h = ((b - r) / c) + 2;
        } else {
            h = ((r - g) / c) + 4;
        }
        h *= 60;
        float s = c / M;
        return new int[]{c == 0 ? -1 : (int) h, (int) (s * 100), (int) (M * 100)};
    }
}
