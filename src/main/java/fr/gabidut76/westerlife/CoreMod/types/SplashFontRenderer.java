package fr.gabidut76.westerlife.CoreMod.types;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.resources.DefaultResourcePack;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.SimpleResource;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import java.io.IOException;

public class SplashFontRenderer extends FontRenderer {
    private final Texture fontTexture = new Texture(new ResourceLocation("textures/font/ascii.png"), null, false);


    public SplashFontRenderer() {

        super(Minecraft.getMinecraft().gameSettings, new ResourceLocation("minecraft:textures/font/ascii.png"), null, false);
        super.onResourceManagerReload(null);
    }

    @Override
    protected void bindTexture(@Nonnull ResourceLocation location) {
        if (location != locationFontTexture) throw new IllegalArgumentException();
        System.out.println(fontTexture);
        new Texture(new ResourceLocation("textures/font/ascii.png"), null, false).bind();
    }

    @Nonnull
    @Override
    protected IResource getResource(@Nonnull ResourceLocation location) throws IOException {
        DefaultResourcePack pack = Minecraft.getMinecraft().defaultResourcePack;
        return new SimpleResource(pack.getPackName(), location, pack.getInputStream(location), null, null);
    }
}