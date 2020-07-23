package toolsWorkshop;

import java.io.Serializable;
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
	private Airport fromLocation; // can be airport
	private Airport toLocation; // can be airport
	private DateTime date;
	public static String[] daysOfWeek = {"saturday", "sunday", "monday", "tuesday", "wednesday", "thursday", "friday"};
	private int terminal;
	private FlightType flightType; // false = Departure, true = Arrival

	public Flight(String flightID, String flightCompany, Airport fromLocation, Airport toLocation, DateTime date,
			int terminal, FlightType flightType) {
		super();
		setFlightID(flightID);
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
		this.flightID = flightID.toUpperCase();
	}

	public String getFlightCompany() {
		return flightCompany;
	}

	public void setFlightCompany(String flightCompany) {
		this.flightCompany = flightCompany;
	}

	public Airport getFromLocation() {
		return fromLocation;
	}

	public void setFromLocation(Airport fromLocation) {
		this.fromLocation = fromLocation;
	}

	public Airport getToLocation() {
		return toLocation;
	}

	public FlightType getFlightType() {
		return flightType;
	}

	public void setToLocation(Airport toLocation) {
		this.toLocation = toLocation;
	}

	public DateTime getDate() {
		return date;
	}

	public void setDate(DateTime date) {
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

	@Override
	public String toString() {
		return "Flight number " + flightID + " by " + flightCompany + ", from " + fromLocation + " to " + toLocation
				+ ", on " + date.toString() + ",  flight type: " + 
				(flightType == FlightType.Arrival? "arrival" : "departure");
	}
}
