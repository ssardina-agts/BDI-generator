package rmit.agent.generation.generators.basic;

import java.util.ArrayList;
import java.util.List;

import rmit.agent.generation.generators.BeliefSetGenerator;
import rmit.agent.generation.generators.BeliefSetGeneratorConfig;
import rmit.agent.generation.templates.ClassName;
import rmit.agent.generation.templates.beliefset.BeliefSetTemplate;
import rmit.agent.generation.templates.beliefset.BeliefSetWorldType;
import rmit.agent.generation.templates.beliefset.QuerySet;
import rmit.agent.generation.templates.beliefset.QueryTemplate;
import rmit.agent.generation.templates.beliefset.VariableType;
import rmit.agent.generation.templates.beliefset.field.FieldSet;
import rmit.agent.generation.templates.beliefset.field.FieldTemplate;
import rmit.agent.generation.templates.beliefset.field.FieldType;
import rmit.agent.generation.templates.beliefset.field.IntegerRange;

public class BasicBeliefSetGenerator extends BeliefSetGenerator {

	private BeliefSetGeneratorConfig config;
	
	public BasicBeliefSetGenerator(BeliefSetGeneratorConfig config) {
		this.config = config;
	}
	
	@Override
	public BeliefSetGeneratorConfig getConfig() {
		return config;
	}
	
	public BeliefSetTemplate getBeliefSet(ClassName cn) {
		BeliefSetWorldType worldType = BeliefSetWorldType.CLOSED_WORLD;

		int nKeys = config.getNumKeys();
		int nValues = config.getNumValues();
		
		List<FieldTemplate<?>> fields = new ArrayList<FieldTemplate<?>>();
		for (int i = 0; i < nKeys; i++) {
			int domainSize = config.getKeyDomainSize();
			fields.add(new FieldTemplate<Integer>(cn, Integer.toString(i), FieldType.KEY, Integer.class, new IntegerRange(0, domainSize)));
		}
		for (int i = 0; i < nValues; i++) {
			int domainSize = config.getValueDomainSize();
			fields.add(new FieldTemplate<Integer>(cn, Integer.toString(i), FieldType.VALUE, Integer.class, new IntegerRange(0, domainSize)));
		}
		
		FieldSet fieldSet = new FieldSet(fields);
		
		VariableType[][] allVarPerms = getAllPermutations(nKeys + nValues);
		QueryTemplate[] queries = new QueryTemplate[allVarPerms.length];
		for (int i = 0; i < allVarPerms.length; i++)
			queries[i] = new QueryTemplate(fieldSet, allVarPerms[i]);
		
		QuerySet querySet = new QuerySet(queries);
		
		return new BeliefSetTemplate(cn, worldType, fieldSet, querySet);
		
	}
	
}
