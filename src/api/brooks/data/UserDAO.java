package api.brooks.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;

import api.brooks.interfaces.DataAccessInterface;
import api.brooks.models.FriendModel;
import api.brooks.models.LoginModel;
import api.brooks.models.RegistrationModel;
import api.brooks.models.UserModel;
import api.brooks.utility.DatabaseUtility;

public class UserDAO implements DataAccessInterface<RegistrationModel>{
	
	// DB Connection Info
	Connection conn = null;
	String url = DatabaseUtility.url;
	String username = DatabaseUtility.username;
	String password = DatabaseUtility.password;

	@Override
	public boolean create(RegistrationModel model) {
		
		// Insert
		try 
		{
			String driver = "com.mysql.jdbc.Driver";
			Class.forName(driver).newInstance();
			// Connect to the Database
			conn = DriverManager.getConnection(url, username, password);

			// Insert an Album
			String sql1 = String.format("INSERT INTO  users(username, email, password) VALUES('%s', '%s', '%s')", model.getUsername(), model.getEmail(), model.getPassword());
			Statement stmt1 = conn.createStatement();
			stmt1.executeUpdate(sql1);
			
			// Get Auto-Increment PK back
			String sql2= "SELECT LAST_INSERT_ID() AS LAST_ID FROM users";
			ResultSet rs = stmt1.executeQuery(sql2);
			rs.next();
			String lastId = rs.getString("LAST_ID");
			rs.close();
			stmt1.close();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			//throw new DatabaseException(e);
		}
		finally
		{
			// Cleanup Database
			if(conn != null)
			{
				try 
				{
					conn.close();
				} 
				catch (SQLException e) 
				{
					e.printStackTrace();
					//throw new DatabaseException(e);
				}
			}
		}
		
		// Return OK
		return true;
	}

	@Override
	public boolean update(RegistrationModel model) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(RegistrationModel model) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public RegistrationModel find(RegistrationModel model) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RegistrationModel findByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public UserModel tryLogin(LoginModel model) {
		UserModel user = null;
		
		// Insert
		try 
		{
			String driver = "com.mysql.jdbc.Driver";
			Class.forName(driver).newInstance();
			// Connect to the Database
			conn = DriverManager.getConnection(url, username, password);

			// Insert an Album
			String sql1 = String.format("SELECT * FROM users WHERE username='%s' AND password='%s'", model.getUsername(), model.getPassword());
			Statement stmt1 = conn.createStatement();

			ResultSet rs = stmt1.executeQuery(sql1);
			
			if(!rs.next())
			{
				rs.close();
				stmt1.close();
				return new UserModel();
			}
			
			user = new UserModel(rs.getInt("id"), rs.getString("username"), rs.getString("email"), new ArrayList<FriendModel>(), rs.getInt("purchasedAdRemoval"));
			rs.close();
			stmt1.close();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			//throw new DatabaseException(e);
		}
		finally
		{
			// Cleanup Database
			if(conn != null)
			{
				try 
				{
					conn.close();
				} 
				catch (SQLException e) 
				{
					e.printStackTrace();
					//throw new DatabaseException(e);
				}
			}
		}
		return user;
	}
	
}
