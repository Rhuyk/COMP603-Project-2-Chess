/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Chess_Project_2;

import java.sql.Connection;
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
public class GameSaver extends GameDB {
    
    private Statement statement;
    
    public GameSaver() {
        super();
    }
    
    @Override
    public void createTable() {
        String createStatement = "CREATE TABLE GAME_SAVER (NUMBER INT, WHITE VARCHAR(20), BLACK VARCHAR(20), DATE DATE)";
        
        try {
            // Check if the table already exists
            DatabaseMetaData metaData = getConn().getMetaData();
            ResultSet resultSet = metaData.getTables(null, null, "GAME_SAVER", null);
            if (!resultSet.next()) {
                statement.executeUpdate(createStatement);
            }
        } catch (SQLException ex) {
            Logger.getLogger(GameSaver.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void insertContent(int num, String playerWhite, String playerBlack, Date date) {
        String insertStatement = "INSERT INTO GAME_SAVER VALUES ("+num+", '"+playerWhite+"', '"+playerBlack+"', '"+date+"')";
    
        try {
            statement.executeUpdate(insertStatement);
        } catch (SQLException ex) {
            Logger.getLogger(GameSaver.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
