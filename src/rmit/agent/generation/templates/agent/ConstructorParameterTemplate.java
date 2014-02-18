package rmit.agent.generation.templates.agent;

public class ConstructorParameterTemplate {

	private final Class<?> parameterType;
	private final String varName;
	
	public ConstructorParameterTemplate(Class<?> parameterType, String varName) {
		this.parameterType = parameterType;
		this.varName = varName;
	}

	public Class<?> getParameterType() {
		return parameterType;
	}

	public String getVarName() {
		return varName;
	}
	
	
	
}
