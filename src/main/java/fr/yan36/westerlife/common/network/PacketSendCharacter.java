package fr.yan36.westerlife.common.network;

import fr.yan36.westerlife.Main;
import fr.yan36.westerlife.client.Client;
import fr.yan36.westerlife.common.items.ItemCard;
import fr.yan36.westerlife.common.objects.character.Character;
import fr.yan36.westerlife.common.objects.character.Permis;
import fr.yan36.westerlife.server.Serveur;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.Objects;
import java.util.UUID;

public class PacketSendCharacter implements IMessage{


    Character id;
    Permis permis;

    public PacketSendCharacter(){}

    public PacketSendCharacter(Character id) {
        System.out.println(id.toString());
        this.id = id;
        this.permis = new Permis();
    }

    public PacketSendCharacter(Permis permis) {
        this.permis = permis;
        this.id = new Character();
    }
    @Override
    public void fromBytes(ByteBuf buf) {
        this.id = Character.fromString(ByteBufUtils.readUTF8String(buf));
        this.permis = Permis.fromString(ByteBufUtils.readUTF8String(buf));
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, this.id.toString());
        ByteBufUtils.writeUTF8String(buf, this.permis.toString());
    }

    public static class Handler implements IMessageHandler<PacketSendCharacter, IMessage> {
        @Override
        public IMessage onMessage(PacketSendCharacter m, MessageContext ctx) {

            System.out.println(m.id.toString());
            System.out.println(m.permis.toString());

            if(!Objects.equals(m.id.getUuid(), UUID.fromString("00000000-0000-0000-0000-000000000000"))) {
                Client.knowCharacters.put(m.id.getUuid(), m.id);
                System.out.println("Character received");
                Client.waitForSomething.remove("cni");
            }
            System.out.println(m.permis.getObtentionDate());
            if(!Objects.equals(m.permis.getUuid(), UUID.fromString("00000000-0000-0000-0000-000000000000"))) {
                Client.knowPermis.put(m.permis.getUuid(), m.permis);
                System.out.println("Permis received");
                Client.waitForSomething.remove("permis");
            }
            return null;
        }
    }
}