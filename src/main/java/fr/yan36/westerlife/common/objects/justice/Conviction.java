package fr.yan36.westerlife.common.objects.justice;

public class Conviction {

    private String motifs;
    private String date;
    private Punishment punishment;

    public Conviction(String motifs, String date, Punishment punishment) {
        this.motifs = motifs;
        this.date = date;
        this.punishment = punishment;
    }

}
