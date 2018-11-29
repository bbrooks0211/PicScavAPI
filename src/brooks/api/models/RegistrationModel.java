package brooks.api.models;

/**
 * Model for registering a user.
 * @author Brendan Brooks
 *
 */
public class RegistrationModel {
	public String username;
	public String email;
	public String password;
	
	public RegistrationModel()
	{
		username = "";
		email = "";
		password = "";
	}
	
	public RegistrationModel(String username, String email, String password) {
		super();
		this.username = username;
		this.email = email;
		this.password = password;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
