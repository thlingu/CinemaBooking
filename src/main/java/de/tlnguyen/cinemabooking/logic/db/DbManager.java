package de.tlnguyen.cinemabooking.logic.db;

import de.tlnguyen.cinemabooking.model.Employee;
import de.tlnguyen.cinemabooking.model.Seat;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLNonTransientConnectionException;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO 0. org.mariadb.jdbc:mariadb-java-client:3.0.0-alpha als maven- dependenci hinzufuegen
 * TODO 0. requires java.sql; in Modulinfo einfuegen
 * Threadsicher Zugriff auf die Datenbank
 */
public class DbManager {
	
	//region 0.Konstanten
	private static final String JDBC_DRIVER = "org.mariadb.jdbc.Driver";
	
	private static final String DB_LOCAL_SERVER_IP_ADDRESS = "localhost";
	private static final String DB_LOCAL_NAME              = "/cinemabooking";
	
	private static final String DB_LOCAL_CONNECTION_URL =
			"jdbc:mariadb://" + DB_LOCAL_SERVER_IP_ADDRESS + DB_LOCAL_NAME;
	
	private static final String DB_LOCAL_USER_NAME = "root";
	private static final String DB_LOCAL_USER_PW   = "";
	
	
	
	//endregion
	
	//region 1. Decl. and Init Attribute
	private static DbManager instance;
	/**
	 * Zugriff auf die Datenbanktabelle tblUser
	 */
	private        DaoSeats  daoSeats;
	private  DaoPersonnel daoPersonnel;
	//endregion
	
	//region 2. Konstruktoren
	
	/**
	 * Standardkonstruktor
	 */
	private DbManager() {
		this.daoSeats = new DaoSeats();
		this.daoPersonnel = new DaoPersonnel();
	}
	
	//endregion
	
	//region 3. Get Instanz
	/**
	 * Gibt einzige Instanz zurück
	 *
	 * @return instance : DbManager : Einzige Instanz
	 */
	public static synchronized DbManager getInstance() {
		if (instance == null) {
			instance = new DbManager();
		}
		
		return instance;
	}
	//endregion
	
	//region 4. Database Connection
	
	/**
	 * Gibt eine generiert Datenbankverbindung mit Lese(r) als auch Schreibrechten(w)
	 * zurueckt oder null sollte etwas schiefgelaufen sein.
	 *
	 * @return rwDbConnection : {@link Connection} : Verbindung zur Datenbank mit rw - Rechten
	 */
	private Connection getRwDbConnection() throws Exception {
		Connection rwDbConnection = null;
		
		try {
			//: Registeren des JDBC driver
			Class.forName(JDBC_DRIVER);
			
			//2. Offenen einer Verbindung
			rwDbConnection = DriverManager.getConnection(DB_LOCAL_CONNECTION_URL, DB_LOCAL_USER_NAME, DB_LOCAL_USER_PW);
			
		} catch (SQLNonTransientConnectionException sqlNoConnectionEx) {
			throw new Exception("Keine Datenbankverbindung");
		} catch (ClassNotFoundException classNotFoundEx) {
			throw new Exception("JDBC Treiber konnte nicht geladen werden");
		}
		
		return rwDbConnection;
	}
	
	/**
	 * Checkt ob die Datenbank online ist oder nicht
	 *
	 * @return isOnline : boolean : true : Db ist Online : false nicht
	 */
	public boolean isDatabaseOnline() {
		boolean isOnline = true;
		try {
			this.getRwDbConnection().close();
		} catch (Exception e) {
			e.printStackTrace();
			isOnline = false;
		}
		return isOnline;
	}
	//endregion
	
	//region 5. CREATE - Operationen
	//Insert Seat into Db Table
	/**
	 * Einfuegen eines einzelnen {@link de.tlnguyen.cinemabooking.model.Seat}s in die Datenbank
	 *
	 * @param seatToInsert : {@link de.tlnguyen.cinemabooking.model.Seat} : Zum einfuegen in die Datenbank
	 */
	public void insertSeatIntoDbTbl(Seat seatToInsert) {
		
		try {
			if (this.isDatabaseOnline()) {
				//Neue Verbindung erstellen
				this.daoSeats.insertDataRecordIntoDbTbl(this.getRwDbConnection(), seatToInsert);
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	//Insert Employee into Db Table
	/**
	 * Einfuegen eines einzelnen {@link de.tlnguyen.cinemabooking.model.Employee}s in die Datenbank
	 *
	 * @param employeeToInsert : {@link de.tlnguyen.cinemabooking.model.Employee} : Zum Einfuegen in die Datenbank
	 */
	public void insertEmployeeIntoDbTbl(Employee employeeToInsert) {
		
		try {
			if (this.isDatabaseOnline()) {
				//Neue Verbindung erstellen
				this.daoPersonnel.insertDataRecordIntoDbTbl(this.getRwDbConnection(), employeeToInsert);
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	//Insert List of Seats
	/**
	 * Einfuegen meherer {@link Seat} in Datenbank
	 *
	 * @param seatsToInsert : {@link List} - {@link Seat} : Reise zum einfuegen in die Datenbannk
	 */
	public void insertSeatsIntoDbTbl(List<Seat> seatsToInsert) {
		
		try {
			if (this.isDatabaseOnline()) {
				this.daoSeats.insertDataRecordsIntoDbTbl(this.getRwDbConnection(), seatsToInsert);
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	//Insert List of Employees
	/**
	 * Einfuegen meherer {@link Employee} in Datenbank
	 *
	 * @param employeeToInsert : {@link List} - {@link Employee} : Reise zum einfuegen in die Datenbannk
	 */
	public void insertEmployeesIntoDbTbl(List<Employee> employeeToInsert) {
		
		try {
			if (this.isDatabaseOnline()) {
				this.daoPersonnel.insertDataRecordsIntoDbTbl(this.getRwDbConnection(), employeeToInsert);
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	//endregion
	
	//region 6. READ-Operationen
	//Get All Seats
	/**
	 * Liest alle Daten aus der Testtabelle aus
	 *
	 * @return allUsersFromDbTable : {@link List} - {@link Seat}: Alle Noizen aus Db-Tabelle
	 */
	public List<Seat> getAllSeatsFromDb() {
		//Neue Verbindung erstellen
		List<Seat> allSeatsFromDb = new ArrayList<>();
		
		try {
			if (this.isDatabaseOnline()) {
				allSeatsFromDb = this.daoSeats.getAllDataRecordsFromDbTbl(this.getRwDbConnection());
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		
		return allSeatsFromDb;
	}
	
	//Get All Employees
	/**
	 * Liest alle Daten aus der Testtabelle aus
	 *
	 * @return allUsersFromDbTable : {@link List} - {@link Employee}: Alle Notizen aus Db-Tabelle
	 */
	public List<Employee> getAllEmployeesFromDb() {
		//Neue Verbindung erstellen
		List<Employee> allEmployeesFromDb = new ArrayList<>();
		
		try {
			if (this.isDatabaseOnline()) {
				allEmployeesFromDb = this.daoPersonnel.getAllDataRecordsFromDbTbl(this.getRwDbConnection());
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		
		return allEmployeesFromDb;
	}
	
	//Get Seat by Id
	/**
	 * Liest eine bestimmtte Reise aus der Datenbank aus.
	 *
	 * @param iId : int : Id der Noiz die auslgesen werden soll
	 *
	 * @return specificTripFromDbById : {@link Seat} : Ausgelsene Reise oder ein leeres Objekt
	 * sollte die Reise nicht gefunden werden.
	 */
	public Seat getSeatById(int iId) {
		Seat specificSeatFromDbById = new Seat();
		
		try {
			if (this.isDatabaseOnline()) {
				//Neue Verbindung erstellen
				specificSeatFromDbById =
						this.daoSeats.getSpecificDataRecordFromDbTblById(this.getRwDbConnection(), iId);
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		
		return specificSeatFromDbById;
	}
	
	//Get Seat by Employee
	/**
	 * Liest eine bestimmtte Reise aus der Datenbank aus.
	 *
	 * @param iId : int : Id der Noiz die auslgesen werden soll
	 *
	 * @return specificTripFromDbById : {@link Employee} : Ausgelsene Reise oder ein leeres Objekt
	 * sollte die Reise nicht gefunden werden.
	 */
	public Employee getEmployeeById(int iId) {
		Employee specificEmployeeFromDbById = new Employee();
		
		try {
			if (this.isDatabaseOnline()) {
				//Neue Verbindung erstellen
				specificEmployeeFromDbById =
						this.daoPersonnel.getSpecificDataRecordFromDbTblById(this.getRwDbConnection(), iId);
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		
		return specificEmployeeFromDbById;
	}
	//endregion
	
	//region 7. UPDATE - Operationen
	//Update Seat
	/**
	 * Updated den mitgegeben TripLernideen in der Datenbank.
	 *
	 * @param seatToUpdate : {@link Seat} : Reise deren Daten geaendert werden sollen.
	 */
	public void updateSeatInDbTbl(Seat seatToUpdate) {
		try {
			if (this.isDatabaseOnline()) {
				//Neue Verbindung erstellen
				this.daoSeats.updateDataRecordIntoDbTbl(this.getRwDbConnection(), seatToUpdate);
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	//Update Employee
	/**
	 * Updated den mitgegeben TripLernideen in der Datenbank.
	 *
	 * @param employeeToUpdate : {@link Employee} : Reise deren Daten geaendert werden sollen.
	 */
	public void updateEmployeeInDbTbl(Employee employeeToUpdate) {
		try {
			if (this.isDatabaseOnline()) {
				//Neue Verbindung erstellen
				this.daoPersonnel.updateDataRecordIntoDbTbl(this.getRwDbConnection(), employeeToUpdate);
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	//Update Seats
	/**
	 * Update der mitgegeben Reisen in der Datenbank.
	 *
	 * @param seatsToUpdate : {@link List} - {@link Seat} : Reisen deren Daten geaendert werden sollen.
	 */
	public void updateSeatsInDbTbl(List<Seat> seatsToUpdate) {
		try {
			if (this.isDatabaseOnline()) {
				//Neue Verbindung erstellen
				this.daoSeats.updateDataRecordsIntoDbTbl(this.getRwDbConnection(), seatsToUpdate);
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	//Update Employees
	/**
	 * Update der mitgegebenen Reisen in der Datenbank.
	 *
	 * @param employeesToUpdate : {@link List} - {@link Seat} : Reisen deren Daten geaendert werden sollen.
	 */
	public void updateEmployeesInDbTbl(List<Employee> employeesToUpdate) {
		try {
			if (this.isDatabaseOnline()) {
				//Neue Verbindung erstellen
				this.daoPersonnel.updateDataRecordsIntoDbTbl(this.getRwDbConnection(), employeesToUpdate);
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	//endregion
	
	//region 8. DELETE - Operationen
	//Delete Seat
	/**
	 * Loescht den {@link Seat} mit der uebergenen id aus
	 * der Datenbanktabelle
	 *
	 * @param iId : int : Id der {@link Seat} die geloescht werden soll
	 */
	public void deleteSeatInDbTblById(int iId) {
		//Neue Verbindung erstellen
		try {
			if (this.isDatabaseOnline()) {
				this.daoSeats.deleteDataRecordInDbTblById(this.getRwDbConnection(), iId);
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	//Delete Employee
	/**
	 * Loescht den {@link Employee} mit der uebergenen id aus
	 * der Datenbanktabelle
	 *
	 * @param iId : int : Id der {@link Employee} die geloescht werden soll
	 */
	public void deleteEmployeeInDbTblById(int iId) {
		//Neue Verbindung erstellen
		try {
			if (this.isDatabaseOnline()) {
				this.daoPersonnel.deleteDataRecordInDbTblById(this.getRwDbConnection(), iId);
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	//Delete Seats
	/**
	 * Loescht alle {@link Seat}s
	 * der Datenbanktabelle
	 */
	public void deleteAllSeatsInDbTbl() {
		//Neue Verbindung erstellen
		try {
			if (this.isDatabaseOnline()) {
				this.daoSeats.deleteAllDataRecordsInDbTbl(this.getRwDbConnection());
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	//Delete Employees
	/**
	 * Loescht alle {@link Employee}s
	 * der Datenbanktabelle
	 */
	public void deleteAllEmployeesInDbTbl() {
		//Neue Verbindung erstellen
		try {
			if (this.isDatabaseOnline()) {
				this.daoPersonnel.deleteAllDataRecordsInDbTbl(this.getRwDbConnection());
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	//endregion
}
