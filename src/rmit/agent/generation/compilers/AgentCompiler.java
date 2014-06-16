package rmit.agent.generation.compilers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import rmit.agent.generation.templates.agent.AgentTemplate;
import rmit.agent.generation.utils.FileUtils;
import rmit.agent.generation.utils.Utils;
import rmit.agent.generation.writers.JackWriter;

public class AgentCompiler {

	protected List<AgentTemplate> agentTemplates;
	protected String packageName;
	
	protected Path rootDir;
	protected Path jackDir;
	protected Path javaDir;
	protected Path classDir;
	
	public AgentCompiler(List<AgentTemplate> agentTemplates, String packageName) {
		this.agentTemplates = agentTemplates;
		this.packageName = packageName;
		
		rootDir = FileUtils.getRandomTempDirectory();
		jackDir = Paths.get(rootDir.toString(), "jack_src");
		javaDir = Paths.get(rootDir.toString(), "java_src");
		classDir = Paths.get(rootDir.toString());	
	}
	
	public void clean() {
		try {
			FileUtils.recursivelyDelete(rootDir.toFile());
		} catch (IOException e) {
			Utils.throwAsUnchecked(e);
		}
	}

	public void writeJackCode() {
		rootDir.toFile().mkdirs();
		jackDir.toFile().mkdirs();
		
		JackWriter jackWriter = new JackWriter(jackDir);
		jackWriter.recursivelyWriteAgent(agentTemplates);
	}

	public void compileJackToJava(File ... classPathEntries) {
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
	
	public void compileJava(File ... classPathEntries) {
		compileJackToJava();
		classDir.toFile().mkdirs();
		
		CompilerTools.compileJava(javaDir, classDir);
	}
	
	public void makeJar(String jarName, File ... classPathEntries) {
		compileJava();
		
		Path jarFile = Paths.get(jarName);
		CompilerTools.jar(jarFile, rootDir);
	}

	public Path getRootDir() {
		return rootDir;
	}

	public Path getJackDir() {
		return jackDir;
	}

	public Path getJavaDir() {
		return javaDir;
	}

	public Path getClassDir() {
		return classDir;
	}

}
