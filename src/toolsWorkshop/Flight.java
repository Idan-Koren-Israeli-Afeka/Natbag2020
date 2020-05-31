package toolsWorkshop;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 97254
 *
 */
public class Flight implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public enum FlightType {
		Departure, Arrival
	};

	private String flightID; // for example: LY001
	private String flightCompany; // for example: ELAL
	private String fromLocation; // can be airport
	private String toLocation; // can be airport
	private Date date;
	public static String[] daysOfWeek = {"saturday", "sunday", "monday", "tuesday", "wednesday", "thursday", "friday"};
	private int terminal;
	private FlightType flightType; // false = Departure, true = Arrival

	public Flight(String flightID, String flightCompany, String fromLocation, String toLocation, Date date,
			int terminal, FlightType flightType) {
		super();
		this.flightID = flightID;
		this.flightCompany = flightCompany;
		this.fromLocation = fromLocation;
		this.toLocation = toLocation;
		this.date = date;
		this.terminal = terminal;
		this.flightType = flightType;
	}

	public String getFlightID() {
		return flightID;
	}

	public void setFlightID(String flightID) {
		this.flightID = flightID;
	}

	public String getFlightCompany() {
		return flightCompany;
	}

	public void setFlightCompany(String flightCompany) {
		this.flightCompany = flightCompany;
	}

	public String getFromLocation() {
		return fromLocation;
	}

	public void setFromLocation(String fromLocation) {
		this.fromLocation = fromLocation;
	}

	public String getToLocation() {
		return toLocation;
	}

	public FlightType getFlightType() {
		return flightType;
	}

	public void setToLocation(String toLocation) {
		this.toLocation = toLocation;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getTerminal() {
		return terminal;
	}

	public void setTerminal(int terminal) {
		this.terminal = terminal;
	}

	public FlightType isFlightType() {
		return flightType;
	}

	public void setFlightType(FlightType flightType) {
		this.flightType = flightType;
	}

	@SuppressWarnings("deprecation")
	@Override
	public String toString() {
		return "Flight number " + flightID + " by " + flightCompany + ", from " + fromLocation + " to " + toLocation
				+ ", on " + date.getDate() + "/" + (date.getMonth()+1) + "/" + date.getYear() + ", at " + date.getHours()
				+ ":" + date.getMinutes() + ", day of week: " + daysOfWeek[date.getDay()] + ", flight type: " + 
				(flightType == FlightType.Arrival? "arrival" : "departure");
	}
}
