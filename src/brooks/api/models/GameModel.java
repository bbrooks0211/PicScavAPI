package brooks.api.models;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Model for a game and all of it's information
 * @author Brendan Brooks
 *
 */
public class GameModel {
	private int id;
	private int hostID;
	private String hostUsername;
	private String lobbyName;
	private String category;
	private long timeLimit;
	private Timestamp endTime;
	private Timestamp startTime;
	private int numberOfItems;
	private List<PlayerModel> players;
	private List<GameItemModel> items;
	private List<FoundItemModel> foundItems;
	
	public GameModel(int id, int hostID, String hostUsername, String lobbyName, String category, long timeLimit,
			Timestamp endTime, Timestamp startTime, int numberOfItems, List<PlayerModel> players, List<GameItemModel> items,
			List<FoundItemModel> foundItems) {
		super();
		this.id = id;
		this.hostID = hostID;
		this.hostUsername = hostUsername;
		this.lobbyName = lobbyName;
		this.category = category;
		this.timeLimit = timeLimit;
		this.endTime = endTime;
		this.startTime = startTime;
		this.players = players;
		this.items = items;
		this.foundItems = foundItems;
		this.numberOfItems = numberOfItems;
	}
	
	public GameModel(int id, int hostID, String lobbyName, String category, long timeLimit,
			Timestamp endTime, Timestamp startTime, int numberOfItems) {
		super();
		this.id = id;
		this.hostID = hostID;
		this.hostUsername = "";
		this.lobbyName = lobbyName;
		this.category = category;
		this.timeLimit = timeLimit;
		this.endTime = endTime;
		this.startTime = startTime;
		this.numberOfItems = numberOfItems;
		this.players = new ArrayList<PlayerModel>();
		this.items = new ArrayList<GameItemModel>();
		this.foundItems = new ArrayList<FoundItemModel>();
	}
	
	public GameModel(int id, int hostID, String lobbyName, String category, long timeLimit,
			Timestamp endTime, Timestamp startTime) {
		super();
		this.id = id;
		this.hostID = hostID;
		this.hostUsername = "";
		this.lobbyName = lobbyName;
		this.category = category;
		this.timeLimit = timeLimit;
		this.endTime = endTime;
		this.startTime = startTime;
		this.numberOfItems = -1;
		this.players = new ArrayList<PlayerModel>();
		this.items = new ArrayList<GameItemModel>();
		this.foundItems = new ArrayList<FoundItemModel>();
	}
	
	public GameModel() {
		super();
		this.id = -1;
		this.hostID = -1;
		this.hostUsername = "";
		this.lobbyName = "";
		this.category = "";
		this.timeLimit = 0;
		this.endTime = new Timestamp(0);
		this.startTime = new Timestamp(0);
		this.players = new ArrayList<PlayerModel>();
		this.items = new ArrayList<GameItemModel>();
		this.foundItems = new ArrayList<FoundItemModel>();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getHostID() {
		return hostID;
	}

	public void setHostID(int hostID) {
		this.hostID = hostID;
	}

	public String getHostUsername() {
		return hostUsername;
	}

	public void setHostUsername(String hostUsername) {
		this.hostUsername = hostUsername;
	}

	public String getLobbyName() {
		return lobbyName;
	}

	public void setLobbyName(String lobbyName) {
		this.lobbyName = lobbyName;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public long getTimeLimit() {
		return timeLimit;
	}

	public void setTimeLimit(long timeLimit) {
		this.timeLimit = timeLimit;
	}

	public Timestamp getEndTime() {
		return endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	public Timestamp getStartTime() {
		return startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	public List<PlayerModel> getPlayers() {
		return players;
	}

	public void setPlayers(List<PlayerModel> players) {
		this.players = players;
	}

	public List<GameItemModel> getItems() {
		return items;
	}

	public void setItems(List<GameItemModel> items) {
		this.items = items;
	}

	public List<FoundItemModel> getFoundItems() {
		return foundItems;
	}

	public void setFoundItems(List<FoundItemModel> foundItems) {
		this.foundItems = foundItems;
	}

	public int getNumberOfItems() {
		return numberOfItems;
	}

	public void setNumberOfItems(int numberOfItems) {
		this.numberOfItems = numberOfItems;
	}
	
}
