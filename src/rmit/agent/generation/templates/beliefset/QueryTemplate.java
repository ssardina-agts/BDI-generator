package rmit.agent.generation.templates.beliefset;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import rmit.agent.generation.JackCodeUtils;
import rmit.agent.generation.templates.beliefset.field.FieldSet;
import rmit.agent.generation.templates.beliefset.field.FieldTemplate;

public class QueryTemplate implements Serializable {

	private static final long serialVersionUID = 1L;

	private final String name;
	private final ParameterTemplate<?>[] parameters;
	
	public QueryTemplate(FieldSet fields, VariableType ... variableTypes) {
		this(JackCodeUtils.DEFAULT_QUERY_NAME, fields, variableTypes);
	}
	
	public QueryTemplate(String name, FieldSet fields, VariableType ... variableTypes) {
		this.name = name;
		
		if (fields.getSize() != variableTypes.length)
			throw new IllegalArgumentException(fields.getSize() + " != " + variableTypes.length);
		
		List<ParameterTemplate<?>> params = new ArrayList<ParameterTemplate<?>>();
		int i = 0;
		for (FieldTemplate<?> field : fields.getFieldTemplates()) {
			params.add(field.buildQueryParamater(variableTypes[i]));
			i++;
		}
		parameters = params.toArray(new ParameterTemplate[params.size()]);

	}

	public String getName() {
		return name;
	}

	public ParameterTemplate<?>[] getParameters() {
		return parameters;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == null)
			return false;
		else if (o == this)
			return true;
		else if (o instanceof QueryTemplate) {
			QueryTemplate qt = (QueryTemplate) o;
			return this.name.equals(qt.name) &&  Arrays.deepEquals(this.parameters, qt.parameters);
		}
		else
			return false;
	}
	
	@Override
	public int hashCode() {
		return name.hashCode() | Arrays.hashCode(parameters);
	}
	
}
