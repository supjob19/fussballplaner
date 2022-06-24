package at.htlkaindorf;

import java.io.IOException;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {
        try {
            InsertIntoDatabase insertIntoDatabase = new InsertIntoDatabase();
            //insertIntoDatabase.createTable();
            //insertIntoDatabase.insertFromFileTeams();
            insertIntoDatabase.insertFromFilePlayers();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
