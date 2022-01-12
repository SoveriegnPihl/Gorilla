import java.io.FileNotFoundException;

import javafx.animation.PathTransition;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
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
	BorderPane root;
	private final double GRAVITY = 9.81;
	
	public void setup(int angle, int speed, double x, double y, BorderPane root){
		this.x = x;
		this.y = y;
		initialX = this.x;
		initialY = this.y;
		this.angle = angle;
		this.velocity = speed;
		this.root = root;
		xVelocity = velocity * Math.cos(Math.toRadians(this.angle));
		yVelocity = velocity * Math.sin(Math.toRadians(this.angle));
		time = 0;
		
	}

	public void draw(Sprite thrownBanana, GraphicsContext context, Sprite m1, Sprite m2, int round){
		double Toppunktx = 0;
		double Toppunkty = 6000;

    	ViewManager manager = null;
    	
		try {
			manager = new ViewManager();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		
		for(int i = 0; i < 10000; i++){
			time += 0.1;
			thrownBanana.position.x = initialX + xVelocity * time;
			thrownBanana.position.y = initialY - (yVelocity * time - (GRAVITY / 2) * time * time);
			
		
			
			if(thrownBanana.position.y < Toppunkty) {
				Toppunkty = thrownBanana.position.y;
				Toppunktx = thrownBanana.position.x;
			}
			
			if(round%2 == 0) {
				if (thrownBanana.overlaps(m2)) {
					Game.inc_m1();
					break; }
			}
			if(round%2 != 0){ 
				if (thrownBanana.overlaps(m1)) {	
					Game.inc_m2();
					break; }
			}
			
			if(thrownBanana.position.x > manager.getWidth() || thrownBanana.position.y > manager.getHeight() || thrownBanana.position.x < 0) {
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
        
        MoveTo moveto= new MoveTo(initialX,initialY + 113);
        CubicCurveTo cubiccurve= new CubicCurveTo(initialX, initialY + 113, Toppunktx, Toppunkty - 20, thrownBanana.position.x, thrownBanana.position.y + 113);
        
        path.getElements().add(moveto);
        path.getElements().add(cubiccurve);
        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.millis(1500));
        pathTransition.setPath(path);

        pathTransition.setNode(banana);
        pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pathTransition.play();
		

	}
}