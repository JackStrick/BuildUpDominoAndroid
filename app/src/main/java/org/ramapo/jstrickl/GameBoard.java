package org.ramapo.jstrickl;

import java.io.Serializable;
import java.util.Vector;

import kotlin.jvm.internal.SerializedIr;

public class GameBoard implements Serializable {

	// Data Members
	// Stack to store entire gameboard
	private Vector<Tile> m_Stack = new Vector<Tile>();
	
	/* *********************************************************************
	Function Name: GetDominoStack
	Purpose: Used to retrieve gameboard stacks
	Parameters: None
	Return Value: 
				vector<Tile>: Returns the stacks of the gameboard
	Algorithm: None
	Assistance Received: None
	********************************************************************* */
	public Vector<Tile> GetDominoStack()
	{
		return m_Stack;
	}

	/* *********************************************************************
	Function Name: DisplayGameBoard
	Purpose: Displays the current stacks to the user
	Parameters: None
	Return Value: None
	Algorithm:
				1) Loops through and displays all 6 Stack names for Black
				2) Loops through gameboard stacks and displays all 12
				3) Loops through and displays all 6 Stack names for White
	Assistance Received: None
	********************************************************************* */
	public void DisplayGameBoard()
	{
		System.out.print("\n\n\t\t\tGAMEBOARD");
		System.out.print("\n\t");
		for (int i = 1; i < 7; i++)
		{
			System.out.print("B" + i + "\t");
		}
		System.out.print("\n");
		for (int i = 0; i < m_Stack.size(); i++)
		{

			if (i == 6)
			{
				System.out.print("\n");
			}
			System.out.print("\t{" + m_Stack.get(i).getColor());
			System.out.print(m_Stack.get(i).getLeftPips());
			System.out.print(m_Stack.get(i).getRightPips() + "} ");
		}
		System.out.print("\n\t");
		for (int i = 1; i < 7; i++)
		{
			System.out.print("W" + i + "\t");
		}
	}

	/* *********************************************************************
	Function Name: SetGameBoard
	Purpose: Sets the stack vector that holds all of the current stack tiles
	Parameters: 
				a_tiles, vector of tiles that should be placed onto the
				gameboard stack
	Return Value: None
	Algorithm:
				1) Loops through given tile vector, a_tile, and pushes the stack
					objects on to the gameboard vector
	Assistance Received: None
	********************************************************************* */
	public void SetGameBoard(Vector<Tile> a_tiles)
	{
		for (int i = 0; i < a_tiles.size(); i++)
		{
			m_Stack.add(a_tiles.get(i));
		}
	}

	/* *********************************************************************
	Function Name: TilePlacement
	Purpose: Once the player decides on a tile, replaces the stack in
				given position with new tile object
	Parameters:
				a_tile, the tile object selected by the player
				a_pos, the location on the gameboard where the player chose
				to place the tile
	Return Value: None
	Algorithm:
				1) Based on position, creates string that is the equivalent
					to its name (B1 through W6)
				2) Displays where the player is placing the tile
				3) Replaces the tile object at given location
	Assistance Received: None
	********************************************************************* */
	public void TilePlacement(Tile a_tile, int a_pos)
	{
		String position = null;
		if (a_pos > 5)
		{
			position = "W" + Integer.toString(a_pos - 5);
		}

		else if (a_pos <= 5)
		{
			position = "B" + Integer.toString(a_pos + 1);
		}

		System.out.print("placing tile {" + a_tile.getColor() + a_tile.getLeftPips() + a_tile.getRightPips() + "} ");
		System.out.print("on stack " + position);
		m_Stack.set(a_pos, a_tile);
	}

	/* *********************************************************************
	Function Name: ClearBoard
	Purpose: Clears the gameboard vector. Typically used after a round is over.
	Parameters: None
	Return Value: None
	Algorithm:
				1) Clear gameboard vector of tiles
	Assistance Received: None
	********************************************************************* */
	public void ClearBoard()
	{
		m_Stack.clear();
	}
}