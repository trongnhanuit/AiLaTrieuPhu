package util;

import java.sql.Connection;
import java.sql.DriverManager;
/*
 * Class tạo kết nối đến database
 */
public class DatabaseConnection {

	public static Connection getConnection(){
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/altp","root","123456");
			return connection;
		}catch(Exception ex){
			System.out.println("Error -->" + ex.getMessage());
            return null;
		}
	}
	public static void close(Connection connection){
		try{
			connection.close();
		}catch(Exception ex){
			
		}
	}

}
