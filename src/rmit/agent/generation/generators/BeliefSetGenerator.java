package rmit.agent.generation.generators;

import rmit.agent.generation.templates.ClassName;
import rmit.agent.generation.templates.beliefset.BeliefSetTemplate;
import rmit.agent.generation.templates.beliefset.VariableType;

public abstract class BeliefSetGenerator {

	public abstract BeliefSetTemplate getBeliefSet(ClassName cn);
	
	public abstract BeliefSetGeneratorConfig getConfig();
	
	public static VariableType[][] getAllPermutations(int nVars) {
		int nPerms = (int) Math.pow(2, nVars);
		VariableType[][] vars = new VariableType[nPerms][nVars];
		int changeAfter = nPerms/2;
		for (int v = 0; v < nVars; v++) {
			VariableType vt = VariableType.GROUND;
			for (int p = 0; p < nPerms; p++) {
				if (p % changeAfter == 0)
					vt = (vt == VariableType.GROUND ? VariableType.LOGICAL : VariableType.GROUND);
				vars[p][v] = vt;
			}
			changeAfter/=2;
		}
		return vars;
	}
	
}
