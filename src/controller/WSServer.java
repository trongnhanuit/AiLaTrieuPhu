package controller;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
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
	public static Set<ResultRecord> resultmap;
	
	// Bien cho biet server moi chay lan dau
	public static boolean isFirstTime=true;	
	
	// Varible for timer
	static int interval;
	Timer timer;
	// Thong ke dap an
	public static int ansA, ansB, ansC, ansD;
	
	// Answer key for quick question
	static String ansKey;
	public static String currentQuestionContent="";
	public static String currentQuestionansA="";
	public static String currentQuestionansB="";
	public static String currentQuestionansC="";
	public static String currentQuestionansD="";
	static boolean isFinalQuestion;
	
	// Bien trang thai xem moi nguoi co can hien thi lai icon cac user on hay k 
	static boolean isNeed2Refresh=false;
	
	// roundID
	public static int roundID=-1;
	
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
		
		// Xoa tai khoan hien tai khoi ds tai khoan dang dc dang nhap
		for (PlayerPosRecord ppr:LoginController.playerposmap)
			if (pos.equals(ppr.pos))
				LoginController.playerposmap.remove(ppr);
		
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
			
			// if that is mainplayer -> notice to MC
			if (msg.equals("00"))
				getSessionRecord("-1").session.getBasicRemote().sendText("MAINPLAYER SIGNED IN");
				
			// Neu la applicants dang nhap thi kiem tra xem 10 vi tri applicants da day chua
			if (Integer.parseInt(msg)>0 && Integer.parseInt(msg)<11)
			{
				int i;
				for (i=1; i<11; i++)
					if (getSessionRecord("0"+String.valueOf(i))==null)
						break;
				if (i==10 && getSessionRecord(String.valueOf(i))!=null && Function.getCurrentMainPlayer().equals(""))
					getSessionRecord("-1").session.getBasicRemote().sendText("CREATE QUICK ROUND");
			}
		}
		else // Else that is normal message
		{
			// De kiem tra xem nhung nguoi khac nhan dc gi tu server -> Khi lam xong xoa 2 dong code nay di
			for (SessionRecord ssr:sessionmap)
				ssr.session.getBasicRemote().sendText(session.getId()+" sent : "+msg+"<br/>");
			
			// HELP
			// Bao nhan yeu cau dung chung cho 4 help
			if (msg.indexOf("REQUEST help0")==0)
			{
				Function.update(Round.class, "help=help-"+String.valueOf((int)Math.pow(2,Integer.parseInt(msg.replace("REQUEST help0",""))-1)), "roundID="+roundID);
				for (SessionRecord ssr:sessionmap)
					ssr.session.getBasicRemote().sendText(msg);
			}
			
			// HELP04
			// Mainplayer request
			if (msg.indexOf("REQUEST help04")==0 || msg.indexOf("REQUEST help03")==0)
			{
				resultmap = Collections.synchronizedSet(new HashSet<ResultRecord>());
				
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
				else // Nguoc lai Help04 thi phai yeu cau refresh lai trang thai User On
					isNeed2Refresh=true;
			}
			// audience + applicants response
			if (msg.indexOf("RESPONSE help04: ")==0)
				resultmap.add(new ResultRecord(getSessionRecord(session).pos,msg.replace("RESPONSE help04: ", "")));
			if (msg.indexOf("REQUEST RESPONSE HELP04")==0)
			{
				String list="";
				for (ResultRecord rsr:resultmap)
					if (list.equals(""))
						list=rsr.pos;
					else
						list=list+";"+rsr.pos;
				
				for (SessionRecord ssr:sessionmap)
					ssr.session.getBasicRemote().sendText("RESPONSE help04: "+list);
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
				String ans = ansKey.toLowerCase();
				String c ="",wrong="";
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
			
			// HELP02
			if (msg.indexOf("REQUEST help02")==0)
			{
				String ans = ansKey.toLowerCase();
				String res="";
				
				Random rd = new Random();
				if(rd.nextInt(2)==0)
					res=ans;
				else
				{
					res="xx";
				}
				for (SessionRecord ssr:sessionmap)
					ssr.session.getBasicRemote().sendText("RESPONSE help02: "+res);	
			}
			
			// Close fancybox Help
			if (msg.indexOf("CLOSE HELP FANCYBOX")==0)
				for (SessionRecord ssr:sessionmap)
					ssr.session.getBasicRemote().sendText("CLOSE HELP FANCYBOX");
			
			// QUẢNG CÁO
			if (msg.indexOf("REQUEST ADS")==0)
			{
				Random rd = new Random();
				int rand=rd.nextInt(3);
				for (SessionRecord ssr:sessionmap)
					ssr.session.getBasicRemote().sendText("RESPONSE ADS: "+rand);	
			}
			// TẠO VÒNG TRẢ LỜI NHANH
			if (msg.indexOf("CREATE QUICK ROUND")==0)
			{
				// Phai refresh lai trang thai hien thi user On
				isNeed2Refresh=true;
				
				// Tạo map lưu đáp án và tính giờ gửi thống kê
				resultmap = Collections.synchronizedSet(new HashSet<ResultRecord>());
				interval=22;
				timer=new Timer();
				timer.schedule(new TimerTask() 
				{
					public void run() {try {setIntervalquickround();} catch (IOException e) {e.printStackTrace();}}
				}, 1000,1000);
				
				// Get va gui cau hoi
				List<Quickquestion> ql= Function.select(Quickquestion.class,"");
				Random rd = new Random();
				Quickquestion qqt=ql.get(rd.nextInt(ql.size()));
				ansKey=qqt.getAnsKey();
				for (SessionRecord ssr:sessionmap)
					ssr.session.getBasicRemote().sendText("QUICK ROUND QUESTION: "+qqt.getContent()+"@@@"+qqt.getAnsA()+"@@@"+qqt.getAnsB()+"@@@"+qqt.getAnsC()+"@@@"+qqt.getAnsD());
				
			}
			// Nhận đáp án và thống kê
			if (msg.indexOf("QUICK ROUND ANSWER: ")==0)
			{
				String[] arr=msg.replace("QUICK ROUND ANSWER: ","").split(";");
				// Chi thong ke nhung vi tri tra loi dung
				if (arr[0].toUpperCase().equals(ansKey))
					resultmap.add(new ResultRecord(getSessionRecord(session).pos, arr[0],Integer.parseInt(arr[1])));
			}
			
			//VÒNG CHƠI CHÍNH
			//Nhận yêu cầu câu hỏi từ MC
			if (msg.indexOf("REQUEST NEXT QUESTION")==0)
			{
				// Neu can thi thong bao moi nguoi cap nhat lai trang thai hien thi User On
				if (isNeed2Refresh)
				{
					String list="";
					for (SessionRecord ssr:sessionmap)
						if (!ssr.pos.equals("00") && !ssr.pos.equals("-1"))
							if (list.equals(""))
								list=ssr.pos;
							else
								list=list+";"+ssr.pos;
					
					isNeed2Refresh=false;
					for (SessionRecord ssr:sessionmap)
						ssr.session.getBasicRemote().sendText("USER ON LIST: "+list);
				}
				
				// Lay stt cua cau hoi vua qua
				isFinalQuestion=false;
				List <Round> rounds=Function.select(Round.class, "roundID="+roundID);
				String[] questionlist=rounds.get(0).getQuestionlist().split("@");
				List<Question> questions=Function.select(Question.class, "level="+questionlist.length/5);
				
				// Kt co phai cau tiep la cau 15
				if (questionlist.length==14)
					isFinalQuestion=true;
				
				// Neu la cau 6 thi them help4: cap nhat DB va thong bao
				if (questionlist.length==5)
				{
					Function.update(Round.class, "help=help+8", "roundID="+roundID);
					for (SessionRecord ssr:sessionmap)
						ssr.session.getBasicRemote().sendText("ADD HELP04");
				}
				
				// Chon ngau nhien 1 cau hoi tu ds
				int pos=0;
				Random rd = new Random();
				do 
				{
					pos=rd.nextInt(questions.size());
				} while (!checkQuestionID(String.valueOf(questions.get(pos).getQuestionId()), questionlist));
				
				//Cap nhat them id cau hoi dc chon vao database
				Question question=questions.get(pos);
				if (rounds.get(0).getQuestionlist().length()>0)
					Function.update(Round.class, "questionlist=concat(questionlist,'@"+String.valueOf(question.getQuestionId())+"')", "roundID="+roundID);
				else
					Function.update(Round.class, "questionlist='"+String.valueOf(question.getQuestionId())+"'", "roundID="+roundID);
				
				ansKey=question.getAnsKey();
				currentQuestionContent=question.getContent();
				currentQuestionansA=question.getAnsA();
				currentQuestionansB=question.getAnsB();
				currentQuestionansC=question.getAnsC();
				currentQuestionansD=question.getAnsD();
				for (SessionRecord ssr:sessionmap)
					ssr.session.getBasicRemote().sendText("RESPONSE NEXT QUESTION: "+question.getContent()+"@@@"+question.getAnsA()+"@@@"+question.getAnsB()+"@@@"+question.getAnsC()+"@@@"+question.getAnsD()+". AnsKey: "+ansKey+"@@@"+Function.getCurrentQuestion(roundID));
			}
			// Nhận Temp answer tu nguoi choi chinh
			if (msg.indexOf("TEMP ANSWER QUESTION: ")==0)
				for (SessionRecord ssr:sessionmap)
					if (!ssr.pos.equals("00"))
						ssr.session.getBasicRemote().sendText(msg);
			// Nhận Final answer tu nguoi choi chinh
			if (msg.indexOf("FINAL ANSWER QUESTION: ")==0)
			{
				for (SessionRecord ssr:sessionmap)
					ssr.session.getBasicRemote().sendText("QUESTION RESULT: "+msg.replace("FINAL ANSWER QUESTION: ", "")+";"+ansKey);
				// Kiểm tra câu trả lời: nếu sai -> cập nhât DB; nếu đúng và là câu 15 thì cập nhật DB và thông báo thắng
				if (!msg.replace("FINAL ANSWER QUESTION: ","").equals(ansKey.toLowerCase()))
				{
					int check=0;
					if (checkEnough10Applicants())
						check=1;
					Function.update(Round.class, "status=-1", "roundID="+roundID);
					for (SessionRecord ssr:sessionmap)
						ssr.session.getBasicRemote().sendText("MAINPLAYER FAILED: "+Function.getFailedPrize(roundID)+";"+check);
				}	
				else
					if (isFinalQuestion)
					{
						int check=0;
						if (checkEnough10Applicants())
							check=1;
						Function.update(Round.class, "status=1", "roundID="+roundID);
						for (SessionRecord ssr:sessionmap)
							ssr.session.getBasicRemote().sendText("MAINPLAYER WON: 150000000"+";"+check);
					}
			}
			
			// Mainplayer yeu cau dung cuoc choi
			if (msg.indexOf("REQUEST STOP PLAYING")==0)
			{
				int check=0;
				if (checkEnough10Applicants())
					check=1;
				Function.update(Round.class, "status=1", "roundID="+roundID);
				for (SessionRecord ssr:sessionmap)
					ssr.session.getBasicRemote().sendText("MAINPLAYER WON: "+Function.getCurrentPrize(roundID)+";"+check);
			}
			
			// MC yeu cau tam dung game
			if (msg.indexOf("REQUEST PAUSE")==0)
				for (SessionRecord ssr:sessionmap)
					ssr.session.getBasicRemote().sendText("REQUEST PAUSE");
		}
	}
	// Kiem tra xem id cua cau hoi co ton tai trong ds cau hoi da qua khong
	boolean checkQuestionID(String questionID, String[] questionlist)
	{
		for (int i=0; i<questionlist.length; i++)
			if (questionID.equals(questionlist[i]))
				return false;
		return true;
	}
	
	// Kiem tra du 10 ung vien chua
	public static boolean checkEnough10Applicants()
	{
		for (int i=0; i<10; i++)
			if (getSessionRecord("0"+i)==null)
				return false;
		if (getSessionRecord("10")==null)
			return false;
		return true;
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
	
	private final void setIntervalquickround() throws IOException 
	{
	    if (interval == 0)
	    {
	    	// Get Data
	    	ResultTable rst=new ResultTable();
	    	rst.getData();
	    	
	    	// Tạo chuỗi ds các vị trí trả lời đúng.
	    	String pos="";
	    	int maxtimeleft=0;
	    	for (ResultRecord rsr:rst.resultmap)
	    		if (rsr.timeleft>maxtimeleft)
	    		{
	    			pos=rsr.pos;
	    			maxtimeleft=rsr.timeleft;
	    		}
	    	// Get user of mainplayer and create new round
			for(PlayerPosRecord ppr:LoginController.playerposmap)
				if (ppr.pos.equals(pos))
				{
					Round newRound=new Round(Function.selectPlayer(ppr.username), 7, "", 0);
					Function.insert(newRound);
					roundID=Function.getRoundID(ppr.username);
					break;
				}
	    	// Tao chuỗi, không thêm vị trí max vào nữa vì nó đã ở ngay đầu
	    	for (ResultRecord rsr:rst.resultmap)
	    		if (rsr.timeleft!=maxtimeleft)
	    			pos=pos+";"+rsr.pos;
	    	
	    	for (SessionRecord ssr:sessionmap)
				ssr.session.getBasicRemote().sendText("RESULT ROUND QUESTION: "+pos);
	    	// Xoa session nguoi dc chon
	    	sessionmap.remove(getSessionRecord(pos.split(";")[0]));
	        timer.cancel();
	    }
	    interval--;
	}
	
}
