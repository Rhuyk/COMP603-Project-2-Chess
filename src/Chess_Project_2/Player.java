/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Chess_Project_2;

/**
 *
 * @author rh200
 */
public class Player {
    
    private PieceColour colourPiece;
    private String playerName = "player";
    
    public Player(PieceColour colourPiece,String playerName)
    {
        this.playerName = playerName;
        this.colourPiece = colourPiece;
    }

    /**
    * Method: getColourPiece
    * 
    * This method will return the player chess piece 'colour'.
    * 
    * @return 
    */ 
    public PieceColour getColourPiece() 
    {
        return colourPiece;
    }

    /**
    * Method: getColourPiece
    * 
    * This method will modify the player chess piece 'colour'.
    * 
    * @param colourPiece
    */ 
    public void setColourPiece(PieceColour colourPiece) 
    {
        this.colourPiece = colourPiece;
    }

    /**
    * Method: getPlayerName
    * 
    * This method will return the player name.
    * 
    * @return 
    */     
    public String getPlayerName() 
    {
        return playerName;
    }
 
    /**
    * Method: setPlayerName
    * 
    * This method will modify the player name.
    * 
    * @param playerName
    */    
    public void setPlayerName(String playerName) 
    {
        this.playerName = playerName;
    }
}