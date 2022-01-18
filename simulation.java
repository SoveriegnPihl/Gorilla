import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.animation.RotateTransition;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.transform.Rotate;
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
	int animationDuration = 1500;
	boolean animationRunning = true;
	sleepThread animationThread;
	double wind;
	
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

	public void draw(Sprite thrownBanana, Sprite m1, Sprite m2, int round, BMP bmp, GraphicsContext context){
		animationRunning = true;
		double Toppunktx = 0;
		double Toppunkty = 6000;
		Sprite dot = new Sprite();
		
		Image img = new Image("b1.png");
		ImageView banana = new ImageView(img);
		
		banana.setX(m1.position.x);
		banana.setY(m1.position.y);
		banana.setFitHeight(32);
		banana.setFitWidth(32);
		banana.setPreserveRatio(true); 
		
        root.getChildren().add(banana);
		
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
			thrownBanana.render(context);
			
		}
		

    	RotateTransition rotate = new RotateTransition();  
	    rotate.setAxis(Rotate.Z_AXIS);  
	    rotate.setByAngle(720);  
	    rotate.setDuration(Duration.millis(animationDuration));
	    rotate.setInterpolator(Interpolator.LINEAR);
	    rotate.setCycleCount(1);

		

        Path path = new Path();
  
        angle = normalizeAngle(angle);
        MoveTo moveto= new MoveTo(initialX,initialY + buttonHeights);
        CubicCurveTo cubiccurve= new CubicCurveTo(initialX, initialY + buttonHeights, Toppunktx, getTrueTop(Toppunkty),thrownBanana.position.x, thrownBanana.position.y + buttonHeights);
        
        
        path.getElements().add(moveto);
        path.getElements().add(cubiccurve);
    	PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.millis(animationDuration));
        pathTransition.setInterpolator(Interpolator.LINEAR);
        pathTransition.setPath(path);
        
        pathTransition.setNode(banana);
        rotate.setNode(banana);  
        
      //  animationThread = new sleepThread(pathTransition, rotate);
       // animationThread.start();
        
        pathTransition.play();
        rotate.play();
       
        pathTransition.setOnFinished(event ->{
        	animationRunning = false;
        	System.out.println("done");        	
        });

             
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
	public double normalizeAngle(double angle) {
		if(angle >= 90) {
			angle = 180 - angle;	
		}
				
		return angle;
	}
	
	public double getTrueTop(double yVal) {
		double value = yVal;
		
		if(angle >= 80) {
			if(velocity >= 120) {
				value -= velocity*2;}
			if(velocity >= 100) {
				value -=velocity*2.5;}
			if(velocity >= 80) {
				value -= velocity*3;}
			if(velocity <= 80) {
				value -= velocity;}
		}
		else if(angle >= 70 && angle < 80) {
			if(velocity >= 120) {
				value -= velocity*1.2;}
			if(velocity >= 100) {
				value -=velocity*1.7;}
			if(velocity >= 90) {
				value -=velocity*1.5;}
			if(velocity >= 80) {
				value -= velocity*1.5;}
			if(velocity <= 80 && velocity >55) {
				value -= velocity*1.4;}
			if(velocity <= 55) {
				value -= velocity*0.7;;}
		}
		else if(angle >= 60 && angle < 70){
			if(velocity >= 135) {
				value -=velocity*1.4;}
			if(velocity >= 120) {
				value -=velocity*2.7;}
			if(velocity >= 90) {
				value -=velocity;}
			if(velocity >= 80) {
				value -= velocity*1.4;}
			if(velocity < 80 && velocity >55) {
				value -= velocity*1.2;}
			if(velocity <= 55) {
				value -= velocity*0.5;}
			
		}
		else if(angle >= 50 && angle < 60) {
			if(velocity >= 135) {
				value -=velocity;}
			if(velocity >= 120) {
				value -=velocity*1.3;}
			if(velocity >= 100) {
				value -=velocity*1.3;}
			if(velocity >= 80) {
				value -= velocity*1.1;}
			if(velocity < 80 && velocity >55) {
				value -= velocity*0.7;}
			if(velocity <= 55) {
				value += velocity*0.5;}
		}
		else if(angle >= 40 && angle < 50) {
			if(velocity >= 135) {
				value -=velocity*0.7;}
			if(velocity >= 120) {
				value -=velocity*0.9;}
			if(velocity >= 100) {
				value -=velocity*0.7;}
			if(velocity >= 80) {
				value -= velocity*0.5;}
			if(velocity < 80 && velocity >65) {
				value -= velocity*0.3;}
			if(velocity <= 65) {
				value += velocity*1.5;}
		}
		else if(angle >= 30 && angle < 40) {
			if(velocity >= 135) {
				value -=velocity*0.6;}
			if(velocity >= 110) {
				value -=velocity*0.8;}
			if(velocity >= 80) {
				value += velocity*0.1;}
			if(velocity < 80 && velocity >65) {
				value += velocity*0.4;}
			if(velocity <= 65) {
				value += velocity*1.5;}
		}
		else if(angle >= 20 && angle < 30) {
			if(velocity >= 135) {
				value -=velocity*0.2;}
			if(velocity >= 110) {
				value -=velocity*0.2;;}
			if(velocity >= 80) {
				value += velocity*0.4;;}
			if(velocity < 80 && velocity >65) {
				value += velocity*0.8;}
			if(velocity <= 65) {
				value += velocity*1.9;}
		}
		else {
			if(velocity >= 135) {
				value -=velocity*0.8;}
			if(velocity >= 80) {
				value += velocity*1.2;}
			if(velocity < 80 && velocity >65) {
				value += velocity*1.2;}
			if(velocity <= 65) {
				value += velocity*2.4;}
			
		}
		System.out.println(value);
		return value;
	}

	public void setWind(double wind) {
		this.wind = wind;
		
	}



}