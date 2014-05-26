package rmit.agent.generation.generators.basic;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import rmit.agent.generation.generators.AgentGenerator;
import rmit.agent.generation.generators.AgentGeneratorConfig;
import rmit.agent.generation.generators.BeliefSetGenerator;
import rmit.agent.generation.generators.BeliefSetGeneratorConfig;
import rmit.agent.generation.generators.GPFGenerator;
import rmit.agent.generation.generators.GPFGeneratorConfig;
import rmit.agent.generation.generators.TemplateGeneratorConfig;
import rmit.agent.generation.templates.ClassName;
import rmit.agent.generation.templates.agent.AgentSuperClassTemplate;
import rmit.agent.generation.templates.agent.AgentTemplate;
import rmit.agent.generation.templates.intention.IntentionSet;
import rmit.agent.generation.writer.JackWriter;
import rmit.utils.Utils;

public class BasicAgentGenerator extends AgentGenerator {

	protected Path configFile;
	
	protected AgentGeneratorConfig config;
	protected BasicGPFGeneratorConfig gpfConfig;
	protected BeliefSetGeneratorConfig bsConfig;
	
	protected GPFGenerator gpfGenerator;
	protected BeliefSetGenerator bsGenerator;
	
	protected JackWriter jackWriter;
	
	
	public BasicAgentGenerator(Path configFile) {
		jackWriter = new JackWriter();
		this.configFile = configFile;
		config 		= TemplateGeneratorConfig.getInstance(configFile, AgentGeneratorConfig.KEY_CLASS);
		gpfConfig 	= TemplateGeneratorConfig.getInstance(configFile, GPFGeneratorConfig.KEY_CLASS);
		bsConfig 	= TemplateGeneratorConfig.getInstance(configFile, BeliefSetGeneratorConfig.KEY_CLASS);
		
		bsGenerator = new BasicBeliefSetGenerator(bsConfig);
		gpfGenerator = new BasicGPFGenerator(gpfConfig, bsGenerator, config.getPackageName());
	}

	@Override
	public List<AgentTemplate> generateAgents() {
		IntentionSet is = gpfGenerator.getIntentionSet();
		AgentTemplate at = new AgentTemplate(new ClassName(config.getPackageName(), "BasicAgent"), AgentSuperClassTemplate.AGENT_SUPERCLASS, is);
		List<AgentTemplate> templates = new ArrayList<AgentTemplate>();
		templates.add(at);
		return templates;
	}

	@Override
	public JackWriter getJackWriter() {
		return jackWriter;
	}

	@Override
	public AgentGeneratorConfig getConfig() {
		return config;
	}
	
	@Override
	public void makeJar() {
		try {
			Files.copy(configFile, Paths.get(TEMP_DIR.toString(), configFile.getFileName().toString()), StandardCopyOption.REPLACE_EXISTING);
			super.makeJar();
		} catch (IOException e) {
			Utils.throwAsUnchecked(e);
		}
	}


}
