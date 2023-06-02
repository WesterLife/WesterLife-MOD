package fr.yan36.westerlife.client.renderer;

import fr.dynamx.client.renders.model.ModelObjArmor;
import fr.dynamx.common.contentpack.DynamXObjectLoaders;
import fr.nathanael2611.simpledatabasemanager.client.ClientDatabases;
import fr.nathanael2611.simpledatabasemanager.core.DatabaseReadOnly;
import fr.yan36.westerlife.common.Util;
import fr.yan36.westerlife.common.init.DynamxInit;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.player.EntityPlayer;

import java.util.Objects;

import static org.lwjgl.opengl.GL11.*;

public class LayerArmorSuperposition implements LayerRenderer<EntityPlayer> {
    private final RenderPlayer renderer;
    public LayerArmorSuperposition(RenderPlayer renderer) {
        System.out.println("Called here");
        this.renderer = renderer;
    }

    @Override
    public void doRenderLayer(EntityPlayer entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        DatabaseReadOnly db = ClientDatabases.getDatabase("westerlife_armorsuperposition");
        if(db.getString(entitylivingbaseIn.getUniqueID().toString()) == null) return;
        for (String s : db.getString(entitylivingbaseIn.getUniqueID().toString()).split(",")) {
            if(s == null || s.equals("")) continue;
            else {
                if(DynamxInit.fastRegistryAccess.get(s.split("!")[0]) == null) {
                    System.out.println("Armor " + s + " is currently null (unable to find it in the fast registry access)");
                    continue;
                }
                glPushMatrix();
                glMatrixMode(GL_MODELVIEW);
                String[] split = s.split("!");
                ModelObjArmor r = Objects.requireNonNull(DynamXObjectLoaders.ARMORS.findInfo(split[0])).getObjArmor();
//                System.out.println("Rendering " + s + " on " + entitylivingbaseIn.getUniqueID().toString());
                r.setModelAttributes(renderer.getMainModel());
                byte state = Byte.parseByte(split[1]);
                System.out.println(state);
                r.setActivePart(Util.equipementFromSlotID(Integer.parseInt(split[2])), state);
                r.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
                glPopMatrix();
            }

        }



    }

    @Override
    public boolean shouldCombineTextures() {
        return false;
    }


}
