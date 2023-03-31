package org.ramapo.jstrickl;
import java.util.Vector;

public class Hand {

	// Data Member
	// Player Objects Personal Hand
	private Vector<Tile> m_playerHand = new Vector<Tile>();
	
	/* *********************************************************************
	Function Name: GetInitialTile
	Purpose:	Grabs the players first tile that will be used to determine
				who goes first
	Parameters: None
	Return Value: 
				Tile Object
	Algorithm:
				1) Since the hands are shuffled we can grab the tile at
					the first position
	Assistance Received: None
	********************************************************************* */
	public Tile GetInitialTile()
	{
		Tile tile = m_playerHand.get(0);
		return tile;
	}

	/* *********************************************************************
	Function Name: InitialTilePipTotal
	Purpose:	Checks the total pips at players first tile
	Parameters: None
	Return Value:
				int value of the players first tiles' total pips
	Algorithm:
				1) Gets the tiles in the first position, total pips
					and returns the value
	Assistance Received: None
	********************************************************************* */
	public int InitialTilePipTotal()
	{
		return m_playerHand.get(0).getTotalPips();
	}

	/* *********************************************************************
	Function Name: GetCurrentHand
	Purpose:	Returns a vector of tile objects that represents
				a players current hand
	Parameters: None
	Return Value:
				vector<Tile> - Vector of tile objects
	Algorithm: None
	Assistance Received: None
	********************************************************************* */
	public Vector<Tile> GetCurrentHand()
	{
		return m_playerHand;
	}

	/* *********************************************************************
	Function Name: InitialTile
	Purpose:	Adds the initial tile to a users hand
	Parameters: 
				Tile Object - first tile being taken from boneyard
	Return Value: None
	Algorithm: None
	Assistance Received: None
	********************************************************************* */
	public void InitialTile(Tile a_tile)
	{
		m_playerHand.add(a_tile);
	}


	/* *********************************************************************
	Function Name: SetHand
	Purpose:	Sets a players hand during the game
	Parameters:
				a_playerTiles, a vector of tiles that represents 
				tiles that can be added to the hand
	Return Value: None
	Algorithm: None
	Assistance Received: None
	********************************************************************* */
	public void SetHand(Vector<Tile> a_playerTiles)
	{
		for (int i = 0; i < a_playerTiles.size(); i++)
		{
			m_playerHand.add(a_playerTiles.get(i));
		}
	}

	/* *********************************************************************
	Function Name: ClearHand
	Purpose:	Clear any tiles left in the player hand vector<Tile>
	Parameters: None
	Return Value: None
	Algorithm: None
	Assistance Received: None
	********************************************************************* */
	public void ClearHand()
	{
		m_playerHand.clear();
	}

	/* *********************************************************************
	Function Name: Remove
	Purpose:	Remove the tile at given location to erase a specific tile
				object from the player's hand 
	Parameters: 
				a_loc, the location in the hand vector to remove a
				tile from
	Return Value: None
	Algorithm: None
	Assistance Received: None
	********************************************************************* */
	public void Remove(int a_loc)
	{
		m_playerHand.remove(a_loc);
	}

	/* *********************************************************************
	Function Name: DisplayHand
	Purpose:	Display the users hand
	Parameters: None
	Return Value: None
	Algorithm: None
	Assistance Received: None
	********************************************************************* */
	public void DisplayHand()
	{
		for (int i = 0; i < m_playerHand.size(); i++)
		{
			System.out.print("{" + m_playerHand.get(i).getColor());
			System.out.print(m_playerHand.get(i).getLeftPips());
			System.out.print(m_playerHand.get(i).getRightPips() + "} ");
		}
		System.out.print("\n  ");
		for (int i = 0; i < m_playerHand.size(); i++)
		{
			System.out.print(i + "     ");
		}
	}
}
