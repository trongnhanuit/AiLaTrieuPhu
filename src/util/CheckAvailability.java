package util;
 
import java.io.*;  
import java.sql.*;  

import javax.servlet.ServletException;  
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;  
@WebServlet("/CheckAvailability") 
public class CheckAvailability extends HttpServlet {  
  
  
 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

protected void doPost(HttpServletRequest request, HttpServletResponse response)  
            throws ServletException, IOException {  
        response.setContentType("text/html;charset=UTF-8");  
        PrintWriter out = response.getWriter();  
        try {  
  
            String connectionURL = "jdbc:mysql://localhost:3306/altp"; // students is my database name  
            Connection connection = null;  
            Class.forName("com.mysql.jdbc.Driver").newInstance();  
            connection = DriverManager.getConnection(connectionURL, "root", "123456");  
            String uname = request.getParameter("username");  
            PreparedStatement ps = connection.prepareStatement("select username from player where username=?");  
            ps.setString(1,uname);  
            ResultSet rs = ps.executeQuery();  
               
            if (!rs.next()) {  
                out.printf("<font color=\"green\"><b>"+uname+"</b> có thể sử dụng</font>");  
            }  
            else{  
            out.printf("<font color=\"red\"><b>"+uname+"</b> đã được đăng kí</font>");  
            }  
            out.println();  
  
        } catch (Exception ex) {  
            out.println("Error ->" + ex.getMessage());  
        } finally {  
            out.close();  
        }  
    }  
  
    protected void doGet(HttpServletRequest request, HttpServletResponse response)  
            throws ServletException, IOException {  
        doPost(request, response);  
    }  
} 