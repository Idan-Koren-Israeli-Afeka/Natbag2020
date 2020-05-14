package toolsWorkshop;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		
		Scanner in = new Scanner(System.in);
		Airport airport = new Airport("Ben Gurion Airport", "Tel Aviv");
		boolean stopMenu = false;
		do {
			System.out.println(
					"\nChoose an option:\n"
					+ "1. Add a new departure flight\n"
					+ "2. Add a new arrival flight\n"
					+ "3. Show departures flights information (sorted by time)\n"
					+ "4. Show arrivals flights information (sorted by time)\n"
					+ "5. Save flights to file\n"
					+ "6. Load flights from a file\n"
					+ "7. Show depratures between dates\n"
					+ "8. Show arrivals between dates\n"
					+ "9. Show departures by company\n"
					+ "10. Show arrivals by company\n"
					+ "11. Show departures by day of week\n"
					+ "12. Show arrivals by day of week\n"
					+ "Press any other key to finish");
			
			int option = in.nextInt();
			switch(option) {
			case 1:
				//add departure flight
				addNewFlight(Flight.FlightType.Departure, in, airport);
				break;
			case 2:
				//add arrival flight
				addNewFlight(Flight.FlightType.Arrival, in, airport);
				break;
			case 3:
				// show departures
				airport.sortFlightsByTime();
				System.out.println(airport.flightsToString(Flight.FlightType.Departure));
				break;
			case 4:
				airport.sortFlightsByTime();
				System.out.println(airport.flightsToString(Flight.FlightType.Arrival));
				break;
			case 5:
				//save to file
				saveFlights(airport);
				break;
			case 6:
				//load from file
				loadFlights(airport);
				break;
			case 7:
				//show flights from start date to end date
				showFlightsInDates(Flight.FlightType.Departure, in, airport);
				break;
			case 8:
				//show flights from start date to end date
				showFlightsInDates(Flight.FlightType.Arrival, in, airport);
				break;
			case 9:
				//show flights from start date to end date
				System.out.println(showFlightsOfCompany(Flight.FlightType.Departure, in, airport)); //have to change this to all of methonds
				break;
			case 10:
				//show flights from start date to end date
				System.out.println(showFlightsOfCompany(Flight.FlightType.Arrival, in, airport));
				break;
			case 11:
				//show flights from start date to end date
				System.out.println(showFlightsByDayOfWeek(Flight.FlightType.Departure, in, airport));
				break;
			case 12:
				//show flights from start date to end date
				System.out.println(showFlightsByDayOfWeek(Flight.FlightType.Arrival, in, airport));
				break;
			default:
				stopMenu = true;
				break;
			}
		
			
			}while(!stopMenu);
		
	}

	private static void addNewFlight(Flight.FlightType flightType, Scanner in, Airport airport) {
		in.nextLine();
		
		System.out.println("\nPlease enter flight company: ");
		String flightCompany = in.nextLine();
		
		System.out.println("Please enter destination: ");
		String toLocation = in.nextLine();
		
		
		System.out.println("Please enter flight ID: ");
		String flightID = in.nextLine();
		
		System.out.println("Please enter terminal: ");
		int terminal = in.nextInt();
		
		System.out.println("Please enter year");
		int year = in.nextInt();
		
		System.out.println("Please enter month");
		int month = in.nextInt() - 1; //Starting from zero
		
		System.out.println("Please enter day (in month)");
		int day = in.nextInt();
		
		System.out.println("Please enter time (hours and minutes) in format xx:xx");
		String[] timeInDay = in.next().split(":");
		int hours = Integer.parseInt(timeInDay[0]);
		int minutes = Integer.parseInt(timeInDay[1]);
		
		@SuppressWarnings("deprecation")
		Date date = new Date(year,month,day,hours,minutes);
		
		
		Flight newFlight = new Flight(flightID, flightCompany, airport.getLocation(), toLocation, date, terminal, flightType);
		if(airport.addFlight(newFlight))
			System.out.println("flight added succesfully");
		else
			System.out.println("flight wasnt added");
	}
	
	
	private static void saveFlights(Airport airport) {
		try (ObjectOutputStream objOut = new ObjectOutputStream(
				new BufferedOutputStream(new FileOutputStream("flights.obj")))) {
		objOut.writeInt(airport.getDepartures().size());
		for(Flight flight : airport.getDepartures()) {
			objOut.writeObject(flight);
		}
		objOut.writeInt(airport.getArrivals().size());
		for(Flight flight : airport.getArrivals()) {
			objOut.writeObject(flight);
		}
		System.out.println("\nflights saved succesfully!");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	public static void loadFlights(Airport airport) {
		try (ObjectInputStream objIn = new ObjectInputStream(
				new BufferedInputStream(new FileInputStream("flights.obj")))) {
			int depSize = objIn.readInt();
			for (int i = 0; i < depSize; i++) {
				Flight flight = (Flight) objIn.readObject();
				airport.addFlight(flight);
			}
			int arrSize = objIn.readInt();
			for (int i = 0; i < arrSize; i++) {
				Flight flight = (Flight) objIn.readObject();
				airport.addFlight(flight);
			}
			System.out.println("\nflights loaded succesfully!");
		} catch (FileNotFoundException e) {
			System.out.println("Load exception: File \"flights.obj\" was not found!");
		} catch (IOException e) {
			System.out.println("Load exception: " + e.getMessage());
		} catch (ClassNotFoundException e) {
			System.out.println("Load exception: File \"flights.obj\" does not contain flights data!");
		}
	}
	

	private static void showFlightsInDates(Flight.FlightType type, Scanner in, Airport airport) {
		System.out.println("enter starting date:");
		System.out.println("Please enter year");
		int year = in.nextInt();
		
		System.out.println("Please enter month");
		int month = in.nextInt() - 1;
		
		System.out.println("Please enter day (in month)");
		int day = in.nextInt();
		
		
		@SuppressWarnings("deprecation")
		Date startDate = new Date(year,month,day,00,00);
		
		System.out.println("enter ending date:");
		System.out.println("Please enter year");
		year = in.nextInt();
		
		System.out.println("Please enter month");
		month = in.nextInt() - 1;
		
		System.out.println("Please enter day (in month)");
		day = in.nextInt();
		
		
		@SuppressWarnings("deprecation")
		Date endDate = new Date(year,month,day,23,59,59);
		
		airport.sortFlightsByTime();
		
		ArrayList<Flight> array;
		if(type == Flight.FlightType.Arrival)
			array = airport.getArrivals();
		else
			array = airport.getDepartures();
		
		for(Flight f : array) {
			if(f.getDate().getTime() > endDate.getTime())
				return;
			
			if(f.getDate().getTime() >= startDate.getTime())
				System.out.println(f);
			
		}
	}

	
	private static String showFlightsOfCompany(Flight.FlightType type, Scanner in, Airport airport) {
		System.out.println("\nPlease enter flight company: ");
		String flightCompany = in.next();
		String result = "";
		ArrayList<Flight> array;
		if(type == Flight.FlightType.Arrival)
			array = airport.getArrivals();
		else
			array = airport.getDepartures();
		
		
		for(Flight f : array) { 
			int resultOfCompare = f.getFlightCompany().compareTo(flightCompany);
			if(resultOfCompare > 0)
				return "";
			else
				if(resultOfCompare == 0)
					result += f.toString();
		}
		return result;		
	}
	
	
	
	private static String showFlightsByDayOfWeek(Flight.FlightType type, Scanner in, Airport airport) {

		boolean[] daysSelected = new boolean[7];
		int input;
		String result = "";
		do {
			System.out.println("Please enter day (of week 1- sunday, 2- monday...)\nEnter 0 to finish");
			input = in.nextInt();
			if(input > 0 && input < 8)
				daysSelected[input] = true;
		}while(input!=0);
		
		
		
		airport.sortFlightsByTime();
		
		ArrayList<Flight> array;
		if(type == Flight.FlightType.Arrival)
			array = airport.getArrivals();
		else
			array = airport.getDepartures();
		
		for(Flight f : array) {
			if(daysSelected[f.getDate().getDay()-1])
				result += f.toString();
		}
		
		return result;
	}
				
			
	
}
