package controller;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.Random;

import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;

import java.util.Timer;
import java.util.TimerTask;

import model.*;

@javax.websocket.server.ServerEndpoint(value="/servertest")
public class WSServer {
	private static Set<SessionRecord> sessionmap = Collections.synchronizedSet(new HashSet<SessionRecord>());
	private static Set<ResultRecord> resultmap;
	// Varible for timer
	static int interval;
	Timer timer;
	// Thong ke dap an
	public static int ansA, ansB, ansC, ansD;
	
	@OnOpen
	public void onOpen(Session session) throws IOException
	{
		//Do nothing. Notice that the new websocket was opened in onMessage method.
	}
	
	@OnClose
	public void onClose(Session session, CloseReason closeReason) throws IOException
	{
		String pos=getSessionRecord(session).pos;
		sessionmap.remove(getSessionRecord(session));
		// if that is an audience -> notice to everybody
		if (!pos.equals("-1") && !pos.equals("00"))// neither mc nor mainplayer
			for (SessionRecord ssr:sessionmap)
				ssr.session.getBasicRemote().sendText("AUDIENCE OUT: "+pos);
	}
	
	@OnMessage
	public void onMessage(Session session,String msg) throws IOException
	{
		// When receive message with 2 characters (position) => Create and add new sessionRecord to map
		if (msg.length()==2)
		{
			sessionmap.add(new SessionRecord(session, msg));
			// if that is an audience -> notice to everybody
			if (!msg.equals("-1") && !msg.equals("00"))// neither mc nor mainplayer
				for (SessionRecord ssr:sessionmap)
					ssr.session.getBasicRemote().sendText("AUDIENCE IN: "+msg);
		}
		else // Else that is normal message
		{
			// De kiem tra xem nhung nguoi khac nhan dc gi tu server -> Khi lam xong xoa 2 dong code nay di
			for (SessionRecord ssr:sessionmap)
				ssr.session.getBasicRemote().sendText(session.getId()+" sent : "+msg+"<br/>");
			
			// HELP04
			// Mainplayer request
			if (msg.indexOf("REQUEST help04")==0 || msg.indexOf("REQUEST help03")==0)
			{
				resultmap = Collections.synchronizedSet(new HashSet<ResultRecord>());
				for (SessionRecord ssr:sessionmap)
					if (!ssr.pos.equals("00"))
						ssr.session.getBasicRemote().sendText(msg);
				
				// Nếu là help03 thì bắt đầu tính thời gian gửi kết quả
				if (msg.indexOf("REQUEST help03")==0)
				{
					ansA=0; ansB=0; ansC=0; ansD=0;
					interval=12;
					timer=new Timer();
					timer.schedule(new TimerTask() 
					{
						public void run() {try {setInterval();} catch (IOException e) {e.printStackTrace();}}
					}, 1000,1000);
				}
			}
			// audience + applicants response
			if (msg.indexOf("RESPONSE help04: ")==0)
			{
				resultmap.add(new ResultRecord(getSessionRecord(session).pos,msg.replace("RESPONSE help04: ", "")));
				for (SessionRecord ssr:sessionmap)
					ssr.session.getBasicRemote().sendText("RESPONSE help04: "+getSessionRecord(session).pos);
			}
			// mainplayer sends choise list
			if (msg.indexOf("CHOISE LIST help04: ")==0)
			{
				String resultlist="";
				String[] list=msg.replace("CHOISE LIST help04: ", "").split(";");
				for (String li:list)
					for (ResultRecord rsr:resultmap)
						if (rsr.pos.equals(li))
						{
							if (resultlist.equals(""))
								resultlist=rsr.ans;
							else
								resultlist=resultlist+";"+rsr.ans;
							break;
						}
				for (SessionRecord ssr:sessionmap)
					ssr.session.getBasicRemote().sendText("RESULT help04: "+msg.replace("CHOISE LIST help04: ", "")+";"+resultlist);
			}
			
			// HELP03
			// Mainplayer request -> xài ké hàm với help04
			// audience + applicants response
			if (msg.indexOf("RESPONSE help03: ")==0)
			{		
				// Thong ke ket qua
				switch(msg.replace("RESPONSE help03: ", "")) 
				{
				    case "a": ansA++; break;
				    case "b": ansB++; break;
				    case "c": ansC++; break;
				    case "d": ansD++; break;
				}
			}
			
			// HELP01
			if (msg.indexOf("REQUEST help01")==0)
			{
				String ans = "a", c ="",wrong="";
				Random rd = new Random();
				while(wrong.length()<3)
				{
					switch(rd.nextInt(4)+1)
					{
					case 1: c="a";break;
					case 2: c="b";break;
					case 3: c="c";break;
					case 4: c="d";break;
					}
					if(!c.equals(ans)&&!wrong.contains(c))
					{
						wrong+=c;
					}
					if(wrong.length()==1) wrong+=";";
				}
				for (SessionRecord ssr:sessionmap)
					ssr.session.getBasicRemote().sendText("RESPONSE help01: "+wrong);	
			}
		}
	}
	
	public static SessionRecord getSessionRecord(Session ss)
	{
		for (SessionRecord ssr:sessionmap)
			if (ssr.session==ss)
				return ssr;
		return null;
	}
	public static SessionRecord getSessionRecord(String pos)
	{
		for (SessionRecord ssr:sessionmap)
			if (ssr.pos.equals(pos))
				return ssr;
		return null;
	}
	
	private final void setInterval() throws IOException 
	{
	    if (interval == 0)
	    {
	    	// Bat buoc phai tao doi tuong tk thi moi lay dc ket qua. Con khong thi se bi 0;0;0;0
	    	ThongKeTraLoi tk=new ThongKeTraLoi();
	    	tk.setValues();
	    	
	    	for (SessionRecord ssr:sessionmap)
				ssr.session.getBasicRemote().sendText("RESPONSE help03: "+tk.ansA+";"+tk.ansB+";"+tk.ansC+";"+tk.ansD);
	        timer.cancel();
	    }
	    interval--;
	}
}
