package rmit.agent.generation.templates.codeline;

import java.io.Serializable;

import rmit.agent.generation.templates.ClassName;
import rmit.agent.generation.templates.ImportSet;

public class CodeLineTemplate implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private final ImportSet imports;	
	private final String codeLine;
	
	public CodeLineTemplate(String codeLine, ClassName ... imports) {
		this.imports = new ImportSet(imports);
		this.codeLine = codeLine;
	}
	
	public CodeLineTemplate(String codeLine, Iterable<ClassName> imports) {
		this.imports = new ImportSet(imports);
		this.codeLine = codeLine;
	}

	public ImportSet getImports() {
		return imports;
	}
	
	public String getCodeLine() {
		return codeLine;
	}
	
}
