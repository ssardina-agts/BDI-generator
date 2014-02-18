package rmit.agent.generation.templates.beliefset.field;

import java.io.Serializable;
import java.util.SortedMap;
import java.util.TreeMap;


public class FieldSet implements Serializable {

	private static final long serialVersionUID = 1L;

	private final SortedMap<String, FieldTemplate<?>> fields;

	public FieldSet(Iterable<FieldTemplate<?>> fieldColl) {
		this.fields = new TreeMap<String, FieldTemplate<?>>();
		for (FieldTemplate<?> field : fieldColl)
			fields.put(field.getShortName(), field);
	}
	
	public FieldSet(FieldTemplate<?> ... fieldArr) {
		this.fields = new TreeMap<String, FieldTemplate<?>>();
		for (FieldTemplate<?> field : fieldArr)
			fields.put(field.getShortName(), field);
	}
	
	public int getSize() {
		return fields.size();
	}
	
	public FieldTemplate<?>[] getFieldTemplates() {
		return fields.values().toArray(new FieldTemplate<?>[fields.size()]);
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == null)
			return false;
		else if (o == this)
			return true;
		else if (o instanceof FieldSet) {
			FieldSet fs = (FieldSet) o;
			return this.fields.equals(fs.fields);
		}
		return false;
	}
	
}
