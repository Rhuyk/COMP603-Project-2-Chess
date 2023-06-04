/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Chess_Project_2;

import javax.swing.JFrame;

/**
 *
 * @author pj
 */
public class ChessGameGUI {
    
    public static void main(String[] args) 
    {
        JFrame frame = new JFrame("Chess Game");
        ChessPanel chessPanel = new ChessPanel();
        frame.getContentPane().add(chessPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
