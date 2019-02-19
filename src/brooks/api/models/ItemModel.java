package brooks.api.models;

/**
 * General item model
 * @author Brendan Brooks
 *
 */
public class ItemModel {
	
	private int id;
	private String category;
	private String item;
	private int points;
	private int creatorID;
	private String creatorUsername;
	
	public ItemModel(int id, String category, String item, int points, int creatorID, String creatorUsername) {
		super();
		this.id = id;
		this.category = category;
		this.item = item;
		this.points = points;
		this.creatorID = creatorID;
		this.creatorUsername = creatorUsername;
	}
	
	public ItemModel() {
		super();
		this.id = -1;
		this.category = "";
		this.item = "";
		this.points = -1;
		this.creatorID = -1;
		this.creatorUsername = "";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
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

	public int getCreatorID() {
		return creatorID;
	}

	public void setCreatorID(int creatorID) {
		this.creatorID = creatorID;
	}

	public String getCreatorUsername() {
		return creatorUsername;
	}

	public void setCreatorUsername(String creatorUsername) {
		this.creatorUsername = creatorUsername;
	}
}
