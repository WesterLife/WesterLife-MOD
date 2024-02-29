package fr.gabidut76.westerlife.common.handlers;

import fr.gabidut76.westerlife.westercore.Main;
import fr.gabidut76.westerlife.common.init.BlockInit;
import fr.gabidut76.westerlife.common.init.ItemInit;
import fr.gabidut76.westerlife.common.utils.interfaces.IHasModel;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class RegistryHandler {

    public static final ResourceLocation CAPABILITY_LOCATION = new ResourceLocation(Main.MODID, "armorsuperposition"); // On évite d'instancier à chaque fois le même objet

    @SubscribeEvent
    public void onItemRegister(RegistryEvent.Register<Item> event)
    {
        System.out.println("Enregistrement des items");
        event.getRegistry().registerAll(ItemInit.ITEMS.toArray(new Item[0]));
        event.getRegistry().registerAll(ItemInit.ITEMS_FOOD.toArray(new Item[0]));
    }
    @SubscribeEvent
    public void onBlockRegister(RegistryEvent.Register<Block> event)
    {
        System.out.println("Enregistrement des blocks");
        event.getRegistry().registerAll(BlockInit.BLOCKS.toArray(new Block[0]));
    }
    @SubscribeEvent
    public void OnModelRegister(ModelRegistryEvent event) {
        for (Item item : ItemInit.ITEMS) {
            if(item instanceof IHasModel) {
                ((IHasModel)item).registerModels();
                System.out.println("Item: " + item.getRegistryName() + " in tab: " + item.getCreativeTab());
            }
        }
        for (Block block : BlockInit.BLOCKS) {
            if(block instanceof IHasModel) {
                ((IHasModel)block).registerModels();
                System.out.println("block: " + block.getRegistryName());
            }
        }
    }

}

