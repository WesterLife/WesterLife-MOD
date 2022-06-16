package fr.yan36.westerlife.common.items;

import fr.yan36.westerlife.Main;
import fr.yan36.westerlife.common.blocks.WesterBlocks;
import fr.yan36.westerlife.common.registry.SoundsHandler;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
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
    public static final Item CINQEUROS = new Item5E();
    public static final Item DIXEUROS = new Item10E();
    public static final Item VINGTEUROS = new Item20E();
    public static final Item CINQUANTEEUROS = new Item50E();
    public static final Item CENTEUROS = new Item100E();
    public static final Item DEUXCENTEUROS = new Item200E();
    public static final Item CINQCENTEUROS = new Item500E();
    public static final Item DISC_MARSEILLAISE = new ItemMarseillaise("marseillaise", SoundsHandler.MARSEILLAISE);
    public static final Item COD = new ItemCOD();

    public static final Item BLOCK_ATM_ITEM = new ItemBlock(WesterBlocks.ATM).setRegistryName(WesterBlocks.ATM.getRegistryName());

    public static void setItemName(Item item, String name) {
        item.setRegistryName(Main.MODID, name).setTranslationKey(Main.MODID + "." + name);
    }

    // En 1.12+
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void registerItemsModels(ModelRegistryEvent event)
    {
        registerModel(CNI, 0);
        registerModel(PDC, 0);
        registerModel(CB, 0);
        registerModel(CINQEUROS, 0);
        registerModel(DIXEUROS, 0);
        registerModel(VINGTEUROS, 0);
        registerModel(CINQUANTEEUROS, 0);
        registerModel(CENTEUROS, 0);
        registerModel(DEUXCENTEUROS, 0);
        registerModel(CINQCENTEUROS, 0);
        registerModel(BLOCK_ATM_ITEM, 0);
        registerModel(DISC_MARSEILLAISE, 0);
        registerModel(COD, 0);
    }

    @SideOnly(Side.CLIENT)
    public static void registerModel(Item item, int metadata)
    {
        if (metadata < 0) metadata = 0;
        String resourceName = item.getTranslationKey().substring(5).replace('.', ':');;
        if (metadata > 0) resourceName += "_m" + String.valueOf(metadata);

        ModelLoader.setCustomModelResourceLocation(item, metadata, new ModelResourceLocation(resourceName, "inventory"));
    }

}