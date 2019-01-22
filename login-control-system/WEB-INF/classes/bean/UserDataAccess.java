package bean;

import bean.*;
import java.sql.ResultSet;
import java.util.*;


public class UserDataAccess{
	
	public UserDataAccess(){}
	
	public Boolean insert(UserBean user) {
		try{
			String query = "insert into user (username, password, firstname, lastname, dateofbirth, gender, email, phone, questionone, questiontwo, questionthree, answerone, answertwo, answerthree) value ('"+user.getEmail() +"','"+ user.getPassword() +"','"+ user.getFirstName() +"','"+ user.getLastName() +"','"+ user.getDateOfBirth() +"','"+ user.getGender() +"','"+ user.getEmail() +"','"+ user.getPhone() +"','"+ user.getQuestionOne() +"','"+ user.getQuestionTwo() +"','"+ user.getQuestionThree() +"','"+ user.getAnswerOne() +"','"+ user.getAnswerTwo() +"','"+ user.getAnswerThree() +"')";
			if(DataAccess.executeQuery(query) > 0){
				return true;
			}
			return false;
		}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public ArrayList<UserBean> getAll() {
		try{
			String query = "select * from user";
			ResultSet rs = DataAccess.getData(query);
			
			/**	ResultSetMetaData rsmd=rs.getMetaData();
			*	rsmd.getColumnCount() gives the columns
			*	rsmd.getColumnName(x) gives the name of the specific column
			*	rsmd.getColumnTypeName(x) gives the name of the specific column type name
			*/
			
			ArrayList<UserBean> users = new ArrayList<UserBean>();
			
			while(rs.next()){
				UserBean user = new UserBean(Integer.parseInt(rs.getString(1)), rs.getString(2));
				user.setPassword(rs.getString(3));
				user.setFirstName(rs.getString(4));
				user.setLastName(rs.getString(5));
				user.setDateOfBirth(rs.getString(6));
				user.setGender(rs.getString(7));
				user.setEmail(rs.getString(8));
				user.setPhone(rs.getString(9));
				user.setQuestionOne(rs.getString(10));
				user.setQuestionTwo(rs.getString(11));
				user.setQuestionThree(rs.getString(12));
				user.setAnswerOne(rs.getString(13));
				user.setAnswerTwo(rs.getString(14));
				user.setAnswerThree(rs.getString(15));
				
				users.add(user);
			}
			return users;
		}
		catch(Exception e){
			return null;
		}
	}
	
	public UserBean getByUserName(String userName) {
		try{
			String query = "select * from user where username='"+ userName +"'";
			ResultSet rs = DataAccess.getData(query);
			
			if (rs.next()) {
				UserBean user = new UserBean(Integer.parseInt(rs.getString(1)), rs.getString(2));
				user.setPassword(rs.getString(3));
				user.setFirstName(rs.getString(4));
				user.setLastName(rs.getString(5));
				user.setDateOfBirth(rs.getString(6));
				user.setGender(rs.getString(7));
				user.setEmail(rs.getString(8));
				user.setPhone(rs.getString(9));
				user.setQuestionOne(rs.getString(10));
				user.setQuestionTwo(rs.getString(11));
				user.setQuestionThree(rs.getString(12));
				user.setAnswerOne(rs.getString(13));
				user.setAnswerTwo(rs.getString(14));
				user.setAnswerThree(rs.getString(15));
				
				return user;
			} 
			return null;
		}
		catch(Exception e){
			return null;
		}
	}
	
	public Boolean update(UserBean user, String userName) {
		try{
			String query = "update user set password='"+ user.getPassword() +"' where username='"+ userName +"'";
			if(DataAccess.executeQuery(query) > 0){
				return true;
			}
			return false;
		}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public Boolean delete(int userId) {
		String query = "delete from user where id='"+ userId +"'";
		DataAccess.executeQuery(query);
		return true;
	}
}