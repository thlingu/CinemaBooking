package de.tlnguyen.cinemabooking.model;

import javafx.scene.control.Button;

import java.util.Date;

/**
 * Stellt einen Sitzplatz im Saal dar und
 * leitet sich von der Klasse Button ab.
 */
public class Seat extends Button {
	
	//region 0. Konstanten
	private static final Integer DEFAULT_ID      = -1;
	private static final boolean DEFAULT_BOOKED  = false;
	private static final int     DEFAULT_ROW     = -1;
	private static final int     DEFAULT_NUMBER  = -1;
	private static final Date    LOCAL_DATE_TIME = new Date();
	
	//region 1. Decl and Init Attribute
	private Integer  id;
	private boolean  isBooked;
	private int      row;
	private int      number;
	private Date     dateTime;
	private Employee employee;
	//endregion
	
	//region 2. Konstruktoren
	
	/**
	 * Standardkonstruktor
	 * zum direkten Initialisieren
	 * der Attribute
	 */
	public Seat() {
		this.id = DEFAULT_ID;
		this.isBooked = DEFAULT_BOOKED;
		this.row = DEFAULT_ROW;
		this.number = DEFAULT_NUMBER;
		this.dateTime = LOCAL_DATE_TIME;
		this.employee = new Employee();
	}
	
	/**
	 * 2. Konstruktor zum direkten Setzen aller Attribute
	 *
	 * @param id       : {@link Integer} : SitzId(DbId)
	 * @param isBooked : {@link boolean} : true, falls der Sitz gebucht/reserviert wurde, false andernfalls
	 * @param row      : {@link int} : Sitzreihe
	 * @param number   : {@link int} : Sitzplatznummer
	 * @param dateTime : {@link Date} : Aktuelles Datum + Uhrzeit der nächsten Vorstellung
	 * @param employee : {@link Employee} : Mitarbeiter/in, welche(r) die Buchung des Sitzes veranlasst hat
	 */
	public Seat(Integer id, boolean isBooked, int row, int number, Date dateTime, Employee employee) {
		this.id = id;
		this.isBooked = isBooked;
		this.row = row;
		this.number = number;
		this.dateTime = dateTime;
		this.employee = employee;
	}
	//endregion
	
	//region 3. Getter und Setter
	
	public Integer getSeatId() {
		return id;
	}
	
	public void setSeatId(Integer id) {
		this.id = id;
	}
	
	public boolean isBooked() {
		return isBooked;
	}
	
	public void setBooked(boolean isBooked) {
		this.isBooked = isBooked;
	}
	
	public int getRow() {
		return row;
	}
	
	public void setRow(int row) {
		this.row = row;
	}
	
	public int getNumber() {
		return number;
	}
	
	public void setNumber(int number) {
		this.number = number;
	}
	
	public Date getDateTime() {
		return dateTime;
	}
	
	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}
	
	public Employee getEmployee() {
		return employee;
	}
	
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	
	/*public String getBeautifulTaxPrice() {
		return NumberHelper.getBeautifulDoubleCurrencyValue(this.getTaxPrice());
	}*/
	//endregion
	
	//region 4. toString Alt+Einfg
	@Override
	public String toString() {
		return "Seat{" +
				"id=" + id +
				", isBooked=" + isBooked +
				", row=" + row +
				", number=" + number +
				", dateTime=" + dateTime +
				", employee=" + employee +
				'}';
	}
	//endregion
}
