package toolsWorkshop;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import toolsWorkshop.Flight.FlightType;

public class FlightFilter {
	
	private static FlightFilter single_instance = null;

	private Airport airport; //Airport that filters will be applied on

	private boolean departuresOnly;
	private boolean arrivalsOnly;
	private String filteredCompany;
	private String filteredCountry;
	private String filteredCity;
	private String filteredAirportName;
	private Date filteredFromDate;
	private Date filteredToDate;
	private boolean[] filteredDaysOfWeek = new boolean[7];
	
	private FlightFilter(Airport airport) {
		this.airport = airport;
		removeAllFilters();
	}
	
	public static FlightFilter getInstance(Airport airport) {
		if(single_instance == null)
			single_instance = new FlightFilter(airport);
		return single_instance;
	}
	
	
	//This function will use current applied filters to remove flights from the result
	@SuppressWarnings("deprecation")
	public ArrayList<Flight> filter(){
		ArrayList<Flight> flightsLeft = new ArrayList<>(airport.getAllFlights());
	
		ArrayList<Flight> allFlights = new ArrayList<>(airport.getAllFlights());
		for(Flight flight : allFlights) {
			if(departuresOnly)
				if(flight.getFlightType()!=FlightType.Departure) {
					flightsLeft.remove(flight);
					continue; //Flight is removed from list, skipping to the next flight.
				}
			if(arrivalsOnly)
				if(flight.getFlightType()!=FlightType.Arrival) {
					flightsLeft.remove(flight);
					continue;
				}
			if(!filteredCompany.equalsIgnoreCase("") && !flight.getFlightCompany().equalsIgnoreCase(filteredCompany)) {
				flightsLeft.remove(flight);
				continue;
			}
			if(!filteredCountry.equalsIgnoreCase("") &&
					!flight.getToLocation().getCountry().equalsIgnoreCase(filteredCountry) &&
					!flight.getFromLocation().getCountry().equalsIgnoreCase(filteredCountry)) {
				flightsLeft.remove(flight);
				continue;
			}
			if(!filteredCity.equalsIgnoreCase("") &&
					!flight.getToLocation().getCity().equalsIgnoreCase(filteredCity) &&
					!flight.getFromLocation().getCity().equalsIgnoreCase(filteredCity)) {
				flightsLeft.remove(flight);
				continue;
			}
			if(!filteredAirportName.equalsIgnoreCase("") &&
					!flight.getToLocation().getCity().equalsIgnoreCase(filteredAirportName) &&
					!flight.getFromLocation().getCity().equalsIgnoreCase(filteredAirportName)) {
				flightsLeft.remove(flight);
				continue;
			}
			if(flight.getDate().after(filteredFromDate)) {
				System.out.println("Removing Flight " + flight + " Because " + flight.getDate() + " is after" + filteredToDate);
				flightsLeft.remove(flight);
				continue;
			}
			if(flight.getDate().before(filteredToDate)) {
				System.out.println("Removing Flight " + flight + " Because " + flight.getDate() + " is before" + filteredToDate);
				flightsLeft.remove(flight);
				continue;
			}
			for(int day=0;day < 7; day++) {
				if(filteredDaysOfWeek[day] == false && flight.getDate().getDay() == day)
					flightsLeft.remove(flight);
			}
		}
		
		return flightsLeft;
	}

		
	//Region - apply function for every filter
	public void applyDeparturesOnly(){
		arrivalsOnly = false;
		departuresOnly = true;
	}
	
	public void applyArrivelsOnly(){
		arrivalsOnly = true;
		departuresOnly = false;
	}
	
	public void applyCompany(String company){
		filteredCompany = company;
	}
	
	public void applyCountry(String country){
		filteredCountry = country;
	}
	
	public void applyCity(String city){
		filteredCity = city;
	}
	
	public void applyAirportName(String airportName){
		filteredAirportName = airportName;
	}
	
	public void applyFromDate(Date from) {
		filteredFromDate = from;
	}
	
	public void applyToDate(Date to) {
		filteredToDate = to;
	}
	
	//Input: list of days to be filtered, as numbers from 0-6
	public void applySingleDayOfWeek(int... days) {
		Arrays.fill(filteredDaysOfWeek, false);
		for(int day : days)
			filteredDaysOfWeek[day] = true; //Filter only selected days 
	}
	
	//Input: list of days to be filtered, as numbers from 0-6
	public void applySingleDayOfWeek(boolean[] days) {
		filteredDaysOfWeek = days;
	}
	
	
	//Region - remove function for everyfilter
	
	public void removeAllFilters() {
		departuresOnly = false;
		arrivalsOnly = false;
		filteredCompany = "";
		filteredCountry = "";
		filteredCity = "";
		filteredAirportName = "";
		filteredFromDate = new Date(Long.MIN_VALUE); //Minimum date
		filteredToDate = new Date(Long.MAX_VALUE); //Maximum date
		Arrays.fill(filteredDaysOfWeek, true);
	}
	
}
