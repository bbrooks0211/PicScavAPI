package api.brooks.business;

import java.io.UnsupportedEncodingException;

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
}
