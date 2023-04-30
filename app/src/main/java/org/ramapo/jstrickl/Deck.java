package org.ramapo.jstrickl;

import java.io.Serializable;
import java.util.Collections;
import java.util.Vector;

public class Deck implements Serializable
{

	// Data Members
	// Vector of white tiles - computer
	private Vector<Tile> m_whiteTiles = new Vector<Tile>();

	// Vector of black tiles - human
	private Vector<Tile> m_blackTiles = new Vector<Tile>();

	/**
	 Deals white or black tiles to player based on type (which player)
	 @param a_color Character of W or B to determine the player type
	 @return vector<int> that contains all tiles for players boneyard
	 */
	public Vector<Tile> Deal(char a_color)
	{
		if (a_color == 'B')
		{
			return m_blackTiles;
		}
		else if (a_color == 'W')
		{
			return m_whiteTiles;
		}
	
		return null;
	}

	/**
	 Creates each tile object in the deck
	 */
	public void GenerateTiles()
	{
		m_blackTiles.clear();
		for (int leftPips = 0; leftPips < 7; leftPips++)
		{
			for (int rightPips = leftPips; rightPips < 7; rightPips++)
			{
				Tile tile = new Tile('B', leftPips, rightPips);
				m_blackTiles.add(tile);
			}
		}

		// Randomly Changes Tile Position in vector
		Collections.shuffle(m_blackTiles);

		m_whiteTiles.clear();
		for (int leftPips = 0; leftPips < 7; leftPips++)
		{
			for (int rightPips = leftPips; rightPips < 7; rightPips++)
			{
				Tile tile = new Tile('W', leftPips, rightPips);
				m_whiteTiles.add(tile);
			}
		}

		// Randomly Changes Tile Position in vector
		Collections.shuffle(m_whiteTiles);
	}
}
