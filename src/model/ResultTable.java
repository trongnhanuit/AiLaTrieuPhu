package model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import controller.WSServer;

public class ResultTable 
{
	public Set<ResultRecord> resultmap;
	public ResultTable()
	{
		resultmap = Collections.synchronizedSet(new HashSet<ResultRecord>());
	}
	
	public void getData()
	{
		resultmap=WSServer.resultmap;
	}
}
