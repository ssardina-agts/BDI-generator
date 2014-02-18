package rmit.agent.generation.templates.intention;

import rmit.agent.generation.templates.agent.PlanSet;
import rmit.agent.generation.templates.goal.GoalTemplate;

public class IntentionTemplate {

	private final GoalTemplate toplevelGoal;
	private final PlanSet planSet;
	
	public IntentionTemplate(GoalTemplate toplevelGoal, PlanSet planSet) {
		this.toplevelGoal = toplevelGoal;
		this.planSet = planSet;
	}

	public GoalTemplate getToplevelGoal() {
		return toplevelGoal;
	}

	public PlanSet getPlanSet() {
		return planSet;
	}
	
	
	
}
