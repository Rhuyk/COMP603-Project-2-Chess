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
    private boolean[][] availableMoves;
    private int selectedRow = -1;
    private int selectedCol = -1;

    private int currentRow;
    private int rowChange;
    private boolean flipFlag = false;
    private boolean toggleSwitch = false;
    private ChessFrame chessFrame;
    private ChessController chessController;
    private Piece selectedPiece = null; 
    
    public ChessPanel(ChessFrame frame) 
    {
        chessController = new ChessController();
        chessFrame = frame;
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(513, 514));
        availableMoves = new boolean[8][8];
        
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) 
            {
                if (chessController.getPlayer1()!= null && chessController.getPlayer2() != null) 
                {
                    int col = e.getX() / CELL_SIZE;
                    int row = (BOARD_SIZE - 1) - e.getY() / CELL_SIZE;
                    if (isFlipFlag()) 
                    {
                        row = (BOARD_SIZE - 1) - row;
                    }
                    if (isLegalMove(col, row)) 
                    {
                        handleClick(col, row);
                    }
                }
            }
        });
    }
    
    private boolean isLegalMove(int col, int row) 
    {   
        return col >= 0 && col < BOARD_SIZE && row >= 0 && row < BOARD_SIZE;
    }
    
    private void handleClick(int col, int row) 
    {
        if (selectedRow == -1 && selectedCol == -1)  // Check for if piece has not been selected
        {
            selectPiece(col, row);
        } 
        else 
        {
            movePiece(col, row);
        }

        repaint();
    }
    
    private void selectPiece(int col, int row) 
    {
        selectedPiece = chessController.getBoard()[col][row];

        if (selectedPiece != null) // Check for Empty piece
        {
            if (selectedPiece.getColour() == chessController.getCurrentColourTurn())
            {
                selectedRow = row;
                selectedCol = col;
                availableMoves = selectedPiece.getAvailableMoves();
            }
        }
    }
    
    private void movePiece(int col, int row) 
    {
        selectedPiece = chessController.getBoard()[selectedCol][selectedRow];

        if (availableMoves[col][row]) {
            if (chessController.movePiece(selectedCol, selectedRow, col, row)) {
                flipBoard();
                String move = "(" + selectedPiece.getSymbol() + ")" + String.format(" %s%d, %s%d%n", (char)('a' + selectedCol), selectedRow + 1, (char)('a' + col), row + 1);
                if(chessFrame != null)
                {
                    chessFrame.updateMovesTextArea(move);
                }
            }
        }
        
        resetSelection();
        if(chessController.gameEnded())
        {
            // Change later
            JOptionPane.showMessageDialog(ChessPanel.this, "This Chess Game has ended via Checkmate! Game over.");
            chessFrame.switchTab(4);
        }
        checkForPromotion();
        repaint();
    }
    
    private void resetSelection() 
    {
        selectedPiece = null;
        selectedRow = -1;
        selectedCol = -1;
        availableMoves = new boolean[BOARD_SIZE][BOARD_SIZE];
    }
    
    public boolean checkForPromotion()
    {
        if(chessController.canPromote())
        {
            chessFrame.switchTab(5);
            return true;
        }
        return false;
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
        if (chessController.getPlayer1() == null || chessController.getPlayer2() == null) // Check if player has logged in
        {
            g.setColor(new Color(0, 0, 0, 100));
            g.fillRect(CELL_SIZE - 59, CELL_SIZE - 58, BOARD_SIZE * CELL_SIZE, BOARD_SIZE * CELL_SIZE);
        }
        
        if(availableMoves != null)
        {
            drawAvailableMoves(g);
        }
        
    }

    private void drawChessBoard(Graphics g) 
    {
        currentRow = 7;
        rowChange = -1;
        if(isFlipFlag())
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
                Piece piece = chessController.getBoard()[col][currentRow];

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
                
                if (piece != null && piece.getSymbol().contains("K") && chessController.isInCheck(piece)) 
                {
                    g.setColor(Color.RED);
                    g.drawRect(x, y, CELL_SIZE - 1, CELL_SIZE - 1);
                }
                
                // Add yellow overlay on the square where the piece was
                if (selectedCol == col && selectedRow == currentRow) {
                    g.setColor(new Color(255, 255, 0, 100));
                    g.fillRect(x, y, CELL_SIZE, CELL_SIZE);
                }
            }
            currentRow += rowChange; 
        }
    }
    
    public void flipBoard()
    {
        if(isToggleSwitch())
        {
            setFlipFlag(!isFlipFlag());
        }
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
                        if (isFlipFlag()) {
                        y = row * CELL_SIZE;
                        } 
                        
                        g.fillOval(x + 22, y + 20, 25, 25);
                    }
                }
            }
        }
    }

    public void resetGame() 
    {
        chessController.startNewGame();
        selectedRow = -1;
        selectedCol = -1;
        availableMoves = new boolean[8][8];
        repaint();
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

    /**
     * @return the flipFlag
     */
    public boolean isFlipFlag() {
        return flipFlag;
    }

    /**
     * @param flipFlag the flipFlag to set
     */
    public void setFlipFlag(boolean flipFlag) {
        this.flipFlag = flipFlag;
    }

    /**
     * @return the toggleSwitch
     */
    public boolean isToggleSwitch() {
        return toggleSwitch;
    }

    /**
     * @param toggleSwitch the toggleSwitch to set
     */
    public void setToggleSwitch(boolean toggleSwitch) {
        this.toggleSwitch = toggleSwitch;
    }

    /**
     * @return the chessController
     */
    public ChessController getChessController() {
        return chessController;
    }

    /**
     * @param chessController the chessController to set
     */
    public void setChessController(ChessController chessController) {
        this.chessController = chessController;
    }
}