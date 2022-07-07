package fr.yan36.westerlife.common.network;

import fr.yan36.westerlife.Main;
import fr.yan36.westerlife.server.bdd.MethodesBDD;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PacketATMTransaction implements IMessage{

    public String rib, montant;

    public PacketATMTransaction(){}

    public PacketATMTransaction(String rib, String montant) {
        this.rib = rib;
        this.montant = montant;
    }


    @Override
    public void fromBytes(ByteBuf buf) {
        this.montant = ByteBufUtils.readUTF8String(buf);
        this.rib = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, this.montant);
        ByteBufUtils.writeUTF8String(buf, this.rib);

    }

    public static class Handler implements IMessageHandler<PacketATMTransaction, IMessage> {
        @Override
        @SideOnly(Side.SERVER)
        public IMessage onMessage(PacketATMTransaction m, MessageContext ctx) {
            EntityPlayer e = (EntityPlayer) ctx.getServerHandler().player;
            if(MethodesBDD.getPlayerExist(e)){
                if(MethodesBDD.getRibExist(m.rib)){
                    if(MethodesBDD.getArgent(ctx.getServerHandler().player) >= Double.parseDouble(m.montant)){
                        MethodesBDD.setArgent(e, MethodesBDD.getArgent(e) - Double.parseDouble(m.montant));
                        MethodesBDD.setArgentByRIB(m.rib, MethodesBDD.getArgentByRIB(m.rib) + Double.parseDouble(m.montant));
                        e.sendMessage(new TextComponentString("§cTransaction effectuée avec succès."));
                    } else {
                        ctx.getServerHandler().player.sendMessage(new TextComponentString("§cVous n'avez pas assez d'argent sur votre compte pour effectuer cette transaction"));
                    }
                } else {
                    ctx.getServerHandler().player.sendMessage(new TextComponentString("§cLe RIB auquel est destiné la transaction n'existe pas."));
                }
            }

        return null;
        }
    }
}