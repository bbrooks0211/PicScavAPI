package brooks.api.RESTServices;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import brooks.api.business.interfaces.GameInvitesBusinessServiceInterface;
import brooks.api.models.GameInviteModel;
import brooks.api.models.RestResponse;
import brooks.api.utility.exceptions.UserNotFoundException;

@Path("/game")
@Service
public class GameInvitesRestService {
	private static GameInvitesBusinessServiceInterface service;
	
	@POST
	@Path("/sendGameInvite")
	@Produces("application/json")
	public RestResponse<Boolean> sendGameInvite(GameInviteModel model) {
		RestResponse<Boolean> response = new RestResponse<Boolean>(1, "OK", true);
		boolean status = false;
		try {
			status = service.sendInvite(model);
		} catch (UserNotFoundException e) {
			return new RestResponse<Boolean>(-1, "User was not found", Boolean.valueOf(false));
		}
		
		if(status) 
			response.setAll(1, "OK", Boolean.valueOf(true));
		
		return response;
	}
	
	@GET
	@Path("/getGameInvites/{id}")
	@Produces("application/json")
	public RestResponse<List<GameInviteModel>> getGameInvites(@PathParam("id") int id) {
		List<GameInviteModel> invites = new ArrayList<GameInviteModel>();
		try {
			invites = service.getInvitesForUser(id);
		} catch (UserNotFoundException e) {
			return new RestResponse<List<GameInviteModel>>(-1, "User could not be found with that id", new ArrayList<GameInviteModel>());
		}
		
		return new RestResponse<List<GameInviteModel>>(1, "OK", invites);
	}
	
	@Autowired
	public void setGameInvitesBusinessService(GameInvitesBusinessServiceInterface service) {
		this.service = service;
	}
}
