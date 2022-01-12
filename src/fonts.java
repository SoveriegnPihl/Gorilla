import java.io.FileNotFoundException;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class fonts extends Application {
   public void start(Stage stage) throws FileNotFoundException {
      //Creating a text object
      Text text = new Text(30.0, 75.0, "hehj");
      //Loading a font from local file system
      Font font = Font.loadFont("/test/src/fontstest.ttf", 45);
      //Setting the font
      text.setFont(font);
      //Setting color of the text
      text.setFill(Color.BROWN);
      text.setStroke(Color.BLUEVIOLET);
      text.setStrokeWidth(0.5);
      //Creating the inner shadow effect
      //Creating the drop shadow effect
      DropShadow shadow = new DropShadow();
      shadow.setOffsetY(5.0);
      //Setting the effect to the text
      text.setEffect(shadow);
      //Setting the stage
      Group root = new Group(text);
      Scene scene = new Scene(root, 595, 150, Color.BEIGE);
      stage.setTitle("Custom Font");
      stage.setScene(scene);
      stage.show();
   }
   public static void main(String args[]){
      launch(args);
   }
}