package fr.yan36.westerlife.common.utils.commands;

import fr.dynamx.common.entities.PhysicsEntity;
import fr.yan36.westerlife.Main;
import fr.yan36.westerlife.common.entities.DynamX.TestEntity2;
import fr.yan36.westerlife.common.utils.commands.modules.*;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.server.permission.PermissionAPI;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class WesterLifeCommand extends CommandBase {
    public static List<CommandModule> modules = new ArrayList<>();

    public static void initModules() {
        Main.logger.info("Loading modules...");

        modules.add(new ModuleWorld());
        modules.add(new ModuleAdmin());
        modules.add(new ModuleHelp());
        modules.add(new ModuleInfo());
        modules.add(new ModuleEconomy());
        modules.add(new ModuleManagePerso());
        modules.add(new ModulePermis());
    }

    @Override
    public String getName() {
        return "wlmod";
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        if(sender instanceof EntityPlayerMP)
            return PermissionAPI.hasPermission((EntityPlayer) sender, "westerlife.command.wlmod");
        return true;

    }

    @Override
    public String getUsage(ICommandSender sender) {
        return null;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if(args.length == 0) {
            for (CommandModule module : modules) {
                if(Objects.equals("help", module.subCommand)) {
                    module.execute(server, sender, args);
                }
            }
            return;
        }
        for(CommandModule module : modules) {
            if(Objects.equals(args[0], module.subCommand)) {
                module.execute(server, sender, args);
            }
        }

        if(Objects.equals(args[0], "test")) {

            PhysicsEntity<?> entity = new TestEntity2(sender.getEntityWorld());
            entity.setPosition(sender.getPosition().getX() + 0.5, sender.getPosition().getY(), sender.getPosition().getZ());

            sender.getEntityWorld().spawnEntity(entity);
        }
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        List<String> list = new ArrayList<>();
        modules.forEach(module -> list.add(module.subCommand));
        if(args.length == 1) {
            return getListOfStringsMatchingLastWord(args, list);
        } else {
            for(CommandModule module : modules) {
                if(Objects.equals(args[0], module.subCommand)) {
                    return module.getTabCompletion(server, sender, args, targetPos);
                }
            }
        }
        return super.getTabCompletions(server, sender, args, targetPos);
    }

    public void registerModule(CommandModule module) {
        modules.add(module);
    }

}
