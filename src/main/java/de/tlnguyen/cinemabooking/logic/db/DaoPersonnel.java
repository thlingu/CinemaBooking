package de.tlnguyen.cinemabooking.logic.db;

import de.tlnguyen.cinemabooking.model.Employee;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Diese Klasse ist eine DAO-Klasse.
 * DAO seht fuer Data Access Object.
 * Fuehrt die CRUD-Operationen auf der DbTabelle
 */
public class DaoPersonnel extends ADao <Employee> {
	
	//region 0. Konstanten
	protected static final String COL_NAME_USER_NAME                    = "user_name";
	protected static final String COL_NAME_USER_NAME_INC_COL_BACK_TICKS =
			CHAR_COL_BACK_TICK + COL_NAME_USER_NAME + CHAR_COL_BACK_TICK;
	
	protected static final String COL_NAME_ENCRYPTED_PW                    = "encrypted_pw";
	protected static final String COL_NAME_ENCRYPTED_PW_INC_COL_BACK_TICKS =
			CHAR_COL_BACK_TICK + COL_NAME_ENCRYPTED_PW + CHAR_COL_BACK_TICK;
	
	protected static final String COL_NAME_FIRST_NAME                    = "first_name";
	protected static final String COL_NAME_FIRST_NAME_INC_COL_BACK_TICKS =
			CHAR_COL_BACK_TICK + COL_NAME_FIRST_NAME + CHAR_COL_BACK_TICK;
	
	protected static final String COL_NAME_LAST_NAME                    = "last_name";
	protected static final String COL_NAME_LAST_NAME_INC_COL_BACK_TICKS =
			CHAR_COL_BACK_TICK + COL_NAME_LAST_NAME + CHAR_COL_BACK_TICK;
	
	private static final String TABLE_NAME_PERSONNEL = "personnel";
	//endregion
	
	//region 2. Konstruktor
	public DaoPersonnel() {
		
		super(TABLE_NAME_PERSONNEL);
	}
	//endregion
	
	//region 3. Insert
	
	/**
	 * Fuegt einen einzelnen Datensatz in die Datebanktabelle ein
	 *
	 * @param dbRwConnection          : {@link Connection} : Db-Connection mit Schreib und Lesezugriff
	 * @param employeeToInsertIntoDbTable : {@link Employee : Model welches eingefuegt werden soll
	 */
	@Override
	public void insertDataRecordIntoDbTbl(Connection dbRwConnection, Employee employeeToInsertIntoDbTable) {
		
		//Decl and Init
		Statement dbStatementToExecute = null;
		
		try {
			//1. Db Connection ist bereits von DbManger generiert
			
			//2. Statementobjekt zum tatsaechlichen Ausfuehren des unten als String generierten SQL-Statements
			dbStatementToExecute = dbRwConnection.createStatement();
			
			//3. SQL-String angepasst auf die Tabelle generieren

			/*
			 * INSERT INTO personnel(
			 *      `user_name`,
			 *      `encrypted_pw`,
			 *      `first_name`
			 *      `last_name`
			 * )
			 *  VALUES (
			 *      'username123',
			 *      'a36c101570cc4410993de5385ad7034adb2dae6a05139ac7672577803084634d',
			 *      'Vorname',
			 *      'Nachname',
			 * );
			 */
			String strSqlStmtInsert = INSERT_TBL + this.tableName + CHAR_OPEN_PARENTHESIS
					+ COL_NAME_USER_NAME_INC_COL_BACK_TICKS + CHAR_COMMA
					+ COL_NAME_ENCRYPTED_PW_INC_COL_BACK_TICKS + CHAR_COMMA
					+ COL_NAME_FIRST_NAME_INC_COL_BACK_TICKS + CHAR_COMMA
					+ COL_NAME_LAST_NAME + CHAR_CLOSE_PARENTHESIS
					+ VALUES_OPERATOR + CHAR_OPEN_PARENTHESIS
					+ CHAR_VALUE_SINGLE_QUOTE + employeeToInsertIntoDbTable.getUserName() + CHAR_VALUE_SINGLE_QUOTE + CHAR_COMMA
					+ CHAR_VALUE_SINGLE_QUOTE + employeeToInsertIntoDbTable.getEncryptedPw() + CHAR_VALUE_SINGLE_QUOTE + CHAR_COMMA
					+ CHAR_VALUE_SINGLE_QUOTE + employeeToInsertIntoDbTable.getFirstName() + CHAR_VALUE_SINGLE_QUOTE + CHAR_COMMA
					+ CHAR_VALUE_SINGLE_QUOTE + employeeToInsertIntoDbTable.getLastName() + CHAR_VALUE_SINGLE_QUOTE + CHAR_CLOSE_PARENTHESIS_SEMICOLON;
			
			
			//DEBUG
			System.out.println(">>>>>>>> " + strSqlStmtInsert);
			
			//4. SQL - String an Satement objekt zum ausfuerhren geben
			dbStatementToExecute.execute(strSqlStmtInsert);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
			if (dbStatementToExecute != null) {
				//5. Schliessen der des Statements
				try {
					dbStatementToExecute.close();
				} catch (SQLException sqlEx) {
					sqlEx.printStackTrace();
				}
			}
			
			if (dbRwConnection != null) {
				//6. Schliessen der Verbindung
				try {
					dbRwConnection.close();
				} catch (SQLException sqlEx) {
					sqlEx.printStackTrace();
				}
			}
		}
		
		
	}
	
	/**
	 * Fuegt eine Liste von Datensaetzen in die Datebanktabelle ein
	 *
	 * @param dbRwConnection           : {@link Connection} : Db-Connection mit Schreib und Lesezugriff
	 * @param employeesToInsertIntoDbTable : {@link Employee} : Models welches eingefuegt werden soll
	 */
	@Override
	public void insertDataRecordsIntoDbTbl(Connection dbRwConnection, List<Employee> employeesToInsertIntoDbTable) {
		//Decl and Init
		Statement dbStatementToExecute = null;
		
		try {
			//1. Db Connection ist bereits von DbManger generiert
			
			//2. Statementobjekt zum tatsaechlichen ausfuehren des unten als String generierten SQL-Statements
			dbStatementToExecute = dbRwConnection.createStatement();
			
			//Alle Datensaetze durchlaufen
			for (Employee employeeToInsertIntoDbTable : employeesToInsertIntoDbTable) {
				
				/*
				 * INSERT INTO personnel(
				 *      `user_name`,
				 *      `encrypted_pw`,
				 *      `first_name`
				 *      `last_name`
				 * )
				 *  VALUES (
				 *      'username123',
				 *      'a36c101570cc4410993de5385ad7034adb2dae6a05139ac7672577803084634d',
				 *      'Vorname',
				 *      'Nachname',
				 * );
				 */
				String strSqlStmtInsert = INSERT_TBL + this.tableName + CHAR_OPEN_PARENTHESIS
						+ COL_NAME_USER_NAME_INC_COL_BACK_TICKS + CHAR_COMMA
						+ COL_NAME_ENCRYPTED_PW_INC_COL_BACK_TICKS + CHAR_COMMA
						+ COL_NAME_FIRST_NAME_INC_COL_BACK_TICKS + CHAR_COMMA
						+ COL_NAME_LAST_NAME + CHAR_CLOSE_PARENTHESIS
						+ VALUES_OPERATOR + CHAR_OPEN_PARENTHESIS
						+ CHAR_VALUE_SINGLE_QUOTE + employeeToInsertIntoDbTable.getUserName() + CHAR_VALUE_SINGLE_QUOTE + CHAR_COMMA
						+ CHAR_VALUE_SINGLE_QUOTE + employeeToInsertIntoDbTable.getEncryptedPw() + CHAR_VALUE_SINGLE_QUOTE + CHAR_COMMA
						+ CHAR_VALUE_SINGLE_QUOTE + employeeToInsertIntoDbTable.getFirstName() + CHAR_VALUE_SINGLE_QUOTE + CHAR_COMMA
						+ CHAR_VALUE_SINGLE_QUOTE + employeeToInsertIntoDbTable.getLastName() + CHAR_VALUE_SINGLE_QUOTE + CHAR_CLOSE_PARENTHESIS_SEMICOLON;
				
				
				//DEBUG
				System.out.println(">>>>>>>> " + strSqlStmtInsert);
				
				//4. SQL - String an Satement objekt zum ausfuerhren geben
				dbStatementToExecute.execute(strSqlStmtInsert);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
			if (dbStatementToExecute != null) {
				//5. Schliessen der des Statements
				try {
					dbStatementToExecute.close();
				} catch (SQLException sqlEx) {
					sqlEx.printStackTrace();
				}
			}
			
			if (dbRwConnection != null) {
				//6. Schliessen der Verbindung
				try {
					dbRwConnection.close();
				} catch (SQLException sqlEx) {
					sqlEx.printStackTrace();
				}
			}
		}
	}
	//endregion
	
	//region Update
	
	/**
	 * Aendert einen einzelnen Datensatz in der Datebanktabelle
	 *
	 * @param dbRwConnection        : {@link Connection} : Db-Connection mit Schreib und Lesezugriff
	 * @param employeeToUpdateInDbTable : {@link Employee} : Model welches geaendert werden soll
	 */
	@Override
	public void updateDataRecordIntoDbTbl(Connection dbRwConnection, Employee employeeToUpdateInDbTable) {
		
		Statement dbStatementToExecute = null;
		try {
			//1. Db Connection ist bereits vom DbManager geoeffnet wordem
			
			//2. Statementobjek generieren lassen
			dbStatementToExecute = dbRwConnection.createStatement();
			
			/*
			 * UPDATE `personnel`
			 *	SET `user_name` = 'username123', `encrypted_pw` = 'a36c101570cc4410993de5385ad7034adb2dae6a05139ac7672577803084634d',
			 *      `first_name` = 'Vorname', `last_name` = 'Nachname'
			 * WHERE `_id` = 1;
			 */
			String strSqlStmtUpdate = UPDATE_TBL + this.tableName
					+ SET_OPERATOR
					+ COL_NAME_USER_NAME_INC_COL_BACK_TICKS
					+ EQUALS_OPERATOR  + CHAR_VALUE_SINGLE_QUOTE + employeeToUpdateInDbTable.getUserName() + CHAR_VALUE_SINGLE_QUOTE
					+ CHAR_COMMA
					+ COL_NAME_ENCRYPTED_PW_INC_COL_BACK_TICKS
					+ EQUALS_OPERATOR + CHAR_VALUE_SINGLE_QUOTE + employeeToUpdateInDbTable.getEncryptedPw() + CHAR_VALUE_SINGLE_QUOTE
					+ CHAR_COMMA
					+ COL_NAME_FIRST_NAME_INC_COL_BACK_TICKS
					+ EQUALS_OPERATOR + CHAR_VALUE_SINGLE_QUOTE + employeeToUpdateInDbTable.getFirstName() + CHAR_VALUE_SINGLE_QUOTE
					+ CHAR_COMMA
					+ COL_NAME_LAST_NAME_INC_COL_BACK_TICKS
					+ EQUALS_OPERATOR + CHAR_VALUE_SINGLE_QUOTE + employeeToUpdateInDbTable.getLastName() + CHAR_VALUE_SINGLE_QUOTE
					+ WHERE_CONDITION + COL_NAME_ID_INC_COL_BACK_TICKS + EQUALS_OPERATOR + employeeToUpdateInDbTable.getId() + CHAR_SEMICOLON;
			
			//DEBUG
			System.out.println(">>>>>>>> " + strSqlStmtUpdate);
			
			//4. SQL - String an Statement objekt zum ausfuerhren geben
			dbStatementToExecute.executeUpdate(strSqlStmtUpdate);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
			if (dbStatementToExecute != null) {
				//5. Schliessen der des Statements
				try {
					dbStatementToExecute.close();
				} catch (SQLException sqlEx) {
					sqlEx.printStackTrace();
				}
			}
			
			if (dbRwConnection != null) {
				//6. Schliessen der Verbindung
				try {
					dbRwConnection.close();
				} catch (SQLException sqlEx) {
					sqlEx.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * Aendert eine Liste von Datensaetzen in der Datebanktabelle
	 *
	 * @param dbRwConnection         : {@link Connection} : Db-Connection mit Schreib und Lesezugriff
	 * @param employeesToUpdateInDbTable : {@link Employee} : Models welches geaendert werden soll
	 */
	@Override
	public void updateDataRecordsIntoDbTbl(Connection dbRwConnection, List<Employee> employeesToUpdateInDbTable) {
		Statement dbStatementToExecute = null;
		try {
			//1. Db Connection ist bereits vom DbManager geoeffnet wordem
			
			//2. Statementobjekt generieren lassen
			dbStatementToExecute = dbRwConnection.createStatement();
			
			for (Employee employeeToUpdateInDbTable : employeesToUpdateInDbTable) {
				//2. Statementobjek generieren lassen
				dbStatementToExecute = dbRwConnection.createStatement();
				
				/*
				 * UPDATE `personnel`
				 *	SET `user_name` = 'username123', `encrypted_pw` = 'a36c101570cc4410993de5385ad7034adb2dae6a05139ac7672577803084634d',
				 *      `first_name` = 'Vorname', `last_name` = 'Nachname'
				 * WHERE `_id` = 1;
				 */
				String strSqlStmtUpdate = UPDATE_TBL + this.tableName
						+ SET_OPERATOR
						+ COL_NAME_USER_NAME_INC_COL_BACK_TICKS
						+ EQUALS_OPERATOR  + CHAR_VALUE_SINGLE_QUOTE + employeeToUpdateInDbTable.getUserName() + CHAR_VALUE_SINGLE_QUOTE
						+ CHAR_COMMA
						+ COL_NAME_ENCRYPTED_PW_INC_COL_BACK_TICKS
						+ EQUALS_OPERATOR + CHAR_VALUE_SINGLE_QUOTE + employeeToUpdateInDbTable.getEncryptedPw() + CHAR_VALUE_SINGLE_QUOTE
						+ CHAR_COMMA
						+ COL_NAME_FIRST_NAME_INC_COL_BACK_TICKS
						+ EQUALS_OPERATOR + CHAR_VALUE_SINGLE_QUOTE + employeeToUpdateInDbTable.getFirstName() + CHAR_VALUE_SINGLE_QUOTE
						+ CHAR_COMMA
						+ COL_NAME_LAST_NAME_INC_COL_BACK_TICKS
						+ EQUALS_OPERATOR + CHAR_VALUE_SINGLE_QUOTE + employeeToUpdateInDbTable.getLastName() + CHAR_VALUE_SINGLE_QUOTE
						+ WHERE_CONDITION + COL_NAME_ID_INC_COL_BACK_TICKS + EQUALS_OPERATOR + employeeToUpdateInDbTable.getId() + CHAR_SEMICOLON;
				
				//4. SQL - String an Statement objekt zum ausfuehren geben
				System.out.println(strSqlStmtUpdate);
				dbStatementToExecute.executeUpdate(strSqlStmtUpdate);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
			if (dbStatementToExecute != null) {
				//5. Schliessen der des Statements
				try {
					dbStatementToExecute.close();
				} catch (SQLException sqlEx) {
					sqlEx.printStackTrace();
				}
			}
			
			if (dbRwConnection != null) {
				//6. Schliessen der Verbindung
				try {
					dbRwConnection.close();
				} catch (SQLException sqlEx) {
					sqlEx.printStackTrace();
				}
			}
		}
	}
	//endregion
	
	
	//region Read
	
	/**
	 * Gibt alle Datensaetze eine Datenbanktabelle als {@link List} zurueck
	 *
	 * @param dbRwConnection : {@link Connection} : Db-Connection mit Schreib und Lesezugriff
	 *
	 * @return allDataRecordsFromDbTbl : {@link List} Objects extended from {@link Employee} : Liste aller Datensaetze
	 */
	@Override
	public List<Employee> getAllDataRecordsFromDbTbl(Connection dbRwConnection) {
		
		//Decl. and Init
		List<Employee> allEmployeesFromDbTable = new ArrayList<>();
		
		Statement dbStatementToExecute = null;
		
		try {
			//1. Rw Db Connection ist bereits vom DbManger geoeffnet und Integriert
			
			//2. Generieren des Statements
			dbStatementToExecute = dbRwConnection.createStatement();
			
			//3. Query generieren und absetzen und Ergebnismenge merken
			String strSqlStmtGetAll = SELECT_ALL_DATA_FROM + TABLE_NAME_PERSONNEL;
			
			
			ResultSet resultSetFromExecutedQuery = dbStatementToExecute.executeQuery(strSqlStmtGetAll);
			
			//4. ResultSet == Ergebnismenge durchlaufen bis kein Datensaezte mehr da sind
			while (resultSetFromExecutedQuery.next()) {
				
				//5. Aus der Ergebenismenge einen User beschafften
				Employee employeeFromDbTable = this.getModelFromResultSet(resultSetFromExecutedQuery);
				
				//6. Modelobjekt zur passenden Liste adden
				allEmployeesFromDbTable.add(employeeFromDbTable);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
			if (dbStatementToExecute != null) {
				//5. Schliessen der des Statements
				try {
					dbStatementToExecute.close();
				} catch (SQLException sqlEx) {
					sqlEx.printStackTrace();
				}
			}
			
			if (dbRwConnection != null) {
				//6. Schliessen der Verbindung
				try {
					dbRwConnection.close();
				} catch (SQLException sqlEx) {
					sqlEx.printStackTrace();
				}
			}
		}
		
		return allEmployeesFromDbTable;
	}
	
	/**
	 * Gibt einen bestimmten Datensatz einer Datenbanktabelle zurueck
	 *
	 * @param dbRwConnection : {@link Connection} : Db-Connection mit Schreib und Lesezugriff
	 * @param iId
	 *
	 * @return specificDataRecordFoundById : {@link Employee}  oder abgeleitet davon: Gesuchtes Objekt oder null,
	 * sollte es dieses nicht geben
	 */
	@Override
	public Employee getSpecificDataRecordFromDbTblById(Connection dbRwConnection, int iId) {
		Employee specificEmployee = null;
		
		//Decl. and Init
		
		Statement dbStatementToExecute = null;
		
		try {
			//1. Rw Db Connection ist bereits vom DbManger geoeffenent und Integriert
			
			//2. Geneieren des Statenements
			dbStatementToExecute = dbRwConnection.createStatement();
			
			/*
			 * 3. Query generieren und absetzen und Ergebnismenge merken
			 * SELECT * FROM personnel WHERE _id = 1
			 *
			 */
			String strSqlStmtGetById =
					SELECT_ALL_DATA_FROM + TABLE_NAME_PERSONNEL + WHERE_CONDITION + COL_NAME_ID + EQUALS_OPERATOR + iId;
			
			ResultSet resultSetFromExecutedQuery = dbStatementToExecute.executeQuery(strSqlStmtGetById);
			
			//4. ResultSet == Ergebnismenge durchlaufen bis kein Datensaezte mehr da sind
			if (resultSetFromExecutedQuery.first()) {
				
				//5. Aus der Ergebenismenge einen User beschafften
				specificEmployee = this.getModelFromResultSet(resultSetFromExecutedQuery);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
			if (dbStatementToExecute != null) {
				//5. Schliessen der des Statements
				try {
					dbStatementToExecute.close();
				} catch (SQLException sqlEx) {
					sqlEx.printStackTrace();
				}
			}
			
			if (dbRwConnection != null) {
				//6. Schliessen der Verbindung
				try {
					dbRwConnection.close();
				} catch (SQLException sqlEx) {
					sqlEx.printStackTrace();
				}
			}
		}
		
		return specificEmployee;
	}
	//endregion
	
	
	//region Delete
	
	/**
	 * Loescht einen bestimmten Datensatz aus der Datenbanktabelle
	 *
	 * @param dbRwConnection : {@link Connection} : Db-Connection mit Schreib und Lesezugriff
	 * @param iId            : int : Id des Objektes, welches in der DbTabelle geloscht werden soll
	 */
	@Override
	public void deleteDataRecordInDbTblById(Connection dbRwConnection, int iId) {
		Statement dbStatementToExecute = null;
		
		try {
			//1 Db Connection bereits vom DbManager geoeffent
			
			//2. Geneieren des Statenements
			dbStatementToExecute = dbRwConnection.createStatement();
			
			/*
			 * 3. Statement generieren
			 * String strSqlDeleteUserById = "DELETE FROM `personnel` WHERE `_id` =" + iId;
			 */
			
			String strSqlDeleteUserById = ASqlKeyWords.DELETE_FROM_TBL + TABLE_NAME_PERSONNEL + WHERE_CONDITION
					+ COL_NAME_ID_INC_COL_BACK_TICKS
					+ ASqlKeyWords.EQUALS_OPERATOR + iId;
			
			//DEBUG
			System.out.println(">>>>>>>> " + strSqlDeleteUserById);
			
			dbStatementToExecute.executeUpdate(strSqlDeleteUserById);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
			if (dbStatementToExecute != null) {
				//5. Schliessen der des Statements
				try {
					dbStatementToExecute.close();
				} catch (SQLException sqlEx) {
					sqlEx.printStackTrace();
				}
			}
			
			if (dbRwConnection != null) {
				//6. Schliessen der Verbindung
				try {
					dbRwConnection.close();
				} catch (SQLException sqlEx) {
					sqlEx.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * Loescht alle Datensätze aus der Datenbanktabelle
	 *
	 * @param dbRwConnection : {@link Connection} : Db-Connection mit Schreib und Lesezugriff
	 */
	@Override
	public void deleteAllDataRecordsInDbTbl(Connection dbRwConnection) {
		
		Statement dbStatementToExecute = null;
		
		try {
			//1 Db Connection bereits vom DbManager geoeffnet
			
			//2. Geneieren des Statenements
			dbStatementToExecute = dbRwConnection.createStatement();
			
			/*
			 * 3. Statement generieren
			 * DELETE FROM table_name;
			 * String strSqlDeleteUserById = "DELETE FROM `personnel`;
			 */
			
			String strSqlDeleteUser = ASqlKeyWords.DELETE_FROM_TBL + TABLE_NAME_PERSONNEL;
			
			//DEBUG
			System.out.println(">>>>>>>> " + strSqlDeleteUser);
			
			dbStatementToExecute.executeUpdate(strSqlDeleteUser);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
			if (dbStatementToExecute != null) {
				//5. Schliessen der des Statements
				try {
					dbStatementToExecute.close();
				} catch (SQLException sqlEx) {
					sqlEx.printStackTrace();
				}
			}
			
			if (dbRwConnection != null) {
				//6. Schliessen der Verbindung
				try {
					dbRwConnection.close();
				} catch (SQLException sqlEx) {
					sqlEx.printStackTrace();
				}
			}
		}
	}
	//endregion
	
	//region Model aus ResultSet Formen
	
	/**
	 * Nimmt die Ergebnismenge und formt ein konkretes Model daraus
	 *
	 * @param currentResultSet : {@link ResultSet} : Ergebnismenge der aktuellen Abfrage
	 *
	 * @return trip : {@link Employee} : Model abgeleitet von der Basisklasse
	 *
	 * @throws Exception
	 */
	@Override
	protected Employee getModelFromResultSet(ResultSet currentResultSet) throws Exception {
		//Index auslesen
		final int columnIndexId          = currentResultSet.findColumn(COL_NAME_ID);
		final int columnIndexUserName = currentResultSet.findColumn(COL_NAME_USER_NAME);
		final int columnIndexPw    = currentResultSet.findColumn(COL_NAME_ENCRYPTED_PW);
		final int columnIndexFirstName    = currentResultSet.findColumn(COL_NAME_FIRST_NAME);
		final int columnIndexLastName    = currentResultSet.findColumn(COL_NAME_LAST_NAME);
		
		//6. Durch Auswahl des Datentyps und angabe des Spaltenindizes Auslesen der Daten
		int id = currentResultSet.getInt(columnIndexId);
		String userName        = currentResultSet.getString(columnIndexUserName);
		String pw = currentResultSet.getString(columnIndexPw);
		String  firstName      = currentResultSet.getString(columnIndexFirstName);
		String  lastName = currentResultSet.getString(columnIndexLastName);
		
		//7. Neues Modelobjekt generieren
		Employee employeeFromDb = new Employee();
		
		employeeFromDb.setId(id);
		employeeFromDb.setUserName(userName);
		employeeFromDb.setEncryptedPw(pw);
		employeeFromDb.setFirstName(firstName);
		employeeFromDb.setLastName(lastName);
		
		return employeeFromDb;
	}
	//endregion
}
