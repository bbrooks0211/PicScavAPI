package brooks.api.RESTServices;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import brooks.api.business.interfaces.GameBusinessServiceInterface;
import brooks.api.models.GameModel;
import brooks.api.models.RestResponse;

/**
 * Rest service for game data
 * @author Brendan Brooks
 *
 */
@Path("/game")
@Service
public class GameRestService {
	
	private static GameBusinessServiceInterface service;
	
	/**
	 * Create a new game
	 * @param game
	 * @return RestResponse<Boolean>
	 */
	@POST
	@Path("/create")
	@Produces("application/json")
	public RestResponse<Boolean> createNewGame(GameModel game) {
		RestResponse<Boolean> response = new RestResponse<Boolean>();
		boolean status = service.createNewGame(game);
		if(status) {
			response.setAll(1, "OK", Boolean.valueOf(status));
		} else {
			response.setAll(0, "Failed", Boolean.valueOf(status));
		}
		
		return response;
	}
	
	@Autowired
	@SuppressWarnings("static-access")
	public void setGameBusinessService(GameBusinessServiceInterface service) {
		this.service = service;
	}
}
