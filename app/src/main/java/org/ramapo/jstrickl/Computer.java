package org.ramapo.jstrickl;

import java.io.Serializable;
import java.util.Vector;

public class Computer extends Player
{
	// Data Member Color
	private char m_tileColor = 'W';

	/**
	 To get the current players color
	 @return Char value for players color
	 */
	public char PlayerColor()
	{
		return m_tileColor;
	}


	/**
	 Computer specific player function to determine which tile to place
	 @param a_gameBoardStack Vector of tiles that represents the entire gameboard
	 @return vector<int> that contains the tile position in players hand and the position on the gameboard with which to place the tile
	 */
	public Vector<Integer> Choice(Vector<Tile> a_gameBoardStack)
	{
		// Will hold the tile position from hand in [0]
		// Will hold the tile position on the gameboard in [1]
		Vector<Integer> tile_loc = new Vector<Integer>();

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
