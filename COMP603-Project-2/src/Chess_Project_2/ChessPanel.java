/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Chess_Project_2;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 *
 * @author pj
 */
public class ChessPanel extends JPanel {
    private static final int BOARD_SIZE = 8;
    private static final int CELL_SIZE = 70;
    private PiecesOnBoard board;

    public ChessPanel() {
        setPreferredSize(new Dimension(BOARD_SIZE * CELL_SIZE, BOARD_SIZE * CELL_SIZE));
        board = new PiecesOnBoard();
    }

    @Override
    protected void paintComponent(Graphics g) 
    {
        super.paintComponent(g);
        
        drawChessBoard(g);
        
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
}



