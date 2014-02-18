package rmit.agent.generation.generators;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

import rmit.agent.generation.templates.agent.AgentTemplate;
import rmit.agent.generation.writer.JackCompiler;
import rmit.agent.generation.writer.JackWriter;
import rmit.utils.FileUtils;
import rmit.utils.Utils;

public abstract class AgentGenerator {

	public static final Path TEMP_DIR 		= Paths.get(System.getProperty("user.dir"), "temp_" + Math.abs(new Random().nextInt()));
	public static final Path JACK_DIR 		= Paths.get(TEMP_DIR.toString(), "jack");
	public static final Path JAVA_DIR 		= Paths.get(TEMP_DIR.toString(), "java");
	public static final Path CLASS_DIR 		= Paths.get(TEMP_DIR.toString());


	public abstract List<AgentTemplate> generateAgents();

	public abstract JackWriter getJackWriter();

	public abstract AgentGeneratorConfig getConfig();

	public void compileAgents() {
		cleanUp();
		makeTempDirs();
		List<AgentTemplate> agentTemplates = generateAgents();
		writeJackCode(agentTemplates);
		compileJack();
		//compileJava();
		makeJar();
		cleanUp();
	}

	protected void cleanUp() {
		try {
			FileUtils.recursivelyDelete(TEMP_DIR.toFile());
		} catch (IOException e) {
			Utils.throwAsUnchecked(e);
		}
	}

	protected void makeTempDirs() {
		TEMP_DIR.toFile().mkdirs();
		JACK_DIR.toFile().mkdirs();
		JAVA_DIR.toFile().mkdirs();
		CLASS_DIR.toFile().mkdirs();
	}

	protected void writeJackCode(List<AgentTemplate> agentTemplates) {
		//write JACK code
		JackWriter jackWriter = getJackWriter();
		jackWriter.recursivelyWriteAgent(JACK_DIR, agentTemplates);
	}

	protected void compileJack() {

		String[] packageDirPath = getConfig().getPackageName().split("\\.");
		Path topLevelPath = Paths.get(JACK_DIR.toString(), packageDirPath);
		for (File subDir : topLevelPath.toFile().listFiles()) {
			if (subDir.isDirectory()) {
				JackCompiler.compileJack(Paths.get(subDir.toURI()), JAVA_DIR, CLASS_DIR, true);
			}
		}
		JackCompiler.compileJack(topLevelPath, JAVA_DIR, CLASS_DIR, false);

	}
	
	protected void compileJava() {
		JackCompiler.compileJava(JAVA_DIR, CLASS_DIR);
	}
	
	protected void makeJar() {
		Path jarFile = Paths.get(System.getProperty("user.dir"), getConfig().getJarFileName());
		JackCompiler.jar(jarFile, TEMP_DIR);
	}



}
