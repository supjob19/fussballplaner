package tournamentplan;

public class Match {
    private String homeTeam;
    private String awayTeam;
    private int homeResult;
    private int awayResult;
    private int ebene;

    public Match(String homeTeam, String awayTeam) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public void setHomeTeam(String homeTeam) {
        this.homeTeam = homeTeam;
    }

    public void setAwayTeam(String awayTeam) {
        this.awayTeam = awayTeam;
    }

    public int getHomeResult() {
        return homeResult;
    }

    public void setHomeResult(int homeResult) {
        this.homeResult = homeResult;
    }

    public int getAwayResult() {
        return awayResult;
    }

    public void setAwayResult(int awayResult) {
        this.awayResult = awayResult;
    }

    public int getEbene() {
        return ebene;
    }

    public void setEbene(int ebene) {
        this.ebene = ebene;
    }

    @Override
    public String toString() {
        return "Match{" +
                "homeTeam='" + homeTeam + '\'' +
                ", awayTeam='" + awayTeam + '\'' +
                '}';
    }
}
