package fr.yan36.westerlife.client.gui.other;

import fr.aym.acsguis.component.GuiComponent;
import fr.aym.acsguis.component.panel.GuiPanel;
import fr.dynamx.api.entities.modules.IVehicleController;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public class EngineFailuerController implements IVehicleController {
    @Override
    public void update() {

    }

    @Nullable
    @Override
    public GuiComponent<?> createHud() {
        GuiPanel panel = new GuiPanel();
        panel.setCssId("engine_hud");
        return panel;
    }

    @Nullable
    @Override
    public List<ResourceLocation> getHudCssStyles() {
        return Collections.singletonList(new ResourceLocation("westerlife", "textures/css/hud.css"));
    }
}