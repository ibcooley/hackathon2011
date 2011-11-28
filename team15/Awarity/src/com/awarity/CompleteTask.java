package com.awarity;

public class CompleteTask
{
	private int entryNumber, taskNumber, effort, quality, ratio;
	private String changes;

	public CompleteTask()
	{
		entryNumber = 0;
		taskNumber = 0;
		effort = 0;
		quality = 0;
		ratio = 0;
		changes = "";
	}

	public int getEntryNumber()
	{
		return entryNumber;
	}

	public void setEntryNumber(int entryNumber)
	{
		this.entryNumber = entryNumber;
	}

	public int getTaskNumber()
	{
		return taskNumber;
	}

	public void setTaskNumber(int taskNumber)
	{
		this.taskNumber = taskNumber;
	}

	public int getEffort()
	{
		return effort;
	}

	public void setEffort(int effort)
	{
		this.effort = effort;
	}

	public int getQuality()
	{
		return quality;
	}

	public void setQuality(int quality)
	{
		this.quality = quality;
	}

	public int getRatio()
	{
		return ratio;
	}

	public void setRatio(int ratio)
	{
		this.ratio = ratio;
	}

	public String getChanges()
	{
		return changes;
	}

	public void setChanges(String changes)
	{
		this.changes = changes;
	}

}
