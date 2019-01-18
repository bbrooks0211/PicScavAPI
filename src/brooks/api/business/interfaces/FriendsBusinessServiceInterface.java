package brooks.api.business.interfaces;

import java.util.List;

import brooks.api.models.FriendInviteModel;
import brooks.api.models.FriendModel;
import brooks.api.utility.exceptions.AlreadyFriendsException;
import brooks.api.utility.exceptions.InviteAlreadyAcceptedException;
import brooks.api.utility.exceptions.InviteAlreadySentException;
import brooks.api.utility.exceptions.InviteNotFoundException;
import brooks.api.utility.exceptions.UserNotFoundException;

public interface FriendsBusinessServiceInterface {
	public boolean sendFriendInvite(FriendInviteModel invite) throws AlreadyFriendsException, InviteAlreadySentException, UserNotFoundException;
	public boolean addFriendRelationship(FriendModel model);
	public List<FriendInviteModel> getInvitesForUsername(String username);
	public List<FriendModel> getFriends(String username);
	public FriendInviteModel getInvite(FriendInviteModel invite);
	public boolean acceptInvite(int inviteID) throws InviteNotFoundException, InviteAlreadyAcceptedException;
	public boolean declineInvite(int inviteID) throws InviteNotFoundException;
	public boolean alreadyFriends(FriendModel model);
	public boolean alreadyFriends(FriendInviteModel model);
	public boolean inviteExists(FriendInviteModel invite);
	public boolean deleteFriendship(int id);
	List<FriendInviteModel> setUsernamesForInviteList(List<FriendInviteModel> list);
	FriendInviteModel setUsernamesForInvite(FriendInviteModel invite);
	FriendModel setUsernamesForFriendModel(FriendModel friend);
	List<FriendModel> setUsernamesForFriendList(List<FriendModel> list);
}
