package rmit.agent.generation.templates.agent;

import aos.jack.jak.agent.Agent;

public class AgentSuperClassTemplate {

	public static final AgentSuperClassTemplate AGENT_SUPERCLASS = new AgentSuperClassTemplate(Agent.class, new ConstructorParameterTemplate(String.class, "name"));
	
	private final Class<?> superClass;
	private final ConstructorParameterTemplate[] constructorParams;
	
	public AgentSuperClassTemplate(Class<?> superClass,	ConstructorParameterTemplate ... constructorParams) {
		this.superClass = superClass;
		this.constructorParams = constructorParams;
	}

	public Class<?> getClassName() {
		return superClass;
	}

	public ConstructorParameterTemplate[] getConstructorParams() {
		return constructorParams;
	}
	
	
}
