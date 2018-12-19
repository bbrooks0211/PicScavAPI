package brooks.api.business.interfaces;

import java.util.List;

import brooks.api.models.FriendInviteModel;
import brooks.api.models.FriendModel;
import brooks.api.utility.exceptions.InviteAlreadyAcceptedException;
import brooks.api.utility.exceptions.InviteNotFoundException;

public interface FriendsBusinessServiceInterface {
	public boolean sendFriendInvite(FriendInviteModel invite);
	public boolean addFriendRelationship(FriendModel model);
	public List<FriendInviteModel> getInvitesForUsername(String username);
	public boolean acceptInvite(int inviteID) throws InviteNotFoundException, InviteAlreadyAcceptedException;
}
