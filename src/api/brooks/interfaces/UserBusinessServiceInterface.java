package api.brooks.interfaces;

import java.io.UnsupportedEncodingException;

import api.brooks.models.LoginModel;
import api.brooks.models.RegistrationModel;
import api.brooks.models.UserModel;

public interface UserBusinessServiceInterface {
	public boolean registerUser(RegistrationModel model) throws UnsupportedEncodingException;
	public UserModel loginUser(LoginModel model) throws UnsupportedEncodingException;
}
