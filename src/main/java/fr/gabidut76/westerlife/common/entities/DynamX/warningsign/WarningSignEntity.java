package fr.gabidut76.westerlife.common.entities.DynamX.warningsign;

import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.math.Vector3f;
import fr.dynamx.api.entities.modules.ModuleListBuilder;
import fr.dynamx.api.events.PhysicsEntityEvent;
import fr.dynamx.api.physics.BulletShapeType;
import fr.dynamx.common.DynamXContext;
import fr.dynamx.common.entities.ModularPhysicsEntity;
import fr.dynamx.utils.DynamXConfig;
import fr.dynamx.utils.optimization.MutableBoundingBox;
import fr.gabidut76.westerlife.common.init.DynamXInit;
import fr.gabidut76.westerlife.common.init.ItemInit;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.relauncher.Side;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class WarningSignEntity extends ModularPhysicsEntity<WarningSignPhysicsHandler> {

    List<MutableBoundingBox> unrotatedBoxes = new ArrayList<>();
    float rotation = 0;

    public WarningSignEntity(World worldIn, float spawnRot) {
        super(worldIn, new Vector3f(0,0,0), spawnRot);
        this.rotation = spawnRot;
        setRotation(spawnRot, 0);
    }

    public WarningSignEntity(World worldIn) {
        super(worldIn);
    }

    @Override
    protected void entityInit() {

    }

    @Override
    protected WarningSignPhysicsHandler createPhysicsHandler() {
        return new WarningSignPhysicsHandler(this);
    }

    @Override
    protected void createModules(ModuleListBuilder moduleListBuilder) {
        this.moduleList.add(new WarningSignEntityModule(this));
    }

    @Override
    protected void fireCreateModulesEvent(Side side) {
        MinecraftForge.EVENT_BUS.post(new PhysicsEntityEvent.CreateModules<>(WarningSignEntity.class, this, this.moduleList, side));
        WarningSignEntityModule module = getModuleByType(WarningSignEntityModule.class);
    }

    @Override
    public int getSyncTickRate() {
        return DynamXConfig.propsSyncTickRate;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
    }

    @Override
    public void onCollisionEnter(PhysicsCollisionEvent collisionEvent, BulletShapeType<?> entityA, BulletShapeType<?> entityB) {
        super.onCollisionEnter(collisionEvent, entityA, entityB);
    }

    @Override
    public void setDead() {
        super.setDead();
        if (getModuleByType(WarningSignEntityModule.class).punchingBag != null) {
            DynamXContext.getPhysicsWorld(world).removeCollisionObject(getModuleByType(WarningSignEntityModule.class).punchingBag);
        }
    }

    @Override
    public boolean attackEntityFrom(DamageSource damageSource, float amount) {
        if (damageSource.getImmediateSource() instanceof EntityPlayer && ((EntityPlayer) damageSource.getImmediateSource()).isCreative() || damageSource == DamageSource.OUT_OF_WORLD || damageSource.getImmediateSource().isSneaking()) {
            if(!world.isRemote) {
                if(Objects.requireNonNull(damageSource.getImmediateSource()).isSneaking()) {
                    this.entityDropItem(new ItemStack(ItemInit.panneaucirculation), 0.5f);
                    this.setDead();
                }
            }
            return super.attackEntityFrom(damageSource, amount);
        }
        return false;
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound compound) {
        super.func_70037_a(compound);
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound compound) {
        super.func_70014_b(compound);
    }


    @Override
    public List<MutableBoundingBox> getCollisionBoxes() {
        unrotatedBoxes.clear();
        MutableBoundingBox b = new MutableBoundingBox(new Vec3d(0, 0, 0), new Vec3d(2, 3, 2));
        b.offset(physicsPosition.add(new Vector3f(0, 1, 0)));
        b.offset(-0.5, 3, -0.5);
        unrotatedBoxes.add(b);
        return unrotatedBoxes;
    }
}
