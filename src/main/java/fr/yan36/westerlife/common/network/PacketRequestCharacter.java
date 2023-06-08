package fr.yan36.westerlife.common.network;

import fr.yan36.westerlife.Main;
import fr.yan36.westerlife.common.items.ItemCard;
import fr.yan36.westerlife.server.Serveur;
import fr.yan36.westerlife.server.bdd.DBUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.Objects;
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
                        System.out.println("send");
                        Main.network.sendTo(new PacketSendCharacter(Objects.requireNonNull(DBUtils.getCharacter(UUID.fromString(e.getHeldItem(EnumHand.MAIN_HAND).getTagCompound().getString("link"))))), ctx.getServerHandler().player);
                        Main.network.sendTo(new PacketSendCharacter(Objects.requireNonNull(DBUtils.getPermis(UUID.fromString(e.getHeldItem(EnumHand.MAIN_HAND).getTagCompound().getString("link"))))), ctx.getServerHandler().player);
                    }
                }
            }
            return null;
        }
    }
}