package org.ramapo.jstrickl;

import java.io.FileWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Vector;
import java.util.Scanner;



public class Round {

	//Private Class Objects
		private Human m_human = new Human();
		private Computer m_computer = new Computer();
		private Deck m_deck = new Deck();
		private GameBoard m_gameBoard = new GameBoard();
		private MessageOutput m_msg = new MessageOutput();
		

		//Data Members
		private short m_handCount;
		private short m_roundCount;
		
	
		
		
		
		/* *********************************************************************
		Function Name: GetHumanPoints
		Purpose: To get the total human points after a round
		Parameters: None
		Return Value: The total human points after a round
		Algorithm:
					1) Calls Player::GetPoints
					2) Returns value
		Assistance Received: none
		********************************************************************* */
		public short GetHumanPoints()
		{
			return m_human.GetPoints();
		}

		/* *********************************************************************
		Function Name: GetComputerPoints
		Purpose: To get the total computer points after a round
		Parameters: None
		Return Value: The total computer points after a round
		Algorithm: 
					1) Calls Player::GetPoints
					2) Returns value
		Assistance Received: none
		********************************************************************* */
		public short GetComputerPoints()
		{
			return m_computer.GetPoints();
		}

		/* *********************************************************************
		Function Name: GetHumanRoundsWon
		Purpose: To get the total rounds human won after a tournament
		Parameters: None
		Return Value: The total human rounds won after a round
		Algorithm:
					1) Calls Player::GetRoundsWon
					2) Returns round quantity
		Assistance Received: none
		********************************************************************* */
		public short GetRoundsHumanWon()
		{
			return m_human.GetRoundsWon();
		}


		/* *********************************************************************
		Function Name: GetComputerRoundsWon
		Purpose: To get the total rounds computer won after a tournament
		Parameters: None
		Return Value: The total computer rounds won after a round
		Algorithm:
					1) Calls Player::GetRoundsWon
					2) Returns round quantity
		Assistance Received: none
		********************************************************************* */
		public short GetRoundsComputerWon()
		{
			return m_computer.GetRoundsWon();
		}


		//Mutators
		/* *********************************************************************
		Function Name: SetPlayerTurn
		Purpose: To set whose turn it is
		Parameters: 
					a_player, an object of player type, either computer or human
		Return Value: None
		Algorithm:
					1) Calls Player::SetTurn() to set player turn value to true
		Assistance Received: none
		********************************************************************* */
		public void SetPlayerTurn(Player a_player)
		{
			a_player.SetTurn();
		}

		/* *********************************************************************
		Function Name: SwitchTurn
		Purpose: To switch turns to next player up
		Parameters: None
		Return Value: None
		Algorithm:
					1) Checks if its the computers turn
						a) If yes, make it the humans turn
						b) Call Player::EndTurn() which sets its turn to false
					2) If no, checks if its the humans turn
						a) If yes, make it the computers turn
						b) Call Player::EndTurn() which sets humans turn to false
		Assistance Received: none
		********************************************************************* */
		public void SwitchTurn()
		{
			if (m_computer.IsMyTurn())
			{
				SetPlayerTurn(m_human);
				m_computer.EndTurn();
			}
			else if (m_human.IsMyTurn())
			{
				SetPlayerTurn(m_computer);
				m_human.EndTurn();
			}
		}

		/* *********************************************************************
		Function Name: UpdatePoints
		Purpose: Updates each players point total
		Parameters: None
		Return Value: None
		Algorithm:
					1) Loops through entire gameboard
						a) If the tile on top of stack belongs to the
							human (B), add total pips of that tile to
							human points
						b) If the tile on top of stack belongs to the
							computer (W), add total pips of that tile to
							computer points 
					2) Calls Player::Drop Points to decrease the total tile
						values left in player hand
		Assistance Received: none
		********************************************************************* */
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

		/* *********************************************************************
		Function Name: ResetPoints
		Purpose: Sets points back to zero
		Parameters: None
		Return Value: None
		Algorithm:
					1) Calls Player::PointReset for each player to
						set the points back to zero after a round
		Assistance Received: none
		********************************************************************* */
		public void ResetPoints()
		{
			m_human.PointReset();
			m_computer.PointReset();
		}

		/* *********************************************************************
		Function Name: RoundWin
		Purpose: Increments users total rounds won based on who won the round
		Parameters: None
		Return Value: None
		Algorithm:
					1) Check whose points are greater from current round
						a) If human, human wins round
						b) If computer, computer wins round
						c) If neither, its a tie
		Assistance Received: none
		********************************************************************* */
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
		/* *********************************************************************
		Function Name: StartRound
		Purpose: Called by tournament to begin a round
		Parameters: 
					a_choice, the answer of whether to start new game
					or from a file
		Return Value: None
		Algorithm:
					1) Check if start new game or start from file
						a) Game is initialized based on parameter
					2) Check if each players hand is empty and also
						if the boneyards of each player are not empty
						a) If true, a new hand will start
						b) If false, start from current hand
					3) New hand: 
							a) Both players draw a tile from their 
								boneyard and compare. The larger tile holder
								goes first. If the same return tile, shuffle
								boneyard and repeat process
							b) Set the first up player and diplay to the user
							c) Both players draw the rest of their hand
					4) If not new hand, or step 3 complete:
							a) Check if either player can place a tile
							b) If yes, enter turn sequence. 
							c) Either players turn will call Choice() and return
								a Vector to represent their selected tile and
								the location on the gameboard to place
					5) If done, or 4a returned false:
							a) The hand is over
							b) If players hands are not empty and there were no
								playable tiles, print to let the user know
							c) Update the score and move on to next hand if tiles
								remain in the boneyard
					6) If all hands complete and boneyards are empty
							a) Clear the gameboard 
							b) Update who won the round
		Assistance Received: none
		********************************************************************* */
		public void StartRound(int a_choice)
		{
			//Initializing For Data For New Game
			if (a_choice == 1)
			{
				StartNew();
			}
			
			if (a_choice == 2)
			{
				StartFromFile();
			}

			m_gameBoard.DisplayGameBoard();
			System.out.print("\n\n\t\t\tBuild Up");
			System.out.print("\n______________________________________________________________\n");
			
			while (m_handCount < 4)
			{
				int first;
				if ((m_computer.GetHand().isEmpty() && m_human.GetHand().isEmpty()) && !m_computer.GetBoneYard().isEmpty() && !m_human.GetBoneYard().isEmpty())
				{
					first = 0;
					System.out.print("\n\nStarting New Hand.....");
					int repeat = 3;
					do
					{
						System.out.print("\n\nBoth Players Drawing First Tile Of Hand");
						first = TileCompare(m_human.InitialTile(), m_computer.InitialTile());
						if (first == repeat)
						{
							System.out.print("\nTile Values The Same\nTiles added back to players boneyard\n\nPlayers Redrawing Tiles...");
							m_human.ReturnTiles();
							m_computer.ReturnTiles();
						}
					} while (first == repeat);

					m_msg.FirstUp(first, m_human.FirstTilePipTotal(), m_computer.FirstTilePipTotal());
					m_human.AddToHand(m_human.Draw());
					m_computer.AddToHand(m_computer.Draw());
				}

				while (IsPlaceableTiles(m_computer.GetHand()) || IsPlaceableTiles(m_human.GetHand()))
				{

					//HUMAN TURN
					if (m_human.IsMyTurn())
					{
						m_gameBoard.DisplayGameBoard();
						Vector<Integer> tile_loc = m_human.Choice(m_gameBoard.GetDominoStack());

						
						// If Selection Vector tile_loc is not empty, the tiles can be placed
						if (tile_loc.size() > 1 && tile_loc.get(0) != 89)
						{
							System.out.print("You are ");
							m_gameBoard.TilePlacement(m_human.GetHand().get(tile_loc.get(0)), tile_loc.get(1));
							m_human.RemoveTileFromHand(tile_loc.get(0));
						}

						// Switch turns before saving for no repeat turn
						SwitchTurn();
						//Prompt User to Save and Quit Game
						boolean quit = m_msg.EndGame();
						if (quit)
						{
							SaveGame();
						}

					}
					//COMPUTER TURN
					else if (m_computer.IsMyTurn())
					{
						m_gameBoard.DisplayGameBoard();
						Vector<Integer> tile_loc = m_computer.Choice(m_gameBoard.GetDominoStack());
						if (tile_loc.size() > 1)
						{
							m_gameBoard.TilePlacement(m_computer.GetHand().get(tile_loc.get(0)), tile_loc.get(1));
							m_computer.RemoveTileFromHand(tile_loc.get(0));
						}
						
						SwitchTurn();
					}
				}
				
				if (!m_human.GetHand().isEmpty() && !IsPlaceableTiles(m_human.GetHand()) && !m_computer.GetHand().isEmpty() && !IsPlaceableTiles(m_computer.GetHand()))
				{
					System.out.print("\n\nNo more tiles in either hand can be placed\n\n");
				}

				System.out.print("\nHand Complete!\nUpdating Scoreboard....");
				UpdatePoints();
				m_msg.DisplayScore(m_human.GetPoints(), m_computer.GetPoints());
				m_handCount++;	
			}
			m_gameBoard.DisplayGameBoard();
			m_gameBoard.ClearBoard();
			RoundWin();
		}

		/* *********************************************************************
		Function Name: StartNew
		Purpose: Sets up all components needed for a new game
		Parameters: None
		Return Value: None
		Algorithm:
					1) Initialize all crucial game components for
						a brand new game
					2) Display the gameboard to the user
		Assistance Received: none
		********************************************************************* */
		public void StartNew()
		{
			m_roundCount = 0;
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

		/* *********************************************************************
		Function Name: StartFromFile
		Purpose: Sets up all components needed from a past game
		Parameters: None
		Return Value: None
		Algorithm:
					1) Asks for file path until given a valid file
					2) Set all values for computer and then human
					3) Set gameboard stacks at the end to maintain proper order
						a) The human stacks are first in the Vector
					4) Determine the hand number within the round
						based on the amount of tiles in the boneyard
					4) Display the gameboard to the user
		Assistance Received: none
		********************************************************************* */
		public void StartFromFile()
		{
			File file;
			String home = System.getProperty("user.home");
		    String path = "/Files/School/Ramapo/Spring 2023/CMPS366 - OPL/GameFilesTest/";
			String fileName;
			// file path hold the path of the game file entered by the user
			String filePath = home + path;
			Scanner scan = new Scanner(System.in);
			do 
			{
				System.out.print("\nPlease enter the file name: ");
				fileName = scan.nextLine();
				filePath += fileName;
				
				file = new File(filePath);
				try 
				{
					// Attempt to open file from given path
					Scanner fileScanner = new Scanner(file);
					// check if the file exists
					if (fileScanner.hasNext()) 
					{
						fileScanner.close();
					}
				} catch (FileNotFoundException e)
				{
					System.out.print("\n\nFile Does Not Exist.\n");
				}
				
			// While the file does not exist, or cannot be found, ask for a new file path
			} while (!file.exists());
			
			// File found, tells the user that the game is being loaded in
			m_msg.LoadGame();

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
	                		if (line.equals("Computer")) 
	                		{
	                			m_computer.SetTurn();
	                			m_human.EndTurn();
	                			System.out.print("\n\nComputer Will Go First\n\n");
	                		}
	                		else if (line.equals("Human"))
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

		/* *********************************************************************
		Function Name: TileCompare
		Purpose: To figure out who goes first each hand
		Parameters: 
					a_human, the tile the human first drew
					a_computer, the tile the computer first drew
		Return Value: 
				returns an int that refers to whose turn it will be
		Algorithm:
					1) Checks which player has the larger tile
						and sets them to start first
						a) If the same, 3 is retuned which will indicate
							the need to repeat the process
		Assistance Received: none
		********************************************************************* */
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

		/* *********************************************************************
		Function Name: IsPlaceableTiles
		Purpose: To determine if a players tiles can be placed on the board
		Parameters:
					a_playerTiles, a Vector of tiles that represents a players
					hand
		Return Value:
				returns a bool. 
				If true, means they have a tile that can be placed. 
				If false, they have no tiles in their hand that can be
				placed anywhere on the board
		Algorithm:
					1) Loops through the entire board and checks if
						each tile matches the required criteria to be placed
		Assistance Received: none
		********************************************************************* */
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

		/* *********************************************************************
		Function Name: SaveGame
		Purpose: Stores all current player data to a file
		Parameters: None
		Return Value: None
		Algorithm:
					1) Asks for a name to give the file and creates a new file
						of that given name
					2) Pulls in all data required for the computer
					3) Pulls in all data required for the human
					4) Sets both rounds, score, and finally the player
						whose turn will be next
		Assistance Received: 	
					https://stackoverflow.com/questions/5252612/replace-space-with-an-underscore
					https://www.educative.io/answers/how-to-get-the-current-date-and-time-in-cpp
					https://stackoverflow.com/questions/30120813/how-do-i-provide-a-file-path-in-mac-os-x-while-creating-a-file-in-java
		********************************************************************* */
		public void SaveGame()
		{
			Vector<Tile> temp;
			String home = System.getProperty("user.home");
		    String path = "/Files/School/Ramapo/Spring 2023/CMPS366 - OPL/GameFilesTest/";
		    System.out.print("\n\nEnter file name to save - Do not include extension\n");
		    // Create File Name With Data and txt File Extension
		    Scanner scan = new Scanner(System.in);
		    String fileName = scan.nextLine();
		    
		    
		    fileName = home + path +  fileName + ".txt";
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
		            file.write(temp.get(i).getLeftPips());
		            file.write(temp.get(i).getRightPips() + " ");
		        }

		        // Write Boneyard to file
		        temp = m_computer.GetBoneYard();
		        file.write("\n\tBoneyard: ");
		        for (int i = 0; i < temp.size(); i++) {
		            file.write(temp.get(i).getColor());
		            file.write(temp.get(i).getLeftPips());
		            file.write(temp.get(i).getRightPips() + " ");
		        }

		        // Write Hand to file
		        temp = m_computer.GetHand();
		        file.write("\n\tHand: ");
		        for (int i = 0; i < temp.size(); i++) {
		            file.write(temp.get(i).getColor());
		            file.write(temp.get(i).getLeftPips());
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
		            file.write(temp.get(i).getLeftPips());
		            file.write(temp.get(i).getRightPips() + " ");
		        }

		        // Write Boneyard to file
		        temp = m_human.GetBoneYard();
		        file.write("\n\tBoneyard: ");
		        for (int i = 0; i < temp.size(); i++) 
		        {
		            file.write(temp.get(i).getColor());
		            file.write(temp.get(i).getLeftPips());
		            file.write(temp.get(i).getRightPips() + " ");
		        }

		        // Write Hand to file
		        temp = m_human.GetHand();
		        file.write("\n\tHand: ");
		        for (int i = 0; i < temp.size(); i++) 
		        {
		            file.write(temp.get(i).getColor());
		            file.write(temp.get(i).getLeftPips());
		            file.write(temp.get(i).getRightPips() + " ");   
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
		        
		    }catch (Exception e)
		    {
		    	System.out.print(e);
		    }
		    
		    scan.close();
		    System.exit(0);
		}
		
	
}
