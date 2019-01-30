package brooks.api.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import brooks.api.business.interfaces.GameInvitesBusinessServiceInterface;
import brooks.api.business.interfaces.UserBusinessServiceInterface;
import brooks.api.data.interfaces.DataAccessInterface;
import brooks.api.models.GameInviteModel;
import brooks.api.utility.exceptions.UserNotFoundException;

public class GameInvitesBusinessService implements GameInvitesBusinessServiceInterface {
	
	private DataAccessInterface<GameInviteModel> dao;
	private UserBusinessServiceInterface userService;

	@Override
	public boolean sendInvite(GameInviteModel model) throws UserNotFoundException {
		model.setReceiverID(userService.findByUsername(model.getReceiverUsername()).getId());
		if(model.getReceiverID() == -1) {
			throw new UserNotFoundException();
		}
		
		return dao.create(model);
	}

	@Override
	public boolean acceptInvite(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean declineInvite(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<GameInviteModel> getInvitesForUser(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Autowired
	public void setGameDAO(DataAccessInterface<GameInviteModel> dao) {
		this.dao = dao;
	}
	
	@Autowired
	public void setUserService(UserBusinessServiceInterface service) {
		this.userService = service;
	}
}
