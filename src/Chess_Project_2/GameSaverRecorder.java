/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Chess_Project_2;

import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rh200
 */
public final class GameSaverRecorder extends GameDB {
    
    private Statement statement;
    private int currentGameMoveNum;
    
    public GameSaverRecorder() {
        super();
        createTable();
        currentGameMoveNum = 0;
    }
    
    public void createTable()
    {
        String createStatement = "CREATE TABLE GAME_SAVER_RECORDER (NUMBER INT, MOVE_NUM INT, PIECE_TYPE VARCHAR(2), LOCATION INT, LMN INT, HNM INT, HMO INT)";
        
        try {
            // Check if the table already exists
            DatabaseMetaData metaData = getConn().getMetaData();
            ResultSet resultSet = metaData.getTables(null, null, "GAME_SAVER_RECORDER", null);
            if (!resultSet.next()) {
                statement = getConn().createStatement();
                statement.execute(createStatement);
                statement.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(GameSaverRecorder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //record current game
    public void recordCurrentGame(String pieceType, int location, int LMN, int HNM, int HMO)
    {
        try {
            if (statement == null) {
                statement = getConn().createStatement();
            }
            String insertStatement = "INSERT INTO GAME_SAVER_RECORDER VALUES (0, "+currentGameMoveNum+", '"+pieceType+"', "+location+"', "+LMN+"', "+HNM+"', "+HMO+")";
            statement.executeUpdate(insertStatement);
        } catch (SQLException ex) {
            Logger.getLogger(GameSaverRecorder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //load saved game
    public void loadSavedGame(int slotNum)
    {
        deleteGame(0);
        String insertStatement = "INSERT INTO GAME_SAVER_RECORDER (NUMBER, MOVE_NUM, PIECE_TYPE, LOCATION, LMN, HNM, HMO) " +
                                 "SELECT 0, MOVE_NUM, PIECE_TYPE, LOCATION, LMN, HNM, HMO " +
                                 "FROM GAME_SAVER_RECORDER " +
                                 "WHERE NUMBER = ?";
        
        try {
            PreparedStatement preparedStatement = getConn().prepareStatement(insertStatement);
            preparedStatement.setInt(1, slotNum);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            updateCurrentMoveNum();
        }
        catch (SQLException ex) {
            Logger.getLogger(GameSaverRecorder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //save current game
    public void saveCurrentGame(int slotNum)
    {
        deleteGame(slotNum);
        String insertStatement = "INSERT INTO GAME_SAVER_RECORDER (NUMBER, MOVE_NUM, PIECE_TYPE, LOCATION, LMN, HNM, HMO) " +
                                 "SELECT ?, MOVE_NUM, PIECE_TYPE, LOCATION, LMN, HNM, HMO " +
                                 "FROM GAME_SAVER_RECORDER " +
                                 "WHERE NUMBER = 0";
        
        try {
            PreparedStatement preparedStatement = getConn().prepareStatement(insertStatement);
            preparedStatement.setInt(1, slotNum);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }
        catch (SQLException ex) {
            Logger.getLogger(GameSaverRecorder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //delete game slotNum
    public void deleteGame(int slotNum)
    {
        String deleteStatement = "DELETE FROM GAME_SAVER_RECORDER WHERE NUMBER = " + slotNum;

        try {
            if (statement == null) {
                statement = getConn().createStatement();
            }
            statement.executeUpdate(deleteStatement);
            statement.close();
            if (slotNum == 0) {
                currentGameMoveNum = 0;
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(GameSaverRecorder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public ResultSet getCurrentGameBoard()
    {
        updateCurrentMoveNum();
        ResultSet resultset = null;
        String queryStatement = "SELECT PIECE_TYPE, LOCATION, LMN, HNM, HMO FROM GAME_SAVER_RECORDER WHERE NUMBER=0 AND MOVE_NUM=" + (currentGameMoveNum-1);
        
        try {
            if (statement == null) {
                statement = getConn().createStatement();
            }
            resultset = statement.executeQuery(queryStatement);
            statement.close();
        }
        catch (SQLException ex) {
            Logger.getLogger(GameSaverRecorder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultset;
    }

    private void updateCurrentMoveNum()
    {
        String selectStatement = "SELECT MAX(MOVE_NUM) AS MAX_MOVE_NUM FROM GAME_SAVER_RECORDER WHERE NUMBER = 0";

        try {
            if (statement == null) {
                statement = getConn().createStatement();
            }
            ResultSet resultSet = statement.executeQuery(selectStatement);
            if (resultSet.next()) {
                currentGameMoveNum = resultSet.getInt("MAX_MOVE_NUM") + 1;
            }
            statement.close();
        }
        catch (SQLException ex) {
            Logger.getLogger(GameSaverRecorder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
