package fr.yan36.westerlife.common.utils.commands.modules;

import fr.yan36.westerlife.common.objects.character.Permis;
import fr.yan36.westerlife.common.utils.commands.CommandModule;
import fr.yan36.westerlife.common.utils.commands.WesterLifeCommand;
import fr.yan36.westerlife.server.bdd.DBUtils;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import static net.minecraft.command.CommandBase.getListOfStringsMatchingLastWord;

public class ModulePermis extends CommandModule {
    public ModulePermis() {
        super("permis");
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) {
        if(Objects.equals(args[1], "help")) {
            sender.sendMessage(new TextComponentString("§c/permis help"));
            sender.sendMessage(new TextComponentString("§c/permis info <player>"));
            sender.sendMessage(new TextComponentString("§c/permis add"));
            sender.sendMessage(new TextComponentString("§c/permis delete"));
            sender.sendMessage(new TextComponentString("§c/permis givepermis"));
            sender.sendMessage(new TextComponentString("§c/permis addpoint"));
            sender.sendMessage(new TextComponentString("§c/permis removepoint"));
        } else if (Objects.equals(args[1], "info") && args.length == 3) {
            UUID finalUuid = null;

            // if

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


            System.out.println(finalUuid);





            Permis permis = DBUtils.getPermis(finalUuid);
            sender.sendMessage(new TextComponentString("§cPermis de " + args[2]));
            assert permis != null;
            sender.sendMessage(new TextComponentString("§cPoints: " + permis.getPoints()));
            sender.sendMessage(new TextComponentString("§cPermis: " + permis.getType()));
            sender.sendMessage(new TextComponentString("§cDate : " + permis.getObtentionDate()));

        } else if (Objects.equals(args[1], "info") && args.length <= 3) {
            sender.sendMessage(new TextComponentString("§c/permis info <player>"));
        } else if (Objects.equals(args[1], "add")) {

        } else if (Objects.equals(args[1], "delete")) {

        } else if (Objects.equals(args[1], "givepermis")) {

        } else if (Objects.equals(args[1], "addpoint")) {

        } else if (Objects.equals(args[1], "removepoint")) {

        }
    }
    @Override
    public List<String> getTabCompletion(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        if(args.length == 2) {
            return getListOfStringsMatchingLastWord(args, "help", "info", "add", "delete", "givepermis", "addpoint", "removepoint");
        } else if (args.length == 3 && Objects.equals(args[2], "info")) {
            getListOfStringsMatchingLastWord(args, server.getPlayerList().getPlayers().stream().map(EntityPlayer::getName).toArray(String[]::new));
        }

        return new ArrayList<>();
    }
}
