package rmit.agent.generation.utils;

public class ReflectionUtils {

	public static boolean isPrimitiveWrapper(Class<?> type) {
		return type.equals(Short.class) ||
				type.equals(Integer.class) ||
				type.equals(Long.class) ||
				type.equals(Float.class) ||
				type.equals(Double.class) ||
				type.equals(Byte.class) ||
				type.equals(Character.class) ||
				type.equals(Boolean.class);
	}
	
	public static boolean isNumeric(Class<?> type) {
		return type.equals(Short.class) ||
				type.equals(Integer.class) ||
				type.equals(Long.class) ||
				type.equals(Float.class) ||
				type.equals(Double.class) ||
				type.equals(short.class) ||
				type.equals(int.class) ||
				type.equals(long.class) ||
				type.equals(float.class) ||
				type.equals(double.class);
	}
	
	public static Class<?> getWrappedPrimitive(Class<?> type) {
		if (type.equals(Short.class))
			return short.class;
		if (type.equals(Integer.class))
			return int.class;
		if (type.equals(Long.class))
			return long.class;
		if (type.equals(Float.class))
			return float.class;
		if (type.equals(Double.class))
			return double.class;
		if (type.equals(Byte.class))
			return byte.class;
		if (type.equals(Character.class))
			return char.class;
		if (type.equals(Boolean.class))
			return boolean.class;
		else 
			throw new IllegalArgumentException(type.toString());
	}
	
	public static Class<?> wrapPrimitiveType(Class<?> type) {
		if (type.equals(short.class))
			return Short.class;
		if (type.equals(int.class))
			return Integer.class;
		if (type.equals(long.class))
			return Long.class;
		if (type.equals(float.class))
			return Float.class;
		if (type.equals(double.class))
			return Double.class;
		if (type.equals(byte.class))
			return Byte.class;
		if (type.equals(char.class))
			return Character.class;
		if (type.equals(boolean.class))
			return Boolean.class;
		else 
			throw new IllegalArgumentException(type.toString());
	}

	
	
}
