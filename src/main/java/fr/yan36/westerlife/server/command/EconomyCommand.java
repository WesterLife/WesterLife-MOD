package fr.yan36.westerlife.server.command;

import fr.yan36.westerlife.common.init.ItemInit;
import fr.yan36.westerlife.server.bdd.DBUtils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class EconomyCommand extends CommandBase {
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
        switch (args.length){
            case 0:
            case 1:
                help(player);
                break;
            case 3:
                if(args[0].equalsIgnoreCase("eco") && args[1].equalsIgnoreCase("info")){
                    if(args[2].equalsIgnoreCase("global")){
                        player.sendMessage(new TextComponentString("§cFonctionnalité en cours de développement !"));
                        break;
                    } else {
                        boolean accountFind = false;
                        // Voir les infos via le numéro de compte
                        for(Object account : DBUtils.getMultipleInfos("bank_account", "account_number")){
                            if(account.toString().equalsIgnoreCase(args[2])){
                                accountFind = true;
                                account_number = args[2];
                            }
                        }
                        // Voir les infos via le RIB
                        for(Object account : DBUtils.getMultipleInfos("bank_account", "RIB")){
                            if(account.toString().equalsIgnoreCase(args[2])){
                                accountFind = true;
                                account_number = DBUtils.getStringInfo("account_number", "bank_account", "RIB", args[2]);
                            }
                        }
                        // Voir les infos via l'uuid d'un joueur
                        if(DBUtils.getStringInfo("account_number", "bank_account", "owner", args[2]) != null){
                            accountFind = true;
                            account_number = DBUtils.getStringInfo("account_number", "bank_account", "owner", args[2]);
                        }
                        //Voir les infos via le pseudo d'un joueur
                        String uuid = DBUtils.getStringInfo("uuid", "players", "pseudo", args[2]);
                        if(DBUtils.getStringInfo("account_number", "bank_account", "owner", uuid) != null){
                            accountFind = true;
                            account_number = DBUtils.getStringInfo("account_number", "bank_account", "owner", uuid);
                        }
                        if(accountFind){
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
                } else if(args[0].equalsIgnoreCase("eco") && args[1].equalsIgnoreCase("create")){
                    if(args[2].equalsIgnoreCase("personnal")){
                        Random random = new Random();
                        account_number = String.valueOf(random.nextInt(900000) + 100000);
                        while (DBUtils.getMultipleInfos("bank_account", "account_number").contains(account_number)){
                            account_number = String.valueOf(random.nextInt(900000) + 100000);
                        }
                        LocalDate currentDate = LocalDate.now();
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        DBUtils.createBankAccount(player.getUniqueID().toString(), Integer.parseInt(account_number), String.valueOf(random.nextInt(9000) + 1000), currentDate.format(formatter), true);
                        break;
                    } else if(args[2].equalsIgnoreCase("entreprise")){
                        Random random = new Random();
                        account_number = String.valueOf(random.nextInt(900000) + 100000);
                        LocalDate currentDate = LocalDate.now();
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        DBUtils.createBankAccount(player.getUniqueID().toString(), Integer.parseInt(account_number), String.valueOf(random.nextInt(9000) + 1000), currentDate.format(formatter), false);
                        break;
                    }
                } else if (args[0].equalsIgnoreCase("eco") && args[1].equalsIgnoreCase("delete")){
                    if(DBUtils.getMultipleInfos("bank_account", "account_number").contains(args[2])){
                        DBUtils.removeRow("bank_account", "account_number", args[2]);
                        break;
                    } else {
                        player.sendMessage(new TextComponentString("§cCe compte n'existe pas !"));
                        break;
                    }
                } else if(args[0].equalsIgnoreCase("eco") && args[1].equalsIgnoreCase("givecard")){
                    if(DBUtils.getMultipleInfos("bank_account", "account_number").contains(args[2])){
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
                help(player);
                break;
            case 4:
                if(args[0].equalsIgnoreCase("eco") && args[1].equalsIgnoreCase("addmoney")){
                    if(DBUtils.getMultipleInfos("bank_account", "account_number").contains(args[2])){
                        try {
                            int toAdd = Integer.parseInt(args[3]);
                            int solde = Integer.parseInt(DBUtils.getStringInfo("solde", "bank_account", "account_number", args[2]));
                            DBUtils.setInfo("bank_account", "solde", String.valueOf(solde + toAdd), "account_number", args[2]);
                            player.sendMessage(new TextComponentString("§aVous avez ajouté §6" + toAdd + "§a au compte n°" + args[2]));
                            break;
                        } catch (NumberFormatException e){
                            player.sendMessage(new TextComponentString("§cLe montant doit être un nombre !"));
                            break;
                        }
                    } else {
                        player.sendMessage(new TextComponentString("§cCe compte n'existe pas !"));
                        break;
                    }
                } else if(args[0].equalsIgnoreCase("eco") && args[1].equalsIgnoreCase("removemoney")){
                    if(DBUtils.getMultipleInfos("bank_account", "account_number").contains(args[2])){
                        try {
                            int toRemove = Integer.parseInt(args[3]);
                            int solde = Integer.parseInt(DBUtils.getStringInfo("solde", "bank_account", "account_number", args[2]));
                            if(solde - toRemove < 0){
                                player.sendMessage(new TextComponentString("§cLe compte n°" + args[2] + " n'a pas assez d'argent !"));
                                break;
                            } else {
                                DBUtils.setInfo("bank_account", "solde", String.valueOf(solde - toRemove), "account_number", args[2]);
                                player.sendMessage(new TextComponentString("§aVous avez retiré §6" + toRemove + "§a au compte n°" + args[2]));
                                break;
                            }
                        } catch (NumberFormatException e){
                            player.sendMessage(new TextComponentString("§cLe montant doit être un nombre !"));
                            break;
                        }
                    } else {
                        player.sendMessage(new TextComponentString("§cCe compte n'existe pas !"));
                        break;
                    }
                }
                break;
        }
    }

    public void help(EntityPlayer player){
        player.sendMessage(new TextComponentString("§b============================================="));
        player.sendMessage(new TextComponentString("§6/westerlife eco help §7: Affiche l'aide")); // Fait
        player.sendMessage(new TextComponentString("§6/westerlife eco info <pseudo/uuid/rib/N° de compte> §7: Affiche les informations d'un compte")); // Fait
        player.sendMessage(new TextComponentString("§6/westerlife eco info global §7: Affiche les informations globales de l'économie ( solde total etc )")); // En attente
        player.sendMessage(new TextComponentString("§6/westerlife eco create <account type> §7: Crée un compte bancaire")); // Fait
        player.sendMessage(new TextComponentString("§6/westerlife eco delete <account> §7: Supprime un compte bancaire")); // Fait
        player.sendMessage(new TextComponentString("§6/westerlife eco set <account> <parameter> <value> §7: Changer une valeur d'un compte"));
        player.sendMessage(new TextComponentString("§6/westerlife eco addmoney <account> <value> §7: Ajoute de l'argent à un compte")); // Fait
        player.sendMessage(new TextComponentString("§6/westerlife eco removemoney <account> <value> §7: Retire de l'argent à un compte")); // Fait
        player.sendMessage(new TextComponentString("§6/westerlife eco givecard <account> §7: Donner la carte du compte bancaire")); // Fait
        player.sendMessage(new TextComponentString("§b============================================="));
    }
}
