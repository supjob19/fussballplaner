package at.htlkaindorf;

public class Team {

    private String teamname;
    private int spieleranzahl;
    private String trainer;
    private String kapitaen;

    public Team(String teamname, int spieleranzahl, String trainer, String kapitaen) {
        this.teamname = teamname;
        this.spieleranzahl = spieleranzahl;
        this.trainer = trainer;
        this.kapitaen = kapitaen;
    }

    public String getTeamname() {
        return teamname;
    }

    public int getSpieleranzahl() {
        return spieleranzahl;
    }

    public String getTrainer() {
        return trainer;
    }

    public String getKapitaen() {
        return kapitaen;
    }
}
