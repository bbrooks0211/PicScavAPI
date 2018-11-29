package brooks.api.RESTServices;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import brooks.api.business.UserBusinessService;
import brooks.api.business.interfaces.UserBusinessServiceInterface;
import brooks.api.models.LoginModel;
import brooks.api.models.RegistrationModel;
import brooks.api.models.RestResponse;
import brooks.api.models.UserModel;

/**
 * Rest service for handling all activity based around a user account
 * @author Brendan Brooks
 */
@Path("/user")
@Service
public class UserRestService {
	
	private static UserBusinessServiceInterface service;
	
	/**
	 * Rest service to register a user. Returns if it was successful or not.
	 * @param user - RegistrationModel
	 * @return RestResponse<Boolean>
	 */
	@POST
	@Path("/register")
	@Produces("application/json")
	public RestResponse<Boolean> registerNewUser(RegistrationModel user)
	{
		
		boolean status = false;
		RestResponse<Boolean> response;
		
		try {
			status = service.registerUser(user);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			response = new RestResponse<Boolean>(0, "Connection failed", false);
		}
		
		response = new RestResponse<Boolean>(1, "OK", Boolean.valueOf(status));
		
		return response;
	}
	
	/**
	 * Rest service to login a user and return the user's info
	 * @param user - LoginModel
	 * @return RestResponse<UserModel>
	 */
	@POST
	@Path("/login")
	@Produces("application/json")
	public RestResponse<UserModel> loginUser(LoginModel user)
	{
		UserModel model = new UserModel();
		
		try {
			System.out.println("Trying to login the user");
			model = service.loginUser(user);
		} catch(UnsupportedEncodingException e) {
			e.printStackTrace();
			return new RestResponse<UserModel>(0, "An error has occurred", model);
		}
		
		return new RestResponse<UserModel>(1, "OK", model);
	}
	
	/**
	 * Rest service to update a user's profile info (minus the password)
	 * @param user - UserModel
	 * @return RestResponse<Boolean>
	 */
	@POST
	@Path("/update")
	@Produces("application/json")
	public RestResponse<Boolean> loginUser(UserModel user)
	{
		boolean result = false;
		
		try {
			System.out.println("Trying to update the user");
			result = service.updateUser(user);
		} catch(Exception e) {
			e.printStackTrace();
			return new RestResponse<Boolean>(0, "An error has occurred", Boolean.valueOf(result));
		}
		
		return new RestResponse<Boolean>(1, "OK", Boolean.valueOf(result));
	}
	
	@GET
	@Path("/delete/{id}")
	@Produces("application/json")
	public RestResponse<Boolean> deleteUser(@PathParam("id") int id)
	{
		boolean result = false;
		
		try {
			result = service.deleteUser(id);
		} catch(Exception e) {
			e.printStackTrace();
			return new RestResponse<Boolean>(0, "An error has occurred", Boolean.valueOf(result));
		}
		
		return new RestResponse<Boolean>(1, "OK", Boolean.valueOf(result));
	}
	
	/**
	 * Rest service to check if a username already exists
	 * @param username - String
	 * @return RestResponse<Boolean>
	 */
	@GET
	@Path("/usernameExists/{username}")
	@Produces("application/json")
	public RestResponse<Boolean> usernameExists(@PathParam("username") String username) {
		RestResponse<Boolean> response;
		boolean status = Boolean.valueOf(false);
		
		try {
			status = service.usernameExists(username);
		} catch(SQLException e) { //Create a database exception and use that
			e.printStackTrace();
			response = new RestResponse<Boolean>(0, "Database exception occurred", Boolean.valueOf(status));
			return response;
		}
		
		response = new RestResponse<Boolean>(1, "OK", Boolean.valueOf(status));
		
		return response;
	}
	
	/**
	 * Rest service to check if an email already exists
	 * @param email - String
	 * @return RestResponse<Boolean>
	 */
	@GET
	@Path("/emailExists/{email}")
	@Produces("application/json")
	public RestResponse<Boolean> emailExists(@PathParam("email") String email) {
		RestResponse<Boolean> response;
		boolean status = Boolean.valueOf(false);
		
		try {
			status = service.emailExists(email);
		} catch(SQLException e) { //Create a database exception and use that
			e.printStackTrace();
			response = new RestResponse<Boolean>(0, "Database exception occurred", Boolean.valueOf(status));
			return response;
		}
		
		response = new RestResponse<Boolean>(1, "OK", Boolean.valueOf(status));
		
		return response;
	}
	
	//Dependency injection of the UserBusinessService
	@Autowired
	public void setUserBusinessService(UserBusinessServiceInterface service) {
		this.service = service;
		
		//DELETE THIS
		System.out.println("USER BUSINESS SERVICE AUTOWIRED");
	} 
}
