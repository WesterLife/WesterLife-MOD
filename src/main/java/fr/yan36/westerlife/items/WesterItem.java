package fr.yan36.westerlife.items;

import fr.yan36.westerlife.Main;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod.EventBusSubscriber(value = Side.CLIENT, modid = Main.MODID)
public class WesterItem
{
    public static final Item CNI = new ItemCNI();
    public static final Item PDC = new ItemPDC();
    public static final Item CB = new ItemCB();

    public static void setItemName(Item item, String name) {
        item.setRegistryName(Main.MODID, name).setUnlocalizedName(Main.MODID + "." + name);
    }

    // En 1.12+
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void registerItemsModels(ModelRegistryEvent event)
    {
        registerModel(CNI, 0);
        registerModel(PDC, 0);
        registerModel(CB, 0);
    }

    @SideOnly(Side.CLIENT)
    public static void registerModel(Item item, int metadata)
    {
        if (metadata < 0) metadata = 0;
        String resourceName = item.getUnlocalizedName().substring(5).replace('.', ':');;
        if (metadata > 0) resourceName += "_m" + String.valueOf(metadata);

        ModelLoader.setCustomModelResourceLocation(item, metadata, new ModelResourceLocation(resourceName, "inventory"));
    }

}