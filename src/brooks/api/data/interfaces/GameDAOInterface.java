package brooks.api.data.interfaces;

import brooks.api.models.GameModel;

public interface GameDAOInterface extends DataAccessInterface<GameModel> {
	/**
	 * Creates a database entry for the parameter
	 * @param model
	 * @return boolean
	 */
	public int createAndReturnID(GameModel model);
}
