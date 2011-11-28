package com.awarity;

public class WorkType
{
	private int entryNumber, taskNumber;
	private String type;

	public WorkType()
	{
		entryNumber = 0;
		taskNumber = 0;
		type = "";
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

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}
}
