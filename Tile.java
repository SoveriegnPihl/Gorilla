import javafx.scene.canvas.GraphicsContext;

public class Tile {
	Sprite tile;
	public static final int TILE_SIZE = 32;
	
	public Tile() {
		tile = new Sprite();
		tile.setPosition(0,0);
		tile.setImage("img_building.png");
		
	}
	
	public Tile(double x, double y) {
		tile = new Sprite();
		tile.setPosition(x,y);
		tile.setImage("img_building.png");
		
	}
	
	
	public void render(GraphicsContext context) {
		tile.render(context);
	}
	
	public void setPosition(double x, double y) {
		tile.setPosition(x, y);
	}
	public double getXPos() {
		return tile.position.x;
	}
	public double getYPos() {
		return tile.position.y;
	}
	public void setXPos(double x) {
		tile.position.x = x;
	}
	public void setYPos(double y) {
		tile.position.y = y;
	}
	
	public void setPos(double x, double y) {
		tile.position.x = x;
		tile.position.y = y;
	}
	
	public Sprite getSprite() {
		return this.tile;
	}
	
	public void destroy() {
		tile.setPosition(-80, -80);
	}
	
}
