package rmit.agent.generation.generators;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

import rmit.agent.generation.utils.Utils;

public class GeneratorProperties extends Properties {

	private static final long serialVersionUID = 1L;

	public static final String KEY_PACKAGE_NAME				= "package.name";
	public static final String KEY_JAR_NAME					= "jar.name";
	
	public static final String KEY_AGENT_GENERATOR_CLASS 	= "agent.generator.class";
	public static final String KEY_GPF_GENERATOR_CLASS 		= "gpf.generator.class";
	public static final String KEY_BS_GENERATOR_CLASS 		= "bs.generator.class";

	
	@SuppressWarnings("unchecked")
	public static <T> T getInstance(Properties properties, String typeKey) {		
		try {
			//find type to be instantiated
			String typeStr = properties.getProperty(typeKey);
			Class<T> type = (Class<T>) Class.forName(typeStr);
			
			//find a constructor with a single Properties arg
			Constructor<T> constructor = type.getDeclaredConstructor(Properties.class);
			
			//call the constructor
			T instance = constructor.newInstance(properties);
			
			return instance;
		}  
		catch (ClassNotFoundException e) {
			System.err.println("Cannot load class: " + properties.getProperty(typeKey));
			Utils.throwAsUnchecked(e);
			return null;
		}
		catch (NoSuchMethodException e) {
			//no constructor
			System.err.println("Class " + properties.getProperty(typeKey) + " does not have required constructor");
			Utils.throwAsUnchecked(e);
			return null;
		}
		catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			//instantiation error
			System.err.println("Error instantiating class: " + properties.getProperty(typeKey));
			Utils.throwAsUnchecked(e);
			return null;
		} 		
	}
	
}
