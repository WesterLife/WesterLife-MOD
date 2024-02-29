package fr.gabidut76.westerlife.common.network;

import fr.gabidut76.westerlife.common.capabilities.playerstat.PlayerStatData;
import fr.gabidut76.westerlife.common.capabilities.playerstat.PlayerStatHandler;
import fr.gabidut76.westerlife.common.items.ItemCard;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.Collections;
import java.util.UUID;

public class PacketRequestCharacter implements IMessage{


    UUID id;

    public PacketRequestCharacter(){}

    public PacketRequestCharacter(UUID id) {

        this.id = id;

    }
    @Override
    public void fromBytes(ByteBuf buf) {
        this.id = UUID.fromString(ByteBufUtils.readUTF8String(buf));
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, this.id.toString());
    }

    public static class Handler implements IMessageHandler<PacketRequestCharacter, IMessage> {
        @Override
        public IMessage onMessage(PacketRequestCharacter m, MessageContext ctx) {
            EntityPlayer e = (EntityPlayer) ctx.getServerHandler().player;
            System.out.println("req");
            if(e.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ItemCard) {
                if(e.getHeldItem(EnumHand.MAIN_HAND).getTagCompound() != null) {
                    if(e.getHeldItem(EnumHand.MAIN_HAND).getTagCompound().hasKey("link")) {

                        EntityPlayer player = ctx.getServerHandler().player;

                        MinecraftServer server = player.getServer();

                        if(server == null) {
                            System.out.println("Server is null");
                            player.sendMessage(new TextComponentString("§cTf server is null ?"));
                        }

                        assert server != null;
                        if(server.getPlayerList().getPlayerByUUID(UUID.fromString(player.getHeldItem(EnumHand.MAIN_HAND).getTagCompound().getString("link"))).hasCapability(PlayerStatData.PlayerStatProvider.CAPABILITY, null)) {
                            PlayerStatHandler.sync(server.getPlayerList().getPlayerByUUID(UUID.fromString(player.getHeldItem(EnumHand.MAIN_HAND).getTagCompound().getString("link"))));
                        } else {
                            System.out.println("No capability found for " + server.getPlayerList().getPlayerByUUID(UUID.fromString(player.getHeldItem(EnumHand.MAIN_HAND).getTagCompound().getString("link"))));
                            player.sendMessage(new TextComponentString("§cErreur: Contacter le staff (" + player.getHeldItem(EnumHand.MAIN_HAND).getTagCompound().getString("link") + " doesn't exists)."));
                        }
                    }
                }
            }
            return null;
        }
    }
}