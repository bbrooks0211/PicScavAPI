package brooks.api.models;

public class PlayerModel {
	
	private int id;
	private int userID;
	private String username;
	private int score;
	
	public PlayerModel(int id, int userID, String username, int score) {
		super();
		this.id = id;
		this.userID = userID;
		this.username = username;
		this.score = score;
	}
	
	public PlayerModel() {
		super();
		this.id = -1;
		this.userID = -1;
		this.username = "";
		this.score = -1;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
}
