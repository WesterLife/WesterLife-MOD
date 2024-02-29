package fr.gabidut76.westerlife.common.capabilities.playerinventory;

import fr.gabidut76.westerlife.westercore.Main;
import fr.gabidut76.westerlife.common.capabilities.packets.PacketSyncExtraItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.items.ItemStackHandler;

public class ExtraItemContainer extends ItemStackHandler implements IExtraItemHandler {

    private EntityPlayer player;

    public ExtraItemContainer(EntityPlayer player) {
        super(13);
        this.player = player;
    }

    public ExtraItemContainer() {
        super(13);
    }

    @Override
    public void setPlayer(EntityPlayer player) {

    }

    @Override
    protected void onContentsChanged(int slot) {
        if(FMLCommonHandler.instance().getEffectiveSide()== Side.SERVER) { // AHAHAHAHAH TRES DROLE MDR XPTRDDR JE MEURS DE RIRE
            for (int i = 0; i < this.getSlots(); i++) {
                Main.network.sendToAllTracking(new PacketSyncExtraItem(this.player, i, this.getStackInSlot(i)), this.player);
            }
        }
    }
}
