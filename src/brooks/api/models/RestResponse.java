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
