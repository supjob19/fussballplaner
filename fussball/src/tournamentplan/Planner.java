package tournamentplan;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Planner {

    DBAccess access = new DBAccess;

    private PreparedStatement preparedInsertMatchStatement = null;
    private final static String preparedInsertMatch = "INSERT INTO students "
            + "(heimmanschaft, aussärtsmanschaft, ebene) "
            + "VALUES ( ? , ?, ? );";

    public void addMatch(Match match) throws SQLException {
        if(preparedInsertMatchStatement == null){
            preparedInsertMatchStatement = connection.prepareStatement(preparedInsertMatch);
        }

        preparedInsertMatchStatement.setString(1, match.getHomeTeam());
        preparedInsertMatchStatement.setString(2, match.getAwayTeam());
        preparedInsertMatchStatement.setString(3, match.get());
        preparedInsertMatchStatement.executeUpdate();
    }

    public List<Match> createMatches(List<String> teams){
        ArrayList<Match> matches = new ArrayList<>();
        while (teams.size() >= 2){
            Random random = new Random();
            int first = random.nextInt(teams.size());
            int second;
            do {
                second = random.nextInt(teams.size());
            }while(first == second);
            Match match = new Match(teams.get(first), teams.get(second));
            matches.add(match);
            teams.remove(teams.get(first));
            int secInd = second;
            if (first < second){
                secInd--;
            }
            teams.remove(teams.get(secInd));
        }

        if (teams.size() == 1){
            matches.add(new Match(teams.get(0), null));
        }

        return matches;
    }
}
