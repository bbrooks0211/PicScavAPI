package brooks.api.RESTServices;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import brooks.api.business.interfaces.FriendsBusinessServiceInterface;
import brooks.api.models.FriendInviteModel;
import brooks.api.models.RestResponse;
import brooks.api.utility.exceptions.InviteAlreadyAcceptedException;
import brooks.api.utility.exceptions.InviteNotFoundException;

@Path("/friends")
@Service
public class FriendsRestService {

	private static FriendsBusinessServiceInterface service;
	
	@POST
	@Path("/sendInvite")
	@Produces("application/json")
	public RestResponse<Boolean> sendFriendInvite(FriendInviteModel model)
	{
		RestResponse<Boolean> response = new RestResponse<Boolean>();
		boolean status = service.sendFriendInvite(model);
		
		if(status)
			response = new RestResponse<Boolean>(1, "OK", Boolean.valueOf(status));
		else
			response = new RestResponse<Boolean>(0, "Failure occurred", Boolean.valueOf(status));
		
		return response;
	}
	
	@GET
	@Path("/acceptInvite/{id}")
	@Produces("application/json")
	public RestResponse<Boolean> acceptFriendInvite(@PathParam("id") int id)
	{
		RestResponse<Boolean> response = new RestResponse<Boolean>();
		boolean status = false;
		
		try {
			status = service.acceptInvite(id);
		} catch (InviteNotFoundException e) {
			response = new RestResponse<Boolean>(-1, "Invite could not be found", Boolean.valueOf(status));
			return response;
		} catch (InviteAlreadyAcceptedException e) {
			response = new RestResponse<Boolean>(-2, "Invite has already been accepted", Boolean.valueOf(status));
			return response;
		}
		
		if(status)
			response = new RestResponse<Boolean>(1, "OK", Boolean.valueOf(status));
		else
			response = new RestResponse<Boolean>(0, "Failure occurred", Boolean.valueOf(status));
		
		return response;
	}
	
	@GET
	@Path("/getInvites/{username}")
	@Produces("application/json")
	public RestResponse<List<FriendInviteModel>> getAllInvites(@PathParam("username") String username)
	{
		List<FriendInviteModel> friends = service.getInvitesForUsername(username);
		
		RestResponse<List<FriendInviteModel>> response = new RestResponse<List<FriendInviteModel>>(1, "OK", friends);
		
		return response;
	}
	
	@Autowired
	private void setFriendsBusinessService(FriendsBusinessServiceInterface service) {
		this.service = service;
	}
	
}
