package fr.yan36.westerlife.CoreMod.mixins;

import fr.yan36.westerlife.CoreMod.mixins.splash.Texture;
import fr.yan36.westerlife.CoreMod.mixins.splash.Variables;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.resources.*;
import net.minecraft.crash.CrashReport;
import net.minecraft.launchwrapper.Launch;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.client.SplashProgress;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.ICrashCallable;
import net.minecraftforge.fml.common.ProgressManager;
import net.minecraftforge.fml.common.asm.FMLSanityChecker;
import org.apache.commons.io.IOUtils;
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.Drawable;
import org.lwjgl.opengl.SharedDrawable;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.util.ResourceLoader;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.IntBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Properties;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static net.minecraftforge.fml.client.SplashProgress.getMaxTextureSize;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.GL_BGRA;
import static org.lwjgl.opengl.GL12.GL_UNSIGNED_INT_8_8_8_8_REV;


@Mixin(value = {SplashProgress.class}, priority = 999, remap = false)
public class MixinsSplashScreen {

    @Shadow
    private static Drawable d;
    @Shadow
    private static volatile boolean pause = false;
    @Shadow
    private static volatile boolean done = false;
    @Shadow
    private static Thread thread;
    @Shadow
    private static volatile Throwable threadError;
    @Shadow
    private static int angle = 0;
    @Shadow
    private static final Lock lock = new ReentrantLock(true);

    @Shadow
    private static final IResourcePack mcPack = Minecraft.getMinecraft().defaultResourcePack;
    @Shadow
    private static final IResourcePack fmlPack = createResourcePack(FMLSanityChecker.fmlLocation);
    @Shadow
    private static IResourcePack miscPack;

    @Shadow
    private static Properties config;

    private static boolean enabled;
    @Shadow
    private static boolean rotate;
    @Shadow
    private static int logoOffset;
    @Shadow
    private static int backgroundColor;
    @Shadow
    private static int fontColor;
    @Shadow
    private static int barBorderColor;
    @Shadow
    private static int barColor;
    @Shadow
    private static int barBackgroundColor;
    @Shadow
    private static boolean showMemory;
    @Shadow
    private static int memoryGoodColor;
    @Shadow
    private static int memoryWarnColor;
    @Shadow
    private static int memoryLowColor;
    @Shadow
    private static float memoryColorPercent;
    @Shadow
    private static long memoryColorChangeTime;
    @Shadow
    static boolean isDisplayVSyncForced = false;
    @Shadow
    private static final int TIMING_FRAME_COUNT = 200;
    @Shadow
    private static final int TIMING_FRAME_THRESHOLD = TIMING_FRAME_COUNT * 5 * 1000000; // 5 ms per frame, scaled to nanos
    @Shadow
    static final Semaphore mutex = new Semaphore(1);

    /**
     * @author Gabidut76
     * @reason MixinSplashScreen
     */
    @Overwrite
    public static InputStream open(ResourceLocation loc, @Nullable ResourceLocation fallback, boolean allowResourcePack) throws IOException {
        if (!allowResourcePack)
            return mcPack.getInputStream(loc);

        if (miscPack.resourceExists(loc)) {
            return miscPack.getInputStream(loc);
        } else if (fmlPack.resourceExists(loc)) {
            return fmlPack.getInputStream(loc);
        } else if (!mcPack.resourceExists(loc) && fallback != null) {
            return open(fallback, null, true);
        }
        return mcPack.getInputStream(loc);
    }

    /**
     * @author Gabidut76
     * @reason MixinSplashScreen
     */
    @Overwrite
    public static void start()
    {


        final ResourceLocation logoLoc = new ResourceLocation("westerlife:textures/icons/logo.png");


        if(!enabled) return;
        // getting debug info out of the way, while we still can
        FMLCommonHandler.instance().registerCrashCallable(new ICrashCallable()
        {
            @Override
            public String call() throws Exception
            {
                return "' Vendor: '" + glGetString(GL_VENDOR) +
                        "' Version: '" + glGetString(GL_VERSION) +
                        "' Renderer: '" + glGetString(GL_RENDERER) +
                        "'";
            }

            @Override
            public String getLabel()
            {
                return "GL info";
            }
        });
        CrashReport report = CrashReport.makeCrashReport(new Throwable(), "Loading screen debug info");
        StringBuilder systemDetailsBuilder = new StringBuilder();
        report.getCategory().appendToStringBuilder(systemDetailsBuilder);
        FMLLog.log.info(systemDetailsBuilder.toString());

        try
        {
            d = new SharedDrawable(Display.getDrawable());
            Display.getDrawable().releaseContext();
            d.makeCurrent();
        }
        catch (LWJGLException e)
        {
            FMLLog.log.error("Error starting SplashProgress:", e);
        }

        //Call this ASAP if splash is enabled so that threading doesn't cause issues later
        getMaxTextureSize();

        //Thread mainThread = Thread.currentThread();
        thread = new Thread(new Runnable() {
            private final int barWidth = 400;
            private final int barHeight = 20;
            private final int textHeight2 = 20;
            private final int barOffset = 55;
            private long updateTiming;
            private long framecount;



            @Override
            public void run() {
                setGL();
                Variables.logo = new Texture(logoLoc, null);

                glEnable(GL_TEXTURE_2D);
//                fontRenderer = new SplashProgress.SplashFontRenderer();
                glDisable(GL_TEXTURE_2D);
                while (!done) {
                    framecount++;
                    ProgressManager.ProgressBar first = null, penult = null, last = null;
                    Iterator<ProgressManager.ProgressBar> i = ProgressManager.barIterator();
                    while (i.hasNext()) {
                        if (first == null) first = i.next();
                        else {
                            penult = last;
                            last = i.next();
                        }
                    }

                    setColor(0xFF0000);
                    drawBox(500,500);

                    glClear(GL_COLOR_BUFFER_BIT);

                    // matrix setup
                    int w = Display.getWidth();
                    int h = Display.getHeight();
                    glViewport(0, 0, w, h);
                    glMatrixMode(GL_PROJECTION);
                    glLoadIdentity();
                    glOrtho(320 - w / 2, 320 + w / 2, 240 + h / 2, 240 - h / 2, -1, 1);
                    glMatrixMode(GL_MODELVIEW);
                    glLoadIdentity();

                    // mojang logo
                    setColor(backgroundColor);

                    // memory usage
                    if (showMemory) {
                        glPushMatrix();
                        glTranslatef(320 - (float) barWidth / 2, 20, 0);
//                        drawMemoryBar();
                        glPopMatrix();
                    }

                    // bars
                    if (first != null) {
                        glPushMatrix();
                        glTranslatef(320 - (float) barWidth / 2, 310, 0);
                        drawBar(first);
                        if (penult != null) {
                            glTranslatef(0, barOffset, 0);
                            drawBar(penult);
                        }
                        if (last != null) {
                            glTranslatef(0, barOffset, 0);
                            drawBar(last);
                        }
                        glPopMatrix();
                    }

                    angle += 1;

                    // forge logo
                    glColor4f(1, 1, 1, 1);
                    float fw = (float) Variables.logo.getWidth() / 2;
                    float fh = (float) Variables.logo.getHeight() / 2;
                    if (rotate) {
                        float sh = Math.max(fw, fh);
                        glTranslatef(320 + w / 2 - sh - logoOffset, 240 + h / 2 - sh - logoOffset, 0);
                        glRotatef(angle, 0, 0, 1);
                    } else {
                        glTranslatef(320 + w / 2 - fw - logoOffset, 240 + h / 2 - fh - logoOffset, 0);
                    }
                    int f = (angle / 5) % Variables.logo.getFrames();
                    glEnable(GL_TEXTURE_2D);
                    Variables.logo.bind();
                    glBegin(GL_QUADS);
                    Variables.logo.texCoord(f, 0, 0);
                    glVertex2f(-fw, -fh);
                    Variables.logo.texCoord(f, 0, 1);
                    glVertex2f(-fw, fh);
                    Variables.logo.texCoord(f, 1, 1);
                    glVertex2f(fw, fh);
                    Variables.logo.texCoord(f, 1, 0);
                    glVertex2f(fw, -fh);
                    glEnd();
                    glDisable(GL_TEXTURE_2D);

                    // We use mutex to indicate safely to the main thread that we're taking the display global lock
                    // So the main thread can skip processing messages while we're updating.
                    // There are system setups where this call can pause for a while, because the GL implementation
                    // is trying to impose a framerate or other thing is occurring. Without the mutex, the main
                    // thread would delay waiting for the same global display lock
                    mutex.acquireUninterruptibly();
                    long updateStart = System.nanoTime();
                    Display.update();
                    // As soon as we're done, we release the mutex. The other thread can now ping the processmessages
                    // call as often as it wants until we get get back here again
                    long dur = System.nanoTime() - updateStart;
                    if (framecount < TIMING_FRAME_COUNT) {
                        updateTiming += dur;
                    }
                    mutex.release();
                    if (pause) {
                        clearGL();
                        setGL();
                    }
                    // Such a hack - if the time taken is greater than 10 milliseconds, we're gonna guess that we're on a
                    // system where vsync is forced through the swapBuffers call - so we have to force a sleep and let the
                    // loading thread have a turn - some badly designed mods access Keyboard and therefore GlobalLock.lock
                    // during splash screen, and mutex against the above Display.update call as a result.
                    // 4 milliseconds is a guess - but it should be enough to trigger in most circumstances. (Maybe if
                    // 240FPS is possible, this won't fire?)
                    if (framecount >= TIMING_FRAME_COUNT && updateTiming > TIMING_FRAME_THRESHOLD) {
                        if (!isDisplayVSyncForced) {
                            isDisplayVSyncForced = true;
                            FMLLog.log.info("Using alternative sync timing : {} frames of Display.update took {} nanos", TIMING_FRAME_COUNT, updateTiming);
                        }
                        try {
                            Thread.sleep(16);
                        } catch (InterruptedException ie) {
                        }
                    } else {
                        if (framecount == TIMING_FRAME_COUNT) {
                            FMLLog.log.info("Using sync timing. {} frames of Display.update took {} nanos", TIMING_FRAME_COUNT, updateTiming);
                        }
                        Display.sync(100);
                    }
                }
                clearGL();
            }

            private void setColor(int color)
            {
                glColor3ub((byte)((color >> 16) & 0xFF), (byte)((color >> 8) & 0xFF), (byte)(color & 0xFF));
            }

            private void drawBox(int w, int h)
            {
                glBegin(GL_QUADS);
                glVertex2f(0, 0);
                glVertex2f(0, h);
                glVertex2f(w, h);
                glVertex2f(w, 0);
                glEnd();
            }
            private void drawBar(ProgressManager.ProgressBar b)
            {
                glPushMatrix();
                // title - message
                setColor(fontColor);
                glScalef(2, 2, 1);
                glEnable(GL_TEXTURE_2D);
//                fontRenderer.drawString(b.getTitle() + " - " + b.getMessage(), 0, 0, 0x000000);
                glDisable(GL_TEXTURE_2D);
                glPopMatrix();
                // border
                glPushMatrix();
                glTranslatef(0, textHeight2, 0);
                setColor(barBorderColor);
                drawBox(barWidth, barHeight);
                // interior
                setColor(barBackgroundColor);
                glTranslatef(1, 1, 0);
                drawBox(barWidth - 2, barHeight - 2);
                // slidy part
                setColor(barColor);
                drawBox((barWidth - 2) * (b.getStep() + 1) / (b.getSteps() + 1), barHeight - 2); // Step can sometimes be 0.
                // progress text
                String progress = "" + b.getStep() + "/" + b.getSteps();
//                glTranslatef(((float)barWidth - 2) / 2 - fontRenderer.getStringWidth(progress), 2, 0);
                setColor(fontColor);
                glScalef(2, 2, 1);
                glEnable(GL_TEXTURE_2D);
//                fontRenderer.drawString(progress, 0, 0, 0x000000);
                glPopMatrix();
            }
            private void setGL()
            {
                lock.lock();
                try
                {
                    Display.getDrawable().makeCurrent();
                }
                catch (LWJGLException e)
                {
                    FMLLog.log.error("Error setting GL context:", e);
                    throw new RuntimeException(e);
                }
                glClearColor((float)((backgroundColor >> 16) & 0xFF) / 0xFF, (float)((backgroundColor >> 8) & 0xFF) / 0xFF, (float)(backgroundColor & 0xFF) / 0xFF, 1);
                glDisable(GL_LIGHTING);
                glDisable(GL_DEPTH_TEST);
                glEnable(GL_BLEND);
                glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
            }

            private void clearGL()
            {
                Minecraft mc = Minecraft.getMinecraft();
                mc.displayWidth = Display.getWidth();
                mc.displayHeight = Display.getHeight();
                mc.resize(mc.displayWidth, mc.displayHeight);
                glClearColor(1, 1, 1, 1);
                glEnable(GL_DEPTH_TEST);
                glDepthFunc(GL_LEQUAL);
                glEnable(GL_ALPHA_TEST);
                glAlphaFunc(GL_GREATER, .1f);
                try
                {
                    Display.getDrawable().releaseContext();
                }
                catch (LWJGLException e)
                {
                    FMLLog.log.error("Error releasing GL context:", e);
                    throw new RuntimeException(e);
                }
                finally
                {
                    lock.unlock();
                }
            }
        });
    }



    @Final
    @Shadow
    public static final IntBuffer buf = BufferUtils.createIntBuffer(4 * 1024 * 1024);

    /**
     * @author Gabidut76
     * @reason MixinSplashScreen
     */
    @Overwrite
    private static IResourcePack createResourcePack(File file) {
        if (file.isDirectory()) {
            return new FolderResourcePack(file);
        } else {
            return new FileResourcePack(file);
        }
    }

}
