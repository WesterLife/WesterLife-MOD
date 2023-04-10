package fr.yan36.westerlife.common.utils.commands.modules;

import com.mojang.authlib.GameProfile;
import fr.yan36.westerlife.common.init.ItemInit;
import fr.yan36.westerlife.common.utils.commands.CommandModule;
import fr.yan36.westerlife.server.bdd.DBUtils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class ModuleManagePerso extends CommandModule {

    public ModuleManagePerso() {
        super("manageperso");
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) {
        EntityPlayer player = (EntityPlayer) sender;
        ArrayList<String> BDDPlayers = DBUtils.getMultipleInfos("players", "uuid");
        ArrayList<String> players = new ArrayList<String>();
        players.addAll(BDDPlayers.stream()
                .map(UUID::fromString)
                .map(server.getPlayerProfileCache()::getProfileByUUID).filter(Objects::nonNull)
                .map(GameProfile::getName)
                .collect(Collectors.toList()));
        switch (args.length){
            case 2:
                if(args[1].equalsIgnoreCase("help")){
                    break;
                } else if(args[1].equalsIgnoreCase("set")){
                    player.sendMessage(new TextComponentString("§cVous devez préciser un joueur !"));
                    break;
                } else if(args[1].equalsIgnoreCase("get")){
                    player.sendMessage(new TextComponentString("§cVous devez préciser un joueur !"));
                    break;
                } else if (args[1].equalsIgnoreCase("delete")){
                    player.sendMessage(new TextComponentString("§cVous devez préciser un joueur !"));
                    break;
                }
                break;
            case 3:
                if(args[1].equalsIgnoreCase("set")){
                    if(players.contains(args[2])){
                        player.sendMessage(new TextComponentString("§cVous devez préciser un argument !"));
                    } else {
                        player.sendMessage(new TextComponentString("§cCe joueur n'a pas de personnage !"));
                    }
                    break;
                } else if(args[1].equalsIgnoreCase("get")){
                    if(players.contains(args[2])) {
                        player.sendMessage(new TextComponentString("§cVous devez préciser un argument !"));
                    } else {
                        player.sendMessage(new TextComponentString("§cCe joueur n'a pas de personnage !"));
                    }
                    break;
                } else if (args[1].equalsIgnoreCase("delete")){
                    if (players.contains(args[2])) {
                        EntityPlayerMP targetPlayer = server.getPlayerList().getPlayerByUUID(Objects.requireNonNull(server.getPlayerProfileCache().getGameProfileForUsername(args[2])).getId());
                        targetPlayer.connection.disconnect(new TextComponentString("§cVous avez été expulsé du serveur ! §bRaison : §ePersonnage supprimé !"));
                        DBUtils.removeRow("players", "uuid", server.getPlayerProfileCache().getGameProfileForUsername(args[2]).getId().toString());
                        player.sendMessage(new TextComponentString("§aLe joueur §6" + args[2] + "§a a bien été supprimé !"));
                        break;
                    } else {
                        player.sendMessage(new TextComponentString("§cCe joueur n'a pas de personnage !"));
                        break;
                    }
                } else if(args[1].equalsIgnoreCase("info")){
                    if(players.contains(args[2])){
                        player.sendMessage(new TextComponentString("§b============================================="));
                        player.sendMessage(new TextComponentString("§9Nom §7: §b" + DBUtils.getStringInfo("familyname", "players", "uuid", server.getPlayerProfileCache().getGameProfileForUsername(args[2]).getId().toString())));
                        player.sendMessage(new TextComponentString("§9Prénom §7: §b" + DBUtils.getStringInfo("firstnames", "players", "uuid", server.getPlayerProfileCache().getGameProfileForUsername(args[2]).getId().toString())));
                        player.sendMessage(new TextComponentString("§9Date de naissance §7: §b" + DBUtils.getStringInfo("birthdate", "players", "uuid", server.getPlayerProfileCache().getGameProfileForUsername(args[2]).getId().toString())));
                        player.sendMessage(new TextComponentString("§9Lieu de naissance §7: §b" + DBUtils.getStringInfo("birthplace", "players", "uuid", server.getPlayerProfileCache().getGameProfileForUsername(args[2]).getId().toString())));
                        player.sendMessage(new TextComponentString("§9Nationalité §7: §b" + DBUtils.getStringInfo("nationality", "players", "uuid", server.getPlayerProfileCache().getGameProfileForUsername(args[2]).getId().toString())));
                        player.sendMessage(new TextComponentString("§9Sexe §7: §b" + DBUtils.getStringInfo("sex", "players", "uuid", server.getPlayerProfileCache().getGameProfileForUsername(args[2]).getId().toString())));
                        player.sendMessage(new TextComponentString("§b============================================="));
                    } else {
                        player.sendMessage(new TextComponentString("§cCe joueur n'a pas de personnage !"));
                        break;
                    }
                }
                break;
            case 4:
                if(args[1].equalsIgnoreCase("get")){
                    if(players.contains(args[2])) {
                        if(DBUtils.getStringInfo(args[3], "players", "uuid", server.getPlayerProfileCache().getGameProfileForUsername(args[2]).getId().toString()) != null){
                            player.sendMessage(new TextComponentString("§a" + args[3] + " : " + DBUtils.getStringInfo(args[3], "players", "uuid", server.getPlayerProfileCache().getGameProfileForUsername(args[2]).getId().toString())));
                            break;
                        } else {
                            player.sendMessage(new TextComponentString("§cCet argument n'existe pas !"));
                            break;
                        }
                    } else {
                        player.sendMessage(new TextComponentString("§cCe joueur n'a pas de personnage !"));
                        break;
                    }
                }
                if(args[1].equalsIgnoreCase("set")){
                    if(players.contains(args[2])) {
                        if(DBUtils.getStringInfo(args[3], "players", "uuid", server.getPlayerProfileCache().getGameProfileForUsername(args[2]).getId().toString()) != null){
                            player.sendMessage(new TextComponentString("§cVous devez préciser une valeur !"));
                        } else {
                            player.sendMessage(new TextComponentString("§cCet argument n'existe pas !"));
                        }
                        break;
                    } else {
                        player.sendMessage(new TextComponentString("§cCe joueur n'a pas de personnage !"));
                        break;
                    }
                }
                break;
            case 5:
                if(args[1].equalsIgnoreCase("set")){
                    if(players.contains(args[2])) {
                        if(DBUtils.getStringInfo(args[3], "players", "uuid", server.getPlayerProfileCache().getGameProfileForUsername(args[2]).getId().toString()) != null){
                            DBUtils.setInfo("players", "pseudo", args[2], args[3], args[4]);
                            player.sendMessage(new TextComponentString("§aVous avez modifié l'argument " + args[3] + " du joueur " + args[2] + " avec la valeur " + args[4]));
                        } else {
                            player.sendMessage(new TextComponentString("§cCet argument n'existe pas !"));
                        }
                        break;
                    } else {
                        player.sendMessage(new TextComponentString("§cCe joueur n'a pas de personnage !"));
                        break;
                    }
                }
                break;
        }
    }

    @Override
    public List<String> getTabCompletion(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        List<String> completions = new ArrayList<String>();

        switch (args.length){
            case 2:
                if(args[0].equalsIgnoreCase("manageperso")){
                    completions.add("help");
                    completions.add("set");
                    completions.add("get");
                    completions.add("delete");
                    completions.add("info");
                }
                break;
            case 3:
                if(args[0].equalsIgnoreCase("manageperso")){
                    ArrayList<String> BDDPlayers = DBUtils.getMultipleInfos("players", "uuid");
                    ArrayList<String> onlinePlayers = new ArrayList<String>();
                    onlinePlayers.addAll(BDDPlayers.stream()
                            .map(UUID::fromString)
                            .map(server.getPlayerProfileCache()::getProfileByUUID).filter(Objects::nonNull)
                            .map(GameProfile::getName)
                            .collect(Collectors.toList()));
                    if(args[1].equalsIgnoreCase("delete")){
                        completions.addAll(onlinePlayers);
                    } else if(args[1].equalsIgnoreCase("set")){
                        completions.addAll(onlinePlayers);
                    } else if(args[1].equalsIgnoreCase("get")){
                        completions.addAll(onlinePlayers);
                    } else if(args[1].equalsIgnoreCase("info")) {
                        completions.addAll(onlinePlayers);
                    }
                }
                break;
            case 4:
                if (args[0].equalsIgnoreCase("manageperso")){
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

        return CommandBase.getListOfStringsMatchingLastWord(args, completions);
    }
}
