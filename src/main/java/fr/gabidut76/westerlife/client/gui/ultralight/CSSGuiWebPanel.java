package fr.gabidut76.westerlife.client.gui.ultralight;

import com.labymedia.ultralight.input.UltralightScrollEvent;
import com.labymedia.ultralight.input.UltralightScrollEventType;
import fr.aym.acsguis.component.panel.GuiPanel;
import fr.gabidut76.westerlife.common.objects.StyleToLoad;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

@StyleToLoad
public class CSSGuiWebPanel extends GuiPanel {


    private WebController viewController;


    public CSSGuiWebPanel(String url) {
        WebGuiManager.registerAndGetViewController(url, url);
    }

    @Override
    public void drawBackground(int mouseX, int mouseY, float partialTicks) {
        super.drawBackground(mouseX, mouseY, partialTicks);
    }

    @Override
    public void guiClose() {
        super.guiClose();
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {
        super.keyTyped(typedChar, keyCode);
        if(keyCode == Keyboard.KEY_ESCAPE) {
            this.guiClose();
            return;
        }
        this.viewController.onKeyDown(typedChar, keyCode);
    }

    @Override
    public void drawForeground(int x, int y, float f) {
        int button;
        boolean buttonState;
        int scrollDelta;

        GlStateManager.pushMatrix();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        this.viewController.render();
//        GuiUtils.drawRect(getScreenX(), getScreenY(), getRenderMaxX() - getRenderMinX(), getRenderMaxY() - getRenderMinY(), 1.0f);
        GlStateManager.popMatrix();

        button = Mouse.getEventButton();
        buttonState = Mouse.getEventButtonState();
        scrollDelta = Mouse.getEventDWheel();

        int mouseY = ((y - getScreenY()) + 1) * mc.displayHeight;
        int mouseX = (x - getScreenX()) * mc.displayWidth;

        this.viewController.onMouseMove((int) ((float) mouseX * 0.71428573f), (int) ((float) mouseY * 0.71428573f));
        System.out.printf("Mouse position: (%d, %d)%n", (int) ((float) mouseX * 0.71428573f), (int) ((float) mouseY * 0.71428573f));
        this.viewController.onMouseClick((int) ((float) mouseX * 0.71428573f), (int) ((float) mouseY * 0.71428573f), button, buttonState);
        UltralightScrollEvent scrollEvent = new UltralightScrollEvent();
        scrollEvent.type(UltralightScrollEventType.BY_PIXEL);
        scrollEvent.deltaX(scrollDelta);
        scrollEvent.deltaY(scrollDelta);
        this.viewController.getView().fireScrollEvent(scrollEvent);
    }
}
