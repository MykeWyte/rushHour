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
            int posRow = scnr.nextInt();
            int posCol = scnr.nextInt();
            // put vehicle in garage
            Vehicle newVehicle = new Vehicle(typeString, colorString, isHor);
            garage.put(newVehicle.getSymbol() + "", newVehicle);
            // update start position
            start.addVehicle(newVehicle, posCol, posRow);
        }
        
        // prepare for DFS
        
        ArrayDeque<Position> unchecked = new ArrayDeque();
        unchecked.add(start);
        visited.add(start.getPosStr());
        
        // Run DFS
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
                        ArrayDeque<Position> newMoves = spawnMoves(currBoard, garage.get(currBoard.charAt(i) + ""), i);
                        while(!newMoves.isEmpty())
                        {
                            Position prospectiveMove = newMoves.remove();
                            if (!visited.contains(prospectiveMove.getPosStr()))
                            {
                                if (false/* TODO: check if solved */)
                                {
                                    // print results
                                    prospectiveMove.printMoves(0);
                                    return;
                                }
                                // add unused position to tree
                                unchecked.add(prospectiveMove);
                            }                            
                        }
                    }
                }
            }
        }
        // not solved
        System.out.println("No solution");
    }
    
    private static ArrayDeque<Position> spawnMoves(String boardString, Vehicle movingCar, int posIndex)
    {
        ArrayDeque<Position> moves = new ArrayDeque<>();
        // TODO: Return an ArrayDeque of all posible positions returned from moving the specified car with the proper moveString
        return moves;
    }
}
