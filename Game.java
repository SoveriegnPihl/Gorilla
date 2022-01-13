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

public class Game {
	
	int angleVal, velocityVal, pointsMonkey1, pointsMonkey2;
	int gameWidth,gameHeight;
	int rounds = 1;
	Scene mainScene;
	static int pointsM1, pointsM2;
	public simulation graph = new simulation();
	
	
	
	Building[] buildings;
	public final static int AMOUNT_OF_BUILDINGS = 10;
	
	int totalWidth;
	

	
	public Game(int gameWidth, int gameHeight) {
		this.gameWidth = gameWidth;
		this.gameHeight = gameHeight;
		
		
		buildings = new Building[AMOUNT_OF_BUILDINGS];
		int offset = 0;
		int range = gameWidth/32;
		int fullWidth = 0;
		for(int i = 0; i < AMOUNT_OF_BUILDINGS ;i++) {
			int width = Utility.getRandomInt(3, 7);
			int height = Utility.getRandomInt(4, 12);
						
			buildings[i] = new Building(width, height, offset, gameHeight);
			fullWidth += buildings[i].width;
			offset += width * Tile.TILE_SIZE;
		}
		
		/*while(fullWidth != range) {
			int build = Utility.getRandomInt(0,AMOUNT_OF_BUILDINGS-1);
			if(buildings[build].width >= 3) {
				buildings[build].width++;
				fullWidth++;
			}
			else if(buildings[build].width <= 7) {
				buildings[build].width--;
				fullWidth--;	
			}
			
		}*/
		

		
		
	}
	
	public Scene Run() {

		//Start indstillinger
		BorderPane root = new BorderPane();
		Canvas canvas = new Canvas(gameWidth, gameHeight);
		GraphicsContext context = canvas.getGraphicsContext2D();
	    
		//elementer der skal indsættes
		//baggrund
		root.setCenter(canvas);
		context.setFill(Color.BLUE);
		context.fillRect(0, 0, gameWidth, gameHeight);
		
		//monkey 1
		Sprite monkey1 = new Sprite();
		monkey1.position.set(200, gameHeight - buildings[1].height * Tile.TILE_SIZE - 60);
		monkey1.setImage("monkey_throw.png");
				
		//monkey 2
		Sprite monkey2 = new Sprite();
		monkey2.position.set(gameWidth - 200, gameHeight - buildings[AMOUNT_OF_BUILDINGS-2].height * Tile.TILE_SIZE - 60);
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
        
        //vind og tab spillet vindue
		Button next = new Button("Nyt spil");
		Button logout = new Button("Luk spillet");
    	VBox in = new VBox();
        in.setPadding(new Insets(30, 40, 40, 30));
        in.setBackground(new Background(new BackgroundFill(Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        in.getChildren().addAll(logout,next);
        
        //button event
        Button button = new Button("Throw Banana");
        button.setOnAction((event) -> {
            if(Utility.isInt(angle.getText())) {
                angleVal = Integer.parseInt(angle.getText());
            }
            if(Utility.isInt(speed.getText())) {
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
			
			

			
			
			//tegner bygninger
			for(int i = 0; i< buildings.length; i++) {
				buildings[i].render(context);
			}
			
			
			
			
			
			//tegner aber
            monkey1.render(context);
			monkey2.render(context);
			
			//point tæller
			if (rounds%2 == 0) {
				graph.setup(angleVal, velocityVal, monkey1.getXPos(), monkey1.getYPos());
				graph.draw(dot, context, monkey1, monkey2, rounds, buildings);
			}else {
				graph.setup(180 - angleVal, velocityVal, monkey2.getXPos(), monkey2.getYPos());
				graph.draw(dot, context, monkey1, monkey2, rounds, buildings);
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
		        });
		    	
		        	logout.setOnAction(d -> {
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
		        });
		    					        
		        logout.setOnAction(d -> {
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
		
		//tegner bygninger
		for(int i = 0; i< buildings.length; i++) {
			buildings[i].render(context);
		}
		
		
		mainScene = new Scene(root);
		return mainScene;
	}

	
	

    //public void makeBuilding() {
		
		

		
		/*while (TOTAL_WIDTH != RANGE){
			int i = this.util.getRandomInt(0, AMOUNT_OF_BUILDINGS-1);
			
			if (TOTAL_WIDTH < RANGE && this.buildings.get(i).width<7){
				this.buildings.get(i).width++;
				TOTAL_WIDTH++;
				
			} else if (TOTAL_WIDTH > RANGE && this.buildings.get(i).width>3){
				this.buildings.get(i).width--;
				TOTAL_WIDTH--;				
			}
			
			
		}*/
			

			
		//}



}
