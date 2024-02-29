package fr.gabidut76.westerlife.common.utils.commands.modules;

import fr.gabidut76.westerlife.common.utils.commands.CommandModule;
import fr.gabidut76.westerlife.common.utils.commands.WesterLifeCommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

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
    @Override
    public List<String> getTabCompletion(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        return new ArrayList<>();
    }
}
