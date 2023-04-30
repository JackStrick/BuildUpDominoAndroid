package org.ramapo.jstrickl;

import java.io.Serializable;
import java.util.Vector;

import kotlin.jvm.internal.SerializedIr;

public class GameBoard implements Serializable {

	// Data Members
	// Stack to store entire gameboard
	private Vector<Tile> m_Stack = new Vector<Tile>();
	private String placementString = new String();


	/**
	 Grabs the string to display last move made
	 @return String of what move the last player made
	 */
	public String GetPlacementString()
	{
		return placementString;
	}

	/**
	 Used to retrieve gameboard stacks
	 @return Vector<Tile> representing the stacks of the gameboard
	 */
	public Vector<Tile> GetDominoStack()
	{
		return m_Stack;
	}

	/**
	 Displays the current stacks to the user
	 */
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

	/**
	 Sets the stack vector that holds all of the current stack tiles
	 @param a_tiles Vector of tiles to be placed on the gameboard stack
	 */
	public void SetGameBoard(Vector<Tile> a_tiles)
	{
		for (int i = 0; i < a_tiles.size(); i++)
		{
			m_Stack.add(a_tiles.get(i));
		}
	}

	/**
	 Replaces the stack in given position with new tile object
	 @param a_tile Tile selected by player to place
	 @param a_pos Int that represents the stack location
	 */
	public void TilePlacement(Tile a_tile, int a_pos)
	{
		placementString = "";
		String position = null;
		if (a_pos > 5)
		{
			position = "W" + Integer.toString(a_pos - 5);
		}

		else if (a_pos <= 5)
		{
			position = "B" + Integer.toString(a_pos + 1);
		}

		placementString += "placing tile {" + a_tile.getColor() + a_tile.getLeftPips() + a_tile.getRightPips() + "} ";
		placementString += "on stack " + position;
		m_Stack.set(a_pos, a_tile);
	}

	/**
	 Clears the game board vector. Typically used after a round is over.
	 */
	public void ClearBoard()
	{
		m_Stack.clear();
	}
}