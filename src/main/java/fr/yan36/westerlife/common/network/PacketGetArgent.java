package fr.yan36.westerlife.common.network;

import fr.yan36.westerlife.client.Profil;
import fr.yan36.westerlife.serveur.bdd.MethodesBDD;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PacketGetArgent implements IMessage {

    int player;

    public PacketGetArgent(EntityPlayer player){

        this.player = player.getEntityId();


    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.player = buf.readInt();

    }

    @Override
    public void toBytes(ByteBuf buf) {

        buf.writeInt(this.player);

    }

    public static class handler implements IMessageHandler<PacketGetArgent, IMessage> {

        @Override
        @SideOnly(Side.SERVER)
        public IMessage onMessage(PacketGetArgent m, MessageContext ctx) {
            EntityPlayer e = (EntityPlayer) ctx.getServerHandler().player.world.getEntityByID(m.player);
            if (!MethodesBDD.getPlayerExist(e)) {
                MethodesBDD.getArgent(e);
            }
            Profil.setBank(MethodesBDD.getArgent(e));
            return null;
        }
    }
}
