package rmit.agent.generation.generators;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.util.Properties;

import rmit.agent.generation.templates.ClassName;
import rmit.agent.generation.templates.beliefset.BeliefSetTemplate;
import rmit.agent.generation.templates.beliefset.VariableType;
import rmit.utils.Utils;

public abstract class BeliefSetGenerator {

	@SuppressWarnings("unchecked")
	public static <T extends BeliefSetGenerator> T getInstance(Path configFile, String key) {
		try {
			Properties properties = new Properties();
			properties.load(configFile.toUri().toURL().openStream());
			String typeStr = properties.getProperty(key);	
			Class<T> type = (Class<T>) Class.forName(typeStr);
			Constructor<T> configConstructor = type.getDeclaredConstructor();

			T config = configConstructor.newInstance();
			
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

	public abstract BeliefSetTemplate getBeliefSet(ClassName cn);

	public abstract BeliefSetGeneratorConfig getConfig();

	public static VariableType[][] getAllPermutations(int nVars) {
		int nPerms = (int) Math.pow(2, nVars);
		VariableType[][] vars = new VariableType[nPerms][nVars];
		int changeAfter = nPerms/2;
		for (int v = 0; v < nVars; v++) {
			VariableType vt = VariableType.GROUND;
			for (int p = 0; p < nPerms; p++) {
				if (p % changeAfter == 0)
					vt = (vt == VariableType.GROUND ? VariableType.LOGICAL : VariableType.GROUND);
				vars[p][v] = vt;
			}
			changeAfter/=2;
		}
		return vars;
	}

}
