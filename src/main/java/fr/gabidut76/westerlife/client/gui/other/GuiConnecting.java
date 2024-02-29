package fr.gabidut76.westerlife.client.gui.other;

import fr.gabidut76.westerlife.client.gui.acs.CSSGuiMainMenu;
import fr.gabidut76.westerlife.common.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

import java.awt.*;
import java.io.IOException;

public class GuiConnecting extends net.minecraft.client.multiplayer.GuiConnecting {

    public GuiConnecting(Minecraft mcIn) throws IOException {
        this(new CSSGuiMainMenu().getGuiScreen(), mcIn, "localhost", 25565);
    }
    public GuiConnecting(GuiScreen parent, Minecraft mcIn, ServerData serverDataIn) {
        super(parent, mcIn, serverDataIn);
    }

    public GuiConnecting(GuiScreen parent, Minecraft mcIn, String hostName, int port) {
        super(parent, mcIn, hostName, port);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {

        Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("westerlife", "textures/gui/inventory.png"));
        this.drawTexturedModalRect(0, 0, 0, 0, this.width, this.height);





        for (int i = 0; i < this.buttonList.size(); ++i)
        {
            ((GuiButton)this.buttonList.get(i)).drawButton(this.mc, mouseX, mouseY, partialTicks);
        }

        for (int j = 0; j < this.labelList.size(); ++j)
        {
            ((GuiLabel)this.labelList.get(j)).drawLabel(this.mc, mouseX, mouseY);
        }



        GlStateManager.scale(2, 2, 2);

        Util.drawSplitString(Minecraft.getMinecraft().fontRenderer, "WesterLife", 0, 0, 0xFFFFFF, Color.WHITE.getRGB());


        GlStateManager.scale(-2, -2,- 2);
    }
}
