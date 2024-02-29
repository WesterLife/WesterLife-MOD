package fr.gabidut76.westerlife.CoreMod;

import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.Mixins;

import java.util.Map;

@IFMLLoadingPlugin.MCVersion("1.12.2")
public class WesterLifeCoreMod implements IFMLLoadingPlugin {
    public static Logger log = LogManager.getLogger("WesterLifeCoreMod Logger");
    public String[] getASMTransformerClass() {


        log.info("WesterLifeCoreMod Loading");

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
