package at.htlkaindorf;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

public class InsertMatchgame {

    private PreparedStatement preparedInsertMatchStatement = null;
    private final static String preparedInsertMatch = "INSERT INTO students "
            + "(teamone, teamtow, ebene) "
            + "VALUES ( ? , ?, ? );";

    public boolean insertMatch(Connection connection, Match match){
        try{
            if(preparedInsertMatchStatement == null){
                preparedInsertMatchStatement = connection.prepareStatement(preparedInsertMatch);
            }

            preparedInsertMatchStatement.setString(1, match.getHomeTeam());
            preparedInsertMatchStatement.setString(2, match.getAwayTeam());
            preparedInsertMatchStatement.setInt(3, match.getEbene());
            try{
                preparedInsertMatchStatement.executeUpdate();
            } catch (SQLIntegrityConstraintViolationException ex) {
                System.out.println("Error in insertMatch");
            }
        } catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
        return true;
    }
}
