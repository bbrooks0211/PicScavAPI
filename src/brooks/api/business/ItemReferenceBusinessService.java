package brooks.api.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import brooks.api.business.interfaces.CategoryServiceInterface;
import brooks.api.business.interfaces.ItemReferenceBusinessServiceInterface;
import brooks.api.data.interfaces.DataAccessInterface;
import brooks.api.models.ItemModel;
import brooks.api.utility.exceptions.ItemAlreadyExistsException;

/**
 * Business service class for getting the items to use as a reference, for things such as generating the item lists for newly created games
 * @author Brendan Brooks
 *
 */
public class ItemReferenceBusinessService implements ItemReferenceBusinessServiceInterface {
	
	private DataAccessInterface<ItemModel> itemDAO;
	private CategoryServiceInterface categoryService;

	/**
	 * Add an item to the pool of categorized items
	 * @param item
	 */
	@Override
	public boolean addItem(ItemModel item) throws ItemAlreadyExistsException {
		//Check if the item already exists within the category
		if(itemAlreadyExists(item))
			throw new ItemAlreadyExistsException();
		item.setCategory(item.getCategory().toLowerCase());
		return itemDAO.create(item);
	}

	/**
	 * Update a reference item
	 * @param item The ItemModel of the item to update
	 * @return boolean
	 */
	@Override
	public boolean updateItem(ItemModel item) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Delete a reference item by it's ID
	 * @param id The ID of the item to delete
	 * @return boolean
	 */
	@Override
	public boolean deleteItem(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Get an item by the ItemModel
	 * @param ItemModel 
	 * @return ItemModel
	 */
	@Override
	public ItemModel getItem(ItemModel model) {
		return itemDAO.find(model);
	}

	/**
	 * Get a reference item by the ID
	 * @param id The ID of the item to be retrieved
	 * @return ItemModel
	 */
	@Override
	public ItemModel getItem(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Get all items for a category
	 * @param category The category to get the items for
	 * @return List of ItemModel's
	 */
	@Override
	public List<ItemModel> getAllForCategory(String category) {
		//Get the category object from it's name
		
		//Ensure that it exists
		
		//Find all by the category ID
		return itemDAO.findAllByString(category.toLowerCase());
	}
	
	/**
	 * Get all items for a category's ID
	 * @param id The ID of the category
	 * @return List of ItemModels's
	 */
	@Override
	public List<ItemModel> getAllForCategoryID(int id) {
		return itemDAO.findAllForID(id);
	}

	/**
	 * Gets all the items - USE WITH CAUTION
	 * @return List of all items in the database
	 */
	@Override
	public List<ItemModel> getAllItems() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * Checks if an item already exists (within its category)
	 * @param item
	 * @return boolean
	 */
	private boolean itemAlreadyExists(ItemModel item) {
		
		//Attempt to retrieve the item from the database
		ItemModel model = itemDAO.find(item);
		
		//ID of -1 means the database could not find anything
		if(model.getId() == -1)
			return false;
		
		return true;
	}
	
	@Autowired
	public void setItemDAO(DataAccessInterface<ItemModel> dao) {
		this.itemDAO = dao;
	}
	
	@Autowired
	private void setCategoryService(CategoryServiceInterface service) {
		this.categoryService = service;
	}

}

/*
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
