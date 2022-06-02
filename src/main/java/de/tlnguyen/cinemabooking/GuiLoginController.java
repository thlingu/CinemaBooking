package de.tlnguyen.cinemabooking;

import de.tlnguyen.cinemabooking.helper.Encrypter;
import de.tlnguyen.cinemabooking.logic.db.DbManager;
import de.tlnguyen.cinemabooking.model.Employee;
import de.tlnguyen.cinemabooking.settings.Texts;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Nimmt alle Daten des Logins
 * entgegen und leitet die weitere Logik ein.
 */
public class GuiLoginController implements Initializable {
	
	//region 0. Konstanten
	//endregion
	
	//region 1. Attribute
	@FXML
	private TextField txtUserName;
	
	@FXML
	private PasswordField txtPw;
	
	private List<Employee> personnel;
	//endregion
	
	//region 2. Konstruktoren
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		
		//Temporär zum Testen
		this.txtUserName.setText("tlnguyen");
		this.txtPw.setText("helloJava!");
	}
	//endregion
	
	//region 3. Login
	
	public void login() throws Exception {
		
		String inputtedUserName = txtUserName.getText();
		String inputtedPw       = txtPw.getText();
		
		if (inputtedPw.isEmpty() || inputtedPw.isEmpty()) {
			
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle(Texts.EMPTY_INPUT);
			alert.setHeaderText(null);
			alert.setContentText(Texts.USER_MSG_ENTER_DATA);
			alert.showAndWait();
		} else {
			
			String encryptedInputtedPw = Encrypter.getInstance().encryptToSha256HashHexString(inputtedPw);
			
			this.personnel = DbManager.getInstance().getAllEmployeesFromDb();
			
			if (!this.personnel.isEmpty()) {
				
				Employee employee = this.getEmployeeByLoginData(inputtedUserName, encryptedInputtedPw);
				
				if (employee.getId() != -1) {
					
					//Aktuell eingeloggten User(Mitarbeiter) speichern
					UserManager.getInstance().setCurrentEmployee(employee);
					
					GuiManager.getInstance().openGuiSeatOption();
				} else {
					
					Alert alert = new Alert(Alert.AlertType.INFORMATION);
					alert.setTitle(Texts.INVALID_ENTRY);
					alert.setHeaderText(null);
					alert.setContentText(Texts.USER_MSG_INVALID_DATA);
					
					alert.showAndWait();
				}
			} else {
				
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setTitle(Texts.EMPTY_PERSONNEL_TBL);
				alert.setHeaderText(null);
				alert.setContentText(Texts.USER_MSG_NO_PERSONNEL_DATA_SAVED);
				alert.showAndWait();
				
				GuiManager.getInstance().openGuiSeatOption();
			}
		}
	}
	//endregion
	
	//region 4. Hilfsmethoden und -funktionen
	private Employee getEmployeeByLoginData(String inputtedUserName, String encryptedInputtedPw) {
		
		for (Employee e : this.personnel) {
			
			if (e.getUserName().equals(inputtedUserName) && e.getEncryptedPw().equals(encryptedInputtedPw)) {
				
				return e;
			}
		}
		
		return new Employee();
	}
	//endregion
}




