import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.ButtonType;

public class ViewManager {

	private static final int HEIGHT = 768;
	private static final int WIDTH = 1024;
	private AnchorPane mainPane;
	private Scene mainScene;
	private static Stage mainStage;
	static Stage gameStage = new Stage();
	private final String FONT_PATH = "kenvector_future.ttf";
	
	static int gameWidth = 1000;
	static int gameHeight = 800;
	static int pointsM1, pointsM2;
	static int[] dimensions = new int[2];
	Stage stage;
	Stage stage1;
	TextField height, width, numberOfRounds;
	Label info;
	AnchorPane helpPane;
	AnchorPane startPane;
	
	public Main game = new Main();
	Scene gameScene;
	
	private final static int MENU_BUTTONS_START_X = 100;
	private final static int MENU_BUTTONS_START_Y = 150;
	
	List<GorillaButton> menuButtons;
	
	public Utility util = new Utility();
	public Game spillet = new Game();
	
	
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
	
	public static Stage getMainStage() {
		return mainStage;
	}
	
	private void addMenuButton(GorillaButton button) {
		button.setLayoutX(MENU_BUTTONS_START_X);
		button.setLayoutY(MENU_BUTTONS_START_Y + menuButtons.size() * 100);
		
		menuButtons.add(button);
		mainPane.getChildren().add(button);
		 
	}
	private void addTextfields(TextField text, int textX, int textY,int lblX,int lblY, String type) {
		text.setPromptText("500");
		Label lblName = new Label(type); 
		lblName.setMinWidth(75); 
		lblName.setTextFill(Color.WHITE);
		lblName.setFont(Font.loadFont(getClass().getResourceAsStream(FONT_PATH), 18));
		lblName.setLayoutX(lblX);
		lblName.setLayoutY(lblY);
		/*DropShadow shadow = new DropShadow();
	    shadow.setOffsetY(5.0);
	    lblName.setEffect(shadow);*/
	    
		text.setPrefHeight(40);
		text.setPrefWidth(200);
        text.setMaxWidth(100);
        text.setLayoutX(textX);
		text.setLayoutY(textY);
		text.setStyle("-fx-font: 24 arial;");
		  
		startPane.getChildren().add(lblName);	
		startPane.getChildren().add(text);	
	}
	private void addTextfields2(TextField text, int textX, int textY,int lblX,int lblY, String type) {
		//text.setPromptText("500");
		Label lblName = new Label(type); 
		lblName.setMinWidth(75); 
		lblName.setTextFill(Color.WHITE);
		lblName.setFont(Font.loadFont(getClass().getResourceAsStream(FONT_PATH), 18));
		lblName.setLayoutX(lblX);
		lblName.setLayoutY(lblY);
		/*DropShadow shadow = new DropShadow();
	    shadow.setOffsetY(5.0);
	    lblName.setEffect(shadow);*/
	    
		text.setPrefHeight(40);
		text.setPrefWidth(200);
        text.setMaxWidth(100);
        text.setLayoutX(textX);
		text.setLayoutY(textY);
		text.setStyle("-fx-font: 24 arial;");
		  
		startPane.getChildren().add(lblName);	
		startPane.getChildren().add(text);	
	}
	
	private void createButtons() throws FileNotFoundException {
		createStartButton();
		createScoresButton();	
		createHelpButton();
		//createSettingsButton();
		createExitButton();
		createTextFields();

	}
	
	private void createStartButton() throws FileNotFoundException {
		GorillaButton startButton = new GorillaButton ("PLAY");
		addMenuButton(startButton);
		
		startButton.setOnAction(new EventHandler<ActionEvent>(){
			
		@Override
		public void handle(ActionEvent arg0) {
			
				mainStage.close();

	            Stage settingStage = new Stage();
	            startPane = new AnchorPane();
	            Scene creditsScene = new Scene(startPane, WIDTH, HEIGHT);

	            createBackground(startPane);
	            
	            Text guideOverskrift = new Text();
	            guideOverskrift.setText("Settings");
	            guideOverskrift.setFont(Font.font("Verdana", 40));
	            guideOverskrift.setFont(Font.loadFont(getClass().getResourceAsStream(FONT_PATH), 40));
	            guideOverskrift.setFill(Color.WHITE);
	            guideOverskrift.setX(WIDTH/2 - 110);
	            guideOverskrift.setY(HEIGHT/6);

	     
	            ChoiceBox<String> gravity = new ChoiceBox();
	            String[] levels = {"Earth = 9,81 m/s^2","Mars = 3,72 m/s^2","Moon = 1,62 m/s^2"};
	            gravity.getItems().addAll(levels);
	            gravity.setLayoutX(800);
	            gravity.setLayoutY(340);
	            gravity.setPrefSize(200, 35);
	            gravity.setOnAction(null);
	            
	            ChoiceBox<String> color = new ChoiceBox();
	            String[] colors = {"Black","Blue","Red"};
	            color.getItems().addAll(colors);
	            color.setLayoutX(800);
	            color.setLayoutY(450);
	            color.setPrefSize(200, 35);
	            color.setOnAction(null);
	            

	          
	      
	            
	            Label gravityLbl = new Label();
	            gravityLbl.setText("Select gravity");
	            gravityLbl.setLayoutX(550);
	            gravityLbl.setLayoutY(350);
	            gravityLbl.setMinWidth(75); 
	            gravityLbl.setTextFill(Color.WHITE);
	            gravityLbl.setFont(Font.loadFont(getClass().getResourceAsStream(FONT_PATH), 18));
	            
	            Label colorLbl = new Label();
	            colorLbl.setText("Select color");
	            colorLbl.setLayoutX(550);
	            colorLbl.setLayoutY(450);
	            colorLbl.setMinWidth(75); 
	            colorLbl.setTextFill(Color.WHITE);
	            colorLbl.setFont(Font.loadFont(getClass().getResourceAsStream(FONT_PATH), 18));
	            
	            createTextFields();
	            
	            numberOfRounds = new TextField();
	            addTextfields2(numberOfRounds,850,240,550,250,"Number of rounds");
	    		
	    		width = new TextField();
    			addTextfields(width,350,240,50,250, "Please select width");

    			height = new TextField();
    			addTextfields(height,350,340,50,350,"Please select height");
    			
    			GorillaButton play = new GorillaButton ("PLAY");
    			play.setLayoutX(1024/2-95);
    			play.setLayoutY(550);
	    		
    			startPane.getChildren().addAll(guideOverskrift,gravity,gravityLbl,play,color,colorLbl);
 	            
 	            settingStage.setScene(creditsScene);
 	            settingStage.show();
	    		
    			
    			
	    		 play.setOnAction(g -> {
	    			  
	    				/*boolean hej = false;
	    					if(!(util.isInt(width.getText()))||!(util.isInt(height.getText()))) {
	    						info.setText("skriv nu det i pixels forhelvede paul");
	    						}
	    					else if (Integer.parseInt(width.getText()) > 2000 || Integer.parseInt(height.getText()) > 1000) { 
	    						info.setText("Please lower you resolution");
	    						} 
	    					else if (Integer.parseInt(width.getText()) < 1 || Integer.parseInt(height.getText()) < 1) {
	    						info.setText("Please increase your resolution");
	    						} 
	    					else {
	    						hej = true;
	    						System.out.println("what " + Integer.parseInt(width.getText()) + " - " + Integer.parseInt(height.getText()));
	    						gameWidth = Integer.parseInt(width.getText());
	    						gameHeight =  Integer.parseInt(height.getText()); 
	    						System.out.println("the fuck " + gameWidth + " - " + gameHeight);
	    			 
	    			if (hej) {*/
	    			settingStage.close();
	 				gameStage.setScene(spillet.spil(gameWidth,gameHeight));
	 				gameStage.show();
	    			
	    					
	    					
	    					});
	        }
		});
		
		}
	private void createScoresButton() throws FileNotFoundException {
		GorillaButton scoresButton = new GorillaButton ("SCORES");
		addMenuButton(scoresButton);
	}

		

	private void createMenuButton(Stage mainStage, AnchorPane Pane) {
		
		GorillaButton menuButton = new GorillaButton ("MENU");

		menuButton.setLayoutX(400);
		menuButton.setLayoutY(600);
		
		Pane.getChildren().add(menuButton);
				
		menuButton.setOnAction(j -> {
			mainStage.close();
		game.start(stage);
		});
	}
		
	private void createHelpButton() {
		GorillaButton helpButton = new GorillaButton ("HELP");
		addMenuButton(helpButton);
			
		helpButton.setOnAction(g -> {
		mainStage.close();
				
			
		Stage helpStage = new Stage();
		helpPane = new AnchorPane();
		Scene helpScene = new Scene(mainPane, WIDTH, HEIGHT);
				
		createBackground(mainPane);
				
		Text guideOverskrift = new Text();
		guideOverskrift.setText("Instructions");
		guideOverskrift.setFont(Font.loadFont(getClass().getResourceAsStream(FONT_PATH), 40));
		guideOverskrift.setFill(Color.WHITE);
		guideOverskrift.setX(WIDTH/2 - 150);
		guideOverskrift.setY(HEIGHT/6);
				
		Text guide = new Text();
		guide.setText(" The gorilla game is fun and easy to learn! "
							+ "\n You are playing as a monkey and your goal "
							+ "\n is to hit your opponents with your bananas before they hit you. "
							+ "\n In order to throw your banana you have to "
							+ "\n choose an angle and a speed for the throw."
							+ "\n ");
			
		guide.setFont(Font.loadFont(getClass().getResourceAsStream(FONT_PATH), 18));
		guide.setFill(Color.WHITE);
		guide.setX(WIDTH/8);
		guide.setY(HEIGHT/4);
		
		
		helpStage.setOnCloseRequest(e -> {
			e.consume();
			logout(helpStage);
			
			});
		
		createMenuButton(helpStage, mainPane);
			
		helpPane.getChildren().addAll(guide,guideOverskrift);
		
		helpStage.setScene(helpScene);
		helpStage.show();
		});
	}
		
           
private void logout(Stage mainStage)  {
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Exit");
		alert.setHeaderText("Are you sure you want to exit?");
		
		if(alert.showAndWait().get() == ButtonType.OK) {
			mainStage.close();
		}
}
		
		
	private void createExitButton() throws FileNotFoundException {
		GorillaButton exitButton = new GorillaButton ("EXIT");
		addMenuButton(exitButton);
		
		exitButton.setOnAction(d -> {
        	mainStage.close();
        });
	}
	
	private void createTextFields() {
	
		/*
	numberOfPlayers = new TextField();
    addTextfields2(numberOfPlayers,850, 500,"Number of rounds");
		
		width = new TextField();
			addTextfields(width,850, 400, "width");
			

		height = new TextField();
			addTextfields(height,850, 300,"height");
	   */
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
		public static void close() {
			gameStage.close();
			gameStage.setScene(null);
		}
		
		public int getWidth() {
			return gameWidth;
		}
		public int getHeight() {
			return gameHeight;
		}

		public static void start() {
		mainStage.show();
			
		}

	  
	}

