/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ChessGame;

/**
 *
 * @author User
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.StringTokenizer;

public class ChessBoardFileIO {
    
    /**
    * Method: createGameText
    * 
    * This method takes in the current player name and returns a string representing
    * the name of the file which stores the chess game data. I
    * 
    */ 
    
    private static String createGameText(String currentPlayer)
    {
        String filename = currentPlayer + "_chessData.txt";
        File file = new File(filename);
        try 
        {
            if (!file.exists()) 
            {
                file.createNewFile();
            }
        } 
        catch (IOException e) 
        {
            System.out.println("Error in creating a new file.");
        }
        
        return filename;
    }
    
    /**
    * Method: saveGameForUser
    * 
    * This method takes in the current player object, the two players representing black and white and the chess board.
    * First, it initializes a 'hashmap' called 'chessdata' to store the chess location data. Then it uses to reader to 
    * read the contents of the file. Then it runs the checkForUser to check if the user is present in the file. If so, they get
    * a overwrite option. Finally, the method returns true, meaning the game data has been saved successfully.
    * 
    * @param currentPlayer
    * @param player1
    * @param player2
    * @param board
    * @return 
    */ 
    
    public static boolean saveGameForUser(String currentPlayer,Player player1, Player player2, PiecesOnBoard board)
    {
        String filename = createGameText(currentPlayer);
        boolean overwrite;
        HashMap<String,String> chessData = new HashMap();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) 
        {
            boolean userFound = checkForUser(player1.getPlayerName(), reader);

            if (userFound) 
            {
                overwrite = getOverwriteOption();
                if (!overwrite) 
                {
                    System.out.println("Game not saved.");
                    return false;
                }
            }
        } 
        catch (IOException e) 
        {
            System.out.println("Chess game can not be loaded!");
            return false;
        }

        saveUserDataToText(currentPlayer,player1, player2, board,chessData);
        return true;
    }
    
    /**
    * Method: saveUserDataToText
    * 
    * This method saves the game data to the specified text file. The method will loop through the board rows and columns
    * to get each piece at each location. If a piece is found at the location, it will be add that piece symbol and location
    * to the HashMap. Then it will loop through the HashMap and write each pair to the text as "symbol col row".
    * 
    * @param currentPlayer
    * @param player1
    * @param player2
    * @param board
    * @param chessData
    */ 
    
    public static void saveUserDataToText(String currentPlayer,Player player1, Player player2, PiecesOnBoard board,HashMap<String, String> chessData)
    {
        
        try (PrintWriter writer = new PrintWriter(new FileOutputStream(currentPlayer + "_chessData.txt"))) 
        {
            writer.println(player1.getPlayerName());
            writer.println(player2.getPlayerName());
            
            for (int row = 0; row < 8; row++) 
            {
                for (int col = 0; col < 8; col++) 
                {
                    Piece piece = board.getPiece(col, row);
                    if (piece != null)
                    {
                        String symbol = piece.getSymbol();
                        String position = String.valueOf(col) + " " +String.valueOf(row);
                        chessData.put(position, symbol);
                    }
                }
            }
            for (Map.Entry<String, String> entry : chessData.entrySet()) 
            {
                String position = entry.getKey();
                String symbol = entry.getValue();
                writer.println(symbol + " " + position);
            }
            
                    
            writer.println("###");
            System.out.println("Game saved successfully to file!");
            writer.close();
        }  
        catch (IOException e) 
        {
            System.out.println("Chess game can not be saved!");
        }
        catch (NullPointerException e)
        {
            System.out.println("Error saving player names!");
        }
    }
    
    /**
    * Method: saveMovesToText
    * 
    * This method will save the player moves to a text file. This text file is readable and can informative to the player.
    * Therefore, allowing them to study their moves and their opponent moves as well.
    * 
    * @param moves
    */ 
    public static void saveMovesToText(String moves)
    {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("chessMoves.txt")))
        {
            writer.write(moves);
            System.out.println("Game moves successfully to file!");
            writer.close();
        }
        catch (IOException e) 
        {
            System.out.println("Chess game can not be saved!");
        }
    }
    
    /**
    * Method: loadGame
    * 
    * This method purpose is to load a saved game from a text file and return a PiecesOnBoard object which contains chess game.
    * The method first creates a new board and HashSet. This HashSet is used to keep track of unique pieces.
    * Then, it reads the game data from the file, and uses StringBuilder to accumulate the board state information. 
    * Once, it reaches the delimiter '###'. It will use StringTokenizer to 'Tokenize' the accumulated board state and 
    * create new pieces when necessary. Finally, all the pieces are added to the board if there no duplicate pieces.
    * 
    * @param player1
    * @param current
    * @return 
    */ 
    
    public static PiecesOnBoard loadGame(String player1, Player current) 
    {
        String filename = player1 + "_chessData.txt";
        PiecesOnBoard board = new PiecesOnBoard();
        Set<String> uniquePiece = new HashSet<>();

        try 
        {
            BufferedReader reader;
            reader = new BufferedReader(new FileReader(filename));
            String line;
            StringBuilder gameDataBuilder = new StringBuilder();

            String line2 = reader.readLine();
            if(player1.equals(line2)) 
            {
                current.setColourPiece(PieceColour.WHITE);
            }
            line2 = reader.readLine();
            if(player1.equals(line2)) 
            {
                current.setColourPiece(PieceColour.BLACK);
            }

            while ((line = reader.readLine()) != null) 
            {
                if (line.equals("###")) 
                {
                    board.clearBoard();
                    board.clearAllPieces();
                    String gameData = gameDataBuilder.toString();
                    StringTokenizer tokenizer = new StringTokenizer(gameData, "\n");

                    while (tokenizer.hasMoreTokens()) {
                        String gameLine = tokenizer.nextToken();
                        StringTokenizer lineTokenizer = new StringTokenizer(gameLine, " ");
                        if (lineTokenizer.countTokens() >= 3) {
                            String symbol = lineTokenizer.nextToken();
                            int col = Integer.parseInt(lineTokenizer.nextToken());
                            int row = Integer.parseInt(lineTokenizer.nextToken());
                            String location = col + "," + row;

                            if (!uniquePiece.contains(location)) {
                                Piece piece = createPiece(symbol, col, row);
                                board.addPiece(piece);
                                uniquePiece.add(location);
                            }
                        }
                    }

                    board.refreshBoard();
                    gameDataBuilder = new StringBuilder();
                    uniquePiece.clear();
                } 
                else 
                {
                    gameDataBuilder.append(line).append("\n");
                }
            }
            reader.close();
            System.out.println("Game file has been loaded!");
            } catch (IOException e) 
            {
                System.out.println("Error: Game file could not be loaded!");
            }

        return board;
    }

    /**
    * Method: checkForUser
    * 
    * This private method will check if the user is present in the text file.
    * 
    */ 
    
    private static boolean checkForUser(String player1, BufferedReader reader) throws IOException 
    {
        String line;
        
        while ((line = reader.readLine()) != null) 
        {
            if (player1.equals(line)) 
            {
                System.out.println("Player Name already exists in the file.");
                return true;
            }
        }

        return false;
    }
    
    /**
    * Method: checkForUser
    * 
    * This private method will give the user options whether to overwrite the data or not.
    * 
    */ 
    
    private static boolean getOverwriteOption() 
    {
        System.out.print("Do you wish to overwrite (Y/N): ");
        Scanner scanner = new Scanner(System.in);
        String choice = scanner.nextLine();

        return choice.equalsIgnoreCase("Y");
    }
    
    /**
    * Method: createPiece
    * 
    * This private method will create a chess piece at the specified column and row. It has a switch case on the
    * symbol to create that specific piece when found in the text file.
    * 
    */
    
    private static Piece createPiece(String symbol,int column,int row) {
        switch (symbol) {
            case "wP":
                return new Pawn(PieceColour.WHITE,column,row);
            case "wR":
                return new Rook(PieceColour.WHITE,column,row);
            case "wN":
                return new Knight(PieceColour.WHITE,column,row);
            case "wB":
                return new Bishop(PieceColour.WHITE,column,row);
            case "wQ":
                return new Queen(PieceColour.WHITE,column,row);
            case "wK":
                return new King(PieceColour.WHITE,column,row);
            case "bP":
                return new Pawn(PieceColour.BLACK,column,row);
            case "bR":
                return new Rook(PieceColour.BLACK,column,row);
            case "bN":
                return new Knight(PieceColour.BLACK,column,row);
            case "bB":
                return new Bishop(PieceColour.BLACK,column,row);
            case "bQ":
                return new Queen(PieceColour.BLACK,column,row);
            case "bK":
                return new King(PieceColour.BLACK,column,row);
            default:
                return null;
        }
    }
}