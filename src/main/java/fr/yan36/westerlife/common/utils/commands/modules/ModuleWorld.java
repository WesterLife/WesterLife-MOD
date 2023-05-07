package fr.yan36.westerlife.common.utils.commands.modules;

import fr.yan36.westerlife.common.blocks.tileentity.TEDigicode;
import fr.yan36.westerlife.common.blocks.tileentity.TileMovingGate;
import fr.yan36.westerlife.common.blocks.tileentity.TileRadarFixe;
import fr.yan36.westerlife.common.utils.commands.CommandModule;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.WorldServer;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
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
            } else if(Objects.equals(args[1], "radar")) {


            } else if (Objects.equals(args[1], "digicode")) {
                //Change the code of a digicode
                if(Objects.equals(args[2], "setcode") || Objects.equals(args[2], "getcode")) {
                    if(sender instanceof EntityPlayer) {
                        EntityPlayer player = (EntityPlayer) sender;
                        RayTraceResult rayTraceResult = player.world.rayTraceBlocks(player.getPositionEyes(1), player.getPositionEyes(1).add(player.getLookVec().scale(10)));
                        assert rayTraceResult != null;
                        System.out.println(rayTraceResult.getBlockPos());
                        if(rayTraceResult != null) {
                            TEDigicode teDigicode = (TEDigicode) player.world.getTileEntity(rayTraceResult.getBlockPos());
                            if(teDigicode != null) {
                                if(Objects.equals(args[2], "setcode")) {
                                    teDigicode.setCode(args[3]);
                                    teDigicode.sync();
                                    sender.sendMessage(new TextComponentString("§aCode changed"));
                                } else if(Objects.equals(args[2], "getcode")) {
                                    sender.sendMessage(new TextComponentString("§aCode: " + teDigicode.getCode()));
                                }
                            }
                        }

                    }
                }
                if(Objects.equals(args[2], "setspeed")) {
                    if(sender instanceof EntityPlayer) {
                        EntityPlayer player = (EntityPlayer) sender;
                        RayTraceResult rayTraceResult = player.world.rayTraceBlocks(player.getPositionEyes(1), player.getPositionEyes(1).add(player.getLookVec().scale(10)));
                        assert rayTraceResult != null;
                        System.out.println(rayTraceResult.getBlockPos());
                        if(rayTraceResult != null) {
                            TileRadarFixe tileMovingGate = (TileRadarFixe) player.world.getTileEntity(rayTraceResult.getBlockPos());
                            if(tileMovingGate != null) {
                                if(Objects.equals(args[2], "setspeed")) {
                                    tileMovingGate.setSpeed(Integer.parseInt(args[3]));
                                    tileMovingGate.sync();
                                    sender.sendMessage(new TextComponentString("§aSet speed to " + args[3] + " km/h"));
                                }
                            }
                        }
                    } else {
                        sender.sendMessage(new TextComponentString("§c/wlmod world radar <setspeed>"));
                    }
                }
            }
        } else {
            sender.sendMessage(new TextComponentString("§c/wlmod world <help/barrierelevante>"));
        }
    }
    @Override
    public List<String> getTabCompletion(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        return new ArrayList<>();
    }
}
