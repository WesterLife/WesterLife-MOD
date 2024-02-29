package fr.gabidut76.westerlife.common.items.renderer;

import com.jme3.math.Vector3f;
import fr.dynamx.api.contentpack.object.part.BasePart;
import fr.dynamx.api.contentpack.object.part.IDrawablePart;
import fr.dynamx.api.contentpack.object.render.IModelPackObject;
import fr.dynamx.api.contentpack.object.subinfo.ISubInfoTypeOwner;
import fr.dynamx.client.renders.scene.SceneBuilder;
import fr.dynamx.client.renders.scene.SceneGraph;

import java.util.List;

public class ItemBurgerPart implements IDrawablePart {


    @Override
    public SceneGraph createSceneGraph(Vector3f vector3f, List list) {
        return null;
    }

    @Override
    public void addToSceneGraph(IModelPackObject packInfo, SceneBuilder sceneBuilder) {
        IDrawablePart.super.addToSceneGraph(packInfo, sceneBuilder);
    }

    @Override
    public String getNodeName() {
        return "burgerpart";
    }

    @Override
    public String getObjectName() {
        return "burgerpart";
    }


}
