package fr.gabidut76.westerlife.client.renderer;

import com.mrcrayfish.obfuscate.client.event.ModelPlayerEvent;
import fr.gabidut76.westerlife.client.Client;
import fr.gabidut76.westerlife.common.capabilities.playerstat.PlayerStatData;
import fr.gabidut76.westerlife.common.utils.Animation;
import fr.gabidut76.westerlife.westercore.Main;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod.EventBusSubscriber(value = Side.CLIENT, modid = Main.MODID)
public class AnimationRenderer {
    //TODO: Classify the animations
    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void setupPlayerRotations(ModelPlayerEvent.SetupAngles.Pre event) {

        EntityPlayer ep = event.getEntityPlayer();
        ModelBiped modelBiped = event.getModelPlayer();

        Animation animation = Animation.NONE;
        if (event.getEntityPlayer().hasCapability(PlayerStatData.PlayerStatProvider.CAPABILITY, null)) {
            animation = event.getEntityPlayer().getCapability(PlayerStatData.PlayerStatProvider.CAPABILITY, null).getAnimation();
        }


        if (animation == null) {
            System.out.println("Animation is null");
            animation = Animation.NONE;
        }

        if (!animation.equals(Animation.SITTED)) ep.eyeHeight = 1.5f;
        if (animation.equals(Animation.HANDS_UP)) {
            modelBiped.bipedRightArm.rotateAngleX = (float) Math.toRadians(-180);
            modelBiped.bipedLeftArm.rotateAngleX = (float) Math.toRadians(-180);

        }
        if (animation.equals(Animation.POINTING_FINGER)) {
            modelBiped.bipedRightArm.rotateAngleX = (float) Math.toRadians(-90);
        }
        if (animation.equals(Animation.HANDS_BEHIND) || animation.equals(Animation.MENOTTE)) {
            modelBiped.bipedRightArm.rotateAngleX = (float) Math.toRadians(25);
            modelBiped.bipedLeftArm.rotateAngleX = (float) Math.toRadians(25);
            modelBiped.bipedRightArm.rotateAngleZ = (float) Math.toRadians(-25);
            modelBiped.bipedLeftArm.rotateAngleZ = (float) Math.toRadians(25);
        }
        if (animation.equals(Animation.RIGHT_ARM_UP)) {
            modelBiped.bipedRightArm.rotateAngleX = (float) Math.toRadians(-180);
        }
        if (animation.equals(Animation.STAND_AT)) {
            modelBiped.bipedRightArm.rotateAngleX = (float) Math.toRadians(-180);
            modelBiped.bipedRightArm.rotateAngleZ = (float) Math.toRadians(20);
        }
        if (animation.equals(Animation.SITTED)) {
            modelBiped.bipedLeftLeg.rotateAngleX = (float) Math.toRadians(-90);
            modelBiped.bipedRightLeg.rotateAngleX = (float) Math.toRadians(-90);
            modelBiped.bipedRightLeg.offsetY = 0.58f;
            modelBiped.bipedLeftLeg.offsetY = 0.58f;
            modelBiped.bipedRightLeg.offsetZ = 0.04f;
            modelBiped.bipedLeftLeg.offsetZ = 0.04f;
            ep.eyeHeight = 1f;
            modelBiped.bipedHead.offsetY = 0.5f;
            modelBiped.bipedHeadwear.offsetY = 0.5f;
            modelBiped.bipedBody.offsetY = 0.5f;
            modelBiped.bipedRightArm.offsetY = 0.5f;
            modelBiped.bipedLeftArm.offsetY = 0.5f;
        }
        if (animation.equals(Animation.HELLO)) {
            // make animation from -80 to -110 with ep.world.getTotalWorldTime()
            int val = (int) ep.world.getTotalWorldTime() % 20;
            if (val < 10) {
                modelBiped.bipedRightArm.rotateAngleZ = (float) Math.toRadians(160 - (val * 1.5));
            } else {
                modelBiped.bipedRightArm.rotateAngleZ = (float) Math.toRadians(160 + (val * 1.5) - 30);
            }
        }

        if (animation.equals(Animation.SLEEP)) {
            for (ModelRenderer modelRenderer : modelBiped.boxList) {
                modelRenderer.rotateAngleX = (float) Math.toRadians(-90);
            }

            ep.eyeHeight = 0.5f;

            modelBiped.bipedHead.offsetY = 1.5f;
            modelBiped.bipedBody.offsetY = 1.5f;
            modelBiped.bipedLeftArm.offsetY = 1.3f;
            modelBiped.bipedRightArm.offsetY = 1.3f;

            modelBiped.bipedLeftLeg.offsetY = 0.7f;
            modelBiped.bipedRightLeg.offsetY = 0.7f;
            modelBiped.bipedLeftLeg.offsetZ = -0.7f;
            modelBiped.bipedRightLeg.offsetZ = -0.7f;
        }
    }


}
