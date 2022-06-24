package at.htlkaindorf;

import java.io.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class InsertIntoDatabase {
    /*
        Teamname/Vereinsname
        Anzahl Spieler
        Trainer
        (in meiner Badewanne bin ich) Kapitän
     */

        private Connection connection;

        private String db_url;
        private String db_username;
        private String db_password;
        private String db_driver;


        private PreparedStatement preparedInsertteamsStatement = null;
        private final static String preparedInsertTeam = "INSERT INTO teams " +
                "(teamname, spieleranzahl, trainer, kapitaen)" +
                "VALUES (?, ?, ?, ? );";

    private PreparedStatement preparedInsertPlayersStatement = null;
    private final static String preparedInsertPlayer = "INSERT INTO player " +
            "(playername, teamname, pid)" +
            "VALUES (?, ?, ?);";

        public InsertIntoDatabase() throws SQLException, ClassNotFoundException, IOException {
            loadProperties();
            //Class.forName(db_driver);
            connect();
        }

        private void connect() throws SQLException {
            disconnect();
            connection = DriverManager.getConnection(db_url, db_username, db_password);
        }

        private void loadProperties() throws IOException {
            String path = System.getProperty("user.dir")
                    + File.separator + "src"
                    + File.separator + "res"
                    + File.separator + "db.properties";

            FileInputStream fileInputStream = new FileInputStream(path);
            Properties properties = new Properties();
            properties.load(fileInputStream);

            this.db_url = properties.getProperty("url");
            this.db_driver = properties.getProperty("driver");
            this.db_username = properties.getProperty("user");
            this.db_password = properties.getProperty("passwd");
        }

        public void disconnect(){
            if(connection != null){
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println("DB Access disconnection failed");
                }
            }
        }
        public boolean createDB(){
            String sqlStringDelete ="DROP DATABASE IF EXISTS fussballplaner WITH (FORCE)";
            String sqlString = "CREATE DATABASE fussballplaner";

            Statement statement;
            try {
                statement = connection.createStatement();
                statement.executeUpdate(sqlStringDelete);
                statement.executeUpdate(sqlString);
                statement.close();
            } catch (SQLException e) {
                //System.out.println(e.getMessage());
                return false;
            }
            return true;
        }

        public boolean createTable(){
            String sqlStringDelete = "DROP TABLE IF EXISTS teams";

            String sqlString = "" +
                    "CREATE TABLE teams (" +
                    "teamname VARCHAR(255) PRIMARY KEY," +
                    "spieleranzahl INTEGER NOT NULL," +
                    "trainer VARCHAR(255) NOT NULL," +
                    "kapitaen VARCHAR(255) NOT NULL," +
                    "CONSTRAINT nodupl UNIQUE (teamname, kapitaen) " +
                    ")";
            try {
                Statement statement = connection.createStatement();
                statement.executeUpdate(sqlStringDelete);
                statement.executeUpdate(sqlString);
                statement.close();
            } catch (SQLException e) {
                //System.out.println(e.getMessage());
                return false;
            }
            return true;
        }

        public List<Team> getTeams() throws SQLException {
            List<Team> result = new ArrayList<>();

            Statement statement = connection.createStatement();

            String sqlString = "SELECT * FROM teams";

            ResultSet resultSet = statement.executeQuery(sqlString);
            while (resultSet.next()){
                String teamname = resultSet.getString("teamname");
                int spieleranzahl = resultSet.getInt("spieleranzahl");
                String trainer = resultSet.getString("trainer");
                String kapitaen = resultSet.getString("kapitaen");
                result.add(new Team(teamname, spieleranzahl, trainer, kapitaen));
            }
            return result;
        }

        public boolean insertFromFileTeams(){
            try {
                Statement statement = connection.createStatement();
                File file = new File("src/res/teams.csv");    //creates a new file instance
                FileReader fr = new FileReader(file);   //reads the file
                BufferedReader br = new BufferedReader(fr);  //creates a buffering character input stream
                String line;
                while ((line = br.readLine()) != null) {
                    System.out.println(line);
                    String[] parts = line.split(",");

                    if(preparedInsertteamsStatement == null){
                        preparedInsertteamsStatement = connection.prepareStatement(preparedInsertTeam);
                    }
                    System.out.println(parts[0]);
                    System.out.println(Integer.parseInt(parts[1]));
                    System.out.println(parts[3]);
                    Team team = new Team(parts[0], Integer.parseInt(parts[1]), parts[2], parts[3]);
                    // first ? will be replaced by student.catalog_number
                    preparedInsertteamsStatement.setString(1, team.getTeamname());
                    preparedInsertteamsStatement.setInt(2, team.getSpieleranzahl());
                    preparedInsertteamsStatement.setString(3, team.getTrainer());
                    preparedInsertteamsStatement.setString(4, team.getKapitaen());
                    preparedInsertteamsStatement.executeUpdate();
                }
                statement.close();
            }catch (Exception ex){
                ex.printStackTrace();
                return false;
            }
            return true;
        }

    public boolean insertFromFilePlayers(){
        try {
            Statement statement = connection.createStatement();
            File file = new File("src/res/players.csv");    //creates a new file instance
            FileReader fr = new FileReader(file);   //reads the file
            BufferedReader br = new BufferedReader(fr);  //creates a buffering character input stream
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                String[] parts = line.split(",");

                if(preparedInsertPlayersStatement == null){
                    preparedInsertPlayersStatement = connection.prepareStatement(preparedInsertPlayer);
                }
                Player player = new Player(parts[1], parts[0], Integer.parseInt(parts[2]));
                // first ? will be replaced by student.catalog_number
                preparedInsertPlayersStatement.setString(1, player.getName());
                preparedInsertPlayersStatement.setString(2, player.getTeamname());
                preparedInsertPlayersStatement.setInt(3, player.getPid());

                preparedInsertPlayersStatement.executeUpdate();
            }
            statement.close();
        }catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
        return true;
    }
}
