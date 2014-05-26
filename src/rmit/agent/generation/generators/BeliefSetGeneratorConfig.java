package rmit.agent.generation.generators;

import java.util.Properties;

public abstract class BeliefSetGeneratorConfig extends TemplateGeneratorConfig {
	
	public static final String KEY_CLASS = "bs.config.class";
	
	public static final String KEY_GENERATOR_CLASS = "bs.generator.class";
	
	public abstract void load(Properties p);
	
	public abstract int getNumKeys();
	
	public abstract int getNumValues();
	
	public abstract int getKeyDomainSize();
	
	public abstract int getValueDomainSize();
	
}
