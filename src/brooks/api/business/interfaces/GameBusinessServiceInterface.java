package brooks.api.business.interfaces;

import brooks.api.models.GameModel;
import brooks.api.utility.exceptions.GameNotFoundException;
import brooks.api.utility.exceptions.GameTooLongException;

public interface GameBusinessServiceInterface {
	public boolean createNewGame(GameModel game) throws GameTooLongException, GameNotFoundException;

	boolean gameExists(int id);

	GameModel getGame(int id) throws GameNotFoundException;
}
