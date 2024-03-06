package fr.gabidut76.westerlife.common.init;

import fr.gabidut76.westerlife.common.items.*;
import fr.gabidut76.westerlife.westercore.Main;
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

    public static final Item bacon = new fr.gabidut76.westerlife.common.items.ItemFood("bacon", 1, 0.5f);
    public static final Item baguette = new fr.gabidut76.westerlife.common.items.ItemFood("baguette", 1, 0.5f);
    public static final Item burger_bread = new fr.gabidut76.westerlife.common.items.ItemFood("burger_bread", 1, 0.5f);

    public static final Item cheese = new fr.gabidut76.westerlife.common.items.ItemFood("cheese", 1, 0.5f);
    public static final Item chicken = new fr.gabidut76.westerlife.common.items.ItemFood("chicken", 1, 0.5f);
    public static final Item cooked_steak = new fr.gabidut76.westerlife.common.items.ItemFood("cooked_steak", 1, 0.5f);
    public static final Item salad = new fr.gabidut76.westerlife.common.items.ItemFood("salad", 1, 0.5f);
    public static final Item steak = new fr.gabidut76.westerlife.common.items.ItemFood("steak", 1, 0.5f);
    public static final Item tomatos = new fr.gabidut76.westerlife.common.items.ItemFood("tomatos", 1, 0.5f);
    public static final Item ketchup = new fr.gabidut76.westerlife.common.items.ItemFood("ketchup", 1, 0.5f);
    public static final Item deluxe = new fr.gabidut76.westerlife.common.items.ItemFood("deluxe", 1, 0.5f);
    public static final Item mayo = new fr.gabidut76.westerlife.common.items.ItemFood("mayo", 1, 0.5f);
    public static final Item panneaucirculation = new ItemPlaceCirculationSign("panneaucirculation");
    public static final Item canabis_resine = new ItemBase("canabis_resine")
            .setCreativeTab(Main.WESTER_ILLEGAL);

    public static final Item coke = new ItemBase("coke")
            .setCreativeTab(Main.WESTER_ILLEGAL);
    public static final Item cigarette_paper = new ItemCigarettePaper("cigarette_paper",32)
            .setCreativeTab(Main.WESTER_ILLEGAL);
    public static final Item joint = new ItemSmokable("joint")
            .setCreativeTab(Main.WESTER_ILLEGAL);

    public static void init() {

    }


}


