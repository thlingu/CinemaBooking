package de.tlnguyen.cinemabooking;

import de.tlnguyen.cinemabooking.model.Employee;

/**
 * Speichert den aktuell eingeloggten User(Mitarbeiter) und stellt ihn threadsicher zur Verfügung
 */
public class UserManager {
	
	//region 0. Konstanten
	//endregion
	
	//region 1. Attribute
	private static UserManager instance;
	
	private Employee currentEmployee;
	//endregion
	
	//region 2. Konstruktor
	/**
	 * Standardkonstruktor
	 */
	private UserManager() {
		this.currentEmployee = new Employee();
	}
	//endregion
	
	//region 3. Get Instanz
	/**
	 * Gibt einzige Instanz zurück
	 *
	 * @return instance : UserManager : Einzige Instanz
	 */
	public static synchronized UserManager getInstance() {
		
		if (instance == null) {
			instance = new UserManager();
		}
		
		return instance;
	}
	//endregion
	
	//region 4. Getter und Setter
	public Employee getCurrentEmployee() {
		return currentEmployee;
	}
	
	public void setCurrentEmployee(Employee currentEmployee) {
		this.currentEmployee = currentEmployee;
	}
	//endregion
}
