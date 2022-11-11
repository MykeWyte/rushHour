/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project7.RushHour;

/**
 *
 * @author swocc
 */
public class Position {
    // member variables
    private String posString;
    private String movString;
    private Position prev;
    
    // constructors
    public Position(String newPosStr, String newMovStr, Position prevPos)
    {
        posString = newPosStr;
        movString = newMovStr;
        prev = prevPos;
    }
    
    // methods
    
    // accesors
    
    public String getPosStr()
    {
        return posString;
    }
    
    // other
    
    public void printMoves(int moveCount)
    {
        // recursively start at top
        if (prev != null)
        {
            prev.printMoves(moveCount + 1);
            System.out.println(movString);
        }
        else
        {
            if (moveCount == 1) System.out.println(moveCount + " move");
            else System.out.println(moveCount + " moves");
        }
    }
    
    public void addVehicle(Vehicle added, int posCol, int posRow)
    {
        //TODO: update posString to include the symbols of the added vehicle in the right position
        
        // add protection against wraparound and poorly placed vehicles
        char[] board = posString.toCharArray();
        int index = (posRow * 6) + posCol;
        posString = "";
        
        for (int i = 0; i < added.getLen(); i++)
        {
            if (added.isHor())
            {
                board[index + i] = added.getSymbol();
            }
            else
            {
                board[index + (i * 6)] = added.getSymbol();
            }
        }
        
        posString = new String(board);
    }
}