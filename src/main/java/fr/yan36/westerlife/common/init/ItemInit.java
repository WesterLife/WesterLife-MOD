package fr.yan36.westerlife.common.init;

import fr.yan36.westerlife.common.init.items.ItemBase;
import fr.yan36.westerlife.common.init.items.ItemBillet;
import net.minecraft.item.Item;

import java.util.ArrayList;
import java.util.List;

public class ItemInit {

    public static List<Item> ITEMS = new ArrayList<Item>();

    public static final Item CINQ_EUROS = new ItemBillet("cinqeuros");
    public static final Item DIX_EUROS = new ItemBillet("dixeuros");
    public static final Item VINGT_EUROS = new ItemBillet("vingteuros");
    public static final Item CINQUANTE_EUROS = new ItemBillet("cinquanteeuros");
    public static final Item CENT_EUROS = new ItemBillet("centeuros");
    public static final Item DEUX_CENTS_EUROS = new ItemBillet("deuxcenteuros");
    public static final Item CINQ_CENTS_EUROS = new ItemBillet("cinqcenteuros");

    public static final Item CARTE_BANCAIRE = new ItemBase("carte_bancaire");
}
