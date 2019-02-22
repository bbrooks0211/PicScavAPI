package brooks.api.business.interfaces;

import brooks.api.models.CategoryModel;

public interface CategoryServiceInterface {
	public boolean createNewCategory(CategoryModel category);
	public CategoryModel findByID(int id);
}
