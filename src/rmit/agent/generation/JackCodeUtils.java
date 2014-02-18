package rmit.agent.generation;

import rmit.agent.generation.templates.ClassName;
import rmit.utils.ReflectionUtils;

public class JackCodeUtils {

	public static final String OPEN_BRACE				= "{";
	public static final String CLOSE_BRACE				= "}";
	public static final String OPEN_PAREN				= "(";
	public static final String CLOSE_PAREN				= ")";
	public static final String CODE_TERM				= ";";
	public static final String TAB						= "\t";
	public static final String NEW_LINE					= "\n";
	public static final String COMMENT_PREFIX			= "//";
	

	public static final String LOGICAL_VARIABLE_PREFIX	= "$";
	public static final String DEFAULT_QUERY_NAME		= "get";
	public static final String DEFAULT_POST_METHOD_NAME	= "post";
	
	
	public static boolean isImportRequired(String currentPackage, Class<?> importClass) {		
		importClass = getArrayComponent(importClass);
		if (importClass.isPrimitive())
			return false;
		String packageName = importClass.getPackage().getName();
		if (currentPackage.equals(packageName))
			return false;
		if (packageName.contains("java.lang"))
			return false;

		return true;
	}
	
	public static boolean isImportRequired(String currentPackage, ClassName importClass) {
		String packageName = importClass.getPackageName();
		if (currentPackage.equals(packageName))
			return false;
		if (packageName.contains("java.lang"))
			return false;
			
		return true;
	}
	
	public static String classToJackString(Class<?> type) {
		if (ReflectionUtils.isPrimitiveWrapper(type))
			return ReflectionUtils.getWrappedPrimitive(type).getSimpleName();
		else
			return type.getSimpleName();
	}
	
	public static Class<?> getArrayComponent(Class<?> type) {
		if (!type.isArray())
			return type;
		return  getArrayComponent(type.getComponentType());
		
	}	
}
