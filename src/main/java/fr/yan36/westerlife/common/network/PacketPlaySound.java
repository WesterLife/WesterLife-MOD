package fr.yan36.westerlife.common.network;

import fr.yan36.westerlife.common.registry.SoundsHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PacketPlaySound implements IMessage{

    int soundId;

    public PacketPlaySound(){}

    public PacketPlaySound(int soundId) {
        this.soundId = soundId;
    }
    @Override
    public void fromBytes(ByteBuf buf) {
        this.soundId = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.soundId);
    }

    public static class Handler implements IMessageHandler<PacketPlaySound, IMessage> {
        @Override
        @SideOnly(Side.CLIENT)
        public IMessage onMessage(PacketPlaySound m, MessageContext ctx) {
            switch (m.soundId) {
                case 1:
                    Minecraft.getMinecraft().player.playSound(SoundsHandler.BIP2, 1.0F, 1.0F);
                    break;
            }
            return null;
        }
    }
}