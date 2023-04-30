package org.ramapo.jstrickl;

import android.net.Uri;
import java.io.FileWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.Vector;
import java.util.Scanner;



public class Round implements Serializable
{

	//Private Class Objects
	private Human m_human = new Human();
	private Computer m_computer = new Computer();
	private Deck m_deck = new Deck();
	private GameBoard m_gameBoard = new GameBoard();
	private MessageOutput m_msg = new MessageOutput();


	//Data Members
	private short m_handCount;

	private Tile selectedTile;

	/**
	 * To get the board stack
	 * @return Vector<Tile> objects that represent the board stack
	 */
	public Vector<Tile> GetGameStacks()
	{
		return m_gameBoard.GetDominoStack();
	}

	/**
	 * To get the human's hand
	 * @return Vector<Tile> objects that represent the human hand
	 */
	public Vector<Tile> GetHumanHand()
	{
		return m_human.GetHand();
	}

	/**
	 * To get the human boneyard
	 * @return Vector<Tile> objects that represent the human boneyard
	 */
	public Vector<Tile> GetHumanBY()
	{
		return m_human.GetBoneYard();
	}

	/**
	 * To get the computer's hand
	 * @return Vector<Tile> objects that represent the computer hand
	 */
	public Vector<Tile> GetComputerHand()
	{
		return m_computer.GetHand();
	}

	/**
	 * To get the computer's boneyard
	 * @return Vector<Tile> objects that represent the computer boneyard
	 */
	public Vector<Tile> GetComputerBY()
	{
		return m_computer.GetBoneYard();
	}

	/**
	 * Get the currently selected tile to place
	 * @return Tile objects that represents the tile from hand selected by player
	 */
	public Tile GetSelectedTile()
	{
		return selectedTile;
	};

	/**
	 * Get the number of hands so far
	 * @return Short for the number of hands in the round
	 */
	public short GetHandCount()
	{
		return m_handCount;
	}

	/**
	 * Get the current player who's turn it is
	 * @return Player who's current turn
	 */
	public Player GetPlayerTurn()
	{
		if (m_computer.IsMyTurn())
		{
			return m_computer;
		}
		else
		{
			return m_human;
		}
	}

	/**
	 * To get the total human points after a round
	 * @return short of total human points scored in the round
	 */
	public short GetHumanPoints()
	{
		return m_human.GetPoints();
	}

	/**
	 * To get the total computer points after a round
	 * @return short of total computer points scored in the round
	 */
	public short GetComputerPoints()
	{
		return m_computer.GetPoints();
	}

	/**
	 * To get the total rounds human won after a tournament
	 * @return Short The total human rounds won after a round
	 */
	public short GetRoundsHumanWon()
	{
		return m_human.GetRoundsWon();
	}

	/**
	 * To get the total rounds computer won after a tournament
	 * @return Short The total computer rounds won after a round
	 */
	public short GetRoundsComputerWon()
	{
		return m_computer.GetRoundsWon();
	}

	//Mutators

	/**
	 * To set whose turn it is
	 * @param a_player an object of player type, either computer or human
	 */
	public void SetPlayerTurn(Player a_player)
	{
		a_player.SetTurn();
	}

	/**
	 * To switch turns to next player up
	 */
	public void SwitchTurn()
	{
		if (m_computer.IsMyTurn())
		{
			SetPlayerTurn(m_human);
			m_computer.EndTurn();
		}
		else
		{
			SetPlayerTurn(m_computer);
			m_human.EndTurn();
		}
	}

	/**
	 * Updates each players point total
	 */
	public void UpdatePoints()
	{
		// Go through entire gameboard and give points to each player
		Vector<Tile> board = m_gameBoard.GetDominoStack();
		for (int i = 0; i < board.size(); i++)
		{
			if (board.get(i).getColor() == 'W')
			{
				m_computer.SetPoints(board.get(i).getTotalPips());
			}
			else if (board.get(i).getColor() == 'B')
			{
				m_human.SetPoints(board.get(i).getTotalPips());
			}
		}
		// If Tiles left in Hand - Decrease Points And Removes Tiles From Hand
		m_human.DropPoints();
		m_computer.DropPoints();
	}

	/**
	 * Sets points back to zero
	 */
	public void ResetPoints()
	{
		m_human.PointReset();
		m_computer.PointReset();
	}

	/**
	 * Increments users total rounds won based on who won the round
	 */
	public void RoundWin()
	{
		if (GetHumanPoints() > GetComputerPoints())
		{
			m_human.WonRound();
		}
		else if (GetHumanPoints() < GetComputerPoints())
		{
			m_computer.WonRound();
		}

	}

	//Utility Functions
	/**
	 * Check the move being made is valid
	 * @param handTile Int of which tile in players hand
	 * @param stackTile Int of the loaction the player is trying to place the handTile
	 * @param player Player object, which player is making the request
	 * @return Boolean, if the move is valid or not
	 */
	public boolean CheckValidity(int handTile, int stackTile, Player player)
	{
		selectedTile = player.GetHand().get(handTile);
		boolean valid = player.Play(m_gameBoard.GetDominoStack().get(stackTile), selectedTile);
		if (valid) {
			m_gameBoard.TilePlacement(player.GetHand().get(handTile), stackTile);
			player.RemoveTileFromHand(handTile);
		}
		return valid;
	}

	/**
	 * To decide what player will go first in the start of the round
	 * @return String to display the first player to the user
	 */
	public String DetermineFirst(){
		int first = 0;
		System.out.print("\n\nStarting New Hand.....");
		int repeat = 3;
		do
		{
			System.out.print("\n\nBoth Players Drawing First Tile Of Hand");
			// TileCompare - Compares the two tiles and sets player turn
			first = TileCompare(m_human.InitialTile(), m_computer.InitialTile());
			if (first == repeat)
			{
				System.out.print("\nTile Values The Same\nTiles added back to players boneyard\n\nPlayers Redrawing Tiles...");
				m_human.ReturnTiles();
				m_computer.ReturnTiles();
			}
		} while (first == repeat);


		String whoIsFirst = m_msg.FirstUp(first, m_human.FirstTilePipTotal(), m_computer.FirstTilePipTotal());
		m_human.AddToHand(m_human.Draw());
		m_computer.AddToHand(m_computer.Draw());
		return whoIsFirst;
	}

	/**
	 * When it is the computers turn to make a move
	 * @return String of the move the computer is making
	 */
	public String CompTurn() {
		Vector<Integer> tile_loc = m_computer.Choice(m_gameBoard.GetDominoStack());
		if (tile_loc.size() > 1)
		{

			m_gameBoard.TilePlacement(m_computer.GetHand().get(tile_loc.get(0)), tile_loc.get(1));
			m_computer.RemoveTileFromHand(tile_loc.get(0));
		}

		return m_gameBoard.GetPlacementString();
	}

	/**
	 * Check if either player has a possible move
	 * @return Boolean if there is a move or not available for either player
	 */
	public boolean CheckPlaceable(){
		if (IsPlaceableTiles(m_computer.GetHand()) || IsPlaceableTiles(m_human.GetHand())){
			return true;
		}
		else{
			return false;
		}
	}

	/**
	 * Check if there are tiles that can't be played in the hand
	 * @return Boolean if there are tiles with no possible moves
	 */
	public boolean UnPlaceableTilesInHand() {
		if (!m_human.GetHand().isEmpty() && !IsPlaceableTiles(m_human.GetHand()) && !m_computer.GetHand().isEmpty() && !IsPlaceableTiles(m_computer.GetHand()))
		{
			return true;
		}
		else{
			return false;
		}
	}

	/**
	 * End hand after all valid moves have been made
	 * @return String of the score so far
	 */
	public String EndHand() {
		UpdatePoints();
		String score = m_msg.DisplayScore(m_human.GetPoints(), m_computer.GetPoints());
		m_handCount++;
		return score;
	}

	/**
	 * To end a round after all hands have been played
	 */
	public void EndRound(){
		m_gameBoard.DisplayGameBoard();
		m_gameBoard.ClearBoard();
		RoundWin();
		m_handCount++;
	}

	/**
	 * Get the final score of the round
	 * @return String, the winner of the round
	 */
	public String ScoreGame()
	{
		int human = GetHumanPoints();
		int cpu = GetComputerPoints();
		String winner = "";
		if (human > cpu)
		{
			winner += "Player wins round";
		}
		else if (cpu > human)
		{
			winner += "\n\nComputer wins round";
		}
		else if (cpu == human)
		{
			winner += "\n\nRound ended in a tie";
		}
		ResetPoints();
		return winner;
	}

	/**
	 * Collects both players total rounds won and sends them to MessageOutput class Finished() function to determine the overall tournament winner
	 * @return String of the winner and final rounds score
	 */
	public String EndGame()
	{
		String finish = m_msg.Finished(GetRoundsComputerWon(), GetRoundsHumanWon());
		return finish;
	}

	/**
	 * Sets up all components needed for a new game
	 */
	public void StartNew()
	{
		m_handCount = 0;
		//The deck is created and shuffled for computer and human
		m_deck.GenerateTiles();

		// Human takes their 28 tiles
		m_human.Take(m_deck.Deal(m_human.PlayerColor()));
		// Computer takes their 28 tiles
		m_computer.Take(m_deck.Deal(m_computer.PlayerColor()));

		// Human Draws 6 and places them on the gameboard
		m_gameBoard.SetGameBoard(m_human.Draw());

		// Computer draws 6 and places them on the gameboard
		m_gameBoard.SetGameBoard(m_computer.Draw());
	}

	/**
	 * Sets up all components needed from a past game
	 * @param uri of the file being loaded from
	 */
	public void StartFromFile(Uri uri)
	{
		// Get a content resolver to open the input stream
		String directory = "/mnt/sdcard/Download";
		String fileName ="";
		int filePos = uri.getPath().lastIndexOf('/');
		if (filePos != -1) {
			fileName = uri.getPath().substring(filePos + 1);
		}

		//Get the text file
		File file = new File(directory,fileName);


		// file path hold the path of the game file entered by the user
		//String filePath = uri.getPath();
		if (!file.exists()){
			System.out.println("File not exist");
		}


		// Stores the current line the file is on
		String line;

		// score, holds the player score given from the file
		int score;

		// rounds, holds the player rounds won given from the file
		int rounds;

		// compStacks, holds the computers stack for the gameboard
		Vector<Tile> compStacks = new Vector<Tile>();

		// humanStacks, holds the humans stack for the gameboard
		Vector<Tile> humanStacks = new Vector<Tile>();


		Scanner fileScanner;

		try
		{
			fileScanner = new Scanner(file);
			if (fileScanner.hasNext())
			{
				line = fileScanner.nextLine();
				if (line.equals("Computer:"))
				{
					line = fileScanner.next();
					//line = line.replace("\\s", "");
					if (line.equals("Stacks:"))
					{
						line = fileScanner.nextLine();
						if (!line.equals(" "))
						{
							compStacks = m_computer.SetStacks(line);
						}
					}

					line = fileScanner.next();

					if (line.equals("Boneyard:"))
					{
						line = fileScanner.nextLine();
						if (!line.equals(" "))
						{
							m_computer.SetBoneyard(line);
						}
					}

					line = fileScanner.next();
					if (line.equals("Hand:"))
					{
						line = fileScanner.nextLine();
						if (!line.equals(" "))
						{
							m_computer.SetHand(line);
						}
					}

					line = fileScanner.next();
					if (line.equals("Score:"))
					{
						line = fileScanner.next();
						score = Integer.parseInt(line);
						m_computer.PointReset();
						m_computer.SetPoints(score);
					}

					line = fileScanner.nextLine();
					line = fileScanner.next();

					if (line.equals("Rounds"))
					{
						line = fileScanner.next();
						line = fileScanner.next();
						rounds = Integer.parseInt(line);
						m_computer.SetRoundsWon((short)rounds);
					}

					line = fileScanner.next();

				}
				if (line.equals("Human:"))
				{
					line = fileScanner.next();
					if (line.equals("Stacks:"))
					{
						line = fileScanner.nextLine();
						if (!line.equals(" "))
						{
							humanStacks = m_human.SetStacks(line);
						}
					}

					line = fileScanner.next();
					if (line.equals("Boneyard:"))
					{
						line = fileScanner.nextLine();
						if (!line.equals(" ")) {
							m_human.SetBoneyard(line);
						}
					}

					line = fileScanner.next();
					if (line.equals("Hand:"))
					{
						line = fileScanner.nextLine();
						if (!line.equals(" "))
						{
							m_human.SetHand(line);
						}
					}

					line = fileScanner.next();
					if (line.equals("Score:"))
					{
						line = fileScanner.next();
						score = Integer.parseInt(line);
						m_human.PointReset();
						m_human.SetPoints(score);
					}

					line = fileScanner.nextLine();
					line = fileScanner.next();
					if (line.equals("Rounds"))
					{
						line = fileScanner.next();
						line = fileScanner.next();
						rounds = Integer.parseInt(line);
						m_human.SetRoundsWon((short)rounds);
					}
				}

				line = fileScanner.next();
				if (line.equals("Turn:"))
				{
					if (fileScanner.hasNextLine())
					{

						line = fileScanner.nextLine();
						if (line.equals(" Computer") || line.equals("Computer"))
						{
							m_computer.SetTurn();
							m_human.EndTurn();
							System.out.print("\n\nComputer Will Go First\n\n");
						}
						else if (line.equals("Human") || line.equals(" Human"))
						{
							m_human.SetTurn();
							m_computer.EndTurn();
							System.out.print("\n\nHuman Will Go First\n\n");
						}
					}

				}
				fileScanner.close();
			}

		} catch(FileNotFoundException e)
		{
			System.out.print("Error Reading File - Round.java");
		}

		// Based on the boneyard size, determine which hand the game is on
		int bySize = m_human.GetBoneYard().size();
		switch (bySize)
		{
			case 22:
				m_handCount = 0;
				break;
			case 16:
				m_handCount = 1;
				break;
			case 10:
				m_handCount = 2;
				break;
			case 4:
				// Check if the hands are empty first to make sure no placeable tiles are left
				if (m_human.GetHand().isEmpty() && m_computer.GetHand().isEmpty())
				{
					m_handCount = 3;
				}
				else
				{
					m_handCount = 2;
				}
				break;
			default:
				m_handCount = 3;
				break;
		}

		//Comp Gameboard after human gameboard
		m_gameBoard.SetGameBoard(humanStacks);
		m_gameBoard.SetGameBoard(compStacks);
	}

	/**
	 * To figure out who goes first each hand
	 * @param a_human Tile the human first drew
	 * @param a_computer Tile the computer first drew
	 * @return int that refers to whose turn it will be
	 */
	public int TileCompare(Tile a_human, Tile a_computer)
	{
		// If Human tile is larger than computer tile, return 1
		if (a_human.getTotalPips() > a_computer.getTotalPips())
		{
			SetPlayerTurn(m_human);
			m_computer.EndTurn();
			return 1;
		}
		// Computer tile is larger, return 2
		else if (a_human.getTotalPips() < a_computer.getTotalPips())
		{
			SetPlayerTurn(m_computer);
			m_human.EndTurn();
			return 2;
		}
		// If tiles are same value then redraw initial tile
		else
		{
			return 3;
		}

	}

	/**
	 * To determine if a players tiles can be placed on the board
	 * @param a_playerTiles Vector of tiles that represents a players hand
	 * @return boolean true if placeable tile, else false
	 */
	public boolean IsPlaceableTiles(Vector<Tile> a_playerTiles)
	{
		Vector<Tile> board = m_gameBoard.GetDominoStack();

		//Check first players tiles
		for (int i = 0; i < board.size(); i++)
		{
			for (int j = 0; j < a_playerTiles.size(); j++)
			{
				//Tile total pips larger than on board
				if (a_playerTiles.get(j).getTotalPips() >= board.get(i).getTotalPips())
				{
					return true;
				}
				//If a double tile, it can be placed anywhere unless the stack is a double tile greater to the one in hand
				else if (a_playerTiles.get(j).getLeftPips() == a_playerTiles.get(j).getRightPips())
				{
					if (board.get(i).getLeftPips() != board.get(i).getRightPips())
					{
						return true;
					}
					else if ((board.get(i).getLeftPips() == board.get(i).getRightPips()) && (a_playerTiles.get(j).getTotalPips() > board.get(i).getTotalPips()))
					{
						return true;
					}
				}
			}
		}

		return false;
	}

	/**
	 * Stores all current player data to a file
	 */
	public void SaveGame()
	{

		String directory = "/mnt/sdcard/Download/";
		String fileName ="Test";

		Vector<Tile> temp;
		System.out.print("\n\nEnter file name to save - Do not include extension\n");
		// Create File Name With Data and txt File Extension



		fileName = directory +  fileName + ".txt";
		File saveFile = new File(fileName);

		System.out.print("\n\nSaving Game to: " + fileName);
		// Create New File
		try {
			saveFile.createNewFile();
			FileWriter file = new FileWriter(saveFile);

			// SAVE ALL COMPUTER DATA
			file.write("Computer:\n");

			// Write Computer Stack To File
			temp = m_gameBoard.GetDominoStack();
			file.write("\tStacks: ");
			for (int i = 6; i < temp.size(); i++) {
				file.write(temp.get(i).getColor());
				file.write(Integer.toString(temp.get(i).getLeftPips()));
				file.write(temp.get(i).getRightPips() + " ");
			}

			// Write Boneyard to file
			temp = m_computer.GetBoneYard();
			file.write("\n\tBoneyard: ");
			for (int i = 0; i < temp.size(); i++) {
				file.write(temp.get(i).getColor());
				file.write(Integer.toString(temp.get(i).getLeftPips()));
				file.write(temp.get(i).getRightPips() + " ");
			}

			// Write Hand to file
			temp = m_computer.GetHand();
			file.write("\n\tHand: ");
			for (int i = 0; i < temp.size(); i++) {
				file.write(temp.get(i).getColor());
				file.write(Integer.toString(temp.get(i).getLeftPips()));
				file.write(temp.get(i).getRightPips() + " ");
			}

			// Write Score
			file.write("\n\tScore: " + m_computer.GetPoints());

			// Write Rounds Won
			file.write("\n\tRounds Won: " + m_computer.GetRoundsWon());

			// SAVE ALL HUMAN DATA
			file.write("\n\nHuman:\n");

			// Write Computer Stack To File
			temp = m_gameBoard.GetDominoStack();
			file.write("\tStacks: ");
			for (int i = 0; i < temp.size() - 6; i++) {
				file.write(temp.get(i).getColor());
				file.write(Integer.toString(temp.get(i).getLeftPips()));
				file.write(temp.get(i).getRightPips() + " ");
			}

			// Write Boneyard to file
			temp = m_human.GetBoneYard();
			file.write("\n\tBoneyard: ");
			for (int i = 0; i < temp.size(); i++)
			{
				file.write(temp.get(i).getColor());
				file.write(Integer.toString(temp.get(i).getLeftPips()));
				file.write(temp.get(i).getRightPips() + " ");
			}

			// Write Hand to file
			temp = m_human.GetHand();
			file.write("\n\tHand: ");
			for (int i = 0; i < temp.size(); i++)
			{
				file.write(temp.get(i).getColor());
				file.write(Integer.toString(temp.get(i).getLeftPips()));
				file.write(temp.get(i).getRightPips()+ " ");
			}


			// Write Score
			file.write("\n\tScore: " + m_human.GetPoints());

			// Write Rounds Won
			file.write("\n\tRounds Won: " + m_human.GetRoundsWon());


			// Write Turn
			if (m_computer.IsMyTurn())
			{
				file.write("\n\nTurn: Computer");
			}
			else if (m_human.IsMyTurn())
			{
				file.write("\n\nTurn: Human");
			}

			file.close();

		} catch (Exception e)
		{
			System.out.print(e);
		}

		System.exit(0);
	}
}
