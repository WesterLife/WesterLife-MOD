package fr.yan36.westerlife.common.utils.commands.modules;

import fr.yan36.westerlife.common.init.ItemInit;
import fr.yan36.westerlife.common.items.ItemCard;
import fr.yan36.westerlife.common.objects.character.Permis;
import fr.yan36.westerlife.common.utils.commands.CommandModule;
import fr.yan36.westerlife.common.utils.commands.WesterLifeCommand;
import fr.yan36.westerlife.server.bdd.DBUtils;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;

import javax.annotation.Nullable;
import java.text.DateFormat;
import java.util.*;
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
            UUID finalUuid = returnPlayerPermisByArgs(server, args, sender);
            Permis permis = DBUtils.getPermis(finalUuid);
            sender.sendMessage(new TextComponentString("§cPermis de " + args[2]));
            assert permis != null;
            sender.sendMessage(new TextComponentString("§cPoints: " + permis.getPoints()));
            sender.sendMessage(new TextComponentString("§cPermis: " + permis.getType()));
            sender.sendMessage(new TextComponentString("§cDate : " + permis.getObtentionDate()));

        } else if (Objects.equals(args[1], "info") && args.length <= 3) {
            sender.sendMessage(new TextComponentString("§c/wlmod permis info <player>"));
        } else if (Objects.equals(args[1], "add")) {

            UUID finalUuid = returnPlayerPermisByArgs(server, args, sender);
            if(!DBUtils.isRowExistInDatabase("permis", "uuid", finalUuid.toString())) {
                sender.sendMessage(new TextComponentString("§cAjout du premier permis de " + args[2]));
                if(args.length == 3) {
                    sender.sendMessage(new TextComponentString("§c/wlmod permis add <player> <permis>"));
                    return;
                }
//                Permis permis = new Permis(finalUuid, Collections.singletonList(Permis.PermisType.valueOf(args[3])), "12", DateFormat.getDateInstance().format(new Date()));
//                DBUtils.saveToDB(permis);
                return;
            } else {
                Permis actualPermis = DBUtils.getPermis(finalUuid);
                assert actualPermis != null;
                List<Permis.PermisType> permisTypes = actualPermis.getType();
                if(args.length == 3) {
                    sender.sendMessage(new TextComponentString("§c/wlmod permis delete <player> <permis>"));
                    return;
                }
                if(permisTypes.contains(Permis.PermisType.valueOf(args[3]))) {
                    sender.sendMessage(new TextComponentString("§cLe joueur a déjà ce permis"));
                    return;
                }
                permisTypes.add(Permis.PermisType.valueOf(args[3]));
                actualPermis.setType(permisTypes);
                DBUtils.setInfo("permis", "uuid", finalUuid.toString(), "type", actualPermis.getType().stream().map(Permis.PermisType::toString).collect(Collectors.joining(",")));

                sender.sendMessage(new TextComponentString("§cAjout du permis " + args[3] + " à " + args[2]));
            }
        } else if (Objects.equals(args[1], "add") && args.length <= 3) {
            sender.sendMessage(new TextComponentString("§c/wlmod permis add <player>"));
        }  else if (Objects.equals(args[1], "delete")) {
            UUID finalUuid = returnPlayerPermisByArgs(server, args, sender);
            if(!DBUtils.isRowExistInDatabase("permis", "uuid", finalUuid.toString())) {
                sender.sendMessage(new TextComponentString("§cLe joueur n'a pas de permis"));
                return;
            } else {

                Permis actualPermis = DBUtils.getPermis(finalUuid);
                assert actualPermis != null;
                List<Permis.PermisType> permisTypes = actualPermis.getType();
                if(args.length == 3) {
                    sender.sendMessage(new TextComponentString("§c/wlmod permis delete <player> <permis>"));
                    return;
                }


                if(!permisTypes.contains(Permis.PermisType.valueOf(args[3]))) {
                    sender.sendMessage(new TextComponentString("§cLe joueur n'a pas ce permis"));
                    return;
                }
                permisTypes.remove(Permis.PermisType.valueOf(args[3]));
                actualPermis.setType(permisTypes);
                DBUtils.setInfo("permis", "uuid", finalUuid.toString(), "type", actualPermis.getType().stream().map(Permis.PermisType::toString).collect(Collectors.joining(",")));

                sender.sendMessage(new TextComponentString("§cRetrait du permis " + args[3] + " à " + args[2]));
            }
        } else if (Objects.equals(args[1], "givepermis")) {
            EntityPlayer player = server.getPlayerList().getPlayerByUsername(args[2]);
            if(player == null) {
                sender.sendMessage(new TextComponentString("§cLe joueur n'est pas connecté / introuvable."));
                return;
            }

            ItemStack itemStack = new ItemStack(ItemInit.PERM);
            player.inventory.addItemStackToInventory(itemStack);
            sender.sendMessage(new TextComponentString("§cVous avez donné un permis à " + args[2]));
            player.sendMessage(new TextComponentString("§aVous avez reçu votre permis"));
        } else if (Objects.equals(args[1], "addpoint")) {

        } else if (Objects.equals(args[1], "removepoint")) {

        }
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
