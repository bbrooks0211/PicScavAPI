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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import brooks.api.business.UserBusinessService;
import brooks.api.business.interfaces.UserBusinessServiceInterface;
import brooks.api.models.LoginModel;
import brooks.api.models.RegistrationModel;
import brooks.api.models.RestResponse;
import brooks.api.models.UserModel;

//Undecided whether keeping this class or not
@RestController()
@RequestMapping("/rest1/service1")
public class UserRestService2 {
	
	private UserBusinessServiceInterface service;
	
	@PostMapping("/login")
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
	
	@Autowired
	public void setUserBusinessService(UserBusinessServiceInterface service) {
		this.service = service;
		System.out.println("USER BUSINESS SERVICE AUTOWIRED");
	} 
}
