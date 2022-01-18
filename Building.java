import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;

public class Building {

	public int width, height, offset;
	
	
	public Building(int width, int height, int offset_x, int gameHeight, BMP bmp, PixelReader reader) {
		this.width = width;
		this.height = height;
		this.offset = offset_x;
		
		Color c = Color.rgb(
			Utility.getRandomInt(0,255),
			Utility.getRandomInt(0,255),
			Utility.getRandomInt(0,255)
		);
		
		
		/*if(colorPicker == 0) {
			c = Color.rgb(255, 0, 0);
		} else if(colorPicker == 1) {
			c = Color.rgb(0, 255, 0);
		} else if(colorPicker == 2) {
			c = Color.rgb(255, 0, 0);
		} else if(colorPicker == 3) {
			c = Color.rgb(255, 0, 0);
		}*/
		
		
		
		
		for (int i = offset_x; i < width + offset_x; i++) {
			for(int j = 1; j < height; j++) {
				
				bmp.write(i, gameHeight - j, c);

			}
		}
		int windowWidth = 31;
		int windowHeight = 41;
		
		int marginWidth = windowWidth;
		int marginHeight = windowHeight;
		//Hver vindue er 31x41 pixels

		
		
		
		int AMOUNT_OF_WINDOWS_WIDTH = width / (windowWidth + marginWidth);
		int remainderWidth = width % (windowWidth + marginWidth);
		marginWidth = (AMOUNT_OF_WINDOWS_WIDTH * marginWidth + remainderWidth) / (AMOUNT_OF_WINDOWS_WIDTH + 1);
		remainderWidth = width % (windowWidth + marginWidth);
		
		
		int AMOUNT_OF_WINDOWS_HEIGHT = height / (windowHeight + marginHeight);
		int remainderHeight = height % (windowHeight + marginHeight);
		marginHeight = (AMOUNT_OF_WINDOWS_HEIGHT * marginHeight + remainderHeight) / (AMOUNT_OF_WINDOWS_HEIGHT + 1);
		remainderHeight = height % (windowHeight + marginHeight);
		
		
		
		int widthOfSet = windowWidth + marginWidth;
		int heightOfSet = windowHeight + marginHeight;

		for(int i = 0; i < AMOUNT_OF_WINDOWS_HEIGHT; i++) {
			for(int j = 0; j < AMOUNT_OF_WINDOWS_WIDTH; j++) {
				
				int windowChooser = Utility.getRandomInt(0, 1); 
				
				bmp.pastePictureIntoPixels(
					remainderWidth + j * widthOfSet + offset_x, 
					gameHeight - remainderHeight - windowHeight - i * heightOfSet, 
					windowWidth, windowHeight, reader, windowChooser * windowWidth, 0
				);
			}
		}
		
	}
	
}