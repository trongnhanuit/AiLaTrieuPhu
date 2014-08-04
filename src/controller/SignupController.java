package controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Function;
import model.Player;

/**
 * Servlet implementation class SignupController
 */
@WebServlet("/SignupController")
public class SignupController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SignupController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Player player = new Player();
		player.setUsername(request.getParameter("username"));
		try {
			player.setPassword(Function.generateStorngPasswordHash(request.getParameter("password")));
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		player.setPlayername(request.getParameter("playername"));
		player.setAddress(request.getParameter("address"));
		player.setBirthday(Integer.parseInt(request.getParameter("birthday")));
		player.setGovermentid(request.getParameter("govermentid"));
		boolean sex = (request.getParameter("sex")=="Male")?true:false;
		player.setSex(sex);
		player.setStatus(0);
		Function.insert(player);
		response.sendRedirect("http://localhost:8080/AiLaTrieuPhu/view/login.jsp");
	}

}
