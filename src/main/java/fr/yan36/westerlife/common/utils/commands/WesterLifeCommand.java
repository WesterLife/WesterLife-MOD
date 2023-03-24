package fr.yan36.westerlife.common.utils.commands;

import fr.yan36.westerlife.Main;
import fr.yan36.westerlife.common.network.PacketOpenGUIAdmin;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.server.permission.PermissionAPI;

import java.util.Objects;

public class WesterLifeCommand extends CommandBase {
    @Override
    public String getName() {
        return "wlmod";
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        if(sender instanceof EntityPlayerMP)
            return PermissionAPI.hasPermission((EntityPlayer) sender, "westerlife.command.wlmod");
        return true;

    }

    @Override
    public String getUsage(ICommandSender sender) {
        return null;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {

        if(args.length > 0 && sender instanceof EntityPlayer) {
            if(Objects.equals(args[0], "help")){
                sender.sendMessage(new TextComponentString("§c/wlmod <admin/profile/help/info>"));
            } else if(Objects.equals(args[0], "info")){
                sender.sendMessage(new TextComponentString("§cWesterLife Mod V" + Main.VERSION));
            } else if(Objects.equals(args[0], "admin")){
                sender.sendMessage(new TextComponentString("§cVoici l'interface d'administration."));
                Main.network.sendTo(new PacketOpenGUIAdmin(), (EntityPlayerMP) sender);
            } else if(Objects.equals(args[0], "profile")){
                if(!args[1].isEmpty()){
                    sender.sendMessage(new TextComponentString("§c/wlmod profile <pseudo>"));
                }
            } else if(Objects.equals(args[0], "addwarp")){

            }
        } else {
            sender.sendMessage(new TextComponentString("§c/wlmod <admin/profile/help/info>"));
        }

    }

}
