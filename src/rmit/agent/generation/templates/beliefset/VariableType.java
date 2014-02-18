package rmit.agent.generation.templates.beliefset;

import java.io.Serializable;

public enum VariableType implements Serializable {

	LOGICAL 	("logical"), 
	GROUND		("");
	
	private final String jackString;
	
	private VariableType(String jackString) {
		this.jackString = jackString;
	}
	
	@Override
	public String toString() {
		return jackString;
	}
}
