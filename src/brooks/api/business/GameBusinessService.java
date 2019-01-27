package brooks.api.business;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import brooks.api.business.interfaces.GameBusinessServiceInterface;
import brooks.api.data.interfaces.DataAccessInterface;
import brooks.api.models.GameModel;
import brooks.api.utility.TimeUtility;
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
	public boolean createNewGame(GameModel game) throws GameTooLongException
	{
		//Ensure that the game time isn't longer than (approximately) a month
		if(game.getTimeLimit() > 730) {
			//Throw an error to force this to be dealt with
			throw new GameTooLongException();
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
	
	@Autowired
	private void setGameDAO(DataAccessInterface<GameModel> dao) {
		this.gameDAO = dao;
	}	
}
