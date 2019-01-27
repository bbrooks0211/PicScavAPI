package brooks.api.models;

/**
 * Model for friend invites
 * @author Brendan Brooks
 *
 */
public class FriendInviteModel {
	private int id;
	private String sender;
	private int senderID;
	private String receiver;
	private int receiverID;
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
	
	public FriendInviteModel(int senderID, int receiverID) {
		super();
		this.senderID = senderID;
		this.receiverID = receiverID;
	}
	
	public FriendInviteModel(int id, int senderID, int receiverID, int accepted) {
		super();
		this.id = id;
		this.senderID = senderID;
		this.receiverID = receiverID;
		this.accepted = accepted;
	}
	
	public FriendInviteModel(int id, String sender, int senderID, String receiver, int receiverID , int accepted) {
		super();
		this.id = id;
		this.sender = sender;
		this.receiver = receiver;
		this.accepted = accepted;
		this.senderID = senderID;
		this.receiverID = receiverID;
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
	public int getSenderID() {
		return senderID;
	}
	public void setSenderID(int senderID) {
		this.senderID = senderID;
	}
	public int getReceiverID() {
		return receiverID;
	}
	public void setReceiverID(int receiverID) {
		this.receiverID = receiverID;
	}
	public int getAccepted() {
		return accepted;
	}
	public void setAccepted(int accepted) {
		this.accepted = accepted;
	}
}
