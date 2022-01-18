import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
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
	
	static int gameWidth = 800;
	static int gameHeight = 600;
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
	
	int y = 245;
	int lblY = 250;
	protected double playerY = 325;
	
	static ChoiceBox<String> color,color1,gravity;
	static String monkey1Color,monkey2Color;
	
	
	public ViewManager() throws FileNotFoundException {
		menuButtons = new ArrayList<>();
		mainPane = new AnchorPane();
		mainScene = new Scene(mainPane,WIDTH,HEIGHT);
		mainStage = new Stage(); 
		mainStage.setScene(mainScene);
		createButtons();
		createBackground(mainPane);
		//createTextFields();
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
		  
		startPane.getChildren().addAll(text,lblName);	
	}
	
	
	private void addTextfields3(ChoiceBox<String> gravity, String[] levels, String string, String string2) {
		gravity.setValue(string2);
		gravity.getItems().addAll(levels);
		gravity.getSelectionModel();
		//combo2.getItems().addAll(string);
		gravity.setLayoutX(780);
        gravity.setLayoutY(y);
        gravity.setPrefSize(200, 35);
        gravity.setOnAction(null);
       
        y+=100;
       
        Label Lbl = new Label();
        Lbl.setText(string);
        Lbl.setLayoutX(550);
        Lbl.setLayoutY(lblY);
        Lbl.setMinWidth(75); 
        Lbl.setTextFill(Color.WHITE);
        Lbl.setFont(Font.loadFont(getClass().getResourceAsStream(FONT_PATH), 18));
		
        lblY += 100;
        
        startPane.getChildren().addAll(gravity,Lbl);
		
	}
	
	private void createButtons() throws FileNotFoundException {
		createStartButton();
		createScoresButton();	
		createHelpButton();
		//createSettingsButton();
		createExitButton();
	//	createTextFields();

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

	     
	            gravity = new ChoiceBox();
	            String[] levels = {"Earth = 9,81 m/s^2","Mars = 3,72 m/s^2","Moon = 1,62 m/s^2"};
	            addTextfields3(gravity,levels,"Select gravity","Earth = 9,81 m/s^2");
	            
	            
	            color = new ChoiceBox();
	            String[] colors = {"Black","Blue","Red"};
	            addTextfields3(color,colors,"Select color","Blue");
	            
	            
	            
	            color1 = new ChoiceBox();	          
	            String[] colors1 = {"black","blue","red"};
	            addTextfields3(color1,colors1,"Select color","Red");
	            
	   
	            createPlayerLabel("Player 1");
	            createPlayerLabel("Player 2");
	            createTextFields();
	            
    			
    			GorillaButton play = new GorillaButton ("PLAY");
    			play.setLayoutX(1024/2-95);
    			play.setLayoutY(550);
	    		
    			startPane.getChildren().addAll(guideOverskrift,play);
 	            
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

		private void createPlayerLabel(String player) {
			Label player1 = new Label();
            player1.setText(player);
            player1.setLayoutX(780);
            player1.setLayoutY(playerY );
            player1.setMinWidth(75); 
            player1.setTextFill(Color.WHITE);
            player1.setFont(Font.loadFont(getClass().getResourceAsStream(FONT_PATH), 13));
            startPane.getChildren().add(player1);
            playerY+=100;
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
		Scene helpScene = new Scene(helpPane, WIDTH, HEIGHT);
				
		createBackground(helpPane);
				
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
		
		createMenuButton(helpStage,helpPane);
			
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
	
		numberOfRounds = new TextField();
		addTextfields(numberOfRounds,350,440,50,450,"Number of rounds");
		
		width = new TextField();
		addTextfields(width,350,240,50,250, "Please select width");

		height = new TextField();
		addTextfields(height,350,340,50,350,"Please select height");
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

		public static String getMonkeyColor1() {
			monkey1Color = color.getValue().toString();
			return "monkey_throw_"+monkey1Color.toLowerCase()+".png";
		}

		public static String getMonkeyColor2() {
			monkey2Color = color1.getValue().toString();
			return "monkey_throw_"+monkey2Color.toLowerCase()+"2.png";
		}


	  
	}

