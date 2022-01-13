import java.util.ArrayList;
import javafx.scene.canvas.GraphicsContext;

public class Building {

	int width, height;
	ArrayList<Tile> tiles;
	

	public Building(int width, int height, int offset_x, int gameHeight) {
		tiles = new ArrayList<Tile>();
		this.width = width;
		this.height = height;
		
		
		int MULTIPLYER = 2;
		
		int x = 0;
		for (int i = 0; i < width*MULTIPLYER; i++) {
			for(int j = 0; j < height*MULTIPLYER; j++) {
				tiles.add(new Tile(x + offset_x, gameHeight - height * Tile.TILE_SIZE + j * Tile.TILE_SIZE / MULTIPLYER));
			}
			x += Tile.TILE_SIZE / MULTIPLYER;
		}
		
	}
	
	public void render(GraphicsContext context) {
		for (int i = 0; i < tiles.size(); i++) {
			tiles.get(i).render(context);
		}
	}
}
