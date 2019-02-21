package brooks.api.RESTServices;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import brooks.api.business.interfaces.GameBusinessServiceInterface;
import brooks.api.models.GameModel;
import brooks.api.models.RestResponse;
import brooks.api.utility.exceptions.FailureToCreateException;
import brooks.api.utility.exceptions.GameNotFoundException;
import brooks.api.utility.exceptions.GameTooLongException;
import brooks.api.utility.exceptions.NotEnoughItemsException;

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
		boolean status = false;
		try {
			status = service.createNewGame(game);
		} catch (GameTooLongException e) {
			response.setAll(-1, "Game length is too long", Boolean.valueOf(false));
			return response;
		} catch (GameNotFoundException e) {
			response.setAll(-2, "Game could not be found with that id", Boolean.valueOf(false));
			return response;
		} catch (FailureToCreateException e) {
			response.setAll(-3, "Game failed to create", Boolean.valueOf(false));
		} catch (NotEnoughItemsException e) {
			response.setAll(-4, "Not enough items in category for desired number of game items", Boolean.valueOf(false));
		}
		if(status) {
			response.setAll(1, "OK", Boolean.valueOf(true));
		} else {
			response.setAll(0, "Failed", Boolean.valueOf(false));
		}
		
		return response;
	}
	
	@Autowired
	@SuppressWarnings("static-access")
	public void setGameBusinessService(GameBusinessServiceInterface service) {
		this.service = service;
	}
}
