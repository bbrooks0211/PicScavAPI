package brooks.api.business.interfaces;

import java.util.List;

import brooks.api.models.GameInviteModel;
import brooks.api.utility.exceptions.UserNotFoundException;

public interface GameInvitesBusinessServiceInterface {
	public boolean sendInvite(GameInviteModel model) throws UserNotFoundException;
	public boolean acceptInvite(int id);
	public boolean declineInvite(int id);
	public List<GameInviteModel> getInvitesForUser(int id);
}
