package brooks.api.RESTServices;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import brooks.api.business.interfaces.FriendsBusinessServiceInterface;
import brooks.api.models.FriendInviteModel;
import brooks.api.models.RestResponse;

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
	
	@Autowired
	private void setFriendsBusinessService(FriendsBusinessServiceInterface service) {
		this.service = service;
	}
	
}
