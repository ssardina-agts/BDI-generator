package rmit.agent.generation.templates.beliefset;

import java.io.Serializable;

import rmit.agent.generation.JackCodeUtils;
import rmit.agent.generation.templates.beliefset.field.FieldTemplate;
import rmit.agent.generation.templates.plan.QueryArg;

public class ParameterTemplate<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	private final FieldTemplate<T> field;
	private final VariableType variableType;
	private final String name;
	
	public ParameterTemplate(FieldTemplate<T> field, VariableType variableType) {
		this.field = field;
		this.variableType = variableType;
		
		//append $ to logical variables
		if (variableType.equals(VariableType.LOGICAL) && !field.getCompoundName().startsWith(JackCodeUtils.LOGICAL_VARIABLE_PREFIX))
			name = JackCodeUtils.LOGICAL_VARIABLE_PREFIX + field.getCompoundName();
		else
			name = field.getCompoundName();
	}
	
	public FieldTemplate<?> getField() {
		return field;
	}
	
	public Class<?> getType() {
		return field.getType();
	}
	
	public String getName() {
		return name;
	}
	
	public VariableType getVariableType() {
		return variableType;
	}
	
	public boolean isLogical() {
		return variableType.equals(VariableType.LOGICAL);
	}
	
	public boolean isGround() {
		return variableType.equals(VariableType.GROUND);
	}
	
	@SuppressWarnings("unchecked")
	public QueryArg buildQueryArg(Object argValue) {
		if (isLogical()) {
			if (argValue != null)
				throw new IllegalArgumentException("argValue must be null for logical parameters");
			return new QueryArg(this, null);
		}
		else { //is ground
			if (argValue == null)
				throw new IllegalArgumentException("argValue cannot be null");
			else if (argValue.getClass() != getType())
				throw new IllegalArgumentException("argValue is not of type " + getType());
			return new QueryArg(this, (T) argValue);
		}
		
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == null)
			return false;
		else if (o == this)
			return true;
		else if (o instanceof ParameterTemplate<?>) {
			ParameterTemplate<?> pt = (ParameterTemplate<?>) o;
			return this.variableType.equals(pt.variableType) &&
					this.field.equals(pt.field);
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return variableType.hashCode() | field.hashCode();
	}
	
}
