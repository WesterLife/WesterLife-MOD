package fr.gabidut76.westerlife.common.entities.giraffe;

import com.jme3.math.Vector3f;
import fr.dynamx.api.entities.modules.ModuleListBuilder;
import fr.dynamx.common.entities.ModularPhysicsEntity;
import fr.dynamx.utils.optimization.MutableBoundingBox;
import fr.gabidut76.westerlife.common.entities.DynamX.punchingball.Entity2PhysicsHandler;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;

import java.util.List;

public class EntityGiraffe extends ModularPhysicsEntity<Entity2PhysicsHandler> {


    public EntityGiraffe(World worldIn) {
        super(worldIn);
    }

    public EntityGiraffe(World world, Vector3f pos, float spawnRotationAngle) {
        super(world, pos, spawnRotationAngle);
    }

    @Override
    protected Entity2PhysicsHandler createPhysicsHandler() {
        return null;
    }

    @Override
    protected void createModules(ModuleListBuilder moduleListBuilder) {

    }

    @Override
    protected void fireCreateModulesEvent(Side side) {

    }

    @Override
    public int getSyncTickRate() {
        return 0;
    }

    @Override
    public List<MutableBoundingBox> getCollisionBoxes() {
        return null;
    }

    @Override
    protected void entityInit() {

    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound compound) {

    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound compound) {

    }



}
