package fr.yan36.westerlife.client.phone.util;

import fr.aym.acsguis.component.layout.GuiScaler;
import fr.aym.acsguis.component.panel.GuiFrame;
import fr.aym.acsguis.component.panel.GuiPanel;
import fr.yan36.westerlife.Main;
import fr.yan36.westerlife.common.network.old.PacketOpenPhoneFrame;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;

import java.util.Collections;
import java.util.List;

public class PhoneFrame extends GuiFrame {


    public PhoneFrame() {
        super(new GuiScaler.AdjustToScreenSize(true,1,1));

        GuiPanel screen = new GuiPanel();
        screen.setCssClass("screen");


        add(screen);
    }

    @Override
    public List<ResourceLocation> getCssStyles() {
        return Collections.singletonList(new ResourceLocation("dynamxmod:css/phoneframe.css"));
    }

    /**
     * @apiNote This method is called when the screen is resized. Before calling the super method, you can use the
     */
    public void addElement(GuiPanel pan) {
        add(pan);
    }

    public static void openGui(EntityPlayer e) {
        Main.network.sendTo(new PacketOpenPhoneFrame(), (EntityPlayerMP) e);
    }
}
