
package fr.gabidut76.westerlife.client.gui.acs;

import fr.aym.acsguis.component.panel.GuiPanel;
import fr.aym.acsguis.event.listeners.mouse.IMouseClickListener;
import fr.gabidut76.westerlife.westercore.Main;
import fr.gabidut76.westerlife.common.network.PacketAnimation;

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

        