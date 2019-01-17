package brooks.api.models;

/**
 * Model that contains information for a friend of a user
 * @author Brendan Brooks
 *
 */
public class FriendModel {
	private int id;
	private String friendUsername;
	private int friendID;
	private String username;
	private int userID;
	
	public FriendModel() {
		this.id = -1;
		this.username = "";
		this.friendUsername = "";
		this.friendID = -1;
		this.userID = -1;
	}
	
	public FriendModel(int id, String username, String friendUsername, int friendID, int userID) {
		super();
		this.id = id;
		this.username = username;
		this.friendUsername = friendUsername;
		this.friendID = friendID;
		this.userID = userID;
	}
	
	public FriendModel(int id, int friendID, int userID) {
		super();
		this.id = id;
		this.friendID = friendID;
		this.userID = userID;	
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
	public String getFriendUsername() {
		return friendUsername;
	}
	public void setFriendUsername(String friendUsername) {
		this.friendUsername = friendUsername;
	}
	public int getFriendID() {
		return friendID;
	}
	public void setFriendID(int friendID) {
		this.friendID = friendID;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}
}
