package fr.yan36.westerlife.common.network;

import fr.dynamx.addons.basics.common.modules.LicensePlateModule;
import fr.dynamx.common.entities.BaseVehicleEntity;
import fr.nathanael2611.simpledatabasemanager.core.Databases;
import fr.yan36.westerlife.client.Client;
import fr.yan36.westerlife.common.Util;
import fr.yan36.westerlife.common.blocks.tileentity.TileGarage;
import fr.yan36.westerlife.common.blocks.tileentity.TilePark;
import fr.yan36.westerlife.common.objects.GarageCar;
import fr.yan36.westerlife.common.utils.carmodule.AICarEngineModule;
import fr.yan36.westerlife.common.utils.carmodule.GarageModule;
import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.Objects;

import static fr.yan36.westerlife.client.Client.needToCreateCharacter;

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
                for (BaseVehicleEntity car : cars) {
                    System.out.println("Car found: " + car.getInfoName());
                    GarageModule garageModule = new GarageModule(car);


                    if(garageModule.getOwner().equals("0")) {
                        garageModule.setOwner(ctx.getServerHandler().player.getUniqueID().toString());
                        ctx.getServerHandler().player.sendMessage(new TextComponentString("§6Warning: §eCar is now yours"));
                    }

                    LicensePlateModule licensePlateModule = (LicensePlateModule) car.getModuleByType(LicensePlateModule.class);

                    GarageCar garageCar = new GarageCar(garageModule.getOwner(), car.getInfoName(), licensePlateModule.getPlate(), car.getEntityTextureID());


                    Databases.getPlayerData(ctx.getServerHandler().player).setInteger("garageSize", Databases.getPlayerData(ctx.getServerHandler().player).getInteger("garageSize") + 1);
                    Databases.getPlayerData(ctx.getServerHandler().player).setString("garage_" + Databases.getPlayerData(ctx.getServerHandler().player).getInteger("garageSize"), garageCar.toString());

                    car.setDead();
                }
            } else {
                ctx.getServerHandler().player.sendMessage(new TextComponentString("§4Error: §cTile is null"));
            }
            return null;
        }
    }
}