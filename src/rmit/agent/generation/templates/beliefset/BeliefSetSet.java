package rmit.agent.generation.templates.beliefset;

import rmit.utils.collections.ImmutableSet;

public class BeliefSetSet extends ImmutableSet<BeliefSetTemplate> {

	private static final long serialVersionUID = 1L;

	public BeliefSetSet(BeliefSetTemplate ... array) {
		super(BeliefSetTemplate.class, array);
	}
	
	public BeliefSetSet(Iterable<BeliefSetTemplate> iter) {
		super(BeliefSetTemplate.class, iter);
	}
	
	
	
}
