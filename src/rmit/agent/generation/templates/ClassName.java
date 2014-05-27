package rmit.agent.generation.templates;

import rmit.agent.generation.writer.JackCodeUtils;

public class ClassName {

	private final String className;
	private final String packageName;
	
	public ClassName(Class<?> type) {
		type = JackCodeUtils.getArrayComponent(type);
		
		this.className = type.getSimpleName();
		this.packageName = type.getPackage().getName();
	}
	
	public ClassName(String packageName, String className) {
		this.className = className;
		this.packageName = packageName;
	}

	public String getClassName() {
		return className;
	}

	public String getPackageName() {
		return packageName;
	}
	
	public boolean isDefaultPackage() {
		return packageName.equals("java.lang");
	}
	
	@Override
	public String toString() {
		return packageName + "." + className;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == null)
			return false;
		else if (o == this)
			return true;
		else if (o instanceof ClassName) {
			ClassName cn = (ClassName) o;
			return className.equals(cn.className) && packageName.equals(cn.packageName);
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return className.hashCode() | packageName.hashCode();
	}
	
}
