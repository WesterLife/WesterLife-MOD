package fr.yan36.westerlife.CoreMod;

import net.minecraft.launchwrapper.IClassTransformer;

public class WesterLifeLibLoader implements IClassTransformer {
    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        if (name.equals("net.minecraftforge.fml.client.FMLClientHandler")) {

            // add a line in the method "isGUIOpen" to return false
            WesterLifeCoreMod.log.warn("Transforming FMLClientHandler");
            return new byte[0];

        }
        return new byte[0];
    }
}
