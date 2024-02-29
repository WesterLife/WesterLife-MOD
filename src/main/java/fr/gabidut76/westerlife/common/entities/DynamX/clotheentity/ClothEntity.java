package fr.gabidut76.westerlife.common.entities.DynamX.clotheentity;

import com.jme3.math.Vector3f;
import fr.dynamx.api.entities.modules.ModuleListBuilder;
import fr.dynamx.api.events.PhysicsEntityEvent;
import fr.dynamx.common.DynamXContext;
import fr.dynamx.common.entities.ModularPhysicsEntity;
import fr.dynamx.utils.DynamXConfig;
import fr.dynamx.utils.optimization.MutableBoundingBox;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.relauncher.Side;

import java.util.ArrayList;
import java.util.List;

public class ClothEntity extends ModularPhysicsEntity<ClothEntityPhysicsHandler> {

    List<MutableBoundingBox> unrotatedBoxes = new ArrayList<>();

    public ClothEntity(World worldIn) {
        super(worldIn);
    }

    @Override
    protected void entityInit() {

    }

    @Override
    protected ClothEntityPhysicsHandler createPhysicsHandler() {
        return new ClothEntityPhysicsHandler(this);
    }

    @Override
    protected void createModules(ModuleListBuilder moduleListBuilder) {
        this.moduleList.add(new ClothEntityModule(this));
    }

    @Override
    protected void fireCreateModulesEvent(Side side) {
        MinecraftForge.EVENT_BUS.post(new PhysicsEntityEvent.CreateModules<>(ClothEntity.class, this, this.moduleList, side));
    }

    @Override
    public int getSyncTickRate() {
        return DynamXConfig.propsSyncTickRate;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

//        System.out.println("ok");
    }

    @Override
    public void setDead() {
        super.setDead();
        if (getModuleByType(ClothEntityModule.class).cloth != null) {
            DynamXContext.getPhysicsWorld(world).removeCollisionObject(getModuleByType(ClothEntityModule.class).cloth);
        }
    }

    @Override
    public boolean attackEntityFrom(DamageSource damageSource, float amount) {
        if (damageSource.getImmediateSource() instanceof EntityPlayer && damageSource.getImmediateSource().isSneaking()) {
            return super.attackEntityFrom(damageSource, amount);
        }
        return false;
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound compound) {

    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound compound) {

    }


    @Override
    public List<MutableBoundingBox> getCollisionBoxes() {
        unrotatedBoxes.clear();
        MutableBoundingBox b = new MutableBoundingBox(new Vec3d(0, 0, 0), new Vec3d(2, -3, 2));
        b.offset(physicsPosition.add(new Vector3f(0, 1, 0)));
        b.offset(-0.5, -3, -0.5);
        unrotatedBoxes.add(b);
        return unrotatedBoxes;
    }
}
