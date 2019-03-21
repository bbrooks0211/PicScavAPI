package brooks.api.models;

import java.util.ArrayList;
import java.util.List;

/**
 * User Model that contains all information for the user except their password
 * @author Brendan Brooks
 *
 */
public class UserModel {
	private int id;
	private String username;
	private String email;
	private List<FriendModel> friends;
	private int purchasedAdRemoval;
	
	public UserModel() {
		this.id = -1;
		this.username = "";
		this.email = "";
		this.friends = new ArrayList<FriendModel>();
		this.purchasedAdRemoval = -1;
	}
	
	public UserModel(int id, String username, String email, List<FriendModel> friends, int purchasedAdRemoval) {
		super();
		this.id = id;
		this.username = username;
		this.email = email;
		this.friends = friends;
		this.purchasedAdRemoval = purchasedAdRemoval;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public List<FriendModel> getFriends() {
		return friends;
	}
	public void setFriends(List<FriendModel> friends) {
		this.friends = friends;
	}
	public int getPurchasedAdRemoval() {
		return purchasedAdRemoval;
	}
	public void setPurchasedAdRemoval(int purchasedAdRemoval) {
		this.purchasedAdRemoval = purchasedAdRemoval;
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