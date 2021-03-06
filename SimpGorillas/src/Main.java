import java.io.IOException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application {
	int angleVal, velocityVal, pointsMonkey1, pointsMonkey2, gameHeight,gameWidth;
	int rounds = 1;
	static int pointsM1, pointsM2;
	static int[] dimensions = new int[2];
	Scene mainScene;
	Stage stage;
	
	@FXML
	TextField height;
	
	@FXML 
	TextField width;
	
	@FXML 
	Label pixels;
	
	public simulation graph = new simulation();
	
	@Override
	public void start(Stage stage) {
	
		try {
		Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("Scene1.fxml"));
		Scene scene = new Scene(root);
		
		stage.setScene(scene);
		stage.show();
	} catch (Exception e) {
		e.printStackTrace();
		}
	}

	public void startB(ActionEvent event) throws IOException {
		
		if(is_int(width.getText()) && is_int(height.getText())) {
			gameWidth = Integer.parseInt(width.getText());
			gameHeight =  Integer.parseInt(height.getText());
		}
		
		
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
	    
		//Start indstillinger
	    BorderPane root = new BorderPane();
	    Scene mainScene = new Scene(root);
		Canvas canvas = new Canvas(gameWidth, gameHeight);
		GraphicsContext context = canvas.getGraphicsContext2D();
		stage.setTitle("Simp Gorillas");
	    
		//elementer der skal indsættes
		//baggrund
		root.setCenter(canvas);
		context.setFill(Color.BLUE);
		context.fillRect(0, 0, gameWidth, gameHeight);
		
		//monkey 1
		Sprite monkey1 = new Sprite();
		monkey1.position.set(8, gameHeight - 60);
		monkey1.setImage("monkey_throw.png");
				
		//monkey 2
		Sprite monkey2 = new Sprite();
		monkey2.position.set(gameWidth - 90, gameHeight - 60);
		monkey2.setImage("monkey_throw2.png");
		
		//banekurve dot
		Sprite dot = new Sprite();
		dot.position.set(8, gameHeight-80);
		dot.setImage("dot.png");
		
		//vinkel og fart vindue
        int width_of_field = 100;
		TextField angle = new TextField();
        TextField speed = new TextField();
        angle.setPrefWidth(width_of_field);
        angle.setMaxWidth(width_of_field);
        speed.setPrefWidth(width_of_field);
        speed.setMaxWidth(width_of_field);
        angle.setPromptText("VINKEL");
        speed.setPromptText("SPEED");
        
        //button event
        Button button = new Button("Throw Banana");
        button.setOnAction(e -> {
            if(is_int(angle.getText())) {
                angleVal = Integer.parseInt(angle.getText());
            }
            if(is_int(speed.getText())) {
                velocityVal = Integer.parseInt(speed.getText());
            }
            
            //clear baggrund
            context.setFill(Color.BLUE);
    		context.fillRect(0, 0, gameWidth, gameWidth);
    		
    		//runder
			context.setFill(Color.YELLOW);
			context.setFont(new Font("Arial", 36));
			if (rounds%2 == 0) {
				context.fillText("Runde: " + rounds, gameWidth/2 - 70, 35);
				rounds++;
			}
			else {
				context.fillText("Runde: " + rounds, gameWidth/2 - 70, 35);
				rounds++;
			}
    		
			//tegner aber
            monkey1.render(context);
			monkey2.render(context);
			
			//point tæller
			if (rounds%2 == 0) {
				graph.setup(angleVal, velocityVal, 6, gameHeight - 50);
				graph.draw(dot, context, monkey1, monkey2, rounds);
			}else {
				graph.setup(180 - angleVal, velocityVal, gameWidth - 6, gameHeight - 50 );
				graph.draw(dot, context, monkey1, monkey2, rounds);
			}
			
			//point tekst
			context.setFill(Color.YELLOW);
			context.setFont(new Font("Arial", 36));
			context.fillText(""+pointsM1, 10, 35);
			context.fillText(""+pointsM2, gameWidth-37, 35);
        });
        
        //tegner 
        //vinkel og fart box
        VBox get_numbers = new VBox();
        get_numbers.setPadding(new Insets(20, 20, 20, 20));
        get_numbers.setBackground(new Background(new BackgroundFill(Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        get_numbers.getChildren().addAll(angle, speed, button);
        root.setTop(get_numbers);
        
        //tegner pointscore
		context.setFill(Color.YELLOW);
		context.setFont(new Font("Arial", 36));
		context.fillText("0", 10, 35);
		context.fillText("0", gameWidth-37, 35);
		
		//tegner aber
        monkey1.render(context);
		monkey2.render(context);
		
		if(!is_int(width.getText())||!is_int(height.getText())) {
			pixels.setText("skriv nu det i pixels forhelvede paul");
		} else {
		stage.setScene(mainScene);
		stage.show();
		}
		
		
		

	}
	
   public boolean is_int(String message) {
        
        try {
            int age = Integer.parseInt(message);
 
            return true;
        }catch(NumberFormatException e) {
       }
    
        return false;
    
   
    }
    
    public static void inc_m1() {
    	pointsM1++;
    }
    
    public static void inc_m2() {
    	pointsM2++;
    }
		
		
	public static void main(String[] args) {
		launch(args);
	}
} 