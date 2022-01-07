import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

import javafx.scene.canvas.GraphicsContext;

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
	private final double GRAVITY = 9.81;

	public void setup(int angle, int speed, double x, double y){
		this.x = x;
		this.y = y;
		initialX = this.x;
		initialY = this.y;
		this.angle = angle;
		this.velocity = speed;
		xVelocity = velocity * Math.cos(Math.toRadians(this.angle));
		yVelocity = velocity * Math.sin(Math.toRadians(this.angle));
		time = 0;
	}

	public void draw(Sprite s, GraphicsContext context, Sprite m1, Sprite m2, int round){
		for(int i = 0; i < 1000; i++){
			time += 0.1;
			s.position.x = initialX + xVelocity * time;
			s.position.y = initialY - (yVelocity * time - (GRAVITY / 2) * time * time);
			
			if(round %2 == 0) {
				//if(s.position.x < m2.position.x + mainScreen.getWidth() / 50 && 
				//		s.position.x > m2.position.x - mainScreen.getWidth() / 50 &&
				//		s.position.y < m2.position.y + mainScreen.getHeight() / 50 && 
				//		s.position.y > m2.position.y - mainScreen.getHeight() / 50) {
				if (s.overlaps(m2)) {
					
					Main.inc_m1();
					break;
				}
			}
			
			else if (round % 2 != 0) { 
				//if(s.position.x < m1.position.x + mainScreen.getWidth() / 50 &&
				//		s.position.x > m1.position.x - mainScreen.getWidth() / 50 &&
				//		s.position.y < m1.position.y + mainScreen.getHeight() / 50 && 
				//		s.position.y > m1.position.y - mainScreen.getHeight() / 50){
				if (s.overlaps(m1)) {	
					Main.inc_m2();
					break;
				}
			}
			
			s.render(context);
		}
	}
}