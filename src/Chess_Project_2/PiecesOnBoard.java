/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Chess_Project_2;

import java.util.Scanner;

/**
 *
 * @author rh200
 */
public final class PiecesOnBoard {
    private static final Piece[][] board = new Piece[8][8]; // Piece[column][row]
    private static final boolean[][] checkPath = new boolean[8][8];
    private static AllPieces allPieces = new AllPieces();
    private static boolean whiteIsInCheck = false;
    private static boolean blackIsInCheck = false;
    private static int moveNum = 0;
    private static Piece promotionPawn;
    
    //Board Constructor
    public PiecesOnBoard()
    {
        refreshBoard();
    }
    
    //move a piece on the board from square to square
    public boolean movePiece(int fromCol, int fromRow, int toCol, int toRow)
    {
        Piece selectedPiece = board[fromCol][fromRow];
        if(selectedPiece != null)
        {
            refreshPiecesStatus();
            
            //castle move
            if(isCastling(selectedPiece, toCol, toRow))
            {
                castle(selectedPiece, toCol);
            }
            //en passant move
            else if(isEnPassant(selectedPiece, toCol, toRow))
            {
                enPassant(selectedPiece, toCol);
            }
            //king move
            else if(selectedPiece.getSymbol().contains("K") && selectedPiece.getAvailableMoves()[toCol][toRow])
            {
                move(fromCol, fromRow, toCol, toRow);
            }
            //other moves
            else if(checkPath[toCol][toRow] && selectedPiece.getAvailableMoves()[toCol][toRow])
            {
                move(fromCol, fromRow, toCol, toRow);
            }
            //unavailable move
            else
            {
                System.out.println("That was a Illegal move. Please enter a new one!");
                return false;
            }
            
            //pawn promotion
            if(board[toCol][toRow] != null)
            {
                //promote(board[toCol][toRow]);
            }
        }
        return true;
    }
    
    //return the board
    public Piece[][] getBoard()
    {
        return board;
    }
    
    public int getMoveNum()
    {
        return moveNum;
    }
    
    public void setMoveNum(int moveNum)
    {
        this.moveNum = moveNum;
    }
    
    //return piece from a selected square on the board
    public Piece getPiece(int col, int row)
    {
        return allPieces.getPiece(col, row);
    }
    
    public AllPieces getPieces()
    {
        return allPieces;
    }
    
    //add a piece to white or black pieces list
    public void addPiece(Piece piece)
    {
        allPieces.addPiece(piece);
    }
    
    //clear all pieces from all the pieces lists
    public void clearAllPieces()
    {
        allPieces.clearPieces();
    }
    
    //reset the board and the pieces lists
    public void resetBoardAndPieces()
    {
        this.moveNum = 0;
        allPieces = new AllPieces();
        refreshBoard();
    }
    
    //update the board to the pieces lists
    public void refreshBoard()
    {
        for(int col = 0; col < 8; col++) 
        {
            for(int row = 0; row < 8; row++) 
            {
                board[col][row] = null;
            }
        }
        for(Piece i : allPieces.getAllPieces())
        {
            board[i.getColumn()][i.getRow()] = i;
        }
    }
    
    //return an area on the board of a piece checking a king (check path)
    public boolean[][] getCheckPath()
    {
        refreshPiecesStatus();
        return this.checkPath;
    }
    
    //set white or black in check to true, and save the check path.
    public void setInCheck(PieceColour colour, boolean[][] checkPath)
    {
        for(int col = 0; col < 8; col++)
        {
            for(int row = 0; row < 8; row++)
            {
                if(this.checkPath[col][row] && checkPath[col][row]) {
                    this.checkPath[col][row] = true;
                } else {
                    this.checkPath[col][row] = false;
                }
            }
        }
        
        if(colour == PieceColour.WHITE)
        {
            this.whiteIsInCheck = true;
        }
        else if(colour == PieceColour.BLACK)
        {
            this.blackIsInCheck = true;
        }
    }
    
    //return true if white is in check, else false
    public boolean isInCheck(PieceColour colour)
    {
        if (colour == PieceColour.WHITE) {
            return whiteIsInCheck;
        } else {
            return blackIsInCheck;
        }
    }
    
    //return true if a selected colour piece is checkmated, else false
    public boolean isCheckmate(PieceColour colour)
    {
        refreshPiecesStatus();
        boolean isCheckmate = true;
        //no checkmate if any piece other than king can stop the check
        for(Piece i : allPieces.getAllPieces())
        {
            if (i.getColour() == colour)
            {
                for(int col = 0; col < 8; col++)
                {
                    for(int row = 0; row < 8; row++)
                    {
                        if(i.getAvailableMoves()[col][row] && checkPath[col][row] && !i.getSymbol().contains("K"))
                        {
                            isCheckmate = false;
                        }
                    }
                }
            }
        }
        //no checkmate if king still have available move
        for(int col = 0; col < 8; col++)
        {
            for(int row = 0; row < 8; row++)
            {
                if(!allPieces.getTargetAreas(colour.getOppColour())[col][row] 
                        && allPieces.getKing(colour).getAvailableMoves()[col][row])
                {
                    isCheckmate = false;
                }
            }
        }
        return isCheckmate;
    }
    
    //return true if a selected colour piece is stalemated, else false
    public boolean isStalemate(PieceColour colour)
    {
        refreshPiecesStatus();
        boolean isStalemate = true;
        
        //no stalemate if any piece other than king can still move
        for(Piece i : allPieces.getAllPieces())
        {
            if (i.getColour() == colour)
            {
                for(int col = 0; col < 8; col++)
                {
                    for(int row = 0; row < 8; row++)
                    {
                        if(i.getAvailableMoves()[col][row] && !i.getSymbol().contains("K"))
                        {
                            isStalemate = false;
                        }
                    }
                }
            }
        }
        //no stalemate if king can still move
        for(int col = 0; col < 8; col++)
        {
            for(int row = 0; row < 8; row++)
            {
                if(!allPieces.getTargetAreas(colour.getOppColour())[col][row] 
                        && allPieces.getKing(colour).getAvailableMoves()[col][row])
                {
                    isStalemate = false;
                }
            }
        }
        return isStalemate;
    }
    
    //return true is castling is avaialable, else false
    public boolean isCastling(Piece king, int toCol, int toRow)
    {
        boolean availability = false;
        //if king has not moved yet
        if(king.getSymbol().contains("K") && king.hasNotMoved() && (toRow == 0 || toRow == 7))
        {
            //long castle: if the rook has not moved yet and the castle path has no other pieces
            if(toCol == 2 && board[0][king.getRow()] != null && board[0][king.getRow()].hasNotMoved() 
                    && board[1][king.getRow()] == null && board[2][king.getRow()] == null && board[3][king.getRow()] == null)
            {
                availability = true;
                
                //check if king, rook, and their path are not being targeted
                for(int col = 0; col <= 4; col++)
                {
                    if(allPieces.getTargetAreas(king.getColour().getOppColour())[col][0])
                    {
                        availability = false;
                    }
                }
            }
            //short castle: if the rook has not moved yet and the castle path has no other pieces
            else if(toCol == 6 && board[7][king.getRow()] != null && board[7][king.getRow()].hasNotMoved() 
                    && board[5][king.getRow()] == null && board[6][king.getRow()] == null)
            {
                availability = true;
                for(int col = 7; col >= 4; col--)
                {
                    //check if king, rook, and their path are not being targeted
                    if(allPieces.getTargetAreas(king.getColour().getOppColour())[col][0])
                    {
                        availability = false;
                    }
                }
            }
        }
        return availability;
    }
    
    //perform castling
    private void castle(Piece king, int toCol)
    {
        int col = king.getColumn();
        int row = king.getRow();
        int toRow;
        
        if (king.getColour() == PieceColour.WHITE) {
            toRow = 0;
        } else {
            toRow = 7;
        }
        
        moveNum++;
        allPieces.getPiece(col, row).setLastMoveNum(moveNum);
        allPieces.getPiece(col, row).setMove();
        allPieces.getPiece(col, row).setColAndRow(toCol, toRow);

        if(toCol == 2) //long castle
        {
            allPieces.getPiece(0, toRow).setMove();
            allPieces.getPiece(0, toRow).setColAndRow(3, toRow);
        }
        else if(toCol == 6) //short castle
        {
            allPieces.getPiece(7, toRow).setMove();
            allPieces.getPiece(7, toRow).setColAndRow(5, toRow);
        }
        refreshBoard();
    }
    
    //return true if en passant move is available, else false
    public boolean isEnPassant(Piece pawn, int toCol, int toRow)
    {
        boolean availability = false;
        //if the target square if not empty
        if(board[toCol][pawn.getRow()] != null && (toRow == 5 || toRow == 2))
        {
            /* if selected piece is white pawn at row 5
             * and the target piece is black pawn
             * and that black pawn advanced two squares in the previous move
             */
            if(pawn.getSymbol().equals("wP") && pawn.getRow() == 4 
                    && board[toCol][pawn.getRow()].getSymbol().equals("bP") 
                    && board[toCol][pawn.getRow()].getLastMoveNum() == this.moveNum 
                    && board[toCol][pawn.getRow()].hasMovedOnce())
            {
                availability = true;
            }
            /* if selected piece is black pawn at row 4
             * and the target piece is white pawn
             * and that white pawn advanced two squares in the previous move
             */
            else if(pawn.getSymbol().equals("bP") && pawn.getRow() == 3 
                    && board[toCol][pawn.getRow()].getSymbol().equals("wP") 
                    && board[toCol][pawn.getRow()].getLastMoveNum() == this.moveNum 
                    && board[toCol][pawn.getRow()].hasMovedOnce())
            {
                availability = true;
            }
        }
        
        return availability;
    }
    
    //perform en passant move
    private void enPassant(Piece pawn, int toCol)
    {
        int col = pawn.getColumn();
        int row = pawn.getRow();
        int toRow;
        
        if (pawn.getColour() == PieceColour.WHITE) {
            toRow = 5;
        } else {
            toRow = 2;
        }
        
        moveNum++;
        allPieces.getPiece(col, row).setLastMoveNum(moveNum);
        allPieces.getPiece(col, row).setMove();
        allPieces.removePiece(toCol, row);
        allPieces.getPiece(col, row).setColAndRow(toCol, toRow);
        refreshBoard();
    }
    
//    private void promote(Piece pawn)
//    {
//        boolean availability = false;
//        int col = pawn.getColumn();
//        int row = pawn.getRow();
//        
//        if(pawn.getSymbol().contains("P") && (pawn.getRow() == 7 || pawn.getRow() == 0))
//        {
//            
//        }
//        
//        return availability;
//    }
//    
//    //promote pawn to other piece
//    public void promote(Piece pawn)
//    {
//        int col = pawn.getColumn();
//        int row = pawn.getRow();
//        Scanner scanner = new Scanner(System.in);
//        
//        //if white pawn reach the end of the board
//        if(pawn.getSymbol().contains("P") && (pawn.getRow() == 7 || pawn.getRow() == 0))
//        {
//            Piece promotion;
//            System.out.println("Please enter your promotion: ");
//            String userInput = scanner.nextLine();
//            
//            //player select promotion piece
//            if(userInput.equalsIgnoreCase("queen"))
//            {
//                promotion = new Queen(pawn.getColour(), col, row);
//                allPieces.replacePiece(promotion, col, row);
//            }
//            else if(userInput.equalsIgnoreCase("bishop"))
//            {
//                promotion = new Bishop(pawn.getColour(), col, row);
//                allPieces.replacePiece(promotion, col, row);
//            }
//            else if(userInput.equalsIgnoreCase("knight"))
//            {
//                promotion = new Knight(pawn.getColour(), col, row);
//                allPieces.replacePiece(promotion, col, row);
//            }
//            else if(userInput.equalsIgnoreCase("rook"))
//            {
//                promotion = new Rook(pawn.getColour(), col, row);
//                allPieces.replacePiece(promotion, col, row);
//            }   
//        }
//        refreshBoard();
//    }
    
    private void move(int fromCol, int fromRow, int toCol, int toRow)
    {
        moveNum++;
        allPieces.getPiece(fromCol, fromRow).setLastMoveNum(moveNum);
        allPieces.getPiece(fromCol, fromRow).setMove();
        allPieces.removePiece(toCol, toRow);
        allPieces.getPiece(fromCol, fromRow).setColAndRow(toCol, toRow);
        refreshBoard();
    }
    
    //set isInCheck, isUnderPin, and checkPath back to default then update again
    private void refreshPiecesStatus()
    {
        whiteIsInCheck = false;
        blackIsInCheck = false;
        allPieces.resetUnderPin();
        for(int col = 0; col < 8; col++)
        {
            for(int row = 0; row < 8; row++)
            {
                checkPath[col][row] = true;
            }
        }
        allPieces.getTargetAreas(PieceColour.WHITE);
        allPieces.getTargetAreas(PieceColour.BLACK);
    }
}
