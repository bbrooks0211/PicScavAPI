package brooks.api.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import brooks.api.business.interfaces.GamePlayerInterface;
import brooks.api.business.interfaces.UserBusinessServiceInterface;
import brooks.api.data.interfaces.DataAccessInterface;
import brooks.api.models.FoundItemModel;
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
			player = setUsernameForPlayerModel(player);
		}
		
		return list;
	}
	
	@Override
	public PlayerModel getPlayerByID(int id) {
		PlayerModel player = dao.findByID(id);
		player = setUsernameForPlayerModel(player);
		return player;
	}
	
	@Override
	public PlayerModel getPlayer(PlayerModel player) {
		player = dao.find(player);
		player = setUsernameForPlayerModel(player);
		return player;
	}
	
	@Override
	public boolean updatePlayerPoints(PlayerModel player, FoundItemModel item) {
		player = getPlayer(player);
		player.setScore(player.getScore() + item.getPoints());
		boolean status = dao.update(player);
		
		return status;
	}
	
	@Override
	public boolean addPlayerToGame(int userID, int gameID) {
		return dao.create(new PlayerModel(-1, userID, gameID));
	}
	
	private PlayerModel setUsernameForPlayerModel(PlayerModel player) {
		UserModel user = userService.findByID(player.getUserID());
		player.setUsername(user.getUsername());
		return player;
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
