package rmit.agent.generation.templates.plan;

import java.io.Serializable;

import rmit.agent.generation.templates.beliefset.ParameterTemplate;

public class QueryArg implements Serializable {

	private static final long serialVersionUID = 1L;

	private final ParameterTemplate<?> parameter;
	private final Object argValue;
	
	public QueryArg(ParameterTemplate<?> parameter) {
		this(parameter, null);
	}
	
	public <T> QueryArg(ParameterTemplate<T> parameter, T argValue) {
		this.parameter = parameter;
		this.argValue = argValue;
	}

	public ParameterTemplate<?> getParameter() {
		return parameter;
	}

	public Object getArgValue() {
		return argValue;
	}
	
}
