package model;

import javax.websocket.Session;

public class SessionRecord 
{
	public Session session;
	public String pos;
	public SessionRecord(){}
	public SessionRecord(Session ss, String p)
	{
		session=ss;
		pos=p;
	}
}
