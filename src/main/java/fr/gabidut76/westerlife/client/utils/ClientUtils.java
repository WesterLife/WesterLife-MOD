package fr.gabidut76.westerlife.client.utils;

import fr.betterlights.BetterLightsMod;
import fr.betterlights.gl.shader.ShaderManager;
import net.optifine.shaders.Shaders;

import static org.lwjgl.opengl.GL11.glGetInteger;
import static org.lwjgl.opengl.GL20.*;

public class ClientUtils {

    public static int westerlife_bloom = -1;
    public static int westerlife_metalness = -1;

    public static void initBloom(float bloomValue) {
        if(Shaders.isShadowPass) return;
        if(westerlife_bloom == -1) {
            westerlife_bloom = glGetUniformLocation(glGetInteger(GL_CURRENT_PROGRAM), "westerlife_bloom");
        }

        glUniform1f(westerlife_bloom, bloomValue);

    }

    public static void endBloom() {
        if(!Shaders.isShadowPass) glUniform1f(westerlife_bloom, 0.0f);
    }

    public static void initMetalness(float metalnessValue) {
        if(Shaders.isShadowPass) return;
        if(westerlife_metalness == -1) {
            westerlife_metalness = glGetUniformLocation(glGetInteger(GL_CURRENT_PROGRAM), "westerlife_metalness");
        }

        glUniform1f(westerlife_metalness, metalnessValue);

    }

    public static void endMetalness() {
        if(!Shaders.isShadowPass) glUniform1f(westerlife_metalness, 0.0f);
    }

}
