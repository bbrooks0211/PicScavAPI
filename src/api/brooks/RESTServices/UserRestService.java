package api.brooks.RESTServices;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import api.brooks.business.UserBusinessService;
import api.brooks.models.LoginModel;
import api.brooks.models.RegistrationModel;
import api.brooks.models.RestResponse;
import api.brooks.models.UserModel;

@Path("/user")
public class UserRestService {
	
	UserBusinessService service = new UserBusinessService();
	
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
	
	@POST
	@Path("/login")
	@Produces("application/json")
	public RestResponse<UserModel> loginUser(LoginModel user)
	{
		UserModel model = new UserModel();
		
		try {
			model = service.loginUser(user);
		} catch(UnsupportedEncodingException e) {
			e.printStackTrace();
			return new RestResponse<UserModel>(0, "An error has occurred", model);
		}
		
		return new RestResponse<UserModel>(1, "OK", model);
	}
	
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
	
	@GET
	@Path("/emailExists/{email}")
	@Produces("application/json")
	public RestResponse<Boolean> emailExists(@PathParam("email") String username) {
		RestResponse<Boolean> response;
		boolean status = Boolean.valueOf(false);
		
		try {
			status = service.emailExists(username);
		} catch(SQLException e) { //Create a database exception and use that
			e.printStackTrace();
			response = new RestResponse<Boolean>(0, "Database exception occurred", Boolean.valueOf(status));
			return response;
		}
		
		response = new RestResponse<Boolean>(1, "OK", Boolean.valueOf(status));
		
		return response;
	}
}
