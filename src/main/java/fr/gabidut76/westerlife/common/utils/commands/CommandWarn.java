package fr.gabidut76.westerlife.common.utils.commands;

import fr.gabidut76.westerlife.common.network.PacketOpenMcefGui;
import fr.gabidut76.westerlife.common.network.PacketSendNotif;
import fr.gabidut76.westerlife.common.objects.Notification;
import fr.gabidut76.westerlife.westercore.Main;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class CommandWarn extends CommandBase {

    @Override
    public String getName() {
        return "warn";
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
                sender.sendMessage(new TextComponentString("§cUsage : /warn <player> <reason>"));
                return;
            } else {
                EntityPlayerMP target = server.getPlayerList().getPlayerByUsername(args[0]);
                if(target != null) {
                    if(args.length == 1) {
                        sender.sendMessage(new TextComponentString("§cUsage : /warn <player> <reason>"));
                        return;
                    }
                    StringBuilder reason = new StringBuilder();
                    for (int i = 1; i < args.length; i++) {
                        reason.append(args[i]).append(" ");
                    }

                    Main.network.sendTo(new PacketSendNotif(new Notification("AVERTISSEMENT", "Vous reçu un avertissement de : " + sender.getName() + " pour la raison : " + reason, 0xff0000, System.currentTimeMillis())), player);

                } else {
                    sender.sendMessage(new TextComponentString("§cPlayer not found."));
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
