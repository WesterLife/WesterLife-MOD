package fr.yan36.westerlife.common.utils.commands.modules;

import fr.yan36.westerlife.Main;
import fr.yan36.westerlife.common.CommonProxy;
import fr.yan36.westerlife.common.Util;
import fr.yan36.westerlife.common.capabilities.playerstat.IPlayerStat;
import fr.yan36.westerlife.common.capabilities.playerstat.PlayerStat;
import fr.yan36.westerlife.common.capabilities.playerstat.PlayerStatCapability;
import fr.yan36.westerlife.common.network.PacketOpenAcsGui;
import fr.yan36.westerlife.common.objects.character.Character;
import fr.yan36.westerlife.common.objects.corporations.Corporation;
import fr.yan36.westerlife.common.utils.commands.CommandModule;
import fr.yan36.westerlife.server.api.NemesisAPI;
import fr.yan36.westerlife.server.api.NemesisLink;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.CompletableFuture;

public class ModuleNemesis extends CommandModule {
    public ModuleNemesis() {
        super("nemesis");
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) {
        if (args[1].equals("debug")) {
            sender.sendMessage(new TextComponentString("§c----[§4NemDEBUG§c]----"));
            sender.sendMessage(new TextComponentString("§cNemesisAPI: §4" + fr.yan36.westerlife.server.api.NemesisLink.NEMESIS_API));
            sender.sendMessage(new TextComponentString("§cNemesisURL: §4" + NemesisLink.SERVER_API_URL));
            if(sender instanceof EntityPlayerMP) sender.sendMessage(new TextComponentString("§cUserUUID :  §4" + ((EntityPlayerMP) sender).getUniqueID()));

            sender.sendMessage(new TextComponentString("§c----[§4NemDEBUG§c]----"));
        }

        if (args[1].equals("rq")) {
            try {
                String val = NemesisLink.NEMESIS_API.makeTestRequest();
                sender.sendMessage(new TextComponentString("§cRequête de test."));
                sender.sendMessage(new TextComponentString("§4" + val));
            } catch (Exception e) {
                sender.sendMessage(new TextComponentString("§cErreur étape 2."));
                sender.sendMessage(new TextComponentString("§4" + e.getMessage()));
                e.printStackTrace();
            }
        }

        if (args[1].equals("offlineuuid")) {
            NBTTagCompound nbt = new NBTTagCompound();
            nbt.setString("name", args[2]);

            EntityPlayer p = Main.proxy.loadPlayer(nbt, "", ((EntityPlayerMP) sender).world);


            System.out.println(p.getCapability(PlayerStatCapability.CAPABILITY, null).getCharacter());
        }


        if (args[1].equals("getCorpByID")) {
            Thread th = new Thread(() -> {
                Corporation c = null;
                try {
                    c = NemesisLink.NEMESIS_API.getCorporationByIDAsync(args[2]);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                if(args.length == 4) {
                    if(args[3].equals("true")) {
                        Main.network.sendTo(new PacketOpenAcsGui(8, String.join(";", c.getAsReadableList()), ""), (EntityPlayerMP) sender);
                    }
                }
                sender.sendMessage(new TextComponentString("§cCorporation: §4" + c.toJson()));
            });
            th.start();
        }

        if (args[1].equals("me")) {
            Thread th = new Thread(() -> {
                final EntityPlayer target = (EntityPlayer) sender;
                Character c = null;
                try {
                    c = NemesisLink.NEMESIS_API.getCharacterByUserUUID(target.getUniqueID());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("caca");
                Main.network.sendTo(new PacketOpenAcsGui(8, String.join(";", c.getAsReadableList()), ""), (EntityPlayerMP) sender);

            });
            th.start();
        }

        if (args[1].equals("sync")) {
            Thread thread = new Thread(() -> {
                final EntityPlayer target = (EntityPlayer) sender;

                Character c = null;
                try {
                    c = NemesisLink.NEMESIS_API.getCharacterByUserUUID(target.getUniqueID());
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

                if (target.hasCapability(PlayerStatCapability.CAPABILITY, null)) {
                    IPlayerStat stat = Objects.requireNonNull(target.getCapability(PlayerStatCapability.CAPABILITY, null));
                    stat.setCharacter(c);
                }
                PlayerStatCapability.sync(target, Collections.singletonList(target));
            });

            thread.start();
        }

        if (args[1].equals("reconnect")) {
            sender.sendMessage(new TextComponentString("§c----[§4NEMESIS§c]----"));
            sender.sendMessage(new TextComponentString("§cReconnexion à Nemesis... (si vous avez fait ça sans savoir, spoiler, vous avez surement freeze le serveur.)"));

            try {
                NemesisLink.init();
                sender.sendMessage(new TextComponentString("§cIdentifiants chargés."));
            } catch (Exception e) {
                sender.sendMessage(new TextComponentString("§cErreur étape 1."));
                sender.sendMessage(new TextComponentString("§4" + e.getMessage()));
                e.printStackTrace();
            }

            try {
                String val = NemesisLink.NEMESIS_API.makeTestRequest();
                sender.sendMessage(new TextComponentString("§cRequête de test."));
                sender.sendMessage(new TextComponentString("§4" + val));
            } catch (Exception e) {
                sender.sendMessage(new TextComponentString("§cErreur étape 2."));
                sender.sendMessage(new TextComponentString("§4" + e.getMessage()));
                e.printStackTrace();
            }

            sender.sendMessage(new TextComponentString("§c----[§4NEMESIS§c]----"));
        }


    }

    @Override
    public List<String> getTabCompletion(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        return new ArrayList<>();
    }
}
