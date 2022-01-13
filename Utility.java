import java.util.concurrent.ThreadLocalRandom;
public class Utility {
	
	
	public Utility() {
		
	}
	
	public static int getRandomInt(int min, int max) {
		
		return ThreadLocalRandom.current().nextInt(min, max + 1);
	}
	
	public static boolean isInt(String message) {
        try {
            Integer.parseInt(message);
            return true;
            
        }catch(NumberFormatException e) {
       
        }
        
        return false;
	}
	
}
