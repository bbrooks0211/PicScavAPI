package brooks.api.models;

public class CategoryModel {
	private int id;
	private String name;
	private int paid;
	
	public CategoryModel(int id, String name, int paid) {
		super();
		this.id = id;
		this.name = name;
		this.paid = paid;
	}
	
	public CategoryModel() {
		this.id = -1;
		this.name = "";
		this.paid = 0;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPaid() {
		return paid;
	}

	public void setPaid(int paid) {
		this.paid = paid;
	}
	
}
