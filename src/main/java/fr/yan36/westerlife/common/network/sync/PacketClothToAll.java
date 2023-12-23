package fr.yan36.westerlife.common.network.sync;

import fr.dynamx.common.items.DynamXItemArmor;
import fr.yan36.westerlife.client.Client;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.*;
import java.util.stream.Collectors;

public class PacketClothToAll implements IMessage{

    String uuid;
    String clothe;

    public PacketClothToAll(){}

    public PacketClothToAll(String uuid, String clothes) {
        this.uuid = uuid;
        this.clothe = clothes;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.uuid = ByteBufUtils.readUTF8String(buf);
        this.clothe = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, this.uuid);
        ByteBufUtils.writeUTF8String(buf, String.join(",", this.clothe));
    }


    public static class Handler implements IMessageHandler<PacketClothToAll, IMessage> {
        @Override
//        @SideOnly(Side.CLIENT)
        public IMessage onMessage(PacketClothToAll m, MessageContext ctx) {
            // get all items registered in the game
            EntityPlayer p = Minecraft.getMinecraft().player.world.getPlayerEntityByUUID(UUID.fromString(m.uuid));
            List<DynamXItemArmor<?>> globalitems = ForgeRegistries.ITEMS.getEntries().stream().filter(e -> e.getValue() instanceof DynamXItemArmor<?>).collect(Collectors.toCollection(ArrayList::new)).stream().map(e -> (DynamXItemArmor<?>) e.getValue()).collect(Collectors.toList());

            System.out.println(m.clothe);

            if(m.clothe.equals("removeall")) {
                Client.superpositionState.remove(UUID.fromString(m.uuid));
                return null;
            }

            globalitems.forEach(item -> {
                List<String> clothes = Arrays.asList(m.clothe.split(","));
                clothes.forEach(cloth -> {
                    if(item.getInfo().getFullName().equals(cloth)) {

                        if(Client.superpositionState.containsKey(UUID.fromString(m.uuid))) {

                            List<DynamXItemArmor<?>> items = new ArrayList<>(Client.superpositionState.get(UUID.fromString(m.uuid)));
                            items.add(item);

                            if(Client.superpositionState.get(UUID.fromString(m.uuid)).contains(item)) {
                                // remove item without using item.remove() because it's not the same instance
                                items.removeIf(i -> i.getInfo().getFullName().equals(item.getInfo().getFullName()));
                                System.out.println("remove");
                            } else {
                                items.add(item);
                                System.out.println("add");
                            }

                            items.forEach(i -> System.out.println(i.getInfo().getFullName()));
                            Client.superpositionState.replace(UUID.fromString(m.uuid), items);


                        } else {
                            System.out.println("autre");
                            Client.superpositionState.put(UUID.fromString(m.uuid), Collections.singletonList(item));
                        }

                    }
                });
            });

//            if(Client.superpositionState.containsKey(UUID.fromString(m.uuid)))
//                Client.superpositionState.replace(m.uuid, DynamXObjectLoaders.ARMORS.findInfo(m.clothe).get);
//            else
//                Client.superpositionState.put(m.uuid, Animation.getAnimationById(m.id));
//            System.out.println("Animation " + m.id + " received from " + m.entityId);
            return null;
        }
    }
}