package project7.RushHour;

/**
 *
 * @author swocc
 */
public class Vehicle {
    // static variables
    static char nextSymbol = 'B';
    
    // member variables
    private int len;
    private String color;
    private char symbol;
    private boolean isHorizontal;
    
    // methods
    
    // constructor
    public Vehicle(String type, String newColor, char isHor)
    {
        // set len based on type
        if (type == "car")
        {
            len = 2;
        }
        else if (type == "truck")
        {
            len = 3;
        }
        else
        {
            System.err.println("vehicle Type not recognized: " + type);
        }
        
        color = newColor;
        
        // automatically assign characters to associate with colors
        if (color == "red")
        {
            symbol = 'A';
        }
        else
        {
            symbol = Vehicle.nextSymbol;
            Vehicle.nextSymbol++;
        }
        
        // set horizontal based on char input
        isHorizontal = isHor == 'h';
    }
    
    // accessors
    public int getLen()
    {
        return len;
    }
    
    public char getSymbol()
    {
        return symbol;
    }
    
    public String getColor()
    {
        return color;
    }
    
    public boolean isHor()
    {
        return isHorizontal;
    }
}
