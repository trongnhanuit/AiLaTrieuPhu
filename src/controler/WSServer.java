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

import model.SessionRecord;

@javax.websocket.server.ServerEndpoint(value="/servertest")
public class WSServer {
	private static Set<SessionRecord> sessionmap = Collections.synchronizedSet(new HashSet<SessionRecord>());
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
			for (SessionRecord ssr:sessionmap)
				ssr.session.getBasicRemote().sendText(session.getId()+" sent : "+msg+"<br/>");
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
}
