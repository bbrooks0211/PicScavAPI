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
import brooks.api.business.interfaces.GameItemsServiceInterface;
import brooks.api.business.interfaces.ItemReferenceBusinessServiceInterface;
import brooks.api.data.interfaces.DataAccessInterface;
import brooks.api.data.interfaces.GameDAOInterface;
import brooks.api.models.GameItemModel;
import brooks.api.models.GameModel;
import brooks.api.models.ItemModel;
import brooks.api.utility.TimeUtility;
import brooks.api.utility.exceptions.FailureToCreateException;
import brooks.api.utility.exceptions.GameNotFoundException;
import brooks.api.utility.exceptions.GameTooLongException;
import brooks.api.utility.exceptions.NotEnoughItemsException;
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
		
		//Create the game by sending it to the data access layer
		return true;
	}
	
	public List<GameModel> getGames(int userID) {
		List<GameModel> list = gameDAO.findAllForID(userID);
		
		return list;
	}
	
	@Override
	public GameModel getGame(int id) throws GameNotFoundException {
		if(!gameExists(id))
			throw new GameNotFoundException();
		
		return gameDAO.findByID(id);
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
		
		if(!gameItemsService.create(list))
			throw new FailureToCreateException();
		
		return true;
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
}
