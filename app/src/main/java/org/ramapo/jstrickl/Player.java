package org.ramapo.jstrickl;
import java.io.Serializable;
import java.util.Vector;
import java.util.Collections;
import java.util.Random;

public class Player implements Serializable {
	
	// Player's Individual Hand
	private Hand m_hand = new Hand();
	// Player's bone-yard
	private Vector<Tile> m_boneYard = new Vector<Tile>();

	// Data Members
	private Random rand = new Random();
	private String strat  = new String();
	private short m_points;
	private boolean m_myTurn;
	private short m_roundsWon;

	/**
	 * Get the strategy provided by computer
	 * @return String of the tile placement strategy
	 */
	public String GetStratString()
	{
		return strat;
	}


	/**
	 * Virtual Function for Player Tile Choice
	 * @param a_gameBoardStack Vector of Tiles, the current stacks available for play
	 * @return Vector<Integer> best move to play
	 */
	public Vector<Integer> Choice(Vector<Tile> a_gameBoardStack)
	{
		Vector<Integer> parent = new Vector<Integer>();
		parent = null;
		return parent;
	}

	/**
	 * Virtual function to get the current players hand
	 * @return Char value for players color
	 */
	public char PlayerColor()
	{
		return ' ';
	}

	/**
	 * To get the current players hand
	 * @return Vector<Tile> players hand
	 */
	public Vector<Tile> GetHand()
	{
		return m_hand.GetCurrentHand();
	}

	/**
	 * To get the current players boneyard
	 * @return Vector<Tile> players boneyard
	 */
	public Vector<Tile> GetBoneYard()
	{
		return m_boneYard;
	}

	/**
	 * To get tile total of the players first tile
	 * @return int of total tile pips
	 */
	public int FirstTilePipTotal()
	{
		return m_hand.InitialTilePipTotal();
	}

	/**
	 * To check if its the current players turn
	 * @return bool - true if players turn
	 */
	public boolean IsMyTurn()
	{
		return m_myTurn;
	}

	/**
	 * To check if the tile can be played
	 * @param a_boardTile Tile on the stack to place on top of
	 * @param a_handtile Tile to place on the stack
	 * @return boolean for if the placement is valid
	 */
	public boolean Play(Tile a_boardTile, Tile a_handtile)
	{
		return IsValidPlacement(a_boardTile, a_handtile);
	}

	/**
	 * To get the players points
	 * @return short player points
	 */
	public short GetPoints()
	{
		return m_points;
	}

	/**
	 * To get the players rounds won
	 * @return Short Players rounds won count
	 */
	public short GetRoundsWon()
	{
		return m_roundsWon;
	}


	//MUTATOR START

	/**
	 * To take tiles from the deck
	 * @param a_tiles Vector of tile objects
	 */
	public void Take(Vector<Tile> a_tiles)
	{
		m_boneYard = a_tiles;
	}

	/**
	 * To gather tiles from player boneyard into hand
	 * @return Vector of Tile objects representing the tiles in the players hand
	 */
	public Vector<Tile> Draw()
	{
		Vector<Tile> playerHand = new Vector<Tile>();
		// 6 Tiles for creating gameboard
		if (m_boneYard.size() == 28)
		{
			for (int i = 0; i < 6; i++)
			{
				int selection = rand.nextInt(m_boneYard.size());
				playerHand.add(m_boneYard.remove(selection));
			}
		}
		// 5 Tiles while not last hand 
		else if (m_boneYard.size() > 4)
		{
			for (int i = 0; i < 5; i++)
			{
				int selection = rand.nextInt(m_boneYard.size());
				playerHand.add(m_boneYard.remove(selection));
				
			}
		}
		// 4 Tiles for last hand
		else
		{
			for (int i = 0; i < 3; i++)
			{
				int selection = rand.nextInt(m_boneYard.size());
				playerHand.add(m_boneYard.remove(selection));
			}
		}
		return playerHand;
	}

	/**
	 * To set the game board stacks by parsing a stack string from file
	 * @param a_stack string containing stack info
	 * @return Vector of Tile objects representing the  tiles that make up the gameboard
	 */
	public Vector<Tile> SetStacks(String a_stack)
	{
		char[] currTile = new char[a_stack.length()];
		currTile = a_stack.toCharArray();
		char color;
		int left;
		int right;
		Vector<Tile> stackTiles = new Vector<Tile>();
		for (int i = 0; i < a_stack.length() - 1; i++)
		{
			if (currTile[i] != ' ')
			{
				if (currTile[i] == 'W' || currTile[i] == 'B')
				{
					// Add each tile object to the gameboard stacks
					Tile tile = new Tile(currTile[i], currTile[i + 1] - 48, currTile[i + 2] - 48);
					stackTiles.add(tile);
				}
			}
		}
		return stackTiles;
	}

	/**
	 * To set the boneyard by parsing a boneyard string. This is used when starting from a file
	 * @param a_boneyard string containing boneyard info
	 */
	public void SetBoneyard(String a_boneyard)
	{
		char[] currBy = new char[a_boneyard.length()];
		currBy = a_boneyard.toCharArray();
		char color;
		int left;
		int right;
		
		// Loop through the string from start to finish
		for (int i = 0; i < a_boneyard.length() - 1; i++)
		{
			if (currBy[i] != ' ')
			{
				if (currBy[i] == 'W' || currBy[i] == 'B')
				{
					// Create each tile object and add them to the boneyard
					Tile tile = new Tile(currBy[i], currBy[i + 1] - 48, currBy[i + 2] - 48);
					System.out.print("PLAYER JAVA 265: " + (currBy[i+1]-48) + (currBy[i+2]-48));
					m_boneYard.add(tile);
				}
			}
		}
	}

	/**
	 * To set the players hand by parsing a hand string. This is used when starting from a file
	 * @param a_hand string containing hand info
	 */
	public void SetHand(String a_hand)
	{
		char[] currHand = new char[a_hand.length()];
		currHand = a_hand.toCharArray();
		
		Vector<Tile> handTiles = new Vector<Tile>();
		for (int i = 0; i < a_hand.length() - 1; i++)
		{
			if (currHand[i] != ' ')
			{
				if (currHand[i] == 'W' || currHand[i] == 'B')
				{
					// Add each tile object to the player hand
					Tile tile = new Tile(currHand[i], currHand[i + 1] - 48, currHand[i + 2] - 48);
					handTiles.add(tile);
				}
			}
		}
		// Adds the tiles to the players hand
		AddToHand(handTiles);
	}

	/**
	 * Drop Player points back to 0
	 */
	public void PointReset()
	{
		m_points = 0;
	}

	/**
	 * Add points to the running total
	 * @param a_points int holding the players current points after a hand
	 */
	public void SetPoints(int a_points)
	{
		m_points += a_points;
	}

	/**
	 * To set the number of rounds a player has won. This is used when starting from a file
	 * @param a_rounds short of total rounds a player has won so far
	 */
	public void SetRoundsWon(short a_rounds)
	{
		if(a_rounds >= 0)
		{
			m_roundsWon = a_rounds;
		}
		else
		{
			m_roundsWon = 0;
		}
	}

	/**
	 * Sets the players turn to true
	 */
	public void SetTurn()
	{
		m_myTurn = true;
	}

	/**
	 * Sets the players turn to false
	 */
	public void EndTurn()
	{
		m_myTurn = false;
	}

	/**
	 * Returns initial tile back to the boneyard, clears the hand, and reshuffles the boneyard
	 */
	public void ReturnTiles()
	{
		Tile playerTile = m_hand.GetInitialTile();
		m_hand.ClearHand();
		m_boneYard.add(playerTile);
		ShuffleBoneyard();
	}

	/**
	 * Sets the players hand to be equal to the Vector of tile objects being passed in
	 * @param a_playerTiles Vector of tile objects that will be the hand
	 */
	public void AddToHand(Vector<Tile> a_playerTiles)
	{
		m_hand.SetHand(a_playerTiles);
	}

	/**
	 * Removes the tile at the index given by a_loc
	 * @param a_loc int, the index of which tile to removed from the hand
	 */
	public void RemoveTileFromHand(int a_loc)
	{
		m_hand.Remove(a_loc);
	}

	/**
	 * Decreases the players points based on the tiles remaining in their hand once the hand ends, and then removes them from the hand
	 */
	/* *********************************************************************
	Function Name: DropPoints
	Purpose:
			Decreases the players points based on the tiles remaining 
			in their hand once the hand ends, and then removes them
			from the hand
	Parameters: None
	Return Value: None
	Algorithm: None
	Assistance Received: none
	********************************************************************* */
	public void DropPoints()
	{
		if (!GetHand().isEmpty())
		{
			int handSize = GetHand().size();
			for (int i = 0; i < handSize; i++)
			{
				int pips = GetHand().get(0).getTotalPips();
				if ((m_points - pips) < 0)
				{
					m_points = 0;
				}
				else
				{
					m_points -= pips;
				}
				RemoveTileFromHand(0);
			}
		}
	}

	/**
	 * Called when a player wins the round. Increase their win count by 1
	 */
	public void WonRound()
	{
		m_roundsWon += 1;
	}

	//UTILITY FUNCTIONS

	/**
	 * Used to shuffle a players boneyard after they put a tile back in their pile
	 */
	public void ShuffleBoneyard()
	{
		Collections.shuffle(m_boneYard);
	}

	/**
	 * Randomly selects a tile from the shuffled boneyard and returns it to a players hand to check who will go first
	 * @return the tile object that will determine who goes first
	 */
	public Tile InitialTile()
	{
		int selection = rand.nextInt(m_boneYard.size());

		Tile playerTile = m_boneYard.remove(selection);

		m_hand.InitialTile(playerTile);

		return playerTile;
	}

	/**
	 * The logic used by the computer to play a piece. Computer uses for each play. Human can ask for help.
	 * @param a_gameboard a Vector of tile objects representing the gameboard stacks
	 * @return Vector of a Vector of integers that contains the location to place and the position of the tile in the hand
	 */
	public Vector<Vector<Integer>> Strategy(Vector<Tile> a_gameboard)
	{
		strat = "";
		Vector<Vector<Integer>> possibleMoves = new Vector<Vector<Integer>>(2);
		Vector<Integer> locVec = new Vector<Integer>();
		Vector<Integer> tileVec = new Vector<Integer>();
		possibleMoves.add(locVec);
		possibleMoves.add(tileVec);
		// Check which player is going
		if (GetHand().get(0).getColor() == 'B')
		{
			// First the player will attempt to play a tile on top of the opposing players tile
			for (int location = 0; location < a_gameboard.size(); location++)
			{
				for (int tile = 0; tile < GetHand().size(); tile++)
				{
					if (a_gameboard.get(location).getColor() == 'W')
					{
						if (IsValidPlacement(a_gameboard.get(location), GetHand().get(tile)))
						{
							// Add the first valid placement, this will be default and checked against every time after
							if (possibleMoves.get(0).isEmpty())
							{
								possibleMoves.get(0).add(location);
								possibleMoves.get(1).add(tile);
							}
							// If there is a better position, lower tile from hand and higher tile on stack,
							// Remove possible move and add the new one
							// First check if the tile is a double tile, and get the highest possible tile it could be placed on
							else if (GetHand().get(tile).getLeftPips() == GetHand().get(tile).getRightPips() && a_gameboard.get(location).getTotalPips() >= a_gameboard.get(possibleMoves.get(0).get(0)).getTotalPips())
							{
								if (a_gameboard.get(location).getLeftPips() != a_gameboard.get(location).getRightPips() || GetHand().get(tile).getTotalPips() >= a_gameboard.get(location).getTotalPips())
								{
									possibleMoves.get(0).clear();
									possibleMoves.get(1).clear();
									possibleMoves.get(0).add(location);
									possibleMoves.get(1).add(tile);
								}
							}
							// Then get the next best with a non double tile
							else if (a_gameboard.get(location).getTotalPips() >= a_gameboard.get(possibleMoves.get(0).get(0)).getTotalPips() && GetHand().get(tile).getTotalPips() < GetHand().get(possibleMoves.get(1).get(0)).getTotalPips())
							{
								possibleMoves.get(0).clear();
								possibleMoves.get(1).clear();
								possibleMoves.get(0).add(location);
								possibleMoves.get(1).add(tile);
							}

						}
					}

				}
			}
			// This is empty if there is no location to place on top of your opponents tile
			if (possibleMoves.get(0).isEmpty())
			{
				// Now will check the rest of the board for any possible moves
				strat += "\nSince there are no moves to play on White Tiles";
				for (int location = 0; location < a_gameboard.size(); location++)
				{
					for (int tile = 0; tile < GetHand().size(); tile++)
					{
						if (IsValidPlacement(a_gameboard.get(location), GetHand().get(tile)))
						{
							if (possibleMoves.get(0).isEmpty())
							{
								possibleMoves.get(0).add(location);
								possibleMoves.get(1).add(tile);
							}
							else if (a_gameboard.get(location).getTotalPips() >= a_gameboard.get(possibleMoves.get(0).get(0)).getTotalPips() && GetHand().get(tile).getTotalPips() < GetHand().get(possibleMoves.get(1).get(0)).getTotalPips())
							{
								possibleMoves.get(0).clear();
								possibleMoves.get(1).clear();
								possibleMoves.get(0).add(location);
								possibleMoves.get(1).add(tile);
							}
						}
					}
				}
				// Based on the gameboard position of the Vector
				// Determine the stack name

				if (!possibleMoves.get(0).isEmpty()) 
				{
					String stack = null;
					if (possibleMoves.get(0).get(0) < 6)
					{
						stack = "B" + (possibleMoves.get(0).get(0) + 1);
					}
					else if (possibleMoves.get(0).get(0) > 5)
					{
						stack = "W" + (possibleMoves.get(0).get(0) - 5);
					}

					strat += "\nThe best move is to play Tile {" + GetHand().get(possibleMoves.get(1).get(0)).getColor() + GetHand().get(possibleMoves.get(1).get(0)).getLeftPips() + GetHand().get(possibleMoves.get(1).get(0)).getRightPips() + "} ";
					strat += "on stack " + stack + " \nSince that is the lowest tile and highest stack tile";
				}
				else
				{
					strat = "There is no possible move you can make. You need to skip your turn. Press Skip";
				}
			}
			else {
				String stack = null;
				if (possibleMoves.get(0).get(0) < 6)
				{
					stack = "B" + Integer.toString(possibleMoves.get(0).get(0) + 1);
				}
				else if (possibleMoves.get(0).get(0) > 5)
				{
					stack = "W" + Integer.toString(possibleMoves.get(0).get(0) - 5);
				}
				strat += "\nA tile can be played on top of a white tile";
				strat += "\nThe best move is to play Tile {" + GetHand().get(possibleMoves.get(1).get(0)).getColor() + GetHand().get(possibleMoves.get(1).get(0)).getLeftPips() + GetHand().get(possibleMoves.get(1).get(0)).getRightPips() + "} ";
				strat += "on Stack " + stack + " \nSince that is the lowest tile that can play on top of this stack tile";
			}

		}

		else if (GetHand().get(0).getColor() == 'W')
		{
			// First the player will attempt to play a tile on top of the opposing players tile
			for (int location = 0; location < a_gameboard.size(); location++)
			{
				for (int tile = 0; tile < GetHand().size(); tile++)
				{
					if (a_gameboard.get(location).getColor() == 'B')
					{
						if (IsValidPlacement(a_gameboard.get(location), GetHand().get(tile)))
						{
							// Add the first valid placement, this will be default and checked against every time after
							if (possibleMoves.get(0).isEmpty())
							{
								possibleMoves.get(0).add(location);
								possibleMoves.get(1).add(tile);
							}
							// If there is a better position, lower tile from hand and higher tile on stack,
							// Remove possible move and add the new one
							// First check if the tile is a double tile, and get the highest possible tile it could be placed on
							else if (GetHand().get(tile).getLeftPips() == GetHand().get(tile).getRightPips() && a_gameboard.get(location).getTotalPips() >= a_gameboard.get(possibleMoves.get(0).get(0)).getTotalPips())
							{
								if (a_gameboard.get(location).getLeftPips() != a_gameboard.get(location).getRightPips() || GetHand().get(tile).getTotalPips() >= a_gameboard.get(location).getTotalPips())
								{
									possibleMoves.get(0).clear();
									possibleMoves.get(1).clear();
									possibleMoves.get(0).add(location);
									possibleMoves.get(1).add(tile);
								}
							}
							// Then get the next best with a non double tile
							else if (a_gameboard.get(location).getTotalPips() >= a_gameboard.get(possibleMoves.get(0).get(0)).getTotalPips() && GetHand().get(tile).getTotalPips() < GetHand().get(possibleMoves.get(1).get(0)).getTotalPips())
							{

								possibleMoves.get(0).clear();
								possibleMoves.get(1).clear();
								possibleMoves.get(0).add(location);
								possibleMoves.get(1).add(tile);
							}
						}
					}

				}
			}
			// This is empty if there is no location to place on top of your opponents tile
			if (possibleMoves.get(0).isEmpty())
			{
				strat += "\nSince there are no moves to play on Black Tiles";
				for (int location = 0; location < a_gameboard.size(); location++)
				{
					// Now will check the rest of the board for any possible moves
					for (int tile = 0; tile < GetHand().size(); tile++)
					{
						if (IsValidPlacement(a_gameboard.get(location), GetHand().get(tile)))
						{
							if (possibleMoves.get(0).isEmpty())
							{
								possibleMoves.get(0).add(location);
								possibleMoves.get(1).add(tile);
							}
							else if (a_gameboard.get(location).getTotalPips() >= a_gameboard.get(possibleMoves.get(0).get(0)).getTotalPips() && GetHand().get(tile).getTotalPips() < GetHand().get(possibleMoves.get(1).get(0)).getTotalPips())
							{
								possibleMoves.get(0).clear();
								possibleMoves.get(1).clear();
								possibleMoves.get(0).add(location);
								possibleMoves.get(1).add(tile);
							}
						}
					}
				}
			}
			else {
				strat += "\nThe computer has a tile that can be placed on top of a human's tile";
				strat += "\nThe computer will choose the lowest tile that can be placed on the highest stack tile";
			}
		}

		return possibleMoves;
	}

	/**
	 * Checks if the players choice of tile is a valid move
	 * @param a_boardTile the tile on the gamboard to place on
	 * @param a_handtile the tile from the players hand to place
	 * @return boolean if the placement is valid
	 */
	public boolean IsValidPlacement(Tile a_boardTile, Tile a_handtile)
	{
		// If hand tile is larger than board, always true
		if (a_handtile.getTotalPips() >= a_boardTile.getTotalPips())
		{
			return true;
		}
		// If the hand tile is a double tile
		else if (a_handtile.getLeftPips() == a_handtile.getRightPips())
		{
			// Check if the board tile is a double tile, if not always return true
			if ((a_boardTile.getLeftPips() != a_boardTile.getRightPips()))
			{
				return true;
			}
			// If board tile is a double, make sure the hand tile is larger
			else if ((a_boardTile.getLeftPips() == a_boardTile.getRightPips()) && (a_handtile.getTotalPips() > a_boardTile.getTotalPips()))
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks if either player has any tiles in their hand that are valid moves
	 * @param a_playerTiles the tiles from the players hand
	 * @param a_gameBoardTiles the tiles on the gamboard
	 * @return boolean if there exists a tile in hand that can be played or not
	 */
	public boolean IsValidPlaceableTile(Vector<Tile> a_playerTiles, Vector<Tile> a_gameBoardTiles)
	{

		//Check first players tiles
		for (int i = 0; i < a_gameBoardTiles.size(); i++)
		{
			for (int j = 0; i < a_playerTiles.size(); j++)
			{
				//Tile total pips larger than on board
				if (a_playerTiles.get(i).getTotalPips() >= a_gameBoardTiles.get(i).getTotalPips())
				{
					return true;
				}
				//If a double tile, it can be placed anywhere unless the stack is a double tile greater to the one in hand
				else if (a_playerTiles.get(i).getLeftPips() == a_playerTiles.get(i).getRightPips())
				{
					if (a_gameBoardTiles.get(i).getLeftPips() != a_gameBoardTiles.get(i).getRightPips())
					{
						return true;
					}
					else if ((a_gameBoardTiles.get(i).getLeftPips() == a_gameBoardTiles.get(i).getRightPips()) && (a_playerTiles.get(i).getTotalPips() > a_gameBoardTiles.get(i).getTotalPips()))
					{
						return true;
					}
				}
			}
		}
		return false;
	}
}
