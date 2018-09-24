package ar.edu.itba.multiagent.pacman.communication;

import ar.edu.itba.multiagent.pacman.agents.Ghost;
import com.badlogic.gdx.math.Vector2;

public class Message {
	private MessageType type;
	private int sender;
	private Object data;

	public Message(MessageType type, int sender, Object data){
		this.type = type;
		this.data = data;
		this.sender = sender;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public MessageType getType() {
		return type;
	}

	public void setType(MessageType type) {
		this.type = type;
	}

	public GhostPosition getDataAsPosition() {
		if(data instanceof GhostPosition){
			return (GhostPosition) data;
		}
		throw new IllegalStateException("data is not a position");
	}

	public int getSender() {
		return sender;
	}
}
