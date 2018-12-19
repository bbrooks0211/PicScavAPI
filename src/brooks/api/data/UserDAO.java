package brooks.api.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import brooks.api.data.interfaces.DataAccessInterface;
import brooks.api.data.interfaces.UserDataAccessInterface;
import brooks.api.models.FriendModel;
import brooks.api.models.LoginModel;
import brooks.api.models.RegistrationModel;
import brooks.api.models.UserModel;
import brooks.api.utility.DatabaseUtility;
import brooks.api.utility.interceptors.LoggingInterceptor;

/**
 * Data Access Service for users. 
 * A custom interface had to be made for this one specifically because of findByEmail and findByUsername.
 * @author Brendan Brooks
 */
public class UserDAO implements UserDataAccessInterface{
	
	@SuppressWarnings("unused")
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplateObject;
	
	private final Logger logger = LoggerFactory.getLogger(LoggingInterceptor.class);
	
	/**
	 * @param t - RegistrationModel
	 * @return boolean - true = success, false = failure
	 */
	@Override
	public boolean create(RegistrationModel t) {
		String sql = "INSERT INTO users(username, email, password) VALUES(?, ?, ?)";
		try
		{
			int rows = jdbcTemplateObject.update(sql, t.getUsername(), t.getEmail(), t.getPassword());
			
			return rows == 1 ? true : false;
		} catch (Exception e)
		{
			e.printStackTrace();
			logger.error("[ERROR] DATABASE EXCEPTION OCCURRED: " + e.getLocalizedMessage() + "\n ------Stack trace: \n" + e.getStackTrace().toString());
		}
		
		return false;
	}
	
	@Override
	public boolean update(UserModel model) {
		String sql = "UPDATE users SET username=?, email=? WHERE id=?";
		try
		{
			int rows = jdbcTemplateObject.update(sql, model.getUsername(), model.getEmail(), model.getId());
			
			return rows == 1 ? true : false;
		} catch (Exception e)
		{
			e.printStackTrace();
			logger.error("[ERROR] DATABASE EXCEPTION OCCURRED: " + e.getLocalizedMessage() + "\n ------Stack trace: \n" + e.getStackTrace().toString());
		}
		
		return false;
	}

	@Override
	public boolean update(RegistrationModel model) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(int id) {
		String sql = "DELETE FROM users WHERE id=?";
		try
		{
			int rows = jdbcTemplateObject.update(sql, id);
			
			return rows == 1 ? true : false;
		} catch (Exception e)
		{
			e.printStackTrace();
			logger.error("[ERROR] DATABASE EXCEPTION OCCURRED: " + e.getLocalizedMessage() + "\n ------Stack trace: \n" + e.getStackTrace());
		}
		
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
	
	/**
	 * Find a user based on their username
	 * 
	 * @param username - String
	 * @return UserModel - if id = -1, the user does not exist
	 */
	@Override
	public UserModel findByUsername(String username) {
		String sql = "SELECT * FROM users WHERE username=?";
		
		try
		{
			SqlRowSet srs = jdbcTemplateObject.queryForRowSet(sql, username);
			if (srs.next())
			{
				UserModel user = new UserModel(srs.getInt("id"), srs.getString("username"), srs.getString("email"), new ArrayList<FriendModel>(), srs.getInt("purchasedAdRemoval"));
				return user;
			}
			else
			{
				return new UserModel();
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("[ERROR] DATABASE EXCEPTION OCCURRED: " + e.getLocalizedMessage() + "\n ------Stack trace: \n" + e.getStackTrace());
			
			return new UserModel();
		}
	}
	
	/**
	 * Find a user based on their email
	 * 
	 * @param email - String
	 * @return UserModel - if id = -1, the user does not exist
	 */
	@Override
	public UserModel findByEmail(String email) {
		String sql = "SELECT * FROM users WHERE email=?";
		
		try
		{
			SqlRowSet srs = jdbcTemplateObject.queryForRowSet(sql, email);
			if (srs.next())
			{
				UserModel user = new UserModel(srs.getInt("id"), srs.getString("username"), srs.getString("email"), new ArrayList<FriendModel>(), srs.getInt("purchasedAdRemoval"));
				return user;
			}
			else
			{
				return new UserModel();
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("[ERROR] DATABASE EXCEPTION OCCURRED: " + e.getLocalizedMessage() + "\n ------Stack trace: \n" + e.getStackTrace());
			
			return new UserModel();
		}
	}
	
	/**
	 * Login function that will find a user from the database based on their username and
	 * encrypted password.
	 * 
	 * @param model - a LoginModel with the Login Info
	 * @return UserModel for the log-in attempt. An id of -1 means the user doesn't exist.
	 */
	@Override
	public UserModel findByLoginCredentials(LoginModel model) {
		
		String sql = "SELECT * FROM users WHERE username=? AND password=?";
		
		try
		{
			SqlRowSet srs = jdbcTemplateObject.queryForRowSet(sql, model.getUsername(), model.getPassword());
			if (srs.next())
			{
				System.out.println("Found the user");
				UserModel user = new UserModel(srs.getInt("id"), srs.getString("username"), srs.getString("email"), new ArrayList<FriendModel>(), srs.getInt("purchasedAdRemoval"));
				return user;
			}
			else
			{
				return new UserModel();
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("[ERROR] DATABASE EXCEPTION OCCURRED: " + e.getLocalizedMessage() + "\n ------Stack trace: \n" + e.getStackTrace());
			
			return new UserModel();
		}
	}
	
	@Autowired
	private void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}

	@Override
	public List<RegistrationModel> findAllForID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RegistrationModel> findAllByString(String string) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
