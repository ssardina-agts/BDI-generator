package rmit.agent.generation.templates;

import rmit.agent.generation.utils.collections.ImmutableSet;

public class ImportSet extends ImmutableSet<ClassName> {

	private static final long serialVersionUID = 1L;

	public ImportSet(ClassName ... array) {
		super(ClassName.class, array);
	}
	
	public ImportSet(Iterable<ClassName> iter) {
		super(ClassName.class, iter);
	}

}
