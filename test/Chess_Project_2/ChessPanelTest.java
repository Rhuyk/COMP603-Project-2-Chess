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
    }
    
    /**
     * Test of if if a move is legal based on the board size.
     */
    @Test
    public void testIsLegalMove() 
    {
        int legalCol = 3;
        int legalRow = 4;
        int illegalCol = -1;
        int illegalRow = 8;

        assertTrue(chessPanel.isLegalMove(legalCol, legalRow));
        assertFalse(chessPanel.isLegalMove(illegalCol, legalRow));
        assertFalse(chessPanel.isLegalMove(legalCol, illegalRow));
        assertFalse(chessPanel.isLegalMove(illegalCol, illegalRow));
    }
    
    /**
     * Test of if if a move is legal based on the board size.
     */
    @Test
    public void testValidSelectPiece() 
    {
        int validCol = 3;
        int validRow = 1;

        chessPanel.selectPiece(validCol, validRow);

        assertNotNull(chessPanel.getSelectedPiece());
        assertEquals(validCol, chessPanel.getSelectedCol());
        assertEquals(validRow, chessPanel.getSelectedRow());
    }
    
    /**
     * Test of if if a move is illegal based on the board size.
     */
    @Test
    public void testInvalidSelectPiece()
    {
        int illegalCol = 4;
        int illegalRow = 5;

        chessPanel.selectPiece(illegalCol, illegalRow);
        assertNull(chessPanel.getSelectedPiece());
        assertEquals(-1, chessPanel.getSelectedCol());
        assertEquals(-1, chessPanel.getSelectedRow());
    }
    
    
    
    

    
}
