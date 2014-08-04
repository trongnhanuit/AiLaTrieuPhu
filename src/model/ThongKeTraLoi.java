package model;
import controller.*;

public class ThongKeTraLoi 
{
	public int ansA, ansB, ansC, ansD;
	public ThongKeTraLoi(){}
	public void setValues()
	{
		ansA=WSServer.ansA;
		ansB=WSServer.ansB;
		ansC=WSServer.ansC;
		ansD=WSServer.ansD;
	}
}
