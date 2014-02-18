package rmit.agent.generation.templates.beliefset;

import java.util.ArrayList;
import java.util.List;

import rmit.agent.generation.JackCodeUtils;
import rmit.agent.generation.templates.ClassName;
import rmit.agent.generation.templates.ImportSet;
import rmit.agent.generation.templates.JackSourceFile;
import rmit.agent.generation.templates.JackSourceFileType;
import rmit.agent.generation.templates.beliefset.field.FieldSet;
import rmit.agent.generation.templates.beliefset.field.FieldTemplate;

public class BeliefSetTemplate extends JackSourceFile {

	private static final long serialVersionUID = 1L;
	
	private final BeliefSetWorldType worldType;
	private final FieldSet fields;
	private final QuerySet queries;
	private final ImportSet imports;
	
	public BeliefSetTemplate(ClassName name, BeliefSetWorldType worldType, FieldSet fields, QuerySet queries) {
		super(name, JackSourceFileType.BELIEF_SET);
		this.worldType = worldType;
		this.fields = fields;
		this.queries = queries;
		List<ClassName> imps = new ArrayList<ClassName>();
		for (FieldTemplate<?> field : fields.getFieldTemplates()) {
			if (JackCodeUtils.isImportRequired(className.getPackageName(), field.getType()));
				imps.add(new ClassName(field.getType()));
		}
		imports = new ImportSet(imps);
	}

	public BeliefSetWorldType getWorldType() {
		return worldType;
	}
	
	public FieldSet getFields() {
		return fields;
	}

	public QuerySet getQueries() {
		return queries;
	}
	
	public String getVariableName() {
		return className.getClassName().toLowerCase();
	}
	
	@Override
	public ImportSet getImports() {
		return imports;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == null)
			return false;
		else if (o == this)
			return true;
		else if (o instanceof BeliefSetTemplate) {
			BeliefSetTemplate bs = (BeliefSetTemplate) o;
			return this.className.equals(bs.className) &&
					this.fields.equals(bs.fields) &&
					this.queries.equals(bs.queries) &&
					this.worldType.equals(bs.worldType);
		}
		return false;
	}

	
	
}
