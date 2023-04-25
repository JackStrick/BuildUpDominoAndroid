package org.ramapo.jstrickl;


public class Tournament {
	private Round m_round = new Round();
	private MessageOutput m_msg = new MessageOutput();
	int m_numRounds;
	
	
	
	/* *********************************************************************
	Function Name: StartGame
	Purpose:	Initializes everything needed to begin the game and launch 
	            each round. The tournament will control all rounds and track 
	            the score after each round.
	Parameters: 
	            a_choice, determines start new or start from file
	Return Value: None
	Algorithm: 
	            1) Runs the setup based on the choice
	            2) Do-while loop, to continue until user wants to stop playing
	                    a) Passes in the choice to start game by file or new
	                    b) After each round get the score and then reset
	                        the points
	            3) Player no longer wishes to play, go into the end game
	Assistance Received: None
	********************************************************************* */
	public void StartGame(int a_choice)
	{
		///Initialize For New Game
	    if (a_choice == 1)
	    {
	        m_numRounds = 0;
	    }

	    do
	    {
	        if (a_choice == 1)
	        {
	            m_msg.GameSetup();
	        }
	       
	        m_round.StartRound();
	        ScoreGame();
	        m_round.ResetPoints();
	        m_numRounds++;
	        a_choice = 1;
	        m_msg.RoundsScore(m_round.GetRoundsComputerWon(), m_round.GetRoundsHumanWon());
	    } while (ContinuePlaying());

	    EndGame();
	}
	
	public Round getRound()
	{
		return m_round;
	}

	public void SetRounds(int count)
	{
		m_numRounds = count;
	}
	
	/* *********************************************************************
	Function Name:  ContinuePlaying
	Purpose:	Enters MessageOutput Class to determine whether the user would
	            like to continue playing the next round or end the tournament
	Parameters: None
	Return Value: 
	            True - if use would like to continue
	            False - if user would like to stop playing
	Algorithm: None
	Assistance Received: None
	********************************************************************* */
	public Boolean ContinuePlaying()
	{
	    if (!m_msg.Continue())
	    {
	        return false;
	    }
	    return true;
	}
	
	
	
	
	/* *********************************************************************
	Function Name: ScoreGame
	Purpose:	Takes in both players points, checks them against eachother and
	            determines the winner of the previous round
	Parameters: None
	Return Value: None
	Algorithm:
	            1) Checks points and determines tie or who won
	Assistance Received: None
	********************************************************************* */
	public void ScoreGame()
	{
	    int human = m_round.GetHumanPoints();
	    int cpu = m_round.GetComputerPoints();
	    if (human > cpu)
	    {
	        System.out.println("\n\nPlayer wins round");
	    }
	    else if (cpu > human)
	    {
	    	System.out.println("\n\nComputer wins round");
	    }
	    else if (cpu == human)
	    {
	    	System.out.println("\n\nRound ended in a tie");
	    }
	}


	
	/* *********************************************************************
	Function Name: EndGame
	Purpose:	Collects both players total rounds won and sends them
	            to MessageOutput class Finished() function to determine
	            the overall tournament winner
	Parameters: None
	Return Value: None
	Algorithm:None
	Assistance Received: None
	********************************************************************* */
	public void EndGame()
	{
	    m_msg.Finished(m_round.GetRoundsComputerWon(), m_round.GetRoundsHumanWon());
	}
	
}
