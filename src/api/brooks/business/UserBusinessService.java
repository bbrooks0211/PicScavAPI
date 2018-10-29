package api.brooks.business;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;

import api.brooks.data.UserDAO;
import api.brooks.interfaces.UserBusinessServiceInterface;
import api.brooks.models.LoginModel;
import api.brooks.models.RegistrationModel;
import api.brooks.models.UserModel;
import api.brooks.utility.PasswordSecurityUtility;

public class UserBusinessService implements UserBusinessServiceInterface {
	
	//@Autowired
	UserDAO dao = new UserDAO();

	@Override
	public boolean registerUser(RegistrationModel model) throws UnsupportedEncodingException {
		
		model.setPassword(PasswordSecurityUtility.getHashedPassword(model.getPassword()));
		boolean result = dao.create(model);
		return result;
	}

	@Override
	public UserModel loginUser(LoginModel model) throws UnsupportedEncodingException {
		model.setPassword(PasswordSecurityUtility.getHashedPassword(model.getPassword()));
		UserModel user = dao.tryLogin(model);
		return user;
	}
	
	public UserModel findByUsernam(String username) {
		return dao.findByUsername(username);
	}
	
	public UserModel findByEmail(String email) {
		return dao.findByEmail(email);
	}
	
	public boolean usernameExists(String username) throws SQLException {
		UserModel user = dao.findByUsername(username);
		
		if (user.getId() == -1) {
			return false;
		}
		
		return true;
	}
	
	public boolean emailExists(String email) throws SQLException {
		UserModel user = dao.findByEmail(email);
		
		if(user.getId() == -1) {
			return false;
		}
		
		return true;
	}
	
}
