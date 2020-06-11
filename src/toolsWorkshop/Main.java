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

import toolsWorkshop.Flight.FlightType;

public class Main {
	
	final static String MAIN_MENU_MESSAGE = "\nMain Menu: (choose an option)\n" + "1. Add a new flight\n" + "2. Remove a flight\n"
			+ "3. Save flights to file\n" + "4. Load flights from file\n" + "5. Display flights menu\n";
	
	final static String FILTER_MENU_MESSAGE = "\nDisplay Menu: (choose an option)\n" + "1. Departures Only\n"
			+ "2. Arrivals Only\n"+"3. Company\n" + "4. City\n" + "5. From Date\n" + "6. To Date\n" + "7. Days of Week\n"
			+ "8. Reset All Filters\n";
	
	
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		Airport airport = new Airport("Ben Gurion Airport", "Tel Aviv");
		boolean stopMenu = false; 
		do {
			System.out.println(MAIN_MENU_MESSAGE);

			int option = in.nextInt();
			switch (option) {

			case 1: // add a new flight
				addNewFlight(in, airport);
				break;

			case 2: // remove a flight
				removeFlight(in, airport);
				break;

			case 3: // save to file
				saveFlights(airport);
				break;

			case 4: // load from file
				loadFlights(airport);
				break;

			case 5: // display menu
				showDisplayMenu(in ,airport);
				break;
			default:
				stopMenu = true;
				break;
			}

		} while (!stopMenu);

	}


	
	public static void showDisplayMenu(Scanner in, Airport airport){
		boolean stopDisplayMenu = false;
		ArrayList<Flight> flightsToDisplay;
		FlightFilter menu = FlightFilter.getInstance(airport);
		
		do {
			System.out.println(FILTER_MENU_MESSAGE);

			int displayOption = in.nextInt();
			switch (displayOption) {
			case 1: // departures
				menu.applyDeparturesOnly();
				break;
			case 2: // arrivals
				menu.applyArrivelsOnly();
				break;
			case 3: // company
				System.out.println("Please enter location to filter:");
				String companyToFilter = in.next();
				menu.applyCompany(companyToFilter);
				break;
			case 4: // location
				System.out.println("Please enter location to filter:");
				String locationToFilter = in.next();
				menu.applyLocation(locationToFilter);
				break;

			case 5: // from date
				Date fromDate = getDateFromUser(in);
				menu.applyFromDate(fromDate);
				break;

			case 6: // to date
				Date toDate = getDateFromUser(in);
				menu.applyToDate(toDate);
				break;

			case 7: // days of week
				//flightsToDisplay = displayDaysOfWeek(in, airport, flightsToDisplay, temp);
				break;

			case 8: // reset
				menu.removeAllFilters();
				break;

			default:
				stopDisplayMenu = true;
				break;
			}

			flightsToDisplay = menu.filter();
			flightsToDisplay.sort(new SortByTime());
			for (Flight flight : flightsToDisplay) {
				System.out.println(flight);
			}

		} while (!stopDisplayMenu);
	}

	private static void removeFlight(Scanner in, Airport airport) {
		System.out
				.println("Which type of flight would you like to remove?\n" + "press d for departure or a for arrival");
		char answer2 = in.next().charAt(0);
		if (answer2 == 'd' || answer2 == 'D')
			removeFlightHelper(true, in, airport);
		else if (answer2 == 'a' || answer2 == 'A')
			removeFlightHelper(false, in, airport);
		else
			System.out.println("wrong input");

	}

	private static void removeFlightHelper(boolean flightType, Scanner in, Airport airport) {
		in.nextLine();

		System.out.println("please enter flight ID: ");
		String flightID = in.nextLine();

		if (airport.removeFlightByFlightID(flightID, flightType))
			System.out.println("Flight " + flightID + " removed successfully");
		else {
			System.out.println("nothing happened");
		}
	}

	private static void addNewFlight(Scanner in, Airport airport) {
		System.out.println("Which type of flight would you like to add?\n" + "press d for departure or a for arrival");
		char answer = in.next().charAt(0);
		if (answer == 'd' || answer == 'D')
			addNewFlightHelper(Flight.FlightType.Departure, in, airport);
		else if (answer == 'a' || answer == 'A')
			addNewFlightHelper(Flight.FlightType.Arrival, in, airport);
		else
			System.out.println("wrong input");
	}

	private static void addNewFlightHelper(Flight.FlightType flightType, Scanner in, Airport airport) {
		in.nextLine();

		System.out.println("\nPlease enter flight company: ");
		String flightCompany = in.nextLine();

		System.out.println("Please enter location: ");
		String location = in.nextLine();

		System.out.println("Please enter flight ID: ");
		String flightID = in.nextLine();

		System.out.println("Please enter terminal: ");
		int terminal = in.nextInt();

		Date date = getDateFromUser(in);
		Flight newFlight = null;
		if (flightType == FlightType.Arrival) {
			newFlight = new Flight(flightID, flightCompany, location, airport.getLocation(), date, terminal,
					flightType);
		} else {
			newFlight = new Flight(flightID, flightCompany, airport.getLocation(), location, date, terminal,
					flightType);
		}
		if (airport.addFlight(newFlight))
			System.out.println("Flight added succesfully");
		else
			System.out.println("Flight wasnt added");
	}

	private static void saveFlights(Airport airport) {
		try (ObjectOutputStream objOut = new ObjectOutputStream(
				new BufferedOutputStream(new FileOutputStream("flights.obj")))) {
			objOut.writeInt(airport.getDepartures().size());
			for (Flight flight : airport.getDepartures()) {
				objOut.writeObject(flight);
			}
			objOut.writeInt(airport.getArrivals().size());
			for (Flight flight : airport.getArrivals()) {
				objOut.writeObject(flight);
			}
			System.out.println("\nFlights saved succesfully!");
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
	
	@SuppressWarnings("deprecation")
	public static Date getDateFromUser(Scanner in)
	{
		System.out.println("Please enter year:");
		int year = in.nextInt();

		System.out.println("Please enter month:");
		int month = in.nextInt() - 1; // Starting from zero

		System.out.println("Please enter day (in month):");
		int day = in.nextInt();

		System.out.println("Please enter time (hours and minutes) in format xx:xx");
		String[] timeInDay = in.next().split(":");
		int hours = Integer.parseInt(timeInDay[0]);
		int minutes = Integer.parseInt(timeInDay[1]);

		return new Date(year, month, day, hours, minutes);
	}
}
