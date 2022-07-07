package fr.yan36.westerlife.common.network;

import fr.yan36.westerlife.Main;
import fr.yan36.westerlife.common.items.WesterItem;
import fr.yan36.westerlife.server.bdd.MethodesBDD;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PacketRetirerArgentServer implements IMessage {

    int player;
    int money;

    public PacketRetirerArgentServer() {}

    public PacketRetirerArgentServer(EntityPlayer player, int money){

        this.player=player.getEntityId();
        this.money=money;

    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.player= buf.readInt();
        this.money= buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.player);
        buf.writeInt(this.money);
    }

    public static class ServerHandler implements IMessageHandler<PacketRetirerArgentServer, IMessage> {
        @Override
        @SideOnly(Side.SERVER)
        public IMessage onMessage(PacketRetirerArgentServer m, MessageContext ctx) {
            EntityPlayer e = (EntityPlayer) ctx.getServerHandler().player.world.getEntityByID(m.player);
            if(MethodesBDD.getArgent(e) >= m.money){
                    switch (m.money) {

                        //Vérification le pack est sécurisé.

                        case 5:
                            e.addItemStackToInventory(new ItemStack(WesterItem.CINQEUROS));
                            MethodesBDD.setArgent(e, MethodesBDD.getArgent(e) - 5);
                            break;
                        case 10:
                            e.addItemStackToInventory(new ItemStack(WesterItem.DIXEUROS));
                            MethodesBDD.setArgent(e, MethodesBDD.getArgent(e) - 10);
                            break;
                        case 20:
                            e.addItemStackToInventory(new ItemStack(WesterItem.VINGTEUROS));
                            MethodesBDD.setArgent(e, MethodesBDD.getArgent(e) - 20);
                            break;
                        case 50:
                            e.addItemStackToInventory(new ItemStack(WesterItem.CINQUANTEEUROS));
                            MethodesBDD.setArgent(e, MethodesBDD.getArgent(e) - 50);
                            break;
                        case 100:
                            e.addItemStackToInventory(new ItemStack(WesterItem.CENTEUROS));
                            MethodesBDD.setArgent(e, MethodesBDD.getArgent(e) - 100);
                            break;
                        case 200:
                            e.addItemStackToInventory(new ItemStack(WesterItem.DEUXCENTEUROS));
                            MethodesBDD.setArgent(e, MethodesBDD.getArgent(e) - 200);
                            break;
                        case 500:
                            e.addItemStackToInventory(new ItemStack(WesterItem.CINQCENTEUROS));
                            MethodesBDD.setArgent(e, MethodesBDD.getArgent(e) - 500);
                            break;
                        default:
                            break;

                    }
                } else {
                    e.sendMessage(new TextComponentString("§cVous n'avez pas les fonds nécessaires sur votre compte bancaire pour effectuer ce débit."));
                }
                System.out.println(m.money);
                Main.network.sendTo(new PacketSyncPlayer(MethodesBDD.getNom(e),MethodesBDD.getPrenom(e),MethodesBDD.getSex(e),MethodesBDD.getDate(e),MethodesBDD.getArgent(e),MethodesBDD.getRIB(e)), (EntityPlayerMP) e);

            return null;
        }
    }
}
