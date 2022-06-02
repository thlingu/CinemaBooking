module de.tlnguyen.cinemabooking {
	requires javafx.controls;
	requires javafx.fxml;
	requires java.sql;
	
	
	opens de.tlnguyen.cinemabooking to javafx.fxml;
	exports de.tlnguyen.cinemabooking;
}