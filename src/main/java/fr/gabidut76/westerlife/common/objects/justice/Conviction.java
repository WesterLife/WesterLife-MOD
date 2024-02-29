package fr.gabidut76.westerlife.common.objects.justice;

import fr.gabidut76.westerlife.common.objects.IDatabaseVariable;

import java.util.ArrayList;
import java.util.List;

public class Conviction implements IDatabaseVariable {

    /**
     * @see java.util.UUID#toString()
     * */
    private String associated_user;
    private String motifs;
    private String date;
    private List<Punishment> punishment;


    public Conviction(String associated_user, String motifs, String date, List<Punishment> punishment) {
        this.associated_user = associated_user;
        this.motifs = motifs;
        this.date = date;
        this.punishment = punishment;
    }

    @Override
    public String toString() {
        // list to string with ;
        StringBuilder punishments = new StringBuilder();
        for (Punishment punishment : punishment) {
            punishments.append(punishment.toString()).append(";");
        }
        return associated_user + " ;" + motifs + ";" + date + ";" + punishments;
    }

    public Conviction parseFromString(String s) {
        String[] split = s.split(";");
        List<Punishment> ps = new ArrayList<>();
        for (String punishment : split[3].split("!")) {
            ps.add(Punishment.fromString(punishment));
        }
        return new Conviction(split[0], split[1], split[2], ps);
    }

    @Override
    public String tableName() {
        return "convictions";
    }

    @Override
    public List<String> getValues() {
        List<String> vars = new ArrayList<>();
        vars.add(associated_user);
        vars.add(motifs);
        vars.add(date);
        StringBuilder punishments = new StringBuilder();
        for (Punishment punishment : punishment) {
            punishments.append(punishment.toString()).append(";");
        }
        vars.add(punishments.toString());
        vars.add(IDatabaseVariable.ID_ROW);

        return vars;
    }

    @Override
    public RowDetails getIDRow() {
        return new RowDetails("id", true, false);
    }

    public String getAssociated_user() {
        return associated_user;
    }

    public String getMotifs() {
        return motifs;
    }

    public String getDate() {
        return date;
    }

    public List<Punishment> getPunishment() {
        return punishment;
    }
}
