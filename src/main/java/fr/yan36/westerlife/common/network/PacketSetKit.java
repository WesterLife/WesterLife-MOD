package fr.yan36.westerlife.common.network;

import fr.nathanael2611.simpledatabasemanager.core.Databases;
import fr.yan36.westerlife.Main;
import fr.yan36.westerlife.common.Util;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketCreativeInventoryAction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;

public class PacketSetKit implements IMessage{

    String kitname;
    String items;


    public PacketSetKit(){}

    public PacketSetKit(String kitname, String items) {

        this.kitname = kitname;
        this.items = items;

    }
    @Override
    public void fromBytes(ByteBuf buf) {
        this.kitname = ByteBufUtils.readUTF8String(buf);
        this.items = ByteBufUtils.readUTF8String(buf);

    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, this.kitname);
        ByteBufUtils.writeUTF8String(buf, this.items);
    }

    public static class Handler implements IMessageHandler<PacketSetKit, IMessage> {
        @Override
        public IMessage onMessage(PacketSetKit m, MessageContext ctx) {
            if(!Util.hasPermission(ctx.getServerHandler().player, "op")) return null;
            if(!ctx.getServerHandler().player.isCreative()) return null;
            Main.wl_db.setString("kits."+m.kitname, m.items);
            Main.wl_db.setString("kits", Main.wl_db.getString("kits")+";"+m.kitname);
            System.out.println("Kit "+m.kitname+" set to "+ m.items);

            return null;
        }
    }
}