package rmit.agent.generation.writer;

import java.nio.file.Path;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import rmit.agent.generation.JackCodeUtils;
import rmit.agent.generation.templates.ClassName;
import rmit.agent.generation.templates.JackSourceFile;
import rmit.agent.generation.templates.agent.AgentTemplate;
import rmit.agent.generation.templates.agent.ConstructorParameterTemplate;
import rmit.agent.generation.templates.beliefset.BeliefSetTemplate;
import rmit.agent.generation.templates.beliefset.ParameterTemplate;
import rmit.agent.generation.templates.beliefset.QueryTemplate;
import rmit.agent.generation.templates.beliefset.field.FieldTemplate;
import rmit.agent.generation.templates.codeline.CodeLineTemplate;
import rmit.agent.generation.templates.goal.GoalTemplate;
import rmit.agent.generation.templates.goal.PostingMethodTemplate;
import rmit.agent.generation.templates.plan.BodyMethodTemplate;
import rmit.agent.generation.templates.plan.GoalPostTemplate;
import rmit.agent.generation.templates.plan.PlanTemplate;
import rmit.agent.generation.templates.plan.PostingMethod;
import rmit.agent.generation.templates.plan.QueryArg;
import rmit.agent.generation.templates.plan.QueryCallTemplate;
import rmit.utils.FileUtils;

public class JackWriter {

	private static final Logger logger = Logger.getLogger(JackWriter.class);
	
	private static final DateFormat df = new SimpleDateFormat("yyyy.MM.dd 'at' HH:mm:ss");
	
	private Path rootDir;
	private CodeBuilder cb;
	
	public JackWriter() {
		cb = new CodeBuilder();
	}
	
	public void recursivelyWriteAgent(Path rootDir, List<AgentTemplate> agents) {
		this.rootDir = rootDir;
		Set<GoalTemplate> goals = new HashSet<GoalTemplate>();
		Set<BeliefSetTemplate> beliefs = new HashSet<BeliefSetTemplate>();
		Set<PlanTemplate> plans = new HashSet<PlanTemplate>();
		
		for (AgentTemplate agent : agents) {
			for (GoalTemplate gt : agent.getHandledEvents()) 
				goals.add(gt);
			for (GoalPostTemplate gpt : agent.getPostedSentEvents())
				goals.add(gpt.getPostedGoal());
			for (BeliefSetTemplate bst : agent.getReferencedData())
				beliefs.add(bst);
			for (PlanTemplate pt : agent.getPlans())
				plans.add(pt);
			
			writeAgent(agent);
		}
		
		for (GoalTemplate gt : goals)
			writeGoal(gt);
		for (BeliefSetTemplate bst : beliefs)
			writeBeliefSet(bst);
		for (PlanTemplate pt : plans)
			writePlan(pt);
		
	}
	
	public void writeBeliefSet(BeliefSetTemplate bs) {
		logger.debug("Writing " + bs.getClassName());
		cb.clear();
		writeSummary(bs);
		
		//package
		cb.writeLine("package " + bs.getClassName().getPackageName());
		cb.newLine();
		
		//imports
		for (ClassName imp : bs.getImports())
			cb.writeLine("import " + imp);
		cb.newLine();

		//bs declaration
		cb.openBlock("public beliefset " + bs.getClassName().getClassName() + " extends " + bs.getWorldType());
		
		//write keys
		for (FieldTemplate<?> field : bs.getFields().getFieldTemplates())
			cb.writeLine("#" + field.getFieldType() + " field " + JackCodeUtils.classToJackString(field.getType()) + " " + field.getShortName());
		cb.newLine();
		
		//write queries
		for (QueryTemplate query : bs.getQueries()) {
			ParameterTemplate<?>[] params = query.getParameters();
			String[] paramStrs = new String[params.length];
			for (int i = 0; i < params.length; i++) {
				ParameterTemplate<?> param = params[i];
				paramStrs[i] = (param.isLogical() ? (param.getVariableType() + " ") : "") + JackCodeUtils.classToJackString(param.getType()) + " " + param.getName();
			}
			cb.writeLine("#indexed query " + query.getName(), paramStrs);
		}
		cb.newLine();
		cb.closeBlock();
		FileUtils.writeFile(bs.getSourceFile(rootDir), cb.getCodeString());
	}
	
	public void writeGoal(GoalTemplate gt) {
		logger.debug("Writing " + gt.getClassName());
		cb.clear();
		writeSummary(gt);
		
		//package
		cb.writeLine("package " + gt.getClassName().getPackageName());
		cb.newLine();

		//imports
		for (ClassName imp : gt.getImports())
			cb.writeLine("import " + imp);
		cb.newLine();
		
		cb.openBlock("public event " + gt.getClassName().getClassName() + " extends " + gt.getEventType());
		cb.newLine();
		
		for (PostingMethodTemplate pm : gt.getPostingMethods()) {
			cb.writeLine("#" + pm.getPostingType() + " " + pm.getName() + "()");
		}
		
		cb.closeBlock();
		FileUtils.writeFile(gt.getSourceFile(rootDir), cb.getCodeString());
	}
	
	public void writePlan(PlanTemplate pt) {
		logger.debug("Writing " + pt.getClassName());
		cb.clear();
		writeSummary(pt);
		
		//package
		cb.writeLine("package " + pt.getClassName().getPackageName());
		cb.newLine();
		
		//imports
		for (ClassName imp : pt.getImports())
			cb.writeLine("import " + imp);
		cb.newLine();

		//plan declaration
		cb.openBlock("public plan " + pt.getClassName().getClassName() + " extends Plan");
		cb.newLine();
		
		//event declarations
		cb.writeLine("#handles event " + pt.getHandledGoal().getClassName().getClassName() + " " + pt.getHandledGoal().getVariableName());
		cb.newLine();
		for (GoalPostTemplate gpt : pt.getBodyMethod().getPostedGoals())
			cb.writeLine("#posts event " + gpt.getPostedGoal().getClassName().getClassName() + " " + gpt.getPostedGoal().getVariableName());
		cb.newLine();
		
		//data declarations
		for (BeliefSetTemplate bst : pt.getReferencedData())
			cb.writeLine("#uses data " + bst.getClassName().getClassName() + " " +bst.getVariableName());
		cb.newLine();
		
		//other declarations
		for (CodeLineTemplate dec : pt.getDeclarations())
			cb.writeLine(dec.getCodeLine());
		cb.newLine();
		
		//relevant method
		cb.openBlock("static boolean relevant", pt.getHandledGoal().getClassName().getClassName() + " " + pt.getHandledGoal().getVariableName());
		cb.writeLine("return true");
		cb.closeBlock();
		cb.newLine();
		
		//context method
		//first declare logical variables used in queries
		for (QueryCallTemplate query : pt.getContextMethod().getBsQueries()) {
			for (QueryArg arg : query.getArgs()) {
				if(arg.getParameter().isLogical())
					cb.writeLine("logical " + JackCodeUtils.classToJackString(arg.getParameter().getType()) + " " + arg.getParameter().getName());
			}
		}
		cb.openBlock("context", "");
		String queries = "";
		int q = 0;
		for (QueryCallTemplate query : pt.getContextMethod().getBsQueries()) {
			String[] argStrs = new String[query.getArgs().length];
			int i = 0;
			for (QueryArg arg : query.getArgs()) {
				argStrs[i] = arg.getParameter().isLogical() ? arg.getParameter().getName() : arg.getArgValue().toString(); 
				i++;
			}
			queries += CodeBuilder.formatCSV(query.getBeliefSet().getVariableName() + "." + query.getQuery().getName(), argStrs) + " ";
			if (q < pt.getContextMethod().getBsQueries().getSize()-1 || pt.getContextMethod().getCodeLines().getSize() > 0)
				queries+= "&& ";
			q++;
		}
		q = 0;
		for (CodeLineTemplate codeLine : pt.getContextMethod().getCodeLines()) {
			queries+=codeLine.getCodeLine();
			if (q < pt.getContextMethod().getCodeLines().getSize()-1)
				queries+= "&& ";
		}
		
		cb.writeLine(queries);
		
		cb.closeBlock();
		cb.newLine();
		
		//plan body
		BodyMethodTemplate bt = pt.getBodyMethod();
		cb.openBlock("body", "");
		for (CodeLineTemplate clt : bt.getCodeLines())
			cb.writeLine(clt.getCodeLine());
		for (GoalPostTemplate gpt : bt.getPostedGoals()) {
			cb.writeLine("@" + gpt.getPostingMethod(), gpt.getPostedGoal().getVariableName() + "." + gpt.getPostedGoal().getPostingMethods().getItems()[0].getName() + "()");
		}
		cb.closeBlock();
		cb.newLine();
		
		//end of plan 
		cb.closeBlock();

		FileUtils.writeFile(pt.getSourceFile(rootDir), cb.getCodeString());
	}
	
	public void writeAgent(AgentTemplate at) {
		logger.debug("Writing " + at.getClassName());
		cb.clear();
		writeSummary(at);
		
		//package
		cb.writeLine("package " + at.getClassName().getPackageName());
		cb.newLine();
		
		//imports
		for (ClassName imp : at.getImports())
			cb.writeLine("import " + imp);
		cb.newLine();	
		
		//declaration
		cb.openBlock("public agent " + at.getClassName().getClassName() + " extends " + at.getSuperclass().getClassName().getSimpleName());
		cb.newLine();
		
		//data
		for (BeliefSetTemplate bst : at.getReferencedData())
			cb.writeLine("#private data " + bst.getClassName().getClassName() + " " + bst.getVariableName() + "()");
		cb.newLine();
		
		
		//events
		for (GoalTemplate gt : at.getHandledEvents()) 
			cb.writeLine("#handles event " + gt.getClassName().getClassName());
		cb.newLine();
		
		for (GoalPostTemplate gpt : at.getPostedSentEvents())
			cb.writeLine((gpt.getPostingMethod().equals(PostingMethod.SEND) ? "#sends" : "#posts") + " event " + gpt.getPostedGoal().getClassName().getClassName() + " " + gpt.getPostedGoal().getVariableName());
		cb.newLine();
		
		//plans
		for (PlanTemplate pt : at.getPlans())
			cb.writeLine("#uses plan " + pt.getClassName().getClassName());
		cb.newLine();

		//other declarations
		for (CodeLineTemplate dec : at.getDeclarations())
			cb.writeLine(dec.getCodeLine());
		cb.newLine();
		
		//constructor
		ConstructorParameterTemplate[] paramTemplates = at.getSuperclass().getConstructorParams();
		String[] params = new String[paramTemplates.length];
		String[] superArgs = new String[paramTemplates.length];
		int i = 0;
		for (ConstructorParameterTemplate cpt : paramTemplates) {
			params[i] = cpt.getParameterType().getSimpleName() + " " + cpt.getVarName();
			superArgs[i] = cpt.getVarName();
			i++;
		}
		
		cb.openBlock("public " + at.getClassName().getClassName(), params);
		cb.writeLine("super", superArgs);
		cb.closeBlock();
		
		cb.closeBlock();
		FileUtils.writeFile(at.getSourceFile(rootDir), cb.getCodeString());
	}
	
	private void writeSummary(JackSourceFile source) {
		cb.writeComment("Created by:      " + System.getProperty("user.name"));
		cb.writeComment("Created at:      " + df.format(new Date(System.currentTimeMillis())));
		cb.writeComment("Java version:    " + System.getProperty("java.version"));
		cb.writeComment("File written to: " + source.getSourceFile(rootDir));
		cb.newLine();
	}
	
	
	
}
