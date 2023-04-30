package org.ramapo.jstrickl;

import java.io.Serializable;

public class Tile implements Serializable {

	// Data Members
	private char m_Color; 
	private int m_LeftPips;
	private int m_RightPips;

	/**
	 * Creation of every tile, sets the color, left pips, and right pips
	 * @param color char, that decides which color the tile is
	 * @param leftPips int, the left pips of the tile
	 * @param rightPips int, the right pips of the tile
	 */
	public Tile(char color, int leftPips, int rightPips)
	{
		m_Color = color;
		m_LeftPips = leftPips;
		m_RightPips = rightPips;
	}

	/**
	 * Returns the specific tile color
	 * @return Char of the tile color
	 */
	public char getColor()
	{
		return m_Color;
	}

	/**
	 * Gets the specific tile left pips quantity
	 * @return int m_LeftPips: Pips count on left side of tile
	 */
	public int getLeftPips()
	{
		return m_LeftPips;
	}

	/**
	 * Gets the specific tile right pips quantity
	 * @return int m_RightPips: Pips count on right side of tile
	 */
	public int getRightPips()
	{
		return m_RightPips;
	}

	/**
	 * Gets the specific tile total pips quantity (left + right)
	 * @return int Total tile pips
	 */
	public int getTotalPips()
	{
		return m_LeftPips + m_RightPips;
	}
}
