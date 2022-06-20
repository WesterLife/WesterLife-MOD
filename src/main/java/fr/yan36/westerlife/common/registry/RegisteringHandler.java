package fr.yan36.westerlife.common.registry;

import fr.yan36.westerlife.common.blocks.WesterBlocks;
import fr.yan36.westerlife.common.items.WesterItem;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class RegisteringHandler {

    @SubscribeEvent
    public void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(WesterItem.COD, WesterItem.DISC_MARSEILLAISE, WesterItem.CNI, WesterItem.BLOCK_ATM_ITEM, WesterItem.PDC, WesterItem.CB, WesterItem.CINQEUROS, WesterItem.DIXEUROS, WesterItem.VINGTEUROS, WesterItem.CINQUANTEEUROS, WesterItem.CENTEUROS, WesterItem.DEUXCENTEUROS, WesterItem.CINQCENTEUROS);
    }

    @SubscribeEvent
    public void registerBlocks(RegistryEvent.Register<Block> event)
    {
        event.getRegistry().registerAll(WesterBlocks.ATM);
    }

    public static void initRegistries() {
        SoundsHandler.registerSounds();
    }

}

