package controller;

import java.io.IOException;
import java.io.PrintWriter;
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

import util.PasswordProcess;
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Player> ql= Function.select(Player.class,"username='"+request.getParameter("usernamel")+"'");
		if(!ql.isEmpty())
		{
		for(Player emp : ql)
			{
			try {
				if(PasswordProcess.validatePassword(request.getParameter("password"), emp.getPassword()))
				 {
					response.sendRedirect("http://google.com");
					 break;
				 }
				else 
				{
					response.setContentType("text/html; charset=UTF-8");
					Cookie user_cookie=new Cookie("error",URLEncoder.encode("Sai mật khẩu! Vui lòng thử lại", "UTF-8"));
		            user_cookie.setMaxAge(5);
		            response.addCookie(user_cookie);
		            response.sendRedirect("/AiLaTrieuPhu/view/login.jsp");
				}
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvalidKeySpecException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
		}
		else 
		{
            response.setContentType("text/html; charset=UTF-8");
			Cookie user_cookie=new Cookie("error",URLEncoder.encode("Tên đăng nhập không tồn tại! Vui lòng thử lại", "UTF-8"));
            user_cookie.setMaxAge(5);
            response.addCookie(user_cookie);
            response.sendRedirect("/AiLaTrieuPhu/view/login.jsp");
		}
	}
}