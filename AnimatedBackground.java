import java.util.Random;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

public class AnimatedBackground extends Application
{
    // #########################################################################################################
    //                                                                                                      MAIN
    // #########################################################################################################
    
    public static void main(String[] args) 
    {
        Application.launch(args);
    }

    // #########################################################################################################
    //                                                                                                INSTÂNCIAS
    // #########################################################################################################
    
    private StackPane root;
    private StackPane circles;
    private Rectangle rect_background;
    private Scene cenario;
    
    // UI
    
    private VBox lay_box_controls;
    
    private Label lab_test;
    private TextArea texA_test;
    private Button but_test;
    
    // #########################################################################################################
    //                                                                                                 INÍCIO FX
    // #########################################################################################################
    
    @Override public void start(Stage stage) throws Exception 
    {
        this.confFX();
        
        cenario = new Scene(this.root , 640 , 480);
        
        this.rect_background.widthProperty().bind(this.cenario.widthProperty());
        this.rect_background.heightProperty().bind(this.cenario.heightProperty());

        stage.setScene(cenario);
        stage.setTitle("Meu programa JavaFX - R.D.S.");
        stage.show();
    }
    
    protected void confFX()
    {
        this.root = new StackPane();
        
        this.circles = new StackPane();
        this.circles.setStyle("-fx-border-color:blue");
        
        // Initiate the circles and all animation stuff.
        for(int cont = 0 ; cont < 15 ; cont++)
        {
            Circle circle = new Circle();
            circle.setFill(Color.WHITE);
            circle.setEffect(new GaussianBlur(Math.random() * 8 + 2));
            circle.setOpacity(Math.random());
            circle.setRadius(20);
            
            this.circles.getChildren().add(circle);
            
            double randScale = (Math.random() * 4) + 1;
            
            KeyValue kValueX = new KeyValue(circle.scaleXProperty() , randScale);
            KeyValue kValueY = new KeyValue(circle.scaleYProperty() , randScale);
            KeyFrame kFrame = new KeyFrame(Duration.millis(5000 + (Math.random() * 5000)) , kValueX , kValueY);
            
            Timeline timeL = new Timeline();
            timeL.getKeyFrames().add(kFrame);
            timeL.setAutoReverse(true);
            timeL.setCycleCount(Animation.INDEFINITE);
            timeL.play();
        }

        this.rect_background = new Rectangle();

        this.root.getChildren().add(this.rect_background);
        this.root.getChildren().add(this.circles);
        
        // UI
        
        this.lay_box_controls = new VBox();
        this.lay_box_controls.setSpacing(20);
        this.lay_box_controls.setAlignment(Pos.CENTER);
        this.lay_box_controls.setPadding(new Insets(0 , 90 , 0 , 90));
        
        this.but_test = new Button("CHANGE POSITIONS");
        this.but_test.setAlignment(Pos.CENTER);
        
        // Pressing the button, the circles will change to random positions.
        this.but_test.setOnAction(new EventHandler<ActionEvent>() 
        {
            @Override public void handle(ActionEvent e) 
            {
                Random random = new Random();
                
                for(Node circle : circles.getChildren())
                {
                    // The layout behaves in a very unexpected way.
                    circle.setTranslateX( random.nextInt((int) cenario.getWidth()) - cenario.getWidth() / 2 );
                    circle.setTranslateY( random.nextInt((int) cenario.getHeight()) - cenario.getHeight() / 2 );
                }
            }
        });
        
        this.texA_test = new TextArea();
        this.texA_test.setText("This is just a test.");
        
        this.lab_test = new Label("This is just a label.");
        this.lab_test.setTextFill(Color.WHITE);
        this.lab_test.setFont(new Font(32));
        
        this.lay_box_controls.getChildren().add(this.lab_test);
        this.lay_box_controls.getChildren().add(this.texA_test);
        this.lay_box_controls.getChildren().add(this.but_test);
        
        this.root.getChildren().add(this.lay_box_controls);
    }
}