package at.htlkaindorf;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLIntegrityConstraintViolationException;

public class InsertGoal {
    private PreparedStatement preparedStatement = null;
    private final static String prepareinsertGoal = "INSERT INTO goal"
            + "(pid, treffendesTeam, mid, minute) "
            + "VALUES (? , ? , ? , ? );";

    public boolean addGoal(Connection connection, Goal goal){
        try {
            if(preparedStatement != null){
                preparedStatement = connection.prepareStatement(prepareinsertGoal);
            }
            preparedStatement.setInt(1, goal.getPid());
            preparedStatement.setInt(2, goal.getPid().getTeam());
            preparedStatement.setInt(3, goal.getMid());
            preparedStatement.setInt(4, goal.getMinute());
            try{
                preparedStatement.executeUpdate();
            } catch (SQLIntegrityConstraintViolationException ex) {
                System.out.println("Error in insertGoal");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
