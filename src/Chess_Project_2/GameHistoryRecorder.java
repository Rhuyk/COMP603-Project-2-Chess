/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Chess_Project_2;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rh200
 */
public final class GameHistoryRecorder extends GameDB {
    
    private Statement statement;
    
    public GameHistoryRecorder() {
        super();
        createTable();
    }
    
    public void createTable() {
        
        String createStatement = "CREATE TABLE GAME_HISTORY_RECORDER (NUMBER INT, MOVE_NUM INT, PIECE_TYPE VARCHAR(2), col INT, row INT)";
        
        try {
            // Check if the table already exists
            DatabaseMetaData metaData = getConn().getMetaData();
            ResultSet resultSet = metaData.getTables(null, null, "GAME_HISTORY_RECORDER", null);
            if (!resultSet.next()) {
                statement = getConn().createStatement();
                statement.execute(createStatement);
                //statement.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(GameHistoryRecorder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void uploadCompletedGame()
    {
        String insertStatement = "INSERT INTO GAME_HISTORY_RECORDER (NUMBER, MOVE_NUM, PIECE_TYPE, col, row) " +
                                 "SELECT NUMBER, MOVE_NUM, PIECE_TYPE, col, row " +
                                 "FROM GAME_SAVER_RECORDER " +
                                 "WHERE NUMBER = 0";
        try {
            if (statement == null) {
                statement = getConn().createStatement();
            }
            statement.executeUpdate(insertStatement);
            //statement.close();

        }
        catch (SQLException ex) {
            Logger.getLogger(GameHistoryRecorder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ResultSet getHistoryGameBoard(int slotNum)
    {
        ResultSet resultset = null;
        String queryStatement = "SELECT MOVE_NUM, PIECE_TYPE, col, row FROM GAME_HISTORY_RECORDER WHERE NUMBER=" + slotNum + " ORDER BY MOVE_NUM ASC";
        
        try {
            if (statement == null) {
                statement = getConn().createStatement();
            }
            resultset = statement.executeQuery(queryStatement);
            //statement.close();
        }
        catch (SQLException ex) {
            Logger.getLogger(GameHistoryRecorder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultset;
    }
}
