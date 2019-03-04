package brooks.api.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import brooks.api.business.interfaces.GamePlayerInterface;
import brooks.api.business.interfaces.UserBusinessServiceInterface;
import brooks.api.data.interfaces.DataAccessInterface;
import brooks.api.models.PlayerModel;
import brooks.api.models.UserModel;

public class GamePlayerBusinessService implements GamePlayerInterface {
	
	private DataAccessInterface<PlayerModel> dao;
	private UserBusinessServiceInterface userService;

	@Override
	public List<PlayerModel> getPlayersForGame(int gameID) {
		List<PlayerModel> list = dao.findAllForID(gameID);
		
		for(PlayerModel player : list)
		{
			UserModel user = userService.findByID(gameID);
			player.setUsername(user.getUsername());
		}
		
		return list;
	}
	
	@Override
	public boolean addPlayerToGame(int userID, int gameID) {
		return dao.create(new PlayerModel(-1, userID, gameID));
	}

	@Autowired
	private void setPlayerDAO(DataAccessInterface<PlayerModel> dao) {
		this.dao = dao;
	}
	
	@Autowired
	private void setUserService(UserBusinessServiceInterface service) {
		this.userService = service;
	}
}
