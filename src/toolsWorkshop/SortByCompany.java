package toolsWorkshop;

import java.util.Comparator;

public class SortByCompany implements Comparator<Flight> {

	@Override
	public int compare(Flight o1, Flight o2) {
		if(o1.getFlightCompany().equals(o2.getFlightCompany()))
			return o1.getDate().compareTo(o2.getDate());
		return o1.getFlightCompany().compareTo(o2.getFlightCompany());
	}
}