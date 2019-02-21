package brooks.api.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import brooks.api.business.interfaces.ItemReferenceBusinessServiceInterface;
import brooks.api.data.interfaces.DataAccessInterface;
import brooks.api.models.ItemModel;
import brooks.api.utility.exceptions.ItemAlreadyExistsException;

/**
 * Business service class for getting the items to use as a reference, for things such as generating the item lists for newly created games, etc.
 * @author Brendan Brooks
 *
 */
public class ItemReferenceBusinessService implements ItemReferenceBusinessServiceInterface {
	
	private DataAccessInterface<ItemModel> itemDAO;

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

	@Override
	public boolean updateItem(ItemModel item) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteItem(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ItemModel getItem(ItemModel model) {
		// TODO Auto-generated method stub
		return itemDAO.find(model);
	}

	@Override
	public ItemModel getItem(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ItemModel> getAllForCategory(String category) {
		
		return itemDAO.findAllByString(category.toLowerCase());
	}

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

}
