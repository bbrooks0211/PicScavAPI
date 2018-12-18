package brooks.api.business;

import org.springframework.beans.factory.annotation.Autowired;

import brooks.api.business.interfaces.FriendsBusinessServiceInterface;
import brooks.api.data.interfaces.DataAccessInterface;
import brooks.api.models.FriendInviteModel;
import brooks.api.models.FriendModel;

public class FriendsBusinessService implements FriendsBusinessServiceInterface {
	
	private DataAccessInterface<FriendModel> friendDAO;
	private DataAccessInterface<FriendInviteModel> friendInviteDAO;
	
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
	
	@Autowired
	private void setFriendDAO(DataAccessInterface<FriendModel> dao) {
		this.friendDAO = dao;
	}
	
	@Autowired
	private void setFriendInviteDAO(DataAccessInterface<FriendInviteModel> dao) {
		this.friendInviteDAO = dao;
	}
}
