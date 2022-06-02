package de.tlnguyen.cinemabooking.logic.db;

import de.tlnguyen.cinemabooking.model.Employee;
import de.tlnguyen.cinemabooking.model.Seat;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * TODO 4. Diese Klasse kopieren und einfeugen
 * Diese Klasse ist eine DAO-Klasse.
 * DAO seht fuer Data Access Object.
 * Fuert die CRUD  Operationen auf der DbTabelle
 */
public class DaoSeats extends ADao<Seat> {
	
	//region 0. Konstanten
	protected static final String COL_NAME_IS_BOOKED                    = "is_booked";
	protected static final String COL_NAME_IS_BOOKED_INC_COL_BACK_TICKS =
			CHAR_COL_BACK_TICK + COL_NAME_IS_BOOKED + CHAR_COL_BACK_TICK;
	
	protected static final String COL_NAME_ROW                    = "row";
	protected static final String COL_NAME_ROW_INC_COL_BACK_TICKS =
			CHAR_COL_BACK_TICK + COL_NAME_ROW + CHAR_COL_BACK_TICK;
	
	protected static final String COL_NAME_NUMBER                    = "number";
	protected static final String COL_NAME_NUMBER_INC_COL_BACK_TICKS =
			CHAR_COL_BACK_TICK + COL_NAME_NUMBER + CHAR_COL_BACK_TICK;
	
	protected static final String COL_NAME_DATE_TIME                    = "date_time";
	protected static final String COL_NAME_DATE_TIME_INC_COL_BACK_TICKS =
			CHAR_COL_BACK_TICK + COL_NAME_DATE_TIME + CHAR_COL_BACK_TICK;
	
	protected static final String COL_NAME_PERSONNEL_ID                    = "personnel_id";
	protected static final String COL_NAME_PERSONNEL_ID_INC_COL_BACK_TICKS =
			CHAR_COL_BACK_TICK + COL_NAME_PERSONNEL_ID + CHAR_COL_BACK_TICK;
	
	private static final String TABLE_NAME_SEATS = "seats";
	public static final  String DATE_PATTERN     = "YYYY-MM-dd HH:mm:ss";
	//endregion
	
	//region Attribut
	private SimpleDateFormat simpleDateFormat;
	//endregion
	
	//region 2. Konstruktor
	public DaoSeats() {
		
		super(TABLE_NAME_SEATS);
		simpleDateFormat = new SimpleDateFormat(DATE_PATTERN);
	}
	//endregion
	
	//region Insert
	
	/**
	 * Fuegt einen einzelnen Datensatz in die Datebanktabelle ein
	 *
	 * @param dbRwConnection          : {@link Connection} : Db-Connection mit Schreib und Lesezugriff
	 * @param seatToInsertIntoDbTable : {@link de.tlnguyen.cinemabooking.model.Seat : Model welches eingefuegt werden soll
	 */
	@Override
	public void insertDataRecordIntoDbTbl(Connection dbRwConnection, Seat seatToInsertIntoDbTable) {
		
		//Decl and Init
		Statement dbStatementToExecute = null;
		
		try {
			//1. Db Connection ist bereits von DbManger generiert
			
			//2. Statementobjekt zum tatsaechlichen ausfuehren des unten als String generierten SQL-Statements
			dbStatementToExecute = dbRwConnection.createStatement();
			
			//3. SQL-String angepasst auf die Tabelle generieren

			/*
			 * INSERT INTO seats (
			 *      `is_booked`,
			 *      `row`,
			 *      `number`
			 *      `date_time`
			 *      `personnel_id`
			 * )
			 *  VALUES (
			 *      'false',
			 *      1,
			 *      1,
			 *      '2022:04:14 16:00:00'
			 *      1
			 * );
			 */
			String strSqlStmtInsert = INSERT_TBL + this.tableName + CHAR_OPEN_PARENTHESIS
					+ COL_NAME_IS_BOOKED_INC_COL_BACK_TICKS + CHAR_COMMA
					+ COL_NAME_ROW_INC_COL_BACK_TICKS + CHAR_COMMA
					+ COL_NAME_NUMBER_INC_COL_BACK_TICKS + CHAR_COMMA
					+ COL_NAME_DATE_TIME + CHAR_COMMA
					+ COL_NAME_PERSONNEL_ID + CHAR_CLOSE_PARENTHESIS
					+ VALUES_OPERATOR + CHAR_OPEN_PARENTHESIS
					+ seatToInsertIntoDbTable.isBooked() + CHAR_COMMA
					+ seatToInsertIntoDbTable.getRow() + CHAR_COMMA
					+ seatToInsertIntoDbTable.getNumber() + CHAR_COMMA
					+ CHAR_VALUE_SINGLE_QUOTE + this.simpleDateFormat.format(seatToInsertIntoDbTable.getDateTime())
					+ CHAR_VALUE_SINGLE_QUOTE + CHAR_COMMA
					+ seatToInsertIntoDbTable.getEmployee().getId() + CHAR_CLOSE_PARENTHESIS_SEMICOLON;
			
			
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
	 * @param seatsToInsertIntoDbTable : {@link Seat} : Models welches eingefuegt werden soll
	 */
	@Override
	public void insertDataRecordsIntoDbTbl(Connection dbRwConnection, List<Seat> seatsToInsertIntoDbTable) {
		//Decl and Init
		Statement dbStatementToExecute = null;
		
		try {
			//1. Db Connection ist bereits von DbManger generiert
			
			//2. Statementobjekt zum tatsaechlichen ausfuehren des unten als String generierten SQL-Statements
			dbStatementToExecute = dbRwConnection.createStatement();
			
			//Alle Datensaetze durchlaufen
			for (Seat seatToInsertIntoDbTable : seatsToInsertIntoDbTable) {
				
				/*
				 * INSERT INTO seats (
				 *      `is_booked`,
				 *      `row`,
				 *      `number`
				 *      `date_time`
				 *      `personnel_id`
				 * )
				 *  VALUES (
				 *      'false',
				 *      1,
				 *      1,
				 *      '2022:04:14 16:00:00'
				 *      1
				 * );
				 */
				String strSqlStmtInsert = INSERT_TBL + this.tableName + CHAR_OPEN_PARENTHESIS
						+ COL_NAME_IS_BOOKED_INC_COL_BACK_TICKS + CHAR_COMMA
						+ COL_NAME_ROW_INC_COL_BACK_TICKS + CHAR_COMMA
						+ COL_NAME_NUMBER_INC_COL_BACK_TICKS + CHAR_COMMA
						+ COL_NAME_DATE_TIME_INC_COL_BACK_TICKS + CHAR_COMMA
						+ COL_NAME_PERSONNEL_ID_INC_COL_BACK_TICKS + CHAR_CLOSE_PARENTHESIS
						+ VALUES_OPERATOR + CHAR_OPEN_PARENTHESIS
						+ seatToInsertIntoDbTable.isBooked() + CHAR_COMMA
						+ seatToInsertIntoDbTable.getRow() + CHAR_COMMA
						+ seatToInsertIntoDbTable.getNumber() + CHAR_COMMA
						+ CHAR_VALUE_SINGLE_QUOTE + this.simpleDateFormat.format(seatToInsertIntoDbTable.getDateTime())
						+ CHAR_VALUE_SINGLE_QUOTE + CHAR_COMMA
						+ seatToInsertIntoDbTable.getEmployee().getId() + CHAR_CLOSE_PARENTHESIS_SEMICOLON;
				
				
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
	 * Aendert einen einzelen Datensatz in der Datebanktabelle
	 *
	 * @param dbRwConnection        : {@link Connection} : Db-Connection mit Schreib und Lesezugriff
	 * @param seatToUpdateInDbTable : {@link Seat} : Model welches geaendert werden soll
	 */
	@Override
	public void updateDataRecordIntoDbTbl(Connection dbRwConnection, Seat seatToUpdateInDbTable) {
		
		Statement dbStatementToExecute = null;
		try {
			//1. Db Connection ist bereits vom DbManager geoeffnet wordem
			
			//2. Statementobjek generieren lassen
			dbStatementToExecute = dbRwConnection.createStatement();
			
			/*
			 * UPDATE `trips`
			 *	SET `is_booked` = 'false', `row` = 1, `number` = 1, `date_time` = '2022:04:14 16:00:00', `personnel_id` = 1;
			 * WHERE `_id` = 1;
			 */
			String strSqlStmtUpdate = UPDATE_TBL + this.tableName
					+ SET_OPERATOR
					+ COL_NAME_IS_BOOKED_INC_COL_BACK_TICKS
					+ EQUALS_OPERATOR + seatToUpdateInDbTable.isBooked()
					+ CHAR_COMMA
					+ COL_NAME_ROW_INC_COL_BACK_TICKS
					+ EQUALS_OPERATOR + seatToUpdateInDbTable.getRow()
					+ CHAR_COMMA
					+ COL_NAME_NUMBER_INC_COL_BACK_TICKS
					+ EQUALS_OPERATOR + seatToUpdateInDbTable.getNumber()
					+ CHAR_COMMA
					+ COL_NAME_DATE_TIME_INC_COL_BACK_TICKS
					+ EQUALS_OPERATOR + CHAR_VALUE_SINGLE_QUOTE + this.simpleDateFormat.format(seatToUpdateInDbTable.getDateTime()) + CHAR_VALUE_SINGLE_QUOTE
					+ CHAR_COMMA
					+ COL_NAME_PERSONNEL_ID_INC_COL_BACK_TICKS
					+ EQUALS_OPERATOR + seatToUpdateInDbTable.getEmployee().getId()
					+ WHERE_CONDITION + COL_NAME_ID_INC_COL_BACK_TICKS + EQUALS_OPERATOR + seatToUpdateInDbTable.getSeatId() + CHAR_SEMICOLON;
			
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
	 * @param seatsToUpdateInDbTable : {@link Seat} : Models welches geaendert werden soll
	 */
	@Override
	public void updateDataRecordsIntoDbTbl(Connection dbRwConnection, List<Seat> seatsToUpdateInDbTable) {
		Statement dbStatementToExecute = null;
		try {
			//1. Db Connection ist bereits vom DbManager geoeffnet wordem
			
			//2. Statementobjekt generieren lassen
			dbStatementToExecute = dbRwConnection.createStatement();
			
			for (Seat seatToUpdateInDbTable : seatsToUpdateInDbTable) {
				//2. Statementobjek generieren lassen
				dbStatementToExecute = dbRwConnection.createStatement();
				
				/*
				 * UPDATE `trips`
				 *	SET `is_booked` = 'false', `row` = 1, `number` = 1, `date_time` = '2022:04:14 16:00:00', `personnel_id` = 1;
				 * WHERE `_id` = 1;
				 */
				String strSqlStmtUpdate = UPDATE_TBL + this.tableName
						+ SET_OPERATOR
						+ COL_NAME_IS_BOOKED_INC_COL_BACK_TICKS
						+ EQUALS_OPERATOR + seatToUpdateInDbTable.isBooked()
						+ CHAR_COMMA
						+ COL_NAME_ROW_INC_COL_BACK_TICKS
						+ EQUALS_OPERATOR + seatToUpdateInDbTable.getRow()
						+ CHAR_COMMA
						+ COL_NAME_NUMBER_INC_COL_BACK_TICKS
						+ EQUALS_OPERATOR + seatToUpdateInDbTable.getNumber()
						+ CHAR_COMMA
						+ COL_NAME_DATE_TIME_INC_COL_BACK_TICKS
						+ EQUALS_OPERATOR + CHAR_VALUE_SINGLE_QUOTE + this.simpleDateFormat.format(seatToUpdateInDbTable.getDateTime()) + CHAR_VALUE_SINGLE_QUOTE
						+ CHAR_COMMA
						+ COL_NAME_PERSONNEL_ID_INC_COL_BACK_TICKS
						+ EQUALS_OPERATOR + seatToUpdateInDbTable.getEmployee().getId()
						+ WHERE_CONDITION + COL_NAME_ID_INC_COL_BACK_TICKS + EQUALS_OPERATOR + seatToUpdateInDbTable.getSeatId() + CHAR_SEMICOLON;
				
				//4. SQL - String an Satement objekt zum ausfuehren geben
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
	 * @return allDataRecordsFromDbTbl : {@link List} Objects extended from {@link Seat} : Liste aller Datensaetze
	 */
	@Override
	public List<Seat> getAllDataRecordsFromDbTbl(Connection dbRwConnection) {
		
		//Decl. and Init
		List<Seat> allSeatsFromDbTable = new ArrayList<>();
		
		Statement dbStatementToExecute = null;
		
		try {
			//1. Rw Db Connection ist bereits vom DbManger geoeffnet und Integriert
			
			//2. Generieren des Statements
			dbStatementToExecute = dbRwConnection.createStatement();
			
			//3. Query generieren und absetzen und Ergebnismenge merken
			String strSqlStmtGetAll = SELECT_ALL_DATA_FROM + TABLE_NAME_SEATS;
			
			
			ResultSet resultSetFromExecutedQuery = dbStatementToExecute.executeQuery(strSqlStmtGetAll);
			
			//4. ResultSet == Ergebnismenge durchlaufen bis kein Datensaezte mehr da sind
			while (resultSetFromExecutedQuery.next()) {
				
				//5. Aus der Ergebenismenge einen User beschafften
				Seat seatFromDbTable = this.getModelFromResultSet(resultSetFromExecutedQuery);
				
				//6. Modelobjekt zur passenden Liste adden
				allSeatsFromDbTable.add(seatFromDbTable);
				
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
		
		return allSeatsFromDbTable;
	}
	
	/**
	 * Gibt einen bestimmten Datensatz einer Datenbanktabelle zurueck
	 *
	 * @param dbRwConnection : {@link Connection} : Db-Connection mit Schreib und Lesezugriff
	 * @param iId
	 *
	 * @return specificDataRecordFoundById : {@link Seat}  oder abgeleitet davon: Gesuchtes Objekt oder null,
	 * sollte es dieses nicht geben
	 */
	@Override
	public Seat getSpecificDataRecordFromDbTblById(Connection dbRwConnection, int iId) {
		
		Seat specificSeat = null;
		
		//Decl. and Init
		
		Statement dbStatementToExecute = null;
		
		try {
			//1. Rw Db Connection ist bereits vom DbManger geoeffnet und integriert
			
			//2. Generieren des Statements
			dbStatementToExecute = dbRwConnection.createStatement();
			
			/*
			 * 3. Query generieren und absetzen und Ergebnismenge merken
			 * SELECT * FROM trips WHERE _id = 1
			 *
			 */
			String strSqlStmtGetById =
					SELECT_ALL_DATA_FROM + TABLE_NAME_SEATS + WHERE_CONDITION + COL_NAME_ID + EQUALS_OPERATOR + iId;
			
			ResultSet resultSetFromExecutedQuery = dbStatementToExecute.executeQuery(strSqlStmtGetById);
			
			//4. ResultSet == Ergebnismenge durchlaufen bis kein Datensaetze mehr da sind
			if (resultSetFromExecutedQuery.first()) {
				
				//5. Aus der Ergebnismenge einen User beschafften
				specificSeat = this.getModelFromResultSet(resultSetFromExecutedQuery);
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
		
		return specificSeat;
	}
	//endregion
	
	
	//region Delete
	
	/**
	 * Loescht einen bestimmten Datensatz aus der Datenbanktabelle
	 *
	 * @param dbRwConnection : {@link Connection} : Db-Connection mit Schreib und Lesezugriff
	 * @param iId            : int : Id des Objektes welches in der DbTabelle geloscht werden soll
	 */
	@Override
	public void deleteDataRecordInDbTblById(Connection dbRwConnection, int iId) {
		Statement dbStatementToExecute = null;
		
		try {
			//1 Db Connection bereits vom DbManager geoeffnet
			
			//2. Generieren des Statements
			dbStatementToExecute = dbRwConnection.createStatement();
			
			/*
			 * 3. Statement generieren
			 * String strSqlDeleteUserById = "DELETE FROM `trips` WHERE `_id` =" + iId;
			 */
			
			String strSqlDeleteUserById = ASqlKeyWords.DELETE_FROM_TBL + TABLE_NAME_SEATS + WHERE_CONDITION
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
			
			//2. Generieren des Statements
			dbStatementToExecute = dbRwConnection.createStatement();
			
			/*
			 * 3. Statement generieren
			 * DELETE FROM table_name;
			 * String strSqlDeleteUserById = "DELETE FROM `seats`;
			 */
			
			String strSqlDeleteUser = ASqlKeyWords.DELETE_FROM_TBL + TABLE_NAME_SEATS;
			
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
	 * @return trip : {@link Seat} : Model abgeleitet von der Basisklasse
	 *
	 * @throws Exception
	 */
	@Override
	protected Seat getModelFromResultSet(ResultSet currentResultSet) throws Exception {
		//Index auslesen
		final int columnIndexId          = currentResultSet.findColumn(COL_NAME_ID);
		final int columnIndexIsBooked    = currentResultSet.findColumn(COL_NAME_IS_BOOKED);
		final int columnIndexRow         = currentResultSet.findColumn(COL_NAME_ROW);
		final int columnIndexNumber      = currentResultSet.findColumn(COL_NAME_NUMBER);
		final int columnIndexDateTime    = currentResultSet.findColumn(COL_NAME_DATE_TIME);
		final int columnIndexPersonnelId = currentResultSet.findColumn(COL_NAME_PERSONNEL_ID);
		
		//6. Durch Auswahl des Datentyps und angabe des Spaltenindizes Auslesen der Daten
		int     id          = currentResultSet.getInt(columnIndexId);
		boolean isBooked    = currentResultSet.getBoolean(columnIndexIsBooked);
		int     row         = currentResultSet.getInt(columnIndexRow);
		int     number      = currentResultSet.getInt(columnIndexNumber);
		Date    dateTime    = (Date) currentResultSet.getObject(columnIndexDateTime);
		int     personnelId = currentResultSet.getInt(columnIndexPersonnelId);
		
		//7. Neues Model-Objekt generieren
		Seat seatFromDb = new Seat();
		
		seatFromDb.setSeatId(id);
		seatFromDb.setBooked(isBooked);
		seatFromDb.setRow(row);
		seatFromDb.setNumber(number);
		seatFromDb.setDateTime(dateTime);
		
		Employee employee = new Employee();
		employee.setId(personnelId);
		seatFromDb.setEmployee(employee);
		
		return seatFromDb;
	}
	//endregion
}