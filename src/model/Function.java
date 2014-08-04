package model;
import org.hibernate.*;
import org.hibernate.cfg.*;
import java.util.*;


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
