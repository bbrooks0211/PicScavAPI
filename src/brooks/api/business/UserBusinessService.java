package brooks.api.business;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;

import brooks.api.business.interfaces.UserBusinessServiceInterface;
import brooks.api.data.UserDAO;
import brooks.api.data.interfaces.UserDataAccessInterface;
import brooks.api.models.LoginModel;
import brooks.api.models.RegistrationModel;
import brooks.api.models.UserModel;
import brooks.api.utility.PasswordSecurityUtility;
/**
 * Service for user business logic
 * @author Brendan Brooks
 */
public class UserBusinessService implements UserBusinessServiceInterface {
	
	UserDataAccessInterface dao;

	/**
	 * Business service for adding the user to the database. Encrypts the password
	 * before passing it to the DAO.
	 * 
	 * @return Boolean (false = fail, true = success)
	 * @throws UnsupportedEncodingException
	 */
	@Override
	public boolean registerUser(RegistrationModel model) throws UnsupportedEncodingException {
		
		//Encrypt the password using the security utility
		model.setPassword(PasswordSecurityUtility.getHashedPassword(model.getPassword()));
		
		//Insert into the database and ensure that it succeeded or failed
		boolean result = dao.create(model);
		
		//Return the result
		return result;
	}

	/**
	 * Logs in the user by passing a UserModel to the data service.
	 * 
	 * @param model - LoginModel
	 * @return UserModel for the login attempt. If the model's id = -1, the login failed.
	 * @throws UnsupportedEncodingException
	 */
	@Override
	public UserModel loginUser(LoginModel model) throws UnsupportedEncodingException {
		System.out.println("ENTERING LOGINUSER FUCNCTION");
		//Encrypt the password so it can be checked with the encrypted password in the database
		model.setPassword(PasswordSecurityUtility.getHashedPassword(model.getPassword()));
		
		//Grab the returned UserModel from the login attempt
		UserModel user = dao.findByLoginCredentials(model);
		System.out.println("EXITING LOGINUSER FUCNCTION");
		//Return the UserModel
		return user;
	}
	
	/**
	 * Finds a user by username
	 * 
	 * @param username - String
	 * @return UserModel for the user. If id = -1, the user couldn't be found.
	 */
	@Override
	public UserModel findByUsername(String username) {
		return dao.findByUsername(username);
	}
	
	/**
	 * Find a user by their email
	 * @param email - String
	 * @return UserModel for the user. If id = -1, the user couldn't be found.
	 */
	@Override
	public UserModel findByEmail(String email) {
		return dao.findByEmail(email);
	}
	
	/**
	 * Checks if a username already exists in the database or not
	 * 
	 * @param username - String
	 * @return boolean for if the username already exists or not
	 */
	@Override
	public boolean usernameExists(String username) throws SQLException {
		UserModel user = dao.findByUsername(username);
		
		if (user.getId() == -1) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * Checks the database to see if the email already exists
	 * @return boolean for if the email is already in the database or not
	 */
	@Override
	public boolean emailExists(String email) throws SQLException {
		UserModel user = dao.findByEmail(email);
		
		if(user.getId() == -1) {
			return false;
		}
		
		return true;
	}
	
	@Autowired
	public void setUserDAO(UserDAO dao)
	{
		this.dao = dao;
	}

	@Override
	public boolean updateUser(UserModel user) {
		return dao.update(user);
	}

	@Override
	public boolean deleteUser(int id) {
		return dao.delete(id);
	}
}
