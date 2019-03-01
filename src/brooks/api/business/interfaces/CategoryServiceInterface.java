package brooks.api.business.interfaces;

import java.util.List;

import brooks.api.models.CategoryModel;
import brooks.api.utility.exceptions.CategoryDoesNotExistException;

public interface CategoryServiceInterface {
	public boolean createNewCategory(CategoryModel category);
	public CategoryModel findByID(int id) throws CategoryDoesNotExistException;
	CategoryModel findByCategoryName(String name) throws CategoryDoesNotExistException;
}
