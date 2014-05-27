package rmit.agent.generation.generators.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import rmit.agent.generation.generators.BeliefSetGenerator;
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

	public static final String KEY_N_KEYS = "bs.keys";
	public static final String KEY_N_VALS = "bs.vals";

	public static final String KEY_KEYS_DOMAIN = "bs.keys.domain";
	public static final String KEY_VALS_DOMAIN = "bs.vals.domain";
	
	private int nKeys;
	private int nValues;
	private int keyDomainSize;
	private int valueDomainSize;
	
	public BasicBeliefSetGenerator(Properties properties) {
		load(properties);
	}
	
	protected void load(Properties properties) {
		nKeys = Integer.valueOf(properties.getProperty(KEY_N_KEYS));
		nValues = Integer.valueOf(properties.getProperty(KEY_N_VALS));
		keyDomainSize = Integer.valueOf(properties.getProperty(KEY_KEYS_DOMAIN));	
		valueDomainSize = Integer.valueOf(properties.getProperty(KEY_VALS_DOMAIN));
	}
	
	@Override
	public BeliefSetTemplate getBeliefSet(ClassName cn) {
		BeliefSetWorldType worldType = BeliefSetWorldType.CLOSED_WORLD;

		int nKeys = getNumKeys();
		int nValues = getNumValues();
		
		List<FieldTemplate<?>> fields = new ArrayList<FieldTemplate<?>>();
		for (int i = 0; i < nKeys; i++) {
			int domainSize = getKeyDomainSize();
			fields.add(new FieldTemplate<Integer>(cn, Integer.toString(i), FieldType.KEY, Integer.class, new IntegerRange(0, domainSize)));
		}
		for (int i = 0; i < nValues; i++) {
			int domainSize = getValueDomainSize();
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
	
	public int getNumKeys() {
		return nKeys;
	}
	
	public int getNumValues() {
		return nValues;
	}
	
	public int getKeyDomainSize() {
		return keyDomainSize;
	}
	
	public int getValueDomainSize() {
		return valueDomainSize;
	}
	
}
