package brooks.api.models;

public class FriendInviteModel {
	private int id;
	private String sender;
	private String receiver;
	private int accepted;
	
	public FriendInviteModel() {
		super();
		this.id = -1;
		this.sender = "";
		this.receiver = "";
		this.accepted = -1;
	}
	
	public FriendInviteModel(int id, String sender, String receiver, int accepted) {
		super();
		this.id = id;
		this.sender = sender;
		this.receiver = receiver;
		this.accepted = accepted;
	}
	
	public FriendInviteModel(String sender, String receiver) {
		super();
		this.id = -1;
		this.sender = sender;
		this.receiver = receiver;
		this.accepted = -1;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public int getAccepted() {
		return accepted;
	}

	public void setAccepted(int accepted) {
		this.accepted = accepted;
	}
}
