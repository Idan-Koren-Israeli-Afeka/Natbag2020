package toolsWorkshop;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class Airport implements Serializable{

	private String name;
	private String country;
	private String city;

	private ArrayList<Flight> departures;
	private ArrayList<Flight> arrivals;

	private ArrayList<Flight> allFlights;
	
	public Airport(String name, String country, String city) {
		super();
		this.name = name;
		this.city = city;
		this.country = country;
		departures = new ArrayList<Flight>();
		arrivals = new ArrayList<Flight>();
		allFlights = new ArrayList<Flight>();
	}

	public Airport(String name, String country, String city, ArrayList<Flight> departures, ArrayList<Flight> arrivals) {
		super();
		this.name = name;
		this.city = city;
		this.country = country;
		this.departures = departures;
		this.arrivals = arrivals;
		allFlights = new ArrayList<Flight>();
		allFlights.addAll(departures);
		allFlights.addAll(arrivals);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Flight> getDepartures() {
		return departures;
	}

	public void setDepartures(ArrayList<Flight> departures) {
		this.departures = departures;
	}

	public ArrayList<Flight> getArrivals() {
		return arrivals;
	}

	public void setArrivals(ArrayList<Flight> arrivals) {
		this.arrivals = arrivals;
	}

	public ArrayList<Flight> getAllFlights() {
		return allFlights;
	}

	public void setAllFlights(ArrayList<Flight> allFlights) {
		this.allFlights = allFlights;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Override
	public String toString() {
		return "Airport [name=" + name + " country=" + country +", city=" + city + "]";
	}

	public void sortFlightsByTime() {
		Collections.sort(departures, new SortByTime());
		Collections.sort(arrivals, new SortByTime());
		Collections.sort(allFlights, new SortByTime());
	}
	
	public void sortFlightsByCompany() {
		Collections.sort(departures, new SortByCompany());
		Collections.sort(arrivals, new SortByCompany());
		Collections.sort(allFlights, new SortByCompany());
	}


	public String flightsToString(Flight.FlightType type) {
		ArrayList<Flight> flights;
		if (type == Flight.FlightType.Arrival)
			flights = arrivals;
		else
			flights = departures;

		StringBuffer result = new StringBuffer();
		for (Flight f : flights) {
			result.append(f + "\n");
		}

		return result.toString();
	}

	public String allFlightsToString() {
		StringBuffer result = new StringBuffer();
		for (Flight f : allFlights) {
			result.append(f + "\n");
		}

		return result.toString();
	}

	public boolean addFlight(Flight newFlight) {
		if (newFlight == null)
			return false;
		if (newFlight.getFlightType() == Flight.FlightType.Arrival)
			arrivals.add(newFlight);
		else if(newFlight.getFlightType() == Flight.FlightType.Departure)
			departures.add(newFlight);
		allFlights.add(newFlight);
		return true;
	}
	
	public boolean removeFlightByFlightID(String flightID, boolean flightType) {
		if(flightType) {
			for (Flight flight : departures) {
				if(flight.getFlightID().equals(flightID)) {
					departures.remove(flight);
					return allFlights.remove(flight);
				}
			}
		}
		else {
			for (Flight flight : arrivals) {
				if(flight.getFlightID().equals(flightID)) {
					arrivals.remove(flight);
					return allFlights.remove(flight);
				}
			}
		}
		return false;
	}

}
