package brooks.api.business;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import brooks.api.business.interfaces.FriendsBusinessServiceInterface;
import brooks.api.business.interfaces.UserBusinessServiceInterface;
import brooks.api.data.interfaces.DataAccessInterface;
import brooks.api.models.FriendInviteModel;
import brooks.api.models.FriendModel;
import brooks.api.models.UserModel;
import brooks.api.utility.exceptions.AlreadyFriendsException;
import brooks.api.utility.exceptions.InviteAlreadyAcceptedException;
import brooks.api.utility.exceptions.InviteAlreadySentException;
import brooks.api.utility.exceptions.InviteNotFoundException;
import brooks.api.utility.exceptions.UserNotFoundException;


/**
 * Business service class for all friend functionalities
 * @author Brendan Brooks
 *
 */
public class FriendsBusinessService implements FriendsBusinessServiceInterface {
	
	private DataAccessInterface<FriendModel> friendDAO;
	private DataAccessInterface<FriendInviteModel> friendInviteDAO;
	private UserBusinessServiceInterface userService;
	
	
	/**
	 * Method for sending a friend invite to another user
	 * @param FriendInviteModel
	 * @throws AlreadyFriendsException
	 * @throws InviteAlreadySentException
	 * @return boolean
	 */
	@Override
	public boolean sendFriendInvite(FriendInviteModel invite) throws AlreadyFriendsException, InviteAlreadySentException, UserNotFoundException {
		if(!userService.usernameExists(invite.getReceiver()))
			throw new UserNotFoundException();
		
		//Find the users by their usernames to get more information
		UserModel sender = userService.findByUsername(invite.getSender());
		UserModel receiver = userService.findByUsername(invite.getReceiver());

		invite.setSenderID(sender.getId());
		invite.setReceiverID(receiver.getId());

		
		//Ensure that the two users aren't already friends
		if(alreadyFriends(invite))
			throw new AlreadyFriendsException();
		//Ensure that this user hasn't already sent an invite to the receiver
		if(inviteExists(invite))
			throw new InviteAlreadySentException();
		
		//Check if the receiver has already sent an invite to the sender of this invite
		if(inviteExists(new FriendInviteModel(-1, invite.getReceiver(), invite.getReceiverID(), invite.getSender(), invite.getSenderID(), -1)))
		{
			//Get the invite that was already sent by the receiver
			FriendInviteModel receiverInvite = getInvite(new FriendInviteModel(-1, invite.getReceiver(), invite.getReceiverID(), invite.getSender(), invite.getSenderID(), -1));
			
			
			try {
				//Accept the invite using the ID of the invite sent by the receiver
				return acceptInvite(receiverInvite.getId());
			}
			//InviteNotFoundException is swallowed because if an invite doesn't already exist between the two, then one will be created at the end of this method
			//InviteAlreadyAcceptedException is swallowed because this function already checks if the two users are friends
			catch (InviteNotFoundException | InviteAlreadyAcceptedException e) {} 
		}
		
		return friendInviteDAO.create(invite);
	}

	/**
	 * Adds a friend relationship to the database
	 * @param FriendModel
	 * @return Boolean
	 */
	@Override
	public boolean addFriendRelationship(FriendModel model) {
		//Attempt to add the friend relationship
		boolean status = friendDAO.create(model);
		
		//Return the status
		return status;
	}
	
	/**
	 * Get's the friends for a user based on their username
	 * @param String
	 * @return boolean
	 */
	@Override
	public List<FriendModel> getFriends(String username)
	{	
		UserModel user = userService.findByUsername(username);
		List<FriendModel> list = friendDAO.findAllForID(user.getId());
		list = setUsernamesForFriendList(list);
		return list;
	}
	
	/**
	 * Gets all the invites for a user based on their username
	 * @param String
	 * @return boolean
	 */
	@Override
	public List<FriendInviteModel> getInvitesForUsername(String username)
	{
		UserModel user = userService.findByUsername(username);
		List<FriendInviteModel> list = friendInviteDAO.findAllForID(user.getId());
		list = setUsernamesForInviteList(list);
		return list;
	}
	
	/**
	 * Accepts an invite based on the ID
	 * @param int
	 * @return boolean
	 */
	@Override
	public boolean acceptInvite(int inviteID) throws InviteNotFoundException, InviteAlreadyAcceptedException 
	{
		//Finds the invite based on the ID
		FriendInviteModel invite = friendInviteDAO.findByID(inviteID);
		
		//Cgeck if it's already been accepted
		if(invite.getAccepted() == 1)
			throw new InviteAlreadyAcceptedException();
		
		//Set up additional status variables to return if it was successful or not
		boolean acceptStatus = false, addRelationStatus = false;

		//If the invite does exist, accept it and add the friend relationship to the database
		if(invite.getId() != -1)
		{
			acceptStatus = friendInviteDAO.update(invite);
			addRelationStatus = addFriendRelationship(new FriendModel(-1, invite.getSenderID(), invite.getReceiverID()));
		} else {
			throw new InviteNotFoundException();
		}
		
		//Return if it was successful or not
		if (acceptStatus && addRelationStatus)
			return true;
		else
			return false;
	}
	
	/**
	 * Declines an invite based on the ID
	 * @param int
	 * @return boolean
	 */
	@Override
	public boolean declineInvite(int inviteID) throws InviteNotFoundException 
	{
		//set up status variable for returning if the operation was successful or not
		boolean status = false;
		
		//get the invite that's being declined
		FriendInviteModel invite = friendInviteDAO.findByID(inviteID);
		
		//If it doesn't exist, throw an exception
		if(invite.getId() == -1)
		{
			throw new InviteNotFoundException();	
		}
		
		//Delete the invite from the database
		status = friendInviteDAO.delete(inviteID);
		
		//Return if it was successful or not
		return status;
	}
	
	/**
	 * Checks if people are already friends or not
	 * @param FriendModel
	 * @return boolean
	 */
	@Override
	public boolean alreadyFriends(FriendModel model)
	{
		//Try and find a friend relationship between the two
		FriendModel friends = friendDAO.find(model);
		
		//If the object return has an id of -1, then the relationship could not be found
		if(friends.getId() == -1)
			return false;
		
		return true;
	}
	
	/**
	 * Checks if two users are already friends or not from an invite
	 * @param FriendInviteModel
	 * @return boolean
	 */
	@Override
	public boolean alreadyFriends(FriendInviteModel model)
	{	
		return alreadyFriends(new FriendModel(-1, model.getSenderID(), model.getReceiverID()));
	}
	
	/**
	 * Checks if an invite was already sent from one user to the other
	 * @param FriendInviteModel
	 * @return boolean
	 */
	@Override
	public boolean inviteExists(FriendInviteModel invite)
	{
		//Try and find an existing invite
		FriendInviteModel inv = friendInviteDAO.find(invite);
		
		//If the returned one's ID is equal to one, or if the one found was already accepted, return false
		if(inv.getId() == -1 || inv.getAccepted() == 1)
			return false;
		
		return true;
	}
	
	/**
	 * Get an invite based on an invite model
	 * @param FriendInviteModel
	 * @return FriendInviteModel
	 */
	@Override
	public FriendInviteModel getInvite(FriendInviteModel invite)
	{
		//Get the invite
		FriendInviteModel m = friendInviteDAO.find(invite);
		
		//Set the sender and receiver based on the IDs provided by the database
		m.setSender(userService.findByID(invite.getSenderID()).getUsername());
		m.setReceiver(userService.findByID(invite.getReceiverID()).getUsername());
		return m;
	}
	
	/**
	 * Remove a friend relation from the database
	 * @param int
	 * @return boolean
	 */
	@Override
	public boolean deleteFriendship(int id)
	{
		return friendDAO.delete(id);
	}
	
	/**
	 * Takes a list of invites and sets all the usernames for that list
	 * @param List<FriendInviteModel>
	 */
	@Override
	public List<FriendInviteModel> setUsernamesForInviteList(List<FriendInviteModel> list) 
	{
		//Loop through each invite and set the username
		for(FriendInviteModel m : list) {
			m = setUsernamesForInvite(m);
		}
		
		return list;
	}
	
	/**
	 * Sets the usernames for an invite, since invites when retrieved from the database only have ID's. 
	 * Mainly a helper function
	 * @param FriendInviteModel
	 * @return FriendInviteModel
	 */
	@Override
	public FriendInviteModel setUsernamesForInvite(FriendInviteModel invite) 
	{
		//Set the sender and receiver by retrieving the user info from the database
		invite.setReceiver(userService.findByID(invite.getReceiverID()).getUsername());
		invite.setSender(userService.findByID(invite.getSenderID()).getUsername());
		
		return invite;
	}
	
	/**
	 * Takes a list of FriendModel and sets all the usernames for that list
	 * @param List<FriendModel>
	 * @return List<FriendModel>
	 */
	@Override
	public List<FriendModel> setUsernamesForFriendList(List<FriendModel> list) 
	{
		//Loop through each one and set the username
		for(FriendModel m : list) {
			m = setUsernamesForFriendModel(m);
		}
		
		return list;
	}
	
	/**
	 * Finds and sets the usernames for a FriendModel
	 * @param friend
	 * @return FriendModel
	 */
	@Override
	public FriendModel setUsernamesForFriendModel(FriendModel friend) {
		//Set the usernames
		friend.setFriendUsername(userService.findByID(friend.getFriendID()).getUsername());
		friend.setUsername(userService.findByID(friend.getUserID()).getUsername());
		
		return friend;
	}
	
	@Autowired
	private void setFriendDAO(DataAccessInterface<FriendModel> dao) {
		this.friendDAO = dao;
	}
	
	@Autowired
	private void setFriendInviteDAO(DataAccessInterface<FriendInviteModel> dao) {
		this.friendInviteDAO = dao;
	}
	
	@Autowired
	public void setUserBusinessService(UserBusinessServiceInterface service) {
		this.userService = service;
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