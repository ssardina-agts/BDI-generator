package rmit.agent.generation.templates.beliefset.field;

import java.io.Serializable;

public enum FieldType implements Serializable {

	KEY 	("key"), 
	VALUE 	("value");
	
	private final String jackString;
	
	private FieldType(String jackString) {
		this.jackString = jackString;
	}
	
	@Override
	public String toString() {
		return jackString;
	}
}
