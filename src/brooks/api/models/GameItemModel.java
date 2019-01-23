package brooks.api.models;

public class GameItemModel {
	private int id;
	private int gameID;
	private String item;
	private int points;
	private int found;
	
	public GameItemModel(int id, int gameID, String item, int points, int found) {
		super();
		this.id = id;
		this.gameID = gameID;
		this.item = item;
		this.points = points;
		this.found = found;
	}
	
	public GameItemModel() {
		super();
		this.id = -1;
		this.gameID = -1;
		this.item = "";
		this.points = -1;
		this.found = -1;
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

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public int getFound() {
		return found;
	}

	public void setFound(int found) {
		this.found = found;
	}
}
