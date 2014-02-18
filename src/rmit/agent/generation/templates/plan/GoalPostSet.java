package rmit.agent.generation.templates.plan;

import rmit.utils.collections.ImmutableSet;

public class GoalPostSet extends ImmutableSet<GoalPostTemplate> {

	private static final long serialVersionUID = 1L;

	public GoalPostSet(GoalPostTemplate ... array) {
		super(GoalPostTemplate.class, array);
	}
	
	public GoalPostSet(Iterable<GoalPostTemplate> iter) {
		super(GoalPostTemplate.class, iter);
	}

}
