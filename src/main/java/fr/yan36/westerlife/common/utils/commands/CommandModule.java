package fr.yan36.westerlife.common.utils.commands;

import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;

public abstract class CommandModule {
    public String subCommand;

    public CommandModule(String subCommand) {
        this.subCommand = subCommand;
    }

    public abstract void execute(MinecraftServer server, ICommandSender sender, String[] args);
}
