package rmit.agent.generation.templates.beliefset.field;

public class DoubleRange implements FieldRange<Double> {

	private double min;
	private double max;
	private double[] values;
	
	public DoubleRange(double min, double max, double res) {
		this.min = min;
		this.max = max;
		values = new double[(int) ((max-min)/res)];
		for (int i = 0; i < values.length; i++) {
			values[i] = i * (res);
		}
	}
	
	@Override
	public Double getMinimum() {
		return min;
	}

	@Override
	public Double getMaximum() {
		return max;
	}

	@Override
	public int getNumValues() {
		return values.length;
	}

	@Override
	public Double getValueAtIndex(int i) {
		if (i < 0 || i > values.length)
			throw new IllegalArgumentException("Illegal index: " + i);
		return values[i];
	}

	@Override
	public int getIndexOf(Double value) {
		if (value < min || value > max)
			throw new IllegalArgumentException("Illegal value: " + value);
		
		for (int i = 0; i < values.length-1; i++) {
			if (value < values[i+1])
				return i;
		}
		return values.length-1;
	}

}
