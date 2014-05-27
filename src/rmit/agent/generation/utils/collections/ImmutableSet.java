package rmit.agent.generation.utils.collections;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import rmit.agent.generation.utils.Utils;

public class ImmutableSet<T> implements Iterable<T>, Serializable {

	private static final long serialVersionUID = 1L;
	
	private final Set<T> set;
	private final Class<T> type;
	
	public static <S> ImmutableSet<S> merge(Class<S> type, Collection<? extends ImmutableSet<S>> sets) {
		ImmutableSet<S> newSet = new ImmutableSet<S>(type);
		for (ImmutableSet<S> set : sets)
			newSet.set.addAll(set.set);

		return newSet;
	}
	
	public ImmutableSet(Class<T> type, Iterable<T> iterable) {
		set = new HashSet<T>();
		for (T query : iterable)
			set.add(query);
		this.type = type;
	}
	
	@SafeVarargs
	public ImmutableSet(Class<T> type, T ... array) {
		set = new HashSet<T>();
		for (T query :array)
			set.add(query);
		this.type = type;
	}
	
	public int getSize() {
		return set.size();
	}
	
	public T[] getItems() {
		return Utils.toGenericArray(set, type);
	}
	
	public boolean contains(T item) {
		return set.contains(item);
	}

	@Override
	public Iterator<T> iterator() {
		return new ImmutableIterator<T>(set.iterator());
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == null)
			return false;
		else if (o == this)
			return true;
		else if (o instanceof ImmutableSet) {
			ImmutableSet<?> is = (ImmutableSet<?>) o;
			return set.equals(is.set);
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return type.hashCode() | set.hashCode();
	}
	
	private class ImmutableIterator<S> implements Iterator<S> {

		private Iterator<S> it;
		
		public ImmutableIterator(Iterator<S> it) {
			this.it = it;
		}
		
		@Override
		public boolean hasNext() {
			return it.hasNext();
		}

		@Override
		public S next() {
			return it.next();
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException(ImmutableSet.this.getClass() + " is immutable");
		}		
	}

}
