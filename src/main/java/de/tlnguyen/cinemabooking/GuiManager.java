package de.tlnguyen.cinemabooking;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Dieser Singleton verwaltet threadsicher alle Guis zum Oeffnen.
 */
public class GuiManager {
	
	//region 0. Konstanten
	public static final int    SEAT_OPTION_SCENE_WIDTH     = 800;
	public static final int    SEAT_OPTION_SCENE_HEIGHT    = 800;
	public static final String GUI_SEAT_OPTION_LAYOUT_FXML = "gui_seat_option_layout.fxml";
	public static final String GUI_LOGIN_LAYOUT_FXML       = "gui_login_layout.fxml";
	public static final String APPLICATION_NAME            = "Cinema Booking System";
	//endregion
	
	//region 1. Decl and Init Attribute
	private static GuiManager instance;
	private        Stage      primaryStage;
	//endregion
	
	//region 2. Konstruktoren
	
	/**
	 * Standardkonstruktor
	 */
	private GuiManager() {
		//Nichts zu tun ausser privat zu sein
	}
	//endregion
	
	//region 3. Get Instance
	
	/**
	 * Gibt die einzige Instanz dieser Klasse
	 * synchronisiert und somit Threadsicher zurueck
	 *
	 * @return instance : {@link GuiManager} : Einzige threadsichere Instanz
	 */
	public static synchronized GuiManager getInstance() {
		
		if (instance == null) {
			instance = new GuiManager();
		}
		
		return instance;
	}
	//endregion
	
	//region 4. Getter und Setter
	public Stage getPrimaryStage() {
		return primaryStage;
	}
	
	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}
	//endregion
	
	//region 5. Methoden und Funktionen
	public void openGuiSeatOption() {
		try {
			
			FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(GUI_SEAT_OPTION_LAYOUT_FXML));
			Scene      scene      = new Scene(fxmlLoader.load(), SEAT_OPTION_SCENE_WIDTH, SEAT_OPTION_SCENE_HEIGHT);
			
			this.primaryStage.setTitle(APPLICATION_NAME);
			this.primaryStage.setScene(scene);
			this.primaryStage.show();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
	public void openGuiLogin() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(GUI_LOGIN_LAYOUT_FXML));
			
			Scene scene = new Scene(fxmlLoader.load(), SEAT_OPTION_SCENE_WIDTH, SEAT_OPTION_SCENE_HEIGHT);
			
			this.primaryStage.setTitle(APPLICATION_NAME);
			this.primaryStage.setScene(scene);
			this.primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//endregion
}