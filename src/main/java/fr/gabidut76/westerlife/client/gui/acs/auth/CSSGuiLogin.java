package fr.gabidut76.westerlife.client.gui.acs.auth;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import fr.aym.acsguis.api.ACsGuiApi;
import fr.aym.acsguis.api.GuiAPIClientHelper;
import fr.aym.acsguis.component.button.GuiButton;
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
import fr.gabidut76.westerlife.client.Client;
import fr.gabidut76.westerlife.client.gui.other.GuiConnecting;
import fr.gabidut76.westerlife.westercore.Main;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiWorldSelection;
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

public class CSSGuiLogin extends GuiFrame {
    private int shifter = 0;

    private GuiTextField logLOC;
    private GuiLabel box3 = new GuiLabel("Reload css styles");

    public CSSGuiLogin() throws IOException {
        super(new GuiScaler.Identity());

        logLOC = new GuiTextField();
        logLOC.setCssClass("logloc");

        GuiLabel label = new GuiLabel("WesterLife - Connection manuelle à Nemesis.");
        label.setCssClass("title");
        add(label);

        GuiPanel background = new GuiPanel();
        background.setCssClass("background");

        GuiTextField username = new GuiTextField(){  // changing some function colors due to background color
            @Override
            protected void drawHintLines(float scale) {
                if (!this.isFocused() && this.text.isEmpty()) {
                    GlStateManager.enableTexture2D();
                    List<String> hintTextLines = this.getHintTextLines();
                    if (hintTextLines != null) {
                        for(int i = 0; i < hintTextLines.size(); ++i) {
                            float height = scale * (float)((TextComponentStyleManager)this.getStyle()).getFontHeight((String)hintTextLines.get(i));
                            CssFontHelper.draw((float)(this.getScreenX() + this.getPaddingLeft() - this.getLineScrollOffsetX()) / scale, ((float)(this.getScreenY() + this.getPaddingTop()) + GuiAPIClientHelper.getRelativeTextY(i, hintTextLines.size(), this.getHeight() - (this.getPaddingTop() + this.getPaddingBottom()), ((TextComponentStyleManager)this.getStyle()).getVerticalTextAlignment(), height) - (float)this.getLineScrollOffsetY()) / scale, (String)hintTextLines.get(i), Color.decode("#31384A").getRGB());
                        }
                    }
                }
            }

            @Override
            protected void drawCursor(float scale) {
                if (this.cursorCounter / 20 % 2 == 0 && this.isFocused()) {
                    String line = (String)this.getRenderedTextLines().get(this.getLine(this.cursorIndex));
                    float height = scale * 9.0F;
                    float cursorPosX = (float)mc.fontRenderer.getStringWidth(line.substring(0, this.getPosition(this.cursorIndex))) * scale - (float)this.lineScrollOffsetX;
                    float cursorPosY = GuiAPIClientHelper.getRelativeTextY(this.getLine(this.cursorIndex), this.getRenderedTextLines().size(), this.getHeight() - (this.getPaddingTop() + this.getPaddingBottom()), ((TextComponentStyleManager)this.getStyle()).getVerticalTextAlignment(), height) - (float)this.getLineScrollOffsetY();
                    drawRect((int)(((float)(this.getScreenX() + this.getPaddingLeft()) + cursorPosX) / scale), (int)(((float)(this.getScreenY() + this.getPaddingTop()) + cursorPosY) / scale), (int)(((float)(this.getScreenX() + this.getPaddingLeft()) + cursorPosX) / scale + 1.0F), (int)(((float)(this.getScreenY() + this.getPaddingTop()) + cursorPosY) / scale + 9.0F), Color.BLACK.getRGB());
                }
            }
        };
        username.setCssClass("username");
        username.setHintText("Nom d'utilisateur");

        GuiPasswordField password = new GuiPasswordField(){ // changing some function colors due to background color
            @Override
            protected void drawHintLines(float scale) {
                if (!this.isFocused() && this.text.isEmpty()) {
                    GlStateManager.enableTexture2D();
                    List<String> hintTextLines = this.getHintTextLines();
                    if (hintTextLines != null) {
                        for(int i = 0; i < hintTextLines.size(); ++i) {
                            float height = scale * (float)((TextComponentStyleManager)this.getStyle()).getFontHeight((String)hintTextLines.get(i));
                            CssFontHelper.draw((float)(this.getScreenX() + this.getPaddingLeft() - this.getLineScrollOffsetX()) / scale, ((float)(this.getScreenY() + this.getPaddingTop()) + GuiAPIClientHelper.getRelativeTextY(i, hintTextLines.size(), this.getHeight() - (this.getPaddingTop() + this.getPaddingBottom()), ((TextComponentStyleManager)this.getStyle()).getVerticalTextAlignment(), height) - (float)this.getLineScrollOffsetY()) / scale, (String)hintTextLines.get(i), Color.decode("#31384A").getRGB());
                        }
                    }
                }
            }
            @Override
            protected void drawCursor(float scale) {
                if (this.cursorCounter / 20 % 2 == 0 && this.isFocused()) {
                    String line = (String)this.getRenderedTextLines().get(this.getLine(this.cursorIndex));
                    float height = scale * 9.0F;
                    float cursorPosX = (float)mc.fontRenderer.getStringWidth(line.substring(0, this.getPosition(this.cursorIndex))) * scale - (float)this.lineScrollOffsetX;
                    float cursorPosY = GuiAPIClientHelper.getRelativeTextY(this.getLine(this.cursorIndex), this.getRenderedTextLines().size(), this.getHeight() - (this.getPaddingTop() + this.getPaddingBottom()), ((TextComponentStyleManager)this.getStyle()).getVerticalTextAlignment(), height) - (float)this.getLineScrollOffsetY();
                    drawRect((int)(((float)(this.getScreenX() + this.getPaddingLeft()) + cursorPosX) / scale), (int)(((float)(this.getScreenY() + this.getPaddingTop()) + cursorPosY) / scale), (int)(((float)(this.getScreenX() + this.getPaddingLeft()) + cursorPosX) / scale + 1.0F), (int)(((float)(this.getScreenY() + this.getPaddingTop()) + cursorPosY) / scale + 9.0F), Color.BLACK.getRGB());
                }
            }
        };
        password.setCssClass("password");
        password.setHintText("Mot de passe");

        GuiPanel connect = new GuiPanel();
        connect.setCssClass("login_frame");


        add(background);
        add(username);
        add(password);

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
            String rep = WesterLifeSecurityManager.httpPost(WesterLifeSecurityManager.NEMESIS_URL + "api/auth/check", "{\"username\":\"" + username.getText() + "\",\"password\":\"" + password.getText() + "\"}");

            // parse rep
            JsonObject jsonObject = new Gson().fromJson(rep, JsonObject.class);
            if (Objects.equals(jsonObject.get("info").getAsJsonObject().get("state").getAsJsonObject().get("code").getAsString(), "ERROR")) {
                GuiLabel label1 = new GuiLabel(jsonObject.get("info").getAsJsonObject().get("state").getAsJsonObject().get("code").getAsString() + " : " + jsonObject.get("info").getAsJsonObject().get("errorDetails").getAsString());
                label1.setCssClass("error");
                add(label1);
            } else {
                File file = new File(Minecraft.getMinecraft().gameDir + "\\launcher_profiles.json");

                JsonObject jsonObject1 = new JsonObject();
                jsonObject1.add("account", new JsonObject());
                jsonObject1.get("account").getAsJsonObject().addProperty("username", jsonObject.get("data").getAsJsonObject().get("user").getAsJsonObject().get("token").getAsString());
                jsonObject1.addProperty("attention", "This file is automatically generated by the launcher, do not modify it manually. If you want to change your settings, please use the launcher.");
                jsonObject1.addProperty("isOpti", true);

                try {
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    fileOutputStream.write(jsonObject1.toString().getBytes());
                    fileOutputStream.close();
                    WesterLifeSecurityManager.SHOULD_MANUAL_LOGIN = false;
                    Minecraft.getMinecraft().displayGuiScreen(new GuiMainMenu());
                } catch (IOException e) {
                    GuiLabel label1 = new GuiLabel("Impossible de sauvegarder le fichier de configuration");
                    label1.setCssClass("error");
                    add(label1);
                    e.printStackTrace();
                }


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
