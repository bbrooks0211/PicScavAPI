package brooks.api.business.interfaces;

import brooks.api.models.FriendInviteModel;
import brooks.api.models.FriendModel;

public interface FriendsBusinessServiceInterface {
	public boolean sendFriendInvite(FriendInviteModel invite);
	public boolean addFriendRelationship(FriendModel model);
}
