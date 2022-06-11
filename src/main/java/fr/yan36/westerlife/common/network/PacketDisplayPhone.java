package fr.yan36.westerlife.common.network;

import fr.aym.acsguis.api.ACsGuiApi;
import fr.yan36.westerlife.Main;
import fr.yan36.westerlife.client.gui.phone.CSSGuiPhone;
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

public class PacketDisplayPhone implements IMessage {

    public PacketDisplayPhone() {}


    @Override
    public void fromBytes(ByteBuf buf) {
    }

    @Override
    public void toBytes(ByteBuf buf) {
    }

    public static class ServerHandler implements IMessageHandler<PacketDisplayPhone, IMessage> {
        @Override
        @SideOnly(Side.SERVER)
        public IMessage onMessage(PacketDisplayPhone m, MessageContext ctx) {
            ACsGuiApi.asyncLoadThenShowGui("phone", CSSGuiPhone::new);
            return null;
        }
    }
}
