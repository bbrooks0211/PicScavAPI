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
import brooks.api.models.FriendModel;
import brooks.api.models.RestResponse;
import brooks.api.utility.exceptions.AlreadyFriendsException;
import brooks.api.utility.exceptions.InviteAlreadyAcceptedException;
import brooks.api.utility.exceptions.InviteAlreadySentException;
import brooks.api.utility.exceptions.InviteNotFoundException;
import brooks.api.utility.exceptions.UserNotFoundException;

/**
 * REST service for all friend-based operations
 * @author Brendan Brooks
 *
 */
@Path("/friends")
@Service
public class FriendsRestService {

	private static FriendsBusinessServiceInterface service;
	
	/**
	 * Sends a friend invite <br>
	 * @param model
	 * @return RestResponse<Boolean>
	 */
	@POST
	@Path("/sendInvite")
	@Produces("application/json")
	public RestResponse<Boolean> sendFriendInvite(FriendInviteModel model)
	{
		RestResponse<Boolean> response = new RestResponse<Boolean>();
		boolean status = false;
		try {
			status = service.sendFriendInvite(model);
		} catch (AlreadyFriendsException e) {
			response = new RestResponse<Boolean>(-1, "The two users are already friends", Boolean.valueOf(false));
			return response;
		} catch (InviteAlreadySentException e) {
			response = new RestResponse<Boolean>(-2, "An invite has already been sent by this user", Boolean.valueOf(false));
			return response;
		} catch (UserNotFoundException e) {
			response = new RestResponse<Boolean>(-3, "The user could not be found", Boolean.valueOf(false));
			return response;
		}
		
		if(status)
			response = new RestResponse<Boolean>(1, "OK", Boolean.valueOf(status));
		else
			response = new RestResponse<Boolean>(0, "Failure occurred", Boolean.valueOf(status));
		
		return response;
	}
	
	/**
	 * Accepts a friend invite <br>
	 * @param model
	 * @return RestResponse<Boolean>
	 */
	@GET
	@Path("/acceptInvite/{id}")
	@Produces("application/json")
	public RestResponse<Boolean> acceptFriendInvite(@PathParam("id") int id)
	{
		RestResponse<Boolean> response = new RestResponse<Boolean>();
		boolean status = false;
		
		try {
			//Try to accept the invite
			status = service.acceptInvite(id);
		} catch (InviteNotFoundException e) {
			//Returns exception if the invite couldn't be found
			response = new RestResponse<Boolean>(-1, "Invite could not be found", Boolean.valueOf(status));
			return response;
		} catch (InviteAlreadyAcceptedException e) {
			//Returns an exception if the invite has somehow already been accepted
			response = new RestResponse<Boolean>(-2, "Invite has already been accepted", Boolean.valueOf(status));
			return response;
		}
		
		//If it was successful, return that it was
		if(status)
			response = new RestResponse<Boolean>(1, "OK", Boolean.valueOf(status));
		//In case any other failure could have happened
		else
			response = new RestResponse<Boolean>(0, "Failure occurred", Boolean.valueOf(status));
		
		//Return the response
		return response;
	}
	
	/**
	 * Declines a game invite
	 * @param id
	 * @return
	 */
	@GET
	@Path("/declineInvite/{id}")
	@Produces("application/json")
	public RestResponse<Boolean> declineFriendInvite(@PathParam("id") int id)
	{
		//Set up response variables
		RestResponse<Boolean> response = new RestResponse<Boolean>();
		boolean status = false;
		
		try {
			//Decline the invite, capture if it was successful or not in the status variable
			status = service.declineInvite(id);
		} catch (InviteNotFoundException e) {
			//If the invite could not be found, send that information back to the user
			response = new RestResponse<Boolean>(-1, "Invite could not be found", Boolean.valueOf(status));
			return response;
		}
		
		//Doing one last check to make sure everything went right 
		if(status)
			response = new RestResponse<Boolean>(1, "OK", Boolean.valueOf(status));
		else
			response = new RestResponse<Boolean>(0, "Failure occurred", Boolean.valueOf(status));
		
		//Return the response
		return response;
	}
	
	/**
	 * Get the invites for a user by their username
	 * @param username
	 * @return RestResponse<List<FriendInviteModel>>
	 */
	@GET
	@Path("/getInvites/{username}")
	@Produces("application/json")
	public RestResponse<List<FriendInviteModel>> getAllInvites(@PathParam("username") String username)
	{
		//Get the list of invites
		List<FriendInviteModel> friends = service.getInvitesForUsername(username);
		
		//Setup the rest response
		RestResponse<List<FriendInviteModel>> response = new RestResponse<List<FriendInviteModel>>(1, "OK", friends);
		
		//Return the response
		return response;
	}
	
	/**
	 * Get the friends for a user by their username
	 * @param username
	 * @return RestResponse<List<FriendModel>>
	 */
	@GET
	@Path("/getFriends/{username}")
	@Produces("application/json")
	public RestResponse<List<FriendModel>> getFriends(@PathParam("username") String username)
	{
		//Get the list of friends
		List<FriendModel> friends = service.getFriends(username);
		
		//Setup the rest response
		RestResponse<List<FriendModel>> response = new RestResponse<List<FriendModel>>(1, "OK", friends);
		
		//Return the response
		return response;
	}
	
	/**
	 * Removes a friend based on the ID of the relationship (Not the userID of the friend)
	 * @param id
	 * @return RestResponse<Boolean>
	 */
	@GET
	@Path("/removeFriend/{id}")
	@Produces("application/json")
	public RestResponse<Boolean> removeFriend(@PathParam("id") int id)
	{
		//Initialize the response
		RestResponse<Boolean> response = new RestResponse<Boolean>();
		
		//Delete the friendship, capture if it was successful or not
		boolean status = service.deleteFriendship(id);
		
		//Set the response based on if it was successful or not
		if(status)
			response = new RestResponse<Boolean>(1, "OK", Boolean.valueOf(status));
		else
			response = new RestResponse<Boolean>(0, "Unable to remove the friend relationship", Boolean.valueOf(status));
		
		//Return the response
		return response;
	}
	
	@Autowired 
	private void setFriendsBusinessService(FriendsBusinessServiceInterface service) {
		this.service = service;
	}
	
}

/*
This file is part of PicScav.

PicScav is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

PicScav is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with PicScav.  If not, see <https://www.gnu.org/licenses/>.
*/