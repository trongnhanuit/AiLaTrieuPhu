package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Function;
import model.Player;

/**
 * Servlet implementation class LoginController
 */
@WebServlet("/LoginController")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
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
						// Nếu là MC
						if (request.getParameter("usernamel").equals("admin"))
							response.sendRedirect("http://localhost:8080/AiLaTrieuPhu/view/mc.jsp");
						else // Nếu là applicants
						{
							// Tìm chỗ trống vào ngồi
							// Tim vtri trong.
							int i;
							for (i=1; i<11; i++)
								if (WSServer.getSessionRecord("0"+String.valueOf(i))==null)
									break;
							// Neu i dừng trong khoảng từ 1-9 thì vào chỗ đó luôn
							if (i<10)
								response.sendRedirect("http://localhost:8080/AiLaTrieuPhu/view/applicants.jsp?pos=0"+String.valueOf(i));
							else // Nếu bằng 10 phải kiểm tra xem có trống không
								if (WSServer.getSessionRecord(String.valueOf(i))==null)
									response.sendRedirect("http://localhost:8080/AiLaTrieuPhu/view/applicants.jsp?pos="+String.valueOf(i));
								else // Ngược lại hết chỗ
									sendError(response,"Đã đủ 10 người chơi. Vui lòng quay trở lại trong đợt chơi kế tiếp.");
						}
						 break;
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

