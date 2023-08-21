
package fr.yan36.westerlife.client.gui.acs;

import fr.aym.acsguis.component.layout.GuiScaler;
import fr.aym.acsguis.component.panel.GuiFrame;
import fr.aym.acsguis.component.panel.GuiPanel;
import fr.aym.acsguis.event.listeners.mouse.IMouseClickListener;
import fr.yan36.westerlife.Main;
import fr.yan36.westerlife.common.network.PacketAnimation;
import net.minecraft.util.ResourceLocation;

import java.util.Collections;
import java.util.List;

public class AnimationPanel extends GuiPanel {
    private int animation;

    public AnimationPanel(int animation) {
        this.animation = animation;
    }

    public int getAnimation() {
        return animation;
    }

    @Override
    public List<IMouseClickListener> getClickListeners() {
        System.out.println("getclickevent");
        Main.network.sendToServer(new PacketAnimation(animation));
        return super.getClickListeners();
    }
}

        