package brooks.api.business.interfaces;

import java.util.List;

import brooks.api.models.GameInviteModel;
import brooks.api.utility.exceptions.GameNotFoundException;
import brooks.api.utility.exceptions.InviteAlreadyAcceptedException;
import brooks.api.utility.exceptions.InviteNotFoundException;
import brooks.api.utility.exceptions.UserNotFoundException;

public interface GameInvitesBusinessServiceInterface {
	public boolean sendInvite(GameInviteModel model) throws UserNotFoundException;
	public boolean acceptInvite(int id) throws InviteNotFoundException, InviteAlreadyAcceptedException, GameNotFoundException;
	public boolean declineInvite(int id) throws InviteNotFoundException, InviteAlreadyAcceptedException;
	public List<GameInviteModel> getInvitesForUser(int id) throws UserNotFoundException;
}
