package rmit.agent.generation.templates.beliefset.field;

public class IntegerRange implements FieldRange<Integer> {

	private int min;
	private int max;
	
	public IntegerRange(int min, int max) {
		this.min = min;
		this.max = max;
	}
	
	@Override
	public Integer getMinimum() {
		return min;
	}

	@Override
	public Integer getMaximum() {
		return max;
	}

	@Override
	public int getNumValues() {
		return (max+1)-min;
	}

	@Override
	public Integer getValueAtIndex(int i) {
		if (i < 0 || i > getNumValues())
			throw new IllegalArgumentException("Illegal index: " + i);
		
		return min+i;
	}

	@Override
	public int getIndexOf(Integer value) {
		if (value < min || value > max)
			throw new IllegalArgumentException("Illegal value: " + value);
		return value-min;
	}

}
