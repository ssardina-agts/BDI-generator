package rmit.agent.generation;

import java.nio.file.Path;

import rmit.agent.generation.generators.AgentGenerator;
import rmit.agent.generation.generators.basic.BasicAgentGenerator;
import rmit.utils.FileUtils;

public class TestMain {

	public static void main(String[] args) {
		Path confPath = FileUtils.findFile("test.properties");
		AgentGenerator ag = new BasicAgentGenerator(confPath);
		ag.compileAgents();
		
	}


}
