package rmit.agent.generation.templates.agent;

import rmit.agent.generation.templates.goal.GoalTemplate;
import rmit.utils.collections.ImmutableSet;

public class GoalSet extends ImmutableSet<GoalTemplate> {

	private static final long serialVersionUID = 1L;

	public GoalSet(GoalTemplate ... array) {
		super(GoalTemplate.class, array);
	}
	
	public GoalSet(Iterable<GoalTemplate> iter) {
		super(GoalTemplate.class, iter);
	}

}
