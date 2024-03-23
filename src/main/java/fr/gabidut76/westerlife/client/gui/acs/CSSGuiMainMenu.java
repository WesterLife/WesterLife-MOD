package fr.gabidut76.westerlife.client.gui.acs;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import fr.aym.acsguis.component.layout.GuiScaler;
import fr.aym.acsguis.component.panel.GuiFrame;
import fr.aym.acsguis.component.panel.GuiPanel;
import fr.aym.acsguis.component.textarea.GuiLabel;
import fr.gabidut76.westerlife.westercore.Main;
import fr.gabidut76.westerlife.client.Client;
import fr.gabidut76.westerlife.client.gui.other.GuiConnecting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiWorldSelection;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Loader;
import org.apache.commons.io.FileUtils;
import org.lwjgl.input.Keyboard;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CSSGuiMainMenu extends GuiFrame {

    public CSSGuiMainMenu() throws IOException {
        super(new GuiScaler.Identity());

        List<String> loadedMods = Loader.instance().getModList().stream().map(modContainer -> modContainer.getModId() + " " + modContainer.getVersion()).collect(Collectors.toList());
        System.out.println("Loaded mods: " + loadedMods);

        GuiPanel background = new GuiPanel();
        background.setCssClass("background");

        GuiPanel settings = new GuiPanel();
        settings.setCssClass("settings");

        settings.addClickListener((mouseX, mouseY, mouseButton) -> {
            try {
                Minecraft.getMinecraft().displayGuiScreen(new GuiOptions(new CSSGuiMainMenu().getGuiScreen(), Minecraft.getMinecraft().gameSettings));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        background.add(settings);
        GuiPanel connect = new GuiPanel();
        connect.setCssClass("connect");

        connect.addClickListener((mouseX, mouseY, mouseButton) -> {
            if(!Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
                Client.isLogginIn = true;
                try {
                    File file = new File(Minecraft.getMinecraft().gameDir, "launcher_profiles.json");
                    if(!file.exists()) {
                        System.out.println("Shit");
                        Minecraft.getMinecraft().displayGuiScreen(new GuiServerError(this.getGuiScreen(), new TextComponentString("Casse toi met le fichier non ?")).getGuiScreen());
                        return;
                    }
                    String content = null;
                    try {
                        content = FileUtils.readFileToString(file, "utf-8");
                    } catch (Exception e) {
                        Minecraft.getMinecraft().displayGuiScreen(new GuiServerError(this.getGuiScreen(), new TextComponentString("Wtf 2 ?")).getGuiScreen());
                        e.printStackTrace();
                    }
                    JsonObject jsonObject = new Gson().fromJson(content, JsonObject.class);
                    if(jsonObject.has("lurl")) {
                        mc.displayGuiScreen(new GuiConnecting(this.getGuiScreen() ,mc, jsonObject.get("lurl").getAsString().split(":")[0], Integer.parseInt(jsonObject.get("lurl").getAsString().split(":")[1])));
                    } else {
                        mc.displayGuiScreen(new GuiConnecting(mc));
                    }

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            } else {
                try {
                    Minecraft.getMinecraft().displayGuiScreen(new GuiWorldSelection(new CSSGuiMainMenu().getGuiScreen()));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        GuiPanel exit = new GuiPanel();
        exit.setCssClass("exit");

        exit.addClickListener((mouseX, mouseY, mouseButton) -> {
            Minecraft.getMinecraft().shutdown();
        });

        background.add(exit);

        background.add(connect);

        GuiLabel perf = new GuiLabel("Mode performance");
        perf.setCssClass("perf");

        background.add(perf);


        add(background);

    }

    public List<ResourceLocation> getCssStyles() {
        return Collections.singletonList(new ResourceLocation(Main.MODID, "acsgui/mainmenu.css"));
    }
}
