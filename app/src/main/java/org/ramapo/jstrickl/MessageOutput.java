package org.ramapo.jstrickl;
import java.io.Serializable;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class MessageOutput implements Serializable
{
	/**
	 * Creates string to display which user is going to go first in a hand
	 * @param a_first Int of the first player
	 * @param a_humanTotal Int of the total human first tile pips
	 * @param a_computerTotal Int of the total computer first tile pips
	 * @return String of the player who is first and the total of both player's tiles
	 */
	public String FirstUp(int a_first, int a_humanTotal, int a_computerTotal)
	{
		String firstUp = "\nYour tile value was " + Integer.toString(a_humanTotal) +
				"\nThe Computer's tile value was " + Integer.toString(a_computerTotal);
		if (a_first == 1)
		{
			firstUp += "\nYour tile value was larger so you'll go first!";
		}
		else if (a_first == 2)
		{
			firstUp += "\nThe computer tile value was larger so the computer will go first!";
		}

		return firstUp;
	}

	/**
	 * Creates string to of the current score board
	 * @param a_playerScore Short of the total human score
	 * @param a_cpuScore Short of the total computer score
	 * @return String of total score of each player
	 */
	public String DisplayScore(short a_playerScore, short a_cpuScore)
	{
		String score = "------------------------------------------------";
		score += "\nHuman: " + a_playerScore;
		score += "\nComputer: "  + a_cpuScore;
		return score;
	}

	/**
	 * Creates String of the total rounds won by both players to be announce tournament winner, or tie.
	 * @param a_cpuRounds Short of the total rounds the computer won
	 * @param a_humanRounds Short of the total rounds the human won
	 * @return String of how many rounds won by each player and final result
	 */
	public String Finished(short a_cpuRounds, short a_humanRounds)
	{
		String finish = "";
		finish += "\n------------------------------------------------";
		finish += "\nHuman\nRounds: " + a_humanRounds;
		finish += "\nComputer\nRounds: " + a_cpuRounds;
		if (a_cpuRounds > a_humanRounds)
		{
			finish += "\n\nThe computer has won :(";
		}
		else if (a_cpuRounds < a_humanRounds)
		{
			finish += "\n\nYou have won!";
		}
		else if (a_cpuRounds == a_humanRounds)
		{
			finish += "\n\nThe tournament has ended in a tie";
		}
		finish += "\n\nThank you for playing!\n";
		return finish;
	}
		
}
