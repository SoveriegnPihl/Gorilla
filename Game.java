import java.io.FileNotFoundException;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Game {
	static int pointsM1, pointsM2;
	int angleVal, velocityVal, pointsMonkey1, pointsMonkey2;
	int rounds = 1;
	Scene mainScene;
	
	final static Image image2 = new Image ("mainbackground.png");
	
	int gameWidth;
	int gameHeight;
	boolean modifierExist = false;
	
	simulation graph = new simulation();
	Utility util = new Utility();
	Main game = new Main();
	Stage primaryStage;
	BorderPane root = new BorderPane();
	BorderPane root1 = new BorderPane();
	GraphicsContext context;
	BonusModifier bonus;
	static Sprite doublePoints;
	private static int xCoordinate;
	private static int yCoordinate;
		
	public Scene spil (int gameWidth, int gameHeight) {
		
		this.gameWidth = gameWidth;
		this.gameHeight = gameHeight;
	
		//Start indstillinger
	    mainScene = new Scene(root);
		Canvas canvas = new Canvas(gameWidth, gameHeight);
		context = canvas.getGraphicsContext2D();
		ViewManager.gameStage.setTitle("Simp Gorillas");
		root.setCenter(canvas);
		
		context.drawImage(image2,0,0, gameWidth, gameHeight);
	

		//monkey 1
		Sprite monkey1 = new Sprite();
		monkey1.position.set(8, gameHeight - 60);
		monkey1.setImage(ViewManager.getMonkeyColor1());
		System.out.print(ViewManager.getMonkeyColor1());
		//monkey 2
		Sprite monkey2 = new Sprite();
		monkey2.position.set(gameWidth - 90, gameHeight - 60);
		monkey2.setImage(ViewManager.getMonkeyColor2());
		
		//banekurve dot
		Sprite dot = new Sprite();
		dot.position.set(8, gameHeight-80);
		dot.setImage("dot.png");
		
		//spiller tur trekanter
		Polygon m1Triangle = drawTriangle(monkey1);
		Polygon m2Triangle = drawTriangle(monkey2);
        root.getChildren().add(m1Triangle);
		
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
        
        Button back = new Button("Back");
        back.setLayoutX(gameWidth);
		back.setLayoutY(0);
	
		back.setOnAction(b -> {
		ViewManager.gameStage.close();
		game.start(primaryStage);
		
		});
		
        //vind og tab spillet vindue
		Button next = new Button("Nyt spil");
		Button logout = new Button("Luk spillet");
    	VBox in = new VBox();
    	in.setSpacing(7);
        in.setPadding(new Insets(30, 40, 40, 30));
        in.setBackground(new Background(new BackgroundFill(Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        in.getChildren().addAll(next,logout);
        
        //button event
        Button button = new Button("Throw");
        //button.setPadding(4,4,4,4);
        button.setOnAction(e -> {
        	
            if(Utility.isInt(angle.getText())) {
                angleVal = Integer.parseInt(angle.getText());
            }
            if(Utility.isInt(speed.getText())) {
                velocityVal = Integer.parseInt(speed.getText());
            }
                    
            //clear baggrund
           context.drawImage(image2,0,0, gameWidth, gameHeight);
           if (Utility.getRandomInt(0,8) == 0){
        	    bonus = new BonusModifier(gameWidth, gameHeight);
        	    modifierExist = true;
        	   
           }
   			if (modifierExist ) {
   				bonus.render(context);
   				
   			}
        
    		
    		//runder
    		//runder
			context.setFill(Color.YELLOW);
			context.setFont(new Font("Arial", 36));
					
			if (rounds%2 == 0) {
				removeTriangle(m2Triangle);
		        root.getChildren().add(m1Triangle);
				context.fillText("Runde: " + rounds, gameWidth/2 - 70, 35);
				rounds++;						
			}
			
			else {
				root.getChildren().remove(m1Triangle);
		        root.getChildren().add(m2Triangle);
				context.fillText("Runde: " + rounds, gameWidth/2 - 70, 35);
				rounds++;			    					
			}
			
			//tegner aber
            monkey1.render(context);
			monkey2.render(context);
			
			//point t�ller
			 if (rounds%2 == 0) {
	              graph.setup(angleVal, velocityVal, monkey1.position.x + 5, monkey1.position.y +5, root);
	               graph.draw(dot, monkey1, monkey2, rounds,context);
			 	}else {
	               graph.setup(180 - angleVal, velocityVal, monkey2.position.x + monkey2.boundary.width - 5, monkey2.position.y + 5, root );
	               graph.draw(dot, monkey1, monkey2, rounds,context);
	           }
			
			if(pointsM1 >= 3) {
				removeTriangle(m2Triangle);
				//image2,0,0, gameWidth, gameHeight);
	    		context.setFill(Color.YELLOW);
	    		context.setFont(new Font("Arial", 20));
		    	context.fillText("Tillykke abe 1, du har vundet spillet!", gameWidth/2 - 150, 35);
		        root.setTop(in);
		    	
		        next.setOnAction(f -> {
		        	pointsM1 = 0;
		        	pointsM2 = 0;
		        	ViewManager.gameStage.close();
		        	game.start(primaryStage);
		        });
		    	
		        	logout.setOnAction(d -> {
		        		ViewManager.gameStage.close();
		        });
		    					    
			}
			if(pointsM2 >= 3) {
				removeTriangle(m1Triangle);
				context.drawImage(image2 ,0,0, gameWidth, gameHeight);
	    		context.setFill(Color.YELLOW);
	    		context.setFont(new Font("Arial", 20));
		    	context.fillText("Tillykke abe 2, du har vundet spillet!", gameWidth/2 - 150, 35);
		    
		    	root.setTop(in);
		    	
		        next.setOnAction(f -> {
		        	pointsM1 = 0;
		        	pointsM2 = 0;
		        	game.start(primaryStage);
		        });
		    					        
		        logout.setOnAction(d -> {
		        	ViewManager.gameStage.close();
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
        get_numbers.setSpacing(7);
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
	
	public Polygon drawTriangle(Sprite object) {
		Polygon polygonTri = new Polygon();
		polygonTri.setFill(Color.YELLOW);
		double trueY = object.position.y + graph.buttonHeights;
		
		if (object.getName() == "monkey_throw.png") {
			polygonTri.getPoints().addAll(new Double[]{
				object.position.x + 45, trueY - 25,
                object.position.x + object.boundary.width - 25, trueY - 25,
                object.position.x + 10 + object.boundary.width * 0.5, trueY - 10});
		}
		else {
			polygonTri.getPoints().addAll(new Double[]{
					object.position.x + 25, trueY - 25,
	                object.position.x + object.boundary.width - 45, trueY - 25,
	                object.position.x - 10 + object.boundary.width * 0.5, trueY - 10});
		}
		
		return polygonTri;
	}
	

	public void removeTriangle(Polygon triangle) {
		root.getChildren().remove(triangle);
	}

	public static void inc_m1() {
		pointsM1++;
    }
  
    public static void inc_m2() {
    	pointsM2++;
   
}
    
    }
 
