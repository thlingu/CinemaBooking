package de.tlnguyen.cinemabooking;

import de.tlnguyen.cinemabooking.helper.NumberHelper;
import de.tlnguyen.cinemabooking.logic.db.DbManager;
import de.tlnguyen.cinemabooking.model.Employee;
import de.tlnguyen.cinemabooking.model.Seat;
import de.tlnguyen.cinemabooking.settings.Texts;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Nimmt die Events der Sitzplatzauswahl entgegen und
 * leitet die weitere Logik ein.
 */
public class GuiSeatOptionController implements Initializable {
	
	//region 0. Konstanten
	public static final boolean SEAT_AVAILABLE                   = false;
	public static final int     NUMBER_OF_COLUMNS                = 10;
	public static final int     NUMBER_OF_ROWS                   = 5;
	public static final int     TICKET_PRICE                     = 5;
	public static final int     DISCOUNT_TICKET_PRICE_DIFFERENCE = 2;
	public static final int     SHOWTIME_HOUR                    = 20;
	public static final int     SHOWTIME_MINUTE                  = 0;
	
	//region 1. Attribute
	@FXML
	private GridPane gpSeats;
	
	@FXML
	private VBox vbSeats;
	
	@FXML
	private Label lblPrice;
	
	@FXML
	private Label lblNumberOfTickets;
	
	@FXML
	private Label lblTotalPrice;
	
	@FXML
	private Label lblMovieName;
	
	@FXML
	private Label lblMovieShowtime;
	
	@FXML
	private Label lblSubtotal;
	
	@FXML
	private Label lblNumberOfDiscountTickets;
	
	@FXML
	private Label lblTotalDiscount;
	
	@FXML
	private Label lblDiscountPrice;
	
	private List<Seat> seats;
	
	private int numberOfSelectedSeats;
	
	private List<Seat> currentListOfSeats;
	
	private Employee currentEmployee;
	
	private int numberOfDiscountTickets;
	//endregion
	
	//region 2. Initialize
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		
		this.seats = DbManager.getInstance().getAllSeatsFromDb();
		
		if (this.seats.isEmpty()) {
			
			this.insertNewButtonsToDbTbl();
		} else {
			
			Date now = new Date();
			
			//c.setTime(this.seats.get(0).getDateTime());
			Date newDay = this.getNextDay(this.seats.get(0).getDateTime());
			
			//wenn die letzte Vorstellung bereits begonnen hat (zuletzt gespeichertes Datum + 24 Std)
			//dann soll der Saalplan sich automatisch erneuern
			//Alle Sitze werden wieder freigegeben
			//Setzt nächste Vorstellung (heute noch oder morgen, je nach aktueller Uhrzeit)
			//Alle 24 Std. beginnt eine neue Vorstellung (pro Tag wird im Kino 1 Film gezeigt)
			if (now.after(newDay)) {
				
				for (Seat seat : this.seats) {
					
					seat.setBooked(SEAT_AVAILABLE);
					seat.setDateTime(this.getNextShowtime());
					seat.setEmployee(new Employee());
				}
				
				DbManager.getInstance().updateSeatsInDbTbl(this.seats);
			} else {
				
				for (Seat seat : this.seats) {
					
					int currentPersonnelId = seat.getEmployee().getId();
					
					if (currentPersonnelId != -1) {
						
						//Mitarbeiterdaten von bereits gebuchten Plätzen werden mithilfe der Id aus der Datenbank gelesen
						seat.setEmployee(DbManager.getInstance().getEmployeeById(currentPersonnelId));
					}
				}
			}
		}
		
		this.currentEmployee = UserManager.getInstance().getCurrentEmployee(); //aktuell eingeloggter Mitarbeiter
		this.currentListOfSeats = new ArrayList<>(); //Liste mit allen ausgewählten (markierten) Sitzen
		this.numberOfSelectedSeats = 0; //Anzahl der für die Buchung ausgewählten Sitze
		this.numberOfDiscountTickets = 0;
		this.setMovieName();
		this.setMovieShowtime();
		this.setTicketPrices();
		this.initializeButtons();
	}
	//endregion
	
	//region 3. Insert New Seats To Db-Table
	/**
	 * Speichert Sitze in die Datenbank / legt Daten in Tabelle seats an
	 */
	private void insertNewButtonsToDbTbl() {
		
		for (int rowNumber = 1; rowNumber <= NUMBER_OF_ROWS; rowNumber++) {
			
			for (int seatNumber = 1; seatNumber <= NUMBER_OF_COLUMNS; seatNumber++) {
				
				Seat seatToInsertIntoDbTbl =
						new Seat(seatNumber, SEAT_AVAILABLE, rowNumber, seatNumber, this.getShowtimeToday(), new Employee());
				
				DbManager.getInstance().insertSeatIntoDbTbl(seatToInsertIntoDbTbl);
			}
		}
	}
	//endregion
	
	//region 4. Hilfsmethoden und Funktionen
	/**
	 * Vergleicht die aktuelle Uhrzeit mit der geplanten Vorstellungszeit und gibt entsprechend die naechste Vorstellung
	 * zurueck - heute noch oder morgen zur (gleichen) Vorstellungszeit
	 * @return : {@link Date}
	 */
	private Date getNextShowtime() {
		
		Date nextShowtime = this.getShowtimeToday();
		
		SimpleDateFormat parser = new SimpleDateFormat("HH:mm");
		
		try {
			Date compareShowtimeHour = parser.parse("20:00");
			Date compareNowHour = parser.parse(LocalDateTime.now().getHour()+ ":00");
			
			if (compareNowHour.after(compareShowtimeHour)) {
				
				nextShowtime = getNextDay(nextShowtime);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return nextShowtime;
	}
	
	/**
	 * gibt das Vorstellungsdatum und die -zeit von heute zurueck
	 * @return : {@link Date}
	 */
	private Date getShowtimeToday() {
		
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, SHOWTIME_HOUR);
		cal.set(Calendar.MINUTE, SHOWTIME_MINUTE);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		
		return cal.getTime();
	}
	
	/**
	 * rechnet genau 24 Std dem Eingabedatum hinzu und gibt das neue Datum zurueck
	 * @param dateBefore : Datum, zu dem 1 Tag addiert wird
	 * @return : {@link Date} : naechster Tag, genau 24 Std. spaeter
	 */
	private Date getNextDay(Date dateBefore) {
		
		Calendar c = Calendar.getInstance();
		c.setTime(dateBefore);
		c.add(Calendar.DATE, 1);
		
		return c.getTime();
	}
	
	private void setMovieName() {
		
		this.lblMovieName.setText(Texts.CURRENT_MOVIE_NAME);
		this.lblMovieName.setTextFill(Color.RED);
	}
	
	private void setMovieShowtime() {
		
		String           pattern          = Texts.GUI_DATE_TIME_FORMAT_PATTERN;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		
		Date movieShowtime = this.seats.get(0).getDateTime();
		
		this.lblMovieShowtime.setText(simpleDateFormat.format(movieShowtime) + " Uhr");
		this.lblMovieShowtime.setTextFill(Color.RED);
	}
	
	private void setTicketPrices() {
		
		this.lblPrice.setText(NumberHelper.getBeautifulDoubleCurrencyValue(TICKET_PRICE));
		this.lblDiscountPrice.setText(NumberHelper.getBeautifulDoubleCurrencyValue(TICKET_PRICE - DISCOUNT_TICKET_PRICE_DIFFERENCE));
	}
	//endregion
	
	//region 5. Initialize Buttons in Gui
	/**
	 * Generiert dynamisch Sitze(Buttons) in ein Gridpane in der GUI
	 */
	private void initializeButtons() {
		
		//Vbox Border anzeigen
		this.vbSeats.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, null)));
		
		//Gridpane-Reihen und -Spalten hinzufuegen
		for (int rowCount = 0; rowCount < NUMBER_OF_ROWS; rowCount++) {
			
			RowConstraints rowConst = new RowConstraints();
			rowConst.setPrefHeight(50);
			this.gpSeats.getRowConstraints().add(rowConst);
		}
		
		for (int columnCount = 0; columnCount < NUMBER_OF_COLUMNS; columnCount++) {
			
			ColumnConstraints colConst = new ColumnConstraints();
			colConst.setPrefWidth(50);
			this.gpSeats.getColumnConstraints().add(colConst);
		}
		
		this.gpSeats.setAlignment(Pos.CENTER);
		this.gpSeats.getChildren().clear();
		
		//Buttons generieren, formatieren und ins Gridpane setzen
		int listCount = 0;
		
		for (int rowCount = 0; rowCount < this.gpSeats.getRowCount(); rowCount++) {
			
			for (int columnCount = 0; columnCount < this.gpSeats.getColumnCount(); columnCount++) {
				
				Seat tempSeat = this.seats.get(listCount);
				tempSeat.setPrefWidth(30);
				tempSeat.setPrefHeight(30);
				
				//X setzen, wenn der Platz bereits verkauft wurde / belegt ist
				if (tempSeat.isBooked()) {
					
					tempSeat.setText(Texts.SEAT_TEXT_X);
				}
				
				tempSeat.setOnAction(actionEvent -> onSeatButtonClick(actionEvent));
				this.gpSeats.add(tempSeat, columnCount, rowCount);
				
				this.gpSeats.setHalignment(tempSeat, HPos.CENTER);
				this.gpSeats.setValignment(tempSeat, VPos.CENTER);
				
				listCount++;
			}
		}
	}
	
	private void setTotalPriceLabel() {
		
		int totalDiscount = DISCOUNT_TICKET_PRICE_DIFFERENCE * this.numberOfDiscountTickets;
		this.lblTotalDiscount.setText(NumberHelper.getBeautifulDoubleCurrencyValue(totalDiscount * (-1)));
		
		int totalPrice = TICKET_PRICE * this.numberOfSelectedSeats;
		totalPrice -= totalDiscount;
		this.lblTotalPrice.setText(NumberHelper.getBeautifulDoubleCurrencyValue(totalPrice));
		this.lblTotalPrice.setTextFill(Color.RED);
	}
	//endregion
	
	//region 6. On Seat Button Click
	/**
	 * Markiert oder entfernt Markierung des ausgewählten Sitzplatzes und
	 * setzt die entsprechende Anzahl an Plätzen/Tickets sowie die zu zahlende Zwischensumme,
	 * welche auf der GUI angezeigt werden
	 * @param ae : {@link ActionEvent} : das Event, welches durch den Button-Click erzeugt und weitergegeben wird
	 */
	private void onSeatButtonClick(ActionEvent ae) {
		
		if (ae.getSource() instanceof Seat) {
			
			Seat seat               = (Seat) ae.getSource(); //angeklickter Sitz-Button
			int  indexOfCurrentSeat = this.seats.indexOf(seat);
			
			//Falls der Sitz noch nicht gebucht/belegt ist, kann man ihn beliebig oft an- und abwählen
			if (!(this.seats.get(indexOfCurrentSeat).isBooked() && !this.currentListOfSeats.contains(seat))) {
				
				seat.setTextFill(Color.RED); //zur Unterscheidung von den bereits gebuchten Plätzen
				
				if (!seat.getText().equals(Texts.SEAT_TEXT_X)) { //wenn noch nicht markiert, markiere mit X
					
					this.numberOfSelectedSeats++;
					seat.setText(Texts.SEAT_TEXT_X);
					seat.setBooked(true);
					this.currentListOfSeats.add(seat);
				} else {
					
					this.numberOfSelectedSeats--;
					seat.setText(Texts.SEAT_TEXT_BLANK);
					seat.setBooked(false);
					this.currentListOfSeats.remove(seat);
				}
			}
		}
		
		this.lblNumberOfTickets.setText(String.valueOf(this.numberOfSelectedSeats));
		
		int subtotalPrice = this.numberOfSelectedSeats * TICKET_PRICE;
		this.lblSubtotal.setText(NumberHelper.getBeautifulDoubleCurrencyValue(subtotalPrice));
		
		this.setTotalPriceLabel();
	}
	//endregion
	
	//region 7. Book
	/**
	 * Sichert die Buchung und zeigt die gebuchten Plätze in numerischer Reihenfolge nach Reihe und Sitz sortiert an
	 * @param actionEvent: {@link ActionEvent} : keine Verwendung
	 */
	public void book(ActionEvent actionEvent) {
		
		if (this.currentListOfSeats.isEmpty()) {
			
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle(Texts.VOID_SELECTION);
			alert.setHeaderText(null);
			alert.setContentText(Texts.USER_MSG_SELECT_SEATS);
			alert.showAndWait();
		} else {
			
			for (Seat seat : this.currentListOfSeats) {
				
				seat.setEmployee(this.currentEmployee);
				
				DbManager.getInstance().updateSeatInDbTbl(seat);
				
				seat.setTextFill(Color.BLACK);
			}
			
			//Buchungsbestätigung anzeigen
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle(Texts.BOOKING_CONFIRMATION);
			alert.setHeaderText(Texts.BOOKING_SUCCESSFULL);
			
			//Gebuchte Plätze zuerst nach Reihe, dann nach Sitz sortieren
			this.currentListOfSeats.sort(Comparator.comparing(Seat::getRow));
			
			List<List<Seat>> sortingList     = new ArrayList<>();
			List<Seat>       firstListInList = new ArrayList<>();
			sortingList.add(firstListInList);
			int countRow   = 0;
			int tempRow = this.currentListOfSeats.get(0).getRow();
			
			//alle Reihen mit ihren Sitzen jeweils in einer Liste speichern, welche wiederum in einer Liste zusammengefasst werden
			for (Seat seat : this.currentListOfSeats) {
				
				if (tempRow == seat.getRow()) {
					
					sortingList.get(countRow).add(seat);
				} else {
					
					sortingList.add(new ArrayList<>());
					tempRow = seat.getRow();
					countRow++;
					
					sortingList.get(countRow).add(seat);
				}
			}
			
			//Jede Liste in der Liste nach Sitznummer sortieren
			for (List<Seat> list : sortingList) {
				
				list.sort(Comparator.comparing(Seat::getNumber));
			}
			
			//Ausgabestring (Infotext) Zeile für Zeile generieren
			String allBookedSeats = "";
			
			for (List<Seat> list : sortingList) {
				
				for (Seat seat : list) {
					
					allBookedSeats += Texts.ROW + seat.getRow()
							+ Texts.SEAT + seat.getNumber() + "\n";
				}
			}
			
			alert.setContentText(Texts.BOOKED_SEATS +
					allBookedSeats);
			
			alert.showAndWait();
			
			//Zahlungsuebersicht zuruecksetzen
			this.lblNumberOfTickets.setText(String.valueOf(0));
			this.lblNumberOfDiscountTickets.setText(String.valueOf(0));
			this.lblSubtotal.setText(NumberHelper.getBeautifulDoubleCurrencyValue(0));
			this.lblTotalDiscount.setText(NumberHelper.getBeautifulDoubleCurrencyValue(0));
			this.lblTotalPrice.setText(NumberHelper.getBeautifulDoubleCurrencyValue(0));
			
			this.currentListOfSeats.clear();
			this.numberOfSelectedSeats = 0;
			this.numberOfDiscountTickets = 0;
		}
	}
	//endregion
	
	//region 8. Logout
	public void logout(ActionEvent actionEvent) {
		
		UserManager.getInstance().setCurrentEmployee(new Employee());
		this.currentEmployee = new Employee();
		
		GuiManager.getInstance().openGuiLogin();
	}
	//endregion
	
	//region 9. Andere Button-Funktionen
	public void reduceDiscountTicket(ActionEvent actionEvent) {
		
		if (this.numberOfDiscountTickets != 0) {
			
			this.lblNumberOfDiscountTickets.setText(String.valueOf(--this.numberOfDiscountTickets));
			
			this.setTotalPriceLabel();
		}
	}
	
	public void addDiscountTicket(ActionEvent actionEvent) {
		
		if (this.numberOfDiscountTickets < this.numberOfSelectedSeats) {
			
			this.lblNumberOfDiscountTickets.setText(String.valueOf(++this.numberOfDiscountTickets));
			
			this.setTotalPriceLabel();
		}
	}
	//endregion
}
