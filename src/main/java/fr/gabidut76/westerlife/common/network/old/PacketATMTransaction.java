package fr.gabidut76.westerlife.common.network.old;

import fr.gabidut76.westerlife.westercore.Main;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
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
//            if(MethodesBDD.getCharacterExists(e)){
//                if(MethodesBDD.getRibExist(m.rib)){
//                    if(MethodesBDD.getArgent(ctx.getServerHandler().player) >= Double.parseDouble(m.montant)){
//                        MethodesBDD.setArgent(e, MethodesBDD.getArgent(e) - Double.parseDouble(m.montant));
//                        MethodesBDD.setArgentByRIB(m.rib, MethodesBDD.getArgentByRIB(m.rib) + Double.parseDouble(m.montant));
//                        e.sendMessage(new TextComponentString("§cTransaction effectuée avec succès."));
//                        Main.network.sendTo(new PacketSyncPlayer(MethodesBDD.getNom(e), MethodesBDD.getPrenom(e), MethodesBDD.getSex(e), MethodesBDD.getDate(e), MethodesBDD.getArgent(e), MethodesBDD.getRIB(e)), (EntityPlayerMP) e);
//                    } else {
//                        ctx.getServerHandler().player.sendMessage(new TextComponentString("§cVous n'avez pas assez d'argent sur votre compte pour effectuer cette transaction"));
//                    }
//                } else {
//                    ctx.getServerHandler().player.sendMessage(new TextComponentString("§cLe RIB auquel est destiné la transaction n'existe pas."));
//                }
//            }

        return null;
        }
    }
}