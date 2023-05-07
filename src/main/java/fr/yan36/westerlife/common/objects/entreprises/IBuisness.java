package fr.yan36.westerlife.common.objects.entreprises;

import fr.yan36.westerlife.common.objects.entreprises.types.BuisnessInfos;
import fr.yan36.westerlife.common.objects.entreprises.types.Rank;

import java.util.List;

public interface IBuisness {
    Float getMinimumCapital();
    List<Rank> getRanks();
    List<String> getImpots();
    List<String> getVehicles();
    Float getCreationCost();
    BuisnessInfos.Type getType();

    void setRanks(List<Rank> ranks);
    void setImpots(List<String> impots);
    void setVehicles(List<String> vehicles);
}
