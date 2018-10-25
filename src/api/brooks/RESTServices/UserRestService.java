package api.brooks.RESTServices;

import java.io.UnsupportedEncodingException;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
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
}
