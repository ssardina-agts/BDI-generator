package rmit.agent.generation.templates.agent;

import java.util.ArrayList;
import java.util.List;

import rmit.agent.generation.templates.ClassName;
import rmit.agent.generation.templates.ImportSet;
import rmit.agent.generation.templates.JackSourceFile;
import rmit.agent.generation.templates.JackSourceFileType;
import rmit.agent.generation.templates.beliefset.BeliefSetSet;
import rmit.agent.generation.templates.beliefset.BeliefSetTemplate;
import rmit.agent.generation.templates.codeline.CodeLineTemplate;
import rmit.agent.generation.templates.goal.GoalTemplate;
import rmit.agent.generation.templates.intention.IntentionSet;
import rmit.agent.generation.templates.intention.IntentionTemplate;
import rmit.agent.generation.templates.plan.GoalPostSet;
import rmit.agent.generation.templates.plan.GoalPostTemplate;
import rmit.agent.generation.templates.plan.PlanTemplate;
import rmit.agent.generation.writers.JackCodeUtils;

public class AgentTemplate extends JackSourceFile {

	private static final long serialVersionUID = 1L;

	private final PlanSet plans;
	private final GoalSet handledEvents;
	private final GoalPostSet postedSentEvents;
	private final BeliefSetSet referencedData;
	private final AgentSuperClassTemplate superclass;
	private final ImportSet imports;
	private final GoalSet topLevelGoals;
	private final CodeLineTemplate[] declarations;
	
	public AgentTemplate(ClassName className, AgentSuperClassTemplate superclass, IntentionSet intentions, CodeLineTemplate ... declarations) {
		super(className, JackSourceFileType.AGENT);
		this.superclass = superclass;
		List<PlanTemplate> plans = new ArrayList<PlanTemplate>();
		List<GoalTemplate> tlgs = new ArrayList<GoalTemplate>();
		for (IntentionTemplate intention : intentions) {
			for (PlanTemplate pt : intention.getPlanSet())
				plans.add(pt);
		}
		this.plans = new PlanSet(plans);
		this.topLevelGoals = new GoalSet(tlgs);
		
		List<GoalTemplate> handled = new ArrayList<GoalTemplate>();
		List<GoalPostTemplate> posted = new ArrayList<GoalPostTemplate>();
		List<BeliefSetTemplate> bsList = new ArrayList<BeliefSetTemplate>();
		List<ClassName> impList = new ArrayList<ClassName>();
		
		for (PlanTemplate plan : plans) {
			if (JackCodeUtils.isImportRequired(className.getPackageName(), plan.getClassName()))
				impList.add(plan.getClassName());
			
			handled.add(plan.getHandledGoal());
			for (GoalPostTemplate gpt : plan.getBodyMethod().getPostedGoals())
				posted.add(gpt);
			for (BeliefSetTemplate bst : plan.getReferencedData())
				bsList.add(bst);
		}

		this.handledEvents  = new GoalSet(handled);
		this.postedSentEvents = new GoalPostSet(posted);
		this.referencedData = new BeliefSetSet(bsList);
		this.declarations = declarations;
		for (GoalTemplate gt : handledEvents) {
			if (JackCodeUtils.isImportRequired(className.getPackageName(), gt.getClassName()))
				impList.add(gt.getClassName());
		}
		for (GoalPostTemplate gpt : postedSentEvents) {
			if (JackCodeUtils.isImportRequired(className.getPackageName(), gpt.getPostedGoal().getClassName()))
				impList.add(gpt.getPostedGoal().getClassName());
		}
		for (GoalTemplate gt : topLevelGoals) {
			if (JackCodeUtils.isImportRequired(className.getPackageName(), gt.getClassName()))
				impList.add(gt.getClassName());
		}
		for (BeliefSetTemplate bs : referencedData) {
			if (JackCodeUtils.isImportRequired(className.getPackageName(), bs.getClassName()))
				impList.add(bs.getClassName());
		}
		for (CodeLineTemplate codeLine : declarations) {
			for (ClassName imp : codeLine.getImports()) {
				if (JackCodeUtils.isImportRequired(className.getPackageName(), imp))
					impList.add(imp); 
			}
		}
		if (JackCodeUtils.isImportRequired(className.getPackageName(), superclass.getClassName()))
				impList.add(new ClassName(superclass.getClassName()));
		for (ConstructorParameterTemplate cpt : superclass.getConstructorParams()) {
			if (JackCodeUtils.isImportRequired(className.getPackageName(), cpt.getParameterType()))
				impList.add(new ClassName(cpt.getParameterType())); 
		}
		
		
		this.imports = new ImportSet(impList);
	}

	public PlanSet getPlans() {
		return plans;
	}

	public GoalSet getHandledEvents() {
		return handledEvents;
	}

	public GoalPostSet getPostedSentEvents() {
		return postedSentEvents;
	}

	public AgentSuperClassTemplate getSuperclass() {
		return superclass;
	}

	public BeliefSetSet getReferencedData() {
		return referencedData;
	}

	@Override
	public ImportSet getImports() {
		return imports;
	}

	public GoalSet getTopLevelGoals() {
		return topLevelGoals;
	}

	public CodeLineTemplate[] getDeclarations() {
		return declarations;
	}
	
	

}
