/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Chess_Project_2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author pj
 */
public class ChessPanel extends JPanel {
    private static final int BOARD_SIZE = 8;
    private static final int CELL_SIZE = 63;
    private PiecesOnBoard board;
    private boolean[][] availableMoves;
    private int selectedRow = -1;
    private int selectedCol = -1;
    private Player player1;
    private Player player2;
    private Player currentPlayer;
    private boolean whiteTurn;
    
    private int currentRow;
    private int rowChange;
    private boolean flipFlag = false;
    private String moves;
    private ChessFrame chessFrame;
    
    public ChessPanel(ChessFrame frame) 
    {
        chessFrame = frame;
        moves = "";
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(513, 514));
        board = new PiecesOnBoard();
        availableMoves = new boolean[8][8];
        whiteTurn = true;
        
        addMouseListener();
    }

    private void addMouseListener() {
        addMouseListener(new MouseAdapter() {
            Piece selectedPiece = null;
            @Override
            public void mousePressed(MouseEvent e) 
            {
                if (getPlayer1() != null && getPlayer2() != null) 
                {
                    int col = e.getX() / CELL_SIZE;
                    int row = (BOARD_SIZE - 1) - e.getY() / CELL_SIZE;
                    if (flipFlag) 
                    {
                        row = (BOARD_SIZE - 1) - row;
                    }
                    if (col >= 0 && col < BOARD_SIZE && row >= 0 && row < BOARD_SIZE) 
                    {
                        Piece clickedPiece = getBoard().getBoard()[col][row];
                        if(selectedPiece == null) 
                        {
                            if (clickedPiece != null && clickedPiece.getColour() == getCurrentPlayer().getColourPiece()) 
                            {
                                selectedPiece = clickedPiece;
                                selectedRow = row;
                                selectedCol = col;
                                availableMoves = selectedPiece.getAvailableMoves();
                            }
                        } else {
                            if(availableMoves[col][row]) 
                            {
                                if(selectedPiece.getColour() == getCurrentPlayer().getColourPiece() && getBoard().movePiece(selectedCol, selectedRow, col, row)) 
                                {
                                    setWhiteTurn(!isWhiteTurn());
                                    moves = "(" + selectedPiece.getSymbol() + ")" + String.format(" %s%d, %s%d%n", (char)('a' + selectedCol), selectedRow + 1, (char)('a' + col), row + 1);
                                    if(chessFrame != null)
                                    {
                                        chessFrame.updateMovesTextArea();
                                    }
                                }
                                
                            }

                            selectedPiece = null;
                            selectedRow = -1;
                            selectedCol = -1;
                            availableMoves = new boolean[8][8];
                        }

                        repaint();
                    }
                    if(!whiteTurn) 
                    {
                        currentPlayer = player2;
                    }
                    else
                    {
                        currentPlayer = player1;
                    }
                    
                    if(!checkForCheckmate())
                    {
                        checkForStalement();
                    }
                }
            }
        });
    }
    
    public boolean checkForCheckmate() 
    {
        if (board.isCheckmate(getCurrentPlayer().getColourPiece())) 
        {
            JOptionPane.showMessageDialog(ChessPanel.this, "This Chess Game has ended via Checkmate! Game over.");
            chessFrame.switchTab(4);
            return true;
        }
        return false;
    }
    
    public void checkForStalement()
    {
        if(board.isStalemate(getCurrentPlayer().getColourPiece()))
        {
            JOptionPane.showMessageDialog(ChessPanel.this, "This Chess Game is a Stalement! Game over.");
        }
    }

    @Override
    protected void paintComponent(Graphics g) 
    {
        super.paintComponent(g);
        
        drawChessBoard(g);
        
        int frameWidth = 5; 
        int boardWidth = BOARD_SIZE * CELL_SIZE;
        int boardHeight = BOARD_SIZE * CELL_SIZE;

        // Draw the frame
        g.setColor(Color.BLACK);
        g.fillRect(CELL_SIZE - frameWidth - 59, CELL_SIZE - frameWidth - 58, frameWidth, boardHeight + 2 * frameWidth); // LEFT
        g.fillRect(CELL_SIZE - frameWidth - 59, CELL_SIZE - frameWidth - 58, boardWidth + 2 * frameWidth, frameWidth); // TOP
        g.fillRect(CELL_SIZE - frameWidth - 59, CELL_SIZE + boardHeight  - 58, boardWidth + 2 * frameWidth, frameWidth);
        g.fillRect(CELL_SIZE + boardWidth - 59, CELL_SIZE - frameWidth - 58, frameWidth, boardHeight + 2 * frameWidth);
//        if (player1 == null || player2 == null) // Check if player has logged in
//        {
//            g.setColor(new Color(0, 0, 0, 200));
//            g.fillRect(CELL_SIZE - 59, CELL_SIZE - 58, BOARD_SIZE * CELL_SIZE, BOARD_SIZE * CELL_SIZE);
//            
//        }
        
        if(availableMoves != null)
        {
            drawAvailableMoves(g);
        }
        
    }

    private void drawChessBoard(Graphics g) 
    {
        currentRow = 7;
        rowChange = -1;
        if(flipFlag)
        {
            currentRow = 0;
            rowChange = 1;
        }
        boolean flag = true;
        for (int row = 0; row < 8; row++) 
        {
            for (int col = 0; col < 8; col++) 
            {
                int x = col * CELL_SIZE;
                int y = row * CELL_SIZE;
                x += 4;
                y += 5;
                Piece piece = getBoard().getBoard()[col][currentRow];

                if ((row + col) % 2 == 0) 
                {
                    g.setColor(Color.WHITE);
                } 
                else 
                {
                    g.setColor(Color.LIGHT_GRAY);

                }
                
                g.fillRect(x, y, CELL_SIZE, CELL_SIZE);
                
                if(piece != null)
                {
                    g.drawImage(piece.getImage(), x + 8, y + 7, null);
                }

                if (row == BOARD_SIZE - 1) 
                {   
                    if(flag)
                    {
                        g.setColor(Color.WHITE);
                        flag = !flag;
                    }
                    else
                    {
                        g.setColor(Color.LIGHT_GRAY);
                        flag = !flag;
                    }
                    g.drawString(String.valueOf((char) ('a' + col)), x + CELL_SIZE / 2 + 20, y + CELL_SIZE - 6);
                }

                if (col == 0) 
                {   
                    g.setColor(Color.BLACK);
                    g.drawString(String.valueOf(BOARD_SIZE - row), x + 3, y + CELL_SIZE / 2 - 16);
                }
            }
            currentRow += rowChange; 
        }
    }
    
    public void flipBoard()
    {
        flipFlag = !flipFlag;
    }
    
    private void drawAvailableMoves(Graphics g) 
    {
        if (selectedRow != -1 && selectedCol != -1) 
        {
            g.setColor(new Color(0, 0, 0, 50));

            for (int row = 0; row < 8; row++) 
            {
                for (int col = 0; col < 8; col++) 
                {
                    if (availableMoves[col][row]) 
                    {
                        int x = col * CELL_SIZE;
                        int y = (BOARD_SIZE - 1 - row) * CELL_SIZE; 
                        if (flipFlag) {
                        y = row * CELL_SIZE;
                        } 
                        
                        g.fillOval(x + 22, y + 20, 25, 25);
                    }
                }
            }
        }
    }

    /**
     * @param player1 the player1 to set
     */
    public void setPlayer1(Player player1) 
    {
        this.player1 = player1;
    }

    /**
     * @param player2 the player2 to set
     */
    public void setPlayer2(Player player2)
    {
        this.player2 = player2;
    }


    
    public void resetGame(PiecesOnBoard board) 
    {
        board.clearAllPieces();
        board.resetBoardAndPieces();
        board.refreshBoard();
        selectedRow = -1;
        selectedCol = -1;
        availableMoves = new boolean[8][8];
        setWhiteTurn(true);
        setCurrentPlayer(getPlayer1());
        repaint();
    }

    /**
     * @return the currentPlayer
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * @param currentPlayer the currentPlayer to set
     */
    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    /**
     * @return the whiteTurn
     */
    public boolean isWhiteTurn() {
        return whiteTurn;
    }

    /**
     * @param whiteTurn the whiteTurn to set
     */
    public void setWhiteTurn(boolean whiteTurn) {
        this.whiteTurn = whiteTurn;
    }

    /**
     * @return the board
     */
    public PiecesOnBoard getBoard() {
        return board;
    }

    /**
     * @param board the board to set
     */
    public void setBoard(PiecesOnBoard board) {
        this.board = board;
    }

    /**
     * @return the player1
     */
    public Player getPlayer1() {
        return player1;
    }

    /**
     * @return the player2
     */
    public Player getPlayer2() {
        return player2;
    }

    /**
     * @return the moves
     */
    public String getMoves() {
        return moves;
    }

    /**
     * @return the chessFrame
     */
    public ChessFrame getChessFrame() {
        return chessFrame;
    }

    /**
     * @param chessFrame the chessFrame to set
     */
    public void setChessFrame(ChessFrame chessFrame) {
        this.chessFrame = chessFrame;
    }
}