package brooks.api.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import brooks.api.business.interfaces.ItemReferenceBusinessServiceInterface;
import brooks.api.data.interfaces.DataAccessInterface;
import brooks.api.models.ItemModel;
import brooks.api.utility.exceptions.ItemAlreadyExistsException;

public class ItemReferenceBusinessService implements ItemReferenceBusinessServiceInterface {
	
	private DataAccessInterface<ItemModel> itemDAO;

	@Override
	public boolean addItem(ItemModel item) throws ItemAlreadyExistsException {
		if(itemAlreadyExists(item))
			throw new ItemAlreadyExistsException();
		
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
		return null;
	}

	@Override
	public ItemModel getItem(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ItemModel> getAllForCategory(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ItemModel> getAllItems() {
		// TODO Auto-generated method stub
		return null;
	}
	
	private boolean itemAlreadyExists(ItemModel item) {
		ItemModel model = itemDAO.find(item);
		
		if(model.getId() == -1)
			return false;
		
		return true;
	}
	
	@Autowired
	public void setItemDAO(DataAccessInterface<ItemModel> dao) {
		this.itemDAO = dao;
	}

}
