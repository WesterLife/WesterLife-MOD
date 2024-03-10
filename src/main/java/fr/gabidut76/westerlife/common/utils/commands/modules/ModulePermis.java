package fr.gabidut76.westerlife.common.utils.commands.modules;

import fr.gabidut76.westerlife.common.init.ItemInit;
import fr.gabidut76.westerlife.common.objects.character.Permis;
import fr.gabidut76.westerlife.common.utils.commands.CommandModule;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;

import javax.annotation.Nullable;
import java.util.*;
import java.util.stream.Collectors;

import static net.minecraft.command.CommandBase.getListOfStringsMatchingLastWord;

public class ModulePermis extends CommandModule {
    public ModulePermis() {
        super("permis");
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) {

    }
    @Override
    public List<String> getTabCompletion(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        if(args.length == 2) {
            return getListOfStringsMatchingLastWord(args, "help", "info", "add", "delete", "givepermis", "addpoint", "removepoint");
        } else if (args.length == 3 && (Objects.equals(args[1], "info") || Objects.equals(args[1], "add"))) {
            getListOfStringsMatchingLastWord(args, server.getPlayerList().getPlayers().stream().map(EntityPlayer::getName).collect(Collectors.toList()));
        } else if (args.length == 4 && (Objects.equals(args[1], "add") || Objects.equals(args[1], "delete"))) {
            return getListOfStringsMatchingLastWord(args, Arrays.asList(Permis.PermisType.values()));
        }

        return new ArrayList<>();
    }

    public static UUID returnPlayerPermisByArgs(MinecraftServer server, String[] args, ICommandSender sender) {
        UUID finalUuid = null;
        EntityPlayer target = server.getPlayerList().getPlayerByUsername(args[2]);
        if(target == null) {
            try {
                UUID.fromString(args[2]);
                finalUuid = UUID.fromString(args[2]);
            } catch (IllegalArgumentException ignored) {
                sender.sendMessage(new TextComponentString("§cJoueur introuvable"));
            }
        } else {
            finalUuid = target.getUniqueID();
        }
        return finalUuid;
    }
}
