import javafx.scene.image.WritableImage;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.PixelReader;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritablePixelFormat;
import javafx.scene.paint.Color;


public class BMP {
	private WritableImage wImage;
	private PixelWriter pWriter;
	private PixelReader pReader;
	
	public BMP(int gameWidth, int gameHeight) {

		
		//Creating an image 
	    wImage = new WritableImage(gameWidth, gameHeight);  
	    
	    pWriter = wImage.getPixelWriter();
	    pReader = wImage.getPixelReader();
	    

	}
	
	
	public void write(int x, int y, Color c) {
		if(x < 0 || x >= wImage.getWidth() || y < 0 || y >= wImage.getHeight()) {
			return;
		}
		pWriter.setColor(x, y, c);
	}

	public Color read(int x, int y) {
		if(x < 0 || x >= wImage.getWidth() || y < 0 || y >= wImage.getHeight()) {
			return Color.TRANSPARENT;
		}
		return pReader.getColor(x, y);
	}
	
	public void render(GraphicsContext context) {
	    context.drawImage(wImage, 0, 0);

	}
	
	public boolean collision(Sprite s) {
		int width = (int)s.getWidth();
		int height = (int)s.getWidth();
		int x = (int)s.getXPos();
		int y = (int)s.getYPos();
		
		for(int i = x; i < x + width; i++) {
			for(int j = y; j < y + height; j++) {
				if(!read(i,j).equals(Color.TRANSPARENT)) {
					return true;
					
				}
			}
		}
		return false;

	}
	
	public void pastePictureIntoPixels(int dstx, int dsty, int width, int height, PixelReader reader, int srcx, int srcy) {
		this.pWriter.setPixels(dstx, dsty, width, height, reader, srcx, srcy);
	}
	
	
}