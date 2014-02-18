package rmit.agent.generation.generators;

import java.util.Properties;

public class AgentGeneratorConfig extends TemplateGeneratorConfig {

	public static final String KEY_CLASS = "agent.config.class";
	public static final String KEY_PACKAGE_NAME	= "package.name";
		
	protected String packageName;
	
	@Override
	public void load(Properties props) {
		packageName = props.getProperty(KEY_PACKAGE_NAME);		
	}
	
	public String getJarFileName() {
		return packageName + ".jar";
	}
	
	public String getPackageName() {
		return packageName;
	}
	
}
