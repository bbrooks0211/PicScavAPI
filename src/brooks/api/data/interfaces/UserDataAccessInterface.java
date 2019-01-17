package brooks.api.data.interfaces;

import brooks.api.models.LoginModel;
import brooks.api.models.RegistrationModel;
import brooks.api.models.UserModel;

/**
 * Interface specifically for User Data, because of the extra functions needed within.
 * @author Brendan Brooks
 */
public interface UserDataAccessInterface extends DataAccessInterface<RegistrationModel> {
	public boolean update(UserModel model);
	public UserModel findByUsername(String username);
	public UserModel findByEmail(String email);
	public UserModel findByLoginCredentials(LoginModel model);
	public UserModel findUserByID(int id);
}
