package brooks.api.business;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import brooks.api.business.interfaces.GameBusinessServiceInterface;
import brooks.api.business.interfaces.GameInvitesBusinessServiceInterface;
import brooks.api.business.interfaces.GameItemsServiceInterface;
import brooks.api.business.interfaces.GamePlayerInterface;
import brooks.api.business.interfaces.ItemReferenceBusinessServiceInterface;
import brooks.api.business.interfaces.UserBusinessServiceInterface;
import brooks.api.data.interfaces.DataAccessInterface;
import brooks.api.data.interfaces.GameDAOInterface;
import brooks.api.models.GameInviteModel;
import brooks.api.models.GameItemModel;
import brooks.api.models.GameModel;
import brooks.api.models.ItemModel;
import brooks.api.models.PlayerModel;
import brooks.api.models.UserModel;
import brooks.api.utility.TimeUtility;
import brooks.api.utility.exceptions.FailureToCreateException;
import brooks.api.utility.exceptions.GameNotFoundException;
import brooks.api.utility.exceptions.GameTooLongException;
import brooks.api.utility.exceptions.NotEnoughItemsException;
import brooks.api.utility.exceptions.UserNotFoundException;
import brooks.api.utility.interceptors.LoggingInterceptor;


/**
 * Business Service for game logic, etc.
 * @author Brendan Brooks
 *
 */
public class GameBusinessService implements GameBusinessServiceInterface {
	
	private GameDAOInterface gameDAO;
	private ItemReferenceBusinessServiceInterface itemService;
	private GameItemsServiceInterface gameItemsService;
	private GameInvitesBusinessServiceInterface invitesService;
	private UserBusinessServiceInterface userService;
	private GamePlayerInterface playerService;
	
	private final Logger logger = LoggerFactory.getLogger(LoggingInterceptor.class);

	/**
	 * Creates a new game from a game model. Creates the items and sends the invites. 
	 * @param game
	 * @throws GameTooLondException
	 * @return boolean
	 * @throws FailureToCreateException 
	 * @throws NotEnoughItemsException 
	 */
	@Override
	public boolean createNewGame(GameModel game) throws GameTooLongException, FailureToCreateException, NotEnoughItemsException
	{
		//Ensure that the game time isn't longer than (approximately) a month
		if(game.getTimeLimit() > 730) {
			//Throw an exception to force this to be dealt with
			throw new GameTooLongException();
		}
		
		//Create a date to initialize a new Timestamp
		Date date = new Date();
		Timestamp time = new Timestamp(date.getTime());
		//Set the game start time
		game.setStartTime(time);
		//Set the end time for the game by adding the length 
		game.setEndTime(TimeUtility.addHoursToTimestamp(time, game.getTimeLimit()));
		//Create the game and return the ID
		int gameID = gameDAO.createAndReturnID(game);
		
		//If the game failed to create, throw an exception
		if(gameID == -1)
			throw new FailureToCreateException();
		
		try {
			//Create the game items
			generateGameItems(gameID, game.getNumberOfItems(), game.getCategory());
		} catch (FailureToCreateException | NotEnoughItemsException e) {
			logger.error("[ERROR] EXCEPTION OCCURRED: " + e.getLocalizedMessage() + "\n" + e.getStackTrace());
			
			//Delete the created game since it couldn't be created
			gameDAO.delete(gameID);
			
			//Rethrow the exceptions
			if(e instanceof NotEnoughItemsException)
				throw new NotEnoughItemsException();

			throw new FailureToCreateException();
		}
		
		//Send the game invites
		try {
			boolean allInvitesSent = sendInvitesFromList(game.getPlayers(), gameID, game.getHostID());
			boolean addedHostToGame = playerService.addPlayerToGame(game.getHostID(), gameID);
		} catch (GameNotFoundException e) {
			logger.error("[ERROR] GAME COULD NOT BE FOUND FOR ID OF GAME JUST CREATED (ID: " + gameID + ") \n" + e.getStackTrace());
		}
		
		//Create the game by sending it to the data access layer
		return true;
	}
	
	/**
	 * Get all the games for a user by their ID
	 * @param userID The id of the user
	 * @return List List of GameModels
	 */
	@Override
	public List<GameModel> getGames(int userID) throws UserNotFoundException {
		//Get the user
		UserModel user = userService.findByID(userID);
		
		//Check that the returned user is a valid one
		if(user.getId() == -1)
			throw new UserNotFoundException();
		
		//Get all the games for the user
		List<GameModel> list = gameDAO.findAllForID(userID);
		
		//Set the rest of the details for the games
		for(GameModel game : list)
		{
			setGameDetails(game);
		}
		
		return list;
	}
	
	/**
	 * Get all current games for a user by their ID
	 * @param userID The id of the user
	 * @return List List of GameModels
	 */
	@Override
	public List<GameModel> getCurrentGames(int userID) throws UserNotFoundException {
		//Get the user
		UserModel user = userService.findByID(userID);
		
		//Check that the returned user is a valid one
		if(user.getId() == -1)
			throw new UserNotFoundException();
		
		//Get all the games for the user
		List<GameModel> list = gameDAO.getCurentGames(userID);
		
		//Set the rest of the details for the games
		for(GameModel game : list)
		{
			setGameDetails(game);
		}
		
		return list;
	}
	
	/**
	 * Get all current games for a user by their ID
	 * @param userID The id of the user
	 * @return List List of GameModels
	 */
	@Override
	public List<GameModel> getPastGames(int userID) throws UserNotFoundException {
		//Get the user
		UserModel user = userService.findByID(userID);
		
		//Check that the returned user is a valid one
		if(user.getId() == -1)
			throw new UserNotFoundException();
		
		//Get all the games for the user
		List<GameModel> list = gameDAO.getPastGames(userID);
		
		//Set the rest of the details for the games
		for(GameModel game : list)
		{
			setGameDetails(game);
		}
		
		return list;
	}
	
	/**
	 * Get a game by the ID
	 * @param id The ID of the game to be retrieved
	 * @return GameModel
	 */
	@Override
	public GameModel getGame(int id) throws GameNotFoundException {
		//Ensure that the game exists
		if(!gameExists(id))
			throw new GameNotFoundException();
		
		GameModel game = gameDAO.findByID(id);
		
		game = setGameDetails(game);
		
		//Return the game
		return game;
	}
	
	/**
	 * Checks if a game exists based on the id
	 * @param int
	 * @return boolean
	 */
	@Override
	public boolean gameExists(int id) {
		//Get the game based on the id
		GameModel game = gameDAO.findByID(id);
		//If the id is -1, it doesn't exist
		if(game.getId() == -1)
			return false;
		
		return true;
	}
	
	/**
	 * Generates the items for a game being created
	 * @param gameID
	 * @param numberOfItems
	 * @param category
	 * @return
	 * @throws FailureToCreateException
	 * @throws NotEnoughItemsException
	 */
	private boolean generateGameItems(int gameID, int numberOfItems, String category) throws FailureToCreateException, NotEnoughItemsException {
		List<GameItemModel> list = new ArrayList<GameItemModel>();
		List<ItemModel> fullItemList = new ArrayList<ItemModel>();
		fullItemList = itemService.getAllForCategory(category);
		Random r = new Random();
		
		if(fullItemList.size() < numberOfItems)
			throw new NotEnoughItemsException();
		
		//Simple loop that gets a random item from the list. Could be more advanced by shuffling the list before pulling an item from it. 
		for(int i = 0; i < numberOfItems; i++)
		{
			ItemModel item = fullItemList.remove(r.nextInt(fullItemList.size()));
			list.add(new GameItemModel(-1, gameID, item.getItem(), item.getPoints(), 0, null));
		}
		
		if(!gameItemsService.addNewGameItem(list))
			throw new FailureToCreateException();
		
		return true;
	}
	
	/**
	 * Send invites from a list of users
	 * @param list
	 * @param gameID
	 * @param hostID
	 * @return boolean 
	 * @throws GameNotFoundException
	 */
	private boolean sendInvitesFromList(List<PlayerModel> list, int gameID, int hostID) throws GameNotFoundException {
		boolean status = true;
		if(!gameExists(gameID))
			throw new GameNotFoundException();
		
		for(PlayerModel p : list)
		{
			try {
				invitesService.sendInvite(new GameInviteModel(-1, hostID, gameID, p.getUserID(), 0));
			} catch (UserNotFoundException e) {
				logger.error("[ERROR] COULD NOT SEND INVITE BECAUSE USER COULD NOT BE FOUND (ID: " + p.getUserID() + ") \n" + e.getStackTrace() );
				status = false;
			}
		}
		return status;
	}
	
	/**
	 * 
	 * @param game The game that needs details set
	 * @return GameModel The game's model with populated items, players, and found items
	 */
	private GameModel setGameDetails(GameModel game) {
		game.setItems(gameItemsService.getItemsForGame(game.getId()));
		game.setPlayers(playerService.getPlayersForGame(game.getId()));
		game.setFoundItems(gameItemsService.getFoundItemsForGame(game.getId()));
		return game;
	}
	
	@Autowired
	private void setGameDAO(GameDAOInterface dao) {
		this.gameDAO = dao;
	}
	
	@Autowired
	public void setItemService(ItemReferenceBusinessServiceInterface service) {
		this.itemService = service;
	}
	
	@Autowired
	public void setGameItemsService(GameItemsServiceInterface service) {
		this.gameItemsService = service;
	}
	
	@Autowired
	private void setGameInviteService(GameInvitesBusinessServiceInterface service) {
		this.invitesService = service;
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
