package fr.yan36.westerlife.common.utils.commands.modules;

import fr.yan36.westerlife.common.utils.commands.CommandModule;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.WorldServer;

import java.util.Objects;

public class ModuleWorld extends CommandModule {

    public ModuleWorld() {
        super("world");
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) {
        if(args.length > 1) {
            if(Objects.equals(args[1], "help")) {
                sender.sendMessage(new TextComponentString("§c/wlmod world <help/list>"));
            } else if(Objects.equals(args[1], "list")) {
                sender.sendMessage(new TextComponentString("§cVoici la liste des mondes:"));
                for(WorldServer world : server.worlds) {
                    sender.sendMessage(new TextComponentString("§c- " + world.getWorldInfo().getWorldName()));
                }
            }
        } else {
            sender.sendMessage(new TextComponentString("§c/wlmod world <help/barrierelevante>"));
        }
    }
}
