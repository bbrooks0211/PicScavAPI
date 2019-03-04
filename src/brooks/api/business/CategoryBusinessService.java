package brooks.api.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import brooks.api.business.interfaces.CategoryServiceInterface;
import brooks.api.data.interfaces.DataAccessInterface;
import brooks.api.models.CategoryModel;
import brooks.api.utility.exceptions.CategoryDoesNotExistException;

/**
 * Business services for categories
 * @author Brendan Brooks
 *
 */
public class CategoryBusinessService implements CategoryServiceInterface {
	
	DataAccessInterface<CategoryModel> categoryDAO;

	/**
	 * For adding a new category
	 * @param category The category desired to be added
	 * @return Boolean
	 */
	@Override
	public boolean createNewCategory(CategoryModel category) {
		return categoryDAO.create(category);
	}

	/**
	 * Find a category by the ID
	 * @param id The id of the desired category to retrieve
	 * @return CategoryModel 
	 */
	@Override
	public CategoryModel findByID(int id) throws CategoryDoesNotExistException {
		CategoryModel category = categoryDAO.findByID(id);
		if(category.getId() == -1)
			throw new CategoryDoesNotExistException();
		
		return category;
	}
	
	/**
	 * Find a category by it's name
	 * @param name The name of the category
	 * @return CategoryModel
	 */
	@Override
	public CategoryModel findByCategoryName(String name) throws CategoryDoesNotExistException {
		CategoryModel category = categoryDAO.find(new CategoryModel(-1, name, 0));
		if(category.getId() == -1)
			throw new CategoryDoesNotExistException();
		
		return category;
	}
	
	@Autowired
	private void setCategoryDAO(DataAccessInterface<CategoryModel> dao) {
		this.categoryDAO = dao;
	}
}
