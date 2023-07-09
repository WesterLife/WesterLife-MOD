package fr.yan36.westerlife.CoreMod;

import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Map;

@IFMLLoadingPlugin.MCVersion("1.12.2")
public class WesterLifeCoreMod implements IFMLLoadingPlugin {
    public static Logger log = LogManager.getLogger("WesterLifeCoreMod Logger");
    public String[] getASMTransformerClass() {





        return new String[] {  };
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
