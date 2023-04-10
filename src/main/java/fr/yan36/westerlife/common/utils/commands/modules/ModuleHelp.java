package fr.yan36.westerlife.common.utils.commands.modules;

import fr.yan36.westerlife.common.utils.commands.CommandModule;
import fr.yan36.westerlife.common.utils.commands.WesterLifeCommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;

public class ModuleHelp extends CommandModule {
    public ModuleHelp() {
        super("help");
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) {
        StringBuilder sb = new StringBuilder();
        WesterLifeCommand.modules.forEach(module -> sb.append(module.subCommand).append("/"));
        sender.sendMessage(new TextComponentString("§c/wlmod <" + sb + ">"));
    }
}
