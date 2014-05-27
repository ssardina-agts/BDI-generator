package rmit.agent.generation.generators;

import java.util.List;
import rmit.agent.generation.templates.agent.AgentTemplate;

public interface AgentGenerator {
	
	public abstract List<AgentTemplate> generateAgents();

}
