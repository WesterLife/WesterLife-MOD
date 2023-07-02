package fr.yan36.westerlife.common.init;

import fr.dynamx.common.items.DynamXItemArmor;
import fr.yan36.westerlife.Main;
import fr.yan36.westerlife.common.blocks.dynamx.*;
import fr.yan36.westerlife.common.items.dynamx.ItemDynamx;
import fr.yan36.westerlife.common.items.dynamx.ItemExtincteur;
import fr.yan36.westerlife.common.items.dynamx.ItemPaper;
import fr.yan36.westerlife.common.items.dynamx.ItemPoteauRemote;
import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;

public class DynamXInit {

    public static ItemDynamx PistoletRadar;
    public static ItemDynamx Belier;
    public static ItemDynamx Menottes;
    public static ItemDynamx Taser;
    public static ItemPaper Paper;
    public static ItemExtincteur Extincteur;
    public static ItemDynamx Seringue;
    public static ItemPoteauRemote PoteauRemote;
    public static ItemDynamx MatraqueTelescopique;
    public static BlockDistributeur Distributeur;
    public static BlockDigicode digicode;
    public static BlockBisign doublefeurouge;
    public static BlockTerminalDePaiement TerminalDePaiement;
    public static BlockRadarFixe radarFixe;
    public static BlockRalentisseur ralentisseur;
    public static BlockBarriereLevante barriereLevante;
    public static BlockComputer computer;
    public static BlockPhotocopieuse photocopieuse;
    public static BlockDeskPhone deskPhone;
    public static BlockPompeEssence pompeEssence;
    public static BlockColored bookshelf;
    public static BlockColored desklamp;
    public static BlockDynamx videoprojector;
    public static BlockDynamx relay;
    public static BlockDynamx flag;
    public static BlockDynamx flageu;
    public static BlockDynamx officeflag;
    public static BlockDynamx officeflageu;
    public static BlockPanneauAgglomeration panneauAgglomeration;
    public static BlockIRM irm;
    public static BlockRoad barrierePolice;
    public static BlockRoad clotureChantier;
    public static BlockRoad poteau;
    public static BlockFeuRouge feurouge;
    public static BlockHerse herse;
    public static BlockRoad trafficcone;
    public static BlockRoad balisep;
    public static BlockRoad balise_white;
    public static BlockRoad balise_yellow;
    public static BlockRoad balise_green;
    public static BlockRoad balise_orange;
    public static BlockPanneauRue panneauRue;
    public static BlockPanneauRue panneauRueSP;

    public static BlockTombe tombe;
    public static BlockSpot spot;
    public static BlockLyre lyre;
    public static BlockScreen screen;
    public static BlockPoteauLevant poteauLevant;
    public static BlockChair chair;
    public static BlockColored table;
    public static BlockPorteNom porteNom;
    public static HashMap<String, DynamXItemArmor<?>> fastRegistryAccess = new HashMap<>();
    public static void init() {

        //Validated items
        PistoletRadar = (ItemDynamx) new ItemDynamx(Main.MODID, "pistoletradar", new ResourceLocation("westerlife","models/dynamx/items/pistoletradar/pistoletradar.obj")).setMaxStackSize(1);
        Menottes = (ItemDynamx) new ItemDynamx(Main.MODID, "menottes", new ResourceLocation("westerlife","models/dynamx/items/menottes/menotte.obj")).setMaxStackSize(1);
        Taser = (ItemDynamx) new ItemDynamx(Main.MODID, "taser", new ResourceLocation("westerlife","models/dynamx/items/taser/taser.obj")).setMaxStackSize(1);
        Paper = (ItemPaper) new ItemPaper(Main.MODID, "paper", new ResourceLocation("westerlife","models/dynamx/items/paper/paper.obj")).setMaxStackSize(1);
        Extincteur = (ItemExtincteur) new ItemExtincteur(Main.MODID, "extincteur", new ResourceLocation("westerlife","models/dynamx/items/extincteur/extincteur.obj"));
        Seringue = (ItemDynamx) new ItemDynamx(Main.MODID, "seringue", new ResourceLocation("westerlife","models/dynamx/items/seringue/seringue.obj")).setMaxStackSize(1);
        MatraqueTelescopique = (ItemDynamx) new ItemDynamx(Main.MODID, "matraquetelescopique", new ResourceLocation("westerlife","models/dynamx/items/matraquetelescopique/telesc.obj")).setMaxStackSize(1);
        PoteauRemote = (ItemPoteauRemote) new ItemPoteauRemote(Main.MODID, "poteauremote", new ResourceLocation("westerlife","models/dynamx/items/poteauremote/remote.obj")).setMaxStackSize(1);

        //Old items
        Belier = (ItemDynamx) new ItemDynamx(Main.MODID, "belier", new ResourceLocation("westerlife","models/dynamx/belier/belier.obj")).setMaxStackSize(1);

        //Validated blocks
        Distributeur = new BlockDistributeur(Material.ROCK, Main.MODID, "distributeur", new ResourceLocation("westerlife","models/dynamx/blocks/atm/atm.obj"));
        TerminalDePaiement = new BlockTerminalDePaiement(Material.ANVIL, Main.MODID, "tdp", new ResourceLocation("westerlife","models/dynamx/blocks/tdp/paiement.obj"));
        radarFixe = new BlockRadarFixe(Material.ANVIL, Main.MODID, "radar", new ResourceLocation("westerlife","models/dynamx/blocks/radar/radarfixe.obj"));
        ralentisseur = new BlockRalentisseur(Material.ANVIL, Main.MODID, "ralentisseur", new ResourceLocation("westerlife","models/dynamx/blocks/ralentisseur/ralentisseur.obj"));
        barriereLevante = new BlockBarriereLevante(Material.ANVIL, Main.MODID, "barrierelevante", new ResourceLocation("westerlife","models/dynamx/blocks/barriere_levante/barriere_levante.obj"));
        computer = new BlockComputer(Material.ANVIL, Main.MODID, "computer", new ResourceLocation("westerlife","models/dynamx/blocks/computer/pc.obj"));
        photocopieuse = new BlockPhotocopieuse(Material.ANVIL, Main.MODID, "photocopieuse", new ResourceLocation("westerlife","models/dynamx/blocks/photocopieuse/photocopieuse.obj"));
        digicode = new BlockDigicode(Material.ANVIL, Main.MODID, "digicode", new ResourceLocation("westerlife","models/dynamx/blocks/digicode/digicode.obj"));
        deskPhone = new BlockDeskPhone(Material.ANVIL, Main.MODID, "deskphone", new ResourceLocation("westerlife","models/dynamx/blocks/deskphone/telephonefixe.obj"));
        pompeEssence = new BlockPompeEssence(Material.ANVIL, Main.MODID, "pompeessence", new ResourceLocation("westerlife","models/dynamx/blocks/pompe_essence/pompeessence.obj"));
        tombe = new BlockTombe(Material.ANVIL, Main.MODID, "tombe", new ResourceLocation("westerlife","models/dynamx/tombe/tombe.obj"));
        screen = new BlockScreen(Material.ANVIL, Main.MODID, "screen", new ResourceLocation("westerlife","models/dynamx/blocks/screen/screen.obj"));
        bookshelf = new BlockColored(Material.ANVIL, Main.MODID, "bookshelf", new ResourceLocation("westerlife","models/dynamx/blocks/bookshelf/bookshelf.obj"));
        videoprojector = new BlockDynamx(Material.ANVIL, Main.MODID, "videoprojector", new ResourceLocation("westerlife","models/dynamx/blocks/videoprojector/videoprojector.obj"));
        chair = new BlockChair(Material.ANVIL, Main.MODID, "chair_white", new ResourceLocation("westerlife","models/dynamx/blocks/chair_white/chair_white.obj"));
        table = new BlockColored(Material.ANVIL, Main.MODID, "table", new ResourceLocation("westerlife","models/dynamx/blocks/table/table.obj"));
        irm = new BlockIRM(Material.ANVIL, Main.MODID, "irm", new ResourceLocation("westerlife","models/dynamx/blocks/irm/irm.obj"));
        flag = new BlockDynamx(Material.ANVIL, Main.MODID, "flag", new ResourceLocation("westerlife","models/dynamx/blocks/flag/flag.obj"));
        flageu = new BlockDynamx(Material.ANVIL, Main.MODID, "flageu", new ResourceLocation("westerlife","models/dynamx/blocks/flag/flageu.obj"));
        officeflag = new BlockDynamx(Material.ANVIL, Main.MODID, "officeflag", new ResourceLocation("westerlife","models/dynamx/blocks/officeflag/officeflag.obj"));
        officeflageu = new BlockDynamx(Material.ANVIL, Main.MODID, "officeflageu", new ResourceLocation("westerlife","models/dynamx/blocks/officeflag/officeflageu.obj"));
        panneauAgglomeration = new BlockPanneauAgglomeration(Material.ANVIL, Main.MODID, "panneauagglomeration", new ResourceLocation("westerlife","models/dynamx/blocks/panneauagglomeration/panneauagglomeration.obj"));
        panneauRue = new BlockPanneauRue(Material.ANVIL, Main.MODID, "panneaurue", new ResourceLocation("westerlife","models/dynamx/blocks/panneaurue/panneaurue.obj"), BlockPanneauRue.Type.WALL);
        panneauRueSP = new BlockPanneauRue(Material.ANVIL, Main.MODID, "panneauruesp", new ResourceLocation("westerlife","models/dynamx/blocks/panneauruesp/panneauruesp.obj"), BlockPanneauRue.Type.PILLAR);

        //Road blocks
        barrierePolice = new BlockRoad(Material.ANVIL, Main.MODID, "barrierepolice", new ResourceLocation("westerlife","models/dynamx/blocks/barriere_police/barriere.obj"));
        clotureChantier = new BlockRoad(Material.ANVIL, Main.MODID, "cloturechantier", new ResourceLocation("westerlife","models/dynamx/blocks/cloture_chantier/cloturechantier.obj"));
        poteau = new BlockRoad(Material.ANVIL, Main.MODID, "poteau", new ResourceLocation("westerlife","models/dynamx/blocks/poteau/poteau.obj"));
        herse = new BlockHerse(Material.ANVIL, Main.MODID, "herse", new ResourceLocation("westerlife","models/dynamx/blocks/herse/herse.obj"));
        feurouge = new BlockFeuRouge(Material.ANVIL, Main.MODID, "feurouge", new ResourceLocation("westerlife","models/dynamx/blocks/feurouge/feurouge.obj"));
        poteauLevant = new BlockPoteauLevant(Material.ANVIL, Main.MODID, "poteaulevant", new ResourceLocation("westerlife","models/dynamx/blocks/poteau_levant/poteau.obj"));
        relay = new BlockDynamx(Material.ANVIL, Main.MODID, "relay", new ResourceLocation("westerlife","models/dynamx/blocks/relay/relay.obj"));
        desklamp = new BlockColored(Material.ANVIL, Main.MODID, "desklamp", new ResourceLocation("westerlife","models/dynamx/blocks/desklamp/desklamp.obj"));
        trafficcone = new BlockRoad(Material.ANVIL, Main.MODID, "trafficcone", new ResourceLocation("westerlife","models/dynamx/blocks/trafficcone/cone.obj"));
        balisep = new BlockRoad(Material.ANVIL, Main.MODID, "balisep", new ResourceLocation("westerlife","models/dynamx/blocks/balisep/balisep.obj"));
        balise_white = new BlockRoad(Material.ANVIL, Main.MODID, "balise_white", new ResourceLocation("westerlife","models/dynamx/blocks/balise/white/balise.obj"));
        balise_orange = new BlockRoad(Material.ANVIL, Main.MODID, "balise_orange", new ResourceLocation("westerlife","models/dynamx/blocks/balise/orange/balise.obj"));
        balise_yellow = new BlockRoad(Material.ANVIL, Main.MODID, "balise_yellow", new ResourceLocation("westerlife","models/dynamx/blocks/balise/yellow/balise.obj"));
        balise_green = new BlockRoad(Material.ANVIL, Main.MODID, "balise_green", new ResourceLocation("westerlife","models/dynamx/blocks/balise/green/balise.obj"));
        porteNom = new BlockPorteNom(Material.ANVIL, Main.MODID, "portenom", new ResourceLocation("westerlife","models/dynamx/blocks/portenom/portenom.obj"));

        //Old items
        doublefeurouge = new BlockBisign(Material.ANVIL, Main.MODID, "doublefeurouge", new ResourceLocation("westerlife","models/dynamx/bisign/bicolor.obj"));
        //feu_tricolore = new BlockDynamx(Material.ANVIL, Main.MODID, "feutricolore", "feut/feut.obj");
        //Keypad = new BlockKeypad(Material.ANVIL, Main.MODID, "keypad", "keypad/keypad.obj");

        // SCENE

        spot = new BlockSpot(Material.ANVIL, Main.MODID, "spot", new ResourceLocation("westerlife","models/dynamx/blocks/spot/spot.obj"));
        lyre = new BlockLyre(Material.ANVIL, Main.MODID, "lyre", new ResourceLocation("westerlife","models/dynamx/blocks/lyre/lyre.obj"));



    }
}
