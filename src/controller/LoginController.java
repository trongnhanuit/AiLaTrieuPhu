package controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.*;
/**
 * Servlet implementation class LoginController
 */
@WebServlet("/LoginController")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static Set<PlayerPosRecord> playerposmap = Collections.synchronizedSet(new HashSet<PlayerPosRecord>());
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		List<Player> ql= Function.select(Player.class,"username='"+request.getParameter("usernamel")+"'");
		if(!ql.isEmpty())
			for(Player emp : ql)
			{
				try 
				{
					if(Function.validatePassword(request.getParameter("password"), emp.getPassword()))
					 {
						// Kiem tra xem tai khoan nay hien tai da co ai dang nhap chua
						boolean islogined=false;
						for (PlayerPosRecord ppr: playerposmap)
							if (ppr.username.equals(request.getParameter("usernamel")))
								islogined=true;
						if (islogined)
							sendError(response,"Tài khoản này đã được đăng nhập. Vui lòng đăng nhập bằng tài khoản khác!");
						else
						{
							// Nếu là MC
							if (request.getParameter("usernamel").equals("admin"))
							{
								response.sendRedirect("http://localhost:8080/AiLaTrieuPhu/view/mc.jsp");
								playerposmap.add(new PlayerPosRecord("-1",request.getParameter("usernamel")));
							}
							else //  Neu la current mainplayer
								if (request.getParameter("usernamel").equals(Function.getCurrentMainPlayer()))
								{
									WSServer.roundID=Function.getRoundID(request.getParameter("usernamel"));
									playerposmap.add(new PlayerPosRecord("00",request.getParameter("usernamel")));
									response.sendRedirect("http://localhost:8080/AiLaTrieuPhu/view/mainplayer.jsp");
								}
								else //Nếu là applicants
								{
									// Tìm chỗ trống vào ngồi
									// Tim vtri trong.
									int i;
									for (i=1; i<11; i++)
										if (WSServer.getSessionRecord("0"+String.valueOf(i))==null)
											break;
									// Neu i dừng trong khoảng từ 1-9 thì vào chỗ đó luôn
									if (i<10)
									{
										response.sendRedirect("http://localhost:8080/AiLaTrieuPhu/view/applicants.jsp?pos=0"+String.valueOf(i));
										playerposmap.add(new PlayerPosRecord("0"+String.valueOf(i),request.getParameter("usernamel")));
									}
									else // Nếu bằng 10 phải kiểm tra xem có trống không
										if (WSServer.getSessionRecord(String.valueOf(i))==null)
										{
											response.sendRedirect("http://localhost:8080/AiLaTrieuPhu/view/applicants.jsp?pos="+String.valueOf(i));
											playerposmap.add(new PlayerPosRecord(String.valueOf(i),request.getParameter("usernamel")));
										}
										else // Ngược lại hết chỗ
											sendError(response,"Đã đủ 10 người chơi. Vui lòng quay trở lại trong đợt chơi kế tiếp.");
								}
							 break;
						 }
					}
					else 
						sendError(response,"Sai mật khẩu! Vui lòng thử lại");
				} catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvalidKeySpecException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		else 
			sendError(response,"Tên đăng nhập không tồn tại! Vui lòng thử lại");
	}
	
	void sendError(HttpServletResponse response, String errorString) throws IOException
	{
		response.setContentType("text/html; charset=UTF-8");
		Cookie user_cookie=new Cookie("error",URLEncoder.encode(errorString, "UTF-8"));
	    user_cookie.setMaxAge(5);
	    response.addCookie(user_cookie);
	    response.sendRedirect("/AiLaTrieuPhu/view/login.jsp");
	}
}

