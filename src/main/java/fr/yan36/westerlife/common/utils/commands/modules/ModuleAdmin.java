package fr.yan36.westerlife.common.utils.commands.modules;

import fr.dynamx.common.entities.BaseVehicleEntity;
import fr.yan36.westerlife.Main;
import fr.yan36.westerlife.common.capabilities.playergarage.PlayerGarage;
import fr.yan36.westerlife.common.capabilities.playergarage.PlayerGarageCapability;
import fr.yan36.westerlife.common.capabilities.playerinventory.ExtraItemCapability;
import fr.yan36.westerlife.common.network.old.PacketOpenGUIAdmin;
import fr.yan36.westerlife.common.utils.commands.CommandModule;
import fr.yan36.westerlife.common.utils.commands.WesterLifeCommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class ModuleAdmin extends CommandModule {
    public ModuleAdmin() {
        super("admin");
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) {
//        sender.sendMessage(new TextComponentString("§cVoici l'interface d'administration."));
        Main.network.sendTo(new PacketOpenGUIAdmin(), (EntityPlayerMP) sender);
        if (Objects.equals(args[1], "garage")) {

            if (Objects.equals(args[2], "clear")) {
                EntityPlayerMP player = (EntityPlayerMP) sender;
                assert PlayerGarageCapability.CAPABILITY != null;
                if (player.hasCapability(PlayerGarageCapability.CAPABILITY, null)) {
                    PlayerGarage garage = (PlayerGarage) player.getCapability(PlayerGarageCapability.CAPABILITY, null);
                    garage.setCars(new ArrayList<>());
                    PlayerGarageCapability.sync(player, Collections.singletonList(player));
                    sender.sendMessage(new TextComponentString("§cCleared."));
                }
            }
            if (Objects.equals(args[2], "rd")) {
                EntityPlayerMP player = (EntityPlayerMP) sender;
                AxisAlignedBB aabb = new AxisAlignedBB(player.getPosition()).grow(2);
                List<BaseVehicleEntity> cars = player.world.getEntitiesWithinAABB(BaseVehicleEntity.class, aabb);
                for (BaseVehicleEntity car : cars) {
                    sender.sendMessage(new TextComponentString("§a> " + car.serializeNBT()));
                    System.out.println(car.serializeNBT());
                }
            }

            if (Objects.equals(args[2], "sync")) {
                EntityPlayerMP player = (EntityPlayerMP) sender;
                assert PlayerGarageCapability.CAPABILITY != null;
                if (player.hasCapability(PlayerGarageCapability.CAPABILITY, null)) {
                    PlayerGarageCapability.sync(player, Collections.singletonList(player));
                } else {
                    sender.sendMessage(new TextComponentString("§cVous n'avez pas de garage."));
                }
            }
            EntityPlayerMP player = (EntityPlayerMP) sender;
            if (player.hasCapability(PlayerGarageCapability.CAPABILITY, null)) {
                PlayerGarage garage = (PlayerGarage) player.getCapability(PlayerGarageCapability.CAPABILITY, null);
                sender.sendMessage(new TextComponentString("§cVoici votre garage :"));
                for (int i = 0; i < garage.getCars().size(); i++) {
                    sender.sendMessage(new TextComponentString("§c" + i + " : " + garage.getCars().get(i).getCarPlate()));
                }

            }
        }
        if (Objects.equals(args[1], "itemdbg")) {
            if (Objects.equals(args[2], "add")) {
                ItemStack item = ((EntityPlayerMP) sender).getHeldItemMainhand();
                EntityPlayerMP player = (EntityPlayerMP) sender;
                assert ExtraItemCapability.CAPABILITY != null;
                if (player.hasCapability(ExtraItemCapability.CAPABILITY, null)) {
                    player.getCapability(ExtraItemCapability.CAPABILITY, null).setStackInSlot(Integer.parseInt(args[3]), item);
                }
                player.inventory.removeStackFromSlot(player.inventory.currentItem);
                player.sendMessage(new TextComponentString("Vous avez transféré l'item de votre main dans le slot " + args[3] + "."));
            }
            if (args[2].equals("restore")) {
                EntityPlayerMP player = (EntityPlayerMP) sender;
                assert ExtraItemCapability.CAPABILITY != null;
                if (player.hasCapability(ExtraItemCapability.CAPABILITY, null)) {
                    player.inventory.setInventorySlotContents(player.inventory.currentItem, player.getCapability(ExtraItemCapability.CAPABILITY, null).getStackInSlot(Integer.parseInt(args[3])));

                    player.inventoryContainer.detectAndSendChanges();
                    player.getCapability(ExtraItemCapability.CAPABILITY, null).setStackInSlot(Integer.parseInt(args[3]), ItemStack.EMPTY);
                }
                player.sendMessage(new TextComponentString("Vous avez transféré l'item du slot " + args[3] + " dans votre main."));
            }
            if (args[2].equals("list")) {
                EntityPlayerMP player = (EntityPlayerMP) sender;
                assert ExtraItemCapability.CAPABILITY != null;
                if (player.hasCapability(ExtraItemCapability.CAPABILITY, null)) {
                    for (int i = 0; i < player.getCapability(ExtraItemCapability.CAPABILITY, null).getSlots(); i++) {
                        player.sendMessage(new TextComponentString("Slot " + i + " : " + player.getCapability(ExtraItemCapability.CAPABILITY, null).getStackInSlot(i).getDisplayName()));
                    }
                }
            }
        }
    }

    @Override
    public List<String> getTabCompletion(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        if (args.length == 2) {
            List<String> list = new ArrayList<>();
            list.add("itemdbg");
            return list;
        }
        if (args.length == 3 && Objects.equals(args[1], "itemdbg")) {
            List<String> list = new ArrayList<>();
            list.add("add");
            list.add("restore");
            list.add("list");
            return list;
        }
        return new ArrayList<>();
    }
}
