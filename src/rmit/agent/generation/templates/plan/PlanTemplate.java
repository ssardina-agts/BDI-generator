package rmit.agent.generation.templates.plan;

import java.util.ArrayList;
import java.util.List;

import rmit.agent.generation.JackCodeUtils;
import rmit.agent.generation.templates.ClassName;
import rmit.agent.generation.templates.ImportSet;
import rmit.agent.generation.templates.JackSourceFile;
import rmit.agent.generation.templates.JackSourceFileType;
import rmit.agent.generation.templates.beliefset.BeliefSetSet;
import rmit.agent.generation.templates.beliefset.BeliefSetTemplate;
import rmit.agent.generation.templates.codeline.CodeLineTemplate;
import rmit.agent.generation.templates.goal.GoalTemplate;

public class PlanTemplate extends JackSourceFile {

	private static final long serialVersionUID = 1L;

	private final GoalTemplate handledGoal;
	private final ContextMethodTemplate contextMethod;
	private final BodyMethodTemplate bodyMethod;
	private final BeliefSetSet referencedData;
	private final CodeLineTemplate[] declarations;
	private final ImportSet imports;
	
	public PlanTemplate(ClassName className, GoalTemplate handledGoal, ContextMethodTemplate contextMethod, BodyMethodTemplate bodyMethod, CodeLineTemplate ... declarations) {
		super(className, JackSourceFileType.PLAN);
		this.handledGoal = handledGoal;
		this.contextMethod = contextMethod;
		this.bodyMethod = bodyMethod;	
		this.declarations = declarations;
		referencedData = contextMethod.getAllBeliefSets();
		
		List<ClassName> imps = new ArrayList<ClassName>();
		if (JackCodeUtils.isImportRequired(className.getPackageName(), handledGoal.getClassName()))
			imps.add(handledGoal.getClassName());
		
		for (CodeLineTemplate codeLine : contextMethod.getCodeLines()) {
			for (ClassName imp : codeLine.getImports()) {
				if (JackCodeUtils.isImportRequired(className.getPackageName(), imp))
					imps.add(imp); 
			}
		}
		
		for (GoalPostTemplate gpt : bodyMethod.getPostedGoals()) {
			if (JackCodeUtils.isImportRequired(className.getPackageName(), gpt.getPostedGoal().getClassName()))
				imps.add(gpt.getPostedGoal().getClassName());
		}
		for (CodeLineTemplate codeLine : bodyMethod.getCodeLines()) {
			for (ClassName imp : codeLine.getImports()) {
				if (JackCodeUtils.isImportRequired(className.getPackageName(), imp))
					imps.add(imp); 
			}
		}
		for (CodeLineTemplate codeLine : declarations) {
			for (ClassName imp : codeLine.getImports()) {
				if (JackCodeUtils.isImportRequired(className.getPackageName(), imp))
					imps.add(imp); 
			}
		}
		
		for (BeliefSetTemplate bst : referencedData) {
			if (JackCodeUtils.isImportRequired(className.getPackageName(), bst.getClassName()))
				imps.add(bst.getClassName());
		}
		
		
		imports = new ImportSet(imps);
	}

	public GoalTemplate getHandledGoal() {
		return handledGoal;
	}

	public ContextMethodTemplate getContextMethod() {
		return contextMethod;
	}

	public BodyMethodTemplate getBodyMethod() {
		return bodyMethod;
	}

	public BeliefSetSet getReferencedData() {
		return referencedData;
	}

	@Override
	public ImportSet getImports() {
		return imports;
	}

	public CodeLineTemplate[] getDeclarations() {
		return declarations;
	}
	
}
