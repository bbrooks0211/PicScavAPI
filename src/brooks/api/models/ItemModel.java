package brooks.api.models;

public class ItemModel {
	
	private int id;
	private String category;
	private String item;
	private int points;
	
	public ItemModel(int id, String category, String item, int points) {
		super();
		this.id = id;
		this.category = category;
		this.item = item;
		this.points = points;
	}
	
	public ItemModel() {
		super();
		this.id = -1;
		this.category = "";
		this.item = "";
		this.points = -1;
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
}
