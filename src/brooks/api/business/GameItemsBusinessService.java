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
import brooks.api.utility.exceptions.ItemAlreadyFoundException;
import brooks.api.utility.interceptors.LoggingInterceptor;


public class GameItemsBusinessService implements GameItemsServiceInterface {
	
	private DataAccessInterface<GameItemModel> gameItemDAO;
	private DataAccessInterface<FoundItemModel> foundItemDAO;
	private UserBusinessServiceInterface userService;
	private GamePlayerInterface playerService;

	/**
	 * Adds a game item to the database
	 * @param item The item to be added
	 * @return boolean
	 */
	@Override
	public boolean addNewGameItem(GameItemModel item) {
		return gameItemDAO.create(item);
	}
	
	/**
	 * Adds a found item to the database
	 * @param item The found item to be added
	 * @return boolean
	 */
	@Override
	public boolean addFoundItem(FoundItemModel item) throws ItemAlreadyFoundException {
		item = setOtherFoundItemInfo(item);
		
		if (itemAlreadyFound(item))
			throw new ItemAlreadyFoundException();
		
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

	/**
	 * Adds a list of game items
	 * @param List The list of GameItemModels to be added to the database
	 * @return boolean
	 */
	@Override
	public boolean addNewGameItem(List<GameItemModel> list) {
		//Set the default status to successful
		boolean status = true;
		
		//For each item in the list, add it to the database
		for(GameItemModel item : list)
		{
			//If the item doesn't exist, set the status to false so we can deal with it
			if(!addNewGameItem(item))
				status = false;
		}
		
		return status;
	}
	
	/**
	 * Get all the items for a game by it's ID
	 * @param gameID The ID of the game
	 * @return List The list of GameItemModels to be returned
	 */
	@Override
	public List<GameItemModel> getItemsForGame(int gameID) {
		return gameItemDAO.findAllForID(gameID);
	}
	
	/**
	 * Get all the found items for a game by it's ID
	 * @param gameID The ID of the game
	 * @return List The list of FoundItemModels to be returned
	 */
	@Override
	public List<FoundItemModel> getFoundItemsForGame(int gameID) {
		//Get all the items
		List<FoundItemModel> items = foundItemDAO.findAllForID(gameID);
		
		//Set additional info for the items
		for(FoundItemModel item : items) 
		{
			item = setOtherFoundItemInfo(item);
		}
		
		return items;
	}
	
	/**
	 * Get a game item by the ID
	 * @param id The ID of the item to be grabbed
	 * @return GameItemModel
	 */
	@Override
	public GameItemModel getGameItem(int id) {
		GameItemModel item = gameItemDAO.findByID(id);
		return item;
	}
	
	/**
	 * Set the points and username for a found item
	 * @param item The item to update the info for
	 * @return FoundItemModel 
	 */
	private FoundItemModel setOtherFoundItemInfo(FoundItemModel item) {
		//Get the parent item that was referenced for the found item
		GameItemModel referencedItem = getGameItem(item.getItemID());
		
		//Set the point value from the info given by the referenced item
		item.setPoints(referencedItem.getPoints());
		
		//Get the user who found the item
		UserModel user = userService.findByID(item.getUserID());
		
		//Set the username for who found the item
		item.setUsername(user.getUsername());
		return item;
	}
	
	/**
	 * 
	 * @param item
	 * @return
	 */
	private boolean itemAlreadyFound(FoundItemModel item) {
		if (item.getGameID() == -1)
			return false;
		
		List<FoundItemModel> foundItems = getFoundItemsForGame(item.getGameID());
		
		for (FoundItemModel f : foundItems) {
			if (f.getItemID() == item.getItemID()) {
				return true;
			}
		}
		
		return false;
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