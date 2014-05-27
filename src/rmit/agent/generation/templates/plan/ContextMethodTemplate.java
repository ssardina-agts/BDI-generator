package rmit.agent.generation.templates.plan;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import rmit.agent.generation.templates.beliefset.BeliefSetSet;
import rmit.agent.generation.templates.beliefset.BeliefSetTemplate;
import rmit.agent.generation.templates.codeline.CodeLineTemplate;
import rmit.agent.generation.utils.collections.ImmutableSet;

public class ContextMethodTemplate implements Serializable {

	private static final long serialVersionUID = 1L;

	private final ImmutableSet<QueryCallTemplate> bsQueries;
	private final ImmutableSet<CodeLineTemplate> others;
	
	private BeliefSetSet allBeliefSets;
	
	public ContextMethodTemplate(QueryCallTemplate ... queries) {
		this(queries, new CodeLineTemplate[0]);
	}
	
	public ContextMethodTemplate(CodeLineTemplate ... code) {
		this(new QueryCallTemplate[0], code);
	}
	
	public ContextMethodTemplate(QueryCallTemplate[] queries, CodeLineTemplate[] codeLines) {
		bsQueries = new ImmutableSet<QueryCallTemplate>(QueryCallTemplate.class, queries);
		others = new ImmutableSet<CodeLineTemplate>(CodeLineTemplate.class, codeLines);
		init();
	}

	private void init() {
		List<BeliefSetTemplate> bss = new ArrayList<BeliefSetTemplate>();
		for (QueryCallTemplate q : bsQueries.getItems()) {
			bss.add(q.getBeliefSet());
		}
		allBeliefSets = new BeliefSetSet(bss);
	}

	public BeliefSetSet getAllBeliefSets() {
		return allBeliefSets;
	}

	public ImmutableSet<QueryCallTemplate> getBsQueries() {
		return bsQueries;
	}

	public ImmutableSet<CodeLineTemplate> getCodeLines() {
		return others;
	}

}
