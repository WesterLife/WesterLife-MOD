package fr.yan36.westerlife.common.utils.commands.modules;

import fr.yan36.westerlife.Main;
import fr.yan36.westerlife.common.network.old.PacketOpenGUIAdmin;
import fr.yan36.westerlife.common.utils.commands.CommandModule;
import fr.yan36.westerlife.common.utils.commands.WesterLifeCommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;

public class ModuleAdmin extends CommandModule {
    public ModuleAdmin() {
        super("admin");
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) {
        sender.sendMessage(new TextComponentString("§cVoici l'interface d'administration."));
        Main.network.sendTo(new PacketOpenGUIAdmin(), (EntityPlayerMP) sender);
    }
}
