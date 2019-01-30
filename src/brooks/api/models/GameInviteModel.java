package brooks.api.models;

/**
 * Model for game invites
 * @author Brendan Brooks
 *
 */
public class GameInviteModel {
	
	private int id;
	private int senderID;
	private String senderUsername;
	private int gameID;
	private String lobbyName;
	private long timeRemaining;
	private int receiverID;
	private String receiverUsername;
	
	public GameInviteModel(int id, int senderID, String senderUsername, int gameID, String lobbyName,
			long timeRemaining, int receiverID, String receiverUsername) {
		super();
		this.id = id;
		this.senderID = senderID;
		this.senderUsername = senderUsername;
		this.gameID = gameID;
		this.lobbyName = lobbyName;
		this.timeRemaining = timeRemaining;
		this.receiverID = receiverID;
		this.receiverUsername = receiverUsername;
	}
	
	public GameInviteModel(int id, int senderID, int gameID, int receiverID) {
		super();
		this.id = id;
		this.senderID = senderID;
		this.gameID = gameID;
		this.receiverID = receiverID;
		this.receiverUsername = "";
		this.senderUsername = "";
	}
	
	public GameInviteModel() {
		super();
		this.id = -1;
		this.senderID = -1;
		this.senderUsername = "";
		this.gameID = -1;
		this.lobbyName = "";
		this.timeRemaining = -1;
		this.receiverID = -1;
		this.receiverUsername = "";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSenderID() {
		return senderID;
	}

	public void setSenderID(int senderID) {
		this.senderID = senderID;
	}

	public String getSenderUsername() {
		return senderUsername;
	}

	public void setSenderUsername(String senderUsername) {
		this.senderUsername = senderUsername;
	}

	public int getGameID() {
		return gameID;
	}

	public void setGameID(int gameID) {
		this.gameID = gameID;
	}

	public String getLobbyName() {
		return lobbyName;
	}

	public void setLobbyName(String lobbyName) {
		this.lobbyName = lobbyName;
	}

	public long getTimeRemaining() {
		return timeRemaining;
	}

	public void setTimeRemaining(long timeRemaining) {
		this.timeRemaining = timeRemaining;
	}

	public int getReceiverID() {
		return receiverID;
	}

	public void setReceiverID(int receiverID) {
		this.receiverID = receiverID;
	}

	public String getReceiverUsername() {
		return receiverUsername;
	}

	public void setReceiverUsername(String receiverUsername) {
		this.receiverUsername = receiverUsername;
	}
}
