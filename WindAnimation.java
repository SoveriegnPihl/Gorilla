import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class WindAnimation extends Application{

	
	// Load images from the images folder
	final static Image image1 = new Image("b1.png");
	
	
	final static Image image2 = new Image ("dot.png");
	final static Image image3 = new Image ("title3.png");

	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		final ImageView image_1 = new ImageView(image1);
		final ImageView image_2 = new ImageView(image2);
		final ImageView image_3 = new ImageView(image3);
		
		Group image = new Group(image_1);
		
		image.setTranslateX(200);
		image.setTranslateY(200);
		
		Timeline t = new Timeline();
		
		t.setCycleCount(Timeline.INDEFINITE);
		
		t.getKeyFrames().add(new KeyFrame(
				Duration.millis(200),
				(ActionEvent event) -> {
					image.getChildren().setAll(image_2);
					
				}
				
				
				));
		t.getKeyFrames().add(new KeyFrame(
				Duration.millis(300),
				(ActionEvent event) -> {
					image.getChildren().setAll(image_3);
					
				}
				
				
				));
		
		t.play();
				
		primaryStage.setScene(new Scene(image,600,400));
		
		primaryStage.setTitle("hej");
		
		primaryStage.show();
		
		
	}

	public static void main(String[] args) {
		Application.launch(args);
		launch(args);
	}
	
}
