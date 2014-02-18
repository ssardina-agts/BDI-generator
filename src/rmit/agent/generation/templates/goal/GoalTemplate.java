package rmit.agent.generation.templates.goal;


import rmit.agent.generation.templates.ClassName;
import rmit.agent.generation.templates.ImportSet;
import rmit.agent.generation.templates.JackSourceFile;
import rmit.agent.generation.templates.JackSourceFileType;

public class GoalTemplate extends JackSourceFile {

	private static final long serialVersionUID = 1L;

	private final EventType eventType;
	private final PostingMethodSet postingMethods;
	private final ImportSet imports;
	
	public GoalTemplate(ClassName className, EventType eventType) {
		super(className, JackSourceFileType.EVENT);
		this.eventType = eventType;
		//a single default posting method, ie #posted when post();
		postingMethods = new PostingMethodSet(new PostingMethodTemplate());
		imports = new ImportSet();
	}

	public EventType getEventType() {
		return eventType;
	}
	
	public PostingMethodSet getPostingMethods() {
		return postingMethods;
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
		else if (o instanceof GoalTemplate) {
			GoalTemplate gt = (GoalTemplate) o;
			return this.className.equals(gt.className) && 
					this.eventType.equals(gt.eventType) && 
					this.postingMethods.equals(gt.postingMethods);
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return className.hashCode() | eventType.hashCode() | postingMethods.hashCode();
	}
	
}
