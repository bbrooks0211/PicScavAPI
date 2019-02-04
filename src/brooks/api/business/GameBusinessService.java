package brooks.api.business;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import brooks.api.business.interfaces.GameBusinessServiceInterface;
import brooks.api.data.interfaces.DataAccessInterface;
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
	
	private DataAccessInterface<GameModel> gameDAO;

	/**
	 * Creates a new game from a game model
	 * @param game
	 * @throws GameTooLondException
	 * @return boolean
	 */
	@Override
	public boolean createNewGame(GameModel game) throws GameTooLongException, GameNotFoundException
	{
		//Ensure that the game time isn't longer than (approximately) a month
		if(game.getTimeLimit() > 730) {
			//Throw an exception to force this to be dealt with
			throw new GameTooLongException();
		}
		//Ensure that the game exists, if not, throw an exception
		else if (!gameExists(game.getId())) {
			throw new GameNotFoundException();
		}
		
		//Create a date to initialize a new Timestamp
		Date date = new Date();
		Timestamp time = new Timestamp(date.getTime());
		//Set the game start time
		game.setStartTime(time);
		//Set the end time for the game by adding the length 
		game.setEndTime(TimeUtility.addHoursToTimestamp(time, game.getTimeLimit()));
		
		//Create the game by sending it to the data access layer
		return gameDAO.create(game);
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
	
	@Autowired
	private void setGameDAO(DataAccessInterface<GameModel> dao) {
		this.gameDAO = dao;
	}	
}
