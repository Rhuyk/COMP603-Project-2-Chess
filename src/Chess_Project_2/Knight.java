/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Chess_Project_2;

import java.awt.Image;
import javax.swing.ImageIcon;

/**
 *
 * @author rh200
 */
public class Knight extends Piece {
    private boolean[][] availableMoves = new boolean[8][8];
    private boolean[][] targetArea = new boolean[8][8];
    private PiecesOnBoard pieces;
    
    //knight piece constructor
    public Knight(PieceColour colour,int col, int row)
    {
        super(colour, col, row);
    }
    
    //return white knight or black knight symbol
    @Override
    public String getSymbol()
    {
        if (getColour() == PieceColour.WHITE) 
        {
            return "wN";
        } 
        else
        {
            return "bN";
        }
    }
    
    @Override
    public Image getImage()
    {
        String path = "chessPiece/" + getSymbol() + ".png";
        ImageIcon icon = new ImageIcon(path);
        
        return icon.getImage();
    } 
    
    //return knight's available moves (L-patterned move)
    //move can be unavailable due to the board boundary, the same colour pieces, or under pin.
    @Override
    public boolean[][] getAvailableMoves()
    {
        pieces = new PiecesOnBoard();
        int col, row;
        
        for(int i = 0; i < 8; i++)
        {
            for(int j = 0; j < 8; j++)
            {
                availableMoves[i][j] = false;
            }
        }
        
        col = super.getColumn() + 2;
        row = super.getRow() + 1;
        setAvailableMoves(col, row);
                
        col = super.getColumn() + 2;
        row = super.getRow() - 1;
        setAvailableMoves(col, row);
        
        col = super.getColumn() + 1;
        row = super.getRow() + 2;
        setAvailableMoves(col, row);
        
        col = super.getColumn() + 1;
        row = super.getRow() - 2;
        setAvailableMoves(col, row);
        
        col = super.getColumn() - 1;
        row = super.getRow() + 2;
        setAvailableMoves(col, row);
        
        col = super.getColumn() - 1;
        row = super.getRow() - 2;
        setAvailableMoves(col, row);
        
        col = super.getColumn() - 2;
        row = super.getRow() + 1;
        setAvailableMoves(col, row);
        
        col = super.getColumn() - 2;
        row = super.getRow() - 1;
        setAvailableMoves(col, row);
        
        //if knight is under pin, then there is no available move for knight
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

            return newAvailableMoves;
        }
        
        return availableMoves;
    }
    
    //return knight's targeting squares (L-patterned)
    //if knight check the opponent king, send the check path to the PiecesOnBoard class for movement restriction.
    @Override
    public boolean[][] getTargetArea()
    {
        pieces = new PiecesOnBoard();
        int col, row;
        
        for(int i = 0; i < 8; i++)
        {
            for(int j = 0; j < 8; j++)
            {
                targetArea[i][j] = false;
            }
        }
        
        col = super.getColumn() + 2;
        row = super.getRow() + 1;
        setTargetArea(col, row);
                
        col = super.getColumn() + 2;
        row = super.getRow() - 1;
        setTargetArea(col, row);
        
        col = super.getColumn() + 1;
        row = super.getRow() + 2;
        setTargetArea(col, row);
        
        col = super.getColumn() + 1;
        row = super.getRow() - 2;
        setTargetArea(col, row);
        
        col = super.getColumn() - 1;
        row = super.getRow() + 2;
        setTargetArea(col, row);
        
        col = super.getColumn() - 1;
        row = super.getRow() - 2;
        setTargetArea(col, row);
        
        col = super.getColumn() - 2;
        row = super.getRow() + 2;
        setTargetArea(col, row);
        
        col = super.getColumn() - 2;
        row = super.getRow() - 2;
        setTargetArea(col, row);
        
        return targetArea;
    }
    
    //set knight's available squares
    private void setAvailableMoves(int col, int row)
    {
        if(col <= 7 && col >= 0 && row <= 7 && row >= 0)
        {
            if(pieces.getPiece(col, row) == null)
            {
                availableMoves[col][row] = true;
            }
            else
            {
                if(pieces.getPiece(col, row).getColour() != super.getColour())
                {
                    availableMoves[col][row] = true;
                }
            }
        }
    }
    
    //set knight's targeting squares as targeted
    private void setTargetArea(int col, int row)
    {
        if(col <= 7 && col >= 0 && row <= 7 && row >= 0)
        {
            targetArea[col][row] = true;
            
            if(pieces.getPiece(col, row) != null)
            {
                //if knight check the opponent king, send the check path to the PiecesOnBoard class for movement restriction.
                if(pieces.getPiece(col, row).getColour() != super.getColour() && pieces.getPiece(col, row).getSymbol().contains("K"))
                {
                    boolean[][] checkPath = new boolean[8][8];
                    for(int i = 0; i < 8; i++)
                    {
                        for(int j = 0; j < 8; j++)
                        {
                            checkPath[i][j] = false;
                        }
                    }
                    checkPath[super.getColumn()][super.getRow()] = true;
                    pieces.setInCheck(pieces.getPiece(col, row).getColour(), checkPath);
                }
            }
        }
    }
}
