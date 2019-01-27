package brooks.api.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Model for items tied to a specific game
 * @author Brendan Brooks
 *
 */
public class GameItemModel {
	private int id;
	private int gameID;
	private String item;
	private int points;
	private int found;
	private List<FoundItemModel> findings;
	
	public GameItemModel(int id, int gameID, String item, int points, int found, List<FoundItemModel> findings) {
		super();
		this.id = id;
		this.gameID = gameID;
		this.item = item;
		this.points = points;
		this.found = found;
		this.findings = findings;
	}
	
	public GameItemModel() {
		super();
		this.id = -1;
		this.gameID = -1;
		this.item = "";
		this.points = -1;
		this.found = -1;
		this.findings = new ArrayList<FoundItemModel>();
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

	public List<FoundItemModel> getFindings() {
		return findings;
	}

	public void setFindings(List<FoundItemModel> findings) {
		this.findings = findings;
	}
}
