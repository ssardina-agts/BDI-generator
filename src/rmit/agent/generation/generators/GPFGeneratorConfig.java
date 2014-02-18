package rmit.agent.generation.generators;

import java.util.Properties;

public abstract class GPFGeneratorConfig extends TemplateGeneratorConfig {

	public static final String KEY_CLASS = "gpf.config.class";
	
	public abstract void load(Properties properties);


}
