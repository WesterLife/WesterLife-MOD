package fr.yan36.westerlife.common.network;

import fr.yan36.westerlife.common.items.WesterItem;
import fr.yan36.westerlife.serveur.bdd.MethodesBDD;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PacketRetirerArgent implements IMessage {

    int player;
    int money;
    ItemStack is;

    public PacketRetirerArgent(EntityPlayer player, ItemStack is, int money){

        this.player=player.getEntityId();
        this.money=money;
        this.is=is;

    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.player= buf.readInt();
        this.money= buf.readInt();
        this.is = ByteBufUtils.readItemStack(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.player);
        buf.writeInt(this.money);
        ByteBufUtils.writeItemStack(buf, this.is);
    }

    public static class ServerHandler implements IMessageHandler<PacketRetirerArgent, IMessage> {
        @Override
        @SideOnly(Side.SERVER)
        public IMessage onMessage(PacketRetirerArgent m, MessageContext ctx) {
            EntityPlayer player = (EntityPlayer) ctx.getServerHandler().player.world.getEntityByID(m.player);
            System.out.println("Message !!!");
            assert player != null;
            if(!MethodesBDD.getPlayerExist(player)) {
                System.out.println("Le joueur existe on peut le débiter");
                if(m.is.isItemEqual(new ItemStack(WesterItem.CINQEUROS))){
                    System.out.println("On vérifie que l'itemstack demandé est un billet.");
                        System.out.println("On vérifie qu'il a le billet.");
                        if(MethodesBDD.getArgent(player) > m.money){
                            System.out.println("Si le joueur a assez d'argent.");
                            MethodesBDD.setArgent(player, MethodesBDD.getArgent(player) - m.money);
                            player.addItemStackToInventory(m.is);

                    }

                }
            }
            return null;
        }
    }
}
