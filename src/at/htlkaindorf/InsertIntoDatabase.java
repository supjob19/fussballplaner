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

    public void disconnect() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println("DB Access disconnection failed");
            }
        }
    }

    public boolean createDB() {
        String sqlStringDelete = "DROP DATABASE IF EXISTS fussballplaner WITH (FORCE)";
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

    public boolean createTable() {
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
        while (resultSet.next()) {
            String teamname = resultSet.getString("teamname");
            int spieleranzahl = resultSet.getInt("spieleranzahl");
            String trainer = resultSet.getString("trainer");
            String kapitaen = resultSet.getString("kapitaen");
            result.add(new Team(teamname, spieleranzahl, trainer, kapitaen));
        }
        return result;
    }

    public List<Player> getPlayers() throws SQLException {
        List<Player> result = new ArrayList<>();

        Statement statement = connection.createStatement();

        String sqlString = "SELECT * FROM teams";

        ResultSet resultSet = statement.executeQuery(sqlString);
        while (resultSet.next()) {
            String playername = resultSet.getString("playername");
            int pid = resultSet.getInt("pid");
            String teamname = resultSet.getString("teamname");
            result.add(new Player(teamname, playername, pid));
        }
        return result;
    }

    //todo Functions überarbeiten wenn Klassen Goal und Match im Projekt sind
    /*
    public List<Goal> getGoals() throws SQLException {
        List<Goal> result = new ArrayList<>();

        Statement statement = connection.createStatement();

        String sqlString = "SELECT * FROM teams";

        ResultSet resultSet = statement.executeQuery(sqlString);
        while (resultSet.next()) {
            String playername = resultSet.getString("playername");
            int pid = resultSet.getInt("pid");
            String teamname = resultSet.getString("teamname");
            result.add(new Player(teamname, playername, pid));
        }
        return result;
    }

    public List<Match> getMatches() throws SQLException {
        List<Player> result = new ArrayList<>();

        Statement statement = connection.createStatement();

        String sqlString = "SELECT * FROM teams";

        ResultSet resultSet = statement.executeQuery(sqlString);
        while (resultSet.next()) {
            String playername = resultSet.getString("playername");
            int pid = resultSet.getInt("pid");
            String teamname = resultSet.getString("teamname");
            result.add(new Player(teamname, playername, pid));
        }
        return result;
    }
    */
    public boolean insertMatchgame(Match match){
        InsertMatchgame im = new InsertMatchgame();
        return im.insertMatch(connection, match);
    }

    public boolean insertFromFileTeams() {
        InsertTeams it = new InsertTeams();
        return it.insertTeams(connection);
    }

    public boolean insertFromFilePlayers() {
        InsertPlayers ip = new InsertPlayers();
        return ip.insertPlayers(connection);
    }

}
