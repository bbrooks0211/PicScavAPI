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

import brooks.api.business.interfaces.GameBusinessServiceInterface;
import brooks.api.models.GameModel;
import brooks.api.models.RestResponse;
import brooks.api.utility.exceptions.FailureToCreateException;
import brooks.api.utility.exceptions.GameNotFoundException;
import brooks.api.utility.exceptions.GameTooLongException;
import brooks.api.utility.exceptions.NotEnoughItemsException;
import brooks.api.utility.exceptions.UserNotFoundException;

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
			return response;
		} catch (NotEnoughItemsException e) {
			response.setAll(-4, "Not enough items in category for desired number of game items", Boolean.valueOf(false));
			return response;
		}
		
		if(status) {
			response.setAll(1, "OK", Boolean.valueOf(true));
		} else {
			response.setAll(0, "Failed", Boolean.valueOf(false));
		}
		
		return response;
	}
	
	@GET
	@Path("/getGame/{id}")
	@Produces("application/json")
	public RestResponse<GameModel> getGame(@PathParam("id") int id)
	{		
			try {
				GameModel game = service.getGame(id);
				return new RestResponse<GameModel>(1, "OK", game);
			} catch (GameNotFoundException e) {
				return new RestResponse<GameModel>(-1, "Game could not be found with that id", new GameModel());
			}
	}
	
	@GET
	@Path("/getGames/{id}")
	@Produces("application/json")
	public RestResponse<List<GameModel>> getPlayerGames(@PathParam("id") int id)
	{		
		try {
			List<GameModel> list = service.getGames(id);
			return new RestResponse<List<GameModel>>(1, "OK", list);
		} catch (UserNotFoundException e) {
			return new RestResponse<List<GameModel>>(-1, "User could not be found with that id", new ArrayList<GameModel>());
		}
	}
	
	@GET
	@Path("/getCurrentGames/{id}")
	@Produces("application/json")
	public RestResponse<List<GameModel>> getCurrentGames(@PathParam("id") int id)
	{		
		try {
			List<GameModel> list = service.getCurrentGames(id);
			return new RestResponse<List<GameModel>>(1, "OK", list);
		} catch (UserNotFoundException e) {
			return new RestResponse<List<GameModel>>(-1, "User could not be found with that id", new ArrayList<GameModel>());
		}
	}
	
	@Autowired
	@SuppressWarnings("static-access")
	public void setGameBusinessService(GameBusinessServiceInterface service) {
		this.service = service;
	}
}

/*
Copyright 2019, Brendan Brooks.  

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