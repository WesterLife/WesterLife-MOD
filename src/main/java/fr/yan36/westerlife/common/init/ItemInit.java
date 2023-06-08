package fr.yan36.westerlife.common.init;

import fr.yan36.westerlife.common.items.ItemBase;
import fr.yan36.westerlife.common.items.ItemBillet;
import fr.yan36.westerlife.common.items.ItemCard;
import fr.yan36.westerlife.common.items.ItemDrink;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;

import java.util.ArrayList;
import java.util.List;

public class ItemInit {

    public static List<Item> ITEMS = new ArrayList<Item>();
    public static List<ItemFood> ITEMS_FOOD = new ArrayList<ItemFood>();

    public static final Item CINQ_EUROS = new ItemBillet("cinqeuros");
    public static final Item DIX_EUROS = new ItemBillet("dixeuros");
    public static final Item VINGT_EUROS = new ItemBillet("vingteuros");
    public static final Item CINQUANTE_EUROS = new ItemBillet("cinquanteeuros");
    public static final Item CENT_EUROS = new ItemBillet("centeuros");
    public static final Item DEUX_CENTS_EUROS = new ItemBillet("deuxcenteuros");
    public static final Item CINQ_CENTS_EUROS = new ItemBillet("cinqcenteuros");

    public static final Item CARTE_BANCAIRE = new ItemBase("carte_bancaire");

    public static final Item CNI = new ItemCard("cni", ItemCard.CardType.CNI);
    public static final Item DIPLO = new ItemCard("diplo", ItemCard.CardType.DIPLO);
    public static final Item GDI = new ItemCard("gdi", ItemCard.CardType.GDI);
    public static final Item GOUV = new ItemCard("gouv", ItemCard.CardType.GOUV);
    public static final Item PERM = new ItemCard("perm", ItemCard.CardType.PERM);
    public static final Item PREF = new ItemCard("pref", ItemCard.CardType.PREF);

    public static final Item BOISSON = new ItemDrink("bouteille",1, 0.0F);
}


