package fr.yan36.westerlife.client.phone;

import fr.aym.acsguis.api.ACsGuiApi;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;

public class App {
    public ResourceLocation iconLoc;
    public String appName;
    public String version;

    public App(ResourceLocation iconLoc, String appName, String version) {
        this.iconLoc = iconLoc;
        this.appName = appName;
        this.version = version;
        Apps.APPS.add(this);
    }

    public ResourceLocation getIconLoc() {
        return iconLoc;
    }

    public String getAppName() {
        return appName;
    }

    public String getVersion() {
        return version;
    }

    public void setIconLoc(ResourceLocation iconLoc) {
        this.iconLoc = iconLoc;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public void setVersion(String version) {
        this.version = version;
    }
    public void onClick(EntityPlayer executor) {
        executor.sendMessage(new TextComponentString("Cette application n'est pas encore disponible"));
    }
}
