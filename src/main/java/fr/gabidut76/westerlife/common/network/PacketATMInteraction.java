package fr.gabidut76.westerlife.common.network;

import fr.aym.acslib.utils.packetserializer.SerializablePacket;
import fr.dynamx.api.network.EnumNetworkType;
import fr.dynamx.api.network.IDnxPacket;
import fr.gabidut76.westerlife.common.objects.Notification;
import fr.gabidut76.westerlife.westercore.Main;
import fr.gabidut76.westerlife.common.init.ItemInit;
import fr.gabidut76.westerlife.common.objects.character.Character;
import fr.gabidut76.westerlife.common.objects.economy.BankAccount;
import fr.gabidut76.westerlife.westerapi.api.NemesisLink;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class PacketATMInteraction extends SerializablePacket implements IDnxPacket {

    String interaction;


    public PacketATMInteraction() {
    }


    public PacketATMInteraction(String interaction, List<String> args) {
        super(args);
        this.interaction = interaction;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        super.fromBytes(buf);
        this.interaction = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        super.toBytes(buf);
        ByteBufUtils.writeUTF8String(buf, this.interaction);
    }

    @Override
    public EnumNetworkType getPreferredNetwork() {
        return EnumNetworkType.DYNAMX_UDP;
    }

    @Override
    public void handleUDPReceive(EntityPlayer context, Side side) {
        if (side.isServer()) {
            List<String> args2 = (List<String>) this.getObjectsIn()[0];
            System.out.println(interaction);
            if (Objects.equals(interaction, "givecard")) {
                EntityPlayerMP player = (EntityPlayerMP) context;
                boolean isOp = player.canUseCommand(4, "/op");
                if(isOp) {
                    EntityPlayer target = player.getServer().getPlayerList().getPlayerByUUID(UUID.fromString(args2.get(1)));
                    ItemStack card = new ItemStack(ItemInit.CARTE_BANCAIRE);

                    card.setTagCompound(new NBTTagCompound());
                    card.getTagCompound().setString("relatedBankAccount", args2.get(0));
                    card.setStackDisplayName("§c§lCarte bancaire de " + args2.get(0));
                    target.inventory.addItemStackToInventory(card);
                    target.onUpdate();

                }

            }
            if (Objects.equals(interaction, "login")) {
                System.out.println("Login attempt");
                System.out.println(this.getObjectsIn());

                if (context.canUseCommand(4, "/op")) {
                    context.sendMessage(new TextComponentString("§a§l[ATM DEBUG] §r§cVous avez saisi le code: " + args2.get(0) + " pour le compte " + args2.get(1) + "."));
                }

                try {
                    BankAccount account = NemesisLink.NEMESIS_API.getBankAccountAny(args2.get(1));
                    if (account != null) {
                        if (Objects.equals(account.getAccountpassword(), args2.get(0))) {
                            if (account.getType().equals(BankAccount.BankAccountType.PERSONAL)) {
                                Character character = NemesisLink.NEMESIS_API.getCharacterByUserUUID(UUID.fromString(account.getOwner()));
                                Main.network.sendTo(new PacketOpenAcsGui(11, args2.get(1), args2.get(0) + "$" + character.toString()), (EntityPlayerMP) context);
                            } else {
                                Main.network.sendTo(new PacketOpenAcsGui(11, args2.get(1), args2.get(0) + "$corp"), (EntityPlayerMP) context);
                            }

                        } else {
                            context.sendMessage(new TextComponentString("§c§l[ATM] §r§cLe code est incorrect."));
                            Main.network.sendTo(new PacketOpenAcsGui(0, "", ""), (EntityPlayerMP) context);
                        }
                    } else {
                        context.sendMessage(new TextComponentString("§c§l[ATM] §r§cLe compte associé à la carte n'est pas accessible."));
                        Main.network.sendTo(new PacketOpenAcsGui(0, "", ""), (EntityPlayerMP) context);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            } else if (Objects.equals(interaction, "transfer")) {
                try {
                    BankAccount from = NemesisLink.NEMESIS_API.getBankAccountAny(args2.get(0));
                    BankAccount to = NemesisLink.NEMESIS_API.getBankAccountAny(args2.get(2));

                    if(from == null || to == null) {
                        Main.network.sendTo(new PacketSendNotif(new Notification("Erreur", "Le compte destinataire n'existe pas.", 0xFF0000, System.currentTimeMillis())), (EntityPlayerMP) context);
                        Main.network.sendTo(new PacketOpenAcsGui(0, "", ""), (EntityPlayerMP) context);
                        return;
                    }

                    if(from.getAccountpassword().equals(args2.get(1))) {
                        NemesisLink.NEMESIS_API.makeTransaction(from.getRib(), to.getRib(), args2.get(3));
                        if(from.getType().equals(BankAccount.BankAccountType.PERSONAL)) {
                            System.out.println(NemesisLink.NEMESIS_API.getCharacterByUserUUID(UUID.fromString(from.getOwner())));
                            Main.network.sendTo(new PacketOpenAcsGui(12, from.getRib(), args2.get(1) + "$" + NemesisLink.NEMESIS_API.getCharacterByUserUUID(UUID.fromString(from.getOwner()))), (EntityPlayerMP) context);
                        } else {
                            Main.network.sendTo(new PacketOpenAcsGui(12, from.getRib(), args2.get(1) + "$corp"), (EntityPlayerMP) context);

                        }
                    }
                } catch (IOException e) {
                    context.sendMessage(new TextComponentString("§c§l[ATM] §r§cLe compte destinataire n'existe pas."));
                    throw new RuntimeException(e);
                }

            } else if (Objects.equals(interaction, "changePassword")) {
                try {
                    BankAccount from = NemesisLink.NEMESIS_API.getBankAccountAny(args2.get(0));

                    if(from.getAccountpassword().equals(args2.get(1))) {
                        NemesisLink.NEMESIS_API.changePassword(from.getRib(), args2.get(2));
                        context.sendMessage(new TextComponentString("§a§l[ATM] §r§aLe mot de passe a été changé."));
                        Main.network.sendTo(new PacketOpenAcsGui(0, "", ""), (EntityPlayerMP) context);
                    }
                } catch (IOException e) {
                    context.sendMessage(new TextComponentString("§c§l[ATM] §r§cUne erreur est survenue."));
                    throw new RuntimeException(e);
                }

            } else if (Objects.equals(interaction, "exchange")) {


                if (args2.get(3).equals("remove")) {
                    BankAccount account = null;
                    try {
                        account = NemesisLink.NEMESIS_API.getBankAccountAny(args2.get(0));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    if (account != null) {
                        System.out.println("Account found");
                        System.out.println(args2);
                        if (Objects.equals(account.getAccountpassword(), args2.get(1))) {
                            System.out.println("Adding money");
                            switch (args2.get(2)) {
                                case "5":
                                    if (Float.parseFloat(account.getMoney()) > 5.0f) {
                                        try {
                                            NemesisLink.NEMESIS_API.makeTransaction(args2.get(0), "void", "5");
                                            context.inventory.addItemStackToInventory(new ItemStack(ItemInit.CINQ_EUROS));
                                        } catch (IOException e) {
                                            context.sendMessage(new TextComponentString("§c§l[ATM] §r§cUne erreur est survenue."));
                                            throw new RuntimeException(e);
                                        }

                                    } else {
                                        context.sendMessage(new TextComponentString("§c§l[ATM] §r§cVous n'avez pas assez d'argent sur votre compte."));
                                        Main.network.sendTo(new PacketOpenAcsGui(0, "", ""), (EntityPlayerMP) context);
                                    }
                                    return;
                                case "10":
                                    if (Float.parseFloat(account.getMoney()) > 10.0f) {
                                        try {
                                            NemesisLink.NEMESIS_API.makeTransaction(args2.get(0), "void", "10");
                                            context.inventory.addItemStackToInventory(new ItemStack(ItemInit.DIX_EUROS));
                                        } catch (IOException e) {
                                            context.sendMessage(new TextComponentString("§c§l[ATM] §r§cUne erreur est survenue."));
                                            throw new RuntimeException(e);
                                        }

                                    } else {
                                        context.sendMessage(new TextComponentString("§c§l[ATM] §r§cVous n'avez pas assez d'argent sur votre compte."));
                                        Main.network.sendTo(new PacketOpenAcsGui(0, "", ""), (EntityPlayerMP) context);
                                    }
                                    return;
                                case "20":
                                    if (Float.parseFloat(account.getMoney()) > 20.0f) {
                                        try {
                                            NemesisLink.NEMESIS_API.makeTransaction(args2.get(0), "void", "20");
                                            context.inventory.addItemStackToInventory(new ItemStack(ItemInit.VINGT_EUROS));
                                        } catch (IOException e) {
                                            context.sendMessage(new TextComponentString("§c§l[ATM] §r§cUne erreur est survenue."));
                                            throw new RuntimeException(e);
                                        }

                                    } else {
                                        context.sendMessage(new TextComponentString("§c§l[ATM] §r§cVous n'avez pas assez d'argent sur votre compte."));
                                        Main.network.sendTo(new PacketOpenAcsGui(0, "", ""), (EntityPlayerMP) context);
                                    }
                                    return;
                                case "50":
                                    if (Float.parseFloat(account.getMoney()) > 50.0f) {
                                        try {
                                            NemesisLink.NEMESIS_API.makeTransaction(args2.get(0), "void", "50");
                                            context.inventory.addItemStackToInventory(new ItemStack(ItemInit.CINQUANTE_EUROS));
                                        } catch (IOException e) {
                                            context.sendMessage(new TextComponentString("§c§l[ATM] §r§cUne erreur est survenue."));
                                            throw new RuntimeException(e);
                                        }

                                    } else {
                                        context.sendMessage(new TextComponentString("§c§l[ATM] §r§cVous n'avez pas assez d'argent sur votre compte."));
                                        Main.network.sendTo(new PacketOpenAcsGui(0, "", ""), (EntityPlayerMP) context);
                                    }
                                    return;
                                case "100":
                                    if (Float.parseFloat(account.getMoney()) > 100.0f) {
                                        try {
                                            NemesisLink.NEMESIS_API.makeTransaction(args2.get(0), "void", "100");
                                            context.inventory.addItemStackToInventory(new ItemStack(ItemInit.CENT_EUROS));
                                        } catch (IOException e) {
                                            context.sendMessage(new TextComponentString("§c§l[ATM] §r§cUne erreur est survenue."));
                                            throw new RuntimeException(e);
                                        }

                                    } else {
                                        context.sendMessage(new TextComponentString("§c§l[ATM] §r§cVous n'avez pas assez d'argent sur votre compte."));
                                        Main.network.sendTo(new PacketOpenAcsGui(0, "", ""), (EntityPlayerMP) context);
                                    }
                                    return;
                                case "200":
                                    if (Float.parseFloat(account.getMoney()) > 200.0f) {
                                        try {
                                            NemesisLink.NEMESIS_API.makeTransaction(args2.get(0), "void", "200");
                                            context.inventory.addItemStackToInventory(new ItemStack(ItemInit.DEUX_CENTS_EUROS));
                                        } catch (IOException e) {
                                            context.sendMessage(new TextComponentString("§c§l[ATM] §r§cUne erreur est survenue."));
                                            throw new RuntimeException(e);
                                        }

                                    } else {
                                        context.sendMessage(new TextComponentString("§c§l[ATM] §r§cVous n'avez pas assez d'argent sur votre compte."));
                                        Main.network.sendTo(new PacketOpenAcsGui(0, "", ""), (EntityPlayerMP) context);
                                    }
                                    return;
                                case "500":
                                    if (Float.parseFloat(account.getMoney()) > 500.0f) {
                                        try {
                                            NemesisLink.NEMESIS_API.makeTransaction(args2.get(0), "void", "500");
                                            context.inventory.addItemStackToInventory(new ItemStack(ItemInit.CINQ_CENTS_EUROS));
                                        } catch (IOException e) {
                                            context.sendMessage(new TextComponentString("§c§l[ATM] §r§cUne erreur est survenue."));
                                            throw new RuntimeException(e);
                                        }

                                    } else {
                                        context.sendMessage(new TextComponentString("§c§l[ATM] §r§cVous n'avez pas assez d'argent sur votre compte."));
                                        Main.network.sendTo(new PacketOpenAcsGui(0, "", ""), (EntityPlayerMP) context);
                                    }
                                    return;
//                                default:
//                                    ((EntityPlayerMP) context).connection.disconnect(new TextComponentString("§c§l[ATM] Triche enregistrée : " + args2.get(2) +"."));
//                                    Main.network.sendTo(new PacketOpenAcsGui(0, "", ""), (EntityPlayerMP) context);
                            }
                        } else {
                            ((EntityPlayerMP) context).connection.disconnect(new TextComponentString("§c§l[ATM] Triche enregistrée 2."));
                            Main.network.sendTo(new PacketOpenAcsGui(0, "", ""), (EntityPlayerMP) context);
                        }
                    } else {
                        ((EntityPlayerMP) context).connection.disconnect(new TextComponentString("§c§l[ATM] Triche enregistrée 1."));
                        Main.network.sendTo(new PacketOpenAcsGui(0, "", ""), (EntityPlayerMP) context);
                    }

                } else if (args2.get(3).equals("add")) {
                    BankAccount account = null;
                    try {
                        account = NemesisLink.NEMESIS_API.getBankAccountAny(args2.get(0));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    if (account != null) {
                        System.out.println("Account found");
                        System.out.println(args2);
                        if (Objects.equals(account.getAccountpassword(), args2.get(1))) {
                            System.out.println("Adding money");
                            switch (args2.get(2)) {
                                case "5":
                                    if (context.inventory.hasItemStack(new ItemStack(ItemInit.CINQ_EUROS))) {
                                        try {
                                            NemesisLink.NEMESIS_API.makeTransaction("void", args2.get(0), "5");
                                            context.inventory.clearMatchingItems(ItemInit.CINQ_EUROS, -1, 1, null);
                                        } catch (IOException e) {
                                            context.sendMessage(new TextComponentString("§c§l[ATM] §r§cUne erreur est survenue."));
                                            throw new RuntimeException(e);
                                        }

                                    } else {
                                        context.sendMessage(new TextComponentString("§c§l[ATM] §r§cVous n'avez pas de billet de 5€ sur vous."));
                                        Main.network.sendTo(new PacketOpenAcsGui(0, "", ""), (EntityPlayerMP) context);
                                    }
                                    return;
                                case "10":
                                    if (context.inventory.hasItemStack(new ItemStack(ItemInit.DIX_EUROS))) {
                                        try {
                                            NemesisLink.NEMESIS_API.makeTransaction("void", args2.get(0), "10");
                                            context.inventory.clearMatchingItems(ItemInit.DIX_EUROS, -1, 1, null);
                                        } catch (IOException e) {
                                            context.sendMessage(new TextComponentString("§c§l[ATM] §r§cUne erreur est survenue."));
                                            throw new RuntimeException(e);
                                        }

                                    } else {
                                        context.sendMessage(new TextComponentString("§c§l[ATM] §r§cVous n'avez pas de billet de 10€ sur vous."));
                                        Main.network.sendTo(new PacketOpenAcsGui(0, "", ""), (EntityPlayerMP) context);
                                    }
                                    return;
                                case "20":
                                    if (context.inventory.hasItemStack(new ItemStack(ItemInit.VINGT_EUROS))) {
                                        try {
                                            NemesisLink.NEMESIS_API.makeTransaction("void", args2.get(0), "20");
                                            context.inventory.clearMatchingItems(ItemInit.VINGT_EUROS, -1, 1, null);
                                        } catch (IOException e) {
                                            context.sendMessage(new TextComponentString("§c§l[ATM] §r§cUne erreur est survenue."));
                                            throw new RuntimeException(e);
                                        }

                                    } else {
                                        context.sendMessage(new TextComponentString("§c§l[ATM] §r§cVous n'avez pas de billet de 20€ sur vous."));
                                        Main.network.sendTo(new PacketOpenAcsGui(0, "", ""), (EntityPlayerMP) context);
                                    }
                                    return;
                                case "50":
                                    if (context.inventory.hasItemStack(new ItemStack(ItemInit.CINQUANTE_EUROS))) {
                                        try {
                                            NemesisLink.NEMESIS_API.makeTransaction("void", args2.get(0), "50");
                                            context.inventory.clearMatchingItems(ItemInit.CINQUANTE_EUROS, -1, 1, null);
                                        } catch (IOException e) {
                                            context.sendMessage(new TextComponentString("§c§l[ATM] §r§cUne erreur est survenue."));
                                            throw new RuntimeException(e);
                                        }

                                    } else {
                                        context.sendMessage(new TextComponentString("§c§l[ATM] §r§cVous n'avez pas de billet de 50€ sur vous."));
                                        Main.network.sendTo(new PacketOpenAcsGui(0, "", ""), (EntityPlayerMP) context);
                                    }
                                    return;
                                case "100":
                                    if (context.inventory.hasItemStack(new ItemStack(ItemInit.CENT_EUROS))) {
                                        try {
                                            NemesisLink.NEMESIS_API.makeTransaction("void", args2.get(0), "100");
                                            context.inventory.clearMatchingItems(ItemInit.CENT_EUROS, -1, 1, null);
                                        } catch (IOException e) {
                                            context.sendMessage(new TextComponentString("§c§l[ATM] §r§cUne erreur est survenue."));
                                            throw new RuntimeException(e);
                                        }

                                    } else {
                                        context.sendMessage(new TextComponentString("§c§l[ATM] §r§cVous n'avez pas de billet de 100€ sur vous."));
                                        Main.network.sendTo(new PacketOpenAcsGui(0, "", ""), (EntityPlayerMP) context);
                                    }
                                    return;
                                case "200":
                                    if (context.inventory.hasItemStack(new ItemStack(ItemInit.DEUX_CENTS_EUROS))) {
                                        try {
                                            NemesisLink.NEMESIS_API.makeTransaction("void", args2.get(0), "200");
                                            context.inventory.clearMatchingItems(ItemInit.DEUX_CENTS_EUROS, -1, 1, null);
                                        } catch (IOException e) {
                                            context.sendMessage(new TextComponentString("§c§l[ATM] §r§cUne erreur est survenue."));
                                            throw new RuntimeException(e);
                                        }

                                    } else {
                                        context.sendMessage(new TextComponentString("§c§l[ATM] §r§cVous n'avez pas de billet de 200€ sur vous."));
                                        Main.network.sendTo(new PacketOpenAcsGui(0, "", ""), (EntityPlayerMP) context);
                                    }
                                    return;
                                case "500":
                                    if (context.inventory.hasItemStack(new ItemStack(ItemInit.CINQ_CENTS_EUROS))) {
                                        try {
                                            NemesisLink.NEMESIS_API.makeTransaction("void", args2.get(0), "500");
                                            context.inventory.clearMatchingItems(ItemInit.CINQ_CENTS_EUROS, -1, 1, null);
                                        } catch (IOException e) {
                                            context.sendMessage(new TextComponentString("§c§l[ATM] §r§cUne erreur est survenue."));
                                            throw new RuntimeException(e);
                                        }
                                    } else {
                                        context.sendMessage(new TextComponentString("§c§l[ATM] §r§cVous n'avez pas de billet de 500€ sur vous."));
                                        Main.network.sendTo(new PacketOpenAcsGui(0, "", ""), (EntityPlayerMP) context);
                                    }
                                    return;
                            }
                        }
                    }
                }
            }
        }
    }

    public static class ServerHandler implements IMessageHandler<PacketATMInteraction, IMessage> {
        @Override
        public IMessage onMessage(PacketATMInteraction message, MessageContext ctx) {
            System.out.println("Recived packet");
            Objects.requireNonNull(ctx.getServerHandler().player.getServer()).addScheduledTask(() -> {
                message.handleUDPReceive(ctx.getServerHandler().player, Side.SERVER);
            });
            return null;
        }
    }

    public static class ClientHandler implements IMessageHandler<PacketATMInteraction, IMessage> {
        @Override
        public IMessage onMessage(PacketATMInteraction message, MessageContext ctx) {
            System.out.println("Recived packet");
            Minecraft.getMinecraft().addScheduledTask(() -> {
                message.handleUDPReceive(Minecraft.getMinecraft().player, Side.CLIENT);
            });
            return null;
        }
    }
}
