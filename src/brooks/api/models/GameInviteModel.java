package brooks.api.models;

/**
 * Model for game invites
 * @author Brendan Brooks
 *
 */
public class GameInviteModel {
	
	private int id;
	private int senderID;
	private String senderUsername;
	private int gameID;
	private String lobbyName;
	private long timeRemaining;
	private int receiverID;
	private String receiverUsername;
	private int accepted;
	
	public GameInviteModel(int id, int senderID, String senderUsername, int gameID, String lobbyName,
			long timeRemaining, int receiverID, String receiverUsername, int accepted) {
		super();
		this.id = id;
		this.senderID = senderID;
		this.senderUsername = senderUsername;
		this.gameID = gameID;
		this.lobbyName = lobbyName;
		this.timeRemaining = timeRemaining;
		this.receiverID = receiverID;
		this.receiverUsername = receiverUsername;
		this.accepted = accepted;
	}
	
	public GameInviteModel(int id, int senderID, int gameID, int receiverID, int accepted) {
		super();
		this.id = id;
		this.senderID = senderID;
		this.gameID = gameID;
		this.receiverID = receiverID;
		this.receiverUsername = "";
		this.senderUsername = "";
		this.accepted = accepted;
	}
	
	public GameInviteModel() {
		super();
		this.id = -1;
		this.senderID = -1;
		this.senderUsername = "";
		this.gameID = -1;
		this.lobbyName = "";
		this.timeRemaining = -1;
		this.receiverID = -1;
		this.receiverUsername = "";
		this.accepted = -1;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSenderID() {
		return senderID;
	}

	public void setSenderID(int senderID) {
		this.senderID = senderID;
	}

	public String getSenderUsername() {
		return senderUsername;
	}

	public void setSenderUsername(String senderUsername) {
		this.senderUsername = senderUsername;
	}

	public int getGameID() {
		return gameID;
	}

	public void setGameID(int gameID) {
		this.gameID = gameID;
	}

	public String getLobbyName() {
		return lobbyName;
	}

	public void setLobbyName(String lobbyName) {
		this.lobbyName = lobbyName;
	}

	public long getTimeRemaining() {
		return timeRemaining;
	}

	public void setTimeRemaining(long timeRemaining) {
		this.timeRemaining = timeRemaining;
	}

	public int getReceiverID() {
		return receiverID;
	}

	public void setReceiverID(int receiverID) {
		this.receiverID = receiverID;
	}

	public String getReceiverUsername() {
		return receiverUsername;
	}

	public void setReceiverUsername(String receiverUsername) {
		this.receiverUsername = receiverUsername;
	}

	public int getAccepted() {
		return accepted;
	}

	public void setAccepted(int accepted) {
		this.accepted = accepted;
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