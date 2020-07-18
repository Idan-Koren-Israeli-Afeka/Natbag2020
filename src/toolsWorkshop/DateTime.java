package toolsWorkshop;

import java.util.InputMismatchException;

//Immutable objects, checking input while constructing
public class DateTime {
	private int day;
	private int month;
	private int year;
	private int dayOfWeek; // 0 = Sunday
	
	private int hour; //00 to 23
	private int minute;
	
	public DateTime(int day, int month, int year, int dayOfWeek, int hour, int minute) throws InputMismatchException {
		super();
		if(day<0 || day > 31)
			throw new InputMismatchException("Invalid day");
		this.day = day;
		if(month < 0 || month > 12)
			throw new InputMismatchException("Invalid month");
		this.month = month;
		this.year = year;
		if(dayOfWeek<0 || dayOfWeek > 6)
			throw new InputMismatchException("Invalid day of week");
		this.dayOfWeek = dayOfWeek; 
		if(dayOfWeek<0 || dayOfWeek > 6)
			throw new InputMismatchException("Invalid day of week");
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
	
}
