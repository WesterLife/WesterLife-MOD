package fr.yan36.westerlife.client.phone.util;

import fr.aym.acsguis.component.layout.GuiScaler;
import fr.aym.acsguis.component.panel.GuiFrame;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.util.Collections;
import java.util.List;

public class PhoneFrame extends GuiFrame {


    public PhoneFrame() {
        super(new GuiScaler.AdjustToScreenSize(true,1,1));
        GL11.glPopMatrix();
        Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("westerlife:textures/gui/phone/font.png"));
        this.drawTexturedBackground(0,0, 0.0F);
        GL11.glPushMatrix();
    }

    @Override
    public List<ResourceLocation> getCssStyles() {
        return Collections.singletonList(new ResourceLocation("dynamxmod:css/phoneframe.css"));
    }
}
