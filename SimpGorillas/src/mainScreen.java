import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class mainScreen extends Application {
	
	int angleVal, velocityVal, pointsMonkey1, pointsMonkey2;
	int rounds = 1;
	static int pointsM1, pointsM2;
	static int[] dimensions = new int[2];
	Scene mainScene;
	Stage mainStage2;
	
	@FXML
	TextField nameTextField;
	
	@FXML 
	private TextField nameH;
	
	public simulation graph = new simulation();
	
	public static void main(String[] args) {
        launch(args);
    }
	@FXML
	public void startB(ActionEvent event) throws IOException {
	 // root = FXMLLoader.load(getClass().getResource("Scene1.fxml"));
	
	mainStage2.setScene(mainScene);
		
	String username = nameTextField.getText();
	String hojde = nameH.getText();
	}
	 
	@Override
	public void start (Stage mainStage) throws IOException {

		
		Scanner scanner = new Scanner(System.in);  
	    System.out.println("Indtast dimensioner:  ");
	    
	    
	    dimensions[0] = scanner.nextInt();
	    dimensions[1] = scanner.nextInt();
		
	    
	    Button but = new Button("Click me");
	    
	    
		mainStage.setTitle("Simp Gorillas");
		BorderPane root = new BorderPane();
		
	    mainScene = new Scene(root);
		mainStage.setScene(mainScene);
		
		root.getChildren().add(but);
		Canvas canvas = new Canvas(getWidth(), getHeight());
		GraphicsContext context = canvas.getGraphicsContext2D();
		
		//baggrund
		root.setCenter(canvas);
		context.setFill(Color.BLUE);
		context.fillRect(0, 0, getWidth(), getHeight());
		
		//monkey 1
		Sprite monkey1 = new Sprite();
		monkey1.position.set(8, getHeight() - 60);
		monkey1.setImage("monkey_throw.png");
				
		//monkey 2
		Sprite monkey2 = new Sprite();
		monkey2.position.set(getWidth() - 90, getHeight() - 60);
		monkey2.setImage("monkey_throw2.png");
		
		//dot
		Sprite dot = new Sprite();
		dot.position.set(8, getHeight()-80);
		dot.setImage("dot.png");
		
		
		//vinkel vindue
        int width_of_field = 100;
		TextField angle = new TextField();
        TextField speed = new TextField();
        angle.setPrefWidth(width_of_field);
        angle.setMaxWidth(width_of_field);
        speed.setPrefWidth(width_of_field);
        speed.setMaxWidth(width_of_field);
        angle.setPromptText("VINKEL");
        speed.setPromptText("SPEED");
        
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
    		context.fillRect(0, 0, getWidth(), getWidth());
    		
    		//runder
			context.setFill(Color.YELLOW);
			context.setFont(new Font("Arial", 36));
			if (rounds%2 == 0) {
				context.fillText("Runde: " + rounds, getWidth()/2 - 70, 35);
				rounds++;
			}
			else {
				context.fillText("Runde: " + rounds, getWidth()/2 - 70, 35);
				rounds++;
			}
    		
			//aber
            monkey1.render(context);
			monkey2.render(context);
			
			if (rounds%2 == 0) {
				graph.setup(angleVal, velocityVal, 6, getHeight() - 50);
				graph.draw(dot, context, monkey1, monkey2, rounds);
			}else {
				graph.setup(180 - angleVal, velocityVal, getWidth() - 6, getHeight() - 50 );
				graph.draw(dot, context, monkey1, monkey2, rounds);
			}
			context.setFill(Color.YELLOW);
			context.setFont(new Font("Arial", 36));
			context.fillText(""+pointsM1, 10, 35);
			context.fillText(""+pointsM2, getWidth()-37, 35);
            
        });
        

 
        VBox get_numbers = new VBox();
        get_numbers.setPadding(new Insets(20, 20, 20, 20));
        get_numbers.setBackground(new Background(new BackgroundFill(Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        get_numbers.getChildren().addAll(angle, speed, button);
        root.setTop(get_numbers);
        
		context.setFill(Color.YELLOW);
		context.setFont(new Font("Arial", 36));
		context.fillText("0", 10, 35);
		context.fillText("0", getWidth()-37, 35);
		
        monkey1.render(context);
		monkey2.render(context);
		
		mainStage.setScene(mainScene);
		mainStage.show();
		
		mainStage2 = mainStage;
		
		
	}
	
    private boolean is_int(String message) {
        
        try {
            int age = Integer.parseInt(message);
            System.out.println("Number: " + message );
            return true;
        }catch(NumberFormatException e) {
            System.out.println("Error " + message + " is not a number");
        } 
    
        return false;
    
    }
    public static int getWidth() {
    	return dimensions[0];
    }
    public static int getHeight() {
    	return dimensions[1];
    }
    
    public static void inc_m1() {
    	pointsM1++;
    }
    
    public static void inc_m2() {
    	pointsM2++;
    }
}