package fr.gabidut76.westerlife.common.utils.commands.modules;

import fr.gabidut76.westerlife.common.capabilities.playerstat.IPlayerStat;
import fr.gabidut76.westerlife.common.capabilities.playerstat.PlayerStatData;
import fr.gabidut76.westerlife.common.capabilities.playerstat.PlayerStatHandler;
import fr.gabidut76.westerlife.westercore.Main;
import fr.gabidut76.westerlife.common.utils.commands.CommandModule;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ModuleInfo extends CommandModule {
    public ModuleInfo() {
        super("info");
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) {
        sender.sendMessage(new TextComponentString("§cWesterLife Mod V" + Main.VERSION));
        if(Objects.equals(args[1], "capa")) {
            EntityPlayer player = (EntityPlayer) sender;
            sender.sendMessage(new TextComponentString("§aPlayerStatCapability: " + player.hasCapability(PlayerStatData.PlayerStatProvider.CAPABILITY, null)));
            IPlayerStat cap = player.getCapability(PlayerStatData.PlayerStatProvider.CAPABILITY, null);
            if(cap != null) {
                sender.sendMessage(new TextComponentString("§aPlayerStatCapability an: " + cap.getAnimation()));
                sender.sendMessage(new TextComponentString("§aPlayerStatCapability ch: " + cap.getCharacter()));

            }
        }
    }
    @Override
    public List<String> getTabCompletion(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        return new ArrayList<>();
    }
}
