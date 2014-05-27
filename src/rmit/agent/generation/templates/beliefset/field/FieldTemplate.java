package rmit.agent.generation.templates.beliefset.field;

import java.io.Serializable;

import rmit.agent.generation.templates.ClassName;
import rmit.agent.generation.templates.beliefset.ParameterTemplate;
import rmit.agent.generation.templates.beliefset.VariableType;
import rmit.agent.generation.utils.ReflectionUtils;

public class FieldTemplate<T> implements Comparable<FieldTemplate<T>>, Serializable {

	private static final long serialVersionUID = 1L;

	private final String name;
	private final String compoundName;
	private final Class<?> type;
	private final FieldRange<T> range;
	private final FieldType fieldType;
	
	public FieldTemplate(ClassName enclosingClassName, String name, FieldType fieldType, Class<?> type, FieldRange<T> range) {
		this.name = (fieldType.equals(FieldType.KEY) ? "k_" : "v_") + name;
		this.compoundName = enclosingClassName.getClassName()  + "_" + this.name;
		this.fieldType = fieldType;
		if (type.isPrimitive())
			this.type = ReflectionUtils.wrapPrimitiveType(type);
		else
			this.type = type;
		
		this.range = range;
	}
	
	public String getShortName() {
		return name;
	}
	
	public String getCompoundName() {
		return compoundName;
	}
	
	public Class<?> getType() {
		return type;
	}
	
	public boolean isKey() {
		return fieldType.equals(FieldType.KEY);
	}
	
	public boolean isValue() {
		return fieldType.equals(FieldType.VALUE);
	}
	
	public FieldType getFieldType() {
		return fieldType;
	}
	
	public ParameterTemplate<T> buildQueryParamater(VariableType variableType) {
		return new ParameterTemplate<T>(this, variableType);
	}
	
	public FieldRange<T> getRange() {
		return range;
	}
	
	@Override
	public int compareTo(FieldTemplate<T> other) {
		int keyComp = Boolean.compare(this.isKey(), other.isKey());
		if (keyComp != 0)
			return keyComp;
		else
			return this.name.compareTo(other.name);
		
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == null)
			return false;
		if (o == this)
			return true;
		else if (o instanceof FieldTemplate<?>) {
			FieldTemplate<?> ft = (FieldTemplate<?>) o;
			return this.name.equals(ft.name) &&
					this.type.equals(ft.type) && 
					this.range.equals(ft.range) &&
					this.fieldType.equals(ft.fieldType);
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return name.hashCode() | type.hashCode() | fieldType.hashCode(); //range.hashCode()
	}
	
}
