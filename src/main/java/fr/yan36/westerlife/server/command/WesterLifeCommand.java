package fr.yan36.westerlife.server.command;

import fr.yan36.westerlife.common.init.ItemInit;
import fr.yan36.westerlife.server.bdd.DBUtils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WesterLifeCommand extends CommandBase {
    @Override
    public String getName() {
        return "westerlife";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return null;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        EntityPlayer player = (EntityPlayer) sender;
        String account_number = "";
        if(args.length >= 1){
            // Commande sur l'économie
            if(args[0].equalsIgnoreCase("eco")){
                switch (args.length) {
                    case 1:
                        help(player, args[0]);
                        break;
                    case 3:
                        if (args[0].equalsIgnoreCase("eco") && args[1].equalsIgnoreCase("info")) {
                            if (args[2].equalsIgnoreCase("global")) {
                                int soldeGlobal = 0;
                                int totalAccount = 0;
                                int totalPersonnalAccount = 0;
                                int totalEntrepriseAccount = 0;
                                for (Object individualSolde : DBUtils.getMultipleInfos("bank_account", "solde")) {
                                    try {
                                        soldeGlobal += Integer.parseInt(individualSolde.toString());
                                    } catch (NumberFormatException e) {
                                        e.printStackTrace();
                                        break;
                                    }
                                }
                                for (Object individualAccount : DBUtils.getMultipleInfos("bank_account", "account_number")) {
                                    totalAccount++;
                                    if (DBUtils.getStringInfo("RIB", "bank_account", "account_number", individualAccount.toString()).contains("F10")) {
                                        totalPersonnalAccount++;
                                    } else {
                                        totalEntrepriseAccount++;
                                    }
                                }
                                player.sendMessage(new TextComponentString("§b============================================="));
                                player.sendMessage(new TextComponentString("§6Informations globales de l'économie"));
                                player.sendMessage(new TextComponentString("§6Solde global : §9" + soldeGlobal));
                                player.sendMessage(new TextComponentString("§6Nombre de comptes : §9" + totalAccount));
                                player.sendMessage(new TextComponentString("§6Nombre de comptes personnels : §9" + totalPersonnalAccount));
                                player.sendMessage(new TextComponentString("§6Nombre de comptes entreprises : §9" + totalEntrepriseAccount));
                                player.sendMessage(new TextComponentString("§6Moyenne de solde par compte : §9" + soldeGlobal / totalAccount));
                                player.sendMessage(new TextComponentString("§b============================================="));
                                break;
                            } else {
                                boolean accountFind = false;
                                // Voir les infos via le numéro de compte
                                for (Object account : DBUtils.getMultipleInfos("bank_account", "account_number")) {
                                    if (account.toString().equalsIgnoreCase(args[2])) {
                                        accountFind = true;
                                        account_number = args[2];
                                    }
                                }
                                // Voir les infos via le RIB
                                for (Object account : DBUtils.getMultipleInfos("bank_account", "RIB")) {
                                    if (account.toString().equalsIgnoreCase(args[2])) {
                                        accountFind = true;
                                        account_number = DBUtils.getStringInfo("account_number", "bank_account", "RIB", args[2]);
                                    }
                                }
                                // Voir les infos via l'uuid d'un joueur
                                if (DBUtils.getStringInfo("account_number", "bank_account", "owner", args[2]) != null) {
                                    accountFind = true;
                                    account_number = DBUtils.getStringInfo("account_number", "bank_account", "owner", args[2]);
                                }
                                //Voir les infos via le pseudo d'un joueur
                                String uuid = DBUtils.getStringInfo("uuid", "players", "pseudo", args[2]);
                                if (DBUtils.getStringInfo("account_number", "bank_account", "owner", uuid) != null) {
                                    accountFind = true;
                                    account_number = DBUtils.getStringInfo("account_number", "bank_account", "owner", uuid);
                                }
                                if (accountFind) {
                                    player.sendMessage(new TextComponentString("§b============================================="));
                                    player.sendMessage(new TextComponentString("§6Informations du compte bancaire n°" + account_number));
                                    player.sendMessage(new TextComponentString("§6Propriétaire : §9" + DBUtils.getStringInfo("pseudo", "players", "uuid", DBUtils.getStringInfo("owner", "bank_account", "account_number", account_number))));
                                    player.sendMessage(new TextComponentString("§6RIB : §9" + DBUtils.getStringInfo("RIB", "bank_account", "account_number", account_number)));
                                    player.sendMessage(new TextComponentString("§6Solde : §9" + DBUtils.getStringInfo("solde", "bank_account", "account_number", account_number)));
                                    player.sendMessage(new TextComponentString("§6Date de création : §9" + DBUtils.getStringInfo("creation_date", "bank_account", "account_number", account_number)));
                                    player.sendMessage(new TextComponentString("§b============================================="));
                                    break;
                                } else {
                                    player.sendMessage(new TextComponentString("§cCe compte n'existe pas !"));
                                    break;
                                }
                            }
                        } else if (args[0].equalsIgnoreCase("eco") && args[1].equalsIgnoreCase("create")) {
                            if (args[2].equalsIgnoreCase("personal")) {
                                Random random = new Random();
                                account_number = String.valueOf(random.nextInt(900000) + 100000);
                                while (DBUtils.getMultipleInfos("bank_account", "account_number").contains(account_number)) {
                                    account_number = String.valueOf(random.nextInt(900000) + 100000);
                                }
                                LocalDate currentDate = LocalDate.now();
                                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                                DBUtils.createBankAccount(player.getUniqueID().toString(), Integer.parseInt(account_number), String.valueOf(random.nextInt(9000) + 1000), currentDate.format(formatter), true);
                                break;
                            } else if (args[2].equalsIgnoreCase("entreprise")) {
                                Random random = new Random();
                                account_number = String.valueOf(random.nextInt(900000) + 100000);
                                LocalDate currentDate = LocalDate.now();
                                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                                DBUtils.createBankAccount(player.getUniqueID().toString(), Integer.parseInt(account_number), String.valueOf(random.nextInt(9000) + 1000), currentDate.format(formatter), false);
                                break;
                            }
                        } else if (args[0].equalsIgnoreCase("eco") && args[1].equalsIgnoreCase("delete")) {
                            if (DBUtils.getMultipleInfos("bank_account", "account_number").contains(args[2])) {
                                DBUtils.removeRow("bank_account", "account_number", args[2]);
                                break;
                            } else {
                                player.sendMessage(new TextComponentString("§cCe compte n'existe pas !"));
                                break;
                            }
                        } else if (args[0].equalsIgnoreCase("eco") && args[1].equalsIgnoreCase("givecard")) {
                            if (DBUtils.getMultipleInfos("bank_account", "account_number").contains(args[2])) {
                                ItemStack card = new ItemStack(ItemInit.CARTE_BANCAIRE);
                                card.setStackDisplayName("§6Carte bancaire n°" + args[2]);
                                card.getTagCompound().setString("account_number", args[2]);
                                card.getTagCompound().setString("RIB", DBUtils.getStringInfo("RIB", "bank_account", "account_number", args[2]));
                                card.getTagCompound().setString("owner", DBUtils.getStringInfo("owner", "bank_account", "account_number", args[2]));
                                player.addItemStackToInventory(card);
                            } else {
                                player.sendMessage(new TextComponentString("§cCe compte n'existe pas !"));
                                break;
                            }
                        }
                        help(player, args[0]);
                        break;
                    case 4:
                        if (args[0].equalsIgnoreCase("eco") && args[1].equalsIgnoreCase("addmoney")) {
                            if (DBUtils.getMultipleInfos("bank_account", "account_number").contains(args[2])) {
                                try {
                                    int toAdd = Integer.parseInt(args[3]);
                                    int solde = Integer.parseInt(DBUtils.getStringInfo("solde", "bank_account", "account_number", args[2]));
                                    DBUtils.setInfo("bank_account", "account_number", args[2], "solde", String.valueOf(solde + toAdd));
                                    player.sendMessage(new TextComponentString("§aVous avez ajouté §6" + toAdd + "§a au compte n°" + args[2]));
                                    break;
                                } catch (NumberFormatException e) {
                                    player.sendMessage(new TextComponentString("§cLe montant doit être un nombre !"));
                                    break;
                                }
                            } else {
                                player.sendMessage(new TextComponentString("§cCe compte n'existe pas !"));
                                break;
                            }
                        } else if (args[0].equalsIgnoreCase("eco") && args[1].equalsIgnoreCase("removemoney")) {
                            if (DBUtils.getMultipleInfos("bank_account", "account_number").contains(args[2])) {
                                try {
                                    int toRemove = Integer.parseInt(args[3]);
                                    int solde = Integer.parseInt(DBUtils.getStringInfo("solde", "bank_account", "account_number", args[2]));
                                    if (solde - toRemove < 0) {
                                        player.sendMessage(new TextComponentString("§cLe compte n°" + args[2] + " n'a pas assez d'argent !"));
                                        break;
                                    } else {
                                        DBUtils.setInfo("bank_account", "account_number", args[2], "solde", String.valueOf(solde - toRemove));
                                        player.sendMessage(new TextComponentString("§aVous avez retiré §6" + toRemove + "§a au compte n°" + args[2]));
                                        break;
                                    }
                                } catch (NumberFormatException e) {
                                    player.sendMessage(new TextComponentString("§cLe montant doit être un nombre !"));
                                    break;
                                }
                            } else {
                                player.sendMessage(new TextComponentString("§cCe compte n'existe pas !"));
                                break;
                            }
                        }
                        break;
                    case 5:
                        if (args[0].equalsIgnoreCase("eco") && args[1].equalsIgnoreCase("set")) {
                            if (DBUtils.getMultipleInfos("bank_account", "account_number").contains(args[2])) {
                                try {
                                    DBUtils.setInfo("bank_account", args[3], args[4], "account_number", args[2]);
                                } catch (Exception e) {
                                    player.sendMessage(new TextComponentString("§cErreur !"));
                                    break;
                                }
                            } else {
                                player.sendMessage(new TextComponentString("§cCe compte n'existe pas !"));
                                break;
                            }
                        } else if (args[0].equalsIgnoreCase("eco") && args[1].equalsIgnoreCase("move")) {
                            if (DBUtils.getMultipleInfos("bank_account", "account_number").contains(args[2]) && DBUtils.getMultipleInfos("bank_account", "account_number").contains(args[3])) {
                                try {
                                    int toMove = Integer.parseInt(args[4]);
                                    int solde = Integer.parseInt(DBUtils.getStringInfo("solde", "bank_account", "account_number", args[2]));
                                    if (solde - toMove < 0) {
                                        player.sendMessage(new TextComponentString("§cLe compte n°" + args[2] + " n'a pas assez d'argent !"));
                                        break;
                                    } else {
                                        DBUtils.setInfo("bank_account", "account_number", args[2], "solde", String.valueOf(solde - toMove));
                                        int solde2 = Integer.parseInt(DBUtils.getStringInfo("solde", "bank_account", "account_number", args[3]));
                                        DBUtils.setInfo("bank_account", "account_number", args[3], "solde", String.valueOf(solde2 + toMove));
                                        player.sendMessage(new TextComponentString("§aVous avez déplacé §6" + toMove + "§a du compte n°" + args[2] + " au compte n°" + args[3]));
                                        break;
                                    }
                                } catch (NumberFormatException e) {
                                    player.sendMessage(new TextComponentString("§cLe montant doit être un nombre !"));
                                    break;
                                }
                            } else {
                                player.sendMessage(new TextComponentString("§cUn des comptes n'existe pas !"));
                                break;
                            }
                        }
                        break;
                }
            } // Commande sur les personnages
            else if(args[0].equalsIgnoreCase("manageperso")){

            }
        } else {
            help(player, "all");
        }
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        List<String> completions = new ArrayList<String>();

        switch (args.length){
            case 1:
                completions.add("eco");
                completions.add("manageperso");
                break;
            case 2:
                if(args[0].equalsIgnoreCase("eco")){
                    completions.add("help");
                    completions.add("info");
                    completions.add("create");
                    completions.add("delete");
                    completions.add("addmoney");
                    completions.add("removemoney");
                    completions.add("set");
                    completions.add("move");
                } else if(args[0].equalsIgnoreCase("manageperso")){
                    completions.add("help");
                    completions.add("set");
                    completions.add("get");
                    completions.add("delete");
                }
                break;
            case 3:
                if(args[0].equalsIgnoreCase("eco")){
                    if(args[1].equalsIgnoreCase("info")){
                        completions.add("global");
                        completions.addAll(DBUtils.getMultipleInfos("bank_account", "account_number"));
                    } else if(args[1].equalsIgnoreCase("create")){
                        completions.add("personal");
                        completions.add("entreprise");
                    } else if(args[1].equalsIgnoreCase("delete")){
                        completions.addAll(DBUtils.getMultipleInfos("bank_account", "account_number"));
                    } else if(args[1].equalsIgnoreCase("addmoney")){
                        completions.addAll(DBUtils.getMultipleInfos("bank_account", "account_number"));
                    } else if(args[1].equalsIgnoreCase("removemoney")){
                        completions.addAll(DBUtils.getMultipleInfos("bank_account", "account_number"));
                    } else if(args[1].equalsIgnoreCase("set")){
                        completions.addAll(DBUtils.getMultipleInfos("bank_account", "account_number"));
                    } else if(args[1].equalsIgnoreCase("move")){
                        completions.addAll(DBUtils.getMultipleInfos("bank_account", "account_number"));
                    }
                }
                if(args[0].equalsIgnoreCase("manageperso")){
                    if(args[1].equalsIgnoreCase("delete")){
                        completions.addAll(DBUtils.getMultipleInfos("players", "pseudo"));
                    } else if(args[1].equalsIgnoreCase("set")){
                        completions.addAll(DBUtils.getMultipleInfos("players", "pseudo"));
                    } else if(args[1].equalsIgnoreCase("get")){
                        completions.addAll(DBUtils.getMultipleInfos("players", "pseudo"));
                    }
                }
                break;
            case 4:
                if(args[0].equalsIgnoreCase("eco")){
                    if(args[1].equalsIgnoreCase("set")){
                        completions.add("solde");
                        completions.add("account_number");
                        completions.add("RIB");
                        completions.add("cb_code");
                        completions.add("owner");
                        completions.add("creation_date");
                    } else if(args[1].equalsIgnoreCase("move")){
                        completions.addAll(DBUtils.getMultipleInfos("bank_account", "account_number"));
                    }
                } else if (args[0].equalsIgnoreCase("manageperso")){
                    if(args[1].equalsIgnoreCase("set") || args[1].equalsIgnoreCase("get")){
                        completions.add("id");
                        completions.add("pseudo");
                        completions.add("uuid");
                        completions.add("familyname");
                        completions.add("firstnames");
                        completions.add("birthdate");
                        completions.add("birthplace");
                        completions.add("nationality");
                        completions.add("sex");
                    }
                }
                break;
        }

        return completions;
    }

    public void help(EntityPlayer player, String args){
        if (args.equalsIgnoreCase("all")){
            player.sendMessage(new TextComponentString("§b============================================="));
            player.sendMessage(new TextComponentString("§6/westerlife eco help §7: Commande pour gérer l'économie")); // Fait
            player.sendMessage(new TextComponentString("§6/westerlife manageperso help §7: Commande pour gérer les personnages")); // Fait
            player.sendMessage(new TextComponentString("§b============================================="));
        } else if(args.equalsIgnoreCase("eco")) {
            player.sendMessage(new TextComponentString("§b============================================="));
            player.sendMessage(new TextComponentString("§6/westerlife eco help §7: Affiche l'aide")); // Fait
            player.sendMessage(new TextComponentString("§6/westerlife eco info <pseudo/uuid/rib/N° de compte> §7: Affiche les informations d'un compte")); // Fait
            player.sendMessage(new TextComponentString("§6/westerlife eco info global §7: Affiche les informations globales de l'économie ( solde total etc )")); // Fait
            player.sendMessage(new TextComponentString("§6/westerlife eco create <account type> §7: Crée un compte bancaire")); // Fait
            player.sendMessage(new TextComponentString("§6/westerlife eco delete <account> §7: Supprime un compte bancaire")); // Fait
            player.sendMessage(new TextComponentString("§6/westerlife eco set <account> <parameter> <value> §7: Changer une valeur d'un compte")); // Fait
            player.sendMessage(new TextComponentString("§6/westerlife eco addmoney <account> <value> §7: Ajoute de l'argent à un compte")); // Fait
            player.sendMessage(new TextComponentString("§6/westerlife eco removemoney <account> <value> §7: Retire de l'argent à un compte")); // Fait
            player.sendMessage(new TextComponentString("§6/westerlife eco givecard <account> §7: Donner la carte du compte bancaire")); // Fait
            player.sendMessage(new TextComponentString("§6/westerlife eco move <from account> <to account> <montant> §7: Donner la carte du compte bancaire")); // Fait
            player.sendMessage(new TextComponentString("§b============================================="));
        } else if (args.equalsIgnoreCase("manageperso")){
            player.sendMessage(new TextComponentString("§b============================================="));
            player.sendMessage(new TextComponentString("§b= §f/westerlife manageperso set <player> <parameters> <value> §b- §fSet un paramètre d'un joueur"));
            player.sendMessage(new TextComponentString("§b= §f/westerlife manageperso get <player> <parameters> §b- §fRécupère un paramètre d'un joueur"));
            player.sendMessage(new TextComponentString("§b= §f/westerlife manageperso delete <player> §b- §fSupprime un joueur"));
            player.sendMessage(new TextComponentString("§b============================================="));
        }
    }
}
