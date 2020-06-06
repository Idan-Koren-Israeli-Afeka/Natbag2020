package toolsWorkshop;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import toolsWorkshop.Flight.FlightType;

public class FlightFilter {

	//Each filter will be mapped to an int id
	public enum Filter{
		Departures(0),
		Arrivals(1),
		Company(2),
		City(3),
		fromDate(4),
		toDate(5),
		daysOfWeek(6);
		
		private final int id;
		
		private Filter(int id) {
			this.id= id;
		}
		
		public int getId() {
			return id;
		}
	};
	
	
	private static FlightFilter single_instance = null;

	private Airport airport; //Airport that filters will be applied on

	private boolean departuresOnly;
	private boolean arrivalsOnly;
	private String filteredCompany;
	private String filteredCity;
	private Date filteredFromDate;
	private Date filteredToDate;
	private boolean[] filteredDaysOfWeek = new boolean[7];
	
	private FlightFilter(Airport airport) {
		this.airport = airport;
		removeAllFilters();
	}
	
	public FlightFilter getInstance(Airport airport) {
		if(single_instance == null)
			single_instance = new FlightFilter(airport);
		return single_instance;
	}
	
	
	//This function will use current applied filters to remove flights from the result
	public ArrayList<Flight> filter(){
		ArrayList<Flight> flightsLeft = airport.getAllFlights();
		
		for(Flight flight : flightsLeft) {
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
			if(!flight.getFlightCompany().equals(filteredCompany)) {
				flightsLeft.remove(flight);
				continue;
			}
			if(!flight.getToLocation().equals(filteredCity)) {
				flightsLeft.remove(flight);
				continue;
			}
			if(!flight.getDate().before(filteredFromDate)) {
				flightsLeft.remove(flight);
				continue;
			}
			if(!flight.getDate().after(filteredToDate)) {
				flightsLeft.remove(flight);
				continue;
			}
			for(int day=0;day < 7; day++) {
				if(filteredDaysOfWeek[day]== false && flight.getDate().getDay() == day)
					flightsLeft.remove(flight);
			}
		}
		
		return flightsLeft;
	}
	
	
	public void applyFilter(Filter filter) {
	
		//Need to check that user doesnt do "DEPRATURES ONLY" AND "ARRIVLS ONLY" at the same time
		
		switch(filter) {
		case Departures:
			
			break;
		case Arrivals:
			
			break;
		case Company:
			
			break;
		case City:
			
			break;
			//.....
		
		}
		
	}
	
	public void removeFilter(Filter filter) {
		switch(filter) {
		case Departures:
			
			break;
		case Arrivals:
			
			break;
		case Company:
			
			break;
		case City:
			
			break;
			//.....
		
		}
	}
	
	public void removeAllFilters() {
		departuresOnly = false;
		arrivalsOnly = false;
		filteredCompany = "";
		filteredCity = "";
		filteredFromDate = new Date(0); //Minimum date
		filteredToDate = new Date(Long.MAX_VALUE); //Maximum date
		Arrays.fill(filteredDaysOfWeek, true);
	}
}
