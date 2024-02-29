package fr.gabidut76.westerlife.common.network.sync;

import fr.gabidut76.westerlife.common.entities.npcdomac.NPCDomacEntity;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLiving;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PacketSetEntityData implements IMessage{


    String uuid;
    String name;
    String value;

    public PacketSetEntityData(){}

    public PacketSetEntityData(String uuid, String name, String value) {
        this.uuid = uuid;
        this.name = name;
        this.value = value;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.uuid = ByteBufUtils.readUTF8String(buf);
        this.name = ByteBufUtils.readUTF8String(buf);
        this.value = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, this.uuid);
        ByteBufUtils.writeUTF8String(buf, this.name);
        ByteBufUtils.writeUTF8String(buf, this.value);
    }

    public static class Handler implements IMessageHandler<PacketSetEntityData, IMessage> {
        @Override
        @SideOnly(Side.CLIENT)
        public IMessage onMessage(PacketSetEntityData m, MessageContext ctx) {

            System.out.println("Packet received");
            Minecraft.getMinecraft().player.world.getEntities(EntityLiving.class, e -> e.getUniqueID().toString().equals(m.uuid)).forEach(e -> {
                if(e instanceof NPCDomacEntity && m.name.equals("status")) {
                    System.out.println("Status changed to " + m.value);
                    ((NPCDomacEntity) e).setStatusData(m.value);
                }
            });

            return null;
        }
    }
}