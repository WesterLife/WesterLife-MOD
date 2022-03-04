package fr.yan36.westerlife.registry;

import fr.yan36.westerlife.items.WesterItem;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class RegisteringHandler {

    @SubscribeEvent
    public void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(WesterItem.CNI, WesterItem.PDC, WesterItem.CB);
    }
}
