package rmit.agent.generation.templates.agent;

import aos.jack.jak.agent.Agent;
import rmit.coverage.agents.CoverageAgent;
import rmit.coverage.calculator.CoverageCalculator;

public class AgentSuperClassTemplate {

	public static final AgentSuperClassTemplate AGENT_SUPERCLASS 			= new AgentSuperClassTemplate(Agent.class, new ConstructorParameterTemplate(String.class, "name"));
	
	public static final AgentSuperClassTemplate COVERAGE_AGENT_SUPERCLASS 	= new AgentSuperClassTemplate(CoverageAgent.class, 
																				new ConstructorParameterTemplate(String.class, "name"),
																				new ConstructorParameterTemplate(Integer.class, "preemptionFlags"),
																				new ConstructorParameterTemplate(CoverageCalculator.class, "coverageCalculator"),
																				new ConstructorParameterTemplate(Class.class, "universalMetaLevelPlan"));
	
//	public static final AgentSuperClassTemplate FIFO_AGENT_SUPERCLASS 		= new AgentSuperClassTemplate(FifoAgent.class, 
//																				new ConstructorParameterTemplate(String.class, "name"));
//	
//	public static final AgentSuperClassTemplate RR_AGENT_SUPERCLASS 		= new AgentSuperClassTemplate(RoundRobinAgent.class, 
//																				new ConstructorParameterTemplate(String.class, "name"));
	
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
