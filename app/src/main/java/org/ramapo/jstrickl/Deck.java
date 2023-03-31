package org.ramapo.jstrickl;

import java.util.Collections;
import java.util.Vector;

public class Deck {

	// Data Members
	// Vector of white tiles - computer
	private Vector<Tile> m_whiteTiles = new Vector<Tile>();

	// Vector of black tiles - human
	private Vector<Tile> m_blackTiles = new Vector<Tile>();
		
	
	/* *********************************************************************
	Function Name: Deal
	Purpose: Deals white or black tiles to player based on type (which player)
	Parameters:
				a_type, stores integer of which player is asking for tiles
					1 = Human Type
					2 = Computer Type
	Return Value:
				vector<int> that contains all tiles for players boneyard
	Algorithm:
				1) Checks which player is asking for tiles
					a) If Human, give human (black) tiles
					b) If Computer, give computer (white) tiles
			
	Assistance Received: none
	********************************************************************* */
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


	/* *********************************************************************
	Function Name: GenerateTiles
	Purpose: Creates each tile object in the deck
	Parameters: None
	Return Value: None
	Algorithm:
				1) Black Tiles: 
					a) For loop for all possible tile pip combination
					b) Adds them to a vector of tiles
					c) Shuffle the entire deck of black tiles
				2) White Tiles: 
					a) For loop for all possible tile pip combination
					b) Adds them to a vector of tiles
					c) Shuffle the entire deck of white tiles
	Assistance Received: None
	********************************************************************* */
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
