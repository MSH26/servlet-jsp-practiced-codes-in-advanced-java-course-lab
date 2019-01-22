package bean;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DataAccess {

	private static Connection connection = null;   
	
	static{  
		try{  
			Class.forName("com.mysql.jdbc.Driver");  
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/login_control_system", "root", "");  
		}
		catch(Exception e){
			//Connection failled
		}  
	}

	public static Connection getConnection(){  
	    return connection;  
	}  
	
	public static int executeQuery(String query)
	{
		try{  
			Statement stmt=connection.createStatement();
			return stmt.executeUpdate(query);
		}
		catch(Exception e){
			return -1;
		}
	}

	public static ResultSet getData(String query)
	{
		try{  
			Statement stmt=connection.createStatement();
			return stmt.executeQuery(query);
		}
		catch(Exception e){
			return null;
		}
	}
}