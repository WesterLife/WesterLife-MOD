package fr.gabidut76.westerlife.common.utils.commands.modules;

import fr.gabidut76.westerlife.westercore.Main;
import fr.gabidut76.westerlife.common.Util;
import fr.gabidut76.westerlife.common.network.PacketOpenAcsGui;
import fr.gabidut76.westerlife.common.utils.commands.CommandModule;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ModuleMagic extends CommandModule {
    public ModuleMagic() {
        super("magic");
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) {
        if(args[1].equals("saveKit")) {
            Main.network.sendTo(new PacketOpenAcsGui(3, args[2], ""), (EntityPlayerMP) sender);
        }
        if(args[1].equals("getKit")) {
            List<ItemStack> items = Util.stringToItemStackList(Main.wl_db.getString("kits."+args[2]));
            System.out.println(Main.wl_db.getString("kits."+args[2]));
            for (ItemStack item : items) {
                sender.sendMessage(new TextComponentString(item.getDisplayName()));
                System.out.println(item.getDisplayName());
            }
        }
        if(args[1].equals("notif")) {
            Main.network.sendTo(new PacketOpenAcsGui(4,",",""), (EntityPlayerMP) sender);
        }
        if(args[1].equals("giveKit")) {
            List<ItemStack> items = Util.stringToItemStackList(Main.wl_db.getString("kits."+args[2]));
            EntityPlayerMP target = (EntityPlayerMP) sender;
            if(args.length > 3) {
                if(server.getPlayerList().getPlayerByUsername(args[3]) == null) {
                    sender.sendMessage(new TextComponentString("Le joueur "+args[3]+" n'existe pas"));
                    return;
                }
                server.getPlayerList().getPlayerByUsername(args[3]).sendMessage(new TextComponentString(sender.getName()+" vous a donné le kit "+args[2]));
                target = server.getPlayerList().getPlayerByUsername(args[3]);
            }

            if(args.length > 4) {
                if(args[4].equals("0") || args[4].equals("false")) {
                    target.inventory.clear();
                }
            }

            for (ItemStack item : items) {
                if(target == null) {
                    sender.sendMessage(new TextComponentString("Le joueur "+args[3]+" n'existe pas"));
                    return;
                }
                target.inventory.addItemStackToInventory(item);
            }
            sender.sendMessage(new TextComponentString("Le kit "+args[2]+" a été donné à "+target.getName()));
        }





    }
    @Override
    public List<String> getTabCompletion(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        if(args.length == 2) {
            List<String> list = new ArrayList<>();
            list.add("saveKit");
            list.add("getKit");
            list.add("giveKit");
            return list;
        }
        if(args.length == 3) {

            return new ArrayList<>(Arrays.asList(Main.wl_db.getString("kits").split(";")));
        }
        if(args.length == 4) {
            return new ArrayList<>(Arrays.asList(server.getOnlinePlayerNames()));
        }
        return new ArrayList<>();
    }
}
