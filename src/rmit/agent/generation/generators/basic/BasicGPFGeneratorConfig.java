package rmit.agent.generation.generators.basic;

import java.util.Properties;

import rmit.agent.generation.generators.GPFGeneratorConfig;

public class BasicGPFGeneratorConfig  extends GPFGeneratorConfig {

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

		@Override
		public void load(Properties properties) {
			nGpts = Integer.valueOf(properties.getProperty(KEY_N));
			treeDepth = Integer.valueOf(properties.getProperty(KEY_DEPTH));
			numPostedGoals = Integer.valueOf(properties.getProperty(KEY_N_GOALS_POSTED));
			numHandledPlans = Integer.valueOf(properties.getProperty(KEY_N_HANDLING_PLANS));
			numContextConditionQueries = Integer.valueOf(properties.getProperty(KEY_N_CONTEXT_QS));
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

}
