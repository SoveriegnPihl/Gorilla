import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
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

public class ViewManager {

	private static final int HEIGHT = 768;
	private static final int WIDTH = 1024;
	private AnchorPane mainPane;
	private Scene mainScene;
	private Stage mainStage;
	
	int angleVal, velocityVal, pointsMonkey1, pointsMonkey2;
	int rounds = 1;
	static int pointsM1, pointsM2;
	static int[] dimensions = new int[2];
	Stage stage;
	int gameWidth = 600;
	int gameHeight = 600;
	public Main game = new Main();
	@FXML
	TextField height;
	
	@FXML 
	TextField width;
	
	@FXML 
	Label pixels;
	
	public simulation graph = new simulation();
	
	private final static int MENU_BUTTONS_START_X = 100;
	private final static int MENU_BUTTONS_START_Y = 150;
	
	List<GorillaButton> menuButtons;
	
	
	public ViewManager() throws FileNotFoundException {
		menuButtons = new ArrayList<>();
		mainPane = new AnchorPane();
		mainScene = new Scene(mainPane,WIDTH,HEIGHT);
		mainStage = new Stage(); 
		mainStage.setScene(mainScene);
		createButtons();
		createBackground();
		
	}
	
	public Stage getMainStage() {
		return mainStage;
	}
	
	private void addMenuButton(GorillaButton button) {
		button.setLayoutX(MENU_BUTTONS_START_X);
		button.setLayoutY(MENU_BUTTONS_START_Y + menuButtons.size() * 100);
		
		menuButtons.add(button);
		mainPane.getChildren().add(button);
		 
	}
	
	private void createButtons() throws FileNotFoundException {
		createStartButton();
		createScoresButton();	
		createHelpButton();
		createCreditsButton();
		createExitButton();

	}
	
	private void createStartButton() throws FileNotFoundException {
		GorillaButton startButton = new GorillaButton ("PLAY");
		addMenuButton(startButton);
		
		
		startButton.setOnAction(new EventHandler<ActionEvent>(){
			
			@Override
			public void handle(ActionEvent arg0) {

				/*if(is_int(width.getText()) && is_int(height.getText())) {
					gameWidth = Integer.parseInt(width.getText());
					gameHeight =  Integer.parseInt(height.getText());
				}*/
				stage = (Stage)((Node)arg0.getSource()).getScene().getWindow();
				
			
			    
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
		    		//runder
					context.setFill(Color.YELLOW);
					context.setFont(new Font("Arial", 36));
					if (rounds%2 == 0) {
						context.fillText("Runde: " + rounds, gameWidth/2 - 70, 35);
						rounds++;
						if(pointsM1 == 3) {
							context.clearRect(0, 0, gameWidth, gameHeight);
							context.setFill(Color.BLUE);
				    		context.fillRect(0, 0, gameWidth, gameWidth);
				    		context.setFill(Color.YELLOW);
				    		context.setFont(new Font("Arial", 20));
					    	context.fillText("Tillykke abe 1, du har vundet spillet!", gameWidth/2 - 150, 35);
					    	
					    	
					    	Button next = new Button("Nyt spil");
					    	Button logout = new Button("Luk spillet");
					    
					    
					    	VBox in = new VBox();
					        in.setPadding(new Insets(30, 40, 40, 30));
					        in.setBackground(new Background(new BackgroundFill(Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY)));
					        in.getChildren().addAll(logout,next);
					        root.setTop(in);
					    	
					        next.setOnAction(f -> {
					        	pointsM1 = 0;
					        	pointsM2 = 0;
					        	game.start(stage);
					        });
					    	
					        
					        logout.setOnAction(d -> {
					        	stage.close();
					        });
					    	
					    
						}
						if(pointsM2 == 3) {
							context.clearRect(0, 0, gameWidth, gameHeight);
							context.setFill(Color.BLUE);
				    		context.fillRect(0, 0, gameWidth, gameWidth);
				    		context.setFill(Color.YELLOW);
				    		context.setFont(new Font("Arial", 20));
					    	context.fillText("Tillykke abe 2, du har vundet spillet!", gameWidth/2 - 150, 35);
					    	
					    	
					    	Button next = new Button("Nyt spil");
					    	Button logout = new Button("Luk spillet");
					    
					    
					    	VBox in = new VBox();
					        in.setPadding(new Insets(30, 40, 40, 30));
					        in.setBackground(new Background(new BackgroundFill(Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY)));
					        in.getChildren().addAll(logout,next);
					        root.setTop(in);
					    	
					        next.setOnAction(f -> {
					        	pointsM1 = 0;
					        	pointsM2 = 0;
					        	game.start(stage);
					        });
					    	
					        
					        logout.setOnAction(d -> {
					        	stage.close();
					        });
					    	}
						
					}
					else {
						context.fillText("Runde: " + rounds, gameWidth/2 - 70, 35);
						rounds++;
						if(pointsM1 == 3) {
							context.clearRect(0, 0, gameWidth, gameHeight);
							context.setFill(Color.BLUE);
				    		context.fillRect(0, 0, gameWidth, gameWidth);
				    		context.setFill(Color.YELLOW);
				    		context.setFont(new Font("Arial", 20));
					    	context.fillText("Tillykke abe 1, du har vundet spillet!", gameWidth/2 - 150, 35);
					    	
					    	
					    	Button next = new Button("Nyt spil");
					    	Button logout = new Button("Luk spillet");
					    
					    
					    	VBox in = new VBox();
					        in.setPadding(new Insets(30, 40, 40, 30));
					        in.setBackground(new Background(new BackgroundFill(Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY)));
					        in.getChildren().addAll(logout,next);
					        root.setTop(in);
					    	
					        next.setOnAction(f -> {
					        	pointsM1 = 0;
					        	pointsM2 = 0;
					        	game.start(stage);
					        });
					    	
					        
					        logout.setOnAction(d -> {
					        	stage.close();
					        });
					    	
						}
						if(pointsM2 == 3) {
							context.clearRect(0, 0, gameWidth, gameHeight);
							context.setFill(Color.BLUE);
				    		context.fillRect(0, 0, gameWidth, gameWidth);
				    		context.setFill(Color.YELLOW);
				    		context.setFont(new Font("Arial", 20));
					    	context.fillText("Tillykke abe 2, du har vundet spillet!", gameWidth/2 - 150, 35);
					    	
					    	
					    	Button next = new Button("Nyt spil");
					    	Button logout = new Button("Luk spillet");
					    
					    
					    	VBox in = new VBox();
					        in.setPadding(new Insets(30, 40, 40, 30));
					        in.setBackground(new Background(new BackgroundFill(Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY)));
					        in.getChildren().addAll(logout,next);
					        root.setTop(in);
					    	
					        next.setOnAction(f -> {
					        	pointsM1 = 0;
					        	pointsM2 = 0;
					        	game.start(stage);
					        });
					    	
					        
					        logout.setOnAction(d -> {
					        	stage.close();
					        });
					    	
					    	
					    	}
					
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
				
				/*if(!is_int(width.getText())||!is_int(height.getText())) {
					pixels.setText("skriv nu det i pixels forhelvede paul");
				} else {
				stage.setScene(mainScene);
				stage.show();
				}*/
				
				stage.setScene(mainScene);
				stage.show();
			

			}
		
		public boolean is_int(String message) {
		        
		        try {
		            int age = Integer.parseInt(message);
		 
		            return true;
		        }catch(NumberFormatException e) {
		       }
		    
		        return false;
		    
		   
		    }
		    
		   
				
		});
	}

	private void createScoresButton() throws FileNotFoundException {
		GorillaButton scoresButton = new GorillaButton ("SCORES");
		addMenuButton(scoresButton);
	}
	private void createHelpButton() throws FileNotFoundException {
		GorillaButton helpButton = new GorillaButton ("HELP");
		addMenuButton(helpButton);
	}
	private void createCreditsButton() throws FileNotFoundException {
		GorillaButton creditsButton = new GorillaButton ("CREDITS");
		addMenuButton(creditsButton);
	}
	private void createExitButton() throws FileNotFoundException {
		GorillaButton exitButton = new GorillaButton ("EXIT");
		addMenuButton(exitButton);
		
		exitButton.setOnAction(d -> {
        	stage.close();
        });
	}
	
	
	
	
	private void createBackground() {
		Image backgroundImage = new Image ("background.jpg",256,256,false,true);
		BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.REPEAT,BackgroundRepeat.REPEAT,BackgroundPosition.DEFAULT,null);
		mainPane.setBackground(new Background(background));
	}

	 public static void inc_m1() {
		    	pointsM1++;
		    }
	  
	    public static void inc_m2() {
	    	pointsM2++;
	    }
}



