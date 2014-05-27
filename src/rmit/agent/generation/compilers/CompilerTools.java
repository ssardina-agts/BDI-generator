package rmit.agent.generation.compilers;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.StandardLocation;
import javax.tools.ToolProvider;

import org.apache.log4j.Logger;

import rmit.agent.generation.utils.FileUtils;
import rmit.agent.generation.utils.Utils;

public class CompilerTools {

	private static final Logger logger = Logger.getLogger(CompilerTools.class);
	
	private static final String CP_PATH_DIVIDER = ";";
	
	private static final File LIB_PATH = Paths.get(System.getProperty("user.dir"), "lib").toFile();
	
	public static void compileJack(Path jackPath, Path javaPath, boolean recursive, File ... classPathEntries) {
		try {
			logger.debug("Compiling " + jackPath);
			Files.createDirectories(javaPath);
			
			

			StringBuilder cpBuilder = new StringBuilder();
			cpBuilder.append("\"");
			//put all jars etc onto the classpath
			for (File jar : classPathEntries)
				cpBuilder.append(jar.getAbsolutePath() + ";");
			//cpBuilder.append(outputPath + ";");
			//cpBuilder.append(javaPath + ";");
			//now add everything in the current class path
			cpBuilder.append(System.getProperty("java.class.path"));
			
			cpBuilder.append("\"");
			String cp = cpBuilder.toString();
			
			//build process
			ProcessBuilder pb = new ProcessBuilder();
			//String[] command = { "java", "-cp", cp, "aos.main.JackBuild", (recursive ? "-r" : ""), "-d", javaPath.toString(), "-dc", outputPath.toString(), "-cp", cp };
			String[] command = { "java", "-cp", cp, "aos.main.JackBuild", (recursive ? "-r" : ""), "-d", javaPath.toString(), "-cp", cp, "-nc" };
			pb.command(command);
			logger.debug("Running aos.main.JackBuild: " + pb.command());
			pb.directory(jackPath.toFile());
			pb.redirectErrorStream(true);
			
			//start
			Process p = pb.start();
			BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				logger.debug(inputLine);
			}
			int rc = p.waitFor();
			if (rc != 0) {
				logger.error("JackBuild returned " + rc);
				throw new IllegalArgumentException("Cannot compile JACK code");
			}
			else
				logger.debug("JACK compiled ok.");
		}
		catch (IOException e) {
			logger.error("Error compiling JACK code", e);
			Utils.throwAsUnchecked(e);
		} 
		catch (InterruptedException e) {
			logger.error("Error compiling JACK code", e);
			Utils.throwAsUnchecked(e);
		}
	}
	
	public static void compileJava(Path javaPath, Path outputPath) {		
	    try {
	    	logger.debug("Compiling from source directory " + javaPath);
	    	
	    	JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
	    	if (compiler == null)
	    		throw new IllegalStateException("Cannot find system Java compiler! The JRE must be part of a JDK.");
	    	
		    StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
	    	
		    Files.createDirectories(outputPath);
		    
	    	//get source files
			List<File> sourceFileList = new ArrayList<File>();
			for (Path sourcePath : FileUtils.getAllFiles(javaPath.toFile()))
				sourceFileList.add(sourcePath.toFile());
			
			Iterable<? extends JavaFileObject> compUnits = fileManager.getJavaFileObjectsFromFiles(sourceFileList);
			
			//put all files in lib dir onto the classpath
			List<File> cpList = new ArrayList<File>();
			for (File jar : LIB_PATH.listFiles()) 
				cpList.add(jar);
			
			//now add everything in the current class path
			for (String cpEntry : System.getProperty("java.class.path").split(CP_PATH_DIVIDER))
				cpList.add(new File(cpEntry));
			
			//run compiler
			fileManager.setLocation(StandardLocation.CLASS_PATH, cpList);		
			fileManager.setLocation(StandardLocation.CLASS_OUTPUT, Arrays.asList(outputPath.toFile()));
			CompilationTask task = compiler.getTask(null, fileManager, null, null, null, compUnits);
			if(!task.call()) 
				throw new IllegalArgumentException("Cannot compile Java code!");
			else
				logger.debug("Java compiled ok.");
				
			
		} 
	    catch (IOException e) {
			logger.error("Error compiling Java code", e);
			Utils.throwAsUnchecked(e);
		}		
	}
	
	public static void jar(Path jarFile, Path directory ) {
		try {
			logger.debug("Packaging directory " + directory + " into jar file " + jarFile);
			
			File pwd = directory.toFile();			
			ProcessBuilder pb = new ProcessBuilder();
			String[] command = {"jar", "cf0", jarFile.toString(), "*"};
			pb.command(command);
			pb.redirectErrorStream(true);
			pb.directory(pwd);
			Process p = pb.start();
			BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				logger.debug(inputLine);
			}
			int rc = p.waitFor();
			if (rc != 0) {
				logger.error("Jar process returned " + rc);
				throw new IllegalArgumentException("Cannot build " + jarFile);
			}
			else
				logger.debug("Jar created ok.");
		}
		catch (IOException e) {
			logger.error("Error building jar", e);
			Utils.throwAsUnchecked(e);
		} 
		catch (InterruptedException e) {
			logger.error("Error building jar", e);
			Utils.throwAsUnchecked(e);
		}
	}
	
}
