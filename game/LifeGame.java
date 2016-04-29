package game;

/**
 * @author Peter Armstrong
 * @version 2.5, 8/12/2011
 * @version 2.6, 21/10/2015
 * 
 * This is the game logic class for Conway's Game of Life.
 * The classes uses the associated GUI.java class to display the login in a game application
 * for the user to play around with.
 * 
 * This code was written in 2011 for a Java assignment from the School of Computing 
 * @ the University of Dundee, Scotland. I've since revisited it and adapted it for my own amusement.
 * It used to just run in a refreshing console window - but since then I've added GUI capabilities, 
 * with help from Dr. Iain Martin's GUI class.
 * 
 * Currently working on: mouse clicking+dragging to change state of tiles.
 */

class LifeGame
{
    public int GRID_SIZE;
    public Boolean [][] state;
    
    public LifeGame(int GRID_SIZE)
    {
        this.GRID_SIZE = GRID_SIZE;
        this.GRID_SIZE = GRID_SIZE;
        
        state = new Boolean[GRID_SIZE][GRID_SIZE];
        
        for (int i = 0; i < GRID_SIZE; i++)
        {
        	for (int j = 0; j < GRID_SIZE; j++)
        	{
        		state[i][j] = false;
        	}
        }
    }
    
    public Boolean getPos(int x, int y)
    {
        if (x >= GRID_SIZE || x < 0 || y >= GRID_SIZE || y < 0)
        {
            return false;
        }
        else
        {
            return state[x][y];
        }
    }
    
    public void setPos(int x, int y, Boolean b)
    {
    	state[x][y] = b;
    }
    
    public int countNeighbours(int x, int y)
    {
        int count = 0;
        
        if (getPos(x+1,y))
        {
            count++;
        }
        
        if (getPos(x-1,y))
        {
            count++;
        }
        
        if (getPos(x,y+1))
        {
            count++;
        }
        
        if (getPos(x,y-1))
        {
            count++;
        }
        
        if (getPos(x+1,y+1))
        {
            count++;
        }
        
        if (getPos(x-1,y-1))
        {
            count++;
        }
        
        if (getPos(x+1,y-1))
        {
            count++;
        }
        
        if (getPos(x-1,y+1))
        {
            count++;
        }
        
        return count;
    }
    
    public void step()
    {
        Boolean [][] new_state = new Boolean[GRID_SIZE][GRID_SIZE];
        
        for (int y = 0; y < GRID_SIZE; y++)
        {
            for (int x = 0; x < GRID_SIZE; x++)
            {
                int neighbours = countNeighbours(x, y);
                
                if (state[x][y])
                {
                    if (neighbours < 2 || neighbours > 3) // Rule that kills a cell if it doesn't have sufficient number of/has too many neighbours
                    {
                        new_state[x][y] = false;
                    }
                    else
                    {
                        new_state[x][y] = true;
                    }
                }
                else
                {
                    if (neighbours == 3) // Method that populates a cell if it has sufficient neighbours
                    {
                        new_state[x][y] = true;
                    }
                    else
                    {
                        new_state[x][y] = false;
                    }
                }
                
            }
            
        }
        
        state = new_state;  // sets the state to the new_state so all cell states are updated
    }

}
