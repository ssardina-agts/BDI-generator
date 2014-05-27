package rmit.agent.generation.generators.random;

import java.util.Properties;

import rmit.agent.generation.utils.Utils;
import rmit.agent.generation.generators.basic.BasicBeliefSetGenerator;

public class RandomBeliefSetGenerator extends BasicBeliefSetGenerator {

	public static final String KEY_MAX_KEYS = "bs.keys.max";
	public static final String KEY_MIN_KEYS = "bs.keys.min";
	public static final String KEY_MAX_VALS = "bs.vals.max";
	public static final String KEY_MIN_VALS = "bs.vals.min";

	public static final String KEY_MAX_KEYS_DOMAIN = "bs.keys.domain.max";
	public static final String KEY_MIN_KEYS_DOMAIN = "bs.keys.domain.min";
	public static final String KEY_MAX_VALS_DOMAIN = "bs.vals.domain.max";
	public static final String KEY_MIN_VALS_DOMAIN = "bs.vals.domain.min";
	
	private int maxKeys;
	private int minKeys;
	private int maxValues;
	private int minValues;

	private int minKeyDomainSize;
	private int maxKeyDomainSize;
	private int minValueDomainSize;
	private int maxValueDomainSize;

	public RandomBeliefSetGenerator(Properties properties) {
		super(properties);	
	}

	protected void load(Properties properties) {
		maxKeys = Integer.valueOf(properties.getProperty(KEY_MAX_KEYS));
		minKeys = Integer.valueOf(properties.getProperty(KEY_MIN_KEYS));
		maxValues = Integer.valueOf(properties.getProperty(KEY_MAX_VALS));
		minValues = Integer.valueOf(properties.getProperty(KEY_MIN_VALS));

		minKeyDomainSize = Integer.valueOf(properties.getProperty(KEY_MIN_KEYS_DOMAIN));
		maxKeyDomainSize = Integer.valueOf(properties.getProperty(KEY_MAX_KEYS_DOMAIN));

		minValueDomainSize = Integer.valueOf(properties.getProperty(KEY_MIN_VALS_DOMAIN));
		maxValueDomainSize = Integer.valueOf(properties.getProperty(KEY_MAX_VALS_DOMAIN));
	}

	@Override
	public int getNumKeys() {
		return Utils.getRandomInt(minKeys, maxKeys);
	}

	@Override
	public int getNumValues() {
		return Utils.getRandomInt(minValues, maxValues);
	}

	@Override
	public int getKeyDomainSize() {
		return Utils.getRandomInt(minKeyDomainSize, maxKeyDomainSize);
	}

	@Override
	public int getValueDomainSize() {
		return Utils.getRandomInt(minValueDomainSize, maxValueDomainSize);
	}


}
