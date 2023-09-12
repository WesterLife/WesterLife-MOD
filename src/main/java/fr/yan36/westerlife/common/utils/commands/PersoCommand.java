package fr.yan36.westerlife.common.utils.commands;

import fr.yan36.westerlife.Main;
import fr.yan36.westerlife.common.network.PacketOpenMcefGui;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class PersoCommand extends CommandBase {

    @Override
    public String getName() {
        return "char";
    }


    @Override
    public String getUsage(ICommandSender sender) {
        return null;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if(sender instanceof EntityPlayerMP) {
            EntityPlayerMP player = (EntityPlayerMP) sender;
            if(args.length == 0) {
                Main.network.sendTo(new PacketOpenMcefGui("perso"), player);
                return;
            } else {
                if(player.isCreative()) {
                    Main.network.sendTo(new PacketOpenMcefGui("perso"), server.getPlayerList().getPlayerByUsername(args[0]));
                }
            }

        }
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        if(sender instanceof EntityPlayerMP) {
            EntityPlayerMP player = (EntityPlayerMP) sender;
            if(args.length == 1 && player.isCreative()) {
                List<String> list = new ArrayList<>();
                for (EntityPlayerMP playerMP : server.getPlayerList().getPlayers()) {
                    list.add(playerMP.getName());
                }
                return list;
            }
        }
        return super.getTabCompletions(server, sender, args, targetPos);
    }
}
