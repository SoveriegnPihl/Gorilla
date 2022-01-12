import java.io.FileNotFoundException;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Game {
	
	int angleVal, velocityVal, pointsMonkey1, pointsMonkey2;
	int rounds = 1;
	Scene mainScene;
	static int pointsM1, pointsM2;
	public simulation graph = new simulation();
	Stage primaryStage;
	Stage gamingStage = new Stage();
	public Main game = new Main();
	
	public Scene spil (int gameWidth, int gameHeight) {
		
		
		//Start indstillinger

		BorderPane root = new BorderPane();
	    mainScene = new Scene(root);
		Canvas canvas = new Canvas(gameWidth, gameHeight);
		GraphicsContext context = canvas.getGraphicsContext2D();
		gamingStage.setTitle("Simp Gorillas");
	    
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
        
        GorillaButton back = new GorillaButton("BACK");
        back.setLayoutX(gameWidth);
		back.setLayoutY(0);
		
		back.setOnAction(b -> {
			
			
        	ViewManager manager = null;
        	
			try {
				manager = new ViewManager();
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			manager.gameStage.close();
			primaryStage = manager.getMainStage();
			primaryStage.show();
			
        	
		});
		
        //vind og tab spillet vindue
		Button next = new Button("Nyt spil");
		Button logout = new Button("Luk spillet");
    	VBox in = new VBox();
        in.setPadding(new Insets(30, 40, 40, 30));
        in.setBackground(new Background(new BackgroundFill(Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        in.getChildren().addAll(logout,next);
        
        //button event
        GorillaButton button = new GorillaButton("Throw");
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
	              graph.setup(angleVal, velocityVal, monkey1.position.x + 5, monkey1.position.y +5, root);
	               graph.draw(dot, context, monkey1, monkey2, rounds);
			 	}else {
	               graph.setup(180 - angleVal, velocityVal, monkey2.position.x + monkey2.boundary.width - 5, monkey2.position.y + 5, root );
	               graph.draw(dot, context, monkey1, monkey2, rounds);
	           }
			
			if(pointsM1 == 3) {
				context.setFill(Color.BLUE);
	    		context.fillRect(0, 0, gameWidth, gameWidth);
	    		context.setFill(Color.YELLOW);
	    		context.setFont(new Font("Arial", 20));
		    	context.fillText("Tillykke abe 1, du har vundet spillet!", gameWidth/2 - 150, 35);

		        root.setTop(in);
		    	
		        next.setOnAction(f -> {
		        	pointsM1 = 0;
		        	pointsM2 = 0;
		        	gamingStage.close();
		        	
		        	game.start(gamingStage);
		        });
		    	
		        	logout.setOnAction(d -> {
		        	gamingStage.close();
		        });
		    					    
			}
			if(pointsM2 == 3) {
				context.setFill(Color.BLUE);
	    		context.fillRect(0, 0, gameWidth, gameWidth);
	    		context.setFill(Color.YELLOW);
	    		context.setFont(new Font("Arial", 20));
		    	context.fillText("Tillykke abe 2, du har vundet spillet!", gameWidth/2 - 150, 35);
		    
		    	root.setTop(in);
		    	
		        next.setOnAction(f -> {
		        	pointsM1 = 0;
		        	pointsM2 = 0;
		        	game.start(gamingStage);
		        });
		    					        
		        logout.setOnAction(d -> {
		        	gamingStage.close();
		        });
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
        get_numbers.getChildren().addAll(angle, speed, button,back);
        root.setTop(get_numbers);
        
        //tegner pointscore
		context.setFill(Color.YELLOW);
		context.setFont(new Font("Arial", 36));
		context.fillText("0", 10, 35);
		context.fillText("0", gameWidth-37, 35);
		
		//tegner aber
        monkey1.render(context);
		monkey2.render(context);
		
		return mainScene;
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

}
