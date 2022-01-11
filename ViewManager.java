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
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
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
	
	int gameWidth,gameHeight;
	static int pointsM1, pointsM2;
	static int[] dimensions = new int[2];
	Stage stage;
	
	TextField height, width;
	Label info;
	
	public Main game = new Main();
	
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
		createBackground(mainPane);
		createLogo();
		
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
	private void addTextfields(TextField text, int x, int y, String type) {
		text.setPromptText("500");
		Label lblName = new Label("Please select "+ type+" in pixels"); 
		lblName.setMinWidth(75); 
		lblName.setTextFill(Color.WHITE);
	   lblName.setStyle("-fx-font: 24 arial;");
	   lblName.setLayoutX(850 - 350 );
		lblName.setLayoutY(y+5);
		
		text.setPrefHeight(40);
		text.setPrefWidth(200);
        text.setMaxWidth(100);
        text.setLayoutX(x);
		text.setLayoutY(y);
		  text.setStyle("-fx-font: 24 arial;");
		  
		mainPane.getChildren().add(lblName);	
		mainPane.getChildren().add(text);	
	}
	
	private void createButtons() throws FileNotFoundException {
		createStartButton();
		createScoresButton();	
		createHelpButton();
		createCreditsButton();
		createExitButton();
		createTextFields();

	}
	
	private void createStartButton() throws FileNotFoundException {
		GorillaButton startButton = new GorillaButton ("PLAY");
		addMenuButton(startButton);
		
		startButton.setOnAction(new EventHandler<ActionEvent>(){
			
			@Override
			public void handle(ActionEvent arg0) {
				boolean hej = false;
				if(!(is_int(width.getText()))||!(is_int(height.getText()))) {
					info.setText("skriv nu det i pixels forhelvede paul"); 	
				}
				else if (Integer.parseInt(width.getText()) > 2000 || Integer.parseInt(height.getText()) > 1000) { 
					info.setText("Please lower you resolution");
					
				} else if (Integer.parseInt(width.getText()) < 1 || Integer.parseInt(height.getText()) < 1) {
					info.setText("Please increase your resolution");
				} else {
					hej = true;
					gameWidth = Integer.parseInt(width.getText());
					gameHeight =  Integer.parseInt(height.getText()); 
					}
				
				Game spil = new Game(gameWidth,gameHeight);
			
			
				if (hej) {
					mainStage.close();
					stage.setScene(Game.returnScene());
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
			 });
		
		}
		private void createScoresButton() throws FileNotFoundException {
		GorillaButton scoresButton = new GorillaButton ("SCORES");
		addMenuButton(scoresButton);
	}

		private void createMenuButton() throws FileNotFoundException{
			GorillaButton menu = new GorillaButton ("Menu");
			addMenuButton(menu);
			
			menu.setOnAction(j -> {
				stage.close();
				game.start(stage);
				
			});
			
		}
		
		private void createHelpButton() throws FileNotFoundException {
			GorillaButton helpButton = new GorillaButton ("HELP");
			addMenuButton(helpButton);
			
				helpButton.setOnAction(g -> {
				mainStage.close();
				
			
				Stage help = new Stage();
				AnchorPane mainPane = new AnchorPane();
				Scene helpScene = new Scene(mainPane, WIDTH, HEIGHT);
				
				createBackground(mainPane);
				
				Text guideOverskrift = new Text();
				guideOverskrift.setText("Instructions");
				guideOverskrift.setFont(Font.font("Verdana", 40));
				guideOverskrift.setFill(Color.WHITE);
				guideOverskrift.setX(WIDTH/2 - 120);
				guideOverskrift.setY(HEIGHT/6);
				
				
				Text guide = new Text();
				guide.setText(" The gorilla game is fun and easy to learn! "
							+ "\n You are playing as a monkey and your goal "
							+ "\n is to hit your opponents with your bananas before they hit you. "
							+ "\n In order to throw your banana you have to "
							+ "\n choose an angle and a speed for the throw."
							+ "\n "
							
							);
				guide.setFont(Font.font("Verdana",20));
				guide.setFill(Color.WHITE);
				guide.setX(WIDTH/8);
				guide.setY(HEIGHT/4);
				
				GorillaButton menuHelpButton = null;
				try {
					menuHelpButton = new GorillaButton ("MENU");
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				
				menuHelpButton.setLayoutX(130);
				menuHelpButton.setLayoutY(400);
				
				menuHelpButton.setOnAction(j -> {
					help.close();
					game.start(stage);
					
				});
				
				mainPane.getChildren().addAll(menuHelpButton,guide,guideOverskrift);
		
				help.setScene(helpScene);
				help.show();
				
			});
		}
		private void createCreditsButton() throws FileNotFoundException {
			GorillaButton creditsButton = new GorillaButton ("CREDITS");
			addMenuButton(creditsButton);
			
			creditsButton.setOnAction(k -> {
				mainStage.close();
				
				Stage help = new Stage();
				BorderPane mainPane = new BorderPane();
				Scene creditsScene = new Scene(mainPane, WIDTH, HEIGHT);
				
				createBackground(mainPane);

				help.setScene(creditsScene);
				help.show();
				
			});
			
			
		}
	private void createExitButton() throws FileNotFoundException {
		GorillaButton exitButton = new GorillaButton ("EXIT");
		addMenuButton(exitButton);
		
		exitButton.setOnAction(d -> {
        	mainStage.close();
        });
	}
	
	private void createTextFields() {
	
		info = new Label("Please select your preffered settings");
		info.setMinWidth(75); 
		info.setTextFill(Color.WHITE);
		info.setStyle("-fx-font: 24 arial;");
		info.setLayoutX(850 - 350 );
		info.setLayoutY(225);
		mainPane.getChildren().add(info);	
		
		
		width = new TextField();
			addTextfields(width,850, 400, "width");
			

			height = new TextField();
			addTextfields(height,850, 300,"height");
	       

	}
	
	private void createBackground(Pane pane) {
		Image backgroundImage = new Image ("background.jpg",256,256,false,true);
		BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.REPEAT,BackgroundRepeat.REPEAT,BackgroundPosition.DEFAULT,null);
		pane.setBackground(new Background(background));
		
	}
	
	private void createLogo() {
		ImageView logo = new ImageView("title3.png");
		logo.setLayoutX(400);
		logo.setLayoutY(50);
		
		logo.setOnMouseEntered(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				logo.setEffect(new DropShadow());
				
			}
		});
		
		logo.setOnMouseExited(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				logo.setEffect(null);
				
			}
		});
		
		mainPane.getChildren().add(logo);
		
	}
	
	


	 public static void inc_m1() {
	   	pointsM1++;
	    }
	  
	    public static void inc_m2() {
    	pointsM2++;
	   
	}

	    public boolean is_int(String message) {
	        
	        try {
	            int age = Integer.parseInt(message);
	           
	            return true;
	        }catch(NumberFormatException e) {
	       }
	        return false;
	 }
	
	}


