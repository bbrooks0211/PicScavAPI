package brooks.api.data.interfaces;

import java.util.List;

import brooks.api.models.GameModel;

public interface GameDAOInterface extends DataAccessInterface<GameModel> {
	/**
	 * Creates a database entry for the parameter
	 * @param model
	 * @return boolean
	 */
	public int createAndReturnID(GameModel model);
	
	
	/**
	 * Queries all current games
	 * @param id The user ID
	 * @return List
	 */
	public List<GameModel> getCurentGames(int id);
}
