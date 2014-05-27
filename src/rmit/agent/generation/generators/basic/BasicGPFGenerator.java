package rmit.agent.generation.generators.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import rmit.agent.generation.generators.BeliefSetGenerator;
import rmit.agent.generation.generators.GPFGenerator;
import rmit.agent.generation.generators.GeneratorProperties;
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
import rmit.agent.generation.utils.Utils;

public class BasicGPFGenerator implements GPFGenerator {
	
	public static final String KEY_PACKAGE_NAME	= "package.name";
	
	public static final String KEY_N					= "gpf.nGpts";
	public static final String KEY_DEPTH				= "gpf.depth";
	public static final String KEY_N_GOALS_POSTED		= "gpf.goals.posted";
	public static final String KEY_N_HANDLING_PLANS		= "gpf.plans.handling";
	public static final String KEY_N_CONTEXT_QS			= "gpf.context.queries";

	private int nGpts;
	private int treeDepth;
	private int numPostedGoals;
	private int numHandledPlans;
	private int numContextConditionQueries;
	
	protected BeliefSetGenerator bsBuilder;
	
	protected int nextIntentionNum;
	protected int nextGoalNum;
	protected int nextPlanNum;
	protected int nextBeliefSetNum;
	
	protected String gptPackageName;
	protected String topLevelPackageName;
	
	public BasicGPFGenerator(Properties properties) {
		topLevelPackageName = properties.getProperty(KEY_PACKAGE_NAME);
		load(properties);
		reset();
	}
	
	protected void load(Properties properties) {
		nGpts = Integer.valueOf(properties.getProperty(KEY_N));
		treeDepth = Integer.valueOf(properties.getProperty(KEY_DEPTH));
		numPostedGoals = Integer.valueOf(properties.getProperty(KEY_N_GOALS_POSTED));
		numHandledPlans = Integer.valueOf(properties.getProperty(KEY_N_HANDLING_PLANS));
		numContextConditionQueries = Integer.valueOf(properties.getProperty(KEY_N_CONTEXT_QS));
	
		bsBuilder = GeneratorProperties.getInstance(properties, GeneratorProperties.KEY_BS_GENERATOR_CLASS);
	}
	
	public void reset() {
		nextIntentionNum = 0;
		nextGoalNum = 0;
		nextPlanNum = 0;
		nextBeliefSetNum = 0;
	}
	
	public int getNumTrees() {
		return nGpts;
	}

	public int getTreeDepth() {
		return treeDepth;
	}

	public int getNumPostedGoals() {
		return numPostedGoals;
	}

	public int getNumHandledPlans() {
		return numHandledPlans;
	}

	public int getNumContextConditionQueries() {
		return numContextConditionQueries;
	}
	
	public IntentionSet getIntentionSet() {
		IntentionTemplate[] its = new IntentionTemplate[getNumTrees()];
		for (int i = 0; i < getNumTrees(); i++)
			its[i] = getIntention();

		return new IntentionSet(its);
	}
	
	protected IntentionTemplate getIntention() {
		gptPackageName = topLevelPackageName + ".gpt_" + Utils.format(nextIntentionNum++);
		GoalTemplate topLevelGoal = getGoalTemplate();
		
		List<PlanTemplate> allPlans = new ArrayList<PlanTemplate>();
		
		List<GoalTemplate> currentGoals = new ArrayList<GoalTemplate>();
		List<PlanTemplate> currentPlans = new ArrayList<PlanTemplate>();
		
		currentGoals.add(topLevelGoal);
		int depth = getTreeDepth();
		for (int i = 0; i < depth; i++) {
			for (GoalTemplate gt : currentGoals) {
				int nHandling = getNumHandledPlans();
				for (int p = 0; p < nHandling; p++) {
					int nPosted = (i == depth - 1) ? 0 : getNumPostedGoals();
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
		ClassName cn = new ClassName(gptPackageName, "Plan_" + Utils.format(nextPlanNum++));
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
		int nQueries = getNumContextConditionQueries();
		QueryCallTemplate[] queries = new QueryCallTemplate[nQueries];
		for (int q = 0; q < nQueries; q++) {
			BeliefSetTemplate bst = bsBuilder.getBeliefSet(new ClassName(gptPackageName, "BeliefSet_" + Utils.format(nextBeliefSetNum++)));
			//pick random query
			QuerySet qs = bst.getQueries();
			QueryTemplate qt = qs.getItems()[Utils.getRandomInt(0, qs.getSize()-1)];
			
			ParameterTemplate<?>[] ps = qt.getParameters();
			Object[] args = new Object[ps.length];
			for (int i = 0; i < args.length; i++) {
				if (ps[i].isLogical()) {
					args[i] = null;
				}
				else {
					FieldRange<?> fr = ps[i].getField().getRange();
					int valueIndex = Utils.getRandomInt(0, fr.getNumValues()-1);
					args[i] = fr.getValueAtIndex(valueIndex);
				}
			}
			queries[q] = new QueryCallTemplate(bst, qt, args);
		}
		return new ContextMethodTemplate(queries, new CodeLineTemplate[]{});
		
	}
	
	protected GoalTemplate getGoalTemplate() {
		ClassName cn = new ClassName(gptPackageName, "Event_" + Utils.format(nextGoalNum++));
		return new GoalTemplate(cn, EventType.BDI_GOAL_EVENT);
	}
	
	
}
