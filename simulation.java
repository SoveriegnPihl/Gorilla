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

	public void draw(Sprite s, GraphicsContext context, Sprite m1, Sprite m2, int round, Building[] buildings){
		for(int i = 0; i < 1000; i++){
			time += 0.1;
			s.position.x = initialX + xVelocity * time;
			s.position.y = initialY - (yVelocity * time - (GRAVITY / 2) * time * time);
			
			if(round %2 == 0) {
				if(collionBuilding(s,buildings)) {
					break;
				}
				
				else if (s.overlaps(m2)) {
					ViewManager.inc_m1();
					break;
				}
			}
			
			else if (round % 2 != 0) { 
				if(collionBuilding(s,buildings)) {
					break;
				}
				
				else if (s.overlaps(m1)) {	
					
					
					ViewManager.inc_m2();
					break;
				}
			}
			
			
			
			s.render(context);
		}
	}
	
	
	
	public boolean collionBuilding(Sprite s, Building[] buildings) {
		
		for(int j = 0; j < Game.AMOUNT_OF_BUILDINGS; j++) {
			for(Tile var : buildings[j].tiles) {
				if(s.overlaps(var.getSprite())) {
					var.destroy();
					return true;
				}	
			}
		}
		return false;
		
	}
	
}