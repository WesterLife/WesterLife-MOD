package fr.yan36.westerlife.common.commands;

import fr.aym.acsguis.api.ACsGuiApi;
import fr.yan36.westerlife.client.gui.phone.CSSGuiPhone;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;
import java.util.List;

public class OpenPhone implements ICommand {

    @Override
    public String getName() {
        return "openphone";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "westerlife.deluser";
    }

    @Override
    public List<String> getAliases() {
        return null;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        ACsGuiApi.asyncLoadThenShowGui("phone", CSSGuiPhone::new);
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return false;
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        return null;
    }

    @Override
    public boolean isUsernameIndex(String[] args, int index) {
        return false;
    }


    @Override
    public int compareTo(ICommand o) {
        return 0;
    }
}
