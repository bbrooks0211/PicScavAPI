package brooks.api.business;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import brooks.api.business.interfaces.GameBusinessServiceInterface;
import brooks.api.business.interfaces.GameInvitesBusinessServiceInterface;
import brooks.api.business.interfaces.UserBusinessServiceInterface;
import brooks.api.data.interfaces.DataAccessInterface;
import brooks.api.models.GameInviteModel;
import brooks.api.models.GameModel;
import brooks.api.models.PlayerModel;
import brooks.api.models.UserModel;
import brooks.api.utility.exceptions.InviteAlreadyAcceptedException;
import brooks.api.utility.exceptions.InviteNotFoundException;
import brooks.api.utility.exceptions.UserNotFoundException;
import brooks.api.utility.interceptors.LoggingInterceptor;
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
	
	private final Logger logger = LoggerFactory.getLogger(LoggingInterceptor.class);


	/**
	 * Sends a game invite to the user
	 * @param GameInviteModel
	 * @return boolean
	 * 
	 */
	@Override
	public boolean sendInvite(GameInviteModel model) throws UserNotFoundException {
		if(model.getReceiverID() > -1)
		{
			UserModel user = userService.findByID(model.getReceiverID());
			if(user.getId() == -1)
				throw new UserNotFoundException();
		}
		else
		{
			//Get the id of the user receiving the invite based on the username provided
			model.setReceiverID(userService.findByUsername(model.getReceiverUsername()).getId());
			
			//Check that the user actually exists based on the id
			if(model.getReceiverID() == -1) {
				throw new UserNotFoundException();
			}
		}
			
		//Create the invite and return the result
		return dao.create(model);
	}
	
	/**
	 * Sends invites from a list 
	 * @param invites The list of GameInvites
	 * @param gameID The id of the game
	 * @throws GameNotFoundException
	 */
	@Override
	public boolean sendListOfInvites(List<GameInviteModel> invites, int gameID) throws GameNotFoundException {
		boolean status = true;
		
		//Ensure that the game exists for the ID
		if (!gameService.gameExists(gameID))
			throw new GameNotFoundException();
		
		//Send every invite
		for(GameInviteModel invite : invites)
		{
			try
			{
				sendInvite(invite);
			} catch (UserNotFoundException e) {
				//If the invite doesn't exist, log the exception and change the status to false
				logger.error("[ERROR] GameInviteService.sendListOfInvites - CANNOT SEND INVITE TO USER BECAUSE THE USERID IS NOT FOUND (ID: " + invite.getReceiverID() + ")");
				status = false;
			}
		}
		
		return status;
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
		
		//Get the list
		List<GameInviteModel> list = dao.findAllForID(id);
		
		//Set the usernames
		this.setNamesForInvites(list);
		
		//Return the list
		return list;
	}
	
	/**
	 * Sets the usernames and lobby name for a lsit of invites
	 * @param list
	 * @return List
	 */
	private List<GameInviteModel> setNamesForInvites(List<GameInviteModel> list) {
		for(GameInviteModel invite : list) {
			UserModel sender = userService.findByID(invite.getSenderID());
			UserModel receiver = userService.findByID(invite.getReceiverID());
			try {
				GameModel game = gameService.getGame(invite.getGameID());
				invite.setLobbyName(game.getLobbyName());
			} catch (GameNotFoundException e) {
				logger.error("[ERROR] A GAME COULD NOT BE FOUND FROM AN ID RETRIEVED FROM THE GAME INVITES TABLE \n" + e.getStackTrace());
			}
			
			invite.setSenderUsername(sender.getUsername());
			invite.setReceiverUsername(receiver.getUsername());
		}
		
		return list;
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

/*
Copyright 2019, Brendan Brooks.  

This file is part of PicScav.

PicScav is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

PicScav is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with PicScav.  If not, see <https://www.gnu.org/licenses/>.
*/
