package rmit.agent.generation.templates.goal;

import java.io.Serializable;

public enum EventType implements Serializable {

	EVENT 				("Event"),
	BDI_GOAL_EVENT 		("BDIGoalEvent"),
	PLAN_CHOICE_EVENT 	("PlanChoiceEvent");
	
	private final String jackString;
	
	private EventType(String jackString) {
		this.jackString = jackString;
	}
	
	@Override
	public String toString() {
		return jackString;
	}
	
}
