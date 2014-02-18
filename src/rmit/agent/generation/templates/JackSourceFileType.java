package rmit.agent.generation.templates;

import java.io.Serializable;

public enum JackSourceFileType implements Serializable {

	EVENT			("event", 		"event"),
	PLAN			("plan", 		"plan"),
	BELIEF_SET 		("beliefset", 	"bel"),
	CAPABILITY		("capability", 	"cap"),
	AGENT			("agent", 		"agent");
	
	private final String declaration;
	private final String fileExtension;
	
	
	private JackSourceFileType(String declaration, String fileExtension) {
		this.declaration = declaration;
		this.fileExtension = fileExtension;
	}


	public String getDeclaration() {
		return declaration;
	}


	public String getFileExtension() {
		return fileExtension;
	}
	
	
	
}
