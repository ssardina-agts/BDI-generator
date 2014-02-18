package rmit.agent.generation.generators.random;

import java.util.Properties;

import rmit.agent.generation.GenerationUtils;
import rmit.agent.generation.generators.BeliefSetGeneratorConfig;

public class RandomBeliefSetConfig extends BeliefSetGeneratorConfig {

	private static final String KEY_MAX_KEYS = "bs.keys.max";
	private static final String KEY_MIN_KEYS = "bs.keys.min";
	private static final String KEY_MAX_VALS = "bs.vals.max";
	private static final String KEY_MIN_VALS = "bs.vals.min";

	private static final String KEY_MAX_KEYS_DOMAIN = "bs.keys.domain.max";
	private static final String KEY_MIN_KEYS_DOMAIN = "bs.keys.domain.min";
	private static final String KEY_MAX_VALS_DOMAIN = "bs.vals.domain.max";
	private static final String KEY_MIN_VALS_DOMAIN = "bs.vals.domain.min";


	private int maxKeys;
	private int minKeys;
	private int maxValues;
	private int minValues;

	private int minKeyDomainSize;
	private int maxKeyDomainSize;
	private int minValueDomainSize;
	private int maxValueDomainSize;


	public void load(Properties p) {

		maxKeys = Integer.valueOf(p.getProperty(KEY_MAX_KEYS));
		minKeys = Integer.valueOf(p.getProperty(KEY_MIN_KEYS));
		maxValues = Integer.valueOf(p.getProperty(KEY_MAX_VALS));
		minValues = Integer.valueOf(p.getProperty(KEY_MIN_VALS));

		minKeyDomainSize = Integer.valueOf(p.getProperty(KEY_MIN_KEYS_DOMAIN));
		maxKeyDomainSize = Integer.valueOf(p.getProperty(KEY_MAX_KEYS_DOMAIN));

		minValueDomainSize = Integer.valueOf(p.getProperty(KEY_MIN_VALS_DOMAIN));
		maxValueDomainSize = Integer.valueOf(p.getProperty(KEY_MAX_VALS_DOMAIN));
	}

	@Override
	public int getNumKeys() {
		return GenerationUtils.getRandomInt(minKeys, maxKeys);
	}

	@Override
	public int getNumValues() {
		return GenerationUtils.getRandomInt(minValues, maxValues);
	}

	@Override
	public int getKeyDomainSize() {
		return GenerationUtils.getRandomInt(minKeyDomainSize, maxKeyDomainSize);
	}

	@Override
	public int getValueDomainSize() {
		return GenerationUtils.getRandomInt(minValueDomainSize, maxValueDomainSize);
	}


}
