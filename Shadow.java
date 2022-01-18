import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.scene.*;
import javafx.scene.effect.*;
import javafx.scene.image.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Shadow extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Image image = new Image(
                "monkey_throw2.png"
        );

        ImageView imageView = new ImageView(image);
        imageView.setClip(new ImageView(image));

        ColorAdjust monochrome = new ColorAdjust();
        monochrome.setSaturation(0.5);
        

        Blend blush = new Blend(
                BlendMode.MULTIPLY,
                monochrome,
                new ColorInput(
                        0,
                        0 ,
                        imageView.getImage().getWidth(),
                        imageView.getImage().getHeight(),     
                        Color.RED
                )
        );
    
       imageView.setEffect(blush);
       
     /*   imageView.effectProperty().bind(
                Bindings
                    .when(imageView.hoverProperty())
                        .then((Effect) blush)
                        .otherwise((Effect) null)
        );*/

        imageView.setCache(true);
        imageView.setCacheHint(CacheHint.SPEED);

        stage.setScene(new Scene(new Group(imageView), Color.AQUA));
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch();
    }
}