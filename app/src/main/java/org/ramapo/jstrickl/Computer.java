package org.ramapo.jstrickl;

import java.util.Vector;

public class Computer extends Player{
	
	// Data Member Color
	private char m_tileColor = 'W';
	
	
	
	
	/* *********************************************************************
	Function Name: PlayerColor
	Purpose: To get the current players color
	Return Value: Char value for players color
	Algorithm: None
	Assistance Received: none
	********************************************************************* */
	public char PlayerColor()
	{
		return m_tileColor;
	}
	
	/* *********************************************************************
	Function Name: Choice
	Purpose: Virtual Function - Computer specific player function to determine
				which tile to place
	Parameters: 
				a_gameBoardStack, the vector of tiles that represents
				the entire gameboard
	Return Value: 
				vector<int> that contains the tile position in players 
				hand and the position on the gameboard with which to place
				the tile
	Algorithm:
				1) Shows the computers hand to the user
					(For demo purposes)
				2) Calls for the Player:Strategy function to get a move
					and returns a vector of a vector of ints that holds 
					the possible move and the location in a hand of which
					tile to place
				3) If there is a move in possibleMoves, then place the move
					onto a vector of ints that will be returned 
				4) If possibleMoves is empty, the computer cannot play
					so it should not give a move
	Assistance Received: none
	********************************************************************* */
	public Vector<Integer> Choice(Vector<Tile> a_gameBoardStack)
	{
		// Will hold the tile position from hand in [0]
		// Will hold the tile position on the gameboard in [1]
		Vector<Integer> tile_loc = new Vector<Integer>();

		System.out.print("\n\nComputer's Hand\n");
		ShowHand();

		// possibleMoves will hold the gameboard position and hand position
		Vector<Vector<Integer>> possibleMoves = Strategy(a_gameBoardStack);

		// Check that possibleMoves has a move, return that move
		if (possibleMoves.get(0).size() > 0)
		{
			System.out.print("\nThe computer is ");
			tile_loc.add(possibleMoves.get(1).get(0));
			tile_loc.add(possibleMoves.get(0).get(0));
			return tile_loc;
		}
		else
		{
			System.out.print("\nComputer Can't Place a Tile");
			return null;
		}
	}
}
