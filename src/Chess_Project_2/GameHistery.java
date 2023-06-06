/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Chess_Project_2;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rh200
 */
public class GameHistery extends GameDB {
    
    private Statement statement;
    
    public GameHistery() {
        super();
    }
    
    @Override
    public void createTable() {
        
        String createStatement = "CREATE TABLE GAME_HISTORY (NUMBER INT, WHITE VARCHAR(20), BLACK VARCHAR(20), RESULT VARCHAR(1), MOVES INT, DATE DATE)";
        
        try {
            // Check if the table already exists
            DatabaseMetaData metaData = getConn().getMetaData();
            ResultSet resultSet = metaData.getTables(null, null, "GAME_HISTORY", null);
            if (!resultSet.next()) {
                statement = getConn().createStatement();
                statement.execute(createStatement);
                statement.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(GameHistery.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
//    public void uploadCompletedGame(String playerWhite, String playerBlack, String result, int moves, Date date) {
//        
//        String insertStatement = "INSERT INTO GAME_HISTORY VALUES (" + playerWhite + "', '" + playerBlack + "', '" + result + "', " + moves + ", '" + date + "')";
//        updateDB(insertStatement);
//    }
}
