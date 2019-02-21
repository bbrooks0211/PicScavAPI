package brooks.api.business.interfaces;

import brooks.api.models.GameModel;
import brooks.api.utility.exceptions.FailureToCreateException;
import brooks.api.utility.exceptions.GameNotFoundException;
import brooks.api.utility.exceptions.GameTooLongException;
import brooks.api.utility.exceptions.NotEnoughItemsException;

public interface GameBusinessServiceInterface {
	public boolean createNewGame(GameModel game) throws GameTooLongException, GameNotFoundException, FailureToCreateException, NotEnoughItemsException;

	boolean gameExists(int id);

	GameModel getGame(int id) throws GameNotFoundException;
}
