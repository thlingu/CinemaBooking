package de.tlnguyen.cinemabooking.helper;

import java.text.DecimalFormat;
import java.util.Currency;
import java.util.Locale;

/**
 * Formatiert Zahlen und Waehrungsstrings
 * created by Rudolf Histel
 */
public class NumberHelper {
	
	//region 0. Konstanten
	public static final int    FRACTION_DIGITS   = 2;
	public static final String BLANK                   = " ";
	//endregion
	
	//region 1. Decl. and Init Attribute
	//endregion
	
	//region 2. Konstruktor
	
	/**
	 * Privater Standardkonstruktor
	 * um die Objektinstanziierung von
	 * ausserhalb dieser Klasse zu unterbinden
	 */
	private NumberHelper() {
		//Nichts zu tun ausser privat zu sein
	}
	//endregion
	
	//region 3. Double Preis Formatierung
	
	/**
	 * Nimmt einen double - Wert entgegen der einene Preis darstellen soll.
	 * Dieser wird dann passend auf die lokale (Deutsche Waehrung) formatiert mit zwei
	 * Nachkommastellen und dem Eurozeichen
	 *
	 * @param dblCurrencyValue : double : Wert der zum schoenen Ausgabepreis werden soll
	 * @return strBeautifulCurrencyValue : {@link String} Formatierter String zur Ausgabe auf der Gui
	 */
	public static synchronized String getBeautifulDoubleCurrencyValue(double dblCurrencyValue) {
		//1. DecimalFormat Objekt geneireren
		DecimalFormat taxPriceFormatter = new DecimalFormat();
		
		//2. Lokale Waehrung festlegen
		taxPriceFormatter.setCurrency(Currency.getInstance(Locale.GERMANY));
		
		//3. Minmum an Nachkommastellen festlegen so das auch 0en angezeigt werden
		taxPriceFormatter.setMinimumFractionDigits(FRACTION_DIGITS);
		
		//4. Maximum an Nachkommastellen festlegen
		taxPriceFormatter.setMaximumFractionDigits(FRACTION_DIGITS);
		
		//5. Rundungsmethode festlegen
		//df.setRoundingMode(RoundingMode.UP);
		
		// 6. String zusammenbauen: Formatierter Preis
		String strBeautifulCurrencyValue = taxPriceFormatter.format(dblCurrencyValue);
		
		//7. String zusammenbauen: Splittzeichen zwischen Preis und Waehrunngssymbol
		strBeautifulCurrencyValue += BLANK;
		
		//8. String zusammenbauen: Waehrungszeichen anhaengen
		strBeautifulCurrencyValue += taxPriceFormatter.getDecimalFormatSymbols().getCurrencySymbol();
		
		return strBeautifulCurrencyValue;
	}
	
	/**
	 * Checkt ob der uebergebene {@link String} in einen Integer gecastet werden kann
	 *
	 * @param strIntegerValueToCheck : {@link String} : Stringwert der gecheckt werden soll
	 * @return isInteger : boolean : true Ganzahl : false nicht
	 */
	public static synchronized boolean isInteger(String strIntegerValueToCheck) {
		
		boolean isInteger = true;
		
		try {
			
			//Eingebener Betrag CASTEN !!! Bei Falscheingaben wird hier eine NumberFormatExcept geworfen
			int iValue = Integer.parseInt(strIntegerValueToCheck);
			
		} catch (NumberFormatException nfEx) {
			isInteger = false;
		}
		
		
		return isInteger;
	}
	
	/**
	 * Checkt ob der uebergebene {@link String} in einen {@link Double} gecastet werden kann
	 *
	 * @param strDoubleValueToCheck : {@link String} : Stringwert der gecheckt werden soll
	 * @return isDouble : boolean : true Kommazahl : false nicht
	 */
	public static synchronized boolean isDouble(String strDoubleValueToCheck) {
		
		boolean isDouble = true;
		
		try {
			
			//Eingebener Betrag CASTEN !!! Bei Falscheingaben wird hier eine NumberFormatExcept geworfen
			double dblValue = Double.parseDouble(strDoubleValueToCheck);
			
		} catch (NumberFormatException nfEx) {
			isDouble = false;
		}
		
		
		return isDouble;
	}
	
	//endregion
}
