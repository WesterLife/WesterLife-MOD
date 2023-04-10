package fr.yan36.westerlife.common.utils.commands.modules;

import fr.yan36.westerlife.common.blocks.tileentity.TileMovingGate;
import fr.yan36.westerlife.common.utils.commands.CommandModule;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.RayTraceResult;
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
            } else if(Objects.equals(args[1], "barrierelevante")) {

                if(Objects.equals(args[2], "adduser") || Objects.equals(args[2], "removeuser") || Objects.equals(args[2], "listusers")) {
                    if(sender instanceof EntityPlayer) {
                        EntityPlayer player = (EntityPlayer) sender;
                        RayTraceResult rayTraceResult = player.world.rayTraceBlocks(player.getPositionEyes(1), player.getPositionEyes(1).add(player.getLookVec().scale(10)));
                        assert rayTraceResult != null;
                        System.out.println(rayTraceResult.getBlockPos());
                        if(rayTraceResult != null) {
                            TileMovingGate tileMovingGate = (TileMovingGate) player.world.getTileEntity(rayTraceResult.getBlockPos());
                            if(tileMovingGate != null) {
                                if(Objects.equals(args[2], "adduser")) {
                                    tileMovingGate.addPlayer(args[3]);
                                    tileMovingGate.sync();
                                    sender.sendMessage(new TextComponentString("§aUser added"));
                                } else if(Objects.equals(args[2], "removeuser")) {
                                    tileMovingGate.removePlayer(args[3]);
                                    tileMovingGate.sync();
                                    sender.sendMessage(new TextComponentString("§aUser removed"));
                                }
                                if(Objects.equals(args[2], "listusers")) {
                                    sender.sendMessage(new TextComponentString("§aUsers: " + tileMovingGate.getPlayer()));
                                }
                            }
                        }

                    }
                }


            }
        } else {
            sender.sendMessage(new TextComponentString("§c/wlmod world <help/barrierelevante>"));
        }
    }
}
