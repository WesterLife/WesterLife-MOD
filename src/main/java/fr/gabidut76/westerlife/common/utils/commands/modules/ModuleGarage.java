 package fr.gabidut76.westerlife.common.utils.commands.modules;

import com.jme3.math.Vector3f;
import fr.dynamx.addons.basics.BasicsAddon;
import fr.dynamx.addons.basics.common.modules.BasicsAddonModule;
import fr.dynamx.addons.basics.common.modules.LicensePlateModule;
import fr.dynamx.common.contentpack.DynamXObjectLoaders;
import fr.dynamx.common.entities.vehicles.CarEntity;
import fr.gabidut76.westerlife.common.utils.commands.CommandModule;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;

import javax.annotation.Nullable;
import java.util.*;

import static net.minecraft.command.CommandBase.getListOfStringsMatchingLastWord;

public class ModuleGarage extends CommandModule {
    public ModuleGarage() {
        super("garage");
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) {
        if(args.length == 1){
            sender.sendMessage(new TextComponentString("§c/permis <add|remove|list|spawncar|printcars>"));
            return;
        }
        if (Objects.equals(args[1], "printcars")) {
            DynamXObjectLoaders.WHEELED_VEHICLES.getInfos().forEach((s, dynamXVehicleInfo) -> {
                sender.sendMessage(new TextComponentString("§a" + s + " §7- §e" + dynamXVehicleInfo.getFullName() + " - " + dynamXVehicleInfo.getName()));
            });
        }
        if (Objects.equals(args[1], "spawncar")) {

            EntityPlayer player = (EntityPlayer) sender;
            CarEntity<?> car = new CarEntity(args[2], sender.getEntityWorld(), new Vector3f(sender.getPosition().getX(), sender.getPosition().getY(), sender.getPosition().getZ()), player.cameraYaw, args[4].isEmpty() ? 0 : Byte.parseByte(args[4]));

            car.setInitCallback((modularPhysicsEntity, modules) -> {
                System.out.println(car.getModules());
                LicensePlateModule module = modularPhysicsEntity.getModuleByType(LicensePlateModule.class);
                module.setPlate(args[3].replaceAll("_", " "));


                BasicsAddonModule module1 = modularPhysicsEntity.getModuleByType(BasicsAddonModule.class);
                module1.setHasLinkedKey(true);
                module1.setLocked(true);


                ItemStack itemStack = new ItemStack(BasicsAddon.keysItem);

                itemStack.setTagCompound(new NBTTagCompound());
                assert itemStack.getTagCompound() != null;
                itemStack.getTagCompound().setString("VehicleId", car.getUniqueID().toString());
                itemStack.getTagCompound().setString("VehicleName", args[2]);
                itemStack.setStackDisplayName("§eClé du véhicule " + args[3].replaceAll("_", " "));

                player.inventory.addItemStackToInventory(itemStack);
                player.inventoryContainer.detectAndSendChanges();

            });


            sender.getEntityWorld().spawnEntity(car);
        }

    }

    @Override
    public List<String> getTabCompletion(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        return null;
    }
}
