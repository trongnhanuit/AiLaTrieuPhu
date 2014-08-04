package controller;
 
import java.io.*;  
import java.sql.*;  
import java.util.List;

import javax.servlet.ServletException;  
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;  

import model.Function;
import model.Player;
import model.Question;
@WebServlet("/CheckUser") 
public class CheckUser extends HttpServlet {  
  
	private static final long serialVersionUID = 1L;

protected void doPost(HttpServletRequest request, HttpServletResponse response)  
            throws ServletException, IOException {  
        response.setContentType("text/html;charset=UTF-8");  
        PrintWriter out = response.getWriter();  
        try {  
        	List<Player> ql= Function.select(Player.class,"username='"+request.getParameter("username")+"'");              
        	if(ql.isEmpty())
                out.printf("OK"); 
            else      
            	out.printf("Error");  
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