package fr.yan36.westerlife.common.objects.entreprises;

import java.util.List;

public interface ICorporation {
    String getSiret();
    void setSiret(String siret);
    List<String> impots();
}
