import java.util.concurrent.ThreadLocalRandom;
public class Utility {
	
	
	public Utility() {
		
	}
	
	public static int getRandomInt(int min, int max) {
		
		return ThreadLocalRandom.current().nextInt(min, max + 1);
	}
	
	public boolean isInt(String message) {
        try {
            int intForTest = Integer.parseInt(message);
            return true;
            
        }catch(NumberFormatException e) {
       
        }
        
        return false;
	}
	
}