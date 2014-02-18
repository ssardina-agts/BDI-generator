package rmit.agent.generation.templates.goal;

import java.io.Serializable;

public enum PostingType implements Serializable {

	POSTED_AS 		("posted as"),
	POSTED_WHEN		("posted when");
	
	private final String jackString;
	
	private PostingType(String jackString) {
		this.jackString = jackString;
	}
	
	@Override
	public String toString() {
		return jackString;
	}
	
}
