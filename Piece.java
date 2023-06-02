/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ChessGame;

/**
 *
 * @author rh200
 */
public abstract class Piece {
    
    private int row;
    private int column;
    private PieceColour colour;
    private boolean hasNotMoved;
    private boolean hasMovedOnce;
    private int lastmoveNum;
    private boolean isUnderPin;
    private boolean[][] pinPath = new boolean[8][8];
    
    //a piece constructor
    public Piece(PieceColour colour, int col, int row)
    {
        this.colour = colour;
        this.column = col;
        this.row = row;
        this.hasNotMoved = true;
        this.hasMovedOnce = false;
        this.lastmoveNum = 0;
    }
    
    //return piece's column
    public int getColumn()
    {
        return this.column;
    }
    
    //return piece's row
    public int getRow()
    {
        return this.row;
    }
    
    //set piece's column and row
    public void setColAndRow(int col, int row)
    {
        this.column = col;
        this.row = row;
    }
    
    //return the colour of the piece
    public PieceColour getColour()
    {
        return colour;
    }
    
    //return the opposite colour of the piece
    public PieceColour getOppColour()
    {
        return this.colour.getOppColour();
    }
    
    //return the piece's symbol
    public String getSymbol()
    {
        return "?";
    }
    
    //return the last move number of the piece
    public int getLastMoveNum()
    {
        return this.lastmoveNum;
    }
    
    //set the move number to the piece
    public void setLastMoveNum(int num)
    {
        this.lastmoveNum = num;
    }
    
    //set move has being done
    public void setMove()
    {
        if(hasNotMoved)
        {
            this.hasMovedOnce = true;
        }
        else
        {
            this.hasMovedOnce = false;
        }
        this.hasNotMoved = false;
    }
    
    //return true if the piece has not moved yet
    public boolean hasNotMoved()
    {
        return this.hasNotMoved;
    }
    
    //return true if the piece only moved once
    public boolean hasMovedOnce()
    {
        return this.hasMovedOnce;
    }
    
    //return the path of pin area on the board
    public boolean[][] getPinPath()
    {
        return this.pinPath;
    }
    
    //set the pin path to the piece
    public void setPinPath(boolean[][] pinPath)
    {
        this.pinPath = pinPath;
    }
    
    //set this piece as under pin
    public void setUnderPin(boolean is)
    {
        this.isUnderPin = is;
    }
    
    //return true is the piece is under pin, else false
    public boolean isUnderPin()
    {
        return this.isUnderPin;
    }
    
    //return the available moves of the piece
    public boolean[][] getAvailableMoves()
    {
        boolean[][] availableMoves = new boolean[8][8];
        for(boolean[] i : availableMoves)
        {
            for(boolean j : i)
            {
                j = false;
            }
        }
        return availableMoves;
    }
    
    //return the target area of the piece
    public boolean[][] getTargetArea()
    {
        boolean[][] targetArea = new boolean[8][8];
        for(boolean[] i : targetArea)
        {
            for(boolean j : i)
            {
                j = false;
            }
        }
        return targetArea;
    }
}