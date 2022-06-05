package fr.yan36.westerlife.common.blocks.tileentity;

import fr.dynamx.common.blocks.TEDynamXBlock;
import fr.dynamx.common.contentpack.type.objects.BlockObject;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;

import java.awt.*;

public class TESign extends TileEntitySyncClient implements ITickable {
    private BlockObject b;


    private static String text = "Panneau";


    public TESign(){
        super(null);
    }

    public TESign(BlockObject<?> blockObjectInfo) {
        super(blockObjectInfo);
        this.b = blockObjectInfo;
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);
        text = tagCompound.getString("text");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);
        tagCompound.setString("text", text);
        return tagCompound;
    }

    //time en tick
    @Override
    public void update() {
        if (this.world.isRemote) {
            GlStateManager.pushMatrix();
            // GlStateManager.translate(0.278, 1.3, -2.769);
            GlStateManager.rotate(180, 0, 0, 1);
            GlStateManager.scale(2, 2, 2);

            // drawSplitString(Minecraft.getMinecraft().fontRenderer, carData.get(e.carEntity.getUniqueID().toString()), 0, 0, 0, Color.black.getRGB());
            Minecraft.getMinecraft().fontRenderer.drawString(text, 0, 0, Color.black.getRGB());
            GlStateManager.color(1,1,1);
            GlStateManager.popMatrix();
            System.out.println(text);
        }
    }

    public static void setText(String text){
        TESign te = (TESign) Minecraft.getMinecraft().world.getTileEntity(Minecraft.getMinecraft().player.getPosition());
        TESign.text = text;
    }
    public String getText(){
        return text;
    }
}
