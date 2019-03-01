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

	@Override
	public boolean createNewCategory(CategoryModel category) {
		return categoryDAO.create(category);
	}

	@Override
	public CategoryModel findByID(int id) throws CategoryDoesNotExistException {
		CategoryModel category = categoryDAO.findByID(id);
		if(category.getId() == -1)
			throw new CategoryDoesNotExistException();
		
		return category;
	}
	
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
