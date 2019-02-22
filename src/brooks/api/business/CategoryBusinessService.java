package brooks.api.business;

import org.springframework.beans.factory.annotation.Autowired;

import brooks.api.business.interfaces.CategoryServiceInterface;
import brooks.api.data.interfaces.DataAccessInterface;
import brooks.api.models.CategoryModel;

public class CategoryBusinessService implements CategoryServiceInterface {
	
	DataAccessInterface<CategoryModel> categoryDAO;

	@Override
	public boolean createNewCategory(CategoryModel category) {
		return categoryDAO.create(category);
	}

	@Override
	public CategoryModel findByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Autowired
	private void setCategoryDAO(DataAccessInterface<CategoryModel> dao) {
		this.categoryDAO = dao;
	}
}
