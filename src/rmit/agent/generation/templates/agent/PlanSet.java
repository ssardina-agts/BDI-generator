package rmit.agent.generation.templates.agent;

import rmit.agent.generation.templates.plan.PlanTemplate;
import rmit.agent.generation.utils.collections.ImmutableSet;

public class PlanSet extends ImmutableSet<PlanTemplate> {

	private static final long serialVersionUID = 1L;

	public PlanSet(PlanTemplate ... arr) {
		super(PlanTemplate.class, arr);
	}
	
	public PlanSet(Iterable<PlanTemplate> iterable) {
		super(PlanTemplate.class, iterable);
	}

}
