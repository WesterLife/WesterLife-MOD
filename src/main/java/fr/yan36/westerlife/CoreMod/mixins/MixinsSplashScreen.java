package fr.yan36.westerlife.CoreMod.mixins;

import fr.yan36.westerlife.CoreMod.types.SplashFontRenderer;
import fr.yan36.westerlife.CoreMod.types.Texture;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.FileResourcePack;
import net.minecraft.client.resources.FolderResourcePack;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.crash.CrashReport;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.SplashProgress;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.ICrashCallable;
import net.minecraftforge.fml.common.ProgressManager;
import net.minecraftforge.fml.common.asm.FMLSanityChecker;
import org.apache.commons.lang3.StringUtils;
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

import javax.annotation.Nullable;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Properties;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static org.lwjgl.opengl.GL11.*;


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
    private static SplashFontRenderer fontRenderer;

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

    private static String getString(String name, String def) {
        String value = config.getProperty(name, def);
        config.setProperty(name, value);
        return value;
    }

    private static boolean getBool(String name, boolean def) {
        return Boolean.parseBoolean(getString(name, Boolean.toString(def)));
    }

    private static int getInt(String name, int def) {
        return Integer.decode(getString(name, Integer.toString(def)));
    }

    private static int getHex(String name, int def) {
        return Integer.decode(getString(name, "0x" + Integer.toString(def, 16).toUpperCase()));
    }

    /**
     * @author Gabidut76
     * @reason MixinSplashScreen
     */
    @Overwrite
    public static void start() {
        File configFile = new File(Minecraft.getMinecraft().gameDir, "config/splash.properties");

        File parent = configFile.getParentFile();
        if (!parent.exists())
            parent.mkdirs();

        config = new Properties();
        try (Reader r = new InputStreamReader(new FileInputStream(configFile), StandardCharsets.UTF_8)) {
            config.load(r);
        } catch (IOException e) {
            FMLLog.log.info("Could not load splash.properties, will create a default one");
        }

        //Some systems do not support this and have weird effects, so we need to detect and disable them by default.
        //The user can always force enable it if they want to take the responsibility for bugs.
        boolean defaultEnabled = true;

        // Enable if we have the flag, and there's either no optifine, or optifine has added a key to the blackboard ("optifine.ForgeSplashCompatible")
        // Optifine authors - add this key to the blackboard if you feel your modifications are now compatible with this code.
        rotate = getBool("rotate", false);
        showMemory = getBool("showMemory", true);
        logoOffset = getInt("logoOffset", 0);
        backgroundColor = getHex("background", 0xFFFFFF);
        fontColor = getHex("font", 0x000000);
        barBorderColor = getHex("barBorder", 0xC0C0C0);
        barColor = getHex("bar", 0xCB3D35);
        barBackgroundColor = getHex("barBackground", 0xFFFFFF);
        memoryGoodColor = getHex("memoryGood", 0x78CB34);
        memoryWarnColor = getHex("memoryWarn", 0xE6E84A);
        memoryLowColor = getHex("memoryLow", 0xE42F2F);

        Display.setTitle("Chargement - WesterLife");


        final ResourceLocation fontLoc = new ResourceLocation(getString("fontTexture", "textures/font/ascii.png"));
        final ResourceLocation logoLoc = new ResourceLocation("loading.png");
        final ResourceLocation forgeLoc = new ResourceLocation(getString("forgeTexture", "fml:textures/gui/forge.png"));
        final ResourceLocation forgeFallbackLoc = new ResourceLocation("fml:textures/gui/forge.png");

        File miscPackFile = new File(Minecraft.getMinecraft().gameDir, getString("resourcePackPath", "resources"));

        try (Writer w = new OutputStreamWriter(new FileOutputStream(configFile), StandardCharsets.UTF_8)) {
            config.store(w, "Splash screen properties");
        } catch (IOException e) {
            FMLLog.log.error("Could not save the splash.properties file", e);
        }

        miscPack = createResourcePack(miscPackFile);

        // getting debug info out of the way, while we still can
        FMLCommonHandler.instance().registerCrashCallable(new ICrashCallable() {
            @Override
            public String call() throws Exception {
                return "' Vendor: '" + glGetString(GL_VENDOR) +
                        "' Version: '" + glGetString(GL_VERSION) +
                        "' Renderer: '" + glGetString(GL_RENDERER) +
                        "'";
            }

            @Override
            public String getLabel() {
                return "GL info";
            }
        });
        CrashReport report = CrashReport.makeCrashReport(new Throwable(), "Loading screen debug info");
        StringBuilder systemDetailsBuilder = new StringBuilder();
        report.getCategory().appendToStringBuilder(systemDetailsBuilder);
        FMLLog.log.info(systemDetailsBuilder.toString());

        try {
            d = new SharedDrawable(Display.getDrawable());
            Display.getDrawable().releaseContext();
            d.makeCurrent();
        } catch (LWJGLException e) {
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
            private TrueTypeFont font = null;

            @Override
            public void run() {
                setGL();
                Texture fontTexture = new Texture(fontLoc, null, false);
                Texture logoTexture = new Texture(logoLoc, null, false);
                glEnable(GL_TEXTURE_2D);
                fontRenderer = new SplashFontRenderer();
                glDisable(GL_TEXTURE_2D);




                try {
                    InputStream inputStream = ResourceLoader.getResourceAsStream("bauhaus.ttf");

                    Font awtFont = Font.createFont(Font.TRUETYPE_FONT, inputStream);
                    awtFont = awtFont.deriveFont(30f); // set font size
                    font = new TrueTypeFont(awtFont, false);

                } catch (Exception e) {
                    e.printStackTrace();
                }

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
                    glEnable(GL_TEXTURE_2D);
                    logoTexture.bind();
                    glBegin(GL_QUADS);
//                    logoTexture.texCoord(0, 0, 0);
//                    glVertex2f(-320, 0);
//                    logoTexture.texCoord(0, 0, 1);
//                    glVertex2f(-320, h);
//                    logoTexture.texCoord(0, 1, 1);
//                    glVertex2f(w, h);
//                    logoTexture.texCoord(0, 1, 0);
//                    glVertex2f(w, 0);

                    logoTexture.texCoord(0, 1, 0);
                    glVertex2f(320 + w / 2, 240 - h / 2);
                    logoTexture.texCoord(0, 1, 1);
                    glVertex2f(320 + w / 2, 240 + h / 2);
                    logoTexture.texCoord(0, 0, 1);
                    glVertex2f(320 - w / 2, 240 + h / 2);
                    logoTexture.texCoord(0, 0, 0);
                    glVertex2f(320 - w / 2, 240 - h / 2);

                    glEnd();
                    glDisable(GL_TEXTURE_2D);
                    // memory usage
                    if (showMemory) {
//                        glPushMatrix();
//                        glTranslatef(320 - (float) barWidth / 2, 20, 0);
//                        drawMemoryBar();
//                        glPopMatrix();
                    }

                    // bars
                    if (first != null) {
                        glPushMatrix();
//                        glTranslatef(320 - Display.getWidth()/2, Display.getHeight(), 0);
                        glTranslatef(320 - Display.getWidth()/2, 240 + h/2 - barHeight, 0);
                        drawBar(first);
                        glPopMatrix();
                    }


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

            private void setColor(int color) {
                glColor3ub((byte) ((color >> 16) & 0xFF), (byte) ((color >> 8) & 0xFF), (byte) (color & 0xFF));
            }

            private void drawBox(int w, int h) {
                glBegin(GL_QUADS);
                glVertex2f(0, 0);
                glVertex2f(0, h);
                glVertex2f(w, h);
                glVertex2f(w, 0);
                glEnd();
            }


            private void drawBar(ProgressManager.ProgressBar b) {
                glPushMatrix();
                // title - message
                setColor(fontColor);
                glScalef(2, 2, 1);
                glEnable(GL_TEXTURE_2D);
                glDisable(GL_TEXTURE_2D);
                glPopMatrix();
                glPushMatrix();
                setColor(0xF27841);
                glTranslatef(1, 1, 0);
                drawBox(Display.getWidth() + 1, barHeight - 2);
                // slidy part
                setColor(0xF14902);
                drawBox((Display.getWidth() + 1) * (b.getStep() + 1) / (b.getSteps() + 1), barHeight - 2);
                String progress = "" + b.getStep() + "/" + b.getSteps();
                glTranslatef(((float) barWidth - 2) / 2 - fontRenderer.getStringWidth(progress), 2, 0);
                setColor(0xFFFFFF);
                glScalef(2, 2, 1);
                glPopMatrix();

            }

            private int bytesToMb(long bytes) {
                return (int) (bytes / 1024L / 1024L);
            }

            private void drawMemoryBar() {
                int maxMemory = bytesToMb(Runtime.getRuntime().maxMemory());
                int totalMemory = bytesToMb(Runtime.getRuntime().totalMemory());
                int freeMemory = bytesToMb(Runtime.getRuntime().freeMemory());
                int usedMemory = totalMemory - freeMemory;
                float usedMemoryPercent = usedMemory / (float) maxMemory;

                glPushMatrix();
                // title - message
                setColor(fontColor);
                glScalef(2, 2, 1);
                glEnable(GL_TEXTURE_2D);

//                font.drawString( 0, 0,"Memory Used / Total", org.newdawn.slick.Color.white);
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

                long time = System.currentTimeMillis();
                if (usedMemoryPercent > memoryColorPercent || (time - memoryColorChangeTime > 1000)) {
                    memoryColorChangeTime = time;
                    memoryColorPercent = usedMemoryPercent;
                }

                int memoryBarColor;
                if (memoryColorPercent < 0.75f) {
                    memoryBarColor = memoryGoodColor;
                } else if (memoryColorPercent < 0.85f) {
                    memoryBarColor = memoryWarnColor;
                } else {
                    memoryBarColor = memoryLowColor;
                }
                setColor(memoryLowColor);
                glPushMatrix();
                glTranslatef((barWidth - 2) * (totalMemory) / (maxMemory) - 2, 0, 0);
                drawBox(2, barHeight - 2);
                glPopMatrix();
                setColor(memoryBarColor);
                drawBox((barWidth - 2) * (usedMemory) / (maxMemory), barHeight - 2);

                // progress text
                String progress = getMemoryString(usedMemory) + " / " + getMemoryString(maxMemory);
                glTranslatef(((float) barWidth - 2) / 2 - fontRenderer.getStringWidth(progress), -50, 0);
                setColor(fontColor);
                glScalef(2, 2, 1);
                glEnable(GL_TEXTURE_2D);
//                font.drawString( 0, 0,progress, org.newdawn.slick.Color.white);
                glPopMatrix();
            }

            private String getMemoryString(int memory) {
                return StringUtils.leftPad(Integer.toString(memory), 4, ' ') + " MB";
            }

            private void setGL() {
                lock.lock();
                try {
                    Display.getDrawable().makeCurrent();
                } catch (LWJGLException e) {
                    FMLLog.log.error("Error setting GL context:", e);
                    throw new RuntimeException(e);
                }
                glClearColor((float) ((backgroundColor >> 16) & 0xFF) / 0xFF, (float) ((backgroundColor >> 8) & 0xFF) / 0xFF, (float) (backgroundColor & 0xFF) / 0xFF, 1);
                glDisable(GL_LIGHTING);
                glDisable(GL_DEPTH_TEST);
                glEnable(GL_BLEND);
                glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
            }

            private void clearGL() {
                Minecraft mc = Minecraft.getMinecraft();
                mc.displayWidth = Display.getWidth();
                mc.displayHeight = Display.getHeight();
                mc.resize(mc.displayWidth, mc.displayHeight);
                glClearColor(1, 1, 1, 1);
                glEnable(GL_DEPTH_TEST);
                glDepthFunc(GL_LEQUAL);
                glEnable(GL_ALPHA_TEST);
                glAlphaFunc(GL_GREATER, .1f);
                try {
                    Display.getDrawable().releaseContext();
                } catch (LWJGLException e) {
                    FMLLog.log.error("Error releasing GL context:", e);
                    throw new RuntimeException(e);
                } finally {
                    lock.unlock();
                }
            }
        });
        thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                FMLLog.log.error("Splash thread Exception", e);
                threadError = e;
            }
        });
        thread.start();
        checkThreadState();
    }

    private static void checkThreadState() {
        if (thread.getState() == Thread.State.TERMINATED || threadError != null) {
            throw new IllegalStateException("Splash thread", threadError);
        }
    }


    /**
     * @author YOU ALSO
     * @reason FUCK YOU
     */
    @Overwrite
    public static void finish() {
        try {
            done = true;
            thread.join();
            glFlush();        // process any remaining GL calls before releaseContext (prevents missing textures on mac)
            d.releaseContext();
            Display.getDrawable().makeCurrent();
        } catch (Exception e) {
            FMLLog.log.error("Error finishing SplashProgress:", e);
        }
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

    @Shadow
    private static int max_texture_size = -1;

    /**
     * @author a
     * @reason a
     */
    @Overwrite
    public static int getMaxTextureSize() {
        if (max_texture_size != -1) return max_texture_size;
        for (int i = 0x4000; i > 0; i >>= 1) {
            GlStateManager.glTexImage2D(GL_PROXY_TEXTURE_2D, 0, GL_RGBA, i, i, 0, GL_RGBA, GL_UNSIGNED_BYTE, null);
            if (GlStateManager.glGetTexLevelParameteri(GL_PROXY_TEXTURE_2D, 0, GL_TEXTURE_WIDTH) != 0) {
                max_texture_size = i;
                return i;
            }
        }
        return -1;
    }

}
