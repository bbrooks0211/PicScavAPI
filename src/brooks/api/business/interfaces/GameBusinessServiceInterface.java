package brooks.api.business.interfaces;

import java.util.List;

import brooks.api.models.GameModel;
import brooks.api.utility.exceptions.FailureToCreateException;
import brooks.api.utility.exceptions.GameNotFoundException;
import brooks.api.utility.exceptions.GameTooLongException;
import brooks.api.utility.exceptions.NotEnoughItemsException;
import brooks.api.utility.exceptions.UserNotFoundException;

public interface GameBusinessServiceInterface {
	
	public boolean createNewGame(GameModel game) throws GameTooLongException, GameNotFoundException, FailureToCreateException, NotEnoughItemsException;
	boolean gameExists(int id);
	GameModel getGame(int id) throws GameNotFoundException;
	List<GameModel> getGames(int userID) throws UserNotFoundException;
	List<GameModel> getCurrentGames(int userID) throws UserNotFoundException;
	List<GameModel> getPastGames(int userID) throws UserNotFoundException;
}
