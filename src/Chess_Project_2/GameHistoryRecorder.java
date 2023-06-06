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
public class GameHistoryRecorder extends GameDB {
    
    private Statement statement;
    
    public GameHistoryRecorder() {
        super();
    }
    
    @Override
    public void createTable() {
        
        String createStatement = "CREATE TABLE GAME_HISTORY_RECORDER (NUMBER INT, MOVE_NUM INT, PIECE_TYPE VARCHAR(2), LOCATION INT)";
        
        try {
            // Check if the table already exists
            DatabaseMetaData metaData = getConn().getMetaData();
            ResultSet resultSet = metaData.getTables(null, null, "GAME_HISTORY_RECORDER", null);
            if (!resultSet.next()) {
                statement = getConn().createStatement();
                statement.execute(createStatement);
                statement.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(GameHistoryRecorder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
//    public void insertContent(int num, int moveNum, String pieceType, int location) {
//        
//        String insertStatement = "INSERT INTO GAME_HISTORY_RECORDER VALUES ("+num+", "+moveNum+", '"+pieceType+"', "+location+")";
//        updateDB(insertStatement);
//    }
}
