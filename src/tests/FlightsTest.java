package tests;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.jupiter.api.Test;

import toolsWorkshop.Airport;
import toolsWorkshop.Flight;

class FlightsTest {

	@Test
	public void statementTest1() {
		Airport airport = createFlight();
		StringBuffer expectedResult1 = new StringBuffer();
		expectedResult1.append("Flight number 64a21 by Airlines, from New York to Tel Aviv, at 23/7/2020, on 18:45\n");
		expectedResult1.append("Flight number 1243a by El Al, from Tel Aviv to London, at 22/7/2020, on 15:30\n");

		assertEquals(expectedResult1.toString(), airport.allFlightsToString());

		StringBuffer expectedResult2 = new StringBuffer();
		expectedResult2.append("Flight number 1243a by El Al, from Tel Aviv to London, at 22/7/2020, on 15:30\n");
		expectedResult2.append("Flight number 64a21 by Airlines, from New York to Tel Aviv, at 23/7/2020, on 18:45\n");

		airport.sortFlightsByTime();

		assertEquals(expectedResult2.toString(), airport.allFlightsToString());
	}

	@SuppressWarnings("deprecation")
	public Airport createFlight() {
		Airport airport = new Airport("Ben Gurion", "Tel Aviv");
		Flight flight1 = new Flight("1243a", "El Al", "Tel Aviv", "London", new Date(2020, 7, 22, 15, 30), 3,
				Flight.FlightType.Departure);
		Flight flight2 = new Flight("64a21", "Airlines", "New York", "Tel Aviv", new Date(2020, 7, 23, 18, 45), 3,
				Flight.FlightType.Arrival);
		airport.addFlight(flight2);
		airport.addFlight(flight1);
		return airport;
	}

}
