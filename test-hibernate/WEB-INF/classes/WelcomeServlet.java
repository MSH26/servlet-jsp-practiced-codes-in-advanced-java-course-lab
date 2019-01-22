import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;

import java.util.Date;



/** Shows all the request headers sent on this
* particular request.
*/
public class WelcomeServlet extends HttpServlet implements ServletContextListener
{
	static UserModel userObj;
	static Session sessionObj;
	static SessionFactory sessionFactoryObj;

	private static SessionFactory buildSessionFactory() {
		// Creating Configuration Instance & Passing Hibernate Configuration File
		Configuration configObj = new Configuration();
		/**	Xml file coudn't solve the entity issue
		*	that's why this method is used for mapping
		*	With Model Class Containing Annotations
		*/
		configObj.addAnnotatedClass(UserModel.class);	
		configObj.configure("hibernate.cfg.xml");

		// Since Hibernate Version 4.x, ServiceRegistry Is Being Used
		ServiceRegistry serviceRegistryObj = new StandardServiceRegistryBuilder().applySettings(configObj.getProperties()).build(); 

		// Creating Hibernate SessionFactory Instance
		sessionFactoryObj = configObj.buildSessionFactory(serviceRegistryObj);
		return sessionFactoryObj;
	}

	
	public boolean insert(UserModel user){
		try{
			sessionObj = buildSessionFactory().openSession();
			sessionObj.beginTransaction();
			sessionObj.save(user);
			sessionObj.clear();
			sessionObj.getTransaction().commit();
			return true;
		}
		catch(Exception sqlException) {
			if(null != sessionObj.getTransaction()) {
				System.out.println("\n.......Transaction Is Being Rolled Back.......");
				sessionObj.getTransaction().rollback();
			}
			sqlException.printStackTrace();
			return false;
		} 
	}
	
	public boolean delete(int id){
		try {
			sessionObj = buildSessionFactory().getCurrentSession();
			session.beginTransaction();
			userObj = session.get(UserModel.class, id);
			session.delete(userObj);
			session.getTransaction().commit();
			return true;
		}
		catch(Exception sqlException) {
			if(null != sessionObj.getTransaction()) {
				System.out.println("\n.......Transaction Is Being Rolled Back.......");
				sessionObj.getTransaction().rollback();
			}
			sqlException.printStackTrace();
			return false;
		} 
	}
	
	public boolean update(int id){
		try {	
			sessionObj = buildSessionFactory().getCurrentSession();
			session.beginTransaction();
			
			session.createQuery("update user set email='abc@gmail.com'").executeUpdate();
			session.getTransaction().commit();
			
			System.out.println("Done!");
		}
		catch(Exception sqlException) {
			if(null != sessionObj.getTransaction()) {
				System.out.println("\n.......Transaction Is Being Rolled Back.......");
				sessionObj.getTransaction().rollback();
			}
			sqlException.printStackTrace();
			return false;
		} 
	}
	
	public UserModel get(int userName){
		try {	
			sessionObj = buildSessionFactory().getCurrentSession();
			session.beginTransaction();
			UserModel user = session.get(UserModel.class, userName);
			session.getTransaction().commit();
			return user;
		}
		catch(Exception sqlException) {
			if(null != sessionObj.getTransaction()) {
				System.out.println("\n.......Transaction Is Being Rolled Back.......");
				sessionObj.getTransaction().rollback();
			}
			sqlException.printStackTrace();
			return null;
		}
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException 
	{
		userObj = new UserModel();
		userObj.setUserName("Editor " );
		userObj.setPassword("Editor " );
		userObj.setFirstName("Editor ");
		userObj.setLastName("Editor " );
		userObj.setDateOfBirth("Editor ");
		userObj.setGender("Editor " );
		userObj.setEmail("Editor " );
		userObj.setPhone("Editor " );
		insert(userObj);
	}
	/** Let the same servlet handle both GET and POST. */
	public void doPost(HttpServletRequest request,
	HttpServletResponse response)
	throws ServletException, IOException {
		doGet(request, response);
	}
}