package rmit.agent.generation.generators.random;

import java.util.Properties;

import rmit.agent.generation.GenerationUtils;
import rmit.agent.generation.generators.basic.BasicGPFGeneratorConfig;

public class RandomGPFGeneratorConfig extends BasicGPFGeneratorConfig {

	public static final String KEY_MIN_DEPTH			= "gpf.depth.min";
	public static final String KEY_MAX_DEPTH			= "gpf.depth.max";
	public static final String KEY_MIN_GOALS_POSTED		= "gpf.goals.posted.min";
	public static final String KEY_MAX_GOALS_POSTED		= "gpf.goals.posted.max";
	public static final String KEY_MIN_HANDLING_PLANS	= "gpf.plans.handling.min";
	public static final String KEY_MAX_HANDLING_PLANS	= "gpf.plans.hadling.max";
	public static final String KEY_MIN_CONTEXT_QS		= "gpf.context.queries.min";
	public static final String KEY_MAX_CONTEXT_QS		= "gpf.context.queries.max";

	private int nGpts;

	private int minDepth;
	private int maxDepth;

	private int minGoalsPosted;
	private int maxGoalsPosted;

	private int minHandlingPlans;
	private int maxHandlingPlans;

	private int minContextQueries;
	private int maxContextQueries;

	@Override
	public void load(Properties properties) {

		nGpts = Integer.valueOf(properties.getProperty(KEY_N));

		minDepth = Integer.valueOf(properties.getProperty(KEY_MIN_DEPTH));
		maxDepth = Integer.valueOf(properties.getProperty(KEY_MAX_DEPTH));

		minGoalsPosted = Integer.valueOf(properties.getProperty(KEY_MIN_GOALS_POSTED));
		maxGoalsPosted = Integer.valueOf(properties.getProperty(KEY_MAX_GOALS_POSTED));

		minHandlingPlans = Integer.valueOf(properties.getProperty(KEY_MIN_HANDLING_PLANS));
		maxHandlingPlans = Integer.valueOf(properties.getProperty(KEY_MAX_HANDLING_PLANS));

		minContextQueries = Integer.valueOf(properties.getProperty(KEY_MIN_CONTEXT_QS));
		maxContextQueries = Integer.valueOf(properties.getProperty(KEY_MAX_CONTEXT_QS));


	}

	@Override
	public int getNumTrees() {
		return nGpts;
	}

	@Override
	public int getTreeDepth() {
		return GenerationUtils.getRandomInt(minDepth, maxDepth);
	}

	@Override
	public int getNumPostedGoals() {
		return GenerationUtils.getRandomInt(minGoalsPosted, maxGoalsPosted);
	}

	@Override
	public int getNumHandledPlans() {
		return GenerationUtils.getRandomInt(minHandlingPlans, maxHandlingPlans);
	}

	@Override
	public int getNumContextConditionQueries() {
		return GenerationUtils.getRandomInt(minContextQueries, maxContextQueries);
	}

}
