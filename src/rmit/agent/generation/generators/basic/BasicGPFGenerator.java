package rmit.agent.generation.generators.basic;

import java.util.ArrayList;
import java.util.List;

import rmit.agent.generation.GenerationUtils;
import rmit.agent.generation.generators.BeliefSetGenerator;
import rmit.agent.generation.generators.GPFGenerator;
import rmit.agent.generation.generators.GPFGeneratorConfig;
import rmit.agent.generation.templates.ClassName;
import rmit.agent.generation.templates.agent.PlanSet;
import rmit.agent.generation.templates.beliefset.BeliefSetTemplate;
import rmit.agent.generation.templates.beliefset.ParameterTemplate;
import rmit.agent.generation.templates.beliefset.QuerySet;
import rmit.agent.generation.templates.beliefset.QueryTemplate;
import rmit.agent.generation.templates.beliefset.field.FieldRange;
import rmit.agent.generation.templates.codeline.CodeLineTemplate;
import rmit.agent.generation.templates.goal.EventType;
import rmit.agent.generation.templates.goal.GoalTemplate;
import rmit.agent.generation.templates.intention.IntentionSet;
import rmit.agent.generation.templates.intention.IntentionTemplate;
import rmit.agent.generation.templates.plan.BodyMethodTemplate;
import rmit.agent.generation.templates.plan.ContextMethodTemplate;
import rmit.agent.generation.templates.plan.GoalPostSet;
import rmit.agent.generation.templates.plan.GoalPostTemplate;
import rmit.agent.generation.templates.plan.PlanTemplate;
import rmit.agent.generation.templates.plan.PostingMethod;
import rmit.agent.generation.templates.plan.QueryCallTemplate;

public class BasicGPFGenerator implements GPFGenerator {
	
	protected BasicGPFGeneratorConfig config;
	
	protected BeliefSetGenerator bsBuilder;
	
	protected int nextIntentionNum;
	protected int nextGoalNum;
	protected int nextPlanNum;
	protected int nextBeliefSetNum;
	
	protected String gptPackageName;
	protected String topLevelPackageName;
	
	public BasicGPFGenerator(BasicGPFGeneratorConfig config, BeliefSetGenerator bsGenerator, String packageName) {
		this.topLevelPackageName = packageName;
		bsBuilder = bsGenerator;
		this.config = config;
		reset();
	}
	
	@Override
	public GPFGeneratorConfig getConfig() {
		return config;
	}
	
	public void reset() {
		nextIntentionNum = 0;
		nextGoalNum = 0;
		nextPlanNum = 0;
		nextBeliefSetNum = 0;
	}
	
	public IntentionSet getIntentionSet() {
		IntentionTemplate[] its = new IntentionTemplate[config.getNumTrees()];
		for (int i = 0; i < config.getNumTrees(); i++)
			its[i] = getIntention();

		return new IntentionSet(its);
	}
	
	protected IntentionTemplate getIntention() {
		gptPackageName = topLevelPackageName + ".gpt_" + GenerationUtils.format(nextIntentionNum++);
		System.out.println(gptPackageName);
		GoalTemplate topLevelGoal = getGoalTemplate();
		
		List<PlanTemplate> allPlans = new ArrayList<PlanTemplate>();
		
		List<GoalTemplate> currentGoals = new ArrayList<GoalTemplate>();
		List<PlanTemplate> currentPlans = new ArrayList<PlanTemplate>();
		
		currentGoals.add(topLevelGoal);
		int depth = config.getTreeDepth();
		for (int i = 0; i < depth; i++) {
			for (GoalTemplate gt : currentGoals) {
				int nHandling = config.getNumHandledPlans();
				for (int p = 0; p < nHandling; p++) {
					int nPosted = (i == depth - 1) ? 0 : config.getNumPostedGoals();
					PlanTemplate pt = getPlanTemplate(gt, nPosted);
					currentPlans.add(pt);
				}
			}
			currentGoals.clear();
			for (PlanTemplate pt : currentPlans) {
				for (GoalPostTemplate gpt : pt.getBodyMethod().getPostedGoals())
					currentGoals.add(gpt.getPostedGoal());
			}
			allPlans.addAll(currentPlans);
			currentPlans.clear();
		}
		
		return new IntentionTemplate(topLevelGoal, new PlanSet(allPlans));
		
	}
	
	protected PlanTemplate getPlanTemplate(GoalTemplate handledGoal, int nPostedGoals) {
		ClassName cn = new ClassName(gptPackageName, "Plan_" + GenerationUtils.format(nextPlanNum++));
		ContextMethodTemplate context = getContextMethod();

		GoalPostTemplate[] posted = new GoalPostTemplate[nPostedGoals];
		for (int i = 0; i < nPostedGoals; i++) {
			posted[i] = new GoalPostTemplate(getGoalTemplate(), PostingMethod.SUBTASK);
		}
		GoalPostSet gpset = new GoalPostSet(posted);
		BodyMethodTemplate body = new BodyMethodTemplate(gpset); 
		return new PlanTemplate(cn, handledGoal, context, body);
	}
	
	protected ContextMethodTemplate getContextMethod() {
		int nQueries = config.getNumContextConditionQueries();
		QueryCallTemplate[] queries = new QueryCallTemplate[nQueries];
		for (int q = 0; q < nQueries; q++) {
			BeliefSetTemplate bst = bsBuilder.getBeliefSet(new ClassName(gptPackageName, "BeliefSet_" + GenerationUtils.format(nextBeliefSetNum++)));
			//pick random query
			QuerySet qs = bst.getQueries();
			QueryTemplate qt = qs.getItems()[GenerationUtils.getRandomInt(0, qs.getSize()-1)];
			
			ParameterTemplate<?>[] ps = qt.getParameters();
			Object[] args = new Object[ps.length];
			for (int i = 0; i < args.length; i++) {
				if (ps[i].isLogical()) {
					args[i] = null;
				}
				else {
					FieldRange<?> fr = ps[i].getField().getRange();
					int valueIndex = GenerationUtils.getRandomInt(0, fr.getNumValues()-1);
					args[i] = fr.getValueAtIndex(valueIndex);
				}
			}
			queries[q] = new QueryCallTemplate(bst, qt, args);
		}
		return new ContextMethodTemplate(queries, new CodeLineTemplate[]{});
		
	}
	
	protected GoalTemplate getGoalTemplate() {
		ClassName cn = new ClassName(gptPackageName, "Event_" + GenerationUtils.format(nextGoalNum++));
		return new GoalTemplate(cn, EventType.BDI_GOAL_EVENT);
	}
	
}
