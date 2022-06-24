package tournamentplan;

import java.util.ArrayList;
import java.util.List;


public class Tester {
    public static void main(String[] args) {
        List<String> teams = new ArrayList<>();
        teams.add("Bayern");
        teams.add("Barca");
        teams.add("Real Madrid");
        teams.add("Atletico Madrid");
        teams.add("Juventus");
        Planner planner = new Planner();
        List<Match> matches = planner.createMatches(teams);
        System.out.println(matches);
    }
}