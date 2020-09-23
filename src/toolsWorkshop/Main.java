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
import java.util.Scanner;

import toolsWorkshop.Flight.FlightType;

public class Main {
	
	final static String MAIN_MENU_MESSAGE = "\nMain Menu: (choose an option)\n" + "1. Add a new flight\n" + "2. Remove a flight\n"
			+ "3. Save flights to file\n" + "4. Load flights from file\n" + "5. Display flights menu\n";
	
	final static String FILTER_MENU_MESSAGE = "\nDisplay Menu: (choose an option)\n" + "1. Departures Only\n"
			+ "2. Arrivals Only\n"+"3. Company\n" + "4. Country\n" + "5. City\n"  + "6. Airport Name\n"  +"7. From Date\n" + "8. To Date\n" + "9. Days of Week\n"
			+ "0. Reset All Filters\n" + "Press any other key to go back";
	
	final static String FLIGHTS_FILE_NAME = "flights.obj";
	
	
	public static void main(String[] args) {
		Airport airport = new Airport("TLV", "Israel", "Tel Aviv");

		if(args.length>0 && args[0].equals("text")) {
			loadFlights(airport);
			ArrayList<Flight> toPrint = generateFilterFromArgs(airport, args).filter();
			System.out.println(toPrint);
			//Right now it prints all flights of the airport, we have to put the generated filter on it
		}
		else
		if(args.length>0 && args[0].equals("html")) {
			loadFlights(airport);
			ArrayList<Flight> toPrint = generateFilterFromArgs(airport, args).filter();
			printWebPage(toPrint);
			//Right now it prints all flights of the airport, we have to put the generated filter on it
		}
		else {
			Scanner in = new Scanner(System.in);
			
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
	}


	
	public static void showDisplayMenu(Scanner in, Airport airport){
		boolean stopDisplayMenu = false;
		ArrayList<Flight> flightsToDisplay;
		FlightFilter menu = FlightFilter.getInstance(airport);
		
		do {
			flightsToDisplay = menu.filter();
			flightsToDisplay.sort(new SortByTime());
			for (Flight flight : flightsToDisplay) {
				System.out.println(flight);
			}
			
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
				System.out.println("Please enter company to filter:");
				String companyToFilter = in.next();
				menu.applyCompany(companyToFilter);
				break;
				
			case 4: // Country
				System.out.println("Please enter country to filter:");
				String countryToFilter = in.next();
				menu.applyCountry(countryToFilter);
				break;
				
			case 5: // City
				System.out.println("Please enter city to filter:");
				String cityToFilter = in.next();
				menu.applyCity(cityToFilter);
				break;
				
			case 6: // Airport Name
				System.out.println("Please enter airport name to filter:");
				String airportNameToFilter = in.next();
				menu.applyAirportName(airportNameToFilter);
				break;

			case 7: // from date
				DateTime fromDate = getDateFromUser(in);
				menu.applyFromDate(fromDate);
				break;

			case 8: // to date
				DateTime toDate = getDateFromUser(in);
				menu.applyToDate(toDate);
				break;

			case 9: // days of week
				boolean[] daysFilter = new boolean[7];
				System.out.println("Choose days of week to show: 0 represents sunday, 6 represents saturday");
				for(int i=0;i<7;i++) {
					System.out.print("Insert day of week #" + i + " (y/n): ");
					String option = in.next();
					if(option.equalsIgnoreCase("y"))
						daysFilter[i] = true;
					else
						daysFilter[i] = false;	
				}
				menu.applyDaysOfWeek(daysFilter);
				break;

			case 0: // reset
				menu.removeAllFilters();
				break;

			default:
				stopDisplayMenu = true;
				break;
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
			System.out.println("Flight not found, nothing happened");
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

		System.out.println("Please enter airport name: ");
		String airportName = in.nextLine();
		
		System.out.println("Please enter airport country: ");
		String airportCountry = in.nextLine();
		
		System.out.println("Please enter airport city: ");
		String airportCity = in.nextLine();
		
		Airport flightAirport = new Airport(airportName, airportCountry, airportCity);

		System.out.println("Please enter flight ID: ");
		String flightID = in.nextLine();

		System.out.println("Please enter terminal: ");
		int terminal = in.nextInt();

		DateTime date = getDateFromUser(in);
		Flight newFlight = null;
		if (flightType == FlightType.Arrival) {
			newFlight = new Flight(flightID, flightCompany, flightAirport , airport, date, terminal,
					flightType);
		} else {
			newFlight = new Flight(flightID, flightCompany, airport, flightAirport , date, terminal,
					flightType);
		}
		if (airport.addFlight(newFlight))
			System.out.println("Flight added succesfully");
		else
			System.out.println("Flight wasnt added");
	}

	private static void saveFlights(Airport airport) {
		try (ObjectOutputStream objOut = new ObjectOutputStream(
				new BufferedOutputStream(new FileOutputStream(FLIGHTS_FILE_NAME)))) {
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
				new BufferedInputStream(new FileInputStream(FLIGHTS_FILE_NAME)))) {
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
		} catch (FileNotFoundException e) {
			System.out.println("Load exception: File \"flights.obj\" was not found!");
		} catch (IOException e) {
			System.out.println("Load exception: " + e.getMessage());
		} catch (ClassNotFoundException e) {
			System.out.println("Load exception: File \"flights.obj\" does not contain flights data!");
		}
	}
	
	public static DateTime getDateFromUser(Scanner in)
	{
		System.out.println("Please enter year:");
		int year = in.nextInt();

		System.out.println("Please enter month:");
		int month = in.nextInt();

		System.out.println("Please enter day (in month):");
		int day = in.nextInt();

		System.out.println("Please enter day of week: (0 for Sunday, 6 for Saturday)" );
		int dayOfWeek = in.nextInt();
		
		System.out.println("Please enter time (hours and minutes) in format xx:xx");
		String[] timeInDay = in.next().split(":");
		int hours = Integer.parseInt(timeInDay[0]);
		int minutes = Integer.parseInt(timeInDay[1]);

		return new DateTime(day,month,year, dayOfWeek, hours, minutes);
	}
	
	
	public static FlightFilter generateFilterFromArgs(Airport airport, String[] allArgs) {
		FlightFilter result = FlightFilter.getInstance(airport);

		switch(allArgs[1].toLowerCase()) {
		case "departures":
			result.applyDeparturesOnly();
			break;
		case "arrivals":
			result.applyArrivelsOnly();
			break;
		}
		
		result.applyCountry(allArgs[3].toLowerCase());
		result.applyCity(allArgs[4].toLowerCase());
		result.applyAirportName(allArgs[5].toLowerCase());
		result.applyCompany(allArgs[2].toLowerCase());
		
		//Edge case - no valid input for date, end date will be max, start date will be min possible
		DateTime fromDate, toDate;
		if(isInputDateValid(allArgs[6],allArgs[7],allArgs[8]))
			fromDate = new DateTime(Integer.parseInt(allArgs[6]),Integer.parseInt(allArgs[7]),Integer.parseInt(allArgs[8]),0,0);
		else
			fromDate = DateTime.MIN_DATE;
		
		if(isInputDateValid(allArgs[9],allArgs[10],allArgs[11]))
			toDate = new DateTime(Integer.parseInt(allArgs[9]),Integer.parseInt(allArgs[10]),Integer.parseInt(allArgs[11]),23,59);
		else
			toDate = DateTime.MAX_DATE;
		result.applyFromDate(fromDate);
		result.applyToDate(toDate);
		
		boolean[] daysOfWeek = new boolean[7];
		for(int i = 12; i<19; i++) {
			if(allArgs[i].equalsIgnoreCase("false"))
				daysOfWeek[i-12] = false;
			else
				daysOfWeek[i-12] = true;
		}
		result.applyDaysOfWeek(daysOfWeek);
		
		
		return result;
	}
	
	public static boolean isInputDateValid(String day, String month, String year) {
		if(year.equals("") || month.equals("") || day.equals(""))
			return false;
		try {
			Integer.parseInt(year);
			Integer.parseInt(month);
			Integer.parseInt(day);
		}
		catch(Exception e) {
			return false;
		}
		return true; //Date is not null and its made of numbers only
	}
	

	
	public static void printWebPage(ArrayList<Flight> toPrint) {
		HTMLPrinter printer = HTMLPrinter.get_instance();
		printer.printPage(toPrint);
	}
	
	
}
