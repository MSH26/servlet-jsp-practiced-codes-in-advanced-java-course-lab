package mvc.controller;

import java.sql.ResultSet;
import java.util.*;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class UserDataAccess{
	
	static UserModel userObj;
	static Session sessionObj;
	static SessionFactory sessionFactoryObj;
	
	
	public UserDataAccess(){}
	
	
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
	
	
	public Boolean insert(UserModel user) {
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
		}  finally {
			if(sessionObj != null) {
				sessionObj.close();
			}
		}
	}
	
	
	public UserModel getByUserName(String userName) {
		try {	
			sessionObj = buildSessionFactory().getCurrentSession();
			sessionObj.beginTransaction();
			UserModel user = sessionObj.get(UserModel.class, userName);
			sessionObj.getTransaction().commit();
			return user;
		}
		catch(Exception sqlException) {
			if(null != sessionObj.getTransaction()) {
				System.out.println("\n.......Transaction Is Being Rolled Back.......");
				sessionObj.getTransaction().rollback();
			}
			sqlException.printStackTrace();
			return null;
		} finally {
			if(sessionObj != null) {
				sessionObj.close();
			}
		}
	}
	
	public Boolean update(UserModel user, String userName) {
		try {	
			sessionObj = buildSessionFactory().getCurrentSession();
			sessionObj.beginTransaction();
			
			sessionObj.createQuery("update user set email='abc@gmail.com'").executeUpdate();
			sessionObj.getTransaction().commit();
			
			System.out.println("Done!");
		}
		catch(Exception sqlException) {
			if(null != sessionObj.getTransaction()) {
				System.out.println("\n.......Transaction Is Being Rolled Back.......");
				sessionObj.getTransaction().rollback();
			}
			sqlException.printStackTrace();
			return false;
		}  finally {
			if(sessionObj != null) {
				sessionObj.close();
			}
		}
	}
	
	public Boolean delete(int userId) {
		try {
			sessionObj = buildSessionFactory().getCurrentSession();
			sessionObj.beginTransaction();
			userObj = sessionObj.get(UserModel.class, id);
			sessionObj.delete(userObj);
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
		}  finally {
			if(sessionObj != null) {
				sessionObj.close();
			}
		}
	}
}