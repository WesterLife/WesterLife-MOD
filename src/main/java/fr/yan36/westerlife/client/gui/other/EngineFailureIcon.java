package fr.yan36.westerlife.client.gui.other;

import fr.aym.acsguis.component.GuiComponent;
import fr.aym.acsguis.component.style.AutoStyleHandler;
import fr.aym.acsguis.component.style.ComponentStyleManager;
import fr.aym.acsguis.cssengine.selectors.EnumSelectorContext;
import fr.aym.acsguis.cssengine.style.EnumCssStyleProperties;
import fr.aym.acsguis.utils.GuiTextureSprite;
import fr.dynamx.client.handlers.hud.HudIcons;
import fr.yan36.westerlife.Main;
import net.minecraft.util.ResourceLocation;

import java.util.Collection;
import java.util.Collections;

public class EngineFailureIcon implements HudIcons {

    public EngineFailureIcon() {
    }

    @Override
    public int iconCount() {
        return 1;
    }

    @Override
    public void initIcon(int componentId, GuiComponent<?> component) {
        component.getStyle().addAutoStyleHandler(new AutoStyleHandler<ComponentStyleManager>() {
            @Override
            public boolean handleProperty(EnumCssStyleProperties property, EnumSelectorContext context, ComponentStyleManager target) {
                target.setTexture(new GuiTextureSprite(new ResourceLocation(Main.MODID, "textures/hud/failure.png")));
                return true;
            }
            @Override
            public Collection<EnumCssStyleProperties> getModifiedProperties(ComponentStyleManager target) {
                return Collections.singletonList(EnumCssStyleProperties.TEXTURE);
            }
        });
    }

    @Override
    public void tick(GuiComponent<?>[] guiComponents) {

    }

    @Override
    public boolean isVisible(int i) {
        return true;
    }
}