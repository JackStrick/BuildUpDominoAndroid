package org.ramapo.jstrickl;

import java.io.Serializable;

public class Tile implements Serializable {

	// Data Members
	private char m_Color; 
	private int m_LeftPips;
	private int m_RightPips;
	
	/* *********************************************************************
	Function Name: Tile Constructor
	Purpose:	Creation of every tile, sets the color, left pips,
				and right pips
	Parameters: 
				color: The color of the tile
				leftPips: The left pip quantity
				rightPips: The right pip quntity
	Return Value: None
	Algorithm: None
	Assistance Received: None
	********************************************************************* */
	public Tile(char color, int leftPips, int rightPips)
	{
		m_Color = color;
		m_LeftPips = leftPips;
		m_RightPips = rightPips;
	}

	/* *********************************************************************
	Function Name: GetColor
	Purpose:	Returns the specific tile color
	Parameters: None
	Return Value: 
				char m_Color: B or W
	Algorithm: None
	Assistance Received: None
	********************************************************************* */
	public char getColor()
	{
		return m_Color;
	}


	/* *********************************************************************
	Function Name: GetLeftPips
	Purpose:	Gets the specific tile left pips qunatity
	Parameters: None
	Return Value:
				int m_LeftPips: Pips count on left side of tile
	Algorithm: None
	Assistance Received: None
	********************************************************************* */
	public int getLeftPips()
	{
		return m_LeftPips;
	}


	/* *********************************************************************
	Function Name: GetRightPips
	Purpose:	Gets the specific tile right pips qunatity
	Parameters: None
	Return Value:
				int m_rightPips: Pips count on right side of tile
	Algorithm: None
	Assistance Received: None
	********************************************************************* */
	public int getRightPips()
	{
		return m_RightPips;
	}


	/* *********************************************************************
	Function Name: GetTotalPips
	Purpose:	Gets the specific tile total pips quantity (left + rigth)
	Parameters: None
	Return Value:
				int (left + rigth pips) - Total tile pips
	Algorithm: None
	Assistance Received: None
	********************************************************************* */
	public int getTotalPips()
	{
		return m_LeftPips + m_RightPips;
	}
	
	
	
	
	
	
	
	
}
