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
	int buttonHeights = 189;
	Sprite Banana;
	boolean modifier = false;
	
	
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

	public void draw(Sprite thrownBanana, Sprite m1, Sprite m2, int round, GraphicsContext context){
		double Toppunktx = 0;
		double Toppunkty = 6000;
		Banana = thrownBanana;
		modifier = false;
		
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
			thrownBanana.render(context);
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
        
        MoveTo moveto= new MoveTo(initialX,initialY + buttonHeights);
        CubicCurveTo cubiccurve= new CubicCurveTo(initialX, initialY + buttonHeights, Toppunktx, Toppunkty - 20, thrownBanana.position.x, thrownBanana.position.y + buttonHeights);
        
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