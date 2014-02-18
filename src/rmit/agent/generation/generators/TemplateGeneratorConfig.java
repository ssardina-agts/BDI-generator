package rmit.agent.generation.generators;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.util.Properties;

import rmit.utils.Utils;

public abstract class TemplateGeneratorConfig {
	
	@SuppressWarnings("unchecked")
	public static <T extends TemplateGeneratorConfig> T getInstance(Path configFile, String key) {
		
		try {
			
			Properties properties = new Properties();
			properties.load(configFile.toUri().toURL().openStream());
			String typeStr = properties.getProperty(key);	
			Class<T> type = (Class<T>) Class.forName(typeStr);
			Constructor<T> configConstructor = type.getDeclaredConstructor();
			
			T config = configConstructor.newInstance();
			config.load(properties);
			return config;
		} 
		catch (IOException e) {
			//cannot load properties
			Utils.throwAsUnchecked(e);
			return null;
		} 
		catch (ClassNotFoundException e) {
			//cannot load class
			Utils.throwAsUnchecked(e);
			return null;
		}
		catch (NoSuchMethodException e) {
			//no constructor
			Utils.throwAsUnchecked(e);
			return null;
		}
		catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			//error initialising config
			Utils.throwAsUnchecked(e);
			return null;
		} 		
	}
	
	public abstract void load(Properties props);
	
	
}
