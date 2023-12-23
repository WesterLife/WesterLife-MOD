package fr.yan36.westerlife.common.network.garage;

import com.jme3.math.Vector3f;
import fr.aym.acslib.utils.packetserializer.SerializablePacket;
import fr.dynamx.addons.basics.BasicsAddon;
import fr.dynamx.addons.basics.common.modules.LicensePlateModule;
import fr.dynamx.api.network.EnumNetworkType;
import fr.dynamx.api.network.IDnxPacket;
import fr.dynamx.api.physics.player.DynamXPhysicsWorldBlacklistApi;
import fr.dynamx.common.DynamXContext;
import fr.dynamx.common.contentpack.DynamXObjectLoaders;
import fr.dynamx.common.entities.BaseVehicleEntity;
import fr.dynamx.common.entities.vehicles.CarEntity;
import fr.dynamx.utils.DynamXUtils;
import fr.nathanael2611.simpledatabasemanager.core.Databases;
import fr.yan36.westerlife.Main;
import fr.yan36.westerlife.common.Util;
import fr.yan36.westerlife.common.blocks.tileentity.TilePark;
import fr.yan36.westerlife.common.capabilities.packets.PacketSyncExtraItem;
import fr.yan36.westerlife.common.capabilities.playergarage.PlayerGarage;
import fr.yan36.westerlife.common.capabilities.playergarage.PlayerGarageCapability;
import fr.yan36.westerlife.common.capabilities.playerinventory.ExtraItemCapability;
import fr.yan36.westerlife.common.capabilities.playerinventory.IExtraItemHandler;
import fr.yan36.westerlife.common.objects.GarageCar;
import fr.yan36.westerlife.common.utils.carmodule.GarageModule;
import fr.yan36.westerlife.server.api.NemesisAPI;
import fr.yan36.westerlife.server.api.NemesisLink;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTException;
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

import java.io.IOException;
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
            try {
                String uid = NemesisLink.NEMESIS_API.getUserIdFromUUID(String.valueOf(context.getUniqueID()));
                List<GarageCar> cars = NemesisLink.NEMESIS_API.getGarageCars(uid);

                GarageCar car11 = (GarageCar) getObjectsIn()[0];

                for (GarageCar garageCar : cars) {
                    System.out.println(car11);
                    if (garageCar.getUniqueID().equals(car11.getUniqueID())) {
                        if (garageCar.isInGarage()) {
                            CarEntity<?> car2 = new CarEntity<>(car11.getCarName(), world, new Vector3f(this.pos.getX(), this.pos.getY(), this.pos.getZ()), 0, car11.getMeta());

                            NemesisLink.NEMESIS_API.setCarState(uid, garageCar.getUniqueID(), "0");

                            System.out.println("CarEntity created");
                            NBTTagCompound nbt = car11.getCarNBT();
                            nbt.setString("bas_immat_plate", garageCar.getCarPlate());
                            car2.setPosition(this.pos.getX(), this.pos.getY(), this.pos.getZ());
                            car2.setPositionNonDirty();
                            car2.readFromNBT(nbt);
                            world.spawnEntity(car2);

                            car2.getModuleByType(LicensePlateModule.class).setPlate(garageCar.getCarPlate());


                            ItemStack stack = new ItemStack(BasicsAddon.keysItem);


                            stack.setTagCompound(new NBTTagCompound());
                            stack.getTagCompound().setString("VehicleId", garageCar.getUniqueID());
                            stack.getTagCompound().setString("VehicleName", garageCar.getCarName());
                            stack.setStackDisplayName("§e" + garageCar.getCarName() + " §7(" + garageCar.getCarPlate() + ")");
                            context.inventory.addItemStackToInventory(stack);


                            Objects.requireNonNull(context.getCapability(PlayerGarageCapability.CAPABILITY, null)).removeCar(garageCar);


                            car2.setPhysicsInitCallback(((modularPhysicsEntity, abstractEntityPhysicsHandler) -> {
                                if (DynamXContext.usesPhysicsWorld(car2.world)) {
                                    car2.getPhysicsHandler().setPhysicsPosition(DynamXUtils.toVector3f(this.pos));
                                    car2.getModuleByType(LicensePlateModule.class).setPlate(garageCar.getCarPlate());
                                    car2.getModuleByType(GarageModule.class).setOwner(context.getUniqueID().toString());
                                } else {
                                    Main.logger.warn("Physics world not found for car " + car2.getUniqueID() + " (" + car2.getName() + ")");
                                }
                            }));

                        }
                    }
                }
            } catch (IOException | NBTException e) {
                throw new RuntimeException(e);
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