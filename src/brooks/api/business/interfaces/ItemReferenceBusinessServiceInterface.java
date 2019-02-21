package brooks.api.business.interfaces;

import java.util.List;

import brooks.api.models.ItemModel;
import brooks.api.utility.exceptions.ItemAlreadyExistsException;

public interface ItemReferenceBusinessServiceInterface {
	public boolean addItem(ItemModel item) throws ItemAlreadyExistsException;
	public boolean updateItem(ItemModel item);
	public boolean deleteItem(int id);
	public ItemModel getItem(ItemModel model);
	public ItemModel getItem(int id);
	public List<ItemModel> getAllForCategory(String category);
	public List<ItemModel> getAllItems();
}
