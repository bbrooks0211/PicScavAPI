package brooks.api.models;

/**
 * Model for game items that have been found
 * @author Brendan Brooks
 *
 */
public class FoundItemModel {
	private int id;
	private int gameID;
	private int itemID;
	private int userID;
	private String username;
	
	public FoundItemModel(int id, int gameID, int itemID, int userID, String username) {
		super();
		this.id = id;
		this.gameID = gameID;
		this.itemID = itemID;
		this.userID = userID;
		this.username = username;
	}
	
	public FoundItemModel() {
		super();
		this.id = -1;
		this.gameID = -1;
		this.itemID = -1;
		this.userID = -1;
		this.username = "";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getGameID() {
		return gameID;
	}

	public void setGameID(int gameID) {
		this.gameID = gameID;
	}

	public int getItemID() {
		return itemID;
	}

	public void setItemID(int itemID) {
		this.itemID = itemID;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
