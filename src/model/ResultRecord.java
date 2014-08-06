package model;

public class ResultRecord 
{
	public String pos, ans;
	public int timeleft;
	public ResultRecord(){}
	public ResultRecord(String p, String a)
	{
		pos=p;
		ans=a;
	}
	public ResultRecord(String p, String a, int tl)
	{
		pos=p;
		ans=a;
		timeleft=tl;
	}
}
