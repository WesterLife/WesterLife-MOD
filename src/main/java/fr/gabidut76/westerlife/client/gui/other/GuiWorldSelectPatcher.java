package fr.gabidut76.westerlife.client.gui.other;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;

import java.io.IOException;

public class GuiWorldSelectPatcher extends GuiWorldSelection {
    public GuiWorldSelectPatcher(GuiScreen screenIn) {
        super(screenIn);
    }

    @Override
    public void postInit() {
        System.out.println("OK");
        super.addButton(new GuiButton(1337, 0,0, 150, 20, "NTM"));
        super.postInit();
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if(button.id == 1337) {

            assert Minecraft.getMinecraft().currentScreen != null;
//            mc.displayGuiScreen(new GuiMultiplayer(Main.browserScreen));

        }
        super.actionPerformed(button);
    }
}
