package de.tlnguyen.cinemabooking.logic.db;

import de.tlnguyen.cinemabooking.model.Seat;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;

/**
 * Diese Klasse definiert, was alle
 * Datenbanktabellen und Ihre Dao-Klassen
 * gemeinsam haben.
 * <ul>
 *     <li>Spaltennamen</li>
 *     <li>CRUD-Operationen</li>
 *     <li>Hilfsmethoden und Funktionen</li>
 * </ul>
 */
public abstract class ADao<T> extends ASqlKeyWords {
	
	//region 0. Konstanten
	/**
	 * Primaerschluessel aller Tabellen dieses Projekts
	 * ansonsten koennnte man auch hier PRIMARY KEY als Namen
	 * verwenden.
	 */
	protected static final String COL_NAME_ID                    = "id";
	protected static final String COL_NAME_ID_INC_COL_BACK_TICKS =
			CHAR_COL_BACK_TICK + COL_NAME_ID + CHAR_COL_BACK_TICK;
	
	//endregion
	
	//region 1. Decl. and Init Attribute
	protected String tableName;
	//endregion
	
	//region 2. Konstruktor
	
	protected ADao(String tableName) {
		this.tableName = tableName;
	}
	
	//endregion
	
	//region 3. Insert
	
	/**
	 * Fuegt einen einzelnen Datensatz in die Datebanktabelle ein
	 *
	 * @param dbRwConnection          : {@link Connection} : Db-Connection mit Schreib und Lesezugriff
	 * @param seatToInsertIntoDbTable : {@link T} : Model welches eingefuegt werden soll
	 */
	public abstract void insertDataRecordIntoDbTbl(Connection dbRwConnection, T seatToInsertIntoDbTable);
	
	/**
	 * Fuegt eine Liste von Datensaetzen in die Datebanktabelle ein
	 *
	 * @param dbRwConnection           : {@link Connection} : Db-Connection mit Schreib und Lesezugriff
	 * @param tripsToInsertIntoDbTable : {@link T} : Models welches eingefuegt werden soll
	 */
	public abstract void insertDataRecordsIntoDbTbl(Connection dbRwConnection, List<T> tripsToInsertIntoDbTable);
	//endregion
	
	//region 4. Update
	
	/**
	 * Aendert einen einzelen Datensatz in der Datebanktabelle
	 *
	 * @param dbRwConnection        : {@link Connection} : Db-Connection mit Schreib und Lesezugriff
	 * @param seatToUpdateInDbTable : {@link T} : Model welches geaendert werden soll
	 */
	public abstract void updateDataRecordIntoDbTbl(Connection dbRwConnection, T seatToUpdateInDbTable);
	
	/**
	 * Aendert eine Liste von Datensaetzen in der Datebanktabelle
	 *
	 * @param dbRwConnection         : {@link Connection} : Db-Connection mit Schreib und Lesezugriff
	 * @param seatsToUpdateInDbTable : {@link T} : Models welches geaendert werden soll
	 */
	public abstract void updateDataRecordsIntoDbTbl(Connection dbRwConnection, List<T> seatsToUpdateInDbTable);
	//endregion
	
	
	//region 5. Read
	
	/**
	 * Gibt alle Datensaetze eine Datenbanktabelle als {@link List} zurueck
	 *
	 * @param dbRwConnection : {@link Connection} : Db-Connection mit Schreib und Lesezugriff
	 *
	 * @return allDataRecordsFromDbTbl : {@link List} Objects extended from {@link T} : Liste aller Datensaetze
	 */
	public abstract List<T> getAllDataRecordsFromDbTbl(Connection dbRwConnection);
	
	/**
	 * Gibt einen bestimmten Datensatz einer Datenbanktabelle zurueck
	 *
	 * @param dbRwConnection : {@link Connection} : Db-Connection mit Schreib und Lesezugriff
	 * @param iId
	 *
	 * @return specificDataRecordFoundById : {@link T}  oder abgeleitet davon: Gesuchtes Objekt oder null,
	 * sollte es dieses nicht geben
	 */
	public abstract T getSpecificDataRecordFromDbTblById(Connection dbRwConnection, int iId);
	//endregion
	
	
	//region 6. Delete
	
	/**
	 * Loescht einen bestimmten Datensatz aus der Datenbanktabelle
	 *
	 * @param dbRwConnection : {@link Connection} : Db-Connection mit Schreib und Lesezugriff
	 * @param iId            : int : Id des Objektes welches in der DbTabelle geloscht werden soll
	 */
	public abstract void deleteDataRecordInDbTblById(Connection dbRwConnection, int iId);
	
	/**
	 * Loescht alle Datensätze aus der Datenbanktabelle
	 *
	 * @param dbRwConnection : {@link Connection} : Db-Connection mit Schreib und Lesezugriff
	 */
	public abstract void deleteAllDataRecordsInDbTbl(Connection dbRwConnection);
	//endregion
	
	//region 7. Model aus ResultSet Formen
	
	/**
	 * Nimmt die Ergebnismenge und formt ein konkretes Model daraus
	 *
	 * @param currentResultSet : {@link ResultSet} : Ergebnismenge der aktuellen Abfrage
	 *
	 * @return trip : {@link T} : Model abgeleitet von der Basisklasse
	 *
	 * @throws Exception
	 */
	protected abstract T getModelFromResultSet(ResultSet currentResultSet) throws Exception;
	//endregion
	
}
