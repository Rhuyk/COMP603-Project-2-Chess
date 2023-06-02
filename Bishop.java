/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ChessGame;

/**
 *
 * @author rh200
 */
public class Bishop extends Piece{
    private boolean[][] availableMoves = new boolean[8][8];
    private boolean[][] targetArea = new boolean[8][8];
    private PiecesOnBoard pieces;
    
    //bishop piece constructor
    public Bishop(PieceColour colour,int col, int row)
    {
        super(colour, col, row);
    }
    
    //return white bishop or black bishop symbol
    @Override
    public String getSymbol()
    {
        if (getColour() == PieceColour.WHITE) 
        {
            return "wB";
        } 
        else
        {
            return "bB";
        }
    }
    
    //return bishop's available moves (diagonally)
    //move can be unavailable due to the board boundary, the same colour pieces, or under pin.
    @Override
    public boolean[][] getAvailableMoves()
    {
        pieces = new PiecesOnBoard();
        int col;
        int row;
        
        for(int i = 0; i < 8; i++)
        {
            for(int j = 0; j < 8; j++)
            {
                availableMoves[i][j] = false;
            }
        }
        
        col = super.getColumn() +1;
        row = super.getRow() +1;
        setAvailableMoves(col, row);
        
        col = super.getColumn() +1;
        row = super.getRow() -1;
        setAvailableMoves(col, row);
        
        col = super.getColumn() -1;
        row = super.getRow() +1;
        setAvailableMoves(col, row);
        
        col = super.getColumn() -1;
        row = super.getRow() -1;
        setAvailableMoves(col, row);
        
        //if bishop is under pin, then return the available moves within the pin path
        if(super.isUnderPin())
        {
            boolean[][] newAvailableMoves = new boolean[8][8];
            for(int i = 0; i < 8; i++)
            {
                for(int j = 0; j < 8; j++)
                {
                    newAvailableMoves[i][j] = false;
                }
            }
            
            for(col = 0; col < 8; col++)
            {
                for(row = 0; row < 8; row++)
                {
                    if(super.getPinPath()[col][row] && availableMoves[col][row])
                    {
                        newAvailableMoves[col][row] = true;
                    }
                }
            }
            
            return newAvailableMoves;
        }
        
        return availableMoves;
    }
    
    //return bishop's targeting squares (diagonally)
    //if bishop pin the opponemt king, send the pin path to the piece that is under the pin and set its isUnderPin to true.
    //if bishop check the opponent king, send the check path to the PiecesOnBoard class for movement restriction.
    @Override
    public boolean[][] getTargetArea()
    {
        pieces = new PiecesOnBoard();
        int col;
        int row;
        
        for(int i = 0; i < 8; i++)
        {
            for(int j = 0; j < 8; j++)
            {
                targetArea[i][j] = false;
            }
        }
        
        col = super.getColumn() +1;
        row = super.getRow() +1;
        setTargetArea(col, row);
        
        col = super.getColumn() +1;
        row = super.getRow() -1;
        setTargetArea(col, row);
        
        col = super.getColumn() -1;
        row = super.getRow() +1;
        setTargetArea(col, row);
        
        col = super.getColumn() -1;
        row = super.getRow() -1;
        setTargetArea(col, row);
        
        return targetArea;
    }
    
    //return a further column away from the bishop
    private int setColUpOrDown(int col)
    {
        if((col - super.getColumn()) > 0)
        {
            col++;
        }
        else if((col - super.getColumn()) < 0)
        {
            col--;
        }
        
        return col;
    }
    
    //return a further row away from the bishop
    private int setRowUpOrDown(int row)
    {
        if((row - super.getRow()) > 0)
        {
            row++;
        }
        else if((row - super.getRow()) < 0)
        {
            row--;
        }
        return row;
    }
        
    //set bishop's available squares
    private void setAvailableMoves(int col, int row)
    {
        while(col <= 7 && col >= 0 && row <= 7 && row >= 0)
        {
            if(pieces.getPiece(col, row) == null)
            {
                availableMoves[col][row] = true;
                col = setColUpOrDown(col);
                row = setRowUpOrDown(row);
            }
            else
            {
                if(pieces.getPiece(col, row).getColour() != super.getColour())
                {
                    availableMoves[col][row] = true;
                }
                break;
            }
        }
    }
    
    //set bishop's targeting squares as targeted
    private void setTargetArea(int col, int row)
    {
        while(col <= 7 && col >= 0 && row <= 7 && row >= 0)
        {
            if(pieces.getPiece(col, row) != null)
            {
                targetArea[col][row] = true;
                
                //if bishop check the opponent king, send the check path to the PiecesOnBoard class for movement restriction.
                if(pieces.getPiece(col, row).getColour() != super.getColour() 
                && pieces.getPiece(col, row).getSymbol().contains("K"))
                {
                    boolean[][] checkPath = new boolean[8][8];
                    for(int i = 0; i < 8; i++)
                    {
                        for(int j = 0; j < 8; j++)
                        {
                            checkPath[i][j] = false;
                        }
                    }
                    int pathCol = super.getColumn();
                    int pathRow = super.getRow();
                    while(!(pathCol == col && pathRow == row))
                    {
                        checkPath[pathCol][pathRow] = true;
                        if(pathCol < col)
                        {
                            pathCol++;
                        }
                        else if(pathCol > col)
                        {
                            pathCol--;
                        }
                        if(pathRow < row)
                        {
                            pathRow++;
                        }
                        else if(pathRow > row)
                        {
                            pathRow--;
                        }
                    }
                    pieces.setInCheck(pieces.getPiece(col, row).getColour(), checkPath);
                }
                
                //if bishop pin the opponemt king, send the pin path to the piece that is under pin and set its isUnderPin to true.
                else if(pieces.getPiece(col, row).getColour() != super.getColour())
                {
                    int pinnedCol = col;
                    int pinnedRow = row;
                    col = setColUpOrDown(col);
                    row = setRowUpOrDown(row);
                    
                    while(col <= 7 && col >= 0 && row <= 7 && row >= 0)
                    {
                        if(pieces.getPiece(col, row) == null)
                        {
                            col = setColUpOrDown(col);
                            row = setRowUpOrDown(row);
                            continue;
                        }
                        else if(pieces.getPiece(col, row).getColour() != super.getColour() 
                                && pieces.getPiece(col, row).getSymbol().contains("K"))
                        {
                            pieces.getPiece(pinnedCol, pinnedRow).setUnderPin(true);
                            
                            boolean[][] pinPath = new boolean[8][8];
                            for(int i = 0; i < 8; i++)
                            {
                                for(int j = 0; j < 8; j++)
                                {
                                    pinPath[i][j] = false;
                                }
                            }
                            int pathCol = super.getColumn();
                            int pathRow = super.getRow();
                            while(!(pathCol == col && pathRow == row))
                            {
                                pinPath[pathCol][pathRow] = true;
                                if(pathCol < col)
                                {
                                    pathCol++;
                                }
                                else if(pathCol > col)
                                {
                                    pathCol--;
                                }
                                if(pathRow < row)
                                {
                                    pathRow++;
                                }
                                else if(pathRow > row)
                                {
                                    pathRow--;
                                }
                            }
                            pieces.getPiece(pinnedCol, pinnedRow).setPinPath(pinPath);
                            break;
                        }
                        else
                        {
                            break;
                        }
                    }
                }
                break;
            }
            targetArea[col][row] = true;
            col = setColUpOrDown(col);
            row = setRowUpOrDown(row);
        }
    }
}