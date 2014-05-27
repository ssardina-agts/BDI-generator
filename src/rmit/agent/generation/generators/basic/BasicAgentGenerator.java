package rmit.agent.generation.generators.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import rmit.agent.generation.generators.AgentGenerator;
import rmit.agent.generation.generators.GPFGenerator;
import rmit.agent.generation.generators.GeneratorProperties;
import rmit.agent.generation.templates.ClassName;
import rmit.agent.generation.templates.agent.AgentSuperClassTemplate;
import rmit.agent.generation.templates.agent.AgentTemplate;
import rmit.agent.generation.templates.intention.IntentionSet;

public class BasicAgentGenerator implements AgentGenerator {

	public static final String KEY_AGENT_CLASS_NAME	= "agent.class.name";
	
	protected String packageName; 

	protected String agentTypeName;
	
	protected GPFGenerator gpfGenerator;
	
	public BasicAgentGenerator(Properties properties) {
		packageName = properties.getProperty(GeneratorProperties.KEY_PACKAGE_NAME);
		load(properties);
	}

	protected void load(Properties properties) {
		agentTypeName = properties.getProperty(BasicAgentGenerator.KEY_AGENT_CLASS_NAME);		
		gpfGenerator = GeneratorProperties.getInstance(properties, GeneratorProperties.KEY_GPF_GENERATOR_CLASS);
	}
	
	@Override
	public List<AgentTemplate> generateAgents() {
		IntentionSet is = gpfGenerator.getIntentionSet();
		AgentTemplate at = new AgentTemplate(new ClassName(packageName, agentTypeName), AgentSuperClassTemplate.AGENT_SUPERCLASS, is);
		List<AgentTemplate> templates = new ArrayList<AgentTemplate>();
		templates.add(at);
		return templates;
	}

}
