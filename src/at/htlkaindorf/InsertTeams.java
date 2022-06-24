package at.htlkaindorf;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;

public class InsertTeams {

    private PreparedStatement preparedInsertteamsStatement = null;
    private final static String preparedInsertTeam = "INSERT INTO teams " +
            "(teamname, spieleranzahl, trainer, kapitaen)" +
            "VALUES (?, ?, ?, ? );";

    public boolean insertTeams(Connection connection){
        try {
            Statement statement = connection.createStatement();
            File file = new File("src/res/teams.csv");    //creates a new file instance
            FileReader fr = new FileReader(file);   //reads the file
            BufferedReader br = new BufferedReader(fr);  //creates a buffering character input stream
            String line;
            while ((line = br.readLine()) != null) {
                //System.out.println(line);
                String[] parts = line.split(",");

                if(preparedInsertteamsStatement == null){
                    preparedInsertteamsStatement = connection.prepareStatement(preparedInsertTeam);
                }
                    /*
                    System.out.println(parts[0]);
                    System.out.println(Integer.parseInt(parts[1]));
                    System.out.println(parts[3]);
                    */
                Team team = new Team(parts[0], Integer.parseInt(parts[1]), parts[2], parts[3]);
                // first ? will be replaced by student.catalog_number
                preparedInsertteamsStatement.setString(1, team.getTeamname());
                preparedInsertteamsStatement.setInt(2, team.getSpieleranzahl());
                preparedInsertteamsStatement.setString(3, team.getTrainer());
                preparedInsertteamsStatement.setString(4, team.getKapitaen());
                try{
                    preparedInsertteamsStatement.executeUpdate();
                } catch(SQLIntegrityConstraintViolationException ex){
                    System.out.println("Team " + team.getTeamname() + " mit dem Kapitaen " + team.getKapitaen() + " existiert bereits");
                }

            }
            statement.close();
        }catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
        return true;
    }

}
