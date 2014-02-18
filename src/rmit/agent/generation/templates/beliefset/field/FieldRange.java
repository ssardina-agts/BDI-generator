package rmit.agent.generation.templates.beliefset.field;


public interface FieldRange<S> {
	
	public S getMinimum();

	public S getMaximum();

	public int getNumValues();
	
	public S getValueAtIndex(int i);
	
	public int getIndexOf(S value);
	
}