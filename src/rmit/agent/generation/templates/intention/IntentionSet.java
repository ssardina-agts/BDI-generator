package rmit.agent.generation.templates.intention;

import rmit.utils.collections.ImmutableSet;

public class IntentionSet extends ImmutableSet<IntentionTemplate> {

	private static final long serialVersionUID = 1L;

	public IntentionSet(IntentionTemplate ... array) {
		super(IntentionTemplate.class, array);
	}
	
	public IntentionSet(Iterable<IntentionTemplate> iter) {
		super(IntentionTemplate.class, iter);
	}

}
