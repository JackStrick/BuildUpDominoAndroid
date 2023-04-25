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
	private short m_points;
	private boolean m_myTurn;
	private short m_roundsWon;

	/* *********************************************************************
	Function Name: Choice
	Purpose: Virtual Function for Player Tile Choice
	Parameters: a_gameBoardStack - the current stacks available for play
	Return Value: Vector<Integer> best move to play
	Algorithm: None
	Assistance Received: none
	********************************************************************* */
	public Vector<Integer> Choice(Vector<Tile> a_gameBoardStack)
	{
		Vector<Integer> parent = new Vector<Integer>();
		parent = null;
		return parent;
	}
	
	/* *********************************************************************
	Function Name: GetHand
	Purpose: To get the current players hand
	Parameters: None
	Return Value: Char value for players color
	Algorithm: None
	Assistance Received: none
	********************************************************************* */
	public char PlayerColor()
	{
		return ' ';
	}
	
	/* *********************************************************************
	Function Name: GetHand
	Purpose: To get the current players hand
	Parameters: None
	Return Value: Vector<Tile> players hand
	Algorithm: None
	Assistance Received: none
	********************************************************************* */
	public Vector<Tile> GetHand()
	{
		return m_hand.GetCurrentHand();
	}

	/* *********************************************************************
	Function Name: GetBoneYard
	Purpose: To get the current players boneyard
	Parameters: None
	Return Value: Vector<Tile> players boneyard
	Algorithm: None
	Assistance Received: none
	********************************************************************* */
	public Vector<Tile> GetBoneYard()
	{
		return m_boneYard;
	}

	/* *********************************************************************
	Function Name: FirstTilePipTotal
	Purpose: To get tile total of the players first tile
	Parameters: None
	Return Value: int of total tile pips
	Algorithm: None
	Assistance Received: none
	********************************************************************* */
	public int FirstTilePipTotal()
	{
		return m_hand.InitialTilePipTotal();
	}

	/* *********************************************************************
	Function Name: IsMyTurn
	Purpose: To check if its the current players turn
	Parameters: None
	Return Value: bool - true if players turn
	Algorithm: None
	Assistance Received: none
	********************************************************************* */
	public boolean IsMyTurn()
	{
		return m_myTurn;
	}

	/* *********************************************************************
	Function Name: ShowHand
	Purpose: To Display the current players hand to the console
	Parameters: None
	Return Value: None
	Algorithm: None
	Assistance Received: none
	********************************************************************* */
	public void ShowHand()
	{
		m_hand.DisplayHand();
	}

	/* *********************************************************************
	Function Name: Play
	Purpose: To check if the tile can be played
	Parameters: 
				a_boardTile, 
				a_handtile, 
	Return Value: bool - True when tile choice and location choice
							are allowed to be placed
	Algorithm: None
	Assistance Received: none
	********************************************************************* */
	public boolean Play(Tile a_boardTile, Tile a_handtile)
	{
		return IsValidPlacement(a_boardTile, a_handtile);
	}

	/* *********************************************************************
	Function Name: GetPoints
	Purpose: To get the players points
	Parameters: None
	Return Value: Players Points
	Algorithm: None
	Assistance Received: none
	********************************************************************* */
	public short GetPoints()
	{
		return m_points;
	}

	/* *********************************************************************
	Function Name: GetRoundsWin
	Purpose: To get the players rounds won
	Parameters: None
	Return Value: Players rounds won count
	Algorithm: None
	Assistance Received: none
	********************************************************************* */
	public short GetRoundsWon()
	{
		return m_roundsWon;
	}

	/* *********************************************************************
	Function Name: DisplayBoneyard
	Purpose: To display the players boneyard to console
	Parameters: None
	Return Value: None
	Algorithm: None
	Assistance Received: none
	********************************************************************* */
	public void DisplayBoneyard()
	{
		for (int i = 0; i < m_boneYard.size(); i++)
		{
			System.out.print("{" + m_boneYard.get(i).getColor());
			System.out.print(m_boneYard.get(i).getLeftPips());
			System.out.print(m_boneYard.get(i).getRightPips() + "} ");
		}
	}


	//MUTATOR START
	/* *********************************************************************
	Function Name: Take
	Purpose: To take tiles from the deck
	Parameters:
				a_tiles, Vector of tile objects
	Return Value: None
	Algorithm: None
	Assistance Received: none
	********************************************************************* */
	public void Take(Vector<Tile> a_tiles)
	{
		m_boneYard = a_tiles;
	}

	/* *********************************************************************
	Function Name: Draw
	Purpose: To gather tiles from player boneyard into hand
	Parameters: None
	Return Value: 
					playerHand, a Vector of Tile objects representing the 
					tiles in the players hand
	Algorithm: 
				1) Checks the size of the boneyard to indicate how far
					players are into the game
					a) This gets used to create the gameboard and
						also every hand after
					b) The last hand only draws 4 tiles 
	Assistance Received: none
	********************************************************************* */
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


	/* *********************************************************************
	Function Name: SetStacks
	Purpose: To set the gameboard stacks by parsing a stack string
				This is used when starting from a file 
	Parameters: string stack, string containing stack info
	Return Value:
					stackTiles, a Vector of Tile objects representing the
					tiles that make up the gameboard
	Algorithm: None
	Assistance Received: none
	********************************************************************* */
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

	/* *********************************************************************
	Function Name: SetBoneyard
	Purpose: To set the boneyard by parsing a boneyard string
				This is used when starting from a file
	Parameters: string a_boneyard, string containing boneyard info
	Return Value: None
	Algorithm: None
	Assistance Received: none
	********************************************************************* */
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

	/* *********************************************************************
	Function Name: SetHand
	Purpose: To set the players hand by parsing a hand string
				This is used when starting from a file
	Parameters: string a_hand, string containing hand info
	Return Value: None
	Algorithm: None
	Assistance Received: none
	********************************************************************* */
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

	/* *********************************************************************
	Function Name: PointReset
	Purpose:
			Drop Player points back to 0
	Parameters: None
	Return Value: None
	Algorithm: None
	Assistance Received: none
	********************************************************************* */
	public void PointReset()
	{
		m_points = 0;
	}

	/* *********************************************************************
	Function Name: SetPoint
	Purpose: 
			Add points to the running total
	Parameters: 
				a_points, holding the players current points after
				a hand
	Return Value: None
	Algorithm: None
	Assistance Received: none
	********************************************************************* */
	public void SetPoints(int a_points)
	{
		m_points += a_points;
	}


	/* *********************************************************************
	Function Name: SetRoundsWon
	Purpose: To set the number of rounds a player has won
				This is used when starting from a file
	Parameters: 
				a_rounds, unsugned short of total rounds a player
				has won so far
	Return Value: None
	Algorithm: None
	Assistance Received: none
	********************************************************************* */
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

	/* *********************************************************************
	Function Name: SetTurn
	Purpose:
			Sets the players turn to true
	Parameters: None
	Return Value: None
	Algorithm: None
	Assistance Received: none
	********************************************************************* */
	public void SetTurn()
	{
		m_myTurn = true;
	}

	/* *********************************************************************
	Function Name: EndTurn
	Purpose:
			Sets the players turn to false
	Parameters: None
	Return Value: None
	Algorithm: None
	Assistance Received: none
	********************************************************************* */
	public void EndTurn()
	{
		m_myTurn = false;
	}


	/* *********************************************************************
	Function Name: ReturnTiles
	Purpose:
			Returns initial tile back to the boneyard, clears the hand,
			and reshuffles the boneyard
	Parameters: None
	Return Value: None
	Algorithm: None
	Assistance Received: none
	********************************************************************* */
	public void ReturnTiles()
	{
		Tile playerTile = m_hand.GetInitialTile();
		m_hand.ClearHand();
		m_boneYard.add(playerTile);
		ShuffleBoneyard();
	}

	/* *********************************************************************
	Function Name: AddHand
	Purpose:
			Sets the players hand to be equal to the Vector of
			tile objects being passed in
	Parameters: 
				a_playerTiles, Vector of tile objects
	Return Value: None
	Algorithm: None
	Assistance Received: none
	********************************************************************* */
	public void AddToHand(Vector<Tile> a_playerTiles)
	{
		m_hand.SetHand(a_playerTiles);
	}

	/* *********************************************************************
	Function Name: RemoveTileFromHand
	Purpose:
			Removes the tile at the index given by a_loc
	Parameters: a_loc, the index of which tile to removed from
				the hand
	Return Value: None
	Algorithm: None
	Assistance Received: none
	********************************************************************* */
	public void RemoveTileFromHand(int a_loc)
	{
		m_hand.Remove(a_loc);
	}


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


	/* *********************************************************************
	Function Name: WonRound
	Purpose:
			Called when a player wins the round. Increase their win
			count by 1
	Parameters: None
	Return Value: None
	Algorithm: None
	Assistance Received: none
	********************************************************************* */
	public void WonRound()
	{
		m_roundsWon += 1;
	}

	//UTILITY FUNCTIONS
	/* *********************************************************************
	Function Name: ShuffleBoneyard
	Purpose:
			Used to shuffle a players boneyard after they put a
			tile back in their pile
	Parameters: None
	Return Value: None
	Algorithm: None
	Assistance Received: none
	********************************************************************* */
	public void ShuffleBoneyard()
	{
		Collections.shuffle(m_boneYard);
	}

	/* *********************************************************************
	Function Name: InitialTile
	Purpose:
			Randomly selects a tile from the shuffled boneyard and
			returns it to a players hand to check who will go first
	Parameters: None
	Return Value: 
				playerTile, the tile object that will determine who
				goes first
	Algorithm: None
	Assistance Received: none
	********************************************************************* */
	public Tile InitialTile()
	{

		int selection = rand.nextInt(m_boneYard.size());

		Tile playerTile = m_boneYard.remove(selection);

		m_hand.InitialTile(playerTile);

		return playerTile;
	}




	/* *********************************************************************
	Function Name: Strategy
	Purpose:
			The logic used by the computer to play a peice. Computer uses
			for each play. Human can ask for help.
	Parameters: 
				a_gameboard, a Vector of tile objects representing
				the gameboard stacks
	Return Value:
				possibleMoves, a Vector of a Vector of integers
				possibleMoves.get(0) = the location on the gameboard
				possibleMoves.get(1) = the tile position in player hand
	Algorithm: 
				1) First check if the player has B or W tiles in hand
					a) The player with B tiles will prefer to place on
						top of white tiles
					b) The player with W tiles will prefer to place on
						top of black tiles
				2) The strategy:
						a) Loop through all of the stacks with
							every tile in hand.
						b) Verify if each is valid to place
						c) If a tile is valid and possibleMoves is empty,
							add the tile location and stack location
						d) Every valid tile after, check which is the 
							lowest possible tile that can be placed on the
							highest possible stack of opposing teams tiles
						e) If there are no options to place on top of the
							opposing players stacks	place on the best of your own
	Assistance Received: 
		https://linuxhint.com/append-Vector-cpp/#:~:text=Appending%20to%20a%20Vector%20means,to%20append%20is%20push_back().
	********************************************************************* */
	public Vector<Vector<Integer>> Strategy(Vector<Tile> a_gameboard)
	{
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
				System.out.print("\nSince there are no moves to play on White Tiles");
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

					System.out.print("\nThe best move is to play Tile {" + GetHand().get(possibleMoves.get(1).get(0)).getColor() + GetHand().get(possibleMoves.get(1).get(0)).getLeftPips() + GetHand().get(possibleMoves.get(1).get(0)).getRightPips() + "} ");
					System.out.print("on stack " + stack + " \nSince that is the lowest tile and highest stack tile");
				}
				else
				{
					System.out.print("\nThere is no possible move you can make. You need to skip your turn. Enter 89 to skip");
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
				System.out.print("\nA tile can be played on top of a white tile");
				System.out.print("\nThe best move is to play Tile {" + GetHand().get(possibleMoves.get(1).get(0)).getColor() + GetHand().get(possibleMoves.get(1).get(0)).getLeftPips() + GetHand().get(possibleMoves.get(1).get(0)).getRightPips() + "} ");
				System.out.print("on Stack " + stack + " \nSince that is the lowest tile that can play on top of this stack tile");
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
				System.out.print("\nSince there are no moves to play on Black Tiles");
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
				System.out.print("\nThe computer has a tile that can be placed on top of a human's tile");
				System.out.print("\nThe computer will choose the lowest tile that can be placed on the highest stack tile");
			}
		}

		return possibleMoves;
	}

	/* *********************************************************************
	Function Name: IsValidPlacement
	Purpose:
			Checks if the players choice of tile is a valid
			move
	Parameters:
				Tile a_boardTile, the tile on the gamboard
				Tile a_handtile, the tile from the players hand
	Return Value:
				bool -> True - If move is valid
				bool -> False - If move is not valid
	Algorithm: None
	Assistance Received:
		https://www.tutorialspoint.com/how-to-print-out-the-contents-of-a-Vector-in-cplusplus
	********************************************************************* */
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

	/* *********************************************************************
	Function Name: IsValidPlaceableTile
	Purpose:
			Checks if either player has any tiles in their hand that are
			valid moves
	Parameters:
				a_gameBoardTiles, the tiles on the gamboard
				a_playerTiles, the tiles from the players hand
	Return Value:
				bool -> True - If there exists a tile in hand that can be played
				bool -> False - If there is no tile in hand that can be played
	Algorithm: None
	Assistance Received: None
	********************************************************************* */
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
