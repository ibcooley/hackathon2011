package com.awarity;

public class Dependency
{
	private long taskNumber;
	private int entryNumber;
	private String who;

	public Dependency()
	{
		entryNumber = 0;
		taskNumber = 0;
		who = "";
	}

	public int getEntryNumber()
	{
		return entryNumber;
	}

	public void setEntryNumber(int entryNumber)
	{
		this.entryNumber = entryNumber;
	}

	public long getTaskNumber()
	{
		return taskNumber;
	}

	public void setTaskNumber(long taskNumber)
	{
		this.taskNumber = taskNumber;
	}

	public String getWho()
	{
		return who;
	}

	public void setWho(String who)
	{
		this.who = who;
	}
}
