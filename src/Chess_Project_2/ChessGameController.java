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
    
    private static GameDB gameSaver = new GameSaver();
    private static GameDB gameSRecorder = new GameSaverRecorder();
    private static GameDB gameHistory = new GameHistory();
    private static GameDB gameHRecorder = new GameHistoryRecorder();
    
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
    
    
    
    
}
