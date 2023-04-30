package org.ramapo.jstrickl;


public class Tournament {
	private Round m_round = new Round();
	int m_numRounds;

	/**
	 * Gets the round object
	 * @return Round object that will control the game
	 */
	public Round getRound()
	{
		return m_round;
	}

	/**
	 * Sets the number of rounds player so far
	 * @param count int of the number of rounds so far
	 */
	public void SetRounds(int count)
	{
		m_numRounds = count;
	}


}
