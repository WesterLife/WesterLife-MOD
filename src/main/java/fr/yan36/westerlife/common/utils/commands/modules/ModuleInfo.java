package fr.yan36.westerlife.common.utils.commands.modules;

import fr.yan36.westerlife.Main;
import fr.yan36.westerlife.common.utils.commands.CommandModule;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class ModuleInfo extends CommandModule {
    public ModuleInfo() {
        super("info");
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) {
        sender.sendMessage(new TextComponentString("§cWesterLife Mod V" + Main.VERSION));
    }
    @Override
    public List<String> getTabCompletion(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        return new ArrayList<>();
    }
}
