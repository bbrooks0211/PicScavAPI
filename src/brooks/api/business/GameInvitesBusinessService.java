package brooks.api.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import brooks.api.business.interfaces.GameBusinessServiceInterface;
import brooks.api.business.interfaces.GameInvitesBusinessServiceInterface;
import brooks.api.business.interfaces.UserBusinessServiceInterface;
import brooks.api.data.interfaces.DataAccessInterface;
import brooks.api.models.GameInviteModel;
import brooks.api.models.PlayerModel;
import brooks.api.models.UserModel;
import brooks.api.utility.exceptions.InviteAlreadyAcceptedException;
import brooks.api.utility.exceptions.InviteNotFoundException;
import brooks.api.utility.exceptions.UserNotFoundException;
import brooks.api.utility.exceptions.GameNotFoundException;

/**
 * Service for game invite business logic
 * @author Brendan Brooks
 *
 */
public class GameInvitesBusinessService implements GameInvitesBusinessServiceInterface {
	
	private DataAccessInterface<GameInviteModel> dao;
	private DataAccessInterface<PlayerModel> playerDAO;
	private GameBusinessServiceInterface gameService;
	private UserBusinessServiceInterface userService;

	/**
	 * Sends a game invite to the user
	 * @param GameInviteModel
	 * @return boolean
	 * 
	 */
	@Override
	public boolean sendInvite(GameInviteModel model) throws UserNotFoundException {
		//Get the id of the user receiving the invite based on the username provided
		model.setReceiverID(userService.findByUsername(model.getReceiverUsername()).getId());
		
		//Check that the user actually exists based on the id
		if(model.getReceiverID() == -1) {
			throw new UserNotFoundException();
		}
		
		//Create the invite and return the result
		return dao.create(model);
	}

	/**
	 * Accepts a received invite
	 * @param int
	 * @return boolean
	 */
	@Override
	public boolean acceptInvite(int id) throws InviteNotFoundException, InviteAlreadyAcceptedException, GameNotFoundException {
		//Get the invite
		GameInviteModel invite = dao.findByID(id);
		
		//Ensure that the invite actually exists
		if (invite.getId() == -1) 
			throw new InviteNotFoundException();
		//Ensure that it hasn't somehow already been accepted
		else if (invite.getAccepted() == 1)
			throw new InviteAlreadyAcceptedException();
		//Ensure that the game exists
		else if (!gameService.gameExists(invite.getGameID()))
			throw new GameNotFoundException();
		
		//Add the user to the game as a player, return false if it fails
		if(!playerDAO.create(new PlayerModel(-1, invite.getReceiverID(), invite.getGameID())))
			return false;
		
		//Mark the invite as accepted, return false if it fails
		if(!dao.update(invite))
			return false;
		
		return true;
	}

	/**
	 * Declines a game invite
	 * @param int
	 * @return boolean
	 */
	@Override
	public boolean declineInvite(int id) throws InviteNotFoundException, InviteAlreadyAcceptedException {
		GameInviteModel invite = dao.findByID(id);
		if (invite.getId() == -1) 
			throw new InviteNotFoundException();
		else if (invite.getAccepted() == 1)
			throw new InviteAlreadyAcceptedException();
		
		return dao.delete(id);
	}

	/**
	 * Get all the game invites for a user
	 * @param int
	 * @return ArrayList
	 */
	@Override
	public List<GameInviteModel> getInvitesForUser(int id) throws UserNotFoundException {
		//Get the user
		UserModel user = userService.findByID(id);
		//Check if the user exists, throw an exception if not
		if (user.getId() == -1) 
			throw new UserNotFoundException();
		//Return the list
		return dao.findAllForID(id);
	}

	@Autowired
	public void setGameDAO(DataAccessInterface<GameInviteModel> dao) {
		this.dao = dao;
	}
	
	@Autowired
	public void setPlayerDAO(DataAccessInterface<PlayerModel> dao) {
		this.playerDAO = dao;
	}
	
	@Autowired
	public void setUserService(UserBusinessServiceInterface service) {
		this.userService = service;
	}
	
	@Autowired
	public void setGameService(GameBusinessServiceInterface service) {
		this.gameService = service;
	}
}
