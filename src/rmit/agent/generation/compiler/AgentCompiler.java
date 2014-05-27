package rmit.agent.generation.compiler;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import rmit.agent.generation.templates.agent.AgentTemplate;
import rmit.agent.generation.utils.FileUtils;
import rmit.agent.generation.utils.Utils;
import rmit.agent.generation.writer.JackWriter;

public class AgentCompiler {

	protected List<AgentTemplate> agentTemplates;
	protected String packageName;
	
	private Path rootDir;
	private Path jackDir;
	private Path javaDir;
	private Path classDir;
	
	public AgentCompiler(List<AgentTemplate> agentTemplates, String packageName) {
		this.agentTemplates = agentTemplates;
		this.packageName = packageName;
		
		rootDir = FileUtils.getRandomTempDirectory();
		jackDir = Paths.get(rootDir.toString(), "jack_src");
		javaDir = Paths.get(rootDir.toString(), "java_src");
		classDir = Paths.get(rootDir.toString(), "bin");
		
	}
	
	public void clean() {
		try {
			FileUtils.recursivelyDelete(rootDir.toFile());
		} catch (IOException e) {
			Utils.throwAsUnchecked(e);
		}
	}

	public void writeJackCode() {
		clean();
		rootDir.toFile().mkdirs();
		jackDir.toFile().mkdirs();
		
		JackWriter jackWriter = new JackWriter(jackDir);
		jackWriter.recursivelyWriteAgent(agentTemplates);
	}

	public void compileJackToJava() {
		writeJackCode();
		javaDir.toFile().mkdirs();
		
		String[] packageDirPath = packageName.split("\\.");
		Path topLevelPath = Paths.get(jackDir.toString(), packageDirPath);
		for (File subDir : topLevelPath.toFile().listFiles()) {
			if (subDir.isDirectory()) {
//				CompilerTools.compileJack(Paths.get(subDir.toURI()), javaDir, true);
			}
		}
		CompilerTools.compileJack(topLevelPath, javaDir, true);

	}
	
	public void compileJava() {
		compileJackToJava();
		classDir.toFile().mkdirs();
		
		CompilerTools.compileJava(javaDir, classDir);
	}
	
	public void makeJar(String jarName) {
		compileJava();
		
		Path jarFile = Paths.get(rootDir.toString(), jarName);
		CompilerTools.jar(jarFile, rootDir);
	}

}
