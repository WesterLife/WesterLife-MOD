package fr.yan36.westerlife.common.utils.carmodule.listeners;

import fr.aym.acsguis.component.panel.GuiFrame;
import fr.aym.acsguis.component.panel.GuiPanel;
import fr.aym.acsguis.utils.GuiTextureSprite;
import fr.dynamx.api.events.VehicleEntityEvent;
import fr.dynamx.client.handlers.hud.CarController;
import fr.dynamx.common.entities.BaseVehicleEntity;
import fr.dynamx.common.entities.PackPhysicsEntity;
import fr.dynamx.common.entities.vehicles.CarEntity;
import fr.yan36.westerlife.Main;
import fr.yan36.westerlife.common.handlers.SoundsHandler;
import fr.yan36.westerlife.common.utils.carmodule.DamageCarModule;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.server.SPacketCustomSound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

import java.util.HashMap;

@Mod.EventBusSubscriber(modid = Main.MODID, value = Side.CLIENT)
public class DamageCarsListener {

    public static HashMap<PackPhysicsEntity<?, ?>, Boolean> engineStarted = new HashMap<>();

    @SubscribeEvent
    public static void updateVehicleController(VehicleEntityEvent.ControllerUpdate event) {
        if (event.getController() instanceof CarController) {
            DamageCarModule module = event.getEntity().getModuleByType(DamageCarModule.class);
            if (module != null) {
                if(engineStarted.containsKey(event.getEntity())) {
                    if(engineStarted.get(event.getEntity()) != ((CarController) event.getController()).isEngineStarted()) {
                        if(((CarController) event.getController()).isEngineStarted()) {
                            System.out.println(module.getPercentage());
                            if(module.getPercentage() >= 5) {
                                System.out.println("Engine should't start");

                                BlockPos loc = event.getEntity().getPosition();

                                Minecraft.getMinecraft().player.world.playSound(loc.getX(), loc.getY(), loc.getZ(), SoundsHandler.CARALARM, SoundCategory.MASTER, 1, 1, false);
                                ((CarController) event.getController()).setEngineStarted(false);
                                ((CarController) event.getController()).setEngineStarted(false);
                            }
                        }
                    }
                    engineStarted.put(event.getEntity(), ((CarController) event.getController()).isEngineStarted());
                } else {
                    System.out.println("Car added");
                    engineStarted.put(event.getEntity(), ((CarController) event.getController()).isEngineStarted());
                }
            }
        }
    }

    @SubscribeEvent
    public static void playerQuitCar(VehicleEntityEvent.EntityDismount e) {
        if(e.getEntity() instanceof CarEntity) {
            engineStarted.remove(e.getEntity());
            Minecraft.getMinecraft().getSoundHandler().stop("westerlife:caralarm", SoundCategory.MASTER);
        }
    }

    @SubscribeEvent
    public static void drawHUD(VehicleEntityEvent.CreateHud event) {
        event.getStyleSheets().add(new ResourceLocation(Main.MODID, "acsgui/carhud.css"));
        DamageCarModule module = event.getEntity().getModuleByType(DamageCarModule.class);
        if(module == null) return;

        if(event.isPlayerDriving() && module.getPercentage() >= 5) {
            GuiPanel panel = new GuiPanel();
            panel.getStyle().setTexture(new GuiTextureSprite(new ResourceLocation(Main.MODID, "textures/hud/enginefail.png"),0,0,128,128));
            panel.setCssClass("enginefail");
            event.getVehicleHud().add(panel);
        }


    }
}
