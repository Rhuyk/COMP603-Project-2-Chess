/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Chess_Project_2;

/**
 *
 * @author rh200
 */
public class ChessGameController {
    
    private static PiecesOnBoard board = new PiecesOnBoard();
    
    private static GameDB gameSaver;
    private static GameDB gameSRecorder;
    private static GameDB gameHistory;
    private static GameDB gameHRecorder;
    
    private static Player player1;
    private static Player player2;
    
    public void resetChessGame()
    {
        
    }
    
    public void setPlayers(String player1Name, String player2Name)
    {
        this.player1 = new Player(PieceColour.WHITE, player1Name);
        this.player2 = new Player(PieceColour.BLACK, player2Name);
    }
    
    public static void main(String[] args) 
    {
        gameSaver = new GameSaver();
        gameSRecorder = new GameSaverRecorder();
        gameHistory = new GameHistory();
        gameHRecorder = new GameHistoryRecorder();
        
    }
    
    
    
}