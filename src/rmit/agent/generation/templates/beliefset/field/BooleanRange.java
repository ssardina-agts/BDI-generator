package rmit.agent.generation.templates.beliefset.field;

public class BooleanRange implements FieldRange<Boolean> {

	@Override
	public Boolean getMinimum() {
		return true;
	}

	@Override
	public Boolean getMaximum() {
		return false;
	}

	@Override
	public int getNumValues() {
		return 2;
	}

	@Override
	public Boolean getValueAtIndex(int i) {
		if (i == 0)
			return true;
		if (i == 1)
			return false;
		
		throw new IllegalArgumentException("Illegal index: " + i);
	}

	@Override
	public int getIndexOf(Boolean value) {
		return value ? 0 : 1;
	}

}
