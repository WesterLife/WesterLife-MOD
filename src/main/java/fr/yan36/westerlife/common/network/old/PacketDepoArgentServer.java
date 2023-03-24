package fr.yan36.westerlife.common.network.old;

import fr.yan36.westerlife.Main;
import fr.yan36.westerlife.common.init.ItemInit;
import fr.yan36.westerlife.server.bdd.MethodesBDD;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PacketDepoArgentServer implements IMessage {


    int money;
    int player;
    ItemStack is;

    public PacketDepoArgentServer() {
    }

    public PacketDepoArgentServer(EntityPlayer player, int money, ItemStack is) {

        this.player = player.getEntityId();
        this.money = money;
        this.is = is;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.player = buf.readInt();
        this.money = buf.readInt();
        this.is = ByteBufUtils.readItemStack(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.player);
        buf.writeInt(this.money);
        ByteBufUtils.writeItemStack(buf, this.is);
    }

    public static class ServerHandler implements IMessageHandler<PacketDepoArgentServer, IMessage> {
        @Override
        @SideOnly(Side.SERVER)
        public IMessage onMessage(PacketDepoArgentServer m, MessageContext ctx) {
            EntityPlayer e = (EntityPlayer) ctx.getServerHandler().player.world.getEntityByID(m.player);

            //Le packet est plutôt sécurisé mais une faille est possible.

            assert e != null;
            if ((m.is.getItem().equals(ItemInit.CINQ_EUROS) || m.is.getItem().equals(ItemInit.CINQUANTE_EUROS) || m.is.getItem().equals(ItemInit.CINQ_CENTS_EUROS) || m.is.getItem().equals(ItemInit.DIX_EUROS) || m.is.getItem().equals(ItemInit.VINGT_EUROS) || m.is.getItem().equals(ItemInit.CENT_EUROS) || m.is.getItem().equals(ItemInit.DEUX_CENTS_EUROS)) && (m.money == 5 || m.money == 50 || m.money == 100 || m.money == 200 || m.money == 500 || m.money == 10 || m.money == 20)){
            if (e.inventory.hasItemStack(m.is)) {
                int count = 0;
                for (int i = 0; i < e.inventory.getSizeInventory(); i++) {
                    if (e.inventory.getStackInSlot(i).isItemEqual(m.is)) {

                        e.inventory.clearMatchingItems(m.is.getItem(), 0, 1, null);
                        break;

                    }

                }

                Main.network.sendTo(new PacketSyncPlayer(MethodesBDD.getNom(e),MethodesBDD.getPrenom(e),MethodesBDD.getSex(e),MethodesBDD.getDate(e),MethodesBDD.getArgent(e), MethodesBDD.getRIB(e)), (EntityPlayerMP) e);
                MethodesBDD.setArgent(e, MethodesBDD.getArgent(e) + m.money);

            } else {
                e.sendMessage(new TextComponentString("§cVous n'avez pas les billets nécessaires pour effectuer ce dépôt."));
            }
            }
            return null;
        }
    }
}