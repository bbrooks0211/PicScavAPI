package brooks.api.business.interfaces;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;

import brooks.api.models.LoginModel;
import brooks.api.models.RegistrationModel;
import brooks.api.models.UserModel;

public interface UserBusinessServiceInterface {
	public boolean registerUser(RegistrationModel model) throws UnsupportedEncodingException;
	public UserModel loginUser(LoginModel model) throws UnsupportedEncodingException;
	public boolean updateUser(UserModel user);
	public boolean deleteUser(int id);
	public UserModel findByUsername(String username);
	public UserModel findByEmail(String email);
	public boolean usernameExists(String username) throws SQLException;
	public boolean emailExists(String email) throws SQLException;
}
