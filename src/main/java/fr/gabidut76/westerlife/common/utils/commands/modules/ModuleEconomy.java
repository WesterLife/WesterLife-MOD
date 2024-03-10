package fr.gabidut76.westerlife.common.utils.commands.modules;

import fr.gabidut76.westerlife.common.init.ItemInit;
import fr.gabidut76.westerlife.common.network.PacketOpenAcsGui;
import fr.gabidut76.westerlife.common.network.PacketOpenGuiECO;
import fr.gabidut76.westerlife.common.objects.character.Character;
import fr.gabidut76.westerlife.common.objects.economy.BankAccount;
import fr.gabidut76.westerlife.common.utils.commands.CommandModule;
import fr.gabidut76.westerlife.westerapi.api.NemesisLink;
import fr.gabidut76.westerlife.westercore.Main;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;

import javax.annotation.Nullable;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ModuleEconomy extends CommandModule {

    public ModuleEconomy() {
        super("eco");
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) {
        EntityPlayer player = (EntityPlayer) sender;
        String account_number = "";
        if(args.length > 1) {
            if(args[1].equals("givecard")) {
                try {
                    List<BankAccount> allBank = NemesisLink.NEMESIS_API.getAllBankAccounts();
                    List<Character> characters = NemesisLink.NEMESIS_API.getAllCharacters();
                    Main.network.sendTo(new PacketOpenGuiECO(allBank, characters), (EntityPlayerMP) sender);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        } else {
            player.sendMessage(new TextComponentString("§b============================================="));
            player.sendMessage(new TextComponentString("§9/wlmod eco help §7- §bAffiche l'aide")); // Fait
//            player.sendMessage(new TextComponentString("§9/wlmod eco info <pseudo/uuid/rib/N° de compte> §7- §bAffiche les informations d'un compte")); // Fait
//            player.sendMessage(new TextComponentString("§9/wlmod eco info global §7- §bAffiche les informations globales de l'économie ( solde total etc )")); // Fait
//            player.sendMessage(new TextComponentString("§9/wlmod eco create <account type> §7- §bCrée un compte bancaire")); // Fait
//            player.sendMessage(new TextComponentString("§9/wlmod eco delete <account> §7- §bSupprime un compte bancaire")); // Fait
//            player.sendMessage(new TextComponentString("§9/wlmod eco set <account> <parameter> <value> §7- §bChanger une valeur d'un compte")); // Fait
//            player.sendMessage(new TextComponentString("§9/wlmod eco addmoney <account> <value> §7- §bAjoute de l'argent à un compte")); // Fait
//            player.sendMessage(new TextComponentString("§9/wlmod eco removemoney <account> <value> §7- §bRetire de l'argent à un compte")); // Fait
            player.sendMessage(new TextComponentString("§9/wlmod eco givecard §7- §bDonner la carte du compte bancaire")); // Fait
//            player.sendMessage(new TextComponentString("§9/wlmod eco move <from account> <to account> <montant> §7- §bDonner la carte du compte bancaire")); // Fait
            player.sendMessage(new TextComponentString("§b============================================="));
        }
    }
    @Override
    public List<String> getTabCompletion(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        List<String> completions = new ArrayList<>();



        return CommandBase.getListOfStringsMatchingLastWord(args, completions);
    }
}
