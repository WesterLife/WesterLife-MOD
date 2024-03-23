package fr.gabidut76.westerlife.client.gui.acs.auth;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import fr.aym.acsguis.api.ACsGuiApi;
import fr.aym.acsguis.api.GuiAPIClientHelper;
import fr.aym.acsguis.component.layout.GuiScaler;
import fr.aym.acsguis.component.panel.GuiFrame;
import fr.aym.acsguis.component.panel.GuiPanel;
import fr.aym.acsguis.component.style.TextComponentStyleManager;
import fr.aym.acsguis.component.textarea.GuiLabel;
import fr.aym.acsguis.component.textarea.GuiPasswordField;
import fr.aym.acsguis.component.textarea.GuiTextField;
import fr.aym.acsguis.cssengine.font.CssFontHelper;
import fr.aym.acslib.api.services.error.ErrorCategory;
import fr.dynamx.utils.DynamXLoadingTasks;
import fr.dynamx.utils.errors.DynamXErrorManager;
import fr.gabidut76.westerlife.CoreMod.WesterLifeSecurityManager;
import fr.gabidut76.westerlife.westercore.Main;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public class CSSGuiConfirmUUID extends GuiFrame {
    private int shifter = 0;

    private GuiTextField logLOC;
    private GuiLabel box3 = new GuiLabel("Reload css styles");

    public CSSGuiConfirmUUID() throws IOException {
        super(new GuiScaler.Identity());

        logLOC = new GuiTextField();
        logLOC.setCssClass("logloc");

        GuiLabel label = new GuiLabel("WesterLife - Confirmez votre UUID.");
        label.setCssClass("title");
        add(label);

        GuiPanel background = new GuiPanel();
        background.setCssClass("background");

        GuiPanel connect = new GuiPanel();
        connect.setCssClass("login_frame");


        add(background);

        GuiLabel label2 = new GuiLabel("§oJe n'ai pas de compte Nemesis");
        label2.setCssClass("register");
        label2.addClickListener((mouseX, mouseY, mouseButton) -> {
            try {
                Desktop.getDesktop().browse(new URL("http://nemesis.westerlife.fr/").toURI());
            } catch (IOException | URISyntaxException e) {
                throw new RuntimeException(e);
            }
        });

        add(label2);

        GuiPanel login = new GuiPanel();
        login.setCssClass("login_btn");
        login.addClickListener((mouseX, mouseY, mouseButton) -> {

            File file = new File(Minecraft.getMinecraft().gameDir + "\\launcher_profiles.json");
            JsonObject jsonObject1 = new JsonObject();
            String filecontent = "";
            try {
                filecontent = new String(java.nio.file.Files.readAllBytes(file.toPath()));
            } catch (IOException e) {
                e.printStackTrace();
            }

            Gson gson = new Gson();
            JsonObject internal = gson.fromJson(filecontent, JsonObject.class);

            String rep = WesterLifeSecurityManager.httpPost(WesterLifeSecurityManager.NEMESIS_URL + "api/auth/confirmuuid", "{\"token\":\"" + internal.get("account").getAsJsonObject().get("username").getAsString() + "\",\"uuid\":\"" + Minecraft.getMinecraft().player.getUniqueID().toString() + "\"}");

            if (rep.contains("error")) {
                GuiLabel error = new GuiLabel("§cErreur: " + rep);
                error.setCssClass("error");
                add(error);
            } else {
                internal.get("account").getAsJsonObject().addProperty("nemesis", rep);
                try {
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    fileOutputStream.write(gson.toJson(internal).getBytes());
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Minecraft.getMinecraft().displayGuiScreen(new GuiMainMenu());
            }


            System.out.println(rep);
        });


        box3.setCssId("reload_css").setCssClass("reload_button");
        box3.addClickListener((x, y, bu) -> {
            box3.setEnabled(false);
            box3.setText("Reloading...");
            DynamXLoadingTasks.reload(DynamXLoadingTasks.TaskContext.CLIENT, new Consumer[]{DynamXLoadingTasks.CSS}).thenAccept((empty) -> {
                box3.setEnabled(true);
                if (DynamXErrorManager.getErrorManager().hasErrors(new ErrorCategory[]{ACsGuiApi.getCssErrorType()})) {
                    box3.setText(TextFormatting.RED + "Some css styles have errors");
                } else {
                    box3.setText("Css styles reloaded");
                }

            });
        });

        add(login);


        background.add(connect);

        GuiPanel exit = new GuiPanel();
        exit.setCssClass("exit");

        exit.addClickListener((mouseX, mouseY, mouseButton) -> {
            Minecraft.getMinecraft().shutdown();
        });

//        background.add(exit);

        background.add(connect);


    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {
        if (keyCode == Keyboard.KEY_LSHIFT) {
            shifter += 1;
        }
        if (keyCode == Keyboard.KEY_F10) {
            if (shifter >= 5) {
                add(logLOC);
                add(box3);
                System.out.println("aa");
            }
        }

        super.keyTyped(typedChar, keyCode);
    }


    public List<ResourceLocation> getCssStyles() {
        return Collections.singletonList(new ResourceLocation(Main.MODID, "acsgui/mainmenu.css"));
    }
}
