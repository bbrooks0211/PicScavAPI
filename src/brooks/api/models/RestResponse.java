package brooks.api.models;

/**
 * Model for all rest responses. Takes in a generic for the data that's being sent back.
 * 
 * @author Brendan Brooks
 *
 * @param <T> - the object to be returned
 */
public class RestResponse<T> {
	int status;
	String message;
	T data;
	
	public RestResponse()
	{
		status = 0;
		message = "Failed.";
		data = null;
	}
	
	/**
	 * 
	 * @param status - int
	 * @param message - String
	 * @param data - T
	 */
	public RestResponse(int status, String message, T data) {
		this.status = status;
		this.message = message;
		this.data = data;
	}
	
	public void setAll(int status, String message, T data) {
		this.status = status;
		this.message = message;
		this.data = data;
	}
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
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