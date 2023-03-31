package org.ramapo.jstrickl;

import java.util.Vector;

public class Human extends Player{
	// Message Class Object
	private MessageOutput m_msg = new MessageOutput();

	// Data Member Color
	private char m_tileColor = 'B';

	
	
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
	Purpose: Virtual Function - Human specific player function to determine
				which tile the user would like to place, or to get help
				from the computer
	Parameters:
				a_gameBoardStack, the Vector of tiles that represents
				the entire gameboard
	Return Value:
				Vector<int> that contains the tile position in players
				hand and the position on the gameboard with which to place
				the tile
	Algorithm:
				1) Shows humans hand to the user
				2) Asks the user for the tile and stack location
					they want to put the tile onto
						a) If that is not a valid move, repeat
						b) If valid move continue
				3) If the user asks for help, call strategy to get the best
					move for the user to make
						a) If one exists, then display it to the user
						b) ask again for a user to enter a tile and location
				4) If the user asks to pass, make sure they have no playable
					tiles, and either alert them that they have a possible move
					or allow the user to pass
				5) This entire function is wrapped in a do-while loop that will
					only break after the user gives a valid tile or if the user
					was allowed to pass
				
	Assistance Received: None
	********************************************************************* */
	public Vector<Integer> Choice(Vector<Tile> a_gameBoardStack)
	{
		// Will hold the tile position from hand in [0]
		// Will hold the tile position on the gameboard in [1]
		Vector<Integer> tile_loc = new Vector<Integer>();
	
		// tile, will store the position of the tile in the users hand
		// location will store the position of the tile on the gameboard to place a tile
		int tile, location = 0;
	
		// Sets true if the tile could be played
		// False if the tile cannot be played
		boolean play;
	
		// Do-While Loop That Takes In Users' Tile Placement and Validates It Or Provides Help
		// Continues until play is true
		do
		{
			System.out.print("\n\nYour Hand\n");
			ShowHand();
			
			System.out.print("\n\nEnter 99 For Help or 89 to Pass your turn");
			tile = m_msg.TileSelection();
			if (tile != 99 && tile != 89)
			{
				location = m_msg.PlacementLocation();
			}
			// Selection was help
			if (tile == 99)
			{
				// If the user needs help, call strategy to get the best move
				System.out.print("\nYou need help");
				
				// possibleMoves will hold the gameboard position and hand position
				Vector<Vector<Integer>> possibleMoves =  new Vector<Vector<Integer>>();
				possibleMoves = Strategy(a_gameBoardStack);
				tile = m_msg.TileSelection();
				location = m_msg.PlacementLocation();
			}
			// Selection is pass
			if (tile == 89)
			{
				// Validate that the user cannot place a tile
				if (!IsValidPlaceableTile(GetHand(), a_gameBoardStack))
				{
					System.out.print("\nNo Playable Tiles So Player Can Pass");
					tile_loc.clear();
					break;
				}
				else
				{
					System.out.print("\nYou have at least 1 playable tile. You may not pass your turn.");
					tile = m_msg.TileSelection();
					location = m_msg.PlacementLocation();
				}
			}
			// Validates the move with the selected tile and location can be played
			if (!Play(a_gameBoardStack.get(location), GetHand().get(tile)))
			{
				play = false;
				System.out.print("\nThis Tile Cannot Be Placed Here\n");
			}
	
			// If it can, add to new Vector of int the tile position and gameboard location
				else
				{
					tile_loc.add(tile);
					tile_loc.add(location);
					play = true;
	
				}
		} while (!play);
	
		return tile_loc;
	}
}
