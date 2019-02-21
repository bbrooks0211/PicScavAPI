package brooks.api.business;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import brooks.api.business.interfaces.GameBusinessServiceInterface;
import brooks.api.business.interfaces.ItemReferenceBusinessServiceInterface;
import brooks.api.data.interfaces.DataAccessInterface;
import brooks.api.data.interfaces.GameDAOInterface;
import brooks.api.models.GameItemModel;
import brooks.api.models.GameModel;
import brooks.api.utility.TimeUtility;
import brooks.api.utility.exceptions.GameNotFoundException;
import brooks.api.utility.exceptions.GameTooLongException;

/**
 * Business Service for game logic, etc.
 * @author Brendan Brooks
 *
 */
public class GameBusinessService implements GameBusinessServiceInterface {
	
	private GameDAOInterface gameDAO;
	private ItemReferenceBusinessServiceInterface itemRefService;

	/**
	 * Creates a new game from a game model
	 * @param game
	 * @throws GameTooLondException
	 * @return boolean
	 */
	@Override
	public boolean createNewGame(GameModel game) throws GameTooLongException
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
		int gameID = gameDAO.createAndReturnID(game);
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
	
	private List<GameItemModel> generateGameItems(int gameID, int numberOfItems, String category) {
		List<GameItemModel> list = new ArrayList<GameItemModel>();
		
		return list;
	}
	
	@Autowired
	private void setGameDAO(GameDAOInterface dao) {
		this.gameDAO = dao;
	}
	
	@Autowired
	private void setItemRefService(ItemReferenceBusinessServiceInterface service) {
		this.itemRefService = service;
	}	
}
