package fr.yan36.westerlife.common.commands;

import fr.yan36.westerlife.server.bdd.MethodesBDD;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class DelUser implements ICommand {

    @Override
    public String getName() {
        return "deluser";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "westerlife.deluser";
    }

    @Override
    public List<String> getAliases() {
        return null;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {

        if(!MethodesBDD.getPlayerExistUUID(UUID.fromString(args[0]))){
            UUID uuid;
            uuid = UUID.fromString(args[0]);

            MethodesBDD.removePlayer(uuid);
            sender.sendMessage(new TextComponentString("§cLe joueur ou la joueuse ayant l'UUID §f" + args[0] + "s'est vu supprimé son profil."));

        } else {
            sender.sendMessage(new TextComponentString("§cLe joueur ou la joueuse  ayant l'UUID §f" + args[0] + "§cn'a pas été trouvé(e)."));
        }
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
            return sender.canUseCommand(4
                    , this.getName()
            );
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        return null;
    }

    @Override
    public boolean isUsernameIndex(String[] args, int index) {
        return false;
    }

    @Override
    public int compareTo(ICommand o) {
        return 0;
    }
}
