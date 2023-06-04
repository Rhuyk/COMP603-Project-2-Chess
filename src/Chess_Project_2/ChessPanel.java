/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Chess_Project_2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author pj
 */
public class ChessPanel extends JPanel {
    private static final int BOARD_SIZE = 8;
    private static final int CELL_SIZE = 63;
    private final PiecesOnBoard board;
    private boolean[][] availableMoves;
    private int selectedRow = -1;
    private int selectedCol = -1;
    private Player player1;
    private Player player2;
    private Player currentPlayer;
    private boolean whiteTurn = true;
    private Image backgroundImage;
    
    private JTextField player1NameField;
    private JTextField player2NameField;
    private JButton startButton;

    public ChessPanel() 
    {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(820, 623));
        board = new PiecesOnBoard();
        availableMoves = new boolean[8][8];
        createPlayerLoginScreen();
        
        addMouseListener();
        loadBackgroundImage();
    }
    
    private void loadBackgroundImage() {
        try {
            backgroundImage = ImageIO.read(new File("b2.png"));
        } catch (IOException e) 
        {
            System.out.println("File Error! IOException");
        } 
    }
    
    private void addMouseListener() {
        addMouseListener(new MouseAdapter() {
            Piece selectedPiece = null;
            @Override
            public void mousePressed(MouseEvent e) {
                if (player1 != null && player2 != null) 
                {
                    int col = e.getX() / CELL_SIZE;
                    int row = (BOARD_SIZE - 1) - e.getY() / CELL_SIZE;
                   // currentPlayer = player1; // REMOVE
                    if (col >= 0 && col < BOARD_SIZE && row >= 0 && row < BOARD_SIZE) {
                        Piece clickedPiece = board.getBoard()[col][row];
                        
                        if (selectedPiece == null) {
                            if (clickedPiece != null && clickedPiece.getColour() == currentPlayer.getColourPiece()) {
                                selectedPiece = clickedPiece;
                                selectedRow = row;
                                selectedCol = col;
                                availableMoves = selectedPiece.getAvailableMoves();
                            }
                        } else {
                            if (availableMoves[col][row]) {
                                if (selectedPiece.getColour() == currentPlayer.getColourPiece() && board.movePiece(selectedCol, selectedRow, col, row)) {
                                    whiteTurn = !whiteTurn;
                                } 
                                else
                                {
                                    JOptionPane.showMessageDialog(null, "Illegal move, " + currentPlayer.getColourPiece() + " King is under attack.");
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
                }
            }
        });
    }
    
    private void createPlayerLoginScreen() 
    {
        JLabel player1Label = new JLabel("Player 1 Name:");
        JLabel player2Label = new JLabel("Player 2 Name:");
        player1NameField = new JTextField(10);
        player2NameField = new JTextField(10);
        startButton = new JButton("Start Game");
        
        // Set font for labels and button
        Font labelFont = new Font(Font.SANS_SERIF, Font.BOLD, 13);
        player1Label.setFont(labelFont);
        player2Label.setFont(labelFont);
        startButton.setFont(labelFont);

        startButton.addActionListener((ActionEvent e) -> {
            String player1Name = player1NameField.getText().trim();
            String player2Name = player2NameField.getText().trim();
            
            if (player1Name.isEmpty() || player2Name.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter both player names.", "Invalid Input", JOptionPane.WARNING_MESSAGE);
            } 
            else 
            {
                player1 = new Player(PieceColour.WHITE, player1Name);
                player2 = new Player(PieceColour.BLACK, player2Name);
                currentPlayer = player1;
                startButton.setEnabled(false);
                startButton.setVisible(false);
                player1NameField.setVisible(false);
                player2NameField.setVisible(false);
                player1Label.setVisible(false);
                player2Label.setVisible(false);
                createGameButtons();
                repaint();
            }
        });
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.Y_AXIS));
        
        // Add player login components to the panel
        loginPanel.add(player1Label);
        loginPanel.add(player1NameField);
        loginPanel.add(player2Label);
        loginPanel.add(player2NameField);
        loginPanel.add(startButton);
        add(loginPanel, BorderLayout.SOUTH);
    }

    @Override
    protected void paintComponent(Graphics g) 
    {
        super.paintComponent(g);
        
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        
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
        if (player1 == null || player2 == null) // Check if player has logged in
        {
            g.setColor(new Color(0, 0, 0, 200));
            g.fillRect(CELL_SIZE - 59, CELL_SIZE - 58, BOARD_SIZE * CELL_SIZE, BOARD_SIZE * CELL_SIZE);
        }
        
        if(availableMoves != null)
        {
            drawAvailableMoves(g);
            
        }
        
        
    }

    private void drawChessBoard(Graphics g) 
    {
        int currentRow = 7;  
        boolean flag = true;
        for (int row = 0; row < 8; row++) 
        {
            for (int col = 0; col < 8; col++) 
            {
                int x = col * CELL_SIZE;
                int y = row * CELL_SIZE;
                x += 4;
                y += 5;
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

private void createGameButtons() {
    JButton resignButton = new JButton("Resign");
    JButton drawButton = new JButton("Draw");
    JButton restartButton = new JButton("Restart");
    JButton quitButton = new JButton("Quit");

    resignButton.addActionListener((ActionEvent e) -> {
        int response = JOptionPane.showConfirmDialog(null, currentPlayer.getPlayerName() + ", do you wish to resign?", "Resign", JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(null, currentPlayer.getPlayerName() + " has resigned. This game has ended via resignation");
        }
    });

    drawButton.addActionListener((ActionEvent e) -> {
        Player otherPlayer = (currentPlayer == player1) ? player2 : player1;
        int response = JOptionPane.showConfirmDialog(null, currentPlayer.getPlayerName() + " asks for a draw. " + otherPlayer.getPlayerName() + ", do you accept the draw?", "Draw Proposal", JOptionPane.YES_NO_OPTION);

        if (response == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(null, "The game has ended via draw.");
        } else {
            JOptionPane.showMessageDialog(null, "Player 2 declined the draw.");
        }
    });

    restartButton.addActionListener((ActionEvent e) -> {
        resetGame();
    });

    quitButton.addActionListener((ActionEvent e) -> {
        System.out.println("Thank you for playing");
        System.exit(0);
    });

    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
    buttonPanel.add(Box.createVerticalGlue());
    buttonPanel.add(resignButton);
    buttonPanel.add(drawButton);
    buttonPanel.add(restartButton);
    buttonPanel.add(quitButton);
    buttonPanel.add(Box.createVerticalGlue());
    add(buttonPanel, BorderLayout.EAST);
}
    
    private void resetGame() 
    {
        board.clearAllPieces();
        board.resetBoardAndPieces();
        board.refreshBoard();
        selectedRow = -1;
        selectedCol = -1;
        availableMoves = new boolean[8][8];
        whiteTurn = true;
        currentPlayer = player1;
        repaint();
    }
}