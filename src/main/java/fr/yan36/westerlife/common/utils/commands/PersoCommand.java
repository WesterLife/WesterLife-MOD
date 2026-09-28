package fr.yan36.westerlife.common.utils.commands;

import fr.dynamx.common.entities.PhysicsEntity;
import fr.yan36.westerlife.Main;
import fr.yan36.westerlife.common.entities.DynamX.TestEntity2;
import fr.yan36.westerlife.common.network.PacketOpenMcefGui;
import fr.yan36.westerlife.common.utils.commands.modules.*;
import fr.yan36.westerlife.server.bdd.DBUtils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.server.permission.PermissionAPI;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PersoCommand extends CommandBase {

    @Override
    public String getName() {
        return "char";
    }


    @Override
    public List<String> getAliases() {
        return java.util.Arrays.asList("perso", "personnage", "characters");
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/char [menu|list|select|create|info]";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (sender instanceof EntityPlayerMP) {
            EntityPlayerMP player = (EntityPlayerMP) sender;
            List<fr.yan36.westerlife.common.objects.character.Character> chars = DBUtils.getCharactersByAccount(player.getUniqueID());

            if (args.length == 0 || args[0].equalsIgnoreCase("menu")) {
                if (chars.isEmpty()) {
                    Main.network.sendTo(new fr.yan36.westerlife.common.network.PacketAskToCreateCharacter(), player);
                } else {
                    Main.network.sendTo(new fr.yan36.westerlife.common.network.PacketCharacterList(chars), player);
                }
                return;
            }

            if (args[0].equalsIgnoreCase("list")) {
                fr.yan36.westerlife.common.objects.character.Character active = fr.yan36.westerlife.server.character.PlayerCharacterManager.getActiveCharacter(player);
                player.sendMessage(new TextComponentString("§6========== Vos personnages RP =========="));
                if (chars.isEmpty()) {
                    player.sendMessage(new TextComponentString("§cVous n'avez aucun personnage. Utilisez §e/char create"));
                } else {
                    for (int i = 0; i < chars.size(); i++) {
                        fr.yan36.westerlife.common.objects.character.Character c = chars.get(i);
                        boolean isActive = (active != null && active.getUuid().equals(c.getUuid()));
                        String status = isActive ? " §a[ACTIF]" : "";
                        player.sendMessage(new TextComponentString("§e" + (i + 1) + ". §f" + c.getFullName() + status + " §7(" + c.getBirthDate() + " - " + c.getBirthPlace() + ")"));
                    }
                    player.sendMessage(new TextComponentString("§7Pour changer : §b/char select <numéro ou nom>"));
                }
                player.sendMessage(new TextComponentString("§6========================================"));
                return;
            }

            if (args[0].equalsIgnoreCase("create")) {
                Main.network.sendTo(new fr.yan36.westerlife.common.network.PacketAskToCreateCharacter(), player);
                return;
            }

            if (args[0].equalsIgnoreCase("info")) {
                fr.yan36.westerlife.common.objects.character.Character active = fr.yan36.westerlife.server.character.PlayerCharacterManager.getActiveCharacter(player);
                if (active == null) {
                    player.sendMessage(new TextComponentString("§cVous n'avez aucun personnage actif. Utilisez §e/char create"));
                    return;
                }
                player.sendMessage(new TextComponentString("§6========== Personnage Actif =========="));
                player.sendMessage(new TextComponentString("§9Nom complet §7: §e" + active.getFullName()));
                player.sendMessage(new TextComponentString("§9Date de naissance §7: §e" + active.getBirthDate()));
                player.sendMessage(new TextComponentString("§9Lieu de naissance §7: §e" + active.getBirthPlace()));
                player.sendMessage(new TextComponentString("§9Nationalité §7: §e" + active.getNationality()));
                player.sendMessage(new TextComponentString("§9Sexe §7: §e" + active.getGender().getSex()));
                player.sendMessage(new TextComponentString("§9UUID Personnage §7: §7" + active.getUuid()));
                player.sendMessage(new TextComponentString("§6======================================"));
                return;
            }

            if (args[0].equalsIgnoreCase("select")) {
                if (args.length < 2) {
                    player.sendMessage(new TextComponentString("§cUsage : §e/char select <numéro ou nom>"));
                    return;
                }

                String query = args[1];
                fr.yan36.westerlife.common.objects.character.Character chosen = null;

                // Try by number index (1-based)
                try {
                    int idx = Integer.parseInt(query) - 1;
                    if (idx >= 0 && idx < chars.size()) {
                        chosen = chars.get(idx);
                    }
                } catch (NumberFormatException ignored) {}

                // Try by name match
                if (chosen == null) {
                    for (fr.yan36.westerlife.common.objects.character.Character c : chars) {
                        if (c.getFullName().toLowerCase().contains(query.toLowerCase()) ||
                            c.getLastName().equalsIgnoreCase(query) ||
                            c.getFirstNames().equalsIgnoreCase(query)) {
                            chosen = c;
                            break;
                        }
                    }
                }

                if (chosen == null) {
                    player.sendMessage(new TextComponentString("§cAucun personnage trouvé correspondant à §e" + query));
                    return;
                }

                fr.yan36.westerlife.server.character.PlayerCharacterManager.selectCharacter(player, chosen.getUuid());
                return;
            }

            if (player.isCreative()) {
                EntityPlayerMP target = server.getPlayerList().getPlayerByUsername(args[0]);
                if (target != null) {
                    List<fr.yan36.westerlife.common.objects.character.Character> targetChars = DBUtils.getCharactersByAccount(target.getUniqueID());
                    Main.network.sendTo(new fr.yan36.westerlife.common.network.PacketCharacterList(targetChars), target);
                }
            }
        }
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        if (args.length == 1) {
            return getListOfStringsMatchingLastWord(args, "menu", "list", "select", "create", "info");
        }
        if (args.length == 2 && args[0].equalsIgnoreCase("select") && sender instanceof EntityPlayerMP) {
            EntityPlayerMP player = (EntityPlayerMP) sender;
            List<fr.yan36.westerlife.common.objects.character.Character> chars = DBUtils.getCharactersByAccount(player.getUniqueID());
            List<String> suggestions = new ArrayList<>();
            for (int i = 0; i < chars.size(); i++) {
                suggestions.add(String.valueOf(i + 1));
                suggestions.add(chars.get(i).getLastName());
                suggestions.add(chars.get(i).getFirstNames());
            }
            return getListOfStringsMatchingLastWord(args, suggestions);
        }
        return super.getTabCompletions(server, sender, args, targetPos);
    }
}
