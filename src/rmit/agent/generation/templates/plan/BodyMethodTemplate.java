package rmit.agent.generation.templates.plan;

import rmit.agent.generation.templates.codeline.CodeLineTemplate;

public class BodyMethodTemplate {

	private final GoalPostSet postedGoals;
	private final CodeLineTemplate[] codeLines;
	
	public BodyMethodTemplate() {
		this(new GoalPostSet());
	}
	
	public BodyMethodTemplate(GoalPostSet postedGoals, CodeLineTemplate ... codeLines) {
		this.postedGoals = postedGoals;
		this.codeLines = codeLines;
	}

	public GoalPostSet getPostedGoals() {
		return postedGoals;
	}

	public CodeLineTemplate[] getCodeLines() {
		return codeLines;
	}

}
