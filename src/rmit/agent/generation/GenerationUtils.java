package rmit.agent.generation;

public class GenerationUtils {

	public static String format(int num) {
		return String.format("%03d", num);
	}
	
	public static int getRandomInt(int min, int max) {
		return (int) ((Math.random() * ((max+1)-min)) + min);
	}
	
}
