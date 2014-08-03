package model;
import org.hibernate.*;
import org.hibernate.cfg.*;

import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class Function {
	static SessionFactory sessionfactory=new Configuration().configure().buildSessionFactory();
	static Session ss;
	static Transaction transaction;
	
	public static List select(Class tableClass, String whereString)
	{
		ss=sessionfactory.openSession();
		transaction= ss.beginTransaction();
		
		String hql="from "+ tableClass.getName().replace("model.", "") +" where "+whereString;
		Query query = ss.createQuery(hql);
		List<Object> Olist=query.list();
		ss.close();
		return Olist;
	}
	
	public static void insert(Object obj)
	{
		ss=sessionfactory.openSession();
		transaction= ss.beginTransaction();
		
		ss.save(obj);
		transaction.commit();
		ss.close();
	}
	
	public static void delete(Class tableClass, String whereString)
	{
		ss=sessionfactory.openSession();
		transaction= ss.beginTransaction();
		
		String hql="delete from "+tableClass.getName().replace("model.", "")+" where "+whereString;
		Query q=ss.createQuery(hql);
		q.executeUpdate();
		transaction.commit();
		ss.close();
	}
	
	public static void update(Class tableClass, String setString, String whereString)
	{
		ss=sessionfactory.openSession();
		transaction= ss.beginTransaction();
		
		String hql="update "+tableClass.getName().replace("model.", "")+" set "+ setString+" where "+whereString;
		Query q=ss.createQuery(hql);
		q.executeUpdate();
		transaction.commit();
		ss.close();
	}
// Các hàm xử lí Password
	public static String generateStorngPasswordHash(String password) throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        int iterations = 1000;
        char[] chars = password.toCharArray();
        byte[] salt = getSalt().getBytes();
         
        PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, 64 * 8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = skf.generateSecret(spec).getEncoded();
        return iterations + ":" + toHex(salt) + ":" + toHex(hash);
    }
     
    public static String getSalt() throws NoSuchAlgorithmException
    {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt.toString();
    }
     
    public static String toHex(byte[] array) throws NoSuchAlgorithmException
    {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        if(paddingLength > 0)
        {
            return String.format("%0"  +paddingLength + "d", 0) + hex;
        }else{
            return hex;
        }
    }
    public static boolean validatePassword(String originalPassword, String storedPassword) throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        String[] parts = storedPassword.split(":");
        int iterations = Integer.parseInt(parts[0]);
        byte[] salt = fromHex(parts[1]);
        byte[] hash = fromHex(parts[2]);
         
        PBEKeySpec spec = new PBEKeySpec(originalPassword.toCharArray(), salt, iterations, hash.length * 8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] testHash = skf.generateSecret(spec).getEncoded();
         
        int diff = hash.length ^ testHash.length;
        for(int i = 0; i < hash.length && i < testHash.length; i++)
        {
            diff |= hash[i] ^ testHash[i];
        }
        return diff == 0;
    }
    public static byte[] fromHex(String hex) throws NoSuchAlgorithmException
    {
        byte[] bytes = new byte[hex.length() / 2];
        for(int i = 0; i<bytes.length ;i++)
        {
            bytes[i] = (byte)Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return bytes;
    }
	public static void main(String[] args) 
	{
		// How to uses: please enter "true" for whereString if you have no whereString.
		
		// SELECT
		List<Question> ql= select(Question.class,"questionID>75");
		for(Question emp : ql)
			System.out.println("List of Question:"+emp.getContent());
		
		//INSERT
		Question q=new Question("Noi dung", "A", "B", "C", "D", "A", 1);
		insert(q);
		
		//UPDATE
		update(Question.class,"content='updated2'","questionID=102");
		
		//DELETE
		delete(Question.class,"questionID=103");
		
	}
}
