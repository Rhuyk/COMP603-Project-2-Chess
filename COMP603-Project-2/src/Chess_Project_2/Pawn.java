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
public class Pawn extends Piece {
    private boolean[][] availableMoves = new boolean[8][8];
    private boolean[][] targetArea = new boolean[8][8];
    private PiecesOnBoard pieces;
    
    //pawn piece constructor
    public Pawn(PieceColour colour,int col, int row)
    {
        super(colour, col, row);
    }
    
    //return white pawn or black pawn symbol
    @Override
    public String getSymbol()
    {
        if (getColour() == PieceColour.WHITE) 
        {
            return "wP";
        } 
        else
        {
            return "bP";
        }
    }
    
    @Override
    public Image getImage()
    {
        String path = "chessPiece/" + getSymbol() + ".png";
        ImageIcon icon = new ImageIcon(path);
        
        return icon.getImage();
    } 
    
    //return pawn's available moves (one square forward, early two squares advance, or one square diagonally forward for capturing)
    //move can be unavailable due to occupied square by any piece infront, or under pin.
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
        
        //if pawn is white, move row positively
        if(super.getColour() == PieceColour.WHITE)
        {
            //one square forward
            row = super.getRow() + 1;
            if(row <= 7)
            {
                if(pieces.getPiece(super.getColumn(), row) == null)
                {
                    availableMoves[super.getColumn()][row] = true;
                    
                    //two squares advance
                    if(super.hasNotMoved())
                    {
                        if(pieces.getPiece(super.getColumn(), row+1) == null)
                        {
                            availableMoves[super.getColumn()][row+1] = true;
                        }
                    }
                }
            }

            //diagonal capture square
            col = super.getColumn() + 1;
            row = super.getRow() + 1;
            if(col <= 7 && row <= 7)
            {
                if(pieces.getPiece(col, row) != null)
                {
                    if(pieces.getPiece(col, row).getColour() != super.getColour())
                    {
                        availableMoves[col][row] = true;
                    }
                }
            }
            
            //diagonal capture square
            col = super.getColumn() - 1;
            row = super.getRow() + 1;
            if(col >= 0 && row <= 7)
            {
                if(pieces.getPiece(col, row) != null)
                {
                    if(pieces.getPiece(col, row).getColour() != super.getColour())
                    {
                        availableMoves[col][row] = true;
                    }
                }
            }
        }
        
        //if pawn is black, move row negatively
        if(super.getColour() == PieceColour.BLACK)
        {
            //one square forward
            row = super.getRow() - 1;
            if(row >= 0)
            {
                if(pieces.getPiece(super.getColumn(), row) == null)
                {
                    availableMoves[super.getColumn()][row] = true;
                    
                    //two squares advance
                    if(super.hasNotMoved())
                    {
                        if(pieces.getPiece(super.getColumn(), row-1) == null)
                        {
                            availableMoves[super.getColumn()][row-1] = true;
                        }
                    }
                }
            }

            //diagonal capture square
            col = super.getColumn() + 1;
            row = super.getRow() - 1;
            if(col <= 7 && row >= 0)
            {
                if(pieces.getPiece(col, row) != null)
                {
                    if(pieces.getPiece(col, row).getColour() != super.getColour())
                    {
                        availableMoves[col][row] = true;
                    }
                }
            }
            
            //diagonal capture square
            col = super.getColumn() - 1;
            row = super.getRow() - 1;
            if(col >= 0 && row >= 0)
            {
                if(pieces.getPiece(col, row) != null)
                {
                    if(pieces.getPiece(col, row).getColour() != super.getColour())
                    {
                        availableMoves[col][row] = true;
                    }
                }
            }
        }
        
        //if pawn is under pin, then return the available moves within the pin path
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
    
    //return pawn's targeting squares (one square diagonally infront)
    //if pawn check the opponent king, send the check path to the PiecesOnBoard class for movement restriction.
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
        
        //if pawn is white, facing row positively
        if(super.getColour() == PieceColour.WHITE)
        {
            col = super.getColumn() + 1;
            row = super.getRow() + 1;
            setTargetArea(col, row);
            
            col = super.getColumn() - 1;
            row = super.getRow() + 1;
            setTargetArea(col, row);
        }
        
        //if pawn is black, facing row negatively
        if(super.getColour() == PieceColour.BLACK)
        {
            col = super.getColumn() + 1;
            row = super.getRow() - 1;
            setTargetArea(col, row);
            
            col = super.getColumn() - 1;
            row = super.getRow() - 1;
            setTargetArea(col, row);
        }
        
        return targetArea;
    }
    
    //set pawn's targeting squares as targeted
    private void setTargetArea(int col, int row)
    {
        if(col <= 7 && col >= 0 && row <= 7 && row >= 0)
        {
            targetArea[col][row] = true;
            
            if(pieces.getPiece(col, row) != null)
            {
                //if pawn check the opponent king, send the check path to the PiecesOnBoard class for movement restriction.
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