/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package Chess_Project_2;

import java.awt.Graphics;
import java.lang.reflect.Field;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author User
 */
public class ChessPanelTest {
    
    private ChessPanel chessPanel;
    private ChessFrame chessFrame;
    
    @Before
    public void setUp() 
    {
        chessFrame = new ChessFrame();
        chessPanel = new ChessPanel(chessFrame);
        chessFrame.add(chessPanel);
        chessFrame.pack();
        chessFrame.setVisible(true);
    }
    
//    @Test
//    public void testSelectPiece() throws NoSuchFieldException, IllegalAccessException {
//        setMockBoard();
//        setMouseCoordinates(0, 0);  // Select the piece at (0, 0)
//        simulateMousePress(chessPanel);
//
//        Piece selectedPiece = getSelectedPiece(chessPanel);
//        assertNotNull(selectedPiece);
//        assertEquals("R", selectedPiece.getSymbol());
//        assertEquals(0, getSelectedRow(chessPanel));
//        assertEquals(0, getSelectedCol(chessPanel));
//    }

    private void setBoard(ChessPanel chessPanel, Piece[][] board) throws NoSuchFieldException, IllegalAccessException 
    {
        Field boardField = ChessPanel.class.getDeclaredField("chessController");
        boardField.setAccessible(true);
        ChessController chessController = (ChessController) boardField.get(chessPanel);
        Field boardArrayField = ChessController.class.getDeclaredField("board");
        boardArrayField.setAccessible(true);
        boardArrayField.set(chessController, board);
    }

    /**
     * Test of paintComponent method, of class ChessPanel.
     */
    @Test
    public void testPaintComponent() {
        System.out.println("paintComponent");
        Graphics g = null;
        ChessPanel instance = null;
        instance.paintComponent(g);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of flipBoard method, of class ChessPanel.
     */
    @Test
    public void testFlipBoard() {
        System.out.println("flipBoard");
        ChessPanel instance = null;
        instance.flipBoard();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of resetGame method, of class ChessPanel.
     */
    @Test
    public void testResetGame() {
        System.out.println("resetGame");
        ChessPanel instance = null;
        instance.resetGame();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getChessFrame method, of class ChessPanel.
     */
    @Test
    public void testGetChessFrame() {
        System.out.println("getChessFrame");
        ChessPanel instance = null;
        ChessFrame expResult = null;
        ChessFrame result = instance.getChessFrame();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setChessFrame method, of class ChessPanel.
     */
    @Test
    public void testSetChessFrame() {
        System.out.println("setChessFrame");
        ChessFrame chessFrame = null;
        ChessPanel instance = null;
        instance.setChessFrame(chessFrame);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isFlipFlag method, of class ChessPanel.
     */
    @Test
    public void testIsFlipFlag() {
        System.out.println("isFlipFlag");
        ChessPanel instance = null;
        boolean expResult = false;
        boolean result = instance.isFlipFlag();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setFlipFlag method, of class ChessPanel.
     */
    @Test
    public void testSetFlipFlag() {
        System.out.println("setFlipFlag");
        boolean flipFlag = false;
        ChessPanel instance = null;
        instance.setFlipFlag(flipFlag);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isToggleSwitch method, of class ChessPanel.
     */
    @Test
    public void testIsToggleSwitch() {
        System.out.println("isToggleSwitch");
        ChessPanel instance = null;
        boolean expResult = false;
        boolean result = instance.isToggleSwitch();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setToggleSwitch method, of class ChessPanel.
     */
    @Test
    public void testSetToggleSwitch() {
        System.out.println("setToggleSwitch");
        boolean toggleSwitch = false;
        ChessPanel instance = null;
        instance.setToggleSwitch(toggleSwitch);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setGameEnded method, of class ChessPanel.
     */
    @Test
    public void testSetGameEnded() {
        System.out.println("setGameEnded");
        boolean gameEnded = false;
        ChessPanel instance = null;
        instance.setGameEnded(gameEnded);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
