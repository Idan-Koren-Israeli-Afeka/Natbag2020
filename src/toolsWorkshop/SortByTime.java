package toolsWorkshop;

import java.util.Comparator;

public class SortByTime implements Comparator<Flight> {

	@Override
	public int compare(Flight o1, Flight o2) {
		return o1.getDate().compareTo(o2.getDate());
	}
}