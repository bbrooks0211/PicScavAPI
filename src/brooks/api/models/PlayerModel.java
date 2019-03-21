package brooks.api.models;

/**
 * Model for participants of a specific game
 * @author Brendan Brooks
 *
 */
public class PlayerModel {
	
	private int id;
	private int userID;
	private int gameID;
	private String username;
	private int score;
	
	public PlayerModel(int id, int userID, String username, int score, int gameID) {
		super();
		this.id = id;
		this.userID = userID;
		this.gameID = gameID;
		this.username = username;
		this.score = score;
	}
	
	public PlayerModel(int id, int userID, int score, int gameID) {
		super();
		this.id = id;
		this.userID = userID;
		this.gameID = gameID;
		this.score = score;
		this.username = "";
	}
	
	public PlayerModel(int id, int userID, int gameID) {
		super();
		this.id = id;
		this.userID = userID;
		this.gameID = gameID;
	}
	
	public PlayerModel() {
		super();
		this.id = -1;
		this.userID = -1;
		this.gameID = -1;
		this.username = "";
		this.score = -1;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getGameID() {
		return gameID;
	}

	public void setGameID(int gameID) {
		this.gameID = gameID;
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