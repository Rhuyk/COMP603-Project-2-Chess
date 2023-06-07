/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Chess_Project_2;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rh200
 */
public class ChessController {
    
    private static PiecesOnBoard board = new PiecesOnBoard();
    
    private static GameSaver gameSaver = new GameSaver();
    private static GameSaverRecorder gameSRecorder = new GameSaverRecorder();
    private static GameHistory gameHistory = new GameHistory();
    private static GameHistoryRecorder gameHRecorder = new GameHistoryRecorder();
    
    private static Player player1;
    private static Player player2;
    
    private PieceColour colourTurn = PieceColour.WHITE;
    
    public static void main(String[] args) {
        
    }
    
    public void setPlayers(String playerWhite, String playerBlack)
    {
        player1 = new Player(PieceColour.WHITE, playerWhite);
        player2 = new Player(PieceColour.BLACK, playerBlack);
    }
    
    public void movePiece(int fromCol, int fromRow, int toCol, int toRow)
    {
        board.movePiece(fromCol, fromRow, toCol, toRow);
        //if promote...
        recordCurrentBoard();
        colourTurn = colourTurn.getOppColour();
    }
    
    public boolean canPromote()
    {
        return board.canPromote();
    }
    
    public void promote(String pieceType)
    {
        board.promote(pieceType);
    }
    
    public Piece[][] getBoard()
    {
        return board.getBoard();
    }
    
    public PieceColour getCurrentColourTurn()
    {
        return colourTurn;
    }
    
    public void startNewGame()
    {
        board.resetBoardAndPieces();
        gameSRecorder.deleteGame(0);
        colourTurn = PieceColour.WHITE;
    }
    
    public void saveGame(int slotNum) //1 to 5
    {
        Date currentDate = new Date();
        java.sql.Date sqlDate = new java.sql.Date(currentDate.getTime());
        gameSaver.saveGame(slotNum, player1.getPlayerName(), player2.getPlayerName(), sqlDate);
        gameSRecorder.saveCurrentGame(slotNum);
    }
    
    private void recordCurrentBoard()
    {
        for(Piece i : board.getPieces().getAllPieces())
        {
            int HNM = 0;
            int HMO = 0;
            if(i.hasNotMoved()) {
                HNM = 1;
            }
            if(i.hasMovedOnce()) {
                HMO = 1;
            }
                
            gameSRecorder.recordCurrentGame(board.getMoveNum(), i.getSymbol(), i.getColumn(), i.getRow(), i.getLastMoveNum(), HNM, HMO);
        }
    }
    
    public void loadSavedGame(int slotNum) //1 to 5
    {
        gameSRecorder.loadSavedGame(slotNum);
        ResultSet resultset = gameSRecorder.getCurrentGameBoard();
        
        try {
            board.setMoveNum(resultset.getInt(1));
            board.clearAllPieces();
            while (resultset.next())
            {
                Piece piece = createPiece(resultset.getString(2), resultset.getInt(3), resultset.getInt(4), resultset.getInt(5), resultset.getInt(6), resultset.getInt(7));
                board.addPiece(piece);
            }
            board.refreshBoard();
            colourTurn = board.getPieces().getCurrentColourTurn();
        } catch (SQLException ex) {
            Logger.getLogger(ChessController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private Piece createPiece(String pieceType, int col, int row, int LMN, int HNM, int HMO)
    {
        Piece piece;
        PieceColour colour;
        boolean HNMboolean;
        boolean HMOboolean;
        
        if (pieceType.contains("w")) {
            colour = PieceColour.WHITE;
        } else {
            colour = PieceColour.BLACK;
        }
        
        if (HNM == 1) {
            HNMboolean = true;
        } else {
            HNMboolean = false;
        }
        if (HMO == 1) {
            HMOboolean = true;
        } else {
            HMOboolean = false;
        }
        
        if (pieceType.contains("P")) {
            piece = new Pawn(colour, col, row, LMN, HNMboolean, HMOboolean);
        } else if (pieceType.contains("Q")) {
            piece = new Queen(colour, col, row, LMN, HNMboolean, HMOboolean);
        } else if (pieceType.contains("B")) {
            piece = new Bishop(colour, col, row, LMN, HNMboolean, HMOboolean);
        } else if (pieceType.contains("R")) {
            piece = new Rook(colour, col, row, LMN, HNMboolean, HMOboolean);
        } else if (pieceType.contains("N")) {
            piece = new Knight(colour, col, row, LMN, HNMboolean, HMOboolean);
        } else {
            piece = new King(colour, col, row, LMN, HNMboolean, HMOboolean);
        }
        
        return piece;
    }
    
}