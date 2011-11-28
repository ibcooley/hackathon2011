package com.awarity;

public class Task
{
	private int taskNumber;
	private boolean isTaskComplete;
	private String name, ETA;

	public Task()
	{
		taskNumber = 0;
		ETA = "";
		isTaskComplete = false;
		name = "";
	}

	public int getTaskNumber()
	{
		return taskNumber;
	}

	public void setTaskNumber(int taskNumber)
	{
		this.taskNumber = taskNumber;
	}

	public String getETA()
	{
		return ETA;
	}

	public void setETA(String eTA)
	{
		ETA = eTA;
	}

	public int isTaskComplete()
	{
		if (isTaskComplete)
			return 1;
		else
			return 0;
	}

	public void setTaskComplete(boolean isTaskComplete)
	{
		this.isTaskComplete = isTaskComplete;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

}
