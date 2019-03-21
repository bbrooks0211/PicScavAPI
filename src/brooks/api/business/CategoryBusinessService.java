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

/*
Copyright 2019, Brendan Brooks.  

This file is part of PicScav.

PicScav is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

PicScav is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with PicScav.  If not, see <https://www.gnu.org/licenses/>.
*/