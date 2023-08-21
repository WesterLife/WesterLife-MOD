package fr.yan36.westerlife.common.network.garage;

import com.jme3.math.Vector3f;
import fr.aym.acslib.utils.packetserializer.SerializablePacket;
import fr.dynamx.addons.basics.BasicsAddon;
import fr.dynamx.addons.basics.common.modules.LicensePlateModule;
import fr.dynamx.api.network.EnumNetworkType;
import fr.dynamx.api.network.IDnxPacket;
import fr.dynamx.common.contentpack.DynamXObjectLoaders;
import fr.dynamx.common.entities.BaseVehicleEntity;
import fr.dynamx.common.entities.vehicles.CarEntity;
import fr.dynamx.utils.DynamXUtils;
import fr.nathanael2611.simpledatabasemanager.core.Databases;
import fr.yan36.westerlife.common.Util;
import fr.yan36.westerlife.common.blocks.tileentity.TilePark;
import fr.yan36.westerlife.common.capabilities.packets.PacketSyncExtraItem;
import fr.yan36.westerlife.common.capabilities.playergarage.PlayerGarage;
import fr.yan36.westerlife.common.capabilities.playergarage.PlayerGarageCapability;
import fr.yan36.westerlife.common.capabilities.playerinventory.ExtraItemCapability;
import fr.yan36.westerlife.common.capabilities.playerinventory.IExtraItemHandler;
import fr.yan36.westerlife.common.objects.GarageCar;
import fr.yan36.westerlife.common.utils.carmodule.GarageModule;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

import java.util.*;

public class PacketExtractFromGarage extends SerializablePacket implements IDnxPacket {


    BlockPos pos;
    GarageCar car;


    public PacketExtractFromGarage() {
        this.pos = new BlockPos(-1, -1, -1);
    }

    public PacketExtractFromGarage(BlockPos pos, GarageCar car) {
        super(car);
        this.pos = pos;

    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.pos = Util.parseBlockPosFromString(ByteBufUtils.readUTF8String(buf));
        System.out.println("PacketExtractFromGarage fromBytes");
        super.fromBytes(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, Objects.requireNonNull(Util.blockPosToString(this.pos)));
        super.toBytes(buf);
    }

    @Override
    public EnumNetworkType getPreferredNetwork() {
        return EnumNetworkType.DYNAMX_UDP;
    }

    @Override
    public void handleUDPReceive(EntityPlayer context, Side side) {
        if (side.isServer()) {
            World world = context.world;
            GarageCar car = (GarageCar) this.getObjectsIn()[0];

            if (context.hasCapability(PlayerGarageCapability.CAPABILITY, null)) {
                PlayerGarage garage = (PlayerGarage) context.getCapability(PlayerGarageCapability.CAPABILITY, null);
                for (GarageCar garageCar : garage.getCars()) {
                    if (garageCar.getCarPlate().equals(car.getCarPlate())) {
                        if (garageCar.isInGarage()) {
                            CarEntity<?> car2 = new CarEntity<>(car.getCarName(), world, new Vector3f(this.pos.getX(), this.pos.getY(), this.pos.getZ()), 0, car.getMeta());

                            NBTTagCompound nbt = car.getCarNBT();
                            nbt.setString("bas_immat_plate", garageCar.getCarPlate());
//                            nbt.setIntArray("Pos", new int[]{this.pos.getX(), this.pos.getY(), this.pos.getZ()});
                            car2.setPosition(this.pos.getX(), this.pos.getY(), this.pos.getZ());
                            car2.setPositionNonDirty();
                            car2.readFromNBT(nbt);
                            world.spawnEntity(car2);

                            car2.getModuleByType(LicensePlateModule.class).setPlate(garageCar.getCarPlate());


                            ItemStack stack = new ItemStack(BasicsAddon.keysItem);
                            stack.setStackDisplayName("§e" + garageCar.getCarName() + " §7(" + garageCar.getCarPlate() + ")");
                            stack.setTagCompound(new NBTTagCompound());
                            stack.getTagCompound().setString("VehicleId", garageCar.getUniqueID());
                            stack.getTagCompound().setString("VehicleName", garageCar.getCarName());

                            context.inventory.addItemStackToInventory(stack);


                            Objects.requireNonNull(context.getCapability(PlayerGarageCapability.CAPABILITY, null)).removeCar(garageCar);


                            car2.setPhysicsInitCallback(((modularPhysicsEntity, abstractEntityPhysicsHandler) -> {
                                car2.getPhysicsHandler().setPhysicsPosition(DynamXUtils.toVector3f(this.pos));
                                car2.getModuleByType(LicensePlateModule.class).setPlate(garageCar.getCarPlate());
                            }));
                            PlayerGarageCapability.sync(context, Collections.singletonList(context));
                        } else {
                            context.sendMessage(new TextComponentString("§cThis car is already out of the garage"));
                        }
                    }
                }
            }


        }
    }

    public static class Handler implements IMessageHandler<PacketExtractFromGarage, IMessage> {
        @Override
        public IMessage onMessage(PacketExtractFromGarage message, MessageContext ctx) {
            System.out.println("PacketExtractFromGarage received");
            ctx.getServerHandler().player.server.addScheduledTask(() -> {
                message.handleUDPReceive(ctx.getServerHandler().player, Side.SERVER);
            });
            return null;
        }
    }
}