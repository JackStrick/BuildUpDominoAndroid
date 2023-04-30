package org.ramapo.jstrickl;
import java.io.Serializable;
import java.util.Vector;

public class Hand implements Serializable
{
	// Data Member
	// Player Objects Personal Hand
	private Vector<Tile> m_playerHand = new Vector<Tile>();

	/**
	 * Grabs the players first tile that will be used to determine who goes first
	 * @return Tile that is in the first place of the players' hand
	 */
	public Tile GetInitialTile()
	{
		Tile tile = m_playerHand.get(0);
		return tile;
	}

	/**
	 * Checks the total pips at players first tile
	 * @return Int value of the players first tiles' total pips
	 */
	public int InitialTilePipTotal()
	{
		return m_playerHand.get(0).getTotalPips();
	}

	/**
	 * Returns a vector of tile objects that represents a players current hand
	 * @return Vector<Tile> of players current hand
	 */
	public Vector<Tile> GetCurrentHand()
	{
		return m_playerHand;
	}

	/**
	 * Adds the initial tile to a users hand
	 * @param a_tile Tile, first taken from the boneyard
	 */
	public void InitialTile(Tile a_tile)
	{
		m_playerHand.add(a_tile);
	}

	/**
	 * Adds the initial tile to a users hand
	 * @param a_playerTiles Vector<Tile>  that represents tiles that will be added to the player's hand
	 */
	public void SetHand(Vector<Tile> a_playerTiles)
	{
		for (int i = 0; i < a_playerTiles.size(); i++)
		{
			m_playerHand.add(a_playerTiles.get(i));
		}
	}

	/**
	 * Clear any tiles left in the player hand vector<Tile>
	 */
	public void ClearHand()
	{
		m_playerHand.clear();
	}

	/**
	 * Remove the tile at given location to erase a specific tile object from the player's hand
	 * @param a_loc the location in the hand vector to remove a tile from
	 */
	public void Remove(int a_loc)
	{
		m_playerHand.remove(a_loc);
	}

	/**
	 * Display the users hand
	 */
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
