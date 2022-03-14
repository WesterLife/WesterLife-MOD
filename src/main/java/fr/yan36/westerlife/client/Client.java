package fr.yan36.westerlife.client;


import fr.yan36.westerlife.client.gui.CSSGuiCreateProfil;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Client {
    public static int create = 0;
    @SubscribeEvent
    public void GuieventHandler(GuiOpenEvent e) {

        if(e.getGui() instanceof GuiMainMenu){
            //e.setGui(new CSSGuiMainMenu().getGuiScreen());
        }
        if (e.getGui() instanceof GuiIngameMenu) {
        }
        if (e.getGui() == null) {
            if(create == 1){
                e.setGui(new CSSGuiCreateProfil().getGuiScreen());
            }
        }

    }

    @SubscribeEvent
    public void InteractWithEntity(PlayerInteractEvent.EntityInteract e) {
        e.getEntityPlayer().sendMessage(new TextComponentString(""+Profil.getNom()));
    }
}
