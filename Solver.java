package project7.RushHour;

import java.util.Scanner;
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.HashMap;
/**
 *
 * @author swocc
 */
public class Solver {
    public static void main(String[] args) {
        // variables for computation
        System.out.println("hello");
        final int BOARDSIZE = 6;
        String currBoard = "____________________________________";
        Position start = new Position(currBoard, "", null);
        HashMap<String, Vehicle> garage = new HashMap<>();
        HashSet<String> visited = new HashSet<>();
        
        // get user input from command line
        // set up scanner to read from stdin
        Scanner scnr = new Scanner(System.in);
        
        final int numVehicles = scnr.nextInt();
        
        for (int i = 0; i < numVehicles; i++)
        {
            String typeString = scnr.next();
            String colorString = scnr.next();
            char isHor = scnr.next().charAt(0);
            int posRow = scnr.nextInt() - 1;
            int posCol = scnr.nextInt() - 1;
            // put vehicle in garage
            Vehicle newVehicle = new Vehicle(typeString, colorString, isHor);
            garage.put(newVehicle.getSymbol() + "", newVehicle);
            // update start position
            start.addVehicle(newVehicle, posCol, posRow);
        }
        
        // prepare for BFS
        //System.out.println(start.getPosStr().substring(0, 6));
        //System.out.println(start.getPosStr().substring(6, 12));
        //System.out.println(start.getPosStr().substring(12, 18));
        //System.out.println(start.getPosStr().substring(18, 24));
        //System.out.println(start.getPosStr().substring(24, 30));
        //System.out.println(start.getPosStr().substring(30, 36));
        ArrayDeque<Position> unchecked = new ArrayDeque();
        unchecked.add(start);
        visited.add(start.getPosStr());
        
        // Run BFS
        while (!unchecked.isEmpty())
        {
            // set up iteration
            Position currPos = unchecked.remove();
            currBoard = currPos.getPosStr();
            
            for (int i = 0; i < currBoard.length(); i++)
            {
                // ignore empty spaces
                if (currBoard.charAt(i) != '_')
                {
                    boolean vehicleRep = true;
                    if (i / BOARDSIZE != 0) // top row
                    {
                        if (currBoard.charAt(i - 6) == currBoard.charAt(i))
                        {
                            vehicleRep = false;
                        }
                    }
                    if (i % BOARDSIZE != 0) // left column
                    {
                        if (currBoard.charAt(i - 1) == currBoard.charAt(i))
                        {
                            vehicleRep = false;
                        }
                    }
                    if (vehicleRep)
                    {
                        ArrayDeque<Position> newMoves = spawnMoves(currPos, garage.get(currBoard.charAt(i) + ""), i);
                        while(!newMoves.isEmpty())
                        {
                            Position prospectiveMove = newMoves.remove();
                            if (!visited.contains(prospectiveMove.getPosStr()))
                            {
                                if (prospectiveMove.getPosStr().indexOf("AA") == 16)
                                {
                                    // print results
                                    prospectiveMove.printMoves(0);
                                    return;
                                }
                                // add unused position to tree
                                unchecked.add(prospectiveMove);
                                visited.add(prospectiveMove.getPosStr());
                            }                            
                        }
                    }
                }
            }
        }
        // not solved
        System.out.println("No solution");
    }
    
    private static ArrayDeque<Position> spawnMoves(Position pos, Vehicle movingCar, int posIndex)
    {
        ArrayDeque<Position> moves = new ArrayDeque<>();
        String boardString = pos.getPosStr();
        int length = movingCar.getLen();
        String color = movingCar.getColor();
        char symbol = movingCar.getSymbol();
        int locPos = 0;
        
        // TODO: Return an ArrayDeque of all posible positions returned from moving the specified car with the proper moveString
        if (movingCar.isHor())
        {
            // find way to reduce code multiples
            for (int i = 1; (posIndex - i) % 6 != 5; i++)
            {
                locPos = posIndex - i;
                if (locPos < 0) break;
                if (boardString.charAt(locPos) != '_') break;
                
                char[] board = boardString.toCharArray();
                for (int j = 0; j < length; j++)
                {
                    board[posIndex + j] = '_';
                    board[locPos + j] = symbol;
                }
                
                moves.add(new Position(new String(board), (color + " " + i + " L"), pos));
            }
            
            for (int i = 0; (posIndex + i + length) % 6 != 0; i++)
            {
                locPos = posIndex + i + length;
                if (locPos >= 36) break;
                if (boardString.charAt(locPos) != '_') break;

                char[] board = boardString.toCharArray();
                for (int j = 0; j < length; j++)
                {
                    board[posIndex - j - 1 + length] = '_';
                    board[locPos - j] = symbol;
                }
                
                moves.add(new Position(new String(board), (color + " " + (i + 1) + " R"), pos));
            }
        }
        else
        {
           for (int i = 1; (posIndex - (i * 6)) >= 0; i++)
            {
                locPos = posIndex - (i * 6);
                if (boardString.charAt(locPos) != '_') break;
                
                char[] board = boardString.toCharArray();
                for (int j = 0; j < length; j++)
                {
                    board[posIndex + (j * 6)] = '_';
                    board[locPos + (j * 6)] = symbol;
                }
                
                moves.add(new Position(new String(board), (color + " " + i + " U"), pos));
            }
            
            for (int i = 0; (posIndex + ((i + length) * 6)) < 36; i++)
            {
                locPos = posIndex + ((i + length) * 6);
                if (boardString.charAt(locPos) != '_') break;

                char[] board = boardString.toCharArray();
                for (int j = 0; j < length; j++)
                {
                    board[posIndex - ((j + 1 - length) * 6)] = '_';
                    board[locPos - (j * 6)] = symbol;
                }
                
                moves.add(new Position(new String(board), (color + " " + (i + 1) + " D"), pos));
            } 
        }
        
        return moves;
    }
}