package brooks.api.models;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * General item model
 * @author Brendan Brooks
 *
 */
public class ItemModel {
	
	private int id;
	@NotNull(message="Category is required")
	@Size(min=1, max=300, message="Category must be between 1 and 100 characters")
	private String category;
	@NotNull(message="Item is required")
	@Size(min=1, max=300, message="Item must be between 1 and 100 characters")
	private String item;
	@NotNull(message="Points is a required field")
	@Min(value=1, message="Points must be at least 1")
	@Max(value=9999, message="Points cannot be over 9999")
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
		this.points = 0;
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
