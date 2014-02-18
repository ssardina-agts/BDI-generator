package rmit.agent.generation.templates.plan;

import java.io.Serializable;

import rmit.agent.generation.templates.goal.GoalTemplate;

public class GoalPostTemplate implements Serializable {

	private static final long serialVersionUID = 1L;

	private final GoalTemplate postedGoal;
	private final PostingMethod method;

	public GoalPostTemplate(GoalTemplate postedGoal, PostingMethod method) {
		this.postedGoal = postedGoal;
		this.method = method;
	}

	public GoalTemplate getPostedGoal() {
		return postedGoal;
	}
		
	public PostingMethod getPostingMethod() {
		return method;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == null)
			return false;
		if (o == this)
			return true;
		if (o instanceof GoalPostTemplate) {
			GoalPostTemplate gpt = (GoalPostTemplate) o;
			return postedGoal.equals(gpt.postedGoal) && method.equals(gpt.method);
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return postedGoal.hashCode() | method.hashCode();
	}
	
}
