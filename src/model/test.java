package model;
import org.hibernate.*;
import org.hibernate.cfg.*;


public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SessionFactory sf = new Configuration().configure().buildSessionFactory();
		Session ss = sf.openSession();
		Transaction tr = ss.beginTransaction();
		
		//Selecting Student Records 
		Object object = ss.load(Question.class,1);;
		Question stud = (Question) object;
		String a =stud.getContent();

		//Get All Employees
		/*Query query = ss.createQuery("from Chidoan");
		List<Chidoan> empList = query.list();
		for(Chidoan emp : empList){
			System.out.println("List of Employees::"+emp.getIdChiDoan());*/
		
		// Add
		/*Chidoan cd = new Chidoan(2, "KP2");
		ss.save(cd);
		tr.commit();*/
		 
		//Update
		/*Object object = ss.load(Chidoan.class,1);
		Chidoan stud = (Chidoan) object;
		String a =stud.getTenChiDoan();
		stud.setTenChiDoan("KP11");
		ss.update(stud);
		tr.commit();*/
		
		//Deleting Student records
		/*Object object = ss.load(Chidoan.class,1);
		Chidoan stud = (Chidoan) object;
				ss.delete(stud);
				tr.commit();
				if (ss != null)
					ss.close();*/


	}

}
