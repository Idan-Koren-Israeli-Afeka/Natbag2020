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

	public static void main(String[] args) {

		final String MAIN_MENU_MESSAGE = "\nMain Menu: (choose an option)\n" + "1. Add a new flight\n" + "2. Remove a flight\n"
				+ "3. Save flights to file\n" + "4. Load flights from file\n" + "5. Display flights menu\n";
		
		final String FILTER_MENU_MESSAGE = "\nDisplay Menu: (choose an option)\n" + "1. Departures / Arrivals\n"
				+ "2. Company\n" + "3. City\n" + "4. from date\n" + "5. to date\n" + "6. days of week\n"
				+ "7. reset\n";
		
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
				boolean stopDisplayMenu = false;
				ArrayList<Flight> flightsToDisplay = new ArrayList<Flight>();
				flightsToDisplay = airport.getAllFlights();
				ArrayList<Flight> temp = new ArrayList<Flight>();
				do {
					System.out.println(FILTER_MENU_MESSAGE);

					int displayOption = in.nextInt();
					switch (displayOption) {
					case 1: // departures / arrivals
						flightsToDisplay = departuresOrArrivals(in, airport, flightsToDisplay);
						break;

					case 2: // company
						flightsToDisplay = displayByCompany(in, airport, flightsToDisplay, temp);
						break;

					case 3: // city
						flightsToDisplay = displayByCity(in, airport, flightsToDisplay, temp);
						break;

					case 4: // from date
						flightsToDisplay = displayFromDate(in, airport, flightsToDisplay, temp);
						break;

					case 5: // to date
						flightsToDisplay = displayToDate(in, airport, flightsToDisplay, temp);
						break;

					case 6: // days of week
						flightsToDisplay = displayDaysOfWeek(in, airport, flightsToDisplay, temp);
						break;

					case 7: // reset
						flightsToDisplay.removeAll(flightsToDisplay);
						flightsToDisplay.addAll(airport.getAllFlights());
						break;

					default:
						stopDisplayMenu = true;
						break;
					}

					flightsToDisplay.sort(new SortByTime());
					for (Flight flight : flightsToDisplay) {
						System.out.println(flight);
					}

				} while (!stopDisplayMenu);
				break;

			default:
				stopMenu = true;
				break;
			}

		} while (!stopMenu);

	}

	@SuppressWarnings("deprecation")
	private static ArrayList<Flight> displayDaysOfWeek(Scanner in, Airport airport, ArrayList<Flight> flightsToDisplay,
			ArrayList<Flight> temp) {
		boolean[] daysSelected = new boolean[7];
		int input;
		do {
			System.out.println("Please enter day (of week: 0- saturday, 1- sunday, 2- monday...)\nEnter -1 to finish");
			input = in.nextInt();
			if (input >= 0 && input < 7)
				daysSelected[input] = true;
		} while (input != -1);
		flightsToDisplay.sort(new SortByTime());
		temp.removeAll(temp);
		for (Flight flight : flightsToDisplay) {
			if (daysSelected[flight.getDate().getDay()])
				temp.add(flight);
		}
		flightsToDisplay.removeAll(flightsToDisplay);
		flightsToDisplay.addAll(temp);
		temp.removeAll(temp);
		return flightsToDisplay;
	}

	private static ArrayList<Flight> displayToDate(Scanner in, Airport airport, ArrayList<Flight> flightsToDisplay,
			ArrayList<Flight> temp) {
		System.out.println("enter ending date:");
		System.out.println("Please enter year");
		int year = in.nextInt();

		System.out.println("Please enter month");
		int month = in.nextInt() - 1;

		System.out.println("Please enter day (in month)");
		int day = in.nextInt();

		@SuppressWarnings("deprecation")
		Date endDate = new Date(year, month, day, 23, 59, 59);

		flightsToDisplay.sort(new SortByTime());
		temp.removeAll(temp);
		for (Flight flight : flightsToDisplay) {
			if (flight.getDate().getTime() < endDate.getTime())
				temp.add(flight);
		}
		flightsToDisplay.removeAll(flightsToDisplay);
		flightsToDisplay.addAll(temp);
		temp.removeAll(temp);
		return flightsToDisplay;
	}

	private static ArrayList<Flight> displayFromDate(Scanner in, Airport airport, ArrayList<Flight> flightsToDisplay,
			ArrayList<Flight> temp) {
		System.out.println("enter starting date:");
		System.out.println("Please enter year");
		int year = in.nextInt();

		System.out.println("Please enter month");
		int month = in.nextInt() - 1;

		System.out.println("Please enter day (in month)");
		int day = in.nextInt();

		@SuppressWarnings("deprecation")
		Date startDate = new Date(year, month, day, 00, 00);

		flightsToDisplay.sort(new SortByTime());
		temp.removeAll(temp);
		for (Flight flight : flightsToDisplay) {
			if (flight.getDate().getTime() >= startDate.getTime())
				temp.add(flight);
		}
		flightsToDisplay.removeAll(flightsToDisplay);
		flightsToDisplay.addAll(temp);
		temp.removeAll(temp);
		return flightsToDisplay;
	}

	private static ArrayList<Flight> displayByCity(Scanner in, Airport airport, ArrayList<Flight> flightsToDisplay,
			ArrayList<Flight> temp) {
		System.out.println("Enter city to display:");
		String city = in.next();
		flightsToDisplay.sort(new SortByCity());
		for (Flight flight : flightsToDisplay) {
			if (!flight.getToLocation().equals(city))
				temp.add(flight);
		}
		flightsToDisplay.removeAll(temp);
		temp.removeAll(temp);
		return flightsToDisplay;
	}

	private static ArrayList<Flight> displayByCompany(Scanner in, Airport airport, ArrayList<Flight> flightsToDisplay,
			ArrayList<Flight> temp) {
		System.out.println("Enter airline to display:");
		String airline = in.next();
		flightsToDisplay.sort(new SortByCompany());
		for (Flight flight : flightsToDisplay) {
			if (!flight.getFlightCompany().equals(airline))
				temp.add(flight);
		}
		flightsToDisplay.removeAll(temp);
		temp.removeAll(temp);
		return flightsToDisplay;
	}

	private static ArrayList<Flight> departuresOrArrivals(Scanner in, Airport airport,
			ArrayList<Flight> flightsToDisplay) {
		System.out.println(
				"Which type of flight would you like to display?\n" + "press d for departures or a for arrivals");
		char answer = in.next().charAt(0);
		if (answer == 'd' || answer == 'D')
			return airport.getDepartures();
		else if (answer == 'a' || answer == 'A')
			return airport.getArrivals();
		else {
			System.out.println("wrong input");
			return airport.getAllFlights();
		}
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

		System.out.println("Please enter destination: ");
		String location = in.nextLine();

		System.out.println("Please enter flight ID: ");
		String flightID = in.nextLine();

		System.out.println("Please enter terminal: ");
		int terminal = in.nextInt();

		System.out.println("Please enter year");
		int year = in.nextInt();

		System.out.println("Please enter month");
		int month = in.nextInt() - 1; // Starting from zero

		System.out.println("Please enter day (in month)");
		int day = in.nextInt();

		System.out.println("Please enter time (hours and minutes) in format xx:xx");
		String[] timeInDay = in.next().split(":");
		int hours = Integer.parseInt(timeInDay[0]);
		int minutes = Integer.parseInt(timeInDay[1]);

		@SuppressWarnings("deprecation")
		Date date = new Date(year, month, day, hours, minutes);
		Flight newFlight = null;
		if (flightType == FlightType.Arrival) {
			newFlight = new Flight(flightID, flightCompany, location, airport.getLocation(), date, terminal,
					flightType);
		} else {
			newFlight = new Flight(flightID, flightCompany, airport.getLocation(), location, date, terminal,
					flightType);
		}
		if (airport.addFlight(newFlight))
			System.out.println("flight added succesfully");
		else
			System.out.println("flight wasnt added");
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
