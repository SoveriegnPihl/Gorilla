import javafx.scene.canvas.GraphicsContext;

public class BonusModifier {
	
	static Sprite doublePoints;
	static boolean hej = false;
	
	public BonusModifier(int gameWidth, int gameHeight) {
		
	
		doublePoints = new Sprite();
		int xCoordinate = Utility.getRandomInt(100,gameWidth-100);
		int yCoordinate = Utility.getRandomInt(200,gameHeight-200);
	
		doublePoints.position.set(xCoordinate, yCoordinate);
		doublePoints.setImage("2x.png");
		hej = true;
		
	}
	
	public void render (GraphicsContext context) {
		doublePoints.render(context);
	}
	
	public static boolean exists() {
	
		return hej;
	}
}