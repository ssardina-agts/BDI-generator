package rmit.agent.generation.templates.plan;

import java.io.Serializable;

public enum PostingMethod implements Serializable {

	POST 		("post"),
	SUBTASK		("subtask"),
	SEND		("send");
	
	private final String jackString;
	
	private PostingMethod(String jackString) {
		this.jackString = jackString;
	}
	
	@Override
	public String toString() {
		return jackString;
	}
	
}
