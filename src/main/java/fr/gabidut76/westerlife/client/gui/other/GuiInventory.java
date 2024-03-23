package fr.gabidut76.westerlife.client.gui.other;

import fr.gabidut76.westerlife.westercore.Main;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

// used inventory
public class GuiInventory extends GuiContainer {
    public static final ResourceLocation texture = new ResourceLocation(Main.MODID, "textures/gui/inventory/inventory.png");

    public GuiInventory(Container inventorySlotsIn) {

        super(inventorySlotsIn);
        System.out.println(inventorySlotsIn.inventorySlots);

    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {

        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
//
//        for (int i = 36; i < 41; i++) {
//            System.out.println(inventorySlots.getSlot(i).isEnabled() && inventorySlots.getSlot(i).getStack().isEmpty());
//        }

    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        this.drawDefaultBackground();
        this.mc.getTextureManager().bindTexture(texture);
        int k = (this.width ) / 2;
        int l = (this.height) / 2;
        GlStateManager.pushMatrix();
        GlStateManager.translate(0,0,0);
        GlStateManager.glBegin(GL11.GL_QUADS);
        GlStateManager.glTexCoord2f(0, 0);
        GlStateManager.glVertex3f(k - 120, l - 80, 0);
        GlStateManager.glTexCoord2f(0, 1);
        GlStateManager.glVertex3f(k - 120, l + 80, 0);
        GlStateManager.glTexCoord2f(1, 1);
        GlStateManager.glVertex3f(k + 120, l + 80, 0);
        GlStateManager.glTexCoord2f(1, 0);
        GlStateManager.glVertex3f(k + 120, l - 80, 0);
        GlStateManager.glEnd();
        GlStateManager.popMatrix();

        GlStateManager.pushMatrix();
        net.minecraft.client.gui.inventory.GuiInventory.drawEntityOnScreen(k - 70, l + 30, 40, (float)(k - 120) - mouseX, (float)(l + 82 - 50) - mouseY, Minecraft.getMinecraft().player);
        GlStateManager.popMatrix();
    }

}
