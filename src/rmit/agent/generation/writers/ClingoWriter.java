package rmit.agent.generation.writers;

import java.nio.file.Path;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import rmit.agent.generation.templates.agent.PlanSet;
import rmit.agent.generation.templates.goal.GoalTemplate;
import rmit.agent.generation.templates.plan.GoalPostTemplate;
import rmit.agent.generation.templates.plan.PlanTemplate;
import rmit.agent.generation.utils.FileUtils;

public class ClingoWriter {

private static final Logger logger = Logger.getLogger(ClingoWriter.class);
	
	private static final DateFormat df = new SimpleDateFormat("yyyy.MM.dd 'at' HH:mm:ss");
	
	private static final String NEW_LINE = "\n";
	private static final String BASE = "#program base.";
	private static final String CUMULATIVE = "#program cumulative(t).";

	
	private Path rootDir;
	
	public ClingoWriter(Path rootDir) {
		this.rootDir = rootDir;
	}
	
	public void writePlans(PlanSet plans) {
		StringBuilder sb = new StringBuilder();
		sb.append("% File created " + df.format(new Date()) + NEW_LINE);
		sb.append(BASE + NEW_LINE);
		for (PlanTemplate pt : plans) {
			sb.append("relevant(" + pt.getClassName().getClassName() + ", " 
						+ pt.getHandledGoal().getClassName().getClassName() + ")." + NEW_LINE);
			
			for (GoalPostTemplate gt : pt.getBodyMethod().getPostedGoals()) {
				sb.append("subgoal(" + gt.getPostedGoal().getClassName().getClassName() + ", "
						+ pt.getClassName().getClassName() + ")." + NEW_LINE);
			}
			sb.append(pt.getBodyMethod().getCodeLines()[0].getCodeLine() + NEW_LINE);
			
			sb.append(NEW_LINE);
		}
		
		System.out.println(sb.toString());
		
		//FileUtils.writeFile(, sb.toString());
	}
	
}
