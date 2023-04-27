package org.ramapo.jstrickl;
import java.io.Serializable;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class MessageOutput implements Serializable {
	

	/* *********************************************************************
	Function Name: Continue
	Purpose:	Asks if the user wants to play a new round
	Parameters: None
	Return Value:
				bool -> True - The player wants to play a new round
				bool -> False - The player would like to end the game
	Algorithm: None
	Assistance Received: None
	********************************************************************* */
	public boolean Continue()
	{
		Scanner userIn = new Scanner(System.in);
		System.out.print("\nWould you like to play a new round?\n\n");

		int choice = 0;
		while ((choice != 1) && (choice != 2))
		{
			System.out.print("Enter the number to confirm your choice:\n" + "1) Yes! :)\n2) No Thanks :(\n");
			choice = userIn.nextInt();
			userIn.nextLine();
		}

		
		if (choice != 1)
		{
			System.out.print("\nTournament Complete\n");
			System.out.print("\nCalculating Score...");
			return false;
		}

		return true;
	}

	/* *********************************************************************
	Function Name: TileSelection
	Purpose:	Asks where the user wants to place a tile
	Parameters: None
	Return Value:
				int choice, which represents the location of the tile 
				in the user's hand to place
	Algorithm: None
	Assistance Received: None
	********************************************************************* */
	public  int TileSelection()
	{
		Scanner userIn = new Scanner(System.in);
		int choice = 0;
		try
		{
			System.out.print("\n\nWhich Tile Would You Like To Place?\n");
			choice = userIn.nextInt();
			userIn.nextLine();
		}
		catch (NoSuchElementException e)
		{
			System.out.print(e);
		}
		
		
		return choice;
	}

	/* *********************************************************************
	Function Name: PlacementLocation
	Purpose:	Asks where the user wants to place a tile
	Parameters: None
	Return Value:
				int choice, which represents the location on the gameboard
				the user wants to place a tile
	Algorithm: None
	Assistance Received: None
	********************************************************************* */
	public int PlacementLocation()
	{
		Scanner userIn = new Scanner(System.in);
		int choice = 0;
		String location;
		char color;
		int stackNum;

		do
		{
			System.out.print("\nWhere would you like to place a tile?\n");
			//userIn.nextLine();
			location = userIn.nextLine();
			color = location.charAt(0);
			stackNum = Character.getNumericValue(location.charAt(1));
			color = Character.toUpperCase(color);
			
		} while ((color != 'B' && color != 'W') || stackNum > 6 || stackNum < 1);



		if (color == 'B')
		{
			choice = stackNum - 1;
		}
		else if (color == 'W')
		{
			choice = stackNum + 5;
		}
		
		return choice;
	}

	/* *********************************************************************
	Function Name: EndGame
	Purpose:	The request to save and quit
	Parameters: None
	Return Value:
				bool -> True - If the user would like to save the game and quit
				bool -> False - If the user would like to continue playing
	Algorithm: None
	Assistance Received: None
	********************************************************************* */
	public boolean EndGame()
	{
		//Scanner userIn = new Scanner(System.in);
		int quit = 1;
		System.out.print("\n\nWould you like to save and quit?\nPress 1 to Continue Playing or 0 to Save and Quit\n");
		//userIn.nextLine();
		//quit = Integer.parseInt(userIn.nextLine());
		

		if (quit == 0)
		{
			return true;
		}
		else
		{
			//userIn.close();
			return false;
		}
	}


	/* *********************************************************************
	Function Name: GameSetup
	Purpose:	Lets the user know, what is happening behind the scenes
	Parameters: None
	Return Value: None
	Algorithm: None
	Assistance Received: None
	********************************************************************* */
	public void GameSetup()
	{
		System.out.print("\n\nShuffling Player and Computer Decks.....");
		System.out.print("\nBoth Players Drawing Tiles....");
		System.out.print("\nCreating Initial Stacks....");
		System.out.print("\n\nGameboard Setup\n");
	}

	/* *********************************************************************
	Function Name: LoadGame
	Purpose:	Lets the user know, that the game is able to initialize from
				the given file, and is loading in
	Parameters: None
	Return Value: None
	Algorithm: None
	Assistance Received: None
	********************************************************************* */
	public void LoadGame()
	{
		System.out.print("\n\nLoading Game From File......\n");
	}


	/* *********************************************************************
	Function Name: FirstUp
	Purpose:	Displays which user is going to go first in a hand
	Parameters: 
				a_first:
					if 1, the users tile was larger
					if 2, the computers tile was larger
				a_humanTotal, the total pips of the humans tile
				a_computerTotal, the total pips of the computer tile
	Return Value: String - Message for Dialog Box to inform who is first
	Algorithm: None
	Assistance Received: None
	********************************************************************* */
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

	/* *********************************************************************
	Function Name: DisplayScore
	Purpose:	Displays the current scoreboard
	Parameters:
				a_playerScore, the total human score
				a_cpuScore, the total computer score
	Return Value: None
	Algorithm: None
	Assistance Received: None
	********************************************************************* */
	public String DisplayScore(short a_playerScore, short a_cpuScore)
	{
		//System.out.print("\n\n\t\tSCOREBOARD");
		String score = "------------------------------------------------";
		score += "\nHuman: " + a_playerScore;
		score += "\nComputer: "  + a_cpuScore;
		return score;
	}

	/* *********************************************************************
	Function Name: Finished
	Purpose:	Displays the total rounds won by both players
	Parameters:
				a_cpuRounds, the total rounds the computer won
				a_humanRounds, the total rounds the human won
	Return Value: None
	Algorithm: None
	Assistance Received: None
	********************************************************************* */
	public void RoundsScore(short a_cpuRounds, short a_humanRounds)
	{
		System.out.print("\n\nThe round has concluded");
		System.out.print("\n\n\t\tSCOREBOARD");
		System.out.print("\n------------------------------------------------");
		System.out.print("\nHuman\t\t\tComputer\n");
		System.out.print("Rounds: " + a_humanRounds + "\t\t\t\t" + "Rounds: " + a_cpuRounds);
	}

	/* *********************************************************************
	Function Name: Finished
	Purpose:	Displays the total rounds won by both players and announces
				tournament winner, or tie.
	Parameters:
				a_cpuRounds, the total rounds the computer won
				a_humanRounds, the total rounds the human won
	Return Value: None
	Algorithm: None
	Assistance Received: None
	********************************************************************* */
	public String Finished(short a_cpuRounds, short a_humanRounds)
	{
		String finish = "";
		//System.out.print("\n\n\t\tSCOREBOARD");
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
