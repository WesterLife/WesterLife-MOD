package fr.yan36.westerlife.common.entities.npcdomac;

import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.util.math.BlockPos;

public class AIMovePathFinding extends EntityAIBase {

    protected final NPCDomacEntity entity;
    protected BlockPos targetPos;

    public AIMovePathFinding(NPCDomacEntity entity, BlockPos targetPos) {
        this.entity = entity;
        this.targetPos = targetPos;
        setMutexBits(1);

        System.out.println("Searching path to " + targetPos);
    }

    @Override
    public void startExecuting() {
        if(this.entity.getPosition().distanceSq(this.targetPos) > 3) {
            this.entity.getNavigator().tryMoveToXYZ(this.targetPos.getX(), this.targetPos.getY(), this.targetPos.getZ(), .1f);
        }

    }

    public boolean shouldContinueExecuting()
    {
        if(this.entity.getPosition().distanceSq(this.targetPos) < 3) {
            return false;
        }
        return !this.entity.getNavigator().noPath();
    }

    @Override
    public boolean shouldExecute() {
        if(this.entity == null) return false;
        if(this.targetPos == null) return false;
        if(this.entity.getPosition().distanceSq(this.targetPos) < 3) {
            return false;
        }
        return entity.getNavigator().noPath();
    }

    @Override
    public void updateTask() {
    }
}
