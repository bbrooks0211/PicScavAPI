package api.brooks.models;

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
