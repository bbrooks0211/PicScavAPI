package brooks.api.models;

/**
 * Model for logging in a user
 * @author Brendan Brooks
 *
 */
public class LoginModel {
	
	private String username;
	private String password;
	
	public LoginModel() {
		this.password = "";
		this.username = "";
	}
	
	public LoginModel(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
