package fr.gabidut76.westerlife.client.gui.guielements;

import fr.aym.acsguis.component.panel.GuiPanel;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.*;

public class GuiColorPicker extends GuiPanel {


    private static float red = 0.0f;
    private static float green = 0.0f;
    private static float blue = 0.0f;

    public GuiColorPicker() {
        super();
    }

    @Override
    public void drawBackground(int mouseX, int mouseY, float partialTicks) {
        super.drawBackground(mouseX, mouseY, partialTicks);

        // Draw the color picker
        GlStateManager.pushMatrix();
//        GlStateManager.translate(getScreenX(), getScreenY(), 0);

        // Set OpenGL to use GUI coordinates
        glMatrixMode(GL_PROJECTION);
        glPushMatrix();
        glLoadIdentity();
        glOrtho(0, getWidth(), getHeight(), 0, -1, 1);
        glMatrixMode(GL_MODELVIEW);

        drawColorPicker();

        // Restore OpenGL state
        glMatrixMode(GL_PROJECTION);
        glPopMatrix();
        glMatrixMode(GL_MODELVIEW);

        GlStateManager.popMatrix();
    }

    private static void drawColorPicker() {
        // Définir la position du sélecteur de couleurs
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();
        GL11.glTranslatef(100.0f, 100.0f, 0.0f);

        // Dessiner un rectangle pour le fond
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glColor3f(0.8f, 0.8f, 0.8f);
        GL11.glVertex2f(0.0f, 0.0f);
        GL11.glVertex2f(200.0f, 0.0f);
        GL11.glVertex2f(200.0f, 200.0f);
        GL11.glVertex2f(0.0f, 200.0f);
        GL11.glEnd();

        // Dessiner un curseur pour la couleur sélectionnée
        GL11.glBegin(GL11.GL_POINTS);
        GL11.glColor3f(red, green, blue);
        GL11.glVertex2f(100.0f + (red * 100.0f), 100.0f + (green * 100.0f));
        GL11.glEnd();

        // Gérer la sélection de la couleur
        if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
            green += 0.01f;
            if (green > 1.0f) {
                green = 1.0f;
            }
        } else if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
            green -= 0.01f;
            if (green < 0.0f) {
                green = 0.0f;
            }
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
            red -= 0.01f;
            if (red < 0.0f) {
                red = 0.0f;
            }
        } else if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
            red += 0.01f;
            if (red > 1.0f) {
                red = 1.0f;
            }
        }
    }
}
