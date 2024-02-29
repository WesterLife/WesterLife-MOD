package fr.gabidut76.westerlife.client.renderer;

import fr.dynamx.client.renders.model.ModelObjArmor;
import fr.dynamx.common.contentpack.DynamXObjectLoaders;
import fr.dynamx.common.items.DynamXItemArmor;
import fr.gabidut76.westerlife.common.capabilities.playerinventory.ExtraItemCapability;
import fr.gabidut76.westerlife.common.capabilities.playerinventory.ExtraItemContainer;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

import java.util.HashMap;
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
        if (entitylivingbaseIn.hasCapability(ExtraItemCapability.CAPABILITY, null)) {
            ExtraItemContainer container = (ExtraItemContainer) entitylivingbaseIn.getCapability(ExtraItemCapability.CAPABILITY, null);

            HashMap<Integer, EntityEquipmentSlot> slots = new HashMap<>(
                    new HashMap<Integer, EntityEquipmentSlot>() {{
                        put(0, EntityEquipmentSlot.HEAD);
                        put(1, EntityEquipmentSlot.CHEST);
                        put(2, EntityEquipmentSlot.LEGS);
                        put(3, EntityEquipmentSlot.FEET);

                        put(4, EntityEquipmentSlot.CHEST);
                        put(5, EntityEquipmentSlot.LEGS);

                        put(6, EntityEquipmentSlot.CHEST);
                        put(7, EntityEquipmentSlot.LEGS);

                        put(8, EntityEquipmentSlot.CHEST);
                    }}
            );

            slots.forEach((slot, slotType) -> {
                assert container != null;
                ItemStack stack = container.getStackInSlot(slot);

                if (stack.getItem() instanceof DynamXItemArmor<?>) {
                    DynamXItemArmor<?> armor = (DynamXItemArmor<?>) stack.getItem();
                    glPushMatrix();
                    glMatrixMode(GL_MODELVIEW);

                    ModelObjArmor r = Objects.requireNonNull(DynamXObjectLoaders.ARMORS.findInfo(armor.getInfo().getFullName())).getObjArmor();
                    r.setModelAttributes(renderer.getMainModel());
                    byte state = armor.getInfo().getObjArmor().getActiveTextureId();
                    r.setActivePart(slotType, state);
                    r.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
                    glPopMatrix();
                }
            });

        }


    }

    @Override
    public boolean shouldCombineTextures() {
        return false;
    }


}
