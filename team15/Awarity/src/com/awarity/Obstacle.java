package com.awarity;

public class Obstacle
{
	private int entryNumber, effortNumber;
	private String type, description;

	public Obstacle()
	{
		entryNumber = 0;
		effortNumber = 0;
		type = "";
		description = "";
	}

	public int getEntryNumber()
	{
		return entryNumber;
	}

	public void setEntryNumber(int entryNumber)
	{
		this.entryNumber = entryNumber;
	}

	public int getEffortNumber()
	{
		return effortNumber;
	}

	public void setEffortNumber(int effortNumber)
	{
		this.effortNumber = effortNumber;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}
}
