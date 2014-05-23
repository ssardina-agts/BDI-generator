package rmit.utils;

import java.lang.reflect.Array;
import java.util.Collection;

public class Utils {

	private Utils() {
	}

	public static <T> String arrayToString(T[] arr) {

		StringBuilder sb = new StringBuilder();
		sb.append("[ ");
		for (T obj : arr) {
			if (obj == null)
				sb.append("null");
			else
				sb.append(obj.toString());
			sb.append(" ");
		}
		sb.append("]");
		return sb.toString();
	}
	
	public static <T> String iterableToString(Iterable<T> iterable) {
		StringBuilder sb = new StringBuilder();
		sb.append("[ ");
		for (T obj : iterable) {
			if (obj == null)
				sb.append("null");
			else
				sb.append(obj.toString());
			sb.append(" ");
		}
		sb.append("]");
		return sb.toString();
	}

	public static void throwAsUnchecked(Exception e) {
		throw new RuntimeException(e);
	}

	public static void sleepUninterruptibly(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			throwAsUnchecked(e);
		}
	}
	
		
	@SuppressWarnings("unchecked")
	public static <T> T[] toGenericArray(Collection<T> collection, Class<T> type) {
		return collection.toArray((T[]) Array.newInstance(type, collection.size()));
	}

}
