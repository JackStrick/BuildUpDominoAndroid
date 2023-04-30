package org.ramapo.jstrickl;

import java.util.Vector;

public class Human extends Player
{
	/* Player's tile color set and checked against */
	private char m_tileColor = 'B';

	/**
	 * To get the current players color
	 * @return Char value for players color
	 */
	public char PlayerColor()
	{
		return m_tileColor;
	}

}
