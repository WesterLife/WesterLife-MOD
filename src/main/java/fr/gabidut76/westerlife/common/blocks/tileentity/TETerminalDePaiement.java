package fr.gabidut76.westerlife.common.blocks.tileentity;

import fr.dynamx.common.contentpack.type.objects.BlockObject;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;

public class TETerminalDePaiement extends TileEntitySyncClient implements ITickable {

    private BlockObject b;

//    private static String status="";
//    private static boolean inPaiement=false;
//    private static Double montant = null;
//    private static String rib = null;
//    private static String nom = null;

    public TETerminalDePaiement(){
        super(null);
    }

    public TETerminalDePaiement(BlockObject<?> blockObjectInfo) {
        super(blockObjectInfo);
        this.b = blockObjectInfo;
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);
//        status = tagCompound.getString("status");
//        inPaiement = tagCompound.getBoolean("inpaiement");
//        montant = tagCompound.getDouble("montant");
//        rib = tagCompound.getString("rib");
//        nom = tagCompound.getString("nom");

    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);
//        tagCompound.setString("status",status);
//        tagCompound.setBoolean("inpaiement",inPaiement);
//        tagCompound.setDouble("montant",montant);
//        tagCompound.setString("rib",rib);
//        tagCompound.setString("nom",nom);
        return tagCompound;
    }

//    public void setStatus(String status) {
//        TETerminalDePaiement.status = status;
//    }
//
//    public static String getStatus() {
//        return status;
//    }
//
//    public void setInPaiement(boolean inPaiement) {
//        TETerminalDePaiement.inPaiement = inPaiement;
//    }
//
//    public static boolean isInPaiement() {
//        return inPaiement;
//    }
//
//    public void setMontant(Double montant) {
//        TETerminalDePaiement.montant = montant;
//    }
//
//    public static Double getMontant() {
//        return montant;
//    }
//
//    public void setRib(String rib) {
//        TETerminalDePaiement.rib = rib;
//    }
//
//    public static String getRib() {
//        return rib;
//    }
//
//    public void setNom(String nom) {
//        TETerminalDePaiement.nom = nom;
//    }
//
//    public static String getNom() {
//        return nom;
//    }

    @Override
    public void update() {
    }
}
