package brooks.api.business;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import brooks.api.business.interfaces.UserBusinessServiceInterface;
import brooks.api.data.UserDAO;
import brooks.api.data.interfaces.DataAccessInterface;
import brooks.api.data.interfaces.UserDataAccessInterface;
import brooks.api.models.FriendModel;
import brooks.api.models.LoginModel;
import brooks.api.models.RegistrationModel;
import brooks.api.models.UserModel;
import brooks.api.utility.PasswordSecurityUtility;
import brooks.api.utility.exceptions.DatabaseException;
import brooks.api.utility.exceptions.EmailAlreadyExistsException;
import brooks.api.utility.exceptions.UsernameAlreadyExistsException;
import brooks.api.utility.interceptors.LoggingInterceptor;
/**
 * Service for user business logic
 * @author Brendan Brooks
 */
public class UserBusinessService implements UserBusinessServiceInterface {
	
	UserDataAccessInterface dao;
	private DataAccessInterface<FriendModel> friendDAO;
	
	private final Logger logger = LoggerFactory.getLogger(LoggingInterceptor.class);
	/**
	 * Business service for adding the user to the database. Encrypts the password
	 * before passing it to the DAO.
	 * 
	 * @return Boolean (false = fail, true = success)
	 * @throws UnsupportedEncodingException, EmailAlreadyExistsException, DatabaseException
	 */
	@Override
	public boolean registerUser(RegistrationModel model) throws UnsupportedEncodingException, EmailAlreadyExistsException, UsernameAlreadyExistsException {
		
		//Encrypt the password using the security utility
		model.setPassword(PasswordSecurityUtility.getHashedPassword(model.getPassword()));

		//Ensure that the chosen email hasn't been selected already
		if (emailExists(model.getEmail()))
		{
			logger.info("[INFO] EMAIL ALREADY EXISTS EXCEPTION OCCURRED");
			//Throw an exception to force this scenario to be handled by the presentation layer
			throw new EmailAlreadyExistsException();
		}
		
		if (usernameExists(model.getUsername()))
		{
			logger.info("[INFO] USERNAME ALREADY EXISTS EXCEPTION OCCURRED");
			//Throw an exception to force this scenario to be handled by the presentation layer
			throw new UsernameAlreadyExistsException();
		}
		
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
		//Encrypt the password so it can be checked with the encrypted password in the database
		model.setPassword(PasswordSecurityUtility.getHashedPassword(model.getPassword()));
		
		//Grab the returned UserModel from the login attempt
		UserModel user = dao.findByLoginCredentials(model);
		
		//Get the friends for the user
		user.setFriends(friendDAO.findAllByString(model.getUsername()));

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
	public boolean usernameExists(String username) {
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
	public boolean emailExists(String email) {
		UserModel user = dao.findByEmail(email);
		
		if(user.getId() == -1) {
			return false;
		}
		
		return true;
	}
	
	@Override
	public boolean updateUser(UserModel user) {
		return dao.update(user);
	}

	//TODO: Delete all friend relationships upon deleting the user, delete all invites, and delete all games hosted by the user, as well as remove them from any games they are/have been involved in
	@Override
	public boolean deleteUser(int id) {
		return dao.delete(id);
	}
	
	@Autowired
	public void setUserDAO(UserDAO dao)
	{
		this.dao = dao;
	}
	
	@Autowired
	private void setFriendDAO(DataAccessInterface<FriendModel> dao) {
		this.friendDAO = dao;
	}
}
