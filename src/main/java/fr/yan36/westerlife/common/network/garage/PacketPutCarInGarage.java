package fr.yan36.westerlife.common.network.garage;

import fr.dynamx.addons.basics.BasicsAddon;
import fr.dynamx.addons.basics.common.modules.LicensePlateModule;
import fr.dynamx.common.entities.BaseVehicleEntity;
import fr.dynamx.common.entities.vehicles.CarEntity;
import fr.nathanael2611.simpledatabasemanager.core.Databases;
import fr.yan36.westerlife.common.Util;
import fr.yan36.westerlife.common.blocks.tileentity.TilePark;
import fr.yan36.westerlife.common.capabilities.playergarage.PlayerGarage;
import fr.yan36.westerlife.common.capabilities.playergarage.PlayerGarageCapability;
import fr.yan36.westerlife.common.objects.GarageCar;
import fr.yan36.westerlife.common.utils.carmodule.GarageModule;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class PacketPutCarInGarage implements IMessage {


    BlockPos pos;


    public PacketPutCarInGarage() {
        this.pos = new BlockPos(-1, -1, -1);
    }

    public PacketPutCarInGarage(BlockPos pos) {
        this.pos = pos;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.pos = Util.parseBlockPosFromString(ByteBufUtils.readUTF8String(buf));
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, Objects.requireNonNull(Util.blockPosToString(this.pos)));
    }

    public static class Handler implements IMessageHandler<PacketPutCarInGarage, IMessage> {
        @Override
        public IMessage onMessage(PacketPutCarInGarage m, MessageContext ctx) {
            TilePark tile = (TilePark) ctx.getServerHandler().player.world.getTileEntity(m.pos);
            System.out.println(m.pos);
            if (tile != null) {
                AxisAlignedBB aabb = new AxisAlignedBB(m.pos).grow(2);
                List<BaseVehicleEntity> cars = ctx.getServerHandler().player.world.getEntitiesWithinAABB(BaseVehicleEntity.class, aabb);
                System.out.println("Cars found: " + cars.size());


                EntityPlayer p = ctx.getServerHandler().player;

                if (ctx.getServerHandler().player.hasCapability(PlayerGarageCapability.CAPABILITY, null)) {
                    for (BaseVehicleEntity car : cars) {
                        PlayerGarage garage = (PlayerGarage) ctx.getServerHandler().player.getCapability(PlayerGarageCapability.CAPABILITY, null);
                        GarageModule garageModule = new GarageModule(car);


                        String plate = "§cNo plate";

                        if (car.hasModuleOfType(LicensePlateModule.class)) {
                            LicensePlateModule module = (LicensePlateModule) car.getModuleByType(LicensePlateModule.class);
                            plate = module.getPlate();
                        }

                        if (car.hasModuleOfType(GarageModule.class)) {
                            garageModule.setOwner(ctx.getServerHandler().player.getUniqueID().toString());
                            garage.addCar(new GarageCar(ctx.getServerHandler().player.getUniqueID().toString(), car.getInfoName(), plate, car.getMetadata(), true, car.serializeNBT(), car.getUniqueID().toString()));
                        }



                        ItemStack stack = new ItemStack(BasicsAddon.keysItem);
                        stack.setTagCompound(new NBTTagCompound());
                        stack.getTagCompound().setString("VehicleId", String.valueOf(car.getUniqueID()));
                        stack.getTagCompound().setString("VehicleName", car.getInfoName());

                        if(p.inventory.hasItemStack(stack)){
                            p.inventory.clearMatchingItems(stack.getItem(), -1, 1, stack.getTagCompound());
                        }

                        PlayerGarageCapability.sync(ctx.getServerHandler().player, Collections.singletonList(ctx.getServerHandler().player));
                        car.setDead();
                        car.onRemovedFromWorld();
                    }
                }

            } else {
                ctx.getServerHandler().player.sendMessage(new TextComponentString("§4Error: §cnull"));
            }
            return null;
        }
    }
}