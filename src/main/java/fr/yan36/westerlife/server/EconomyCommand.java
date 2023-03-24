package fr.yan36.westerlife.server;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;

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
        switch (args.length){
            case 3:
                if(args[0].equalsIgnoreCase("eco") && args[1].equalsIgnoreCase("info") && args[2].equalsIgnoreCase("global")){

                }
                break;
            case 4:
                //WIP
                break;
            default:
                help(player);
                break;
        }
    }

    public void help(EntityPlayer player){
        player.sendMessage(new TextComponentString("§6/westerlife eco help §7: Affiche l'aide")); // Fait
        player.sendMessage(new TextComponentString("§6/westerlife eco info <account type> <pseudo/id/uuid/rib/N° de compte> §7: Affiche les informations d'un compte"));
        player.sendMessage(new TextComponentString("§6/westerlife eco info global §7: Affiche les informations globales de l'économie ( solde total etc )")); // En attente
        player.sendMessage(new TextComponentString("§6/westerlife eco create <account type> §7: Crée un compte bancaire"));
        player.sendMessage(new TextComponentString("§6/westerlife eco delete <account type> <id> §7: Supprime un compte bancaire"));
        player.sendMessage(new TextComponentString("§6/westerlife eco set <account type> <account> <parameter> <value> §7: Changer une valeur d'un compte"));
        player.sendMessage(new TextComponentString("§6/westerlife eco addmoney <account type> <account> <value> §7: Ajoute de l'argent à un compte"));
        player.sendMessage(new TextComponentString("§6/westerlife eco removemoney <account type> <account> <value> §7: Retire de l'argent à un compte"));
        player.sendMessage(new TextComponentString("§6/westerlife eco givecard <account type> <account> §7: Donner la carte du compte bancaire"));
    }
}
