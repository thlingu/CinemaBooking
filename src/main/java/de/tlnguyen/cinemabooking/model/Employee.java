package de.tlnguyen.cinemabooking.model;

/**
 * Stellt einen Mitarbeiter des Kinos dar.
 */
public class Employee {
	
	//region 0. Konstanten
	private static final Integer DEFAULT_ID         = -1;
	private static final String DEFAULT_USER_NAME    = ">noUserNameSetYet<";
	private static final String DEFAULT_ENCRYPTED_PW = ">noPwSetYet<";
	private static final String DEFAULT_FIRST_NAME   = ">noFirstNameSetYet<";
	private static final String  DEFAULT_LAST_NAME  = ">noLastNameSetYet<";
	
	//region 1. Decl and Init Attribute
	private Integer id;
	private String userName;
	private String encryptedPw;
	private String firstName;
	private String  lastName;
	//endregion
	
	//region 2. Konstruktoren
	
	/**
	 * Standardkonstruktor
	 * zum direkten Initialisieren
	 * der Attribute
	 */
	public Employee() {
		this.id = DEFAULT_ID;
		this.userName = DEFAULT_USER_NAME;
		this.encryptedPw = DEFAULT_ENCRYPTED_PW;
		this.firstName = DEFAULT_FIRST_NAME;
		this.lastName = DEFAULT_LAST_NAME;
	}
	
	/**
	 * 2. Konstruktor zum direkten Setzen aller Attribute
	 *
	 * @param id        : {@link Integer} : MitarbeiterId(DbId)
	 * @param userName  : {@link String} : Username des Mitarbeiters fuer den Login
	 * @param pw        : {@link String} : Verschlüsseltes Passwort des Mitarbeiters fuer den Login
	 * @param firstName : {@link String} : Vorname des Mitarbeiters
	 * @param lastName  : {@link String} : Nachname des Mitarbeiters
	 */
	public Employee(Integer id, String userName, String pw, String firstName, String lastName) {
		this.id = id;
		this.userName = userName;
		this.encryptedPw = pw;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	//endregion
	
	//region 3. Getter und Setter
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getEncryptedPw() {
		return encryptedPw;
	}
	
	public void setEncryptedPw(String encryptedPw) {
		this.encryptedPw = encryptedPw;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	//region 4. toString Alt+Einfg
	@Override
	public String toString() {
		return "Employee{" +
				"id=" + id +
				", userName='" + userName + '\'' +
				", pw='" + encryptedPw + '\'' +
				", firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				'}';
	}
	//endregion
}

