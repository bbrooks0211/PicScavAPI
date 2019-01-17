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
		System.out.println("Sender: " + invite.getSenderID());
		
		//Ensure that the two users aren't already friends
		if(alreadyFriends(invite))
			throw new AlreadyFriendsException();
		//Ensure that this user hasn't already sent an invite to the receiver
		if(inviteExists(invite))
			throw new InviteAlreadySentException();
		
		//Check if the receiver has already sent an invite to the sender of this invite
		if(inviteExists(new FriendInviteModel(invite.getReceiver(), invite.getSender())))
		{
			//Get the invite that was already sent by the receiver
			FriendInviteModel receiverInvite = getInvite(new FriendInviteModel(invite.getReceiver(), invite.getSender()));
			
			
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
		boolean status = friendDAO.create(model);
		
		return status;
	}
	
	@Override
	public List<FriendModel> getFriends(String username)
	{	
		return friendDAO.findAllByString(username);
	}
	
	@Override
	public List<FriendInviteModel> getInvitesForUsername(String username)
	{
		UserModel user = userService.findByUsername(username);
		List<FriendInviteModel> list = friendInviteDAO.findAllForID(user.getId());
		list = setUsernamesForInviteList(list);
		return list;
	}
	
	@Override
	public boolean acceptInvite(int inviteID) throws InviteNotFoundException, InviteAlreadyAcceptedException 
	{
		FriendInviteModel invite = friendInviteDAO.findByID(inviteID);
		
		if(invite.getAccepted() == 1)
			throw new InviteAlreadyAcceptedException();
		
		boolean acceptStatus = false, addRelationStatus = false;

		if(invite.getId() != -1)
		{
			acceptStatus = friendInviteDAO.update(invite);
			addRelationStatus = addFriendRelationship(new FriendModel(-1, invite.getSenderID(), invite.getReceiverID()));
		} else {
			throw new InviteNotFoundException();
		}
		
		if (acceptStatus && addRelationStatus)
			return true;
		else
			return false;
	}
	
	@Override
	public boolean declineInvite(int inviteID) throws InviteNotFoundException 
	{
		boolean status = false;
		FriendInviteModel invite = friendInviteDAO.findByID(inviteID);
		
		if(invite.getId() == -1)
		{
			throw new InviteNotFoundException();	
		}
		
		status = friendInviteDAO.delete(inviteID);
		
		return status;
	}
	
	@Override
	public boolean alreadyFriends(FriendModel model)
	{
		
		FriendModel friends = friendDAO.find(model);
		if(friends.getId() == -1)
			return false;
		
		return true;
	}
	
	@Override
	public boolean alreadyFriends(FriendInviteModel model)
	{	
		return alreadyFriends(new FriendModel(-1, model.getSenderID(), model.getReceiverID()));
	}
	
	@Override
	public boolean inviteExists(FriendInviteModel invite)
	{
		FriendInviteModel inv = friendInviteDAO.find(invite);
		
		if(inv.getId() == -1)
			return false;
		
		return true;
	}
	
	@Override
	public FriendInviteModel getInvite(FriendInviteModel invite)
	{
		FriendInviteModel m = friendInviteDAO.find(invite);
		m.setSender(userService.findByID(invite.getSenderID()).getUsername());
		m.setReceiver(userService.findByID(invite.getReceiverID()).getUsername());
		return m;
	}
	
	@Override
	public boolean deleteFriendship(int id)
	{
		return friendDAO.delete(id);
	}
	
	@Override
	public List<FriendInviteModel> setUsernamesForInviteList(List<FriendInviteModel> list) 
	{
		for(FriendInviteModel m : list) {
			m = setUsernamesForInvite(m);
		}
		
		return list;
	}
	
	@Override
	public FriendInviteModel setUsernamesForInvite(FriendInviteModel invite) 
	{
		invite.setReceiver(userService.findByID(invite.getReceiverID()).getUsername());
		invite.setSender(userService.findByID(invite.getSenderID()).getUsername());
		
		return invite;
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
