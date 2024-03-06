package fr.gabidut76.westerlife.client.gui.other;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.client.GuiIngameForge;

public class GuiWesterIngame extends GuiIngameForge {
    protected final GuiNewChat persistantChatGUI;
    public GuiWesterIngame(Minecraft mc) {
        super(mc);
        this.persistantChatGUI = new GuiWesterChat(mc);
    }
    public GuiNewChat getChatGUI()
    {
        return this.persistantChatGUI;
    }

    @Override
    public void renderGameOverlay(float partialTicks) {
        super.renderGameOverlay(partialTicks);
        ScaledResolution scaledresolution = new ScaledResolution(this.mc);
        int j = scaledresolution.getScaledHeight();

        GlStateManager.pushMatrix();
        GlStateManager.translate(0.0F, (float)(j - 48), 0.0F);
        this.mc.profiler.startSection("chat");
        this.persistantChatGUI.drawChat(this.updateCounter);
        this.mc.profiler.endSection();
        GlStateManager.popMatrix();
    }
}
