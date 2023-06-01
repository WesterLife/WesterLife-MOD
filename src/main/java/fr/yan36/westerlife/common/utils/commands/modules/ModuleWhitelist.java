package fr.yan36.westerlife.common.utils.commands.modules;

import com.mojang.authlib.GameProfile;
import fr.yan36.westerlife.common.utils.commands.CommandModule;
import fr.yan36.westerlife.server.bdd.DBUtils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

public class ModuleWhitelist extends CommandModule {

    public ModuleWhitelist() {
        super("whitelist");
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) {
        if(args.length > 1) {
            EntityPlayer player = (EntityPlayer) sender;
            ArrayList<String> whitelists = DBUtils.getMultipleInfos("whitelists", "pseudo");
            switch (args[1]) {
                case "add":
                    if (args.length == 4) {
                        if (DBUtils.getWhitelistExist(args[3])) {
                            player.sendMessage(new TextComponentString("§cCe joueur est déjà whitelist !"));
                            break;
                        } else {
                            DBUtils.createWhitelist(args[3], args[2]);
                            player.sendMessage(new TextComponentString("§a" + args[3] + " a été ajouté à la whitelist !"));
                            break;
                        }
                    } else {
                        player.sendMessage(new TextComponentString("§c/wlmod whitelist add <player/staff> <pseudo>"));
                        break;
                    }
                case "delete":
                    if (args.length == 3) {
                        if (DBUtils.getWhitelistExist(args[2])) {
                            DBUtils.removeRow("whitelists", "pseudo", args[2]);
                            player.sendMessage(new TextComponentString("§a" + args[2] + " a été retiré de la whitelist !"));
                            EntityPlayerMP playerMP = Objects.requireNonNull(server.getPlayerList().getPlayerByUsername(args[2]));
                            if(playerMP != null) {
                                playerMP.connection.disconnect(new TextComponentString("§cVous avez été retiré de la whitelist !"));
                            }
                            break;
                        } else {
                            player.sendMessage(new TextComponentString("§cCe joueur n'est pas whitelist !"));
                            break;
                        }
                    } else {
                        player.sendMessage(new TextComponentString("§c/wlmod whitelist delete <pseudo>"));
                        break;
                    }
                case "check":
                    if (args.length == 3) {
                        if (DBUtils.getWhitelistExist(args[2])) {
                            player.sendMessage(new TextComponentString("§aCe joueur est whitelist !"));
                            break;
                        } else {
                            player.sendMessage(new TextComponentString("§cCe joueur n'est pas whitelist !"));
                            break;
                        }
                    } else {
                        player.sendMessage(new TextComponentString("§c/wlmod whitelist check <pseudo>"));
                        break;
                    }
                default:
                    player.sendMessage(new TextComponentString("§c/wlmod whitelist <§aadd§c/delete/check> §a(type) §c<pseudo>"));
                    break;
            }
        }
    }

    @Override
    public List<String> getTabCompletion(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        List<String> completions = new ArrayList<String>();

        switch (args.length){
            case 2:
                if(args[0].equalsIgnoreCase("whitelist")){
                    completions.add("help");
                    completions.add("add");
                    completions.add("delete");
                    completions.add("check");
                }
                break;
            case 3:
                if(args[0].equalsIgnoreCase("whitelist")){
                    if(args[1].equalsIgnoreCase("add")){
                        completions.add("player");
                        completions.add("staff");
                    }
                }
                break;
            case 4:
                if (args[0].equalsIgnoreCase("whitelist")){
                }
                break;
        }

        return CommandBase.getListOfStringsMatchingLastWord(args, completions);
    }
}
