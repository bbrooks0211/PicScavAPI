package brooks.api.business.interfaces;

import java.util.List;

import brooks.api.models.PlayerModel;

public interface GamePlayerInterface {
	public List<PlayerModel> getPlayersForGame(int gameID);

	boolean addPlayerToGame(int userID, int gameID);
}
