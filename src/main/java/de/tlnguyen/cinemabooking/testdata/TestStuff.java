package de.tlnguyen.cinemabooking.testdata;

import de.tlnguyen.cinemabooking.helper.Encrypter;
import de.tlnguyen.cinemabooking.logic.db.DbManager;
import de.tlnguyen.cinemabooking.model.Seat;
import javafx.application.Application;
import javafx.stage.Stage;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
public class TestStuff {
	
	public static void main(String[] args) throws Exception {
		
		/*String pattern = "YYYY-MM-dd HH:mm:ss";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		String date = simpleDateFormat.format(new Date());
		
		Date dt = new Date();
		System.out.println("Today:    "+dt);
		Calendar c = Calendar.getInstance();
		c.setTime(dt);
		c.add(Calendar.DATE, 1);
		dt = c.getTime();
		System.out.println("Tomorrow: "+dt);*/
		
//		System.out.println(new Date());
		/*System.out.println(Encrypter.getInstance().encryptToSha256HashHexString("Passwort"));
		System.out.println(Encrypter.getInstance().encryptToSha256HashHexString("helloJava!"));*/
		
	/*	Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY,17);
		cal.set(Calendar.MINUTE,30);
		cal.set(Calendar.SECOND,0);
		cal.set(Calendar.MILLISECOND,0);
		
		Date d = cal.getTime();
		System.out.println(d);*/
		
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY,20);
		cal.set(Calendar.MINUTE,0);
		cal.set(Calendar.SECOND,0);
		cal.set(Calendar.MILLISECOND,0);
		
		Date nextShowtime = cal.getTime();
		System.out.println(nextShowtime);
		
		SimpleDateFormat parser = new SimpleDateFormat("HH:mm");
		
		try {
			Date compareShowtimeHour = parser.parse("20:00");
			Date compareNowHour = parser.parse(LocalDateTime.now().getHour()+ ":00");
			
			//System.out.println(LocalDateTime.now().getHour());
			System.out.println(compareShowtimeHour);
			System.out.println(compareNowHour);
			
			if (compareNowHour.after(compareShowtimeHour)) {
				
				nextShowtime = getNewDay(nextShowtime);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		System.out.println(nextShowtime);
	}
	
	private static Date getNewDay(Date dateBefore) {
		
		Calendar c = Calendar.getInstance();
		c.setTime(dateBefore);
		c.add(Calendar.DATE, 1);
		
		return c.getTime();
	}
}
