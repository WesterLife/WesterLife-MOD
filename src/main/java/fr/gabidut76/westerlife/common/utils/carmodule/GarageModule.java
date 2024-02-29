package fr.gabidut76.westerlife.common.utils.carmodule;

import fr.dynamx.api.entities.modules.IPhysicsModule;
import fr.dynamx.api.network.sync.EntityVariable;
import fr.dynamx.api.network.sync.SynchronizationRules;
import fr.dynamx.api.network.sync.SynchronizedEntityVariable;
import fr.dynamx.common.entities.PackPhysicsEntity;
import fr.dynamx.common.physics.entities.AbstractEntityPhysicsHandler;
import fr.gabidut76.westerlife.westercore.Main;
import net.minecraft.nbt.NBTTagCompound;

@SynchronizedEntityVariable.SynchronizedPhysicsModule(modid = Main.MODID)
public class GarageModule implements IPhysicsModule<AbstractEntityPhysicsHandler<?, ?>>, IPhysicsModule.IEntityUpdateListener {

    private final PackPhysicsEntity<?, ?> entity;


    @SynchronizedEntityVariable(name = "owner")
    private final EntityVariable<String> activate = new EntityVariable<>(SynchronizationRules.PHYSICS_TO_SPECTATORS, "0");


    public GarageModule(PackPhysicsEntity<?, ?> entity) {
        this.entity = entity;
    }





    public String getOwner() {
        return activate.get();
    }

    public void setOwner(String act) {
        this.activate.set(act);
    }


    @Override
    public void writeToNBT(NBTTagCompound tag) {
        tag.setString("owner", activate.get());
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        activate.set(tag.getString("owner"));
    }




}