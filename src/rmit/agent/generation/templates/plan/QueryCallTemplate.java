package rmit.agent.generation.templates.plan;

import java.io.Serializable;

import rmit.agent.generation.templates.beliefset.BeliefSetTemplate;
import rmit.agent.generation.templates.beliefset.ParameterTemplate;
import rmit.agent.generation.templates.beliefset.QueryTemplate;

public class QueryCallTemplate implements Serializable {

	private static final long serialVersionUID = 1L;

	private final QueryTemplate query;
	private final BeliefSetTemplate beliefSet;
	private final QueryArg[] queryArgs;
	
	public QueryCallTemplate(BeliefSetTemplate beliefSet, QueryTemplate query, Object ... args) {
		this.query = query;
		this.beliefSet = beliefSet;
		if (args.length != query.getParameters().length)
			throw new IllegalArgumentException();
		
		queryArgs = new QueryArg[args.length];
		int i = 0;
		for (ParameterTemplate<?> param : query.getParameters()) {
			queryArgs[i] = param.buildQueryArg(args[i]);
			i++;
		}
	}

	public QueryTemplate getQuery() {
		return query;
	}

	public BeliefSetTemplate getBeliefSet() {
		return beliefSet;
	}

	public QueryArg[] getArgs() {
		return queryArgs;
	}
}
