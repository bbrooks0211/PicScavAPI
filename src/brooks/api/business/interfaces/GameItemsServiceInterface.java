package brooks.api.business.interfaces;

import java.util.List;

import brooks.api.models.FoundItemModel;
import brooks.api.models.GameItemModel;

public interface GameItemsServiceInterface {
	public boolean addNewGameItem(GameItemModel item);
	public boolean addNewGameItem(List<GameItemModel> list);
	List<GameItemModel> getItemsForGame(int gameID);
	List<FoundItemModel> getFoundItemsForGame(int gameID);
	GameItemModel getGameItem(int id);
	boolean addFoundItem(FoundItemModel item);
}
