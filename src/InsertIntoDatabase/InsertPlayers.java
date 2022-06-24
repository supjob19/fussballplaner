package InsertIntoDatabase;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;

public class InsertPlayers {

    private PreparedStatement preparedInsertPlayersStatement = null;
    private final static String preparedInsertPlayer = "INSERT INTO player " +
            "(playername, teamname, pid)" +
            "VALUES (?, ?, ?);";

    public boolean insertPlayers(Connection connection) {
        try {
            Statement statement = connection.createStatement();
            File file = new File("src/res/players.csv");    //creates a new file instance
            FileReader fr = new FileReader(file);   //reads the file
            BufferedReader br = new BufferedReader(fr);  //creates a buffering character input stream
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                String[] parts = line.split(",");

                if (preparedInsertPlayersStatement == null) {
                    preparedInsertPlayersStatement = connection.prepareStatement(preparedInsertPlayer);
                }
                Player player = new Player(parts[0], parts[1], Integer.parseInt(parts[2]));
                // first ? will be replaced by student.catalog_number
                preparedInsertPlayersStatement.setString(1, player.getName());
                preparedInsertPlayersStatement.setString(2, player.getTeamname());
                preparedInsertPlayersStatement.setInt(3, player.getPid());
                try{
                    preparedInsertPlayersStatement.executeUpdate();
                } catch(SQLIntegrityConstraintViolationException ex){
                    System.out.println("Die SpielerID " + player.getPid() + " existiert bereits");
                }

            }
            statement.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }
}
