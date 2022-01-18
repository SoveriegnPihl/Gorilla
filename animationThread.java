import javafx.animation.AnimationTimer;
import javafx.scene.layout.GridPane;

public class animationThread extends Thread{
	GridPane gridPane1, gridPane2;
	double windSpeed;
	
	public animationThread(GridPane g1, GridPane g2) {
		this.gridPane1 = g1;
		this.gridPane2 = g2;
	}
	
	public void setWind(double windSpeed) {
		this.windSpeed = windSpeed;
	}
	
	@Override
	public void run() {
		AnimationTimer gameTimer = new AnimationTimer() {
			@Override
			public void handle(long now) {				
				gridPane1.setLayoutY(gridPane1.getLayoutY() + 0.5);
				gridPane2.setLayoutY(gridPane2.getLayoutY() + 0.5);
				gridPane1.setLayoutX(gridPane1.getLayoutX() + windSpeed);
				gridPane2.setLayoutX(gridPane2.getLayoutX() + windSpeed);
				
				if (gridPane1.getLayoutY() >= 256) {
					gridPane1.setLayoutY(-256);
				}
				
				if (gridPane2.getLayoutY() >= 256) {
					gridPane2.setLayoutY(-256);
				}
				if (gridPane1.getLayoutX() >= 256) {
					gridPane1.setLayoutX(-256);
				}
				
				if (gridPane2.getLayoutX() >= 256) {
					gridPane2.setLayoutX(-256);
				}
			}
		};
		gameTimer.start();
	}

}
