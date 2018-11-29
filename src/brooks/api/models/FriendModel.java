package brooks.api.models;

/**
 * Model that contains information for a friend of a user
 * @author Brendan Brooks
 *
 */
public class FriendModel {
	private int id;
	private String username;
	
	public FriendModel() {
		this.id = -1;
		this.username = "";
	}
	
	public FriendModel(int id, String username) {
		super();
		this.id = id;
		this.username = username;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
}
