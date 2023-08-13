package fr.yan36.westerlife.CoreMod;

import fr.aym.acslib.services.impl.stats.core.StatsBotCorePlugin;
import fr.aym.loadingscreen.client.SplashScreenTransformer;
import fr.yan36.westerlife.CoreMod.mixins.MixinsSplashScreen;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.Mixins;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Map;

@IFMLLoadingPlugin.MCVersion("1.12.2")
public class WesterLifeCoreMod implements IFMLLoadingPlugin {
    public static Logger log = LogManager.getLogger("WesterLifeCoreMod Logger");
    public String[] getASMTransformerClass() {



        MixinBootstrap.init();
        Mixins.addConfiguration("mixins.westerlife.json");

        return new String[]{
                WesterLifeSecurityManager.class.getName(),
        };
    }

    public String getModContainerClass() {
        return null;
    }

    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {

    }

    public String getAccessTransformerClass() {
        return null;
    }
}
