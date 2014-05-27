package rmit.agent.generation;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.util.List;
import java.util.Properties;

import rmit.agent.generation.compiler.AgentCompiler;
import rmit.agent.generation.generators.AgentGenerator;
import rmit.agent.generation.generators.GeneratorProperties;
import rmit.agent.generation.templates.agent.AgentTemplate;
import rmit.agent.generation.utils.FileUtils;

public class TestMain {
	
	public static void main(String[] args) throws MalformedURLException, IOException {
		
		//find and load the properties file
		Path propertiesFile = FileUtils.findFile("basic.properties"); //try either random.properties or basic.properties
		Properties properties = new Properties();
		properties.load(propertiesFile.toUri().toURL().openStream());
		
		//instantiate the type of agent generator specified in the properties file
		AgentGenerator ag = GeneratorProperties.getInstance(properties, GeneratorProperties.KEY_AGENT_GENERATOR_CLASS);
		
		//generate agent, goal-plan forest and belief set templates
		List<AgentTemplate> agentTemplates = ag.generateAgents();
		
		//look up package name and jar name
		String packageName = properties.getProperty(GeneratorProperties.KEY_PACKAGE_NAME);
		String jarName = properties.getProperty(GeneratorProperties.KEY_JAR_NAME);
		
		//write jack code, compile to java, package into a jar 
		AgentCompiler compiler = new AgentCompiler(agentTemplates, packageName);
		compiler.makeJar(jarName);
		
		//NB the steps can be separated:
		//compiler.writeJackCode();
		//compiler.compileJackToJava();
		//compiler.compileJava();
		
	}


}
