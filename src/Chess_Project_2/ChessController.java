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
    
    private static PiecesOnBoard board;
    private static GameSaver gameSaver;
    private static GameSaverRecorder gameSRecorder;
    private static GameHistory gameHistory;
    private static GameHistoryRecorder gameHRecorder;
    private static Player player1;
    private static Player player2;
    private PieceColour colourTurn;

    public ChessController()
    {
        board = new PiecesOnBoard();
        gameSaver = new GameSaver();
        gameSRecorder = new GameSaverRecorder();
        gameHistory = new GameHistory();
        gameHRecorder = new GameHistoryRecorder();
        colourTurn = PieceColour.WHITE;
    }
    
    public Piece[][] getBoard()
    {
        return board.getBoard();
    }
    
    public PieceColour getCurrentColourTurn()
    {
        return colourTurn;
    }
    
    public void setPlayers(String playerWhite, String playerBlack)
    {
        player1 = new Player(PieceColour.WHITE, playerWhite);
        player2 = new Player(PieceColour.BLACK, playerBlack);
    }
    
    public void movePiece(int fromCol, int fromRow, int toCol, int toRow)
    {
        board.movePiece(fromCol, fromRow, toCol, toRow);
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
    
    public boolean gameEnded()
    {
        if (board.isCheckmate(colourTurn) || board.isStalemate(colourTurn))
        {
            gameHistory.uploadCompletedGame(player1.getName(), player2.getName(), getGameResult(colourTurn, false), board.getMoveNum(), getCurrentDate());
            gameHRecorder.uploadCompletedGame();
            return true;
        }
        return false;
    }
    
    public void resignGame()
    {
        gameHistory.uploadCompletedGame(player1.getName(), player2.getName(), getGameResult(colourTurn, true), board.getMoveNum(), getCurrentDate());
        gameHRecorder.uploadCompletedGame();
    }
    
    public void drawGame()
    {
        gameHistory.uploadCompletedGame(player1.getName(), player2.getName(), "DD", board.getMoveNum(), getCurrentDate());
        gameHRecorder.uploadCompletedGame();
    }
    
    public ResultSet getGameHistoryInfo(int slotNum)
    {
        return gameHistory.getHistoryGameInfo(slotNum);
    }
    
    public void setHistoryGameBoard(int slotNum, int moveNum)
    {
        ResultSet resultSet = gameHRecorder.getHistoryGameBoard(slotNum);
        
        try {
            board.clearAllPieces();
            while(resultSet.next())
            {
                if (resultSet.getInt("MOVE_NUM") == moveNum)
                {
                    Piece piece = createPiece(resultSet.getString(3), resultSet.getInt(4), resultSet.getInt(5), 0, 0, 0);
                    board.addPiece(piece);
                }
            }
            board.refreshBoard();
        }
        catch (SQLException ex) {
            Logger.getLogger(ChessController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void startNewGame()
    {
        board.resetBoardAndPieces();
        gameSRecorder.deleteGame(0);
        recordCurrentBoard();
        colourTurn = PieceColour.WHITE;
    }
    
    public void saveGame(int slotNum) //1 to 5
    {
        gameSaver.saveGame(slotNum, player1.getName(), player2.getName(), getCurrentDate());
        gameSRecorder.saveCurrentGame(slotNum);
    }
    
    public ResultSet getSavedGameInfo(int slotNum)
    {
        return gameSaver.getSavedGameInfo(slotNum);
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
        }
        catch (SQLException ex) {
            Logger.getLogger(ChessController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void quit()
    {
        gameSaver.closeConnections();
        gameSRecorder.closeConnections();
        gameHistory.closeConnections();
        gameHRecorder.closeConnections();
    }
    
    private java.sql.Date getCurrentDate()
    {
        Date currentDate = new Date();
        java.sql.Date sqlDate = new java.sql.Date(currentDate.getTime());
        return sqlDate;
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
    
    private String getGameResult(PieceColour colourTurn, boolean manual)
    {
        String gameResult = "";
        if (board.isCheckmate(colourTurn) || manual)
        {
            if (colourTurn == PieceColour.BLACK) {
                gameResult = "wW";
            } else {
                gameResult = "bW";
            }
        }
        else if (board.isStalemate(colourTurn))
        {
            gameResult = "DD";
        }
        return gameResult;
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