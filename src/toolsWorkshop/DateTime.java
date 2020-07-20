package toolsWorkshop;

import java.io.Serializable;
import java.util.InputMismatchException;

//Immutable objects, checking input while constructing
public class DateTime implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final DateTime MAX_DATE =
			new DateTime(31,12,Integer.MAX_VALUE,6,23,59);
	
	public static final DateTime MIN_DATE =
			new DateTime(1,1,Integer.MIN_VALUE,0,0,0);
	
	private int day;
	private int month;
	private int year;
	private int dayOfWeek; // 0 = Sunday
	
	private int hour; //00 to 23
	private int minute;
	
	public DateTime(int day, int month, int year, int dayOfWeek, int hour, int minute) throws InputMismatchException {
		super();
		if(day<1 || day > 31)
			throw new InputMismatchException("Invalid day");
		this.day = day;
		if(month < 1 || month > 12)
			throw new InputMismatchException("Invalid month");
		this.month = month;
		this.year = year;
		if(dayOfWeek<0 || dayOfWeek > 6)
			throw new InputMismatchException("Invalid day of week");
		this.dayOfWeek = dayOfWeek; 
		if(hour<0 || hour > 23)
			throw new InputMismatchException("Invalid hour");
		this.hour = hour;
		if(minute<0 || minute > 59)
			throw new InputMismatchException("Invalid minute");
		this.minute = minute;
	}
	
	public DateTime(int day, int month, int year, int hour, int minute) throws InputMismatchException {
		super();
		if(day<1 || day > 31)
			throw new InputMismatchException("Invalid day");
		this.day = day;
		if(month < 1 || month > 12)
			throw new InputMismatchException("Invalid month");
		this.month = month;
		this.year = year;
		if(hour<0 || hour > 23)
			throw new InputMismatchException("Invalid hour");
		this.hour = hour;
		if(minute<0 || minute > 59)
			throw new InputMismatchException("Invalid minute");
		this.minute = minute;
	}
	
	public DateTime(int day, int month, int year, int dayOfWeek) {
		super();
		this.day = day;
		this.month = month;
		this.year = year;
		this.dayOfWeek = dayOfWeek;
		this.hour = 12;
		this.minute = 0;
	}
	
	public int getDay() {
		return day;
	}
	public int getMonth() {
		return month;
	}
	public int getYear() {
		return year;
	}
	public int getDayOfWeek() {
		return dayOfWeek;
	}
	public int gethour() {
		return hour;
	}
	public int getminute() {
		return minute;
	}

	
	public boolean before(DateTime other) {
		if(this.year<other.year)
			return true;
		if(this.year==other.year) {
			if(this.month<other.month)
				return true;
			if(this.month == other.month) {
				if(this.day<=other.day)
					return true;
			}
		}
		return false;
	}
	
	public boolean after(DateTime other) {
		if(this.year>other.year)
			return true;
		if(this.year==other.year) {
			if(this.month>other.month)
				return true;
			if(this.month == other.month) {
				if(this.day>=other.day)
					return true;
			}
		}
		return false;
	}
	
	public String toDateString() {
		return day + "/" + month + "/" + year;
	}
	
	public String toHourString() {
		return hour + ":" + minute;
	}
	
	public String toString() {
		return toDateString() + " " + toDayOfWeekString() + " " + toHourString();
	}
	
	public String toDayOfWeekString() {
		switch(dayOfWeek) {
		case 0:
			return "Sunday";
		case 1:
			return "Monday";
		case 2:
			return "Tuesday";
		case 3:
			return "Wednesday";
		case 4:
			return "Thursday";
		case 5:
			return "Friday";
		case 6:
			return "Saturday";
		}
		return "Sunday";
	}
	
	public int compareTo(DateTime other) {
		return ((other.year-this.year)*365) + ((other.month-this.month)*30) + other.day - this.day; 
	}
	
}
