import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Sprite {
	public Vector position;
	public Vector velocity;
	public Rectangle boundary;
	public Image image;
	public String imgName;
	public String name;
	public int counter = 1;
	
	public Sprite() {
		position = new Vector(0,0);
		velocity = new Vector(0,0);
		boundary = new Rectangle(0,0,0,0);
	}
	
	public void setPosition(double x, double y) {
		position.set(x, y);
	}
	
	public void setImage(String filename){
		imgName = filename;
		image = new Image(imgName);
		boundary.width = image.getWidth();
		boundary.height = image.getHeight();
	}
	
	public Rectangle getBoundary() {
		boundary.x = position.x;
		boundary.y = position.y;
		return boundary;
	}
	
	public boolean overlaps(Sprite other) {
		return this.getBoundary().overlaps(other.getBoundary());
	}
	
	public void render (GraphicsContext context) {
		context.drawImage(image, position.x, position.y);
	}
	
	public String getName() {
		return imgName;
	}

}