package rmit.agent.generation.templates.beliefset;

import java.io.Serializable;

public enum BeliefSetWorldType implements Serializable {
	
	OPEN_WORLD 		("OpenWorld"), 
	CLOSED_WORLD 	("ClosedWorld");
	
	private final String jackString;
	
	private BeliefSetWorldType(String jackString) {
		this.jackString = jackString;
	}
	
	@Override
	public String toString() {
		return jackString;
	}
}
