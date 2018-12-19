package brooks.api.business;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import brooks.api.business.interfaces.FriendsBusinessServiceInterface;
import brooks.api.data.interfaces.DataAccessInterface;
import brooks.api.models.FriendInviteModel;
import brooks.api.models.FriendModel;
import brooks.api.utility.exceptions.InviteAlreadyAcceptedException;
import brooks.api.utility.exceptions.InviteNotFoundException;

/**
 * Business service class for all friend functionalities
 * @author Brendan Brooks
 *
 */
public class FriendsBusinessService implements FriendsBusinessServiceInterface {
	
	private DataAccessInterface<FriendModel> friendDAO;
	private DataAccessInterface<FriendInviteModel> friendInviteDAO;
	
	//Needs an Invite Already Exists exception to be implemented for if a repeat invite is sent
	@Override
	public boolean sendFriendInvite(FriendInviteModel invite) {
		boolean status = friendInviteDAO.create(invite);
		
		return status;
	}

	@Override
	public boolean addFriendRelationship(FriendModel model) {
		boolean status = friendDAO.create(model);
		
		return status;
	}
	
	@Override
	public List<FriendInviteModel> getInvitesForUsername(String username)
	{
		List<FriendInviteModel> list = friendInviteDAO.findAllByString(username);
		
		return list;
	}
	
	@Override
	public boolean acceptInvite(int inviteID) throws InviteNotFoundException, InviteAlreadyAcceptedException {
		FriendInviteModel invite = friendInviteDAO.findByID(inviteID);
		
		if(invite.getAccepted() == 1)
			throw new InviteAlreadyAcceptedException();
		
		boolean acceptStatus = false, addRelationStatus = false;

		if(invite.getId() != -1)
		{
			acceptStatus = friendInviteDAO.update(invite);
			addRelationStatus = addFriendRelationship(new FriendModel(-1, invite.getSender(), invite.getReceiver()));
		} else {
			throw new InviteNotFoundException();
		}
		
		if (acceptStatus && addRelationStatus)
			return true;
		else
			return false;
	}
	
	@Override
	public boolean declineInvite(int inviteID) {
		boolean status = false;
		
		status = friendInviteDAO.delete(inviteID);
		
		return status;
	}
	
	@Autowired
	private void setFriendDAO(DataAccessInterface<FriendModel> dao) {
		this.friendDAO = dao;
	}
	
	@Autowired
	private void setFriendInviteDAO(DataAccessInterface<FriendInviteModel> dao) {
		this.friendInviteDAO = dao;
	}
}
