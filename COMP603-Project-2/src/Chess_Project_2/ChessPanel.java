/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Chess_Project_2;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;

/**
 *
 * @author pj
 */
public class ChessPanel extends JPanel {
    private static final int BOARD_SIZE = 8;
    private static final int CELL_SIZE = 70;
    private PiecesOnBoard board;
    private boolean[][] availableMoves;
    private int selectedRow = -1;
    private int selectedCol = -1;
    private Player player1;
    private Player player2;
    private Player currentPlayer;
    

    public ChessPanel() 
    {
       setPreferredSize(new Dimension(BOARD_SIZE * CELL_SIZE, BOARD_SIZE * CELL_SIZE));
       board = new PiecesOnBoard();
       availableMoves = new boolean[8][8];
       

       addMouseListener(new MouseAdapter() {
           
           Piece selectedPiece = null;
           boolean whiteTurn = true;
            
           @Override
           public void mousePressed(MouseEvent e) {
               int col = e.getX() / CELL_SIZE;
               int row = (BOARD_SIZE - 1) - e.getY() / CELL_SIZE;
               currentPlayer = player1;
               if(!whiteTurn)
               {
                    currentPlayer = player2;
               }
               if (col >= 0 && col < BOARD_SIZE && row >= 0 && row < BOARD_SIZE) {
                   Piece clickedPiece = board.getBoard()[col][row];
                   
                   if (selectedPiece == null) 
                   {
                       if (clickedPiece != null && clickedPiece.getColour() == currentPlayer.getColourPiece()) 
                       {
                           selectedPiece = clickedPiece;
                           selectedRow = row;
                           selectedCol = col;
                           availableMoves = selectedPiece.getAvailableMoves();
                       }
                   } 
                   else 
                   {
                       if (availableMoves[col][row]) 
                       {
                           if(selectedPiece.getColour() == currentPlayer.getColourPiece())
                           {
                                board.movePiece(selectedCol, selectedRow, col, row);
                                whiteTurn = !whiteTurn;
                           }
                       }

                       selectedPiece = null;
                       selectedRow = -1;
                       selectedCol = -1;
                       availableMoves = new boolean[8][8];
                   }

                   repaint();
               }
           }
       });
   }
    

    @Override
    protected void paintComponent(Graphics g) 
    {
        super.paintComponent(g);
        
        drawChessBoard(g);
        if(availableMoves != null)
        {
            drawAvailableMoves(g);
        }
        
    }

    private void drawChessBoard(Graphics g) 
    {
        int currentRow = 7;  
        for (int row = 0; row < 8; row++) 
        {
            for (int col = 0; col < 8; col++) 
            {
                int x = col * CELL_SIZE;
                int y = row * CELL_SIZE;
                Piece piece = board.getBoard()[col][currentRow];

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
                    g.drawImage(piece.getImage(), x + 12, y + 10, null);
                }

                if (row == BOARD_SIZE - 1) 
                {
                    g.setColor(Color.BLACK);
                    g.drawString(String.valueOf((char) ('a' + col)), x + CELL_SIZE / 2 - 5, y + CELL_SIZE - 5);
                }

                if (col == 0) 
                {
                    g.setColor(Color.BLACK);
                    g.drawString(String.valueOf(BOARD_SIZE - row), x + 5, y + CELL_SIZE / 2 + 5);
                }
            }
            currentRow--; 
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
                        g.fillOval(x + 22, y + 22, 25, 25);
                    }
                }
            }
        }
    }

    /**
     * @param player1 the player1 to set
     */
    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    /**
     * @param player2 the player2 to set
     */
    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }
}



