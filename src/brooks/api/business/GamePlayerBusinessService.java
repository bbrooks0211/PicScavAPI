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

	/**
	 * Get all the players for a game by the ID of the game
	 * @param gameID The ID of the game
	 * @return List of PlayerModels
	 */
	@Override
	public List<PlayerModel> getPlayersForGame(int gameID) {
		//Get all the players for the game from the database
		List<PlayerModel> list = dao.findAllForID(gameID);
		
		//Set the username for every model
		for(PlayerModel player : list)
		{
			player = setUsernameForPlayerModel(player);
		}
		
		return list;
	}
	
	/**
	 * Get a player by the ID
	 * @param id The id of the relationship
	 * @return PlayerModel
	 */
	@Override
	public PlayerModel getPlayerByID(int id) {
		PlayerModel player = dao.findByID(id);
		player = setUsernameForPlayerModel(player);
		return player;
	}
	
	/**
	 * Get a player based on the PlayerModel
	 * @param player The PlayerModel for the one to be found
	 * @return PlayerModel
	 */
	@Override
	public PlayerModel getPlayer(PlayerModel player) {
		player = dao.find(player);
		player = setUsernameForPlayerModel(player);
		return player;
	}
	
	/**
	 * Update the points for a player
	 * @param player The PlayerModel for the person to update the points for
	 * @param item The FoundItemModel for reference to update the player's points
	 * @return boolean
	 */
	@Override
	public boolean updatePlayerPoints(PlayerModel player, FoundItemModel item) {
		player = getPlayer(player);
		player.setScore(player.getScore() + item.getPoints());
		boolean status = dao.update(player);
		
		return status;
	}
	
	/**
	 * Add a player to a game
	 * @param userID The ID of the user to add to the game
	 * @param gameID The ID of the game to add the player to
	 * @return boolean
	 */
	@Override
	public boolean addPlayerToGame(int userID, int gameID) {
		return dao.create(new PlayerModel(-1, userID, gameID));
	}
	
	/**
	 * Sets the username for a player
	 * @param player The PlayerModel to update
	 * @return PlayerModel
	 */
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
