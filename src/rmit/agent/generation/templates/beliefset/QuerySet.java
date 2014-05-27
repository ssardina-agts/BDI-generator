package rmit.agent.generation.templates.beliefset;

import rmit.agent.generation.utils.collections.ImmutableSet;

public class QuerySet extends ImmutableSet<QueryTemplate> {

	private static final long serialVersionUID = 1L;

	public QuerySet(QueryTemplate ... arr) {
		super(QueryTemplate.class, arr);
	}
	
	public QuerySet(Iterable<QueryTemplate> iterable) {
		super(QueryTemplate.class, iterable);
	}


	
}
