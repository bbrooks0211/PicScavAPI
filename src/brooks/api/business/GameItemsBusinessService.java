package brooks.api.business;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import brooks.api.business.interfaces.GameItemsServiceInterface;
import brooks.api.business.interfaces.GamePlayerInterface;
import brooks.api.business.interfaces.UserBusinessServiceInterface;
import brooks.api.data.interfaces.DataAccessInterface;
import brooks.api.models.FoundItemModel;
import brooks.api.models.GameItemModel;
import brooks.api.models.PlayerModel;
import brooks.api.models.UserModel;
import brooks.api.utility.interceptors.LoggingInterceptor;

public class GameItemsBusinessService implements GameItemsServiceInterface {
	
	private DataAccessInterface<GameItemModel> gameItemDAO;
	private DataAccessInterface<FoundItemModel> foundItemDAO;
	private UserBusinessServiceInterface userService;
	private GamePlayerInterface playerService;

	@Override
	public boolean addNewGameItem(GameItemModel item) {
		return gameItemDAO.create(item);
	}
	
	@Override
	public boolean addFoundItem(FoundItemModel item) {
		item = setOtherFoundItemInfo(item);
		//Get the referenced game item and set the ID
		GameItemModel referencedItem = new GameItemModel();
		referencedItem.setId(item.getItemID());

		//Update the item (sets it's found variable from 0 to 1 in the database)
		gameItemDAO.update(referencedItem);
		
		//update the player's points
		PlayerModel player = new PlayerModel();
		player.setGameID(item.getGameID());
		player.setUserID(item.getUserID());
		boolean didUpdatePlayerPoints = playerService.updatePlayerPoints(player, item);
		
		if(!didUpdatePlayerPoints) {
			//throw new DidNotUpdateException(); (not created yet)
		}
		
		return foundItemDAO.create(item);
	}

	@Override
	public boolean addNewGameItem(List<GameItemModel> list) {
		boolean status = true;
		
		for(GameItemModel item : list)
		{
			if(!addNewGameItem(item))
				status = false;
		}
		
		return status;
	}
	
	@Override
	public List<GameItemModel> getItemsForGame(int gameID) {
		return gameItemDAO.findAllForID(gameID);
	}
	
	@Override
	public List<FoundItemModel> getFoundItemsForGame(int gameID) {
		List<FoundItemModel> items = foundItemDAO.findAllForID(gameID);
		for(FoundItemModel item : items) 
		{
			item = setOtherFoundItemInfo(item);
		}
		
		return items;
	}
	
	@Override
	public GameItemModel getGameItem(int id) {
		GameItemModel item = gameItemDAO.findByID(id);
		return item;
	}
	
	private FoundItemModel setOtherFoundItemInfo(FoundItemModel item) {
		GameItemModel referencedItem = getGameItem(item.getItemID());
		item.setPoints(referencedItem.getPoints());
		UserModel user = userService.findByID(item.getUserID());
		item.setUsername(user.getUsername());
		return item;
	}
	
	@Autowired
	private void setGameItemDAO(DataAccessInterface<GameItemModel> dao) {
		this.gameItemDAO = dao;
	}
	
	@Autowired
	private void setFoundItemDAO(DataAccessInterface<FoundItemModel> dao) {
		this.foundItemDAO = dao;
	}
	
	@Autowired
	private void setUserService(UserBusinessServiceInterface service) {
		this.userService = service;
	}
	
	@Autowired
	private void setPlayerService(GamePlayerInterface service) {
		this.playerService = service;
	}
	
}
