package controler;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;

@javax.websocket.server.ServerEndpoint(value="/servertest")
public class WSServer {
	private static Set<Session> list = Collections.synchronizedSet(new HashSet<Session>());
	
	@OnOpen
	public void onOpen(Session session) throws IOException
	{
		list.add(session);
		for (Session player : list) 
			player.getBasicRemote().sendText(session.getId()+" connected. <br/>");
	}
	
	@OnClose
	public void onClose(Session session, CloseReason closeReason) throws IOException
	{
		list.remove(session);
		for (Session player : list) 
			player.getBasicRemote().sendText(session.getId()+" closed. <br/>");
	}
	
	@OnMessage
	public void onMessage(Session session,String msg) throws IOException
	{
		for (Session player : list) 
		{
			player.getBasicRemote().sendText(session.getId()+" sent: "+msg+"<br/>");
		}
	}
}
