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
    public static BlockSignVillage SignVillage;
    public static BlockLaptop Laptop;
    public static BlockDistributeur Distributeur;
    public static BlockKeypad Keypad;
    public static BlockBisign doublefeurouge;
    public static BlockTerminalDePaiement TerminalDePaiement;
    public static BlockRadarFixe radarFixe;


    public static void init() {


        PistoletRadar = (ItemDynamx) new ItemDynamx(Main.MODID, "pistoletradar", new ResourceLocation("westerlife","models/dynamx/pistoletradar/pistoletradar.obj")).setMaxStackSize(1);
        Belier = (ItemDynamx) new ItemDynamx(Main.MODID, "belier", new ResourceLocation("westerlife","models/dynamx/belier/belier.obj")).setMaxStackSize(1);
        Menottes = (ItemDynamx) new ItemDynamx(Main.MODID, "menottes", new ResourceLocation("westerlife","models/dynamx/menottes/menotte.obj")).setMaxStackSize(1);

        Distributeur = new BlockDistributeur(Material.ROCK, Main.MODID, "distributeur", new ResourceLocation("westerlife","models/dynamx/atm/atm.obj"));
        SignVillage = new BlockSignVillage(Material.ANVIL, Main.MODID, "panneauvillage", new ResourceLocation("westerlife","models/dynamx/signvillage/sign.obj"));
        doublefeurouge = new BlockBisign(Material.ANVIL, Main.MODID, "doublefeurouge", new ResourceLocation("westerlife","models/dynamx/bisign/bicolor.obj"));
//        feu_tricolore = new BlockDynamx(Material.ANVIL, Main.MODID, "feutricolore", "feut/feut.obj");

        Laptop = new BlockLaptop(Material.ANVIL, Main.MODID, "laptop", new ResourceLocation("westerlife","models/dynamx/laptop/pc.obj"));
        TerminalDePaiement = new BlockTerminalDePaiement(Material.ANVIL, Main.MODID, "tdp", new ResourceLocation("westerlife","models/dynamx/tdp/paiement.obj"));
        radarFixe = new BlockRadarFixe(Material.ANVIL, Main.MODID, "radarfixe", new ResourceLocation("westerlife","models/dynamx/radar/radarfixe.obj"));

        //Keypad = new BlockKeypad(Material.ANVIL, Main.MODID, "keypad", "keypad/keypad.obj");
    }
}
