package dao;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Player;
import util.DatabaseConnection;
import util.PasswordProcess;

/*
 * Class GameDAO
 */
public class GameDAO {
	
		private Connection connection;
		private PreparedStatement preparedStatement;
		private Statement statement;
		private ResultSet resultSet;		
		/*
		 * Check login with @param name & pass (0: name& pass not correct, 1: wrong pass,2:ok ).
		 */
		public boolean checkLogin(Player player) throws SQLException{
			boolean check = false;
			String sql="SELECT password FROM player WHERE username=?";
			PasswordProcess ps = new PasswordProcess();
			try {
				connection=DatabaseConnection.getConnection();
				preparedStatement=connection.prepareStatement(sql);
				preparedStatement.setString(1,player.getUsername());
				resultSet=preparedStatement.executeQuery();
				 if(resultSet.next()){
					 check = false;
					 String temp = resultSet.getString(1);
					 if(ps.validatePassword(player.getPassword(), temp))
					 {
						 return true;
					 }
				 }
			}catch (Exception ex) {}
			return check;
		}
		public void addPlayer(Player player) throws SQLException{
			String sql="INSERT INTO player VALUES (null,?,?,?,?,?,?,?,0)";
			try {
				connection=DatabaseConnection.getConnection();
				preparedStatement=connection.prepareStatement(sql);
				preparedStatement.setString(1,player.getUsername());
				preparedStatement.setString(2,PasswordProcess.generateStorngPasswordHash(player.getPassword()));
				preparedStatement.setString(3,player.getPlayername());
				preparedStatement.setString(4,player.getAddress());
				preparedStatement.setInt(5,player.getBirthday());
				preparedStatement.setString(6,player.getGovermentid());
				preparedStatement.setBoolean(7,player.isSex());
				preparedStatement.executeUpdate();
			}catch (Exception ex) {}
		}
}