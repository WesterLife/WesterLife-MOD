package fr.yan36.westerlife.common.entities;

import fr.dynamx.api.entities.modules.ModuleListBuilder;
import fr.dynamx.api.events.PhysicsEntityEvent;
import fr.dynamx.common.DynamXContext;
import fr.dynamx.common.entities.ModularPhysicsEntity;
import fr.dynamx.common.entities.modules.MovableModule;
import fr.dynamx.utils.DynamXConfig;
import fr.dynamx.utils.optimization.MutableBoundingBox;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.relauncher.Side;

import javax.annotation.Nullable;
import java.util.List;

public class TestEntity2 extends ModularPhysicsEntity<Entity2PhysicsHandler> {

    public TestEntity2(World worldIn) {
        super(worldIn);
    }

    @Override
    protected Entity2PhysicsHandler createPhysicsHandler() {
        return new Entity2PhysicsHandler(this);
    }

    @Override
    protected void createModules(ModuleListBuilder moduleListBuilder) {
        this.moduleList.add(new TestEntityModule2(this));
    }

    @Override
    protected void fireCreateModulesEvent(Side side) {
        MinecraftForge.EVENT_BUS.post(new PhysicsEntityEvent.CreateModules<>(TestEntity2.class, this, this.moduleList, side));
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
        if (getModuleByType(TestEntityModule2.class).punchingBag != null) {
            DynamXContext.getPhysicsWorld(world).removeCollisionObject(getModuleByType(TestEntityModule2.class).punchingBag);
        }
    }

    /* tentative désespéré */


    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        return new AxisAlignedBB(-0.5f, -0.5f, -0.5f, 0.5f, 0.5f, 0.5f);
    }

    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox() {
        return new AxisAlignedBB(-0.5f, -0.5f, -0.5f, 0.5f, 0.5f, 0.5f);
    }

    @Nullable
    @Override
    public AxisAlignedBB getCollisionBox(Entity entityIn) {
        return new AxisAlignedBB(-0.5f, -0.5f, -0.5f, 0.5f, 0.5f, 0.5f);
    }

    @Override
    public List<MutableBoundingBox> getCollisionBoxes() {
        List<MutableBoundingBox> list = new java.util.ArrayList<>();
        list.add(new MutableBoundingBox(-0.5f, -0.5f, -0.5f, 0.5f, 0.5f, 0.5f));
        return list;
    }


    @Override
    public AxisAlignedBB getEntityBoundingBox() {
        return new AxisAlignedBB(-0.5f, -0.5f, -0.5f, 0.5f, 0.5f, 0.5f);
    }

    /* fin du merdier */


}
