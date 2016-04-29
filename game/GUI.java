package game;

/**
 * GameGui: 
 * 
 * @author Iain Martin 
 * @version 1.0
 * 
 * Notes to use GameGui
 *  GameGui is intended as a blank Graphical User Interface that may be adapted.
 *  For a variety of uses. This version has been tweaked to make it suitable
 *  for implementing a GameOfLife
 *  Comments that start with GameGui mark where you might 
 *  add your own code. Please do not attempt to use this GUI until
 *  you have already met the minimum requirements of the project and completed a text
 *  based version.
 * 
 * Notes:
 *  Event handlers have been set up for Menu Options
 *  NewGame, LoadGame and Save Game.
 *  
 *  An Event handler has also been set up for a Mouse Click on
 *  the grid which calls fireShot(row, col).
 *  
 *  To add functionality to this GUI add you code to these functions
 *  which are at the end of this file. 
 *  
 *  Potential additions: FileChoosers could be implemented and the grid characters
 *  could be replaced with graphics by loading gifs or jpgs into the grid which is
 *  created from JButtons.
 *  
 *  ########
 *  
 *  THIS CODE WAS WRITTEN BY Dr. Iain Martin of the University of Dundee, Scotland for
 *  part of his first year classes on Java. I've adapted it from the original assignment
 *  but a wholly large amount of it was written by him. Most of the comments too. 
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

import game.LifeGame;

public class GUI implements ActionListener
{
    private int GRID_SIZE = 25;
    private JButton [] buttonArray; 
    private java.util.Timer myTimer;
    private JPanel grid;
    
    private static final long TIMER_INIT_DELAY = 500;
    private static final long TIMER_PERIOD = 50;
    
    public int timertest = 0;
    
    public Boolean isRunning = false;
    
    public LifeGame currentGame;

    public GUI()
    {
    	currentGame = new LifeGame(GRID_SIZE);
    }
    
    public JMenuBar createMenu() 
    {
        JMenuBar menuBar  = new JMenuBar();;
        JMenu pb_menu = new JMenu("Playback");
        JMenuItem menuItem;
       
        //menuBar.add(menu);
        menuBar.add(pb_menu);
        
        // Playback Menu Items
        menuItem = new JMenuItem("Start");
        menuItem.addActionListener(this);
        pb_menu.add(menuItem);
        
        // a sub-menu
        pb_menu.addSeparator();
        
        menuItem = new JMenuItem("Pause");
        menuItem.addActionListener(this);
        pb_menu.add(menuItem);
        
        return menuBar;
    }

    public Container createContentPane() 
    {
        int numButtons = GRID_SIZE * GRID_SIZE;
        grid = new JPanel(new GridLayout(GRID_SIZE,GRID_SIZE));
        buttonArray = new JButton[numButtons];
        
        for (int i = 0; i < numButtons; i++)
        {
            buttonArray[i] = new JButton();

            // This label is used to identify which button was clicked in the action listener
            buttonArray[i].setActionCommand("" + i); // String "0", "1" etc.
            buttonArray[i].addActionListener(this);
            
            grid.add(buttonArray[i]);
        }
        
        updateGridColours();
        
        /* Create the Timer object and set the event period */
        myTimer = new java.util.Timer();
        myTimer.schedule(updateGameTask, TIMER_INIT_DELAY, TIMER_PERIOD); // moved to StartGame()
        
        return grid;
    }

    public void actionPerformed(ActionEvent e) 
    {
        String classname = getClassName(e.getSource());
        JComponent component = (JComponent)(e.getSource());
    
        if (classname.equals("JMenuItem"))
        {
            JMenuItem menusource = (JMenuItem)(e.getSource());
            String menutext  = menusource.getText();
            
            if (menutext.equals("Start"))
            {
                /* GameGui    Add your code here to handle Start Game **********/
                StartGame();
            }
            else if (menutext.equals("Pause"))
            {
                /* GameGui    Add your code here to handle Pause Game **********/
                PauseGame();
            }
        }
        // Handle the event from the user clicking on a command button
        else if (classname.equals("JButton"))
        {
            JButton button = (JButton)(e.getSource());
            int bnum = Integer.parseInt(button.getActionCommand());
            int row = bnum / GRID_SIZE;
            int col = bnum % GRID_SIZE;
                   
            /* GameGui    Add your code here to handle user clicking on the grid ***********/
            clickedOnGrid(row, col);
        }  
    }
    
    protected String getClassName(Object o) 
    {
        String classString = o.getClass().getName();
        int dotIndex = classString.lastIndexOf(".");
        return classString.substring(dotIndex+1);
    }

    private static void createAndShowGUI() 
    {
        // Create and set up the window.
        JFrame frame = new JFrame("Conway's Game of Life");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create and set up the content pane.
        GUI GameGui = new GUI();
        frame.setJMenuBar(GameGui.createMenu());
        frame.setContentPane(GameGui.createContentPane());

        // Display the window, setting the size
        frame.setSize(600, 600);
        frame.setVisible(true);
    }
    
    public static void main(String[] args) 
    {
        javax.swing.SwingUtilities.invokeLater
        (new Runnable() 
        	{
            	public void run() 
            	{
            		createAndShowGUI();
            	}
        	}
        );
    }
    
    final Runnable doUpdateGame = new Runnable() 
    {
        public void run()    
        {
            timerUpdate();
        } 
    };

    TimerTask updateGameTask = new TimerTask() 
    {
        public void run()
        {
            EventQueue.invokeLater(doUpdateGame);
        }
    };

    public void StartGame()
    {
    	if(isRunning)
    	{
    		System.out.println("Game already running");
    		return;
    	}
    	
    	isRunning = true;
    	
        System.out.println("Start game selected");
    }
    
    public void PauseGame()
    {
    	if (!isRunning)
    	{
    		System.out.println("Game not running to pause");
    		return;
    	}
    	
    	isRunning = false;
    	
    	System.out.println("Pause game selected");
    }
    
    public void clickedOnGrid(int row, int col)
    {
          System.out.println("Grid selected: at (" + row + ", " + col + ")");
          
          if (currentGame.getPos(row, col))
          {
        	  currentGame.setPos(row,  col,  false);
          }
          else
          {
        	  currentGame.setPos(row,  col,  true);
          }
          
          updateGridColours();
    }
    
    public void timerUpdate()
    {   
        updateGridColours();
        
        if (isRunning)
        {
        	currentGame.step();
        }
        else
        {
        	return;
        }
        
    }
    
    public void updateGridColours()
    {
        int i, j;
        
        for (i = 0; i < currentGame.state.length; i++)
        {
            for (j = 0; j < currentGame.state.length; j++)
            {
               if (currentGame.state[i][j])
               {
            	   buttonArray[(i*currentGame.state.length)+j].setBackground(Color.black);
               }
               else
               {  
            	   buttonArray[(i*currentGame.state.length)+j].setBackground(Color.white);
               }
            }
        }
    }
    
}





