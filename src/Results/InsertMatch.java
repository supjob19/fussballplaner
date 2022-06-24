package Results;

import tournamentplan.Match;

public class InsertMatch {

    public void insertMatch(Match match, int homeScore, int awayScore){
        match.setHomeResult(homeScore);
        match.setAwayResult(awayScore);
    }
}
