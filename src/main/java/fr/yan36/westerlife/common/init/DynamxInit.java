package fr.yan36.westerlife.common.init;

import fr.yan36.westerlife.Main;
import fr.yan36.westerlife.common.blocks.dynamx.*;
import fr.yan36.westerlife.common.items.ItemDynamx;
import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;

public class DynamxInit {

    public static ItemDynamx PistoletRadar;
    public static ItemDynamx Belier;
    public static ItemDynamx Menottes;
    public static ItemDynamx Taser;

    public static BlockSignVillage SignVillage;
    public static BlockDistributeur Distributeur;
    public static BlockKeypad Keypad;
    public static BlockBisign doublefeurouge;
    public static BlockTerminalDePaiement TerminalDePaiement;
    public static BlockRadarFixe radarFixe;
    public static BlockRalentisseur ralentisseur;
    public static BlockBarriereLevante barriereLevante;
    public static BlockComputer computer;

    public static BlockRoad barrierePolice;
    public static BlockRoad clotureChantier;
    public static BlockRoad poteau;
    public static BlockRoad panneauxType1;

    public static void init() {

        //Validated items
        PistoletRadar = (ItemDynamx) new ItemDynamx(Main.MODID, "pistoletradar", new ResourceLocation("westerlife","models/dynamx/items/pistoletradar/pistoletradar.obj")).setMaxStackSize(1);
        Menottes = (ItemDynamx) new ItemDynamx(Main.MODID, "menottes", new ResourceLocation("westerlife","models/dynamx/items/menottes/menotte.obj")).setMaxStackSize(1);
        Taser = (ItemDynamx) new ItemDynamx(Main.MODID, "taser", new ResourceLocation("westerlife","models/dynamx/items/taser/taser.obj")).setMaxStackSize(1);

        //Old items
        Belier = (ItemDynamx) new ItemDynamx(Main.MODID, "belier", new ResourceLocation("westerlife","models/dynamx/belier/belier.obj")).setMaxStackSize(1);

        //Validated blocks
        Distributeur = new BlockDistributeur(Material.ROCK, Main.MODID, "distributeur", new ResourceLocation("westerlife","models/dynamx/blocks/atm/atm.obj"));
        TerminalDePaiement = new BlockTerminalDePaiement(Material.ANVIL, Main.MODID, "tdp", new ResourceLocation("westerlife","models/dynamx/blocks/tdp/paiement.obj"));
        radarFixe = new BlockRadarFixe(Material.ANVIL, Main.MODID, "radar", new ResourceLocation("westerlife","models/dynamx/blocks/radar/radarfixe.obj"));
        ralentisseur = new BlockRalentisseur(Material.ANVIL, Main.MODID, "ralentisseur", new ResourceLocation("westerlife","models/dynamx/blocks/ralentisseur/ralentisseur.obj"));
        barriereLevante = new BlockBarriereLevante(Material.ANVIL, Main.MODID, "barrierelevante", new ResourceLocation("westerlife","models/dynamx/blocks/barriere_levante/barrièrelevante.obj"));
        computer = new BlockComputer(Material.ANVIL, Main.MODID, "computer", new ResourceLocation("westerlife","models/dynamx/blocks/computer/pc.obj"));

        //Road blocks
        barrierePolice = new BlockRoad(Material.ANVIL, Main.MODID, "barrierepolice", new ResourceLocation("westerlife","models/dynamx/blocks/barriere_police/barriere.obj"));
        clotureChantier = new BlockRoad(Material.ANVIL, Main.MODID, "cloturechantier", new ResourceLocation("westerlife","models/dynamx/blocks/cloture_chantier/cloturechantier.obj"));
        poteau = new BlockRoad(Material.ANVIL, Main.MODID, "poteau", new ResourceLocation("westerlife","models/dynamx/blocks/poteau/poteau.obj"));
        panneauxType1 = new BlockRoad(Material.ANVIL, Main.MODID, "panneautype1", new ResourceLocation("westerlife","models/dynamx/blocks/panneaux/type1/1.obj"));

        //Old items
        SignVillage = new BlockSignVillage(Material.ANVIL, Main.MODID, "panneauvillage", new ResourceLocation("westerlife","models/dynamx/signvillage/sign.obj"));
        doublefeurouge = new BlockBisign(Material.ANVIL, Main.MODID, "doublefeurouge", new ResourceLocation("westerlife","models/dynamx/bisign/bicolor.obj"));
        //feu_tricolore = new BlockDynamx(Material.ANVIL, Main.MODID, "feutricolore", "feut/feut.obj");
        //Keypad = new BlockKeypad(Material.ANVIL, Main.MODID, "keypad", "keypad/keypad.obj");
    }
}
