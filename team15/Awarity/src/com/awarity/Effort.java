package com.awarity;

public class Effort
{
	private long taskNumber;
	private int entryNumber, estCompTime, elapsedTime,progressQuality;
	private String description;
	
	public Effort()
	{
		taskNumber=0;
		entryNumber=0;
		estCompTime=0;
		elapsedTime=0;
		progressQuality=0;
		description="";
	}
	
	

	public String getDescription()
	{
		return description;
	}



	public void setDescription(String description)
	{
		this.description = description;
	}



	public long getTaskNumber()
	{
		return taskNumber;
	}

	public void setTaskNumber(long mRowId)
	{
		this.taskNumber = mRowId;
	}

	public int getEntryNumber()
	{
		return entryNumber;
	}

	public void setEntryNumber(int entryNumber)
	{
		this.entryNumber = entryNumber;
	}

	public int getEstCompTime()
	{
		return estCompTime;
	}

	public void setEstCompTime(int estCompTime)
	{
		this.estCompTime = estCompTime;
	}

	public int getElapsedTime()
	{
		return elapsedTime;
	}

	public void setElapsedTime(int elapsedTime)
	{
		this.elapsedTime = elapsedTime;
	}

	public int getProgressQuality()
	{
		return progressQuality;
	}

	public void setProgressQuality(int progressQuality)
	{
		this.progressQuality = progressQuality;
	}
	
}
