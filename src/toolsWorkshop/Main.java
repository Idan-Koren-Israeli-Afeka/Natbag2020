package toolsWorkshop;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
					+ "Press any other key to finish");
			
			char option = in.next().charAt(0);
			switch(option) {
			case '1':
				//add departure flight
				addNewFlight(Flight.FlightType.Departure, in, airport);
				break;
			case '2':
				//add arrival flight
				addNewFlight(Flight.FlightType.Arrival, in, airport);
				break;
			case '3':
				// show departures
				airport.sortFlights();
				System.out.println(airport.flightsToString(Flight.FlightType.Departure));
				break;
			case '4':
				airport.sortFlights();
				System.out.println(airport.flightsToString(Flight.FlightType.Arrival));
				break;
			case '5':
				//save to file
				saveFlights(airport);
				break;
			case '6':
				//load from file
				loadFlights(airport);
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
}
