package fr.yan36.westerlife.client.gui.ultralight;

import com.labymedia.ultralight.UltralightPlatform;
import com.labymedia.ultralight.UltralightRenderer;
import com.labymedia.ultralight.UltralightView;
import com.labymedia.ultralight.bitmap.UltralightBitmap;
import com.labymedia.ultralight.bitmap.UltralightBitmapSurface;
import com.labymedia.ultralight.input.*;
import com.labymedia.ultralight.javascript.JavascriptContextLock;
import com.labymedia.ultralight.math.IntRect;
import fr.yan36.westerlife.client.gui.ultralight.listeners.LoadListener;
import fr.yan36.westerlife.client.gui.ultralight.mapper.KeyMap;
import fr.yan36.westerlife.client.gui.ultralight.support.js.JavaScriptBridge;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;

import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.*;


public class WebController {
    private final UltralightPlatform platform;
    private final UltralightRenderer renderer;
    private final UltralightView view;

    private final LoadListener loadListener;
    private final JavaScriptBridge bridge;

    private int glTexture;
    private long lastJavascriptGarbageCollections;

    /**
     * Constructs a new {@link WebController} and retrieves the platform.
     */
    public WebController(UltralightRenderer renderer, UltralightView view) {
        this.platform = UltralightPlatform.instance();

        this.renderer = renderer;


        this.view = view;
        this.bridge = new JavaScriptBridge(view);
        this.loadListener = new LoadListener(view);
        this.view.setLoadListener(loadListener);

        this.glTexture = -1;
        this.lastJavascriptGarbageCollections = 0;
    }


    /**
     * Loads the specified URL into this controller.
     *
     * @param url The URL to load
     */
    public void loadURL(String url) {
        this.view.loadURL(url);
    }

    public JavaScriptBridge getJSBridge() {
        return bridge;
    }

    /**
     * Updates and renders the renderer
     */
    public void update() {
        this.renderer.update();
        this.renderer.render();

        if(lastJavascriptGarbageCollections == 0) {
            lastJavascriptGarbageCollections = System.currentTimeMillis();
        } else if(System.currentTimeMillis() - lastJavascriptGarbageCollections > 1000) {
            try(JavascriptContextLock lock = this.view.lockJavascriptContext()) {
                lock.getContext().garbageCollect();
            }
            lastJavascriptGarbageCollections = System.currentTimeMillis();
        }
    }

    /**
     * Resizes the web view.
     *
     * @param width  The new view width
     * @param height The new view height
     */
    public void resize(int width, int height) {
        this.view.resize(width, height);
    }

    /**
     * Render the current image using OpenGL
     */
    public void render() {
        if(glTexture == -1) {
            createGLTexture();
        }

        UltralightBitmapSurface surface = (UltralightBitmapSurface) this.view.surface();
        UltralightBitmap bitmap = surface.bitmap();

        int width = (int) view.width();
        int height = (int) view.height();

        // Prepare OpenGL for 2D textures and bind our texture
        glEnable(GL_TEXTURE_2D);

        GlStateManager.bindTexture(this.glTexture);


        IntRect dirtyBounds = surface.dirtyBounds();

        if(dirtyBounds.isValid()) {
            ByteBuffer imageData = bitmap.lockPixels();
            glPixelStorei(GL_UNPACK_ROW_LENGTH, (int) bitmap.rowBytes() / 4);
            if(dirtyBounds.width() == width && dirtyBounds.height() == height) {
                // Update full image
                glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, width, height, 0, GL_BGRA, GL_UNSIGNED_INT_8_8_8_8_REV, imageData);
                glPixelStorei(GL_UNPACK_ROW_LENGTH, 0);
            } else {
                // Update partial image
                int x = dirtyBounds.x();
                int y = dirtyBounds.y();
                int dirtyWidth = dirtyBounds.width();
                int dirtyHeight = dirtyBounds.height();
                int startOffset = (int) ((y * bitmap.rowBytes()) + x * 4);

                glTexSubImage2D(
                        GL_TEXTURE_2D,
                        0,
                        x, y, dirtyWidth, dirtyHeight,
                        GL_BGRA, GL_UNSIGNED_INT_8_8_8_8_REV,
                        (ByteBuffer) imageData.position(startOffset));
            }
            glPixelStorei(GL_UNPACK_ROW_LENGTH, 0);

            bitmap.unlockPixels();
            surface.clearDirtyBounds();
        }


    }
    public UltralightView getView() {
        return view;
    }


    /**
     * Sets up the OpenGL texture for rendering
     */
    private void createGLTexture() {
        glEnable(GL_TEXTURE_2D);
        this.glTexture = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, this.glTexture);

        glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
        glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        glBindTexture(GL_TEXTURE_2D, 0);
        glDisable(GL_TEXTURE_2D);
    }

    public void onMouseClick(int x, int y, int mouseButton, boolean buttonDown) {
        UltralightMouseEvent event = new UltralightMouseEvent();
        UltralightMouseEventButton button;
        switch (mouseButton) {
            case 0:
                button = UltralightMouseEventButton.LEFT;
                break;
            case 1:
                button = UltralightMouseEventButton.RIGHT;
                break;
            case 2:
            default:
                button = UltralightMouseEventButton.MIDDLE;
                break;

        }
        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
        event.button(button);
        event.x(x);
        event.y(y);
        event.type(buttonDown ? UltralightMouseEventType.DOWN : UltralightMouseEventType.UP);

        view.fireMouseEvent(event);
    }

    public void onMouseMove(int x, int y) {
        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
        UltralightMouseEvent event = new UltralightMouseEvent();
        event.x(x);
        event.y(y);
        event.type(UltralightMouseEventType.MOVED);
        view.fireMouseEvent(event);
    }
    public void onKeyDown(char c, int key) {
        UltralightKeyEvent event = new UltralightKeyEvent();
        event.virtualKeyCode(KeyMap.getKey(key));
        event.unmodifiedText(String.valueOf(c));

        KeyMap.KeyType keyType = KeyMap.getKeyType(key);

        if (keyType == KeyMap.KeyType.ACTION) {
            event.type(UltralightKeyEventType.RAW_DOWN);

        } else if (keyType == KeyMap.KeyType.CHAR) {
            event.type(UltralightKeyEventType.CHAR);
        }


        event.text(String.valueOf(c));
        event.keyIdentifier(UltralightKeyEvent.getKeyIdentifierFromVirtualKeyCode(KeyMap.getKey(key)));

        view.fireKeyEvent(event);
    }

}
