import javafx.animation.PathTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;


public class simulation {
	private double x;
	private double y;
	private double initialX;
	private double initialY;
	private double angle;
	private double velocity;
	private double xVelocity;
	private double yVelocity;
	private double time;
	AnchorPane root;
	private final double GRAVITY = 9.81;
	int buttonHeights = 189;
	Sprite Banana;
	boolean modifier = false;
	
	public void setup(int angle, int speed, double x, double y, AnchorPane root2){
		this.x = x;
		this.y = y;
		initialX = this.x;
		initialY = this.y;
		this.angle = angle;
		this.velocity = speed;
		this.root = root2;
		xVelocity = velocity * Math.cos(Math.toRadians(this.angle));
		yVelocity = velocity * Math.sin(Math.toRadians(this.angle));
		time = 0;
		
	}

	public void draw(Sprite thrownBanana, Sprite m1, Sprite m2, int round, BMP bmp){
		double Toppunktx = 0;
		double Toppunkty = 6000;
		Banana = thrownBanana;
		
		for(int i = 0; i < 10000; i++){
			time += 0.01;
			thrownBanana.position.x = initialX + xVelocity * time;
			thrownBanana.position.y = initialY - (yVelocity * time - (GRAVITY / 2) * time * time);
			
			if(thrownBanana.position.y < Toppunkty) {
				Toppunkty = thrownBanana.position.y;
				Toppunktx = thrownBanana.position.x;
			}
			
if(BonusModifier.exists()) {
				
				if(thrownBanana.overlaps(BonusModifier.doublePoints)) {
				modifier = true;
				System.out.println("hits MF");
			}
				
			}


		if(bmp.collision(thrownBanana)) {
			EXPLODE(thrownBanana, bmp);
			break;
		}
			if(round%2 == 0) {
				
				if (thrownBanana.overlaps(m2)) {
					if (modifier) {
						Game.inc_m1();
					}
					Game.inc_m1();
					break; }
			}
			System.out.println(modifier);
		
			if(round%2 != 0){ 

				if (thrownBanana.overlaps(m1)) {
					if (modifier) {
						Game.inc_m2();
					}
					Game.inc_m2();
					break; }
			}
		
						
			if(thrownBanana.position.x > ViewManager.gameWidth || thrownBanana.position.y > ViewManager.gameHeight || thrownBanana.position.x < 0) {
				break;
			}
			
		}
		

		Image img = new Image("b1.png");
		ImageView banana = new ImageView(img);
		
		banana.setX(m1.position.x);
		banana.setY(m1.position.y);
		
		banana.setFitHeight(32);
		banana.setFitWidth(32);
		banana.setPreserveRatio(true); 
		
        root.getChildren().add(banana);

        Path path = new Path();
        
        MoveTo moveto= new MoveTo(initialX,initialY);
        CubicCurveTo cubiccurve= new CubicCurveTo(initialX, initialY, Toppunktx, Toppunkty - 20, thrownBanana.position.x, thrownBanana.position.y);
        
        path.getElements().add(moveto);
        path.getElements().add(cubiccurve);
        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.millis(1500));
        pathTransition.setPath(path);

        pathTransition.setNode(banana);
        pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pathTransition.play();
		

	}
	public void EXPLODE(Sprite s, BMP bmp) {
		int middleX = (int)(s.getXPos() + s.getWidth() / 2.);
		int middleY = (int)(s.getYPos() + s.getHeight() / 2.);
		
		int radius = 32;
		int limit = 4;
		
		for(int i = middleX - radius; i < middleX + radius; i++) {
			for(int j = middleY - radius; j < middleY + radius; j++) {
				int fuzzyInt = Utility.getRandomInt(-limit, limit);
				double distance = fuzzyInt + Math.sqrt(Math.pow(i-middleX,2) + Math.pow(j-middleY,2));
				
				if(distance < radius) {
					bmp.write(i, j, Color.TRANSPARENT);
				}
			}
		}
		
	}
}