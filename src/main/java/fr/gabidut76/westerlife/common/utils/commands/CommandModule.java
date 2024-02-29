package fr.gabidut76.westerlife.common.utils.commands;

import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;
import java.util.List;

public abstract class CommandModule {
    public String subCommand;

    public CommandModule(String subCommand) {
        this.subCommand = subCommand;
    }

    public abstract void execute(MinecraftServer server, ICommandSender sender, String[] args);

    public abstract List<String> getTabCompletion(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos);
}
