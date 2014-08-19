package model;
import org.hibernate.*;
import org.hibernate.cfg.*;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.*;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;


public class Function {
	static SessionFactory sessionfactory=new Configuration().configure().buildSessionFactory();
	static Session ss;
	static Transaction transaction;
	
	public static List select(Class tableClass, String whereString)
	{
		ss=sessionfactory.openSession();
		transaction= ss.beginTransaction();
		
		String hql="from "+ tableClass.getName().replace("model.", "");
		if (!whereString.equals(""))
			hql=hql+" where "+whereString;
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
		
		String hql="delete from "+tableClass.getName().replace("model.", "");
		if (!whereString.equals(""))
			hql=hql+" where "+whereString;
		Query q=ss.createQuery(hql);
		q.executeUpdate();
		transaction.commit();
		ss.close();
	}
	
	public static void update(Class tableClass, String setString, String whereString)
	{
		ss=sessionfactory.openSession();
		transaction= ss.beginTransaction();
		
		String hql="update "+tableClass.getName().replace("model.", "")+" set "+ setString;
		if (!whereString.equals(""))
			hql=hql+" where "+whereString;
		Query q=ss.createQuery(hql);
		q.executeUpdate();
		transaction.commit();
		ss.close();
	}
	
	// Chuyen tu so cau sang so tien
	public static int convert2Prize(int num)
	{
		switch (num)
		{
			case 0: return 0; 
			case 1: return 200000; 
			case 2: return 400000; 
			case 3: return 600000; 
			case 4: return 1000000; 
			case 5: return 2000000; 
			case 6: return 3000000; 
			case 7: return 6000000; 
			case 8: return 10000000; 
			case 9: return 14000000; 
			case 10: return 22000000; 
			case 11: return 30000000; 
			case 12: return 40000000; 
			case 13: return 60000000; 
			case 14: return 85000000; 
			case 15: return 150000000; 
		}
		return 0;
	}
	
	// Xac dinh so tien sau khi thua cuoc
	public static int getFailedPrize(int roundID)
	{
		List <Round> rounds=select(Round.class,"roundID="+roundID);
		String[] questions=rounds.get(0).getQuestionlist().split("@");
		if (questions.length>10)
			return 22000000;
		else
			if (questions.length>5)
				return 2000000;
			else
				return 0;
	}
	
	// Xac dinh so tien o cau hoi hien tai khi nguoi choi quyet dinh dung cuoc choi
	public static int getCurrentPrize(int roundID)
	{
		List <Round> rounds=select(Round.class,"roundID="+roundID);
		String[] questions=rounds.get(0).getQuestionlist().split("@");
		return convert2Prize(questions.length);
	}
	public static int getCurrentQuestion(int roundID)
	{
		List <Round> rounds=select(Round.class,"roundID="+roundID);
		String[] questions=rounds.get(0).getQuestionlist().split("@");
		return questions.length;
	}
	// get current mainplayer
	public static String getCurrentMainPlayer()
	{
		List <Round> rounds=select(Round.class,"status=0");
		if (rounds.isEmpty())
			return "";
		List <Player> players=select(Player.class,"playerID="+rounds.get(0).getPlayer().getPlayerId());
		if (players.isEmpty())
			return "";
		return players.get(0).getUsername();
	}

	// get Player from username
	public static Player selectPlayer(String username)
	{
		List <Player> players=select(Player.class,"username='"+username+"'");
		return players.get(0);
	}
	// Get RoundID from username
	public static int getRoundID(String username)
	{
		int playerID=selectPlayer(username).getPlayerId();
		List <Round> rounds=select(Round.class,"mainplayer="+playerID);
		return rounds.get(0).getRoundId();
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
		
		/*List<Quickquestion> ql= Function.select(Quickquestion.class,"");
		Random rd = new Random();
		Quickquestion qqt=ql.get(rd.nextInt(ql.size()));
		String ansKey=qqt.getAnsKey();*/
		
		/*// SELECT
		List<Question> ql= select(Question.class,"questionID>75");
		for(Question emp : ql)
			System.out.println("List of Question:"+emp.getContent());
		
		//INSERT
		Question q=new Question("Noi dung", "A", "B", "C", "D", "A", 1);
		insert(q);
		
		//UPDATE
		update(Question.class,"content='updated2'","questionID=102");
		
		//DELETE
		delete(Question.class,"questionID=103");	*/
	}
}
