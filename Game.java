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
import javafx.scene.image.PixelReader;
import javafx.scene.layout.AnchorPane;
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
	
	Sprite monkey_win = new Sprite();
	Sprite monkey1 = new Sprite();
	Sprite monkey2 = new Sprite();

	simulation graph = new simulation();
	Utility util = new Utility();
	Main game = new Main();
	Stage primaryStage;
	AnchorPane root = new AnchorPane();
	BorderPane root1 = new BorderPane();
	Utility Number;
	private VBox get_numbers;
	private static ImageView healthbar;
	private ImageView healthbar2;
	private boolean modifierExist;
	private BonusModifier bonus;
	private GridPane gridPane1;
	private GridPane gridPane2;
	
	Image BACKGROUND_IMAGE = new Image("deep_blue.png");
	
	private static int counter1 = 0;
	private static int counter2 = 0;
	
	BMP bmp;
	Building[] buildings;
	animationThread animation;
	sleepThread sleep;
	double[] windConditions = new double[5];
	GraphicsContext context;
	
	public final static int AMOUNT_OF_BUILDINGS = 10;
		
	public Game (int gameWidth, int gameHeight) {
		
		this.gameWidth = gameWidth;
		this.gameHeight = gameHeight;
		bmp = new BMP(gameWidth, gameHeight);
		
		Image image = new Image("Windows.png");
		PixelReader reader = image.getPixelReader();
		
		
		buildings = new Building[AMOUNT_OF_BUILDINGS];
		int offset = 0;
		
		double defaultWidth = gameWidth / AMOUNT_OF_BUILDINGS;
		double[] buildingsWidth = new double[AMOUNT_OF_BUILDINGS];
		for(int i = 0; i < AMOUNT_OF_BUILDINGS; i++) {
			buildingsWidth[i] = defaultWidth;
		}
	
		for(int i = 0; i < AMOUNT_OF_BUILDINGS; i++) {
			
			int widthChange = Utility.getRandomInt(0, gameWidth / 25);
			buildingsWidth[i] += widthChange;
			double perBuildingChange = ((double)widthChange) / ((double)AMOUNT_OF_BUILDINGS - 1);
			for(int j = 0; j < AMOUNT_OF_BUILDINGS; j++) {
				if(i != j) {
					buildingsWidth[j] -= perBuildingChange; 
				}
			}
		}
		
		for(int i = 0; i < AMOUNT_OF_BUILDINGS - 1 ;i++) {
			int height = Utility.getRandomInt(gameHeight/5, (int)(gameHeight / 2));
			int width = (int)buildingsWidth[i];
			buildings[i] = new Building(width, height, offset, gameHeight, bmp, reader);
			offset += width;
		}
		
		int height = Utility.getRandomInt(gameHeight/5, (int)(gameHeight / 1.3));
		buildings[AMOUNT_OF_BUILDINGS - 1] = new Building(gameWidth - offset, height, offset, gameHeight, bmp, reader);
		

		
		
	}
		
		public Scene Run() {
		
	//Start indstillinger
	    mainScene = new Scene(root);
		Canvas canvas = new Canvas(gameWidth, gameHeight);
		context = canvas.getGraphicsContext2D();
		ViewManager.gameStage.setTitle("Simp Gorillas");
		addwindConditions();
		root.getChildren().add(canvas);
		createBackground();
		//animation = new animationThread(gridPane1, gridPane2);
		//animation.start();
		
		context.drawImage(image2,0,0, gameWidth, gameHeight);
		//elementer der skal indsættes
		//baggrund
		

		//monkey 1
		Building BM1 = buildings[1];
		monkey1 = new Sprite();
		monkey1.setImage(ViewManager.getMonkeyColor1());
		monkey1.position.set(
		BM1.offset + BM1.width / 2 - monkey1.getWidth() / 2, 
		gameHeight - BM1.height - monkey1.getHeight());
				
					
		//monkey 2
		Building BM2 = buildings[AMOUNT_OF_BUILDINGS - 2];
		monkey2 = new Sprite();
		monkey2.setImage(ViewManager.getMonkeyColor2());
		monkey2.position.set(
		BM2.offset + BM2.width / 2 - monkey2.getWidth() / 2, 
		gameHeight - BM2.height - monkey2.getHeight());
		
		// monkey win
		monkey_win.setImage("monkey_win.png");
		
		//banekurve dot
		Sprite dot = new Sprite();
		dot.position.set(8, gameHeight);
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
		Button next = new Button("NEW GAME");
		Button logout = new Button("BACK");
    	VBox in = new VBox();
    	in.setSpacing(7);
        in.setPadding(new Insets(30, 40, 40, 30));
        in.getChildren().addAll(next,logout);
        
     // tegner healthbar
		
    
        healthbar = new ImageView("healthbar_full.png");
		healthbar.setLayoutX(monkey1.position.x + 5);
		healthbar.setLayoutY(monkey1.position.y-30);
		root.getChildren().add(healthbar);
		
		healthbar2 = new ImageView("healthbar_full.png");
		healthbar2.setLayoutX(monkey2.position.x);
		healthbar2.setLayoutY(monkey2.position.y - 30);
		root.getChildren().add(healthbar2);
        
        //button event
        Button button = new Button("Throw");
        //button.setPadding(4,4,4,4);
        button.setOnAction(e -> {
       
    		
            if(util.isInt(angle.getText())) {
                angleVal = Integer.parseInt(angle.getText());
            }
            if(util.isInt(speed.getText())) {
                velocityVal = Integer.parseInt(speed.getText());
            }
            
          //wind
            int windSel = Utility.getRandomInt(0,4);
            animation.setWind(windConditions[windSel]); 
            graph.setWind(windConditions[windSel]);
                    
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
			//tegner bygninger
			bmp.render(context);
			
			//tegner aber
            monkey1.render(context);
			monkey2.render(context);
			
			
			
			//point tæller
			 if (rounds%2 == 0) {
	              graph.setup(angleVal, velocityVal, monkey1.position.x + 5, monkey1.position.y +5, root);
	               graph.draw(dot, monkey1, monkey2, rounds, bmp,context);
	         
			 
			 }else {
	               graph.setup(180 - angleVal, velocityVal, monkey2.position.x + monkey2.boundary.width - 5, monkey2.position.y + 5, root );
	               graph.draw(dot, monkey1, monkey2, rounds,bmp,context);
	             
	           }
			 
			 	if (counter2 == 1) {
			 		root.getChildren().remove(healthbar);
			 		healthbar = new ImageView("healthbar_60.png");
					healthbar.setLayoutX(monkey1.position.x + 5);
					healthbar.setLayoutY(monkey1.position.y-30);
					root.getChildren().add(healthbar);
				}
				if (counter2 == 2) {
					root.getChildren().remove(healthbar);
					healthbar = new ImageView("healthbar_30.png");
					healthbar.setLayoutX(monkey1.position.x + 5);
					healthbar.setLayoutY(monkey1.position.y-30);
					root.getChildren().add(healthbar);
				}
				
				if (counter1 == 1) {
					root.getChildren().remove(healthbar2);
					healthbar2 = new ImageView("healthbar_60.png");
					healthbar2.setLayoutX(monkey2.position.x);
					healthbar2.setLayoutY(monkey2.position.y - 30);
					root.getChildren().add(healthbar2);
				}
				if (counter1 == 2) {
					root.getChildren().remove(healthbar2);
					healthbar2 = new ImageView("healthbar_30.png");
					healthbar2.setLayoutX(monkey2.position.x);
					healthbar2.setLayoutY(monkey2.position.y - 30);
					root.getChildren().add(healthbar2);
				}
				
			
			if(pointsM1 == 3) {
				removeTriangle(m2Triangle);
				context.drawImage(image2,0,0, gameWidth, gameHeight);
	    		context.setFill(Color.YELLOW);
	    		context.setFont(new Font("Arial", 20));
		    	context.fillText("Tillykke abe 1, du har vundet spillet!", gameWidth/2 - 150, 35);
		    	root.getChildren().remove(get_numbers);
		    	
		    	root.getChildren().remove(healthbar2);
		    	root.getChildren().remove(healthbar);
		    	root.getChildren().add(in);
		    	
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
			if(pointsM2 == 3) {
				removeTriangle(m1Triangle);
				context.drawImage(image2,0,0, gameWidth, gameHeight);
	    		context.setFill(Color.YELLOW);
	    		context.setFont(new Font("Arial", 20));
		    	context.fillText("Tillykke abe 2, du har vundet spillet!", gameWidth/2 - 150, 35);
		    	root.getChildren().remove(get_numbers);
		    	root.getChildren().remove(healthbar);
		    	root.getChildren().remove(healthbar2);
		    	root.getChildren().add(in);
		   
		    	
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
			
			//point tekst
			context.setFill(Color.YELLOW);
			context.setFont(new Font("Arial", 36));
			context.fillText(""+pointsM1, 10, 35);
			context.fillText(""+pointsM2, gameWidth-37, 35);
			
			
        });
        
        //tegner 
        //vinkel og fart box
        get_numbers = new VBox();
        get_numbers.setSpacing(7);
        get_numbers.setPadding(new Insets(20, 20, 20, 20));
        get_numbers.getChildren().addAll(angle, speed, button,back);
        root.getChildren().add(get_numbers);
        
        //tegner pointscore
		context.setFill(Color.YELLOW);
		context.setFont(new Font("Arial", 36));
		context.fillText("0", 10, 35);
		context.fillText("0", gameWidth-37, 35);
		
		//tegner aber
        monkey1.render(context);
		monkey2.render(context);

		//tegner bygninger
		bmp.render(context);
		return mainScene;
		
	}
	
	public Polygon drawTriangle(Sprite object) {
		Polygon polygonTri = new Polygon();
		polygonTri.setFill(Color.YELLOW);
		double trueY = object.position.y - 25;
		
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
		
		counter1++;
    }
  
    public static void inc_m2() {
    	pointsM2++;
    	
    	counter2++;
}
    private void createBackground() {
		gridPane1 = new GridPane();
		gridPane2 = new GridPane();
		
		for (int row = 0 ; row < 3; row++) {
			for(int colum = 0; colum < 4; colum++) {
			ImageView backgroundImage1 = new ImageView(BACKGROUND_IMAGE);
			ImageView backgroundImage2 = new ImageView(BACKGROUND_IMAGE);
			
			GridPane.setConstraints(backgroundImage1, colum, row );
			GridPane.setConstraints(backgroundImage2, colum, row );
			gridPane1.getChildren().add(backgroundImage1);
			gridPane2.getChildren().add(backgroundImage2);
			}
		}
		
		gridPane2.setLayoutY(-256);		
		//root.getChildren().addAll(gridPane1, gridPane2);
	}
    public void addwindConditions() {
		windConditions[0] = -0.1;
		windConditions[1] = -0.07;
		windConditions[2] = 0;
		windConditions[3] = 0.07;
		windConditions[4] = 0.1;
		
	}
    public void monkeyWin (Sprite monkey) {
		if(monkey.getName() == "monkey_throw.png") {
			monkey_win.setPosition(monkey1.position.x + 17, monkey1.position.y - 1);
			monkey_win.render(context);
		}
		else {
			monkey_win.setPosition(monkey2.position.x - 1, monkey2.position.y - 1);
			monkey_win.render(context);
		}
	}
    
    
}